package com.example.guest.forumclass.utils;

import android.content.Context;
import android.content.Intent;


import com.example.guest.forumclass.ui.AddChatActivity;
import com.example.guest.forumclass.ui.AddPostActivity;
import com.example.guest.forumclass.ui.CategoriesActivity;
import com.example.guest.forumclass.ui.ChatListActivity;
import com.example.guest.forumclass.ui.LoginActivity;
import com.example.guest.forumclass.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Universal {
    public static void logout(Context mContext) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
    }

    public static void newPost(Context mContext) {
        Intent intent = new Intent(mContext, AddPostActivity.class);
        mContext.startActivity(intent);
    }

    public static void goHome(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    public static void goCategories(Context mContext) {
        Intent intent = new Intent(mContext, CategoriesActivity.class);
        mContext.startActivity(intent);
    }

    public static void goChat(Context mContext) {
        Intent intent = new Intent(mContext, ChatListActivity.class);
        mContext.startActivity(intent);
    }

    public static void newChat(Context mContext) {
        Intent intent = new Intent(mContext, AddChatActivity.class);
        mContext.startActivity(intent);
    }

}
