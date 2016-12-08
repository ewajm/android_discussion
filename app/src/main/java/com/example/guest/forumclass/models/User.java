package com.example.guest.forumclass.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class User {
    String name;
    String pushId;

    public User() {
    }

    public User(String name, String pushId) {
        this.name = name;
        this.pushId = pushId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
