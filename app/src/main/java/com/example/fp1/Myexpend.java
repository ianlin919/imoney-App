package com.example.fp1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Myexpend extends SQLiteOpenHelper
{
    private static final String name = "ddbase.db";
    private static final int version = 1;

    Myexpend(Context context)
    {
        super(context,name,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE myTable(money integer NOT NULL,reason text NOT NULL,year integer NOT NULL,month integer NOT NULL,day integer NOT NULL,seq integer PRIMARY KEY)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS myTable");
        onCreate(db);
    }
}