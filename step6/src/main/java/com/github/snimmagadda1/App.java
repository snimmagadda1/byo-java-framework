/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.snimmagadda1;

import com.github.snimmagadda1.framework.ApplicationContext;
import com.github.snimmagadda1.model.Company;
import com.github.snimmagadda1.service.CompanyService;
import java.util.logging.Logger;

public class App {
  private static final Logger logger = Logger.getLogger(App.class.getName());

  public String getGreeting() {
    return "_template Hello World!";
  }

  public static void main(String[] args) {
    // Constructor with the package to search for managed classes
    final ApplicationContext applicationContext = new ApplicationContext(App.class);
    final CompanyService companyServiceProxy = applicationContext.getBean(CompanyService.class);

    final Company company1 = new Company();
    logger.info(companyServiceProxy.generateToken(company1));
    logger.info(companyServiceProxy.generateToken(company1));

    final Company company2 = new Company();
    logger.info(companyServiceProxy.generateToken(company2));
  }
}
