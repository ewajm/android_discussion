package com.example.guest.forumclass.adapters;

import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.models.Post;
import com.example.guest.forumclass.ui.PostDetailActivity;
import com.example.guest.forumclass.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

/**
 * Created by Guest on 12/5/16.
 */
public class FirebasePostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;
    Post mPost;
    public ValueEventListener mValueEventListener;
    private DatabaseReference mCommentRef;

    public FirebasePostViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindPost(Post post) {
        ImageView postImageView = (ImageView) mView.findViewById(R.id.postImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.titleTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        final TextView commentTextView = (TextView) mView.findViewById(R.id.commentTextView) ;
        mCommentRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY).child(post.getId()).child(Constants.FIREBASE_COMMENTS_QUERY);
        mValueEventListener = mCommentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentTextView.setText("Comments (" + dataSnapshot.getChildrenCount() + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mPost = post;
        if(!post.getImageUrl().isEmpty()){
            Picasso.with(mContext)
                    .load(post.getImageUrl())
                    .into(postImageView);
        }
        nameTextView.setText(post.getTitle());
        categoryTextView.setText(post.getCategory());
        commentTextView.setText("Comments (");
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, PostDetailActivity.class);
        intent.putExtra("post", Parcels.wrap(mPost));
        mCommentRef.removeEventListener(mValueEventListener);
        mContext.startActivity(intent);
    }

}
