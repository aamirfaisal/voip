package com.abtotest.voiptest.Ui;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.adapter.AdapterChatMessage;
import com.abtotest.voiptest.db.dbChat;
import com.abtotest.voiptest.fragments.fragmentActivity;
import com.abtotest.voiptest.model.ChatMessageModel;

import org.abtollc.sdk.AbtoApplication;
import org.abtollc.sdk.AbtoPhone;
import org.abtollc.sdk.OnTextMessageListener;

import java.util.ArrayList;

public class ChatActivity extends Activity implements View.OnClickListener {
    EditText inputMsg;
    Button sendMsg;
    ChatList chatList;
    static public AbtoPhone abtoPhone;
    ListView messgaeList;
    String msgInputValue;
    ArrayList<String> msgListData;
    private AdapterChatMessage adapterChatMessage;
    fragmentActivity fragmentActivityObj;
    dbChat dbChatObj;
    ArrayList<ChatMessageModel> conversationList;
    String senderNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        msgListData = new ArrayList<>();
        sendMsg = (Button) findViewById(R.id.btnSend);
        dbChatObj = new dbChat(getApplicationContext());
        messgaeList = (ListView) findViewById(R.id.msgViewList);
        fragmentActivityObj = new fragmentActivity();
        adapterChatMessage = new AdapterChatMessage(this, R.layout.right);
        messgaeList.setAdapter(adapterChatMessage);
        conversationList = dbChatObj.getConversationList(ChatList.contactNumber);
        for (int i = 0; i < conversationList.size(); i++) {
            adapterChatMessage.add(conversationList.get(i));
        }

        inputMsg = (EditText) findViewById(R.id.msgInput);
        inputMsg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMsg();
                }
                return false;
            }
        });
        abtoPhone = ((AbtoApplication) getApplication()).getAbtoPhone();

        abtoPhone.setInMessageListener(new OnTextMessageListener() {
            @Override
            public void onTextMessageReceived(String msg, String sender, String reciver) {

                adapterChatMessage.add(new ChatMessageModel(msg, true));
                senderNames = sender;
                dbChatObj.insertInboxValues(sender, msg, "1");

            }
        });


        messgaeList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messgaeList.setAdapter(adapterChatMessage);
        //to scroll the list view to bottom on data change
        adapterChatMessage.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                messgaeList.setSelection(adapterChatMessage.getCount() - 1);
            }
        });
        for (int i = 0; i < conversationList.size(); i++) {
            adapterChatMessage.add(conversationList.get(i));

        }

        sendMsg.setOnClickListener(this);
        String msg = getIntent().getStringExtra("msg");
        if (msg != null) {
            inputMsg.setText(msg);
            sendChatMsg();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                sendChatMsg();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void checkEmptyFields() {
        msgInputValue = inputMsg.getText().toString();
        if (msgInputValue.equals("")) {
            sendMsg.setEnabled(false);
        } else {
            sendMsg.setEnabled(true);
        }
    }

    private boolean sendChatMsg() {
        try {

            msgInputValue = inputMsg.getText().toString();
            if (msgInputValue.matches("")) {
                Toast.makeText(this, "please enter text", Toast.LENGTH_SHORT).show();
                return false;
            }
            abtoPhone.sendTextMessage(abtoPhone.getCurrentAccountId(), msgInputValue, ChatList.contactNumber);
            msgListData.add(msgInputValue);
            adapterChatMessage.add(new ChatMessageModel(msgInputValue, false));
            dbChatObj.insertInboxValues(":" + ChatList.contactNumber + "@", msgInputValue, "0");
            Toast.makeText(this, "message sent successfully", Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        inputMsg.setText("");
        return true;
    }
}

