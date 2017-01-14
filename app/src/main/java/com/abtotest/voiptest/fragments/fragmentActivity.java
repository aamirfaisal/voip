package com.abtotest.voiptest.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.Ui.ChatList;
import com.abtotest.voiptest.Ui.CheckActivity;
import com.abtotest.voiptest.Ui.LogsActivity;
import com.abtotest.voiptest.Ui.RegisterActivity;
import com.abtotest.voiptest.Ui.ScreenAV;
import com.abtotest.voiptest.db.dbChat;
import com.abtotest.voiptest.servicess.BackgroundService;

import org.abtollc.sdk.AbtoApplication;
import org.abtollc.sdk.AbtoPhone;
import org.abtollc.sdk.OnIncomingCallListener;
import org.abtollc.sdk.OnRegistrationListener;
import org.abtollc.sdk.OnTextMessageListener;

/**
 * Created by Car on 6/18/2016.
 */
public class fragmentActivity extends FragmentActivity {
    private ProgressDialog dialog;
    static public AbtoPhone abtoPhone;
    public String remoteContact;
    public static String EditText = "";
    dbChat dbChatObj;
    ChatList chatList;
    private Button dialer, callLogs, Contacts, Chat, Configs;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainfragment);
        dbChatObj = new dbChat(this);
        dialer = (Button) findViewById(R.id.Dialer);
        callLogs = (Button) findViewById(R.id.Call_Logs);
        Contacts = (Button) findViewById(R.id.contact_list);
        Chat = (Button) findViewById(R.id.chat_box);
        Configs = (Button) findViewById(R.id.config);
        abtoPhone = ((AbtoApplication) getApplication()).getAbtoPhone();

        abtoPhone.setRegistrationStateListener(new OnRegistrationListener() {

            public void onRegistrationFailed(long accId, int statusCode, String statusText) {

                if (dialog != null) dialog.dismiss();

                AlertDialog.Builder fail = new AlertDialog.Builder(fragmentActivity.this);
                fail.setTitle("Registration Failed");
                fail.setMessage(statusCode + " - " + statusText);
                fail.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                fail.show();

                onUnRegistered(0);
            }

            public void onRegistered(long accId) {

            }

            @Override
            public void onUnRegistered(long arg0) {

                if (dialog != null) dialog.dismiss();

                //Start reg activity
                startActivity(new Intent(fragmentActivity.this, RegisterActivity.class));
                finish();

            }
        }); //registration listener


        abtoPhone.setIncomingCallListener(new OnIncomingCallListener() {

            public void OnIncomingCall(String contact, long accountId) {
                remoteContact = contact;
                startAV(true);
            }
        }); //incoming call listener

        abtoPhone.setInMessageListener(new OnTextMessageListener() {
            @Override
            public void onTextMessageReceived(String msg, String sender, String receiver) {

                dbChatObj.insertInboxValues(sender, msg, "1");
                if (chatList != null) {
                    chatList.refershList();
                }
            }
        });


        Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(fragmentActivity.this, "Functionality coming soon", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= 23) {
                    Contacts.setTextColor(getResources().getColor(R.color.black, null));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.white, null));

                    Chat.setTextColor(getResources().getColor(R.color.white, null));
                    Chat.setBackgroundColor(getResources().getColor(R.color.black, null));
                    Configs.setTextColor(getResources().getColor(R.color.white, null));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black, null));
                    dialer.setTextColor(getResources().getColor(R.color.white, null));
                    dialer.setBackgroundColor(getResources().getColor(R.color.black, null));
                    callLogs.setTextColor(getResources().getColor(R.color.white, null));
                    callLogs.setBackgroundColor(getResources().getColor(R.color.black, null));

                } else {
                    Contacts.setTextColor(getResources().getColor(R.color.black));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.white));

                    Chat.setTextColor(getResources().getColor(R.color.white));
                    Chat.setBackgroundColor(getResources().getColor(R.color.black));
                    Configs.setTextColor(getResources().getColor(R.color.white));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black));
                    dialer.setTextColor(getResources().getColor(R.color.white));
                    dialer.setBackgroundColor(getResources().getColor(R.color.black));
                    callLogs.setTextColor(getResources().getColor(R.color.white));
                    callLogs.setBackgroundColor(getResources().getColor(R.color.black));
                }
                clearBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragframe, new ContactsList()).commit();
            }
        });

        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    Chat.setTextColor(getResources().getColor(R.color.black, null));
                    Chat.setBackgroundColor(getResources().getColor(R.color.white, null));

                    Contacts.setTextColor(getResources().getColor(R.color.white, null));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.black, null));
                    Configs.setTextColor(getResources().getColor(R.color.white, null));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black, null));
                    dialer.setTextColor(getResources().getColor(R.color.white, null));
                    dialer.setBackgroundColor(getResources().getColor(R.color.black, null));
                    callLogs.setTextColor(getResources().getColor(R.color.white, null));
                    callLogs.setBackgroundColor(getResources().getColor(R.color.black, null));

                } else {
                    Chat.setTextColor(getResources().getColor(R.color.black));
                    Chat.setBackgroundColor(getResources().getColor(R.color.white));

                    Contacts.setTextColor(getResources().getColor(R.color.white));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.black));
                    Configs.setTextColor(getResources().getColor(R.color.white));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black));
                    dialer.setTextColor(getResources().getColor(R.color.white));
                    dialer.setBackgroundColor(getResources().getColor(R.color.black));
                    callLogs.setTextColor(getResources().getColor(R.color.white));
                    callLogs.setBackgroundColor(getResources().getColor(R.color.black));
                }
                clearBackStack();
                chatList = new ChatList();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragframe, chatList).commit();

            }
        });

        Configs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Toast.makeText(fragmentActivity.this, "Functionality coming soon", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= 23) {
                    Configs.setTextColor(getResources().getColor(R.color.black, null));
                    Configs.setBackgroundColor(getResources().getColor(R.color.white, null));

                    Contacts.setTextColor(getResources().getColor(R.color.white, null));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.black, null));
                    Configs.setTextColor(getResources().getColor(R.color.white, null));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black, null));
                    dialer.setTextColor(getResources().getColor(R.color.white, null));
                    dialer.setBackgroundColor(getResources().getColor(R.color.black, null));
                    Chat.setTextColor(getResources().getColor(R.color.white, null));
                    Chat.setBackgroundColor(getResources().getColor(R.color.black, null));

                } else {
                    Configs.setTextColor(getResources().getColor(R.color.black));
                    Configs.setBackgroundColor(getResources().getColor(R.color.white));

                    Contacts.setTextColor(getResources().getColor(R.color.white));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.black));
                    Configs.setTextColor(getResources().getColor(R.color.white));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black));
                    dialer.setTextColor(getResources().getColor(R.color.white));
                    dialer.setBackgroundColor(getResources().getColor(R.color.black));
                    Chat.setTextColor(getResources().getColor(R.color.white));
                    Chat.setBackgroundColor(getResources().getColor(R.color.black));
                }
                Intent intent = new Intent(fragmentActivity.this, CheckActivity.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            dialer.setTextColor(getResources().getColor(R.color.black, null));
            dialer.setBackgroundColor(getResources().getColor(R.color.white, null));

            callLogs.setTextColor(getResources().getColor(R.color.white, null));
            callLogs.setBackgroundColor(getResources().getColor(R.color.black, null));

        } else {
            dialer.setTextColor(getResources().getColor(R.color.black));
            dialer.setBackgroundColor(getResources().getColor(R.color.white));

            callLogs.setTextColor(getResources().getColor(R.color.white));
            callLogs.setBackgroundColor(getResources().getColor(R.color.black));
        }


        dialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    dialer.setTextColor(getResources().getColor(R.color.black, null));
                    dialer.setBackgroundColor(getResources().getColor(R.color.white, null));


                    Contacts.setTextColor(getResources().getColor(R.color.white, null));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.black, null));
                    Configs.setTextColor(getResources().getColor(R.color.white, null));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black, null));
                    callLogs.setTextColor(getResources().getColor(R.color.white, null));
                    callLogs.setBackgroundColor(getResources().getColor(R.color.black, null));
                    Chat.setTextColor(getResources().getColor(R.color.white, null));
                    Chat.setBackgroundColor(getResources().getColor(R.color.black, null));

                } else {
                    dialer.setTextColor(getResources().getColor(R.color.black));
                    dialer.setBackgroundColor(getResources().getColor(R.color.white));

                    Contacts.setTextColor(getResources().getColor(R.color.white));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.black));
                    Configs.setTextColor(getResources().getColor(R.color.white));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black));
                    callLogs.setTextColor(getResources().getColor(R.color.white));
                    callLogs.setBackgroundColor(getResources().getColor(R.color.black));
                    Chat.setTextColor(getResources().getColor(R.color.white));
                    Chat.setBackgroundColor(getResources().getColor(R.color.black));
                }

                clearBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragframe, new MainFrag()).commit();
            }
        });

        callLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    callLogs.setTextColor(getResources().getColor(R.color.black, null));
                    callLogs.setBackgroundColor(getResources().getColor(R.color.white, null));
                    Contacts.setTextColor(getResources().getColor(R.color.white, null));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.black, null));
                    Configs.setTextColor(getResources().getColor(R.color.white, null));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black, null));
                    dialer.setTextColor(getResources().getColor(R.color.white, null));
                    dialer.setBackgroundColor(getResources().getColor(R.color.black, null));
                    Chat.setTextColor(getResources().getColor(R.color.white, null));
                    Chat.setBackgroundColor(getResources().getColor(R.color.black, null));

                } else {
                    callLogs.setTextColor(getResources().getColor(R.color.black));
                    callLogs.setBackgroundColor(getResources().getColor(R.color.white));

                    Contacts.setTextColor(getResources().getColor(R.color.white));
                    Contacts.setBackgroundColor(getResources().getColor(R.color.black));
                    Configs.setTextColor(getResources().getColor(R.color.white));
                    Configs.setBackgroundColor(getResources().getColor(R.color.black));
                    dialer.setTextColor(getResources().getColor(R.color.white));
                    dialer.setBackgroundColor(getResources().getColor(R.color.black));
                    Chat.setTextColor(getResources().getColor(R.color.white));
                    Chat.setBackgroundColor(getResources().getColor(R.color.black));
                }

                clearBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragframe, new LogsActivity()).commit();
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragframe, new MainFrag()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (chatList != null) {
            chatList.refershList();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                try {
                    if (dialog == null) {
                        dialog = new ProgressDialog(fragmentActivity.this);
                        dialog.setCancelable(false);
                        dialog.setMessage("Unregistering...");
                    }
                    dialog.show();
                    abtoPhone.unregister();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);
        boolean check = preferences.getBoolean("check", false);
        if (check) {
            startService(new Intent(fragmentActivity.this, BackgroundService.class));
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        } else {
            finish();
        }

    }

    private synchronized void startAV(boolean incoming) {
        Intent intent = new Intent(fragmentActivity.this, ScreenAV.class);
        intent.putExtra("incoming", incoming);
        intent.putExtra(ScreenAV.CALL_ID, abtoPhone.getActiveCallId());
        intent.putExtra(AbtoPhone.REMOTE_CONTACT, remoteContact);
        startActivity(intent);
    }


}
