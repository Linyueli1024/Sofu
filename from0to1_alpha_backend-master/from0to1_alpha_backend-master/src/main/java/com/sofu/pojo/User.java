package com.sofu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * ClassName:User
 * Author：Dee
 * Description：<用户类>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * @Description: 用户id
     */
    @Id
    @Column(name = "user_id")
    private int userId;

    /**
     * @Description: 用户昵称
     */
    private String nickname;

    /**
     * @Description: 微信登录需要的id
     */
    @Column(name = "wx_openid")
    private String wxOpenId;

    /**
     * @Description: 用户头像
     */
    private String avatar;

    /**
     * @Description: 用户状态
     */
    private int state;

    /**
     * @Description: 邀请码
     */
    @Column(name = "invitecode")
    private String inviteCode;

    /**
     * @Description: 邀请人用户id
     */
    @Column(name = "invitedby")
    private int invitedBy;

    /**
     * @Description: 注册时间
     */
    @Column(name = "reg_time")
    private Date regTime;

    /**
     * @Description: 登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

}
