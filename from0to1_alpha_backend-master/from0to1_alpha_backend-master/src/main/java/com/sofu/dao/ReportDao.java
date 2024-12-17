package com.sofu.dao;

import com.sofu.pojo.Report;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* ClassName: ReportDao
* Author：Bellion
* Description：<Report类Dao>
*/
@Mapper
@Repository
public interface ReportDao {

    /**
     * @Description: 新增举报类
     */
    public void insertReport(Report report);

}
