package com.sofu.dao;

import com.sofu.pojo.Question;
import com.sofu.pojo.User;
import com.sofu.pojo.vo.AnswerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

/**
 * ClassName: UserDao
 * Author:Dee
 * Description：<User类 Dao>
 */
@Mapper
@Repository
public interface UserDao {

    /**
     * @Description: 查询昵称是否存在
     */
    public User selectUserByNickname(String nickname);
    /**
     * @Description: 查询openId是否存在
     */
    public User selectUserByWxOpenId(String wxOpenId);

    /**
     * @Description: 保存用户基本信息
     */
    public void insertUser(@Param("nickname") String nickname, @Param("wx_openid")String wxOpenId, @Param("avatar") String avatar, @Param("state") int state, @Param("invitecode") String invitecode, @Param("invitedby") int invitedby, @Param("reg_time") Date regTime, @Param("login_time")Date loginTime);

    /**
     * @Description: 保存用户兴趣
     */
    public void insertUserTaste(@Param("user_id") int userId, @Param("classify_id") int classifyId, @Param("engagement") int engagement);

    /**
     * @Description: 编辑用户信息
     */
    public void updateUser(@Param("user_id") int userId, @Param("avatar") String avatar, @Param("nickname") String nickname);

    /**
     * @Description: 根据用户id查找用户
     */
    public User selectUserById(@Param("user_id") int userId);

    /**
     * @Description: 获取用户回答列表
     */
    public List<AnswerVo> selectAnswerVoList(@Param("user_id") int userId);

    /**
     * @Description: 获取用户提问列表
     */
    public List<Question> selectQuestion(@Param("user_id") int userId);

    /**
     * @Description: 问题重新编辑
     */
    public void updateQuestion(@Param("q_id") int qId, @Param("q_title") String qTitle, @Param("q_info") String qInfo, @Param("img_link") String imgLink);


    /**
     * @Description: 查询用户兴趣
     */
    public List<Integer> queryClassify(@Param("userId")String userId);

    /**
     * @Description: 查询邀请码是否存在
     */
    public User selectUserByInviteCode(@Param("invitecode") String inviteCode);

}
