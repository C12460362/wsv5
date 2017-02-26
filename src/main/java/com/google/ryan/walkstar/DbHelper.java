package com.google.ryan.walkstar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ryan on 17/02/2017.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "USERNAME";
    private static final String COLUMN_PASS = "PASSWORD";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_PASS + " TEXT NOT NULL" + ")";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "drop table if exists"+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getUser()); // User Name
        values.put(COLUMN_PASS, user.getPassword()); // Password

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public boolean validateUser(String username, String password) {
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE "
                        + COLUMN_NAME + "='" + username + "'AND " + COLUMN_PASS + "='" + password + "'", null);
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean sameUser(String username){
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE "
                        + COLUMN_NAME + "='" + username  + "'" ,  null);
        if (c.getCount() > 0) {
            return true;
        }
        else{return false;}
    }
}
