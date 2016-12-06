package com.example.guest.forumclass.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.adapters.FireBaseCommentViewHolder;
import com.example.guest.forumclass.models.Comment;
import com.example.guest.forumclass.models.Post;
import com.example.guest.forumclass.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostDetailActivity extends AppCompatActivity {
    private static final String TAG = PostDetailActivity.class.getSimpleName();
    @Bind(R.id.postTitle)
    TextView mPostTitle;
    @Bind(R.id.postCategory) TextView mPostCategory;
    @Bind(R.id.postBody) TextView mPostBody;
    @Bind(R.id.newComment)
    EditText mNewComment;
    @Bind(R.id.newCommentUser) EditText mNewCommentUser;
    @Bind(R.id.addCommentButton)
    Button mAddCommentButton;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Comment, FireBaseCommentViewHolder> mFirebaseAdapter;
    private DatabaseReference mCommentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Post post = Parcels.unwrap(intent.getParcelableExtra("post"));


        mPostTitle.setText(post.getTitle());
        mPostCategory.setText(post.getCategory());
        mPostBody.setText(post.getBody());

        mCommentReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY).child(post.getId()).child("comments");
        setUpFirebaseAdapter();

        Log.i(TAG, "onCreate: " + post.getId());

        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNewCommentUser.getVisibility() == View.VISIBLE) {
                    String commentHere = mNewComment.getText().toString();
                    String userName = mNewCommentUser.getText().toString();
                    mNewComment.setText("");
                    mNewCommentUser.setText("");
                    Comment comment = new Comment(commentHere, userName);
                    mCommentReference.getRef().push().setValue(comment);
                    mFirebaseAdapter.notifyDataSetChanged();
                    mNewCommentUser.setVisibility(View.GONE);
                    mNewComment.setVisibility(View.GONE);
                    mAddCommentButton.setText("Add New Comment");
                } else {
                    mNewCommentUser.setVisibility(View.VISIBLE);
                    mNewComment.setVisibility(View.VISIBLE);
                    mAddCommentButton.setText("Add");
                }
            }
        });
    }
    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Comment, FireBaseCommentViewHolder>
                (Comment.class, R.layout.comment_list_items, FireBaseCommentViewHolder.class,
                        mCommentReference) {

            @Override
            protected void populateViewHolder(FireBaseCommentViewHolder viewHolder,
                                              Comment model, int position) {
                viewHolder.bindPost(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
