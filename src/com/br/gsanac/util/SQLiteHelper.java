package com.br.gsanac.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Arthur Carvalho
 * @since 06/09/2011
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private String[] scriptsSQLCreate;

    private String[] scritpsSQLDelete;

    public SQLiteHelper(Context context, String DBName, int DBVersion, String[] scriptsSQLCreate, String[] scritpsSQLDelete) {
        super(context, DBName, null, DBVersion);
        this.scriptsSQLCreate = scriptsSQLCreate;
        this.scritpsSQLDelete = scritpsSQLDelete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < this.scriptsSQLCreate.length; i++) {
            db.execSQL(this.scriptsSQLCreate[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = 0; i < this.scritpsSQLDelete.length; i++) {
            db.execSQL(this.scritpsSQLDelete[i]);
        }
        onCreate(db);
    }
}