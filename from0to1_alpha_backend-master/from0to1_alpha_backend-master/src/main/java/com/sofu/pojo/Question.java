package com.sofu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * ClassName:Question
 * Author:Dee
 * Description:<问题类>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    /**
     * @Description: 问题id
     */
    @Id
    @Column(name = "q_id")
    private int qId;

    /**
     * @Description: 问题标题
     */
    @Column(name = "q_title")
    private String qTitle;

    /**
     * @Description: 图片链接
     */
    @Column(name = "img_link")
    private String imgLink;

    /**
     * @Description: 问题回答数
     */
    @Column(name = "answer_num")
    private int answerNum;

    /**
     * @Description: 分类id
     */
    @Column(name = "classify_id")
    private String classifyId;

    /**
     * @Description:  发布时间
     */
    @Column(name = "post_time")
    private Date postTime;

    /**
     * @Description: 同问数
     */
    @Column(name = "concern_num")
    private int concernNum;

    /**
     * @Description:  编辑时间
     */
    @Column(name = "edit_time")
    private Date editTime;

    /**
     * @Description: 问题内容
     */
    @Column(name = "q_info")
    private String qInfo;

    /**
     * @Description: 用户id
     */
    @Column(name = "user_id")
    private int userId;

    /**
     * @Description: 热度值
     */
    @Column(name = "hotdegree")
    private Integer hotDegree;

}
