package com.sofu.service.impl;

import com.sofu.dao.QuestionDao;
import com.sofu.pojo.Question;
import com.sofu.pojo.vo.AnswerVo;
import com.sofu.pojo.vo.QuestionVo;
import com.sofu.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: QuestionServiceImpl
 * Author:Bellion
 * Description：<QuestionService实现类>
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;
    @Override
    public List<Question> selectByTag(String tag, int num) {
        List<Question> questions = questionDao.selectByTag(tag, num);
        return questions;
    }

    @Override
    public void andAnswerNum(Integer qId) {
        questionDao.addAnswerNum(qId);
    }

    /**
     * @Description: 获取问题详情页的问题、回答内容
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<QuestionVo> selectQuestionVoList(int qId) {
        List<QuestionVo> questions = questionDao.selectQuestionVoList(qId);
        return questions;
    }

    /**
     * @Description: 获取问题详情页中回答者的信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<AnswerVo> selectAnswerVoList(int ansId) {
        List<AnswerVo> answerVos = questionDao.selectAnswerVoList(ansId);
        return answerVos;
    }

    /**
     * @Description: 新增问题
     */
    @Override
    public void insertQuestion(Question question) {
        questionDao.insertQuestion(question);
    }

    /**
     * @Description: 搜索问题
     */
    @Override
    public List<Question> queryQuestion(String keyword) {
        List<Question> questions = questionDao.queryQuestion(keyword);
        return questions;
    }

    /**
     * @Description: 多关键词搜索问题
     */
    public List<Question> queryByMultiKeywords(String keyword) {
        List<Question> questions = questionDao.queryByMultiKeywords(keyword);
        return questions;
    }

    /**
     * @Description: 获取全部问题(热度值从高到低)
     */
    @Override
    public List<Question> selectAllQuestion() {
        List<Question> questions = questionDao.selectAllQuestion();
        return questions;
    }

    /**
     * @Description:根据qid查询问题对象
     */
    @Override
    public Question selectQuestionByQid(Integer qId){
        return questionDao.selectQuestionByQid(qId);
    }
    /**
     * @Description: qId对应的ConcernNum属性++
     */
    @Override
    public void addConcernNum(Integer qId){
        questionDao.addConcernNum(qId);
    }

}
