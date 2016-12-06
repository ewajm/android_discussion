package com.example.guest.forumclass.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.forumclass.models.Post;
import com.example.guest.forumclass.ui.PostDetailActivity;
import com.example.guest.forumclass.R;
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
        mPost = post;
        if(!post.getImageUrl().isEmpty()){
            Picasso.with(mContext)
                    .load(post.getImageUrl())
                    .into(postImageView);

            nameTextView.setText(post.getTitle());
            categoryTextView.setText(post.getCategory());
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, PostDetailActivity.class);
        intent.putExtra("post", Parcels.wrap(mPost));
        mContext.startActivity(intent);
    }

}
