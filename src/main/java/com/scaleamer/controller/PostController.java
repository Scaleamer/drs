package com.scaleamer.controller;

import com.scaleamer.domain.Post;
import com.scaleamer.domain.User;
import com.scaleamer.service.syn.PostService;
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
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addPost(@RequestParam(value = "content", required = true) String content) {
        Post post = new Post();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        System.out.println(user);
//        System.out.println(content);
        post.setPublish_date(new Date());
        post.setPublisher_name(user.getUsername());
        post.setPublisher(user);
        post.setContent(content);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            postService.insertPost(post);
            post.setPublisher(null);
            resultMap.put("success", true);
            resultMap.put("data", post);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> deletePost(@RequestParam(value = "post_id", required = true) int post_id) {
        Post post = new Post();
        post.setPost_id(post_id);
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        Map<String, Object> resultMap = new HashMap<>();
        try {
            postService.deletePost(post, user);
            resultMap.put("success", true);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/queryPost", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> queryPost(@RequestParam(value = "content", required = false) String content,
                                          @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Post post = new Post();
        post.setContent(content);
        System.out.println(post);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<Post> pageInfo = postService.queryPostByCondition(post, pageIndex, pageSize);
//            PageResult<Post> pageResult = PageResultUtil.getPageResult(posts);
            resultMap.put("success", true);
            resultMap.put("data", pageInfo);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/queryPostById", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> queryPost(@RequestParam(value = "post_id") int post_id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Post post = postService.queryPostById(post_id);
            resultMap.put("success", true);
            resultMap.put("data", post);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/queryPostByUId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> queryPostByUId(@RequestParam(value = "user_id") int user_id,
                                               @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PageInfo<Post> pageInfo = postService.queryPostsByUserId(user_id, pageIndex, pageSize);
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
