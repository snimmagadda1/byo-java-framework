package com.github.snimmagadda1.dao;

import java.util.logging.Logger;

import com.github.snimmagadda1.framework.annotation.Component;
import com.github.snimmagadda1.framework.annotation.Transactional;
import com.github.snimmagadda1.model.Company;

@Component
public class CompanyDaoImpl implements CompanyDao {

    private static final Logger logger = Logger.getLogger(CompanyDaoImpl.class.getName());

    @Override
    @Transactional
    public void createCompany(Company company) {
        logger.info("DAO:   START - create company");

        logger.info("DAO:   END - create company");
    }

    @Override
    @Transactional
    public void updateCompany(Company company) {
        logger.info("DAO:   START - update company");

        logger.info("DAO:   END - update company");
    }
    
}
