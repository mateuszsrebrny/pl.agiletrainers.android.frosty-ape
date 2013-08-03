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
		logOnTextView("size: "+ size);
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
		
		GMailStatsRetriever gmailRetriever = new GMailStatsRetriever();
		ConversationsStatistic convStat = gmailRetriever.retrieve(this);

		String timeString = convStat.getTime().format("[%Y.%m.%d %H:%M]");
		int numConversations = convStat.getNumConversations();
		int numUnreadConversations = convStat.getNumUnreadConversations();

		chartHelper.addConversationsStatistic(convStat);
		chart.repaint();

		db.insertConversationsStatistic(convStat);

		lolButton.setText(timeString + " Inbox (unread/all): " + numUnreadConversations + " / " + numConversations);		

	}
	
}
