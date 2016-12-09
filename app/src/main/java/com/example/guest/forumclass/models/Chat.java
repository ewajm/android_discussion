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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (publicChat != chat.publicChat) return false;
        if (pushId != null ? !pushId.equals(chat.pushId) : chat.pushId != null) return false;
        return title != null ? title.equals(chat.title) : chat.title == null;

    }

    @Override
    public int hashCode() {
        int result = (publicChat ? 1 : 0);
        result = 31 * result + (pushId != null ? pushId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
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
