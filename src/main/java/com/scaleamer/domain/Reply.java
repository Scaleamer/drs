package com.scaleamer.domain;

import java.util.Date;

public class Reply {
    private Integer reply_id;
    private String publisher_name;
    private Integer publisher_id;
    private Post post;
    private String content;
    private Date publish_date;
    private User publisher;

    public Integer getReply_id() {
        return reply_id;
    }

    public void setReply_id(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public Integer getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(Integer publisher_id) {
        this.publisher_id = publisher_id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "reply_id=" + reply_id +
                ", publisher_name='" + publisher_name + '\'' +
                ", publisher_id=" + publisher_id +
                ", post=" + post +
                ", content='" + content + '\'' +
                ", publish_date=" + publish_date +
                ", publisher=" + publisher +
                '}';
    }
}
