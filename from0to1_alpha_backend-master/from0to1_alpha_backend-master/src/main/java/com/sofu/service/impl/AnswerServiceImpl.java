package com.sofu.service.impl;

import com.sofu.dao.AnswerDao;
import com.sofu.pojo.Answer;
import com.sofu.service.AnswerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: AnswerServiceImpl
 * Author:Bellion
 * Description：<AnswerService实现类>
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerDao answerDao;

    /**
     * @Description: 新增回答
     */
    @Override
    public void addAns(Answer answer) {
        answerDao.addAns(answer);
    }

    /**
     * @Description: 根据问题ID:qId 查询回答对象
     */
    @Override
    public List<Answer> queryByqId(Integer qId) {
        return answerDao.queryByqId(qId);
    }

    /**
     * @Description: 新增回答点赞量
     */
    @Override
    public void addLikeNum(int ansId) {
        answerDao.addLikeNum(ansId);
    }
    /**
     * @Description: 根据回答ID:ansId 查询回答对象
     */
    @Override
    public Answer queryByAnsId(Integer ansId){
        return answerDao.queryByAnsId(ansId);
    }

    /**
     * @Description: 根据问题ID:qId 查询回答对象
     */
    @Override
    public List<Answer> queryByUserId(@Param("userId")int userId){
        return answerDao.queryByUserId(userId);
    }

}
