package com.sofu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
* ClassName: Answer
* Author：Bellion
* Description：<回答类>
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

        /**
         * @Description: 回答ID
         */
        @Id
        @Column(name = "ans_Id")
        private int ansId;

        /**
         * @Description: 从属的UserID
         */
        @Column(name = "user_Id")
        private int userId;

        /**
         * @Description: 从属的回答ID
         */
        @Column(name = "q_id")
        private int qId;

        /**
         * @Description: 被该评论艾特的评论ID
         */
        @Column(name = "cue")
        private int cue;

        /**
         * @Description: 回答文本信息
         */
        @Column(name = "ans_info")
        private String ansInfo;

        /**
         * @Description: 图片链接
         */
        @Column(name = "img_link")
        private String imgLink;

        /**
         * @Description: 喜欢数
         */
        @Column(name = "like_num")
        private int likeNum;

        /**
         * @Description: 被提及数
         */
        @Column(name = "ref_num")
        private int refNum;

        /**
         * @Description:  发布时间
         */
        @Column(name = "post_time")
        private Date postTime;

}
