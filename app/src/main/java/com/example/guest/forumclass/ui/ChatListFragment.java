package com.example.guest.forumclass.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.Chat;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {
    Query chatQuery;
    boolean isPublic;
    String uid;


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
            chatQuery = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHAT_QUERY).orderByChild("public").equalTo(true);
        } else {
            chatQuery = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHAT_QUERY).orderByChild("users").equalTo(true, uid);
        }

        return view;
    }

}
