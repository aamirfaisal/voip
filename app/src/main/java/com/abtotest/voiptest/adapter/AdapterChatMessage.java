package com.abtotest.voiptest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.model.ChatMessageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 12/6/16.
 */

public class AdapterChatMessage extends ArrayAdapter<ChatMessageModel>
{
    private TextView chatText;
    private List<ChatMessageModel> chatMessageList = new ArrayList<ChatMessageModel>();
    private Context context;

    public AdapterChatMessage(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public void add(ChatMessageModel object) {
        chatMessageList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.chatMessageList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ChatMessageModel chatMessageModel = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(chatMessageModel.left){
            row= inflater.inflate(R.layout.left,parent,false);

        }
        else {
            row = inflater.inflate(R.layout.right, parent, false);
        }
       TextView chatMsg = (TextView)row.findViewById(R.id.msgr);
        chatMsg.setText(chatMessageModel.msg);
        return row;
    }
}
