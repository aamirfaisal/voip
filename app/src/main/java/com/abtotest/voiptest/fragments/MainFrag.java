package com.abtotest.voiptest.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.Ui.ScreenAV;
import com.abtotest.voiptest.Ui.SplashActivity;

import org.abtollc.sdk.AbtoPhone;

import java.text.DateFormat;
import java.util.Date;

//import org.pjsip.pjsua.pjsuaConstants;

public class MainFrag extends Fragment{

    public String remoteContact;
    private String domain;
    private AbtoPhone abtoPhone;
    private ProgressDialog dialog;

    private Button mainButton, mainButton1, b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bsterik,bhash,delete, logs;
    private TextView callUri;
    private String number= fragmentActivity.EditText;
    private Boolean abcd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main_frag, container, false);
        mainButton = (Button) view.findViewById(R.id.main_button3);
        mainButton1 = (Button) view.findViewById(R.id.main_button2);
        delete = (Button) view.findViewById(R.id.delete);
        b1 = (Button) view.findViewById(R.id.button);
        b2 = (Button) view.findViewById(R.id.button2);
        b3 = (Button) view.findViewById(R.id.button3);
        b4 = (Button) view.findViewById(R.id.button4);
        b5 = (Button) view.findViewById(R.id.button5);
        b6 = (Button) view.findViewById(R.id.button6);
        b7 = (Button) view.findViewById(R.id.button7);
        b8 = (Button) view.findViewById(R.id.button8);
        b9 = (Button) view.findViewById(R.id.button9);
        b0 = (Button) view.findViewById(R.id.button11);
        bsterik = (Button) view.findViewById(R.id.button10);
        bhash = (Button) view.findViewById(R.id.button12);
        callUri = (TextView) view.findViewById(R.id.sip_number);
        callUri.setText(fragmentActivity.EditText);
        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.dial_sound);
        abcd = true;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setBackgroundResource(R.drawable.first);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b1.setBackgroundResource(R.drawable.firsts);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();

                number = number + "1";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b2.setBackgroundResource(R.drawable.second);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b2.setBackgroundResource(R.drawable.seconds);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "2";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b3.setBackgroundResource(R.drawable.third);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b3.setBackgroundResource(R.drawable.thirds);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "3";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b4.setBackgroundResource(R.drawable.fourth);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b4.setBackgroundResource(R.drawable.fourths);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "4";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b5.setBackgroundResource(R.drawable.fifth);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b5.setBackgroundResource(R.drawable.fifths);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "5";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b6.setBackgroundResource(R.drawable.sixth);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b6.setBackgroundResource(R.drawable.sixths);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "6";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b7.setBackgroundResource(R.drawable.seventh);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b7.setBackgroundResource(R.drawable.sevenths);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "7";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b8.setBackgroundResource(R.drawable.eigth);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b8.setBackgroundResource(R.drawable.eighths);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "8";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b9.setBackgroundResource(R.drawable.ninth);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b9.setBackgroundResource(R.drawable.ninths);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "9";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b0.setBackgroundResource(R.drawable.zero);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b0.setBackgroundResource(R.drawable.zeros);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "0";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        bsterik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsterik.setBackgroundResource(R.drawable.star);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bsterik.setBackgroundResource(R.drawable.star_s);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "*";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });
        bhash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bhash.setBackgroundResource(R.drawable.hash);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bhash.setBackgroundResource(R.drawable.hashs);
                    }
                }, 50);
                Vibrator vb = (Vibrator)   getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(100);
                mp.start();
                number = number + "#";
                fragmentActivity.EditText = number;
                callUri.setText(number);
            }
        });

        domain = abtoPhone.getConfig().getAccountDomain( (int)abtoPhone.getCurrentAccountId() );

        // Set the action depending on registered or calling
        mainButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Get phone number to dial
                String sipNumber = callUri.getText().toString();
                //sipNumber = sipNumber.substring(1,sipNumber.length());
                number = "";
                callUri.setText("");
                if(TextUtils.isEmpty(sipNumber))  return;

                SplashActivity.nameofcontact.add(sipNumber);
                SplashActivity.picture.add(R.drawable.ic_call_outgoing_holo_dark);

                SplashActivity.dateandtime.add(DateFormat.getDateTimeInstance().format(new Date()));
                // Start new call
                try {
                   // abtoPhone.startCall(sipNumber, abtoPhone.getCurrentAccountId());
                    abtoPhone.startVideoCall(sipNumber, abtoPhone.getCurrentAccountId());
                    if(!sipNumber.contains("@")){
                        remoteContact = String.format("<sip:%1$s@%2$s>", sipNumber, domain);
                    }else{
                        remoteContact = String.format("<sip:%1$s>", sipNumber);
                    }

                    startAV(false);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });// mainButton onClicListener

        mainButton1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Get phone number to dial
                String sipNumber = callUri.getText().toString();
                //sipNumber = sipNumber.substring(1,sipNumber.length());
                number = "";
                callUri.setText("");
                if(TextUtils.isEmpty(sipNumber))  return;

                SplashActivity.nameofcontact.add(sipNumber);
                SplashActivity.picture.add(R.drawable.ic_call_outgoing_holo_dark);

                SplashActivity.dateandtime.add(DateFormat.getDateTimeInstance().format(new Date()));
                // Start new call
                try {
                    // abtoPhone.startCall(sipNumber, abtoPhone.getCurrentAccountId());
                    abtoPhone.startCall(sipNumber, abtoPhone.getCurrentAccountId());
                    if(!sipNumber.contains("@")){
                        remoteContact = String.format("<sip:%1$s@%2$s>", sipNumber, domain);
                    }else{
                        remoteContact = String.format("<sip:%1$s>", sipNumber);
                    }

                    startAV(false);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });// mainButton onClicListener


        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!(number.equals(""))){

                    number = "";
                    fragmentActivity.EditText = number;
                    callUri.setText(number);}
                return true;
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(number.equals(""))) {
                    number = number.substring(0, number.length() - 1);
                    fragmentActivity.EditText = number;
                    callUri.setText(number);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        abtoPhone = fragmentActivity.abtoPhone;

    }

    @Override
    public void onResume() {

        super.onResume();
    }


    public void onDestroy() {
        super.onDestroy();
    }


    public void onStop() {
        super.onStop();
    }


    private synchronized void startAV(boolean incoming) {
        Intent intent = new Intent(getContext(), ScreenAV.class);
        intent.putExtra("incoming", incoming);
        intent.putExtra(ScreenAV.CALL_ID, abtoPhone.getActiveCallId());
        intent.putExtra(AbtoPhone.REMOTE_CONTACT, remoteContact);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
