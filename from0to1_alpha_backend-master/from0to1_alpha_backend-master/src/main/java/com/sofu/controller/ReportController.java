package com.sofu.controller;

import com.sofu.pojo.Report;
import com.sofu.service.ReportService;
import com.sofu.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
* ClassName: ReportController
* Author：Bellion
* Description：<举报模块>
*/
@RestController

public class ReportController {

    @Autowired
    ReportService reportService;

    /**
     * @Description: 举报
     */
    @PostMapping("/report")
    public JSONResult reportAnswer(@RequestBody Map<String, String> report) throws ParseException {
        //1.new report类
        Report report1 = new Report();
        report1.setType(Integer.parseInt(report.get("type")));
        report1.setTargetId(Integer.parseInt(report.get("targetid")));
        report1.setReason(Integer.parseInt(report.get("reason")));
        report1.setUserId(Integer.parseInt(report.get("userid")));
        report1.setOther(report.get("other"));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date reportDate = sf.parse(report.get("report_time"));
        report1.setReportTime(reportDate);
        //2.插入report表
        reportService.insertReport(report1);
        return JSONResult.successMsg("举报已提交");
    }

}
