package com.github.snimmagadda1.service;

import java.util.logging.Logger;

import com.github.snimmagadda1.dao.CompanyDao;
import com.github.snimmagadda1.model.Company;

public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class.getName());

    private final CompanyDao companyDao;

    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public void createCompany(Company company) {
        logger.info("## SERVICE ## ---- START - create company");
        companyDao.createCompany(company);
        logger.info("## SERVICE ## ---- END - create company");
    }

    @Override
    public void updateCompany(Company company) {
        logger.info("## SERVICE ## ---- START - update company");
        companyDao.createCompany(company);
        logger.info("## SERVICE ## ---- END - update company");
    }
}
