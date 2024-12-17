package com.sofu.pojo.vo;

import com.sofu.pojo.Answer;
import com.sofu.pojo.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * ClassName:AnswerVo
 * Author:Dee
 * Description:<QuestioVo类>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVo {

    /**
     * @Description: 问题ID
     */
    @Column(name = "q_id")
    private int qId;

    /**
     * @Description: 问题标题
     */
    @Column(name = "q_title")
    private String qTitle;

    /**
     * @Description: 问题回答数
     */
    @Column(name = "answer_num")
    private int answerNum;

    /**
     * @Description: 同问数
     */
    @Column(name = "concern_num")
    private int concernNum;

    /**
     * @Description: 该问题对应的回答类
     */
    private Answer answer;

}
