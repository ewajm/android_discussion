package com.example.guest.forumclass.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guest.forumclass.Constants;
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

public class CategoriesActivity extends AppCompatActivity {
    List<String> mCategories = new ArrayList<>();
    private DatabaseReference mCategoryReference;
    @Bind(R.id.listView)
    ListView mListView;
    @Bind(R.id.addCategoryEditText)
    EditText mAddCategoryEditText;
    @Bind(R.id.addCategoryButton)
    Button mAddCategoryButton;
    private ValueEventListener mCategoryReferenceListener;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mCategoryReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CATEGORY_QUERY);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategoriesActivity.this, android.R.layout.simple_list_item_1, mCategories);
        mListView.setAdapter(adapter);

        mCategoryReferenceListener = mCategoryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCategories.clear();
                for(DataSnapshot categorySnapshot : dataSnapshot.getChildren()){
                    mCategories.add(categorySnapshot.getValue().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategoriesActivity.this, android.R.layout.simple_list_item_1, mCategories);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
                    addToSharedPreferences(mCategories.get(position));
                    startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAddCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCategory = mAddCategoryEditText.getText().toString().trim().toLowerCase();
                if(newCategory.length() > 0 && !mCategories.contains(newCategory)){
                    mCategoryReference.push().setValue(newCategory);
                    mAddCategoryEditText.setText("");
                } else {
                    Toast.makeText(CategoriesActivity.this, "Sorry, one of two things went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.FIREBASE_SINGLE_CATEGORY_QUERY, location).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCategoryReference.removeEventListener(mCategoryReferenceListener);
    }


}
