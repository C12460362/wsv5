package com.google.ryan.walkstar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.SequenceInputStream;

/**
 * Created by ryan on 02/02/2017.
 */
public class DBChallengeHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Walkstar.db";
    public static final String TABLE_NAME="Steps_table";
    public static final String COL_1="C_ID";
    public static final String COL_2="C_NAME";
    public static final String COL_3="C_DESC";
    public static final String COL_4="C_STEPS";
    public static final String COL_5="C_DAYS";

    public DBChallengeHelper(Context context) {
        super(context,DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(C_ID INTEGER PRIMARY KEY AUTOINCREMENT,C_NAME TEXT," +
                "C_DESC TEXT,C_STEPS TEXT,C_DAYS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String desc,String steps,String days){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,desc);
        contentValues.put(COL_4,steps);
        contentValues.put(COL_5,days);
        long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;

    }



    public boolean updateData(String id,String name, String desc,String steps, String days) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,desc);
        contentValues.put(COL_4,steps);
        contentValues.put(COL_5,days);
        db.update(TABLE_NAME, contentValues, "C_ID = ?",new String[] { id });
        return true;
    }

    public boolean deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
        db.execSQL("delete from sqlite_sequence where name='Steps_table';");
        return true;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select C_ID,C_NAME,C_STEPS from " + TABLE_NAME ,null);
        return res;

    }

    public Cursor getChallenge1(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select C_NAME,C_STEPS from " + TABLE_NAME + " WHERE C_ID = 1;",null);
        return res;

    }

    public Cursor getChallenge2(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select C_NAME,C_STEPS from " + TABLE_NAME + " WHERE C_ID = 2;",null);
        return res;

    }

    public Cursor getChallenge3(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select C_NAME,C_STEPS from " + TABLE_NAME + " WHERE C_ID = 3;",null);
        return res;

    }

    public Cursor getSteps1(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select C_STEPS from " + TABLE_NAME + " WHERE C_ID = 1;",null);
        return res;
    }

    public Cursor getSteps2(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select C_STEPS from " + TABLE_NAME + " WHERE C_ID = 2;",null);
        return res;
    }

    public Cursor getSteps3(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select C_STEPS from " + TABLE_NAME + " WHERE C_ID = 3;",null);
        return res;
    }
}