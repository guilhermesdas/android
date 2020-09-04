package com.example.contatos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

// https://www.devmedia.com.br/trabalhando-com-singleton-java/23632
// Singleton
public class TaskDBHelper extends SQLiteOpenHelper {

    private static TaskDBHelper db = null;

    public static TaskDBHelper getInstance(Context context){
        if ( db == null ){
            db = new TaskDBHelper(context);
        }
        return db;
    }

    private TaskDBHelper(Context context){
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = String.format("CREATE TABLE %s (" + "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, %s TEXT, %s TEXT, %s TEXT)",
                TaskContract.TABLE, TaskContract.Columns._ID, TaskContract.Columns.NOME,
                TaskContract.Columns.TELEFONE, TaskContract.Columns.EMAIL, TaskContract.Columns.EMPRESA);
        Log.d("TaskDBHelper", "Query to create table: " + sqlQuery);
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE);
    }

}
