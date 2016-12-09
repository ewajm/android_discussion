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

public class PostDetailActivity extends BaseActivity {
    private static final String TAG = PostDetailActivity.class.getSimpleName();
    @Bind(R.id.postTitle)
    TextView mPostTitle;
    @Bind(R.id.postCategory) TextView mPostCategory;
    @Bind(R.id.postAuthor) TextView mPostAuthor;
    @Bind(R.id.postBody) TextView mPostBody;
    @Bind(R.id.newComment)
    EditText mNewComment;
    @Bind(R.id.addCommentButton)
    Button mAddCommentButton;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Comment, FireBaseCommentViewHolder> mFirebaseAdapter;
    private Query mThisPostCommentReference;
    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Post");
        Intent intent = getIntent();
        mPost = Parcels.unwrap(intent.getParcelableExtra("post"));


        mPostTitle.setText(mPost.getTitle());
        mPostCategory.setText(mPost.getCategory());
        mPostBody.setText(mPost.getBody());
        mPostAuthor.setText(mPost.getAuthor());
        mThisPostCommentReference = commentReference.orderByChild("postId").equalTo(mPost.getId());
        setUpFirebaseAdapter();

        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String commentHere = mNewComment.getText().toString();
                    mNewComment.setText("");
                    Comment comment = new Comment(commentHere, userName);
                    comment.setPostId(mPost.getId());
                    DatabaseReference pushRef = commentReference.getRef().push();
                    comment.setId(pushRef.getKey());
                    pushRef.setValue(comment);
                    mFirebaseAdapter.notifyDataSetChanged();
                    mAddCommentButton.setText("Add New Comment");
            }
        });
    }
    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Comment, FireBaseCommentViewHolder>
                (Comment.class, R.layout.comment_list_items, FireBaseCommentViewHolder.class,
                        mThisPostCommentReference) {

            @Override
            protected void populateViewHolder(FireBaseCommentViewHolder viewHolder,
                                              Comment model, int position) {
                viewHolder.bindPost(model);
            }
        };
        mRecyclerView.setAdapter(mFirebaseAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       // layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
       // mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
