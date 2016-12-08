package com.example.guest.forumclass.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ListIterator;

//TODO: Deal with users already in chat

public class ChatActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = ChatActivity.class.getSimpleName();
    String mChatId;
    private DatabaseReference mUserReference;
    private ValueEventListener mValueEventListener;
    private ArrayList<String> mUserIds;
    private ArrayList<String> mUserNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        mChatId = intent.getStringExtra("chatId");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add_post) {
            mUserIds = new ArrayList<>();
            mUserNames = new ArrayList<>();
            mUserReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER_QUERY);
            mValueEventListener = mUserReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot mUserSnapshot : dataSnapshot.getChildren()) {
                        mUserIds.add(mUserSnapshot.getKey());
                        mUserNames.add(mUserSnapshot.child("username").getValue().toString());
                    }
                    DialogFragment newFragment = new UserDialogFragment().newInstance(mUserNames, mUserIds);
                    newFragment.show(getSupportFragmentManager(), "users");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
//        if(mAddMessageInput.getText().toString().length() > 0){
//            Message newMessage = new Message();
//            newMessage.setTitle("TODO: " + mAddMessageInput.getText().toString());
//            mMessageArray.add(newMessage);
//            if(mMessageTitles.get(0).equals("No TODOs yet! Why not add one?")){
//                mMessageTitles.remove(0);
//            }
//            mMessageTitles.add(newMessage.getTitle());
//            mAdapter.notifyDataSetChanged();
//
//            //for hiding the keyboard after button is pressed
//            InputMethodManager imm = (InputMethodManager)getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(mAddMessageInput.getWindowToken(), 0);
//            mAddMessageInput.setText("");
//        } else {
//            Toast.makeText(MessagesActivity.this, "Please enter a todo first!", Toast.LENGTH_LONG).show();
//        }
    }
}
