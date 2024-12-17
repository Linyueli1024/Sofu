package com.sofu.dao;

import com.sofu.pojo.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: AnswerDao
 * Author:Bellion
 * Description：<Answer类 Dao>
 */
@Mapper
@Repository
public interface AnswerDao {

    /**
     * @Description: 新增回答
     */
    public void addAns(Answer answer);

    /**
     * @Description: 根据问题ID:qId 查询回答对象
     */
    public List<Answer> queryByqId(@Param("qId")int qId);

    /**
     * @Description: 新增回答点赞量
     */
    public void addLikeNum(@Param("ans_id")int ansId);

    /**
     * @Description: 根据回答ID:ansId 查询回答对象
     */
    public Answer queryByAnsId(Integer ansId);
    /**
     * @Description: 根据问题ID:qId 查询回答对象
     */
    public List<Answer> queryByUserId(@Param("userId")int userId);

}
