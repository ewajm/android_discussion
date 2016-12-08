package com.example.guest.forumclass.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.Chat;

import java.util.ArrayList;

/**
 * Created by Ewa on 12/8/2016.
 */

public class PrivateChatListAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private ArrayList<Chat> mChats = new ArrayList<>();
    private Context mContext;

    public PrivateChatListAdapter(ArrayList<Chat> chats, Context context) {
        mChats = chats;
        mContext = context;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        ChatViewHolder viewHolder = new ChatViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.bindChat(mChats.get(position));
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }
}
