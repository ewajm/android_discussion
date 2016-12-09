package com.example.guest.forumclass.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.guest.forumclass.R;
import com.example.guest.forumclass.models.Message;
import com.example.guest.forumclass.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

/**
 * Created by Ewa on 12/8/2016.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    View mView;
    Context mContext;
    Message mMessage;

    public MessageViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }
    public void bindMessage(Message message) {
        TextView messageTextView = (TextView) mView.findViewById(R.id.messageTextView);
        TextView messageInfoView = (TextView) mView.findViewById(R.id.messageInfoTextView);
        RelativeLayout messageRelativeLayout = (RelativeLayout) mView.findViewById(R.id.messageRelativeLayout);
        mMessage = message;
        messageTextView.setText(message.getBody());
        Date messageDate = new Date(mMessage.getTimeStamp());
        String messageInfo = mMessage.getUserName() + "@" + messageDate.toString();
        messageInfoView.setText(messageInfo);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)messageTextView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams)messageInfoView.getLayoutParams();
        if(mMessage.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            messageRelativeLayout.setGravity(Gravity.RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            messageTextView.setLayoutParams(layoutParams);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            messageInfoView.setLayoutParams(layoutParams2);
            messageTextView.setBackgroundColor(Color.parseColor("#cfd8dc"));
        } else{
            messageTextView.setBackgroundColor(Color.parseColor("#90A4AE"));
            messageRelativeLayout.setGravity(Gravity.LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            messageTextView.setLayoutParams(layoutParams);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            messageInfoView.setLayoutParams(layoutParams2);
        }
    }

}
