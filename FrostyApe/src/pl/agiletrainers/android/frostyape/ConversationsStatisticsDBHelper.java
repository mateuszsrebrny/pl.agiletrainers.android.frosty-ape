package pl.agiletrainers.android.frostyape;

import android.database.sqlite.*;
import android.content.*;

public class ConversationsStatisticsDBHelper extends SQLiteOpenHelper
{
	public static String DATABASE_NAME = "ConversationsStatisticsDB";

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
