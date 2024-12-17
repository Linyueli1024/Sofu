package com.sofu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * ClassName:UserTaste
 * Author：Dee
 * Description：<用户兴趣类>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaste {

    /**
     * @Description: id
     */
    @Id
    private int id;

    /**
     * @Description: 用户id
     */
    @Column(name = "user_id")
    private int userId;

    /**
     * @Description: 类别id
     */
    @Column(name = "classify_id")
    private int classifyId;

    /**
     * @Description: 用户参与度
     */
    private int engagement;

}
