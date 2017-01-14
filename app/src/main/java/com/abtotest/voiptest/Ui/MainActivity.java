package com.abtotest.voiptest.Ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abtotest.voiptest.R;

import org.abtollc.sdk.AbtoApplication;
import org.abtollc.sdk.AbtoPhone;
import org.abtollc.sdk.OnRegistrationListener;

public class MainActivity extends Activity implements OnRegistrationListener{

    private String remoteContact;
    private String domain;
    private AbtoPhone abtoPhone;
    private ProgressDialog dialog;
    
    private Button audioCallButton;
    private Button videoCallButton;
    private EditText callUri;
    private TextView accLabel;
    int accExpire;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get AbtoPhone instance
        abtoPhone = ((AbtoApplication) getApplication()).getAbtoPhone();

        // Set registration event
        abtoPhone.setRegistrationStateListener(this);

        audioCallButton = (Button) findViewById(R.id.start_audio_call);
        videoCallButton = (Button) findViewById(R.id.start_video_call);
        callUri = (EditText) findViewById(R.id.sip_number);

        //callUri.setText("150");//77744444444");

        int accId = (int)abtoPhone.getCurrentAccountId();
        accExpire = abtoPhone.getConfig().getAccountExpire(accId);


        accLabel = (TextView)findViewById(R.id.account_label);
        String contact = abtoPhone.getConfig().getAccount(accId).acc_id;
        contact = contact.replace("<", "");
        contact = contact.replace(">", "");

        if (accExpire == 0)  {
            accLabel.setText("Local contact: " + contact + ":" + abtoPhone.getConfig().getSipPort());
            callUri.setHint("number@domain:port");
            domain = "";
        }
        else  {
            accLabel.setText("Registered as : " + contact);
            domain = abtoPhone.getConfig().getAccountDomain(accId);
        }


        audioCallButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startCall(false);
                //try{
                //    String sipNumber = callUri.getText().toString();
                //    abtoPhone.sendTextMessage(abtoPhone.getCurrentAccountId(), "手机之间测试都是好的", sipNumber);//unicode msg test
                //} catch (RemoteException e) {
                //    e.printStackTrace();
                //}
            }
        });

        videoCallButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startCall(true);
            }
        });


    }

    @Override
    public void onRegistrationFailed(long accId, int statusCode, String statusText) {

        if(dialog != null) dialog.dismiss();

        AlertDialog.Builder fail = new AlertDialog.Builder(MainActivity.this);
        fail.setTitle("Registration failed");
        fail.setMessage(statusCode + " - " + statusText);
        fail.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        fail.show();

        onUnRegistered(0);
    }

    @Override
    public void onRegistered(long accId) {
        Toast.makeText(this, "MainActivity - onRegistered", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnRegistered(long arg0) {

        if(dialog != null) dialog.dismiss();

        //Unsubscribe reg events
        abtoPhone.setRegistrationStateListener(null);

        //Start reg activity
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));

        //Close this activity
        finish();
    }


    public void startCall(boolean bVideo)   {
        //Get phone number to dial
        String sipNumber = callUri.getText().toString();
        if(TextUtils.isEmpty(sipNumber))  return;

        if(TextUtils.isEmpty(domain) ) {
            if(!sipNumber.contains("@") ){
                Toast.makeText(this, "Specify remote side address as 'number@domain:port'", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(!sipNumber.contains("sip:") ) sipNumber  = "sip:" + sipNumber;
        if(!sipNumber.contains("@") )    sipNumber += "@"+ domain;


        // Start new call
        try {
            if(bVideo) abtoPhone.startVideoCall(sipNumber, abtoPhone.getCurrentAccountId());
            else       abtoPhone.startCall(sipNumber, abtoPhone.getCurrentAccountId());

            //if(!sipNumber.contains("@") ){
            //    remoteContact = String.format("<%1$s@%2$s>", sipNumber, domain);
            //}else{
            //    remoteContact = String.format("<%1$s>", sipNumber);
            //}
            remoteContact = sipNumber;

            startAV(false);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {

    	super.onResume();
    }


    public void onDestroy() {
        super.onDestroy();
    }


    public void onStop() {
        super.onStop();
    }


    @Override
    public void onBackPressed() {

        if(accExpire==0) {
            onUnRegistered(0);
            return;
        }
        try {
            if(dialog==null)
            {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(false);
                dialog.setMessage("Unregistering...");
            }
            dialog.show();

            abtoPhone.unregister();
        } catch (RemoteException e) {
            e.printStackTrace();
            dialog.dismiss();
        }
    }


    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }

    private synchronized void startAV(boolean incoming) {
        Intent intent = new Intent(this, ScreenAV.class);
        intent.putExtra("incoming", incoming);
        intent.putExtra(ScreenAV.CALL_ID, abtoPhone.getActiveCallId());
        intent.putExtra(AbtoPhone.REMOTE_CONTACT, remoteContact);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
