package com.abtotest.voiptest.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.abtotest.voiptest.R;

import org.abtollc.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Car on 7/11/2016.
 */
public class ContactsList extends Fragment {
    static public List<String> nameofcontact1 = new ArrayList<String>();
    static public List<Integer> picture1 = new ArrayList<Integer>();
    static public List<String> PhoneNumber = new ArrayList<String>();
    static public List<String> callTime1 = new ArrayList<String>();


    private ListView ContactListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contact_list, container, false);
        ContactListView = (ListView) view.findViewById(R.id.contact_list_view);
        nameofcontact1.clear();
        PhoneNumber.clear();
        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            int contactIdIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
            int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int phoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int photoIdIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
            cursor.moveToFirst();
            do {
                String idContact = cursor.getString(contactIdIdx);
                Log.i("hello World1", idContact);
                String name = cursor.getString(nameIdx);
                nameofcontact1.add(name);
                Log.i("hello World2", name);
                String phoneNumber = cursor.getString(phoneNumberIdx);
                PhoneNumber.add(phoneNumber);
                Log.i("hello World3", phoneNumber);
                //...
            } while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, nameofcontact1);
        ContactListView.setAdapter(adapter);
        ContactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),PhoneNumber.get(position),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}

