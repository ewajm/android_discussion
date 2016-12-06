package com.example.guest.forumclass;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddPostActivity extends AppCompatActivity {
    @Bind(R.id.submitButton) Button mSubmitButton;
    @Bind(R.id.titleEditText)
    EditText mTitleEditText;
    @Bind(R.id.categoryEditText) EditText mCategoryEditText;
    @Bind(R.id.bodyEditText) EditText mBodyEditText;
    @Bind(R.id.imageLinkEditText) EditText mImageLinkEditText;
    Post mPost;

    private DatabaseReference mPostReference;
    private DatabaseReference mCategoryReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ButterKnife.bind(this);


    mSubmitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = mTitleEditText.getText().toString().trim();
            String body = mTitleEditText.getText().toString().trim();
            String category = mCategoryEditText.getText().toString().trim().toLowerCase();
            String imageUrl = mImageLinkEditText.getText().toString().trim();
            mPost = new Post(title, body, category, imageUrl);
            mPostReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY);
            mCategoryReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CATEGORY_QUERY);

            mPostReference.push().setValue(mPost);
            Toast.makeText(AddPostActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
            startActivity(intent);
        }
    });

    }
}
