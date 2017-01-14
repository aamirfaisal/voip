package com.abtotest.voiptest.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.abtotest.voiptest.R;

import org.abtollc.sdk.AbtoPhone;

import static com.abtotest.voiptest.Ui.ChatList.contactNumber;

public class AddMsgToSend extends Activity {
    EditText setNumberTo, setSms;
    Button btnSentMSg;
    ImageView imgCall;
    private AbtoPhone abtoPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_msg_to_send);
        btnSentMSg = (Button) findViewById(R.id.btnSentText);
        setNumberTo = (EditText) findViewById(R.id.input_NumberText);
        imgCall = (ImageView) findViewById(R.id.phCall);
        setSms = (EditText) findViewById(R.id.msgInputText);

        btnSentMSg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactNumber = setNumberTo.getText().toString();
                String msgVLaue = setSms.getText().toString();
                Intent back = new Intent(AddMsgToSend.this, ChatActivity.class);
                back.putExtra("msg", msgVLaue);
                startActivity(back);
                finish();
            }
        });
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCall(true);
            }
        });
    }

    private synchronized void startAV(boolean incoming) {
        Intent intent = new Intent(this, ScreenAV.class);
        intent.putExtra("incoming", incoming);
        intent.putExtra(ScreenAV.CALL_ID, abtoPhone.getActiveCallId());
        intent.putExtra(AbtoPhone.REMOTE_CONTACT, contactNumber);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void startCall(boolean bVideo) {
        //Get phone number to dial
        String sipNumber = setNumberTo
                .getText().toString();
        if (TextUtils.isEmpty(sipNumber)) return;


        if (!sipNumber.contains("sip:")) sipNumber = "sip:" + sipNumber;


        // Start new call
        try {
            if (bVideo) abtoPhone.startVideoCall(sipNumber, abtoPhone.getCurrentAccountId());
            else abtoPhone.startCall(sipNumber, abtoPhone.getCurrentAccountId());

            //if(!sipNumber.contains("@") ){
            //    remoteContact = String.format("<%1$s@%2$s>", sipNumber, domain);
            //}else{
            //    remoteContact = String.format("<%1$s>", sipNumber);
            //}
            contactNumber = sipNumber;

            startAV(false);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
