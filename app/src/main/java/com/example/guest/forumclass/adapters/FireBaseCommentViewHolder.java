package com.example.guest.forumclass.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.Comment;
import com.example.guest.forumclass.models.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 12/6/16.
 */
public class FireBaseCommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;
    Comment mComment;
    @Bind(R.id.likeText) TextView mLikeText;
    @Bind(R.id.dislikeText) TextView mDislikeText;
    @Bind(R.id.thumbUp) ImageView mThumbsUp;
    @Bind(R.id.thumbDown) ImageView mThumbsDown;

    public FireBaseCommentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);

        mThumbsUp.setOnClickListener(this);
        mThumbsDown.setOnClickListener(this);
    }

    public void bindPost(Comment comment) {
        TextView nameTextView = (TextView) mView.findViewById(R.id.usernameTextView);
        TextView commentBodyTextView  = (TextView) mView.findViewById(R.id.commentBodyTextView);
        mComment = comment;
        nameTextView.setText(comment.getUserName());
        commentBodyTextView.setText(comment.getBody());
        mLikeText.setText("Likes: " + mComment.getLikes());
        mDislikeText.setText("Dislikes: " + mComment.getDislikes());

    }

    public void onClick(View v) {
        if(v == mThumbsDown) {
            int thisDislikeGuy = mComment.getDislikes();
            mComment.setDislikes(++thisDislikeGuy);
            mDislikeText.setText("Dislikes: " + thisDislikeGuy);
        } else if(v == mThumbsUp) {
            int thisLikeGuy = mComment.getLikes();
            mComment.setLikes(++thisLikeGuy);
            mLikeText.setText("Likes: " + thisLikeGuy);
        }
        DatabaseReference thisRefGuy = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY).child(mComment.getPostId()).child(Constants.FIREBASE_COMMENTS_QUERY).child(mComment.getId());
        thisRefGuy.setValue(mComment);

    }
}
