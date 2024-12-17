package com.sofu.service.impl;

import com.sofu.dao.IdeaDao;
import com.sofu.pojo.Idea;
import com.sofu.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

/**
 * ClassName: IdeaServiceImpl
 * Author:Bellion
 * Description：<IdeaService实现类>
 */
@Service
public class IdeaServiceImpl implements IdeaService {

    @Autowired
    IdeaDao ideaDao;
    @Override
    public void addIdea(Idea idea) {
        ideaDao.addIdea(idea);
    }

}
