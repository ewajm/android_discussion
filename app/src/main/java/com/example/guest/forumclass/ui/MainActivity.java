package com.example.guest.forumclass.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.models.Post;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.adapters.FirebasePostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private Query mCurrentPostReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.button)
    Button mCategoryActivityButton;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCategory = mSharedPreferences.getString(Constants.FIREBASE_SINGLE_CATEGORY_QUERY, null);
        if(mCategory == null){
            mCurrentPostReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY).limitToLast(3).orderByChild("timestamp");

        } else {
            mCurrentPostReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_POST_QUERY).orderByChild(Constants.FIREBASE_SINGLE_CATEGORY_QUERY).equalTo(mCategory);

        }
        setUpFirebaseAdapter();
        mCategoryActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Post, FirebasePostViewHolder>
                (Post.class, R.layout.reddit_list_item, FirebasePostViewHolder.class,
                        mCurrentPostReference) {

            @Override
            protected void populateViewHolder(FirebasePostViewHolder viewHolder,
                                              Post model, int position) {

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
        mEditor = mSharedPreferences.edit();
        mEditor.remove(Constants.FIREBASE_SINGLE_CATEGORY_QUERY).apply();
        mFirebaseAdapter.cleanup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_add_post:
                Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
            startActivity(intent);
            break;
            default:
                break;
        }

        return true;
    }
}
