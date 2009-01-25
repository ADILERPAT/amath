package pl.edu.pw.iem.modrzejm.amath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ExpressionsDbAdapter {

    public static final String KEY_EXPRESSION = "expression";
    public static final String KEY_VALUE = "value";
    public static final String KEY_SAVED = "seaved";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "ExpressionsDbAdapter";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private final Context context;
    
    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "aMath";
    private static final int DATABASE_VERSION = 5;
    
    private static final String CREATE_TABLE =
        "create table "+ DATABASE_TABLE +" ("+ KEY_ROWID +" integer primary key autoincrement, "
                + KEY_EXPRESSION +" text not null, "+ KEY_VALUE +" text not null,"+KEY_SAVED+" text not null);";
    //01-25 17:18:43.964: ERROR/Database(22576): Failure 1 (near "set": syntax error) on 0x1aae10 when preparing 'create table aMath (_id integer primary key autoincrement, expression text not null, value text not null,set text not null);'.

    public ExpressionsDbAdapter(Context ctx) {
        this.context = ctx;
    }

    public ExpressionsDbAdapter open() throws SQLException {
        databaseHelper = new DatabaseHelper(context,DATABASE_NAME,DATABASE_VERSION,DATABASE_TABLE,CREATE_TABLE,TAG);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        databaseHelper.close();
    }

    public long insert(String expression, String value,String set) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_EXPRESSION, expression);
        initialValues.put(KEY_VALUE, value);
        if(set != null){
        	initialValues.put(KEY_SAVED, set);
        }else{
        	initialValues.put(KEY_SAVED, "previous");
        }

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
                KEY_VALUE,KEY_SAVED}, null, null, null, null, null);
    }


    public Cursor fetch(long rowId) throws SQLException {

        Cursor cursor = database.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                         				KEY_EXPRESSION, KEY_VALUE,KEY_SAVED}, KEY_ROWID + "=" + rowId, null,
                         				null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }

    public boolean update(long rowId, String expression, String value) {
        ContentValues args = new ContentValues();
        args.put(KEY_EXPRESSION, expression);
        args.put(KEY_VALUE, value);

        return database.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
