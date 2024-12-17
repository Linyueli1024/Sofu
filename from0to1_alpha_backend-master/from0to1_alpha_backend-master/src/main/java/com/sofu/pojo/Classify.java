package com.sofu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * ClassName:Classify
 * Author：Dee
 * Description：<分类类>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classify {

    /**
     * @Description: 分类id
     */
    @Id
    @Column(name = "classify_id")
    private int classifyId;

    /**
     * @Description: 分类名称
     */
    private String name;

    /**
     * @Description: 所拥有资源数量
     */
    @Column(name = "connect_num")
    private int connectNum;

    /**
     * @Description: 类型，二进制位存储
     * (01)表示想法
     * (10)表示问题
     * (11)表示想法和问题
     * (00)禁用
     */
    private int type;

    @Column(name = "img_link")
    private String imgLink;

}
