package com.abtotest.voiptest.Ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.adapter.AdapterChatList;
import com.abtotest.voiptest.db.dbChat;
import com.abtotest.voiptest.model.InboxMOdel;

import java.util.ArrayList;

/**
 * Created by root on 12/5/16.
 */

public class ChatList extends Fragment {
    View view;
    ListView chatlistView;
    AdapterChatList adapterChatList;
    ArrayList<InboxMOdel> mOdelArrayList;
    dbChat dbChatObject;
    public static String contactNumber;
    Button btnCOntact;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_list, container, false);
        chatlistView = (ListView) view.findViewById(R.id.chatLisView);
        btnCOntact = (Button) view.findViewById(R.id.btn_find_contact);
        context = getContext();
        //     floatingActionButton = (FloatingActionButton) view.findViewById(R.id.btn_find_contact);
        dbChatObject = new dbChat(getActivity());
        mOdelArrayList = new ArrayList<>();
        adapterChatList = new AdapterChatList(getActivity(), mOdelArrayList);
        chatlistView.setAdapter(adapterChatList);
        refershList();
        chatlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactNumber = mOdelArrayList.get(position).getName();
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });
        chatlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
                        dbChatObject.deleteItem(mOdelArrayList.get(position).getUserID());
                        mOdelArrayList.remove(mOdelArrayList.get(position));
                        adapterChatList.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });
                AlertDialog dialouge = alert.create();
                dialouge.show();
                return true;
            }
        });
        btnCOntact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentManager fm = getFragmentManager();
                MyAlertDialoge alertDialog = new MyAlertDialoge();
                alertDialog.show(fm, "fragment_alert");*/
                Intent go = new Intent(getActivity(), AddMsgToSend.class);
                startActivity(go);


            }
        });

        return view;
    }

    public void refershList() {
        mOdelArrayList.clear();
        mOdelArrayList.addAll(dbChatObject.getInboxNameList());
        adapterChatList.notifyDataSetChanged();
    }

    public void myDialogue() {
        AlertDialog.Builder aDialouge = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_alert, null);
        final EditText inputField = (EditText) view.findViewById(R.id.input_Number);
        aDialouge.setTitle("Enter number to send message");
        aDialouge.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactNumber = inputField.getText().toString();
                Toast.makeText(getActivity(), "Contact= " + contactNumber, Toast.LENGTH_SHORT).show();
            }
        });
        aDialouge.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getActivity(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                inputField.setText("");
                dialog.cancel();
            }
        });
        aDialouge.show();
    }

}


