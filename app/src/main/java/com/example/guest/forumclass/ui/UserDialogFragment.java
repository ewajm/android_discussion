package com.example.guest.forumclass.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.guest.forumclass.Constants;
import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Ewa on 12/8/2016.
 */

public class UserDialogFragment extends DialogFragment {
    ArrayList<String> mSelected;
    ArrayList<String> mUserNames;
    ArrayList<String> mUserIds;
    DatabaseReference mUserReference;
    private ValueEventListener mValueEventListener;

    public static UserDialogFragment newInstance(ArrayList<String> userNames, ArrayList<String> userIds){
        UserDialogFragment dialogFragment = new UserDialogFragment();

        Bundle args = new Bundle();
        args.putStringArrayList("userNames", userNames);
        args.putStringArrayList("userIds", userIds);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mUserIds = getArguments().getStringArrayList("userIds");
        mUserNames = getArguments().getStringArrayList("userNames");
        String[] mNameArray = mUserNames.toArray(new String[mUserNames.size()]);
        builder.setTitle(R.string.choose_users)
                .setMultiChoiceItems(mNameArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        if(isChecked){
                            mSelected.add(mUserIds.get(which));
                        } else if(mSelected.contains(mUserIds.get(which))){
                            mSelected.remove(mUserIds.get(which));
                        }

                    }
                })
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

//    public void createUserList(){
//        mUserReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USER_QUERY);
//        mValueEventListener = mUserReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot mUserSnapshot : dataSnapshot.getChildren()) {
//                    mUserIds.add(mUserSnapshot.getKey());
//                    mUserNames.add(mUserSnapshot.child("name").getValue().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // mUserReference.removeEventListener(mValueEventListener);
    }
}
