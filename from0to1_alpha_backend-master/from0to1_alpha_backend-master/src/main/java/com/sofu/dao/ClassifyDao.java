package com.sofu.dao;

import com.sofu.pojo.Classify;
import com.sofu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ClassifyDao
 * Author:Dee
 * Description：<Classify类Dao>
 */
@Mapper
@Repository
public interface ClassifyDao {

    /**
     * @Description: 获取分类列表
     */
    public List<Classify> selectClassify();

    /**
     * @Description: 分类关联问题数据++
     */
    public void addConnectNum(Integer classifyId);

}
