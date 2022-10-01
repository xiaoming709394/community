package com.xiaoming.community.dao.elasticsearch;

import com.xiaoming.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 帖子es仓储类
 *
 * @author 赵明城
 * @date 2022/9/30
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {

}
