package com.sofu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * ClassName: Idea
 * Author:Bellion
 * Description：<想说 实体类>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Idea {
    /**
     * @Description: 想说ID
     */
    @Id
    @Column(name = "idea_id")
    private Integer ideaId;

    /**
     * @Description: 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * @Description: 想说帖子标题
     */
    @Column(name = "idea_title")
    private String ideaTitle;
    /**
     * @Description: 想说帖子内容
     */
    @Column(name="idea_info")
    private String ideaInfo;
    /**
     * @Description: 想说帖子图片链接
     */
    @Column(name="img_link")
    private String imgLink;

    /**
     * @Description: 评论数量
     */
    @Column(name="comment_num")
    private Integer commentNum;

    /**
     * @Description: 分类ID
     */
    @Column(name="classify_id")
    private String classifyId;

    /**
     * @Description: 喜欢数量
     */
    @Column(name="like_num")
    private Integer likeNum;

    /**
     * @Description: 收藏数量
     */
    @Column(name="collect_num")
    private Integer collectNum;

    /**
     * @Description: 发布时间
     */
    @Column(name="post_time")
    private Date postTime;

    /**
     * @Description: 编辑时间
     */
    @Column(name="edit_time")
    private Date editTime;

}
