package com.sofu.service;

import com.sofu.dao.AnswerDao;
import com.sofu.pojo.Answer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: AnswerService
 * Author:Bellion
 * Description：<Answer类 Service接口>
 */
public interface AnswerService {

    /**
     * @Description: 新增回答
     */
    public void addAns(Answer answer);

    /**
     * @Description: 根据问题ID:qId 查询回答对象
     */
    public List<Answer> queryByqId(Integer qId);

    /**
     * @Description: 新增回答点赞量
     */
    public void addLikeNum(int ansId);
    /**
     * @Description: 根据回答ID:ansId 查询回答对象
     */
    public Answer queryByAnsId(Integer ansId);
    /**
     * @Description: 根据问题ID:qId 查询回答对象
     */
    public List<Answer> queryByUserId(@Param("userId")int userId);

}
