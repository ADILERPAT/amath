package pl.edu.pw.iem.modrzejm.amath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ExpressionsDbAdapter {

    public static final String KEY_EXPRESSION = "expression";
    public static final String KEY_VALUE = "value";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "ExpressionsDbAdapter";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private final Context context;
    
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "aMath";
    private static final int DATABASE_VERSION = 2;
    
    private static final String CREATE_TABLE =
        "create table "+ DATABASE_TABLE +" ("+ KEY_ROWID +" integer primary key autoincrement, "
                + KEY_EXPRESSION +" text not null, "+ KEY_VALUE +" text not null);";
    
    public ExpressionsDbAdapter(Context ctx) {
        this.context = ctx;
    }

    public ExpressionsDbAdapter open() throws SQLException {
        databaseHelper = new DatabaseHelper(context,DATABASE_NAME,DATABASE_VERSION,CREATE_TABLE,TAG);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        databaseHelper.close();
    }

    public long insert(String expression, String value) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_EXPRESSION, expression);
        initialValues.put(KEY_VALUE, value);

        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean delete(long rowId) {

        return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean deleteAll(){
    	return database.delete(DATABASE_TABLE, null, null) > 0;
    }
    
    
    public Cursor fetchAll() {

        return database.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_EXPRESSION,
                KEY_VALUE}, null, null, null, null, null);
    }


    public Cursor fetch(long rowId) throws SQLException {

        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                         				KEY_EXPRESSION, KEY_VALUE}, KEY_ROWID + "=" + rowId, null,
                         				null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public boolean update(long rowId, String expression, String value) {
        ContentValues args = new ContentValues();
        args.put(KEY_EXPRESSION, expression);
        args.put(KEY_VALUE, value);

        return database.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
