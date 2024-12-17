package com.sofu.service;

import com.sofu.pojo.Message;

import java.util.List;

/**
 * ClassName: MessageService
 * Author:Bellion
 * Description：<Message类 Service接口>
 */
public interface MessageService {

    /**
     * @Description: 查询User相关消息
     */
    public List<Message> queryById(Integer userId);

}
