package com.scaleamer.dao;

import com.scaleamer.domain.Post;
import com.scaleamer.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PostMapper {
    int insertPost(@Param("post") Post post);
    int deletePost(@Param("post") Post post, @Param("operator") User operator);
    List<Post> queryPostByCondition(@Param("post") Post post, RowBounds rowBounds);//不走id
    Post queryPostById(@Param("id") int post_id);
    List<Post> queryPostsByUserId(@Param("uid") int uid, RowBounds rowBounds);
}
