package com.scaleamer.service.syn.impl;

import com.scaleamer.dao.PostMapper;
import com.scaleamer.domain.Post;
import com.scaleamer.domain.User;
import com.scaleamer.service.syn.PostService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public void insertPost(Post post) {
        int effectNum = postMapper.insertPost(post);
        if (effectNum <= 0) {
            throw new RuntimeException("Dao:插入Post失败");
        }
    }

    @Override
    public void deletePost(Post post, User operator) {
        int effectNum = postMapper.deletePost(post, operator);
        if (effectNum <= 0) {
            throw new RuntimeException("Dao:删除Post失败");
        }
    }

    @Override
    public PageInfo<Post> queryPostByCondition(Post post, int pageIndex, int pageSize) {
        return new PageInfo<>(postMapper.queryPostByCondition(post, new PageRowBounds(pageIndex, pageSize)));
    }

    @Override
    public Post queryPostById(int post_id) {
        return postMapper.queryPostById(post_id);
    }

    @Override
    public PageInfo<Post> queryPostsByUserId(int uid, int pageIndex, int pageSize) {
        return new PageInfo<>(postMapper.queryPostsByUserId(uid, new PageRowBounds(pageIndex, pageSize)));
    }
}
