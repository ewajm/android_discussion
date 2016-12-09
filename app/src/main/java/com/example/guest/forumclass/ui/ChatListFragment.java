package com.example.guest.forumclass.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.adapters.ChatViewHolder;
import com.example.guest.forumclass.adapters.PrivateChatListAdapter;
import com.example.guest.forumclass.models.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {
    Query chatQuery;
    boolean isPublic;
    String mUid;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private PrivateChatListAdapter mChatAdapter;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private ArrayList<Chat> mChats = new ArrayList<>();
    private ValueEventListener mChatEventListener;
    private DatabaseReference mPrivateChatRef;
    private ValueEventListener mValueEventListener;


    public static ChatListFragment newInstance(int position){
        ChatListFragment chatListFragment = new ChatListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        chatListFragment.setArguments(args);
        return chatListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPublic = getArguments().getInt("position") == 0;
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat_list, container, false);
        ButterKnife.bind(this, view);

        mValueEventListener = FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (isPublic) {
                    if (dataSnapshot.hasChild("chats")) {
                        chatQuery = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHAT_QUERY).orderByChild("publicChat").equalTo(true);
                        setUpFirebaseAdapter();
                    }
                } else if (dataSnapshot.hasChild("users")) {
                    if (dataSnapshot.child("users").hasChild(mUid) && dataSnapshot.child("users").child(mUid).child("chats").hasChild("private")) {
                        setUpRecyclerAdapter();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void setUpRecyclerAdapter() {
        mPrivateChatRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER_QUERY).child(mUid).child("chats").child("private");
        mChatAdapter = new PrivateChatListAdapter(mChats, getContext());
        mRecyclerView.setAdapter(mChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mChatEventListener = mPrivateChatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mChats.clear();

                for (DataSnapshot repliesSnapshot : dataSnapshot.getChildren()) {
                    chatQuery = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHAT_QUERY);
                    //--------------
                    Query queryRef = chatQuery.orderByKey().equalTo(repliesSnapshot.getKey());
                    //--------------
                    queryRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            mChats.add(chat);
                            mChatAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Chat, ChatViewHolder>
                (Chat.class, R.layout.chat_list_item, ChatViewHolder.class,
                        chatQuery) {

            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder,
                                              Chat model, int position) {
                viewHolder.bindChat(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPrivateChatRef != null){
            mPrivateChatRef.removeEventListener(mChatEventListener);
        }
        FirebaseDatabase.getInstance().getReference().removeEventListener(mValueEventListener);
        if(mFirebaseAdapter != null){
            mFirebaseAdapter.cleanup();
        }
    }
}
