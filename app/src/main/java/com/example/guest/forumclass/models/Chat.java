package com.example.guest.forumclass.models;


import java.util.ArrayList;
import java.util.List;

public class Chat {
    List<String> users = new ArrayList<>();
    List<String> messages = new ArrayList<>();
    boolean isPublic = true;
    String pushId;
    String title;

    public Chat() {
    }

    public Chat(boolean isPublic, String title) {
        this.isPublic = isPublic;
        this.title = title;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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

    public List<String> getUsers() {
        return users;
    }

    public List<String> getMessages() {
        return messages;
    }
}
