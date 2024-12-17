package com.sofu.service.impl;

import com.sofu.dao.ClassifyDao;
import com.sofu.pojo.Classify;
import com.sofu.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: ClassifyServiceImpl
 * Author:Dee
 * Description：<ClassifyService实现类>
 */
@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    ClassifyDao classifyDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Classify> selectClassify() {
        List<Classify> classify = classifyDao.selectClassify();
        return classify;
    }

    @Override
    public void addConnectNum(Integer classifyId) {
        classifyDao.addConnectNum(classifyId);
    }
}
