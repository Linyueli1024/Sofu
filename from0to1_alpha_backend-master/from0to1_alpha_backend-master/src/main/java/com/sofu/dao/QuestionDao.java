package com.sofu.dao;

import com.sofu.pojo.Question;
import com.sofu.pojo.vo.AnswerVo;
import com.sofu.pojo.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: QuestionDao
 * Author:Bellion
 * Description：<question类 DAO>
 */
@Mapper
@Repository
public interface QuestionDao {

    /**
     * @Description: 根据类别查询指定条数的问题对象
     * @param tag:分类
     * @param num:查询条数
     */
    public List<Question> selectByTag( String tag, int num);

    /**
     * @Description: qId对应的answernum属性++
     */
    public void addAnswerNum(Integer qId);


    /**
     * @Description: 新增问题
     */
    public void insertQuestion(Question question);

    /**
     * @Description: 获取问题详情页的问题、回答内容
     */
    public List<QuestionVo> selectQuestionVoList(@Param("q_id") int qId);

    /**
     * @Description: 获取问题详情页中回答者的信息
     */
    public List<AnswerVo> selectAnswerVoList(@Param("ans_id") int ansId);

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
