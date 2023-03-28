# Application context proxy creation

The framework `ApplicationContext` should wrap object instances in a proxy for control over method invocation.

This will be leveraged to implement the `@Transactional` annotation.
