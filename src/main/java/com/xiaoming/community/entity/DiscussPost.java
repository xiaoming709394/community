package com.xiaoming.community.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 帖子信息实体
 *
 * @author 赵明城
 * @date 2022/8/6
 */
@Data
@Document(indexName = "discusspost", shards = 6, replicas = 3)
public class DiscussPost {

    /**
     * id
     */
    @Id
    private int id;

    /**
     * 用户id
     */
    @Field(type = FieldType.Integer)
    private int userId;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;

    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;

    /**
     * 帖子类型
     */
    @Field(type = FieldType.Integer)
    private int type;

    /**
     * 帖子状态
     */
    @Field(type = FieldType.Integer)
    private int status;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * 评论数量
     */
    @Field(type = FieldType.Integer)
    private int commentCount;

    /**
     * 分数
     */
    @Field(type = FieldType.Double)
    private double score;
}
