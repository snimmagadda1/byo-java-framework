package com.github.snimmagadda1.framework;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import com.github.snimmagadda1.framework.annotation.Autowired;
import com.github.snimmagadda1.framework.annotation.Component;
import com.github.snimmagadda1.framework.exception.FrameworkException;

public class ApplicationContext {
    private static final Logger logger = Logger.getLogger(ApplicationContext.class.getName());

    private final Set<Class<?>> componentBeans;

    public ApplicationContext(Class<?> applicationClass) {
        final Reflections reflections = new Reflections(applicationClass.getPackage().getName());
        // Use refections lib to get all classes annotated with @Component
        this.componentBeans = reflections.getTypesAnnotatedWith(Component.class)
                .stream()
                .filter(clazz -> !clazz.isInterface())
                .collect(Collectors.toSet());
        logger.info("## CONTEXT ## ---- CONSTRUCTOR - initialized context with " + componentBeans.size() + " beans");
    }

    public <T> T getBean(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new FrameworkException("Class " + clazz.getName() + " should be an interface");
        }

        final Class<T> implementation = findImplementationByInterface(clazz);
        return createBean(implementation);
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> findImplementationByInterface(Class<T> clazz) {
        final Set<Class<?>> classesWithInterfaces = componentBeans.stream()
                .filter(componentBean -> List.of(componentBean.getInterfaces()).contains(
                        clazz))
                .collect(Collectors.toSet());

        if (classesWithInterfaces.size() > 1) {
            throw new FrameworkException("There are more than 1 implementation of: " + clazz.getName());
        }

        return (Class<T>) classesWithInterfaces.stream()
                .findFirst()
                .orElseThrow(() -> new FrameworkException("The is no class with interface: " + clazz));

    }

    // Note: this is currently creating the real instance. Will be enhanced w/ proxy
    private <T> T createBean(Class<T> implementation) {
        try {
            final Constructor<T> constructor = findConstructor(implementation);
            final Object[] parameters = findConstructorParameters(constructor);

            return constructor.newInstance(parameters);
        } catch (FrameworkException e) {
            throw e;
        } catch (Exception e) {
            throw new FrameworkException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Constructor<T> findConstructor(Class<T> clazz) {
        final Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
        if (constructors.length == 1) {
            return constructors[0];
        }

        final Set<Constructor<T>> constructorsWithAnnotation = Arrays.stream(constructors)
                .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toSet());

        if (constructorsWithAnnotation.size() > 1) {
            throw new FrameworkException(
                    "There are more than 1 constructor with Autowired annotation: " + clazz.getName());
        }

        return constructorsWithAnnotation.stream()
                .findFirst()
                .orElseThrow(() -> new FrameworkException(
                        "Cannot find constructor with annotation Autowired: " + clazz.getName()));
    }

    // Find constructor parameters @Autowired and recursively call getBean to instantiate through @Compnent mechanisms
    private <T> Object[] findConstructorParameters(Constructor<T> constructor) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        return Arrays.stream(parameterTypes)
                .map(this::getBean)
                .toArray(Object[]::new);
    }

}
