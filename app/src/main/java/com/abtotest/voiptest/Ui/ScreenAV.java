package com.abtotest.voiptest.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abtotest.voiptest.R;

import org.abtollc.sdk.AbtoApplication;
import org.abtollc.sdk.AbtoPhone;
import org.abtollc.sdk.OnCallConnectedListener;
import org.abtollc.sdk.OnCallDisconnectedListener;
import org.abtollc.sdk.OnCallHeldListener;
import org.abtollc.sdk.OnRemoteAlertingListener;
import org.abtollc.sdk.OnToneReceivedListener;

import java.text.DateFormat;
import java.util.Date;

public class ScreenAV extends Activity implements OnCallConnectedListener,
        OnRemoteAlertingListener, OnCallDisconnectedListener,
        OnCallHeldListener, OnToneReceivedListener {

    protected static final String THIS_FILE = "ScreenAV";

    public static final String CALL_TERMINATED = "Call terminated";
    public static final String SEND_VIDEO = "send_video";
    public static final long MILLISECONDS_IN_SECONDS = 1000;
    public static final String POINT_TIME = "pointTime";
    public static final String TOTAL_TIME = "totalTime";
    public static final String CALL_ID = "call_id";

    private AbtoPhone phone;
    private int activeCallId = AbtoPhone.INVALID_CALL_ID;

    private TextView status;
    private TextView name;
    private Button pickUpVideo /*micBtn*/, micBtn, speakerBtn;
    private ImageView /*micBtn, speakerBtn,*/ muteBtn;
    private LinearLayout allVideoLayout;
    private LinearLayout pickUpLayout;
    private static boolean sendingVideo;

    private boolean bIsIncoming;
    private boolean bIsSpeakerOn = false, bIsMicMute = false;

    private WakeLock inCallWakeLock;
    private PowerManager powerManager;
    private int previewRotation = 0, capturerRotation = 0;

    /**
     * executes when activity have been created;
     */

    public void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        super.onCreate(savedInstanceState);

        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        if (inCallWakeLock == null) {

            int flags = PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP;

            inCallWakeLock = powerManager.newWakeLock(flags, "org.abtollc.videoCall");
            inCallWakeLock.setReferenceCounted(false);
        }

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        phone = ((AbtoApplication) getApplication()).getAbtoPhone();
        activeCallId = getIntent().getIntExtra(CALL_ID, AbtoPhone.INVALID_CALL_ID);
        Log.d(THIS_FILE, "callId - " + activeCallId);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.screen_caller);

        name = (TextView) findViewById(R.id.caller_contact_name);
        String remoteContact = getIntent().getStringExtra(AbtoPhone.REMOTE_CONTACT);
        name.setText(remoteContact);

        mTotalTime = getIntent().getLongExtra(TOTAL_TIME, 0);
        mPointTime = getIntent().getLongExtra(POINT_TIME, 0);
        if (mTotalTime != 0) {
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.postDelayed(mUpdateTimeTask, 100);
        }
        status = (TextView) findViewById(R.id.caller_call_status);

        bIsIncoming = getIntent().getBooleanExtra("incoming", false);

        //sendingVideo = getIntent().getBooleanExtra(SEND_VIDEO, false);

        status.setText(bIsIncoming ? "Ringing" : "Calling");

        allVideoLayout = (LinearLayout) findViewById(R.id.all_video_layout);

        pickUpLayout = (LinearLayout) findViewById(R.id.caller_pick_up_layout);
        pickUpLayout.setVisibility(bIsIncoming ? View.VISIBLE : View.GONE);

        pickUpVideo = (Button) findViewById(R.id.caller_pick_up_video_button);
        pickUpVideo.setVisibility(phone.isVideoCall() ? View.VISIBLE : View.INVISIBLE);

        LinearLayout localVideooutLayout = (LinearLayout) ScreenAV.this.findViewById(R.id.local_video_parent);
        LinearLayout remoteVideoLayout = (LinearLayout) ScreenAV.this.findViewById(R.id.remote_video_parent);

        phone.setVideoWindows(localVideooutLayout, remoteVideoLayout);

        remoteVideoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturerRotation += 1;
                capturerRotation %= 4;
                switch (previewRotation) {
                    case 0:
                        phone.rotateCapturer(AbtoPhone.Rotation.ROTATION_0);
                        break;
                    case 1:
                        phone.rotateCapturer(AbtoPhone.Rotation.ROTATION_90);
                        break;
                    case 2:
                        phone.rotateCapturer(AbtoPhone.Rotation.ROTATION_180);
                        break;
                    case 3:
                        phone.rotateCapturer(AbtoPhone.Rotation.ROTATION_270);
                        break;
                }
            }
        });

        localVideooutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewRotation += 1;
                previewRotation %= 4;
                switch (previewRotation) {
                    case 0:
                        phone.rotatePreview(AbtoPhone.Rotation.ROTATION_0);
                        break;
                    case 1:
                        phone.rotatePreview(AbtoPhone.Rotation.ROTATION_90);
                        break;
                    case 2:
                        phone.rotatePreview(AbtoPhone.Rotation.ROTATION_180);
                        break;
                    case 3:
                        phone.rotatePreview(AbtoPhone.Rotation.ROTATION_270);
                        break;
                }
            }
        });

        speakerBtn = /*(ImageView)*/(Button) findViewById(R.id.toggle_speaker_button);
        speakerBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                bIsSpeakerOn = !bIsSpeakerOn;
                if (bIsSpeakerOn) {
                    //speakerBtn.setBackgroundResource(R.drawable.speakers);
                    speakerBtn.setText("speaker off");
                } else {
                    speakerBtn.setText("speaker off");
                }

                try {
                    //phone.holdRetriveCall();
                    phone.setSpeakerphoneOn(bIsSpeakerOn);
                } catch (RemoteException e) {
                    Log.e(THIS_FILE, e.getMessage());
                }
            }
        });

        micBtn = (Button)/*(ImageView)*/ findViewById(R.id.toggle_mic_button);
        micBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                bIsMicMute = !bIsMicMute;
                if (bIsMicMute) {
                    micBtn.setText("microphone on");
                } else {
                    micBtn.setText("microphone on");
                }

                try {
                    phone.setMicrophoneMute(bIsMicMute);
                } catch (RemoteException e) {
                    Log.e(THIS_FILE, e.getMessage());
                }
            }
        });


        ((SurfaceView) localVideooutLayout.getChildAt(0)).setZOrderOnTop(true);


        phone.setCallConnectedListener(this);
        phone.setCallDisconnectedListener(this);
        phone.setOnCallHeldListener(this);
        phone.setRemoteAlertingListener(this);
        phone.setToneReceivedListener(this);
    }

    @Override
    public void onCallConnected(String remoteContact) {
        this.pickUpLayout.setVisibility(View.GONE);
        bIsIncoming = false;

        if (mTotalTime == 0L) {
            mPointTime = System.currentTimeMillis();
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.postDelayed(mUpdateTimeTask, 100);
        }

        showVideoWindows(phone.isVideoCall());
    }

    @Override
    public void onCallDisconnected(String remoteContact, int callId, int statusCode) {
        if (callId == phone.getAfterEndedCallId()) {
            finish();
            SplashActivity.callTime.add(String.valueOf(mTotalTime / 1000));
            mTotalTime = 0;
        }
    }

    @Override
    public void onCallHeld(HoldState state) {
        if (state == HoldState.LOCAL_HOLD) status.setText("Local Hold");
        else if (state == HoldState.REMOTE_HOLD) status.setText("Remote Hold");
        else if (state == HoldState.ACTIVE) status.setText("Active");
    }

    @Override
    public void onRemoteAlerting(long accId, int statusCode) {
        String statusText = "";

        if (activeCallId == AbtoPhone.INVALID_CALL_ID) {
            activeCallId = phone.getActiveCallId();
        }

        switch (statusCode) {
            case TRYING:
                statusText = "Trying";
                break;
            case RINGING:
                statusText = bIsIncoming ? "Ringing" : "Calling";
                break;
            case SESSION_PROGRESS:
                statusText = "Session in progress";
                break;
        }

        status.setText(statusText);
    }

    @Override
    public void onToneReceived(char tone) {
        Toast.makeText(ScreenAV.this, "DTMF received: " + tone, Toast.LENGTH_SHORT).show();
    }

    public void hangUP(View view) {
        try {
            mHandler.removeCallbacks(mUpdateTimeTask);
            if (phone.getBeforeConfirmedCallId() == -1) {
                phone.hangUp();
            } else {
                phone.rejectCall();
            }
        } catch (RemoteException e) {
            Log.e(THIS_FILE, e.getMessage());
        }
    }

    public void pickUp(View view) {
        SplashActivity.nameofcontact.add(name.getText().toString());
        SplashActivity.picture.add(R.drawable.ic_call_incoming_holo_dark);

        SplashActivity.dateandtime.add(DateFormat.getDateTimeInstance().format(new Date()));

        sendingVideo = false;
        try {
            phone.answerCall(200, false);
        } catch (RemoteException e) {
            Log.e(THIS_FILE, e.getMessage());
        }
    }

    public void pickUpVideo(View view) {
        SplashActivity.nameofcontact.add(name.getText().toString());
        SplashActivity.picture.add(R.drawable.ic_call_incoming_holo_dark);

        SplashActivity.dateandtime.add(DateFormat.getDateTimeInstance().format(new Date()));
        try {
            sendingVideo = true;
            phone.answerCall(200, true);

            //phone.answerCall(183, false);
        } catch (RemoteException e) {
            Log.e(THIS_FILE, e.getMessage());
        }
    }

    private void showVideoWindows(boolean show) {
        allVideoLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    // ==========Timer==============
    private long mPointTime = 0;
    private long mTotalTime = 0;
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            mTotalTime += System.currentTimeMillis() - mPointTime;
            mPointTime = System.currentTimeMillis();
            int seconds = (int) (mTotalTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            if (seconds < 10) {
                status.setText("" + minutes + ":0" + seconds);
            } else {
                status.setText("" + minutes + ":" + seconds);
            }

            mHandler.postDelayed(this, 1000);
        }
    };

    // =============================

    @Override
    protected void onPause() {

        if (inCallWakeLock != null && inCallWakeLock.isHeld()) {
            inCallWakeLock.release();
        }

        mHandler.removeCallbacks(mUpdateTimeTask);

        super.onPause();
    }

    @Override
    protected void onResume() {

        pickUpLayout.setVisibility(bIsIncoming ? View.VISIBLE : View.GONE);

        if (mTotalTime != 0L) {
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.postDelayed(mUpdateTimeTask, 100);
        }

        if (inCallWakeLock != null) {
            inCallWakeLock.acquire();
        }
        super.onResume();

        //Restore video after picking up from background
        //if(phone.getActiveCallIdInProgress() != -1){
        //	phone.reinvite();
        //}

    }

    /**
     * executes when activity is destroyed;
     */
    public void onDestroy() {
        super.onDestroy();
        if ("Ringing".equals(status.getText())) {
            SplashActivity.nameofcontact.add(name.getText().toString());
            SplashActivity.picture.add(R.drawable.ic_call_missed_holo_dark);
            SplashActivity.callTime.add("0");
            SplashActivity.dateandtime.add(DateFormat.getDateTimeInstance().format(new Date()));
        }
        mHandler.removeCallbacks(mUpdateTimeTask);

        phone.setCallConnectedListener(null);
        phone.setCallDisconnectedListener(null);
        phone.setOnCallHeldListener(null);
        phone.setRemoteAlertingListener(null);
        phone.setToneReceivedListener(null);
    }


    public void onStop() {
        super.onStop();

    }

    /**
     * overrides panel buttons keydown functionality;
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {

            try {
                phone.hangUp();
            } catch (RemoteException e) {
                Log.e(THIS_FILE, e.getMessage());
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isSendingVideo() {
        return sendingVideo;
    }
}
