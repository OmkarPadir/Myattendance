package com.example.teproject.myattendance;

/**
 * Created by OMKAR on 26-08-2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class dbHandler extends SQLiteOpenHelper{
    private static String DATABASE_NAME="myattendance.db";
    private static final int DATABASE_VERSION=1;

    private static final String TAG="dbtag";

    private static String TABLE_NAME="logindetails";
    private static String COL1="_id";
    private static String COLts="tsflag";
    private static String COL2="email";


    public dbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String query = "create table "+TABLE_NAME+ "( " + COL1 + " integer primary key autoincrement , " + COL2 + " text , "
                    + COLts + " text  );";
            db.execSQL(query);

        }catch(Exception e)
        {
            Log.i(TAG,e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME+";");
        onCreate(db);

    }

    public void addRows(String email, String tsflag)
    {
        try {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COL2,email);
            contentValues.put(COLts,tsflag);

            db.insert(TABLE_NAME,null,contentValues);

        }catch (Exception e)
        { Log.i(TAG,e.getMessage());}
    }

    public String giveResult()
    {
        SQLiteDatabase db = getWritableDatabase();
        String dbResult="";
        try {
            Cursor c = db.rawQuery("Select * from "+TABLE_NAME+ ";" ,null);
            c.moveToFirst();

            while(!c.isAfterLast())
            {
                if(c.getString(c.getColumnIndex(COL2))!=null)
                {
                    dbResult += c.getString(c.getColumnIndex(COL2))+":"+c.getString(c.getColumnIndex(COLts));

                    c.moveToNext();
                }
            }
            c.close();
            db.close();
        }catch (Exception e)
        { Log.i(TAG,e.getMessage());}
        return dbResult;
    }


    public void deletedata()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}