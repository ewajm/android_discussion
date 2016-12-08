package com.example.guest.forumclass.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.adapters.FirebaseChatViewHolder;
import com.example.guest.forumclass.models.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {
    Query chatQuery;
    boolean isPublic;
    String uid;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;


    public static ChatListFragment newInstance(int position, String uid){
        ChatListFragment chatListFragment = new ChatListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("uid", uid);
        chatListFragment.setArguments(args);
        return chatListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPublic = getArguments().getInt("position") == 0;
        uid = getArguments().getString("uid");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);

        if(isPublic){
            chatQuery = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHAT_QUERY).orderByChild("publicChat").equalTo(true);
        } else {
            chatQuery = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHAT_QUERY).orderByChild("users").equalTo(uid);
        }
        setUpFirebaseAdapter();
        return view;
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Chat, FirebaseChatViewHolder>
                (Chat.class, R.layout.chat_list_item, FirebaseChatViewHolder.class,
                        chatQuery) {

            @Override
            protected void populateViewHolder(FirebaseChatViewHolder viewHolder,
                                              Chat model, int position) {
                viewHolder.bindChat(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

}
