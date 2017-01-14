package com.abtotest.voiptest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.model.InboxMOdel;

import java.util.ArrayList;

/**
 * Created by root on 12/5/16.
 */

public class AdapterChatList extends ArrayAdapter<InboxMOdel> {


    Context context;
    ArrayList<InboxMOdel> chatMOdelArrayList;

    public AdapterChatList(Context context_1, ArrayList<InboxMOdel> arrayList) {
        super(context_1, R.layout.chat_list_items, arrayList);
        this.chatMOdelArrayList = arrayList;
        this.context = context_1;
    }

    @Override
    public void add(InboxMOdel object) {
        chatMOdelArrayList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return chatMOdelArrayList.size();
    }

    @Nullable
    @Override
    public InboxMOdel getItem(int position) {
        return chatMOdelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.chat_list_items, parent, false);
        TextView sendName = (TextView) rowView.findViewById(R.id.chat_message_sender);
        TextView msgBody = (TextView) rowView.findViewById(R.id.chat_message_body);
        sendName.setText(chatMOdelArrayList.get(position).getName());
        msgBody.setText(chatMOdelArrayList.get(position).getText());
        return rowView;
    }
}
