package com.abtotest.voiptest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.Ui.SplashActivity;

public class AdapterGridView extends BaseAdapter {
    int groupid;
    Context context;
    LayoutInflater inflater;
    ImageView callLog;
    TextView ContactNumber;
    TextView dateANDtime;
    TextView callDuration;
    public AdapterGridView(Context context, int vg){
        this.context=context;
        groupid=vg;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @Override
    public int getCount() {
        return SplashActivity.nameofcontact.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View cell;
        if(convertView==null){
        cell = inflater.inflate(R.layout.activity_grid,null);
        callLog = (ImageView) cell.findViewById(R.id.Gridedit);
            callLog.setImageResource(SplashActivity.picture.get(position));
        ContactNumber = (TextView) cell.findViewById(R.id.Gridtext2);
        dateANDtime = (TextView) cell.findViewById(R.id.Gridtext1);
            dateANDtime.setText(SplashActivity.dateandtime.get(position));
        ContactNumber.setText(SplashActivity.nameofcontact.get(position));
            callDuration = (TextView) cell.findViewById(R.id.call_duration);
            callDuration.setText(SplashActivity.callTime.get(position)+"s");
    }else{
        cell= (View)convertView;
    }
        return cell;
    }
}
