package com.github.snimmagadda1.service;

import com.github.snimmagadda1.dao.CompanyDao;
import com.github.snimmagadda1.framework.annotation.Autowired;
import com.github.snimmagadda1.framework.annotation.Component;
import com.github.snimmagadda1.framework.annotation.Transactional;
import com.github.snimmagadda1.model.Company;
import java.util.logging.Logger;

@Component
public class CompanyServiceImpl implements CompanyService {

  private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class.getName());

  private final CompanyDao companyDao;

  @Autowired
  public CompanyServiceImpl(CompanyDao companyDao) {
    this.companyDao = companyDao;
  }

  @Override
  public void createCompany(Company company) {
    logger.info("SERVICE:   START - create company");
    // companyDao.createCompany(company);
    createWithTransaction(company);
    logger.info("SERVICE:   END - create company");
  }

  @Transactional
  public void createWithTransaction(Company company) {
    logger.info("SERVICE:   START - createWithTransaction");
    companyDao.createCompany(company);
    logger.info("SERVICE:   END - createWithTransaction");
  }

  @Override
  @Transactional
  public void updateCompany(Company company) {
    logger.info("SERVICE:   START - update company");
    companyDao.updateCompany(company);
    logger.info("SERVICE:   END - update company");
  }
}
