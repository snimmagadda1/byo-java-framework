package com.github.snimmagadda1;

import com.github.snimmagadda1.dao.CompanyDaoImpl;
import com.github.snimmagadda1.model.Company;
import com.github.snimmagadda1.service.CompanyServiceImpl;

public class App {
  public String getGreeting() {
    return "step1 Hello World!";
  }

  public static void main(String[] args) {
    final CompanyDaoImpl companyDao = new CompanyDaoImpl();
    final CompanyServiceImpl companyService = new CompanyServiceImpl(companyDao);

    companyService.createCompany(new Company());
  }
}
