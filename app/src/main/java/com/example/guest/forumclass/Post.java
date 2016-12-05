package com.example.guest.forumclass;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Guest on 12/5/16.
 */
public class Post {
    String title;
    String body;
    String category;
    String imageUrl;
    Long timestamp;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Post() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Post(String title, String body, String category, String imageUrl) {
        this.title = title;
        this.body = body;
        this.category = category;
        this.imageUrl = imageUrl;
        this.timestamp = System.currentTimeMillis();
    }
}
