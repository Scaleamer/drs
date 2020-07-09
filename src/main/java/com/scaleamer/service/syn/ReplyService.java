package com.scaleamer.service.syn;


import com.scaleamer.domain.Reply;
import com.scaleamer.domain.User;
import com.github.pagehelper.PageInfo;

public interface ReplyService {
    void insertReply(Reply reply);
    void deleteReply(Reply reply, User operator);
    //List<Reply> queryReplyByCondition(Reply reply, int pageIndex, int pageSize);
    Reply queryReplyById(int reply_id);
    PageInfo<Reply> queryRepliesByUserId(int uid, int pageIndex, int pageSize);
    PageInfo<Reply> queryRepliesByPostId(int post_id, int pageIndex, int pageSize);
}
