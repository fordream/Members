package org.tourstart.members;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDataBase extends SQLiteOpenHelper {

    public myDataBase(Context context) {
        super(context, "myDataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table myTable ("
                + "id integer primary key autoincrement,"
                + "firstName text,"
                + "lastName text,"
                + "address text,"
                + "gender text,"
                + "birthDay text,"
                + "location text" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
