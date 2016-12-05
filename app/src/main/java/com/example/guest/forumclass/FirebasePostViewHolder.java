package com.example.guest.forumclass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Guest on 12/5/16.
 */
public class FirebasePostViewHolder extends RecyclerView.ViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebasePostViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        //itemView.setOnClickListener(this);
    }

    public void bindPost(Post post) {
        ImageView postImageView = (ImageView) mView.findViewById(R.id.postImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.titleTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);

        Picasso.with(mContext)
                .load(post.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(postImageView);

        nameTextView.setText(post.getTitle());
        categoryTextView.setText(post.getCategory());
    }

//    @Override
//    public void onClick(View view) {
//        final ArrayList<Post> posts = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
////        ref.addListenerForSingleValueEvent(new ValueEventListener() {
////
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                    posts.add(snapshot.getValue(Post.class));
////                }
////
////                int itemPosition = getLayoutPosition();
////
//////                Intent intent = new Intent(mContext, PostDetailActivity.class);
//////                intent.putExtra("position", itemPosition + "");
//////                intent.putExtra("posts", Parcels.wrap(posts));
////
////                //mContext.startActivity(intent);
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////            }
////        });
//    }

}
