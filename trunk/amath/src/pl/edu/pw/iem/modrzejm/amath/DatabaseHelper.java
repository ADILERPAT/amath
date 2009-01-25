package pl.edu.pw.iem.modrzejm.amath;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private String createTable;
	private String TAG ;
	DatabaseHelper(Context context ,String databaseName ,int databaseVersion,String createTable,String TAG) {
    	super(context, databaseName, null, databaseVersion);
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
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }
}