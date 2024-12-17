package com.sofu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * ClassName: Message
 * Author：Bellion
 * Description：<Message实体类>
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * @Description: 消息ID
     */
    @Id
    @Column(name = "m_id")
    private Integer mId;

    /**
     * @Description: 类型(官方消息|回答被@|评论被@)
     */
    @Column(name = "type")
    private Integer type;

    /**
     * @Description: 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * @Description: 补充信息
     */
    @Column(name = "other")
    private String other;


    /**
     * @Description: 被读状态（0未读，1已读）：默认0
     */
    @Column(name = "readstate")
    private Integer readstate;

    /**
     * @Description: 消息内容
     */
    @Column(name = "info")
    private Integer info;

}
