package com.scaleamer.dao;

import com.scaleamer.domain.Post;
import com.scaleamer.domain.Reply;
import com.scaleamer.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ReplyMapper {
    int insertReply(@Param("reply") Reply reply);
    int deleteReply(@Param("reply") Reply reply, @Param("operator") User operator);
    //List<Reply> queryReplyByCondition(@Param("reply") Reply reply, RowBounds rowBounds);//不走id
    Reply queryReplyById(@Param("reply_id") int reply_id);
    List<Reply> queryRepliesByUserId(@Param("uid") int uid, RowBounds rowBounds);
    List<Reply> queryRepliesByPostId(@Param("post_id") int post_id, RowBounds rowBounds);
}
