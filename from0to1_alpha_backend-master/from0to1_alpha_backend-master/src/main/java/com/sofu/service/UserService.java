package com.sofu.service;

import com.sofu.pojo.Concern;
import com.sofu.pojo.Question;
import com.sofu.pojo.User;
import com.sofu.pojo.UserTaste;
import com.sofu.pojo.vo.AnswerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: UserService
 * Author:Dee
 * Description：<User类 Service接口>
 */
public interface UserService {

    /**
     * @Description: 判断昵称是否存在
     */
    public boolean queryNicknameIsExist(String username);
    /**
     * @Description: 判断wx_openId是否存在
     */
    public boolean queryOpenIdIsExist(String wxOpenId);


    /**
     * @Description: 保存用户(用户注册)
     */
    public void saveUser(User user);

    /**
     * @Description: 保存用户兴趣
     */
    public void saveUserTaste(UserTaste userTaste);

    /**
     * @Description: 更新用户信息
     */
    public void updateUser(int userId, String avatar, String nickname);

    /**
     * @Description: 根据用户id查找用户
     */
    public User selectUserById(int userId);

    /**
     * @Description: 获取用户回答列表
     */
    public List<AnswerVo> selectAnswerVoList(int userId);

    /**
     * @Description: 获取用户提问列表
     */
    public List<Question> selectQuestion(int userId);

    /**
     * @Description: 问题重新编辑
     */
    public void updateQuestion(int qId, String qTitle, String qInfo, String imgLink);

    /**
     * @Description: 查询用户兴趣
     */
    public List<Integer> queryClassify(@Param("userId")String userId);
    /**
     * @Description: 查询邀请码是否存在
     */
    public boolean queryInviteCodeIsExist(String inviteCode);

    /**
     * @Description: 根据邀请码查找用户
     */
    public User selectUserByInviteCode(String inviteCode);
    /**
     * @Description: 根据userId查找同问关系
     */
    public List<Concern> selectByUserId(Integer userId);
    /**
     * @Description: 新增同问关系
     * @param concern
     * @return
     */
    public void addConcern(Concern concern);

}
