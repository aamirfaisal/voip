package com.abtotest.voiptest.Ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.abtotest.voiptest.servicess.IncomingCallService;
import com.abtotest.voiptest.R;
import com.abtotest.voiptest.fragments.fragmentActivity;

import org.abtollc.sdk.AbtoApplication;
import org.abtollc.sdk.AbtoPhone;
import org.abtollc.sdk.OnRegistrationListener;

public class RegisterActivity extends Activity {

    private ProgressDialog dialog;
    AbtoPhone abtoPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get AbtoPhone instance
        abtoPhone = ((AbtoApplication) getApplication()).getAbtoPhone();

        Button regButton = (Button) findViewById(R.id.register_button);
        final EditText userEdit = (EditText) findViewById(R.id.login);
        final EditText passEdit = (EditText) findViewById(R.id.password);
        final EditText domainEdit = (EditText) findViewById(R.id.domain);
        final CheckBox disableReg = (CheckBox) findViewById(R.id.disable_registration);


        regButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Show progress
                if (dialog == null) {
                    dialog = new ProgressDialog(RegisterActivity.this);
                    dialog.setCancelable(false);
                    dialog.setMessage("Registering...");
                    dialog.setCancelable(false);
                }

                dialog.show();

                int regTimeout = disableReg.isChecked() ? 0 : 300;

                String user = userEdit.getText().toString();
                String pass = passEdit.getText().toString();
                String domain = domainEdit.getText().toString();


                // Add account
                long accId = abtoPhone.getConfig().addAccount(domain, user, pass, null, user, regTimeout, true);

                //Register
                try {
                    abtoPhone.register();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


        // Set registration event
        abtoPhone.setRegistrationStateListener(new OnRegistrationListener() {

            public void onRegistrationFailed(long accId, int statusCode, String statusText) {

                if (dialog != null) dialog.dismiss();

                AlertDialog.Builder fail = new AlertDialog.Builder(RegisterActivity.this);
                fail.setTitle("Registration failed");
                fail.setMessage(statusCode + " - " + statusText);
                fail.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                fail.show();
            }

            public void onRegistered(long accId) {

                //Hide progress
                if (dialog != null) dialog.dismiss();

                //Unsubscribe reg events
                abtoPhone.setRegistrationStateListener(null);

                //Start incoming call service
                startService(new Intent(RegisterActivity.this, IncomingCallService.class));

                //Start main activity
                Intent intent = new Intent(RegisterActivity.this, /*MainActivity*/fragmentActivity.class);
                startActivity(intent);

                //Close this activity
                finish();
            }

            @Override
            public void onUnRegistered(long arg0) {

                Toast.makeText(RegisterActivity.this, "RegisterActivity::onUnRegistered", Toast.LENGTH_SHORT).show();
            }
        }); //registration listener

    }//onCreate

    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void onBackPressed() {
        //Exit app here
        try {
            abtoPhone.setRegistrationStateListener(null);

            //Stop incoming call service
            Intent intent = new Intent(RegisterActivity.this, IncomingCallService.class);
            stopService(intent);

            //Destroy phone
            if (abtoPhone.isActive()) abtoPhone.destroy();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

}
