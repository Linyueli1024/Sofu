package com.sofu.service.impl;

import com.sofu.dao.MessageDao;
import com.sofu.pojo.Message;
import com.sofu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: MessageServiceImpl
 * Author:Bellion
 * Description：<Message service 实现类>
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDao messageDao;
    @Override
    public List<Message> queryById(Integer userId) {
        return messageDao.queryById(userId);
    }

}
