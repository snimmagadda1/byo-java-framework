package com.github.snimmagadda1.dao;

import com.github.snimmagadda1.model.Company;
import java.util.logging.Logger;

public class CompanyDaoImpl implements CompanyDao {

  private static final Logger logger = Logger.getLogger(CompanyDaoImpl.class.getName());

  @Override
  public void createCompany(Company company) {
    logger.info("## DAO ## ---- START - create company");

    logger.info("## DAO ## ---- END - create company");
  }

  @Override
  public void updateCompany(Company company) {
    logger.info("## DAO ## ---- START - update company");

    logger.info("## DAO ## ---- END - update company");
  }
}
