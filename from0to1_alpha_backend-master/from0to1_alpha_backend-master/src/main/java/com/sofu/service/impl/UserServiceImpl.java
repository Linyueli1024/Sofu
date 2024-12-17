package com.sofu.service.impl;

import com.sofu.dao.ConcernDao;
import com.sofu.dao.UserDao;
import com.sofu.pojo.Concern;
import com.sofu.pojo.Question;
import com.sofu.pojo.User;
import com.sofu.pojo.UserTaste;
import com.sofu.pojo.vo.AnswerVo;
import com.sofu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ClassName: UserServiceImpl
 * Author:Dee
 * Description：<UserService实现类>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    ConcernDao concernDao;

    /**
     * @Description: 判断昵称是否存在
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryNicknameIsExist(String nickname) {
        User result = userDao.selectUserByNickname(nickname);
        return result == null ? false : true;
    }
    /**
     * @Description: 判断wx_openId是否存在
     */
    @Override
    public boolean queryOpenIdIsExist(String wxOpenId){
        User user = userDao.selectUserByWxOpenId(wxOpenId);
        return user == null ? false : true;
    }

    /**
     * @Description: 保存用户(用户注册)
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(User user) {
        String nickname = user.getNickname();
        String wxOpenId = user.getWxOpenId();
        String avatar = user.getAvatar();
        int state = user.getState();
        String inviteCode = user.getInviteCode();
        int invitedBy = user.getInvitedBy();
        Date regTime = user.getRegTime();
        userDao.insertUser(nickname, wxOpenId, avatar, state, inviteCode, invitedBy, regTime, regTime);
    }

    /**
     * @Description: 保存用户兴趣
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserTaste(UserTaste userTaste) {
        int userId = userTaste.getUserId();
        int classifyId = userTaste.getClassifyId();
        userDao.insertUserTaste(userId, classifyId, 0);
    }

    /**
     * @Description: 更新用户信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUser(int userId, String avatar, String nickname){
        userDao.updateUser(userId, avatar, nickname);
    }

    /**
     * @Description: 根据用户id查找用户
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User selectUserById(int userId) {
        User user = userDao.selectUserById(userId);
        return user;
    }

    /**
     * @Description: 获取用户回答列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<AnswerVo> selectAnswerVoList(int userId){
        List<AnswerVo> answerVos = userDao.selectAnswerVoList(userId);
        return answerVos;
    }

    /**
     * @Description: 获取用户提问列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Question> selectQuestion(int userId){
        List<Question> questions = userDao.selectQuestion(userId);
        return questions;
    }

    /**
     * @Description: 问题重新编辑
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void updateQuestion(int qId, String qTitle, String qInfo, String imgLink) {
        userDao.updateQuestion(qId, qTitle, qInfo, imgLink);
    }

    @Override
    public List<Integer> queryClassify(String userId) {
       return userDao.queryClassify(userId);
    }

    /**
     * @Description: 查询邀请码是否存在
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryInviteCodeIsExist(String inviteCode) {
        User result = userDao.selectUserByInviteCode(inviteCode);
        return result == null ? false : true;
    }

    /**
     * @Description: 根据邀请码查找用户
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User selectUserByInviteCode(String inviteCode) {
        User result = userDao.selectUserByInviteCode(inviteCode);
        return result;
    }

    /**
     * @Description: 根据userId查找同问关系
     */
    @Override
    public List<Concern> selectByUserId(Integer userId){
        List<Concern> concerns = concernDao.queryByUserId(userId);
        return concerns;
    }
    /**
     * @Description: 新增同问关系
     * @param concern
     * @return
     */
    @Override
    public void addConcern(Concern concern){
        concernDao.addConcern(concern);
    }


}
