package com.example.guest.forumclass.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 12/6/16.
 */

@Parcel
public class Comment {
    String body;
    String userName;
    int likes;
    int dislikes;
    String postId;
    String id;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Comment() {
    }

    public Comment(String body, String userName) {
        this.body = body;
        this.userName = userName;
        this.likes = 0;
        this.dislikes = 0;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
