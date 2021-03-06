package com.example.guest.forumclass.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.utils.Universal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {

    public DatabaseReference postReference;
    public DatabaseReference commentReference;
    public DatabaseReference chatReference;
    public DatabaseReference userReference;

    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public String userName;
    public String userId;
    Universal mUniversal;
    Context mContext;
    public DatabaseReference messageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY);
        commentReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_COMMENTS_QUERY);
        chatReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHAT_QUERY);
        userReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER_QUERY);
        messageReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_MESSAGE_QUERY);
        mContext = this;
        //Toolbar toolbar = new Toolbar(mContext);
        //toolbar.setTitle(mContext.getClass().getSimpleName());
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    userName = user.getDisplayName();
                    userId = user.getUid();
                }
            }
        };
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout) {
            mUniversal.logout(mContext);
            return true;
        }
        if(id == R.id.action_add_post) {
            mUniversal.newPost(mContext);
            return true;
        }
        if(id == R.id.action_main) {
            mUniversal.goHome(mContext);
            return true;
        }
        if(id == R.id.action_categories) {
            mUniversal.goCategories(mContext);
            return true;
        }
        if(id == R.id.action_chat) {
            mUniversal.goChat(mContext);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
