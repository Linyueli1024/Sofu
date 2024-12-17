package com.sofu.service;

import com.sofu.pojo.Question;
import com.sofu.pojo.vo.AnswerVo;
import com.sofu.pojo.vo.QuestionVo;

import java.util.List;

/**
 * ClassName: QuestionService
 * Author:Bellion
 * Description：<Question类 Service接口>
 */
public interface QuestionService {

    public List<Question> selectByTag(String tag, int num);

    public void andAnswerNum(Integer qId);

    public void insertQuestion(Question question);

    /**
     * @Description: 获取问题详情页的问题、回答内容
     */
    public List<QuestionVo> selectQuestionVoList(int qId);

    /**
     * @Description: 获取问题详情页中回答者的信息
     */
    public List<AnswerVo> selectAnswerVoList(int ansId);

    /**
     * @Description: 搜索问题
     */
    public List<Question> queryQuestion(String keyword);

    /**
     * @Description: 多关键词搜索问题
     */
    public List<Question> queryByMultiKeywords(String keyword);

    /**
     * @Description: 获取全部问题(热度值从高到低)
     */
    public List<Question> selectAllQuestion();


    /**
     * @Description:根据qid查询问题对象
     */
    public Question selectQuestionByQid(Integer qId);

    /**
     * @Description: qId对应的ConcernNum属性++
     */
    public void addConcernNum(Integer qId);
}
