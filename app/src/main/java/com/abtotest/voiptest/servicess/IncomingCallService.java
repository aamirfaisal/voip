package com.abtotest.voiptest.servicess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.abtotest.voiptest.Ui.ScreenAV;

import org.abtollc.sdk.AbtoApplication;
import org.abtollc.sdk.AbtoPhone;
import org.abtollc.sdk.OnIncomingCallListener;

public class IncomingCallService extends Service implements OnIncomingCallListener{

	private AbtoPhone abtoPhone;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		abtoPhone = ((AbtoApplication) getApplication()).getAbtoPhone();
		
		abtoPhone.setIncomingCallListener(this);
		
		return START_STICKY;
	}

	@Override
	public void OnIncomingCall(String remoteContact, long arg1) {
    	Intent intent = new Intent(this, ScreenAV.class);
        intent.putExtra("incoming", true);
        intent.putExtra(ScreenAV.CALL_ID, abtoPhone.getActiveCallId());
        intent.putExtra(AbtoPhone.REMOTE_CONTACT, remoteContact);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
