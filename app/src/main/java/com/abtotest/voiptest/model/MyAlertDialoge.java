package com.abtotest.voiptest.model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abtotest.voiptest.R;
import com.abtotest.voiptest.Ui.ChatActivity;

import static com.abtotest.voiptest.Ui.ChatList.contactNumber;

/**
 * Created by root on 12/8/16.
 */

public class MyAlertDialoge extends DialogFragment {
    public MyAlertDialoge() {
        // Empty constructor required for DialogFragment
    }

   /* public static MyAlertDialoge newInstance(String title) {
        MyAlertDialoge frag = new MyAlertDialoge();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        String title = getArguments().getString("title");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_alert, null);
        final EditText inputField = (EditText) view.findViewById(R.id.input_Number);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setMessage("Enter number");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                contactNumber = inputField.getText().toString();
                Toast.makeText(getActivity(), "Number= "+contactNumber, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), ChatActivity.class);
                startActivity(i);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

}
