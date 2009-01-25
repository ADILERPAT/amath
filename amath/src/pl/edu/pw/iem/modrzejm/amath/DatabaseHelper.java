package pl.edu.pw.iem.modrzejm.amath;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private String createTable;
	private String TAG ;
	private String databaseName;
	private String databaseTable;
	DatabaseHelper(Context context ,String databaseName ,int databaseVersion,String databaseTable,String createTable,String TAG) {
    	super(context, databaseName, null, databaseVersion);
    	this.databaseName = databaseName;
    	this.databaseTable = databaseTable;
    	this.createTable = createTable;
    	this.TAG = TAG;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data "+databaseTable);
        db.execSQL("DROP TABLE IF EXISTS "+databaseTable);
        onCreate(db);
    }
}
//01-25 17:14:25.904: ERROR/AndroidRuntime(20836): Caused by: android.database.sqlite.SQLiteException: near "set": syntax error: , while compiling: SELECT _id, expression, value, set FROM aMath
