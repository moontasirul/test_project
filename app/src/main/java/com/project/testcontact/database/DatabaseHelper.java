package com.project.testcontact.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.testcontact.model.ContactNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user123 on 1/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "emergency.sqlite";
    public static final String DBLOCATION = "/data/data/com.atomappgroup.emergencycontact/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void openDataBase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDataBase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }


    public List<ContactNumber> getListContact() {
        Log.d("check call Method", "Contact Number");
        ContactNumber contactNumber = null;
        List<ContactNumber> contactList = new ArrayList<>();
        openDataBase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM contact_nnumber", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int column_index= 3;
            Log.d("Contact Number", " " + cursor.getInt(0) + "  " + cursor.getString(1) + " " + cursor.getString(2));

            contactNumber = new ContactNumber(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            Log.d("Check test",""+contactNumber.toString());
            contactList.add(contactNumber);
            cursor.moveToNext();
        }
        cursor.close();
        closeDataBase();
        return contactList;
    }


    public List<ContactNumber> getFavList() {
        Log.d("check call Method", "Contact Number");
        ContactNumber contactNumber = null;
        List<ContactNumber> contactList = new ArrayList<>();
        openDataBase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM contact_nnumber where fav_item = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int column_index= 3;
            Log.d("Contact Number", " " + cursor.getInt(0) + "  " + cursor.getString(1) + " " + cursor.getString(2));
            boolean value = cursor.getInt(column_index) > 0;
            contactNumber = new ContactNumber(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            contactList.add(contactNumber);
            cursor.moveToNext();
        }
        cursor.close();
        closeDataBase();
        return contactList;
    }




    public ContactNumber getContactById(int id) {
        ContactNumber number = null;
        openDataBase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM PRODUCT WHERE ID = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        number = new ContactNumber(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        //Only 1 result
        cursor.close();
        closeDataBase();
        return number;
    }



    public long addContact(ContactNumber number) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", number.getId());
        contentValues.put("address_name", number.getTitle());
        contentValues.put("phone_number", number.getNumber());
        openDataBase();
        long returnValue = mDatabase.insert("fav_contact_nnumber", null, contentValues);
        closeDataBase();
        return returnValue;
    }


    public boolean deleteContactById(int id) {
        openDataBase();
        int result = mDatabase.delete("fav_contact_nnumber", "id =?", new String[]{String.valueOf(id)});
        closeDataBase();
        return result != 0;
    }
}
