package com.example.guest.forumclass.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.Chat;
import com.example.guest.forumclass.models.Post;
import com.example.guest.forumclass.ui.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Guest on 12/7/16.
 */
public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;
    Chat mChat;

    public ChatViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        mView.setOnClickListener(this);
    }

    public void bindChat(Chat chat) {
        TextView chatTextView = (TextView) mView.findViewById(R.id.titleTextView);
        chatTextView.setText(chat.getTitle());
        mChat = chat;
    }

    @Override
    public void onClick(View view) {
        if(mChat.getPublicChat()){
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHAT_QUERY).child(mChat.getPushId()).child("users").child(uid).setValue(true);
            FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER_QUERY).child(uid).child("chats").child(mChat.getChatTypeString()).child(mChat.getPushId()).setValue(true);
        }
        Intent intent = new Intent(mContext, ChatActivity.class);
        intent.putExtra("chatId", mChat.getPushId());
        mContext.startActivity(intent);
    }
}
