package com.sofu.pojo.vo;

import com.sofu.pojo.Question;
import com.sofu.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * ClassName:AnswerVo
 * Author:Dee
 * Description:<AnswerVo类>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVo {

    /**
     * @Description: 回答ID
     */
    @Column(name = "ans_id")
    private int ansId;

    /**
     * @Description: 从属的UserID
     */
    @Column(name = "user_id")
    private int userId;

    /**
     * @Description: 从属的回答ID
     */
    @Column(name = "q_id")
    private int qId;

    /**
     * @Description: 该回答对应的问题类
     */
    private Question question;

    /**
     * @Description: 该回答对应的用户类
     */
    private User user;

}
