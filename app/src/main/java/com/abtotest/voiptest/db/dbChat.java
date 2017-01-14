package com.abtotest.voiptest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.abtotest.voiptest.model.ChatMessageModel;
import com.abtotest.voiptest.model.InboxMOdel;

import java.util.ArrayList;

/**
 * Created by root on 12/5/16.
 */

public class dbChat extends SQLiteOpenHelper {
    private static final String DataBase_Name = "chatapp.db";
    private static final int DataBase_Version = 3;
    private static final String INBOX_Table = "inbox_table";
    private static final String INBOX_COLUMN_ID = "id";
    private static final String INBOX_COLUMN_RECIVER_NAME = "reciver_name";
    private static final String INBOX_COLUMN_RECIVER_TEXT = "reciver_text";
    private static final String INBOX_COLUMN_POSITION = "pos_text";

    public dbChat(Context context) {
        super(context, DataBase_Name, null, DataBase_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INBOX_TABLE = "CREATE TABLE " + INBOX_Table + "( " + INBOX_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  ,"
                + INBOX_COLUMN_RECIVER_NAME + " TEXT, " + INBOX_COLUMN_POSITION + " TEXT, " + INBOX_COLUMN_RECIVER_TEXT + " TEXT " + ")";
        db.execSQL(CREATE_INBOX_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist " + INBOX_Table);
    }

    public void insertInboxValues(String reciverName, String reciverText, String flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        reciverName = reciverName.substring(reciverName.indexOf(":") + 1, reciverName.indexOf("@"));
        contentValues.put(INBOX_COLUMN_RECIVER_NAME, reciverName);
        contentValues.put(INBOX_COLUMN_RECIVER_TEXT, reciverText);
        contentValues.put(INBOX_COLUMN_POSITION, flag);
        long checkDbStatus = db.insert(INBOX_Table, null, contentValues);
        if (checkDbStatus > -1) {
            Log.d("Db insert", "insert inbox " + checkDbStatus);
        }
        db.close();
    }

    public ArrayList<InboxMOdel> getInboxNameList() {
        SQLiteDatabase database = this.getWritableDatabase();
        ArrayList<InboxMOdel> list = new ArrayList<>();
        String query = "Select * from " + INBOX_Table + " WHERE " + INBOX_COLUMN_ID + "= " +
                "(SELECT MAX(" + INBOX_COLUMN_ID + ") FROM " + INBOX_Table + " GROUP BY " + INBOX_COLUMN_RECIVER_NAME + ") ";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                InboxMOdel inboxMOdel = new InboxMOdel();
                inboxMOdel.setUserID(cursor.getInt(cursor.getColumnIndex(INBOX_COLUMN_ID)));
                inboxMOdel.setName(cursor.getString(cursor.getColumnIndex(INBOX_COLUMN_RECIVER_NAME)));
                inboxMOdel.setText(cursor.getString(cursor.getColumnIndex(INBOX_COLUMN_RECIVER_TEXT)));
                list.add(inboxMOdel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;

    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(INBOX_Table, INBOX_COLUMN_ID + " = " + id, null);

    }

    public ArrayList<ChatMessageModel> getConversationList(String reciverName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChatMessageModel> conversationList = new ArrayList<>();
        String query = "select * from " + INBOX_Table + " where " + INBOX_COLUMN_RECIVER_NAME + " = " + reciverName;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                ChatMessageModel chatMessageModel = new ChatMessageModel();
                String flag = cursor.getString(cursor.getColumnIndex(INBOX_COLUMN_POSITION));
                if (flag.equals("0")) {
                    chatMessageModel.left = false;
                } else {
                    chatMessageModel.left = true;
                }

                chatMessageModel.msg = cursor.getString(cursor.getColumnIndex(INBOX_COLUMN_RECIVER_TEXT));
                conversationList.add(chatMessageModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return conversationList;
    }


}
