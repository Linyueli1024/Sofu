package com.sofu.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
* ClassName: Report
* Author：Bellion
* Description：<举报类>
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report implements Serializable {

    /**
     * @Description: 举报ID
     */
    @Id
    @Column(name = "r_id")
    private Integer rId;

    /**
     * @Description: 被举报对象的类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * @Description: 被举报内容的ID
     */
    @Column(name = "targetid")
    private Integer targetId;

    /**
     * @Description: 理由选择
     */
    @Column(name = "reason")
    private Integer reason;

    /**
     * @Description: 补充信息
     */
    @Column(name = "other")
    private String other;

    /**
     * @Description: 举报处理状态
     */
    @Column(name = "state")
    private Integer state;

    /**
     * @Description: 举报者ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * @Description: 举报时间
     */
    @Column(name = "report_time")
    private Date reportTime;

    /**
     * @Description:  最新处理结果反馈时间
     */
    @Column(name = "finish_time")
    private Date finishTime;

    /**
     * @Description:  处理的管理员ID
     */
    @Column(name = "adamin_id")
    private Integer adaminId;

}
