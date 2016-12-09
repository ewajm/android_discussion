package com.example.guest.forumclass.ui;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.adapters.MessageViewHolder;
import com.example.guest.forumclass.models.Chat;
import com.example.guest.forumclass.models.Message;
import com.example.guest.forumclass.models.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

//TODO: Deal with users already in chat
//TODO: notifications when user has been added/invited to chat

public class ChatActivity extends BaseActivity implements View.OnClickListener, UserDialogFragment.UserDialogListener{
    private static final String TAG = ChatActivity.class.getSimpleName();
    Chat mChat;
    MenuItem mAddChat;
    private DatabaseReference mUserReference;
    private ValueEventListener mValueEventListener;
    private ArrayList<String> mUserIds;
    private ArrayList<String> mUserNames;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.newMessageButton) Button mNewMessageButton;
    @Bind(R.id.newMessageInput) EditText mNewMessageInput;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    Query mCurrentMessageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mUserReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER_QUERY);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        mChat = Parcels.unwrap(intent.getParcelableExtra("chat"));
        mUserIds = new ArrayList<>();
        mUserNames = new ArrayList<>();
        mNewMessageButton.setOnClickListener(this);
        mValueEventListener = mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserIds.clear();
                mUserNames.clear();
                for (DataSnapshot mUserSnapshot : dataSnapshot.getChildren()) {
                    if(!mUserIds.contains(mUserSnapshot.getKey())){
                        mUserIds.add(mUserSnapshot.getKey());
                    }
                    if(!mUserNames.contains(mUserSnapshot.child("username").getValue().toString())){
                        mUserNames.add(mUserSnapshot.child("username").getValue().toString());
                    }
                }
                invalidateOptionsMenu();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mCurrentMessageReference = messageReference.orderByChild("chatId").equalTo(mChat.getPushId());
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>
                (Message.class, R.layout.message_list_item, MessageViewHolder.class,
                        mCurrentMessageReference) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder,
                                              Message model, int position) {

                viewHolder.bindMessage(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mAddChat = menu.findItem(R.id.action_add_post);
        if(mUserIds.size() == 0){
            mAddChat.setEnabled(false);
        } else {
            if(!mAddChat.isEnabled()){
                mAddChat.setEnabled(true);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add_post) {
            DialogFragment newFragment = new UserDialogFragment().newInstance(mUserNames, mUserIds);
            newFragment.show(getSupportFragmentManager(), "users");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(mNewMessageInput.getText().toString().length() > 0){
            String messageText = mNewMessageInput.getText().toString().trim();
            Message newMessage = new Message(messageText, userName, userId, mChat.getPushId());
            DatabaseReference pushRef = messageReference.push();
            String messagePushId = pushRef.getKey();
            newMessage.setId(messagePushId);
            pushRef.setValue(newMessage);
            chatReference.child(mChat.getPushId()).child(Constants.FIREBASE_MESSAGE_QUERY).child(messagePushId).setValue(true);
            userReference.child(userId).child(Constants.FIREBASE_MESSAGE_QUERY).child(messagePushId).setValue(true);

            //for hiding the keyboard after button is pressed
            InputMethodManager imm = (InputMethodManager)getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mNewMessageInput.getWindowToken(), 0);
            mNewMessageInput.setText("");
        }
    }

    @Override
    public void onDialogPositiveClick(ArrayList<String> selected, DialogFragment dialog) {
        for (String userId: selected) {
            mUserReference.child(userId).child("chats").child(mChat.getChatTypeString()).child(mChat.getPushId()).setValue(true);
            chatReference.child(mChat.getPushId()).child("users").child(userId).setValue(true);
        }
        dialog.dismiss();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i(TAG, "onStop: chat activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
        mUserReference.removeEventListener(mValueEventListener);
    }
}
