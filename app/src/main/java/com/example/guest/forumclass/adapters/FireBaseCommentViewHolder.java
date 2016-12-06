package com.example.guest.forumclass.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.Comment;
import com.example.guest.forumclass.models.Post;
import com.squareup.picasso.Picasso;

/**
 * Created by Guest on 12/6/16.
 */
public class FireBaseCommentViewHolder extends RecyclerView.ViewHolder{
    View mView;
    Context mContext;
    Comment mComment;

    public FireBaseCommentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindPost(Comment comment) {
        TextView nameTextView = (TextView) mView.findViewById(R.id.usernameTextView);
        TextView commentBodyTextView  = (TextView) mView.findViewById(R.id.commentBodyTextView);
        mComment = comment;
        nameTextView.setText(comment.getUserName());
        commentBodyTextView.setText(comment.getBody());

    }
}
