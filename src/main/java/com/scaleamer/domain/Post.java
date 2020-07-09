package com.scaleamer.domain;

import com.github.pagehelper.PageHelper;

import java.util.Date;

public class Post {
    private Integer post_id;
    private String publisher_name;
    private User publisher;
    private Date publish_date;
    private String content;

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", publisher_name='" + publisher_name + '\'' +
                ", publisher=" + publisher +
                ", publisher_date=" + publish_date +
                ", content='" + content + '\'' +
                '}';
    }
}
