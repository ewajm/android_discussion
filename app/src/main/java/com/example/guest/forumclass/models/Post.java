package com.example.guest.forumclass.models;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Guest on 12/5/16.
 */
@Parcel
public class Post {
    String title;
    String author;
    String body;
    String category;
    String imageUrl;
    Long timestamp;
    String id;
    int numComments;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Post(String title, String author, String body, String category, String imageUrl) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.category = category;
        this.imageUrl = imageUrl;
        this.timestamp = System.currentTimeMillis();
    }

}
