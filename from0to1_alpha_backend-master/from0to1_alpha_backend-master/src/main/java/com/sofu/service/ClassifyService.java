package com.sofu.service;

import com.sofu.pojo.Classify;

import java.util.List;

/**
 * ClassName: ClassifyService
 * Author:Dee
 * Description：<Classify类 Service接口>
 */
public interface ClassifyService {

    /**
     * @Description: 获取分类列表
     */
    public List<Classify> selectClassify();

    /**
     * @Description: 分类关联问题数据++
     */
    public void addConnectNum(Integer classifyId);

}
