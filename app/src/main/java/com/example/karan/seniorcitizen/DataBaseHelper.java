package com.example.karan.seniorcitizen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*Notes
*   - By Karan Nagpal
*
*   This class uses file handling to create the database in the appropriate folder(/data/data/dbName/databases)
*   and the database is fetched from db file in assets folder.
*
*   Contains a method that returns Cursor and a method to add something in database.
*
* */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase sqliteDb;
    private static DataBaseHelper instance;
    private static final int DATABASE_VERSION = 1;

    private Context context;
    static Cursor cursor = null;

    DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                   int version)
    {
        super(context, name, factory, version);
        this.context = context;

    }

    private static void initialize(Context context, String databaseName)
    {
        if (instance == null)
        {

            /*If you want to create a new Database every time the app launches,don't check for the database */
            if (!checkDatabase(context, databaseName))
            {

                try {
                    copyDataBase(context, databaseName);
                } catch (IOException e) {

                    System.out.println( databaseName
                            + " does not exists ");
                }
            }

            instance = new DataBaseHelper(context, databaseName, null,
                    DATABASE_VERSION);
            sqliteDb = instance.getWritableDatabase();

            System.out.println("instance of  " + databaseName + " created ");
        }
    }

    public static final DataBaseHelper getInstance(Context context,String databaseName)
    {
        initialize(context, databaseName);
        return instance;
    }

    public static final DataBaseHelper getInstance2(Context context,String databaseName)
    {
        instance = new DataBaseHelper(context, databaseName, null,DATABASE_VERSION);
        sqliteDb = instance.getWritableDatabase();

        System.out.println("instance of  " + databaseName + " created ");
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return sqliteDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void copyDataBase(Context aContext, String databaseName)
            throws IOException {

        InputStream myInput = aContext.getAssets().open(databaseName);

        String outFileName = getDatabasePath(aContext, databaseName);

        File f = new File("/data/data/" + aContext.getPackageName()
                + "/databases/");
        if (!f.exists())
            f.mkdir();

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

        System.out.println(databaseName + " copied");
    }

    public static boolean checkDatabase(Context aContext, String databaseName)
    {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = getDatabasePath(aContext, databaseName);

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

            checkDB.close();
        } catch (SQLiteException e) {

            System.out.println(databaseName + " does not exists");
            Log.i("DatabaseHelper","->checkDatabase "+databaseName + " does not exists");
        }

        return checkDB != null ? true : false;
        //return false;
    }

    private static String getDatabasePath(Context aContext, String databaseName)
    {
        return "/data/data/" + aContext.getPackageName() + "/databases/"
                + databaseName;
    }

    public static Cursor rawQuery(String query)
    {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            cursor = null;
            cursor = sqliteDb.rawQuery(query, null);
            Log.i("rawQuery method","no error");
        } catch (Exception e) {
            Log.i("rawQuery method","yes some error");
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
        return cursor;
    }

    public static void execute(String query) {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);
        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
    }
}

