package com.cuwordstudy.solomolaiye.wordstudyunit.Helpers;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class DbHelper extends SQLiteOpenHelper {
    private static String DB_PATH ;
    private static String DB_NAME = "KJV.db";
    private SQLiteDatabase kjvdb;
    private Context mcontext = null;


    private static int VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        if (Build.VERSION.SDK_INT >= 17) {

            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";

        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        mcontext = context;
    }
    private boolean checkdatabase() {
        SQLiteDatabase tempdb = null;
        try {
            String path = DB_PATH + DB_NAME;
            tempdb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLException e) {
        }
        if (tempdb != null) {
            tempdb.close();
        }            return tempdb != null ? true : false;

    }
    public void copydatabase(){
        try {
            InputStream myinput = mcontext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH+DB_NAME;
            OutputStream myoutput = new FileOutputStream(outputFileName);
            byte [] buffer = new  byte[1024];
            int length;
            while ((length=myinput.read(buffer))>0){
                myoutput.write(buffer,0,length);
            }
            myoutput.flush();
            myoutput.close();
            myinput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void opendatabase(){
        String path = DB_PATH+DB_NAME;
        kjvdb = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
    }
    public  void createdatabase (){
        boolean isdbExist = checkdatabase();
        if(isdbExist)
        {}
        else
        {
            this.getReadableDatabase();
            try {
                copydatabase();
            }catch (Exception ex){}
        }
    }


    @Override
    public synchronized void close() {
        if (kjvdb != null) {
            kjvdb.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}