package com.example.guest.forumclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = CategoryActivity.class.getSimpleName();
    private Query mCurrentPostReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mCategory = intent.getStringExtra("category");
        mCurrentPostReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY).orderByChild(Constants.FIREBASE_SINGLE_CATEGORY_QUERY).equalTo(mCategory);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Post, FirebasePostViewHolder>
                (Post.class, R.layout.reddit_list_item, FirebasePostViewHolder.class,
                        mCurrentPostReference) {

            @Override
            protected void populateViewHolder(FirebasePostViewHolder viewHolder,
                                              Post model, int position) {
                Log.d("CategoryACtivity", "populateViewHolder: " + model.getTitle());
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
