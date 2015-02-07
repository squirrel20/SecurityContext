package me.liaosong.app.securitycontext.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * My SQLiteOpenHelper for set password.
 * Created by squirrel on 2015/2/7.
 */
public class PWSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "password";
    private static final String COLUMN_NAME_ID = "_id";
    private static final String COLUMN_NAME_PASSWORD = "password";
    private static final String COLUMN_NAME_PP_QUESTION = "pp_question"; // 密保问题
    private static final String COLUMN_NAME_PP_ANSWER = "pp_answer";    // 密保答案

    private static final String TABLE_CREATE = "create table " + TABLE_NAME + "(" + COLUMN_NAME_ID +
            " integer primary key autoincrement, " + COLUMN_NAME_PASSWORD + " text not null, " +
            COLUMN_NAME_PP_QUESTION + " text not null, " + COLUMN_NAME_PP_ANSWER +
            " text not null);";

    public PWSQLiteOpenHelper(Context context) {
        super(context, Constants.DataBaseConstants.DATABASE_NAME, null,
                Constants.DataBaseConstants.DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.d(PWSQLiteOpenHelper.class.getName(), "Create table " + TABLE_NAME);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.w(PWSQLiteOpenHelper.class.getName(), "Upgrading data from version " + oldVersion +
                " to " + newVersion + ", which will destroy all old data");
    }

    public void savePassword(
            SQLiteDatabase db, String password, String ppQuestion, String ppAnswer) {
        db.execSQL("DELETE FROM " + TABLE_NAME);

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_PASSWORD, password);
        contentValues.put(COLUMN_NAME_PP_QUESTION, ppQuestion);
        contentValues.put(COLUMN_NAME_PP_ANSWER, ppAnswer);

        db.insert(TABLE_NAME, null, contentValues);
        Log.d(PWSQLiteOpenHelper.class.getName(),
                "Save password, password protection question and password protection answer");
    }

    public Cursor getPassword(SQLiteDatabase db) {
        return db.query(TABLE_NAME, new String[]{COLUMN_NAME_PASSWORD}, null, null, null, null, null);
    }
}
