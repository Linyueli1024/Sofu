package com.sofu.service.impl;

import com.sofu.dao.ReportDao;
import com.sofu.pojo.Report;
import com.sofu.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* ClassName: ReportServiceImpl
* Author：Bellion
* Description：<描述> 
*/
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportDao reportDao;

    @Override
    public void insertReport(Report report) {
        reportDao.insertReport(report);
    }

}
