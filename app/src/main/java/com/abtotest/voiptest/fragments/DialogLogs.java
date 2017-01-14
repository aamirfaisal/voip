package com.abtotest.voiptest.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.Ui.SplashActivity;

/**
 * Created by Car on 6/20/2016.
 */
public class DialogLogs extends DialogFragment
{
    private TextView date,name,duration;
    int mNum;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("position");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_logs, container,
                false);
        getDialog().setTitle("Call Log");
        date = (TextView) rootView.findViewById(R.id.dialog_date);
        name = (TextView) rootView.findViewById(R.id.dialog_name);
        duration = (TextView) rootView.findViewById(R.id.dialog_duration);
        date.setText("Date Time: "+ SplashActivity.dateandtime.get(mNum));
        name.setText("Caller: "+ SplashActivity.nameofcontact.get(mNum));
        duration.setText("Duration: "+ SplashActivity.callTime.get(mNum)+" Seconds");

        // Do something else
        return rootView;
    }
}

