package com.example.guest.forumclass.models;


import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Chat {
    boolean publicChat;
    String pushId;
    String title;

    public Chat() {
    }

    public Chat(boolean isPublic, String title) {
        this.publicChat = isPublic;
        this.title = title;
    }


    public boolean getPublicChat() {
        return publicChat;
    }

    public void setPublicChat(boolean publicChat) {
        this.publicChat = publicChat;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChatTypeString(){
        return publicChat? "public":"private";
    }

}
