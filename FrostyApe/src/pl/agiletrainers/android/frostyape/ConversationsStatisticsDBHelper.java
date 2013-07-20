package pl.agiletrainers.android.frostyape;

import android.database.sqlite.*;
import android.content.*;

public class ConversationsStatisticsDBHelper extends SQLiteOpenHelper
{
	public static String DATABASE_NAME = "ConversationsStatisticsDB";

	private static String TABLE_NAME = "comversations_statistics";
	
	public static String CREATE_SQL = 
	    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
		+ "( id INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ "time_point DATETIME, "
		+ "num_conversations INT, "
	+ "num_unread_conversations INT)";
	
	public ConversationsStatisticsDBHelper(Context context) {
		super(context, DATABASE_NAME, null, 33);
	}

	public void onCreate(SQLiteDatabase p1) {
		// TODO: Implement this method
	}

	public void onUpgrade(SQLiteDatabase p1, int p2, int p3) {

		// TODO: Implement this method
	}


}
