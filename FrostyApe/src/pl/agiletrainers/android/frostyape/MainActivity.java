package pl.agiletrainers.android.frostyape;

import android.accounts.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.text.format.*;
import android.view.*;
import android.widget.*;
import org.achartengine.*;
import org.achartengine.model.*;
import org.achartengine.renderer.*;
import java.util.*;

public class MainActivity extends Activity
{


	
	private ConversationsStatisticsDBHelper db;
	
	private ChartHelper chartHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
	
    	lolButton = (Button) findViewById(R.id.lol_button);
	    lolTextView = (TextView) findViewById(R.id.lol_text_view);
		
		chartHelper = new ChartHelper();
		db = new ConversationsStatisticsDBHelper(getApplicationContext());
	}
	private void addDataFromDB() {

		ArrayList<ConversationsStatistic> allStats = db.getAllStats();
		int size = allStats.size();
		for (int i = 0; i < size ; ++i) {
			ConversationsStatistic convStat = allStats.get(i);
			chartHelper.addConversationsStatistic(convStat);
		}
		//logOnTextView("size: "+ size);
		//numConvSeries.add(1, 2);
		//numConvSeries.add(2, 3);
	}

	public void onResume() {
		super.onResume();
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		//logOnTextView("layout: "+layout);
		if (chart == null) {
		    chart = chartHelper.getChart(this);
			addDataFromDB();
			layout.addView(chart);
		} else {
			chart.repaint();
		}
	}


	
	private Button lolButton;
	private TextView lolTextView;
	private GraphicalView chart;
	
	public void logOnTextView (String s) {
		String buffer = lolTextView.getText() + ", " + s;
		lolTextView.setText(buffer);
	}
	
	final String ACCOUNT_TYPE_GOOGLE = "com.google";
	
	public void onClick(View view) {
		
		try {
			
			AccountManager accountManager = AccountManager.get(this);
			final Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE_GOOGLE);
            String accountName = accounts[0].name;
			//logOnTextView("Account: " + accountName);
			
			
			
		    Cursor labelsCursor = null;
		    ContentResolver contentResolver = getContentResolver();
		    String labelsUriString = "content://" + "com.google.android.gm/" + accountName + "/labels";
		    Uri labelsUri = Uri.parse(labelsUriString);
		
		    labelsCursor = contentResolver.query(labelsUri, null, null, null, null);
			//logOnTextView("labelsCursor: " + labelsCursor);
			
			final int canonicalNameIndex = labelsCursor.getColumnIndexOrThrow("canonicalName");
			final int numConversationsIndex = labelsCursor.getColumnIndexOrThrow("numConversations");
			final int numUnreadConversationsIndex = labelsCursor.getColumnIndexOrThrow("numUnreadConversations");
			while (labelsCursor.moveToNext()) {
				String labelName = labelsCursor.getString(canonicalNameIndex);
				if (labelName.equals("^i")) {
					int numConversations = labelsCursor.getInt(numConversationsIndex);
					int numUnreadConversations = labelsCursor.getInt(numUnreadConversationsIndex);
					
					Time time = new Time();
					time.setToNow();
					
					String timeString = time.format("[%Y.%m.%d %H:%M]");
					
			        lolButton.setText(timeString +" Inbox (unread/all): " + numUnreadConversations + " / " + numConversations);
					ConversationsStatistic convStat = new ConversationsStatistic(time, numConversations, numUnreadConversations);
					chartHelper.addConversationsStatistic(convStat);
					chart.repaint();
					
					db.insertConversationsStatistic(convStat);
					
				    break;
				}
			}
		}  catch (Exception e ){
		
	    	lolTextView.setText("Exception: " + e);
		}
	}
	
}
