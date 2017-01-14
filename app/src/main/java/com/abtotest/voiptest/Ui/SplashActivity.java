package com.abtotest.voiptest.Ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.abtotest.voiptest.R;

import org.abtollc.sdk.AbtoApplication;
import org.abtollc.sdk.AbtoPhone;
import org.abtollc.sdk.AbtoPhoneCfg;
import org.abtollc.sdk.OnInitializeListener;
import org.abtollc.utils.Log;
import org.abtollc.utils.codec.Codec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SplashActivity extends Activity implements OnInitializeListener {

    private AbtoPhone abtoPhone;
    static public List<String> nameofcontact = new ArrayList<String>();
    static public List<Integer> picture = new ArrayList<Integer>();
    static public List<String> dateandtime = new ArrayList<String>();
    static public List<String> callTime = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean bCanStartPhoneInitialization = (Build.VERSION.SDK_INT >= 23) ?  askPermissions() : true;

        if(bCanStartPhoneInitialization)    initPhone();

    }//onCreate




    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    private boolean askPermissions()
    {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))           permissionsNeeded.add("Record audio");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) permissionsNeeded.add("Write logs to sd card");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))                 permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.USE_SIP))                permissionsNeeded.add("Use SIP protocol");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                //String message = "You need to grant access to " + permissionsNeeded.get(0);
                //for (int i = 1; i < permissionsNeeded.size(); i++) message = message + ", " + permissionsNeeded.get(i);


               ActivityCompat.requestPermissions(this,
                    permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

                return false;
            }

            ActivityCompat.requestPermissions(this,
                    permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }


    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission))     return false;
        }


        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(SplashActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }




    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                //Initial
                perms.put(Manifest.permission.RECORD_AUDIO,           PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA,                 PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.USE_SIP,                PackageManager.PERMISSION_GRANTED);

                //Fill with results
                for (int i = 0; i < permissions.length; i++) perms.put(permissions[i], grantResults[i]);

                //Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.USE_SIP) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    initPhone();
                } else {
                    // Permission Denied
                    Toast.makeText(SplashActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
                    ProgressBar bar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
                    bar.setVisibility(View.GONE);
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    @Override
    protected void onResume() {

        super.onResume();
    }

    protected void initPhone()
    {
        // Get AbtoPhone instance
        abtoPhone = ((AbtoApplication) getApplication()).getAbtoPhone();

        if(abtoPhone.isActive()) return;

        abtoPhone.setInitializeListener(this);

        //configure phone instance
        AbtoPhoneCfg config = abtoPhone.getConfig();
        //config.setCodecPriority(Codec.G729, (short) 220);
        config.setCodecPriority(Codec.PCMU, (short) 250);
        //config.setCodecPriority(Codec.GSM, (short) 150);
        //config.setCodecPriority(Codec.PCMA, (short) 240);

        config.setCodecPriority(Codec.H263_1998, (short) 220);
        //config.setCodecPriority(Codec.H264, (short) 210);


        config.setSignallingTransport(AbtoPhoneCfg.SignalingTransportType.UDP);
        //config.setSignallingTransport(AbtoPhoneCfg.SignalingTransportType.TCP);
        //config.setSignallingTransport(AbtoPhoneCfg.SignalingTransportType.TLS);
        //config.setTLSVerifyServer(false);

        config.setUseSRTP(false);

        Log.setLogLevel(5);
        Log.setUseFile(true);

        // Start initializing
        abtoPhone.initialize();
    }

    public void onDestroy()
    {
        abtoPhone.setInitializeListener(null);
        super.onDestroy();

    }//onDestroy

    @Override
    public void onInitializeState(OnInitializeListener.InitializeState state, String message) {
        switch (state) {
            case START:
            case INFO:
            case WARNING: break;
            case FAIL:

                new AlertDialog.Builder(SplashActivity.this)
                        .setTitle("Error")
                        .setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dlg, int which) {
                                dlg.dismiss();

                            }
                        }).create().show();
                break;
            case SUCCESS:

                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }


}