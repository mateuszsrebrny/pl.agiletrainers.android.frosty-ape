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

public class MainActivity extends Activity
{

	private XYSeries numConvSeries;
	private XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	private XYSeriesRenderer currentRenderer;
	
	private ConversationsStatisticsDBHelper db;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
	
    	lolButton = (Button) findViewById(R.id.lol_button);
	    lolTextView = (TextView) findViewById(R.id.lol_text_view);
		
		db = new ConversationsStatisticsDBHelper(getApplicationContext());
	}
	
	public void onResume() {
		super.onResume();
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		//logOnTextView("layout: "+layout);
		if (chart == null) {
		    initChart();
			addSampleData();
			chart = ChartFactory.getTimeChartView(this, dataset, renderer, null);
			layout.addView(chart);
		} else {
			chart.repaint();
		}
	}

	private void addSampleData()
	{
         //numConvSeries.add(1, 2);
		 //numConvSeries.add(2, 3);
	}

	private void initChart() {
		numConvSeries = new XYSeries("numCoversations");
		dataset.addSeries(numConvSeries);
		currentRenderer = new XYSeriesRenderer();
		renderer.addSeriesRenderer(currentRenderer);
		
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
					//String timeString = time.format("%s");
					
					double chartTime = time.toMillis(false);
					
			        lolButton.setText(timeString +" Inbox (unread/all): " + numUnreadConversations + " / " + numConversations);
					
					numConvSeries.add(chartTime, numConversations);
					chart.repaint();
				    break;
				}
			}
		}  catch (Exception e ){
		
	    	lolTextView.setText("Exception: " + e);
		}
	}
	
}
