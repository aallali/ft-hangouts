package com.example.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

public class ContactData extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "contacts.db";

    private static final String TABLE_NAME = "contacts";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_STREET = "street";
    private static final String COLUMN_POSTAL_CODE = "postalcode";

    public ContactData(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_PHONE + " TEXT," +
                        COLUMN_EMAIL + " TEXT," +
                        COLUMN_STREET + " TEXT," +
                        COLUMN_POSTAL_CODE + " TEXT)";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addContact(String name, String phone, String email, String street, String postal_code) {
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_STREET, street);
        cv.put(COLUMN_POSTAL_CODE, postal_code);
        long res = db.insert(TABLE_NAME, null, cv);
        if (res == -1)
            Log.v(this.getClass().getName(), "Failed to add contact");
        else
            Log.v(this.getClass().getName(), "added contact");
    }

    public Cursor getAllContact() {
        String q = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase  db = this.getReadableDatabase();

        return db.rawQuery(q, null);
    }

    public void updateContact(String row_id, String name, String phone, String email, String street, String postal_code) {
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.v(this.getClass().getName(), "Name : " + name + "-Phone : " + phone +  "-Email : " + email +  "-Street : " + street + "-Postal : " + postal_code);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_STREET, street);
        cv.put(COLUMN_POSTAL_CODE, postal_code);
        Log.v("CV : ", cv.toString());
        long res = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (res == -1)
            Log.v(this.getClass().getName(), "Failed to update contact");
        else
            Log.v(this.getClass().getName(), "contact updated");
    }

    public void deleteContact(String row_id) {
        SQLiteDatabase  db = this.getWritableDatabase();
        long res = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (res == -1)
            Log.v(this.getClass().getName(), "Failed to delete contact");
        else
            Log.v(this.getClass().getName(), "contact deleted");
    }
}
