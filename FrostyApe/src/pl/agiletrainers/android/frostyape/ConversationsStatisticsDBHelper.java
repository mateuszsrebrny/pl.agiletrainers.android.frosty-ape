package pl.agiletrainers.android.frostyape;

import android.database.sqlite.*;
import android.content.*;

public class ConversationsStatisticsDBHelper extends SQLiteOpenHelper
{
	public static String DATABASE_NAME = "ConversationsStatisticsDB";
    public static int DATABASE_VERSION = 1;
    
	private static String TABLE_NAME = "conversations_statistics";
	
	private static String COLUMN_ID = "_id";
	private static String COLUMN_NUM_CONV = "num_conversations";
	private static String COLUMN_NUM_UNREAD_CONV = "num_unread_converdations";
	private static String COLUMN_TIME_POINT_MILIS = "time_point_milis";
	
	public static String DB_CREATE_SQL = 
	    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "
		+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ COLUMN_TIME_POINT_MILIS + " INT, "
		+ COLUMN_NUM_CONV + " INT, "
    	+ COLUMN_NUM_UNREAD_CONV + " INT)";
	
	public ConversationsStatisticsDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

    public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE_SQL);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public void insertConversationsStatistic(ConversationsStatistic convStat) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put(
		// todo insert
	}
	
}
