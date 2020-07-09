package com.scaleamer.service.syn;

import com.scaleamer.domain.Post;
import com.scaleamer.domain.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PostService {

    void insertPost(Post post);
    void deletePost(Post post, User operator);
    PageInfo<Post> queryPostByCondition(Post post, int pageIndex, int pageSize);
    Post queryPostById(int post_id);
    PageInfo<Post> queryPostsByUserId(int uid, int pageIndex, int pageSize);
}
