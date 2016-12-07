package com.example.guest.forumclass.models;

import org.parceler.Parcel;

@Parcel
public class Message {
    String body;
    String userName;
    String chatId;
    long timeStamp;
    String id;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message() {
    }

    public Message(String body, String userName, String chatId) {
        this.body = body;
        this.userName = userName;
        this.chatId = chatId;
        this.timeStamp = System.currentTimeMillis();
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

    public long getTimeStamp() {
        return timeStamp;
    }

}