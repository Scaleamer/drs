package com.scaleamer.service.syn.impl;

import com.scaleamer.dao.database.ReplyMapper;
import com.scaleamer.domain.Reply;
import com.scaleamer.domain.User;
import com.scaleamer.service.syn.ReplyService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public void insertReply(Reply reply) {
        int effectedNum = replyMapper.insertReply(reply);
        if(effectedNum<=0){
            throw new RuntimeException("插入reply失败");
        }
    }

    @Override
    public void deleteReply(Reply reply, User operator) {
        int effectedNum = replyMapper.deleteReply(reply, operator);
        if(effectedNum<=0){
            throw new RuntimeException("删除reply失败");
        }
    }

    @Override
    public Reply queryReplyById(int reply_id) {
        return replyMapper.queryReplyById(reply_id);
    }

    @Override
    public PageInfo<Reply> queryRepliesByUserId(int uid, int pageIndex, int pageSize) {
        return new PageInfo<>(replyMapper.queryRepliesByUserId(uid,new PageRowBounds(pageIndex,pageSize)));
    }

    @Override
    public PageInfo<Reply> queryRepliesByPostId(int post_id, int pageIndex, int pageSize) {
        return new PageInfo<>(replyMapper.queryRepliesByPostId(post_id,new PageRowBounds(pageIndex,pageSize)));
    }
}
