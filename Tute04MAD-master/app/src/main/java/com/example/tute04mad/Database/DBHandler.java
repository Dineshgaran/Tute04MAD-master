package com.example.tute04mad.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.tute04mad.Database.UsersMaster.Users.TABLE_NAME;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHandler(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        UsersMaster.Users._ID + " INTEGER PRIMARY KEY," +
                        UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                        UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";
        // Use the details from the UsersMaster and Users classes we created. Specify the primary key from the BaseColumns interface.

        db.execSQL(SQL_CREATE_ENTRIES);  // This will execute the contents of SQL_CREATE_ENTRIES
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addInfo(String userName, String password) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names the keys
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, userName);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
        if(newRowId >= 1)
            return true;
        else
            return false;
    }


    // select all users
    public List readAllInfo()
    {
        SQLiteDatabase db = getReadableDatabase();

        // define a projection that specifieswhich columns from the database
        // you will actually use after this query
        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };
        //Filter results WHERE "userName" = 'SLIIT USER'
        // String selection = Users.COLUMN_NAME_USERNAME + " = ?";
        //String[] selectionArgs = {""};

        // How you want the results sorted in the resulting cursor
        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,           // the table to query
                projection,                 // the columns to return
                null,               // the columns for the WHERE clause
                null,            // the values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                sortOrder                  // the sort order
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString( cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString( cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        cursor.close();
        return userNames;
    }

    // search the given user
    public boolean readInfo(String uName, String pwd)
    {
        SQLiteDatabase db = getReadableDatabase();

        // define a projection that specifieswhich columns from the database
        // you will actually use after this query
        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        //Filter results WHERE "userName" = 'SLIIT USER'
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " = ?" + " AND " + UsersMaster.Users.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {uName, pwd};

        // How you want the results sorted in the resulting cursor
        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,           // the table to query
                projection,                 // the columns to return
                selection,               // the columns for the WHERE clause
                selectionArgs,            // the values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                sortOrder                  // the sort order
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString( cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString( cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(username);
            passwords.add(password);
        }
        if (cursor.getCount() == 0)
            return false;
        else
            return true;
        // cursor.close();

    }

    //This will delete a particular user from the table
    public void deleteInfo(String userName){
        SQLiteDatabase db = getReadableDatabase();
        //Define 'where' part of query
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        //Specify arguments n placeholder order
        String[] selectionArgs = { userName };
        //Issue SQL statement
        db.delete(TABLE_NAME, selection, selectionArgs);

    }

    public boolean updateInfo(String userName, String password) {
        SQLiteDatabase db = getReadableDatabase();

        //New value for one column
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        //Which row to update, based on the title
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {userName};

        int count = db.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        if(count >= 1)
            return true;
        else
            return false;
    }
}
