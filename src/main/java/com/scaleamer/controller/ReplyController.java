package com.scaleamer.controller;

import com.scaleamer.domain.Post;
import com.scaleamer.domain.Reply;
import com.scaleamer.domain.User;
import com.scaleamer.service.syn.ReplyService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @RequestMapping(value = "/addReply", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addReply(@RequestParam(value = "content", required = true) String content,
                                         @RequestParam(value = "post_id", required = true)int post_id) {
        
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        
        Post post = new Post();
        post.setPost_id(post_id);
        
        Reply reply = new Reply();
        reply.setPost(post);
        reply.setPublish_date(new Date());
        reply.setPublisher_name(user.getUsername());
        reply.setPublisher(user);
        reply.setContent(content);
        
        Map<String, Object> resultMap = new HashMap<>();
        try {
            replyService.insertReply(reply);
            reply.setPublisher(null);
            resultMap.put("success", true);
            resultMap.put("data", reply);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/deleteReply", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> deleteReply(@RequestParam(value = "reply_id", required = true) int reply_id) {
        Reply reply = new Reply();
        reply.setReply_id(reply_id);
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Map<String, Object> resultMap = new HashMap<>();
        try {
            replyService.deleteReply(reply, user);
            resultMap.put("success", true);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

//    @RequestMapping(value = "/queryReply", method = RequestMethod.GET)
//    @ResponseBody
//    private Map<String, Object> queryReply(@RequestParam(value = "content", required = false) String content,
//                                          @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
//                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
//        Reply reply = new Reply();
//        reply.setContent(content);
//        System.out.println(reply);
//        Map<String, Object> resultMap = new HashMap<>();
//        try {
//            PageInfo<Reply> pageInfo = replyService.queryReplyByCondition(reply, pageIndex, pageSize);
////            PageResult<Reply> pageResult = PageResultUtil.getPageResult(replys);
//            resultMap.put("success", true);
//            resultMap.put("data", pageInfo);
//            return resultMap;
//        } catch (Exception e) {
//            resultMap.put("success", false);
//            resultMap.put("errMsg", e.getMessage());
//            return resultMap;
//        }
//    }

    @RequestMapping(value = "/queryReplyById", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> queryReply(@RequestParam(value = "reply_id") int reply_id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Reply reply = replyService.queryReplyById(reply_id);
            resultMap.put("success", true);
            resultMap.put("data", reply);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/queryReplyByUId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> queryReplyByUId(@RequestParam(value = "user_id") int user_id,
                                               @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<Reply> pageInfo = replyService.queryRepliesByUserId(user_id, pageIndex, pageSize);
            resultMap.put("success", true);
            resultMap.put("data", pageInfo);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/queryReplyByPostId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> queryReplyByPostId(@RequestParam(value = "post_id") int post_id,
                                                @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<Reply> pageInfo = replyService.queryRepliesByPostId(post_id, pageIndex, pageSize);
            resultMap.put("success", true);
            resultMap.put("data", pageInfo);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }
}
