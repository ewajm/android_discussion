package com.example.guest.forumclass.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.Chat;
import com.example.guest.forumclass.models.Post;

/**
 * Created by Guest on 12/7/16.
 */
public class FirebaseChatViewHolder extends RecyclerView.ViewHolder{
    View mView;
    Context mContext;

    public FirebaseChatViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindChat(Chat chat) {
        TextView chatTextView = (TextView) mView.findViewById(R.id.titleTextView);
        chatTextView.setText(chat.getTitle());
    }
}
