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

	private DataAndChartManager dataAndChartManager;

	private Button lolButton;
	private TextView lolTextView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
    	lolButton = (Button) findViewById(R.id.lol_button);
	    lolTextView = (TextView) findViewById(R.id.lol_text_view);
		
		dataAndChartManager = new DataAndChartManager(getApplicationContext());
		
	}
	

	public void onResume() {
		super.onResume();
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		//logOnTextView("layout: "+layout);
		try {
			dataAndChartManager.getChart().repaint();
		} catch (NullPointerException npe) {
			String log = dataAndChartManager.createChartFromDB();
			logOnTextView(log);		   
			layout.addView(dataAndChartManager.getChart());
		} 
	}


	public void logOnTextView (String s) {
		String buffer = lolTextView.getText() + ", " + s;
		lolTextView.setText(buffer);
	}
	
	
	public void onClick(View view) {
		
		String log = dataAndChartManager.updateDBAndChartWithGMail();
		
		dataAndChartManager.getChart().repaint();
		lolButton.setText(log);

	}
	
}
