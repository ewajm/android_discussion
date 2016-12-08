package com.example.guest.forumclass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.Chat;
import com.google.firebase.database.DatabaseReference;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddChatActivity extends BaseActivity {
    @Bind(R.id.titleEditText) EditText mTitleEditText;
    @Bind(R.id.publicSwitch) Switch mPublicSwitch;
    @Bind(R.id.startChatButton) Button mStartChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        ButterKnife.bind(this);

        mStartChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString().trim();
                boolean isPublic = !mPublicSwitch.isChecked();
                String chatType = isPublic ? "public" : "private";
                Chat chat = new Chat(isPublic, title);
                DatabaseReference pushRef = chatReference.push();
                String pushId = pushRef.getKey();
                chat.setPushId(pushId);
                pushRef.setValue(chat);
                chatReference.child(pushId).child("users").child(userId).setValue(true);
                userReference.child(userId).child("chats").child(chatType).child(pushId).setValue(true);
                Toast.makeText(AddChatActivity.this, "Chat created!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddChatActivity.this, ChatListActivity.class);
                startActivity(intent);
            }
        });
    }
}
