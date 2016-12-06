package com.example.guest.forumclass.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 12/6/16.
 */

@Parcel
public class Comment {
    String body;
    String userName;

    public Comment() {
    }

    public Comment(String body, String userName) {
        this.body = body;
        this.userName = userName;
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

}
