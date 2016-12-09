package com.example.guest.forumclass.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
    ArrayList<String> mSelected = new ArrayList<>();
    ArrayList<String> mUserNames;
    ArrayList<String> mUserIds;
    DatabaseReference mUserReference;
    private ValueEventListener mValueEventListener;

    public interface UserDialogListener{
        public void onDialogPositiveClick(ArrayList<String> selected, DialogFragment dialog);
    }

    UserDialogListener mListener;

    public static UserDialogFragment newInstance(ArrayList<String> userNames, ArrayList<String> userIds){
        UserDialogFragment dialogFragment = new UserDialogFragment();

        Bundle args = new Bundle();
        args.putStringArrayList("userNames", userNames);
        args.putStringArrayList("userIds", userIds);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host context implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (UserDialogListener) context;
        } catch (ClassCastException e) {
            // The context doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
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
                        mListener.onDialogPositiveClick(mSelected, UserDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
