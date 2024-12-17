package com.sofu.dao;/**
 * ClassName: ConcernDao
 * Author:Bellion
 * Description：<同问实体类Dao>
 */

import com.sofu.pojo.Answer;
import com.sofu.pojo.Concern;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ConcernDao
 * @Author zhangzhixi
 * @Description
 * @Date 2023/5/28 10:07
 * @Version 1.0
 */
@Mapper
@Repository
public interface ConcernDao {
    /**
     * @Description: 根据问题ID:qId 查询同问关系对象
     * @param userId
     * @return
     */
    public List<Concern> queryByUserId(@Param("userId") Integer userId);

    /**
     * @Description: 新增同问关系
     * @param concern
     * @return
     */
    public void addConcern(Concern concern);

}
