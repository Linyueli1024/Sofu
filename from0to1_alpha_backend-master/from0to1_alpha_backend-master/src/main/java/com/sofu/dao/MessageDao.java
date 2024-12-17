package com.sofu.dao;

import com.sofu.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: MessageDao
 * Author:Bellion
 * Description：<消息类DAO>
 */
@Mapper
@Repository
public interface MessageDao {

    /**
     * @Description: 查询User相关消息
     */
    public List<Message> queryById(Integer userId);

}
