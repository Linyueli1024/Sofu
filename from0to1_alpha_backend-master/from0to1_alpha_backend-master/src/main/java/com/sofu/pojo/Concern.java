package com.sofu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

/**
 * ClassName: Concern
 * Author:Bellion
 * Description：<同问关系表对应实体类>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Concern {

    /**
     * @Description: id主键
     */
    @Column(name = "id")
    private Integer Id;

    /**
     * @Description: 同问关联的用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * @Description: 同问关联的用户id
     */
    @Column(name = "q_id")
    private Integer qId;

    /**
     * @Description: 同问添加时间
     */
    @Column(name = "add_time")
    private Date addTime;

}
