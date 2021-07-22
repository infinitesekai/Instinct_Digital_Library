package com.example.digital_library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String DBNAME = "LibraryDB.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);

    }

}