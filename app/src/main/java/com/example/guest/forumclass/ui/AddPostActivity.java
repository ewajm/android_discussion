package com.example.guest.forumclass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.models.Post;
import com.example.guest.forumclass.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddPostActivity extends AppCompatActivity {
    @Bind(R.id.submitButton) Button mSubmitButton;
    @Bind(R.id.titleEditText)
    EditText mTitleEditText;
    @Bind(R.id.jerkSpinner)
    Spinner mJerkSpinner;
    @Bind(R.id.bodyEditText) EditText mBodyEditText;
    @Bind(R.id.imageLinkEditText) EditText mImageLinkEditText;
    Post mPost;
    String mCurrentCategory;

    private DatabaseReference mPostReference;
    private DatabaseReference mCategoryReference;
    private ValueEventListener mCategoryReferenceListener;
    private List<String> mCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mCurrentCategory = intent.getStringExtra("category");
        mCategoryReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CATEGORY_QUERY);
        mCategoryReferenceListener = mCategoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCategories.clear();
                for(DataSnapshot categorySnapshot : dataSnapshot.getChildren()){
                    mCategories.add(categorySnapshot.getValue().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddPostActivity.this, android.R.layout.simple_spinner_dropdown_item, mCategories);
                mJerkSpinner.setAdapter(adapter);
                if(mCurrentCategory != null){
                    mJerkSpinner.setSelection(mCategories.indexOf(mCurrentCategory));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString().trim();
                String body = mBodyEditText.getText().toString().trim();
                String category = mJerkSpinner.getSelectedItem().toString();
                String imageUrl = mImageLinkEditText.getText().toString().trim();
                mPost = new Post(title, body, category, imageUrl);
                mPostReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY);

                DatabaseReference pushRef= mPostReference.push();
                String pushId = pushRef.getKey();
                mPost.setId(pushId);
                pushRef.setValue(mPost);
                Toast.makeText(AddPostActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddPostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
