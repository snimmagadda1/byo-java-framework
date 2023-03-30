package com.github.snimmagadda1.service;

import com.github.snimmagadda1.dao.CompanyDao;
import com.github.snimmagadda1.model.Company;
import java.util.logging.Logger;

public class CompanyServiceImpl implements CompanyService {

  private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class.getName());

  private final CompanyDao companyDao;

  public CompanyServiceImpl(CompanyDao companyDao) {
    this.companyDao = companyDao;
  }

  @Override
  public void createCompany(Company company) {
    try {
      beginTransaction();

      logger.info("## SERVICE ## ---- START - create company");
      companyDao.createCompany(company);
      logger.info("## SERVICE ## ---- END - create company");

      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
      throw e;
    }
  }

  @Override
  public void updateCompany(Company company) {
    try {
      beginTransaction();

      logger.info("## SERVICE ## ---- START - update company");
      companyDao.createCompany(company);
      logger.info("## SERVICE ## ---- END - update company");

      commitTransaction();
    } catch (Exception e) {
      rollbackTransaction();
      throw e;
    }
  }

  private void beginTransaction() {
    logger.info("BEGIN TRANSACTION");
  }

  private void commitTransaction() {
    logger.info("COMMIT TRANSACTION");
  }

  private void rollbackTransaction() {
    logger.info("ROLLBACK TRANSACTION");
  }
}
