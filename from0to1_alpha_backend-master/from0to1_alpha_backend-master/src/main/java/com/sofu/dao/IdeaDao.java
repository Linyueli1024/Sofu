package com.sofu.dao;

import com.sofu.pojo.Idea;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ClassName: IdeaDao
 * Author:Bellion
 * Description：<Idea类 DAO>
 */
@Mapper
@Repository
public interface IdeaDao {

    /**
     * @Description: 新增想法
     */
    public void addIdea(Idea idea);

}
