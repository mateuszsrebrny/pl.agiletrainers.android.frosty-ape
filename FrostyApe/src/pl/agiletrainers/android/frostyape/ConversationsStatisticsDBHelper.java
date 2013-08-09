package pl.agiletrainers.android.frostyape;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import java.util.*;

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
		values.put(COLUMN_NUM_CONV, convStat.getNumConversations());
		values.put(COLUMN_NUM_UNREAD_CONV, convStat.getNumUnreadConversations());
		values.put(COLUMN_TIME_POINT_MILIS, convStat.getTimeMilis());
		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	
	public ArrayList<ConversationsStatistic> getAllStats() {
		ArrayList<ConversationsStatistic> allStats = new ArrayList<ConversationsStatistic>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + COLUMN_TIME_POINT_MILIS + " desc limit 150", null);
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			long timePointMilis = cursor.getLong(cursor.getColumnIndex(COLUMN_TIME_POINT_MILIS));
			int numConversations = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_CONV));
			int numUnreadConversations = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_UNREAD_CONV));
		    ConversationsStatistic convStat = new ConversationsStatistic(timePointMilis, numConversations, numUnreadConversations);
			allStats.add(convStat);
			cursor.moveToNext();
		}
		
		cursor.close();
		db.close();
		return allStats;
	}
	
}
