package pl.agiletrainers.android.frostyape;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private DataAndChartManager dataAndChartManager;

	private Button getDataButton;
	private TextView logTextView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
    	getDataButton = (Button) findViewById(R.id.get_data_button);
	    logTextView = (TextView) findViewById(R.id.log_text_view);
		
		dataAndChartManager = new DataAndChartManager(getApplicationContext());
		
	}
		
    @Override
	public void onResume() {
		super.onResume();
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		try {
			dataAndChartManager.getChart().repaint();
		} catch (NullPointerException npe) {
			String log = dataAndChartManager.createChartFromDB(false);
			logTextView.setText(log);		   
			layout.addView(dataAndChartManager.getChart());
		} 
	}

	public void onClick(View view) {
		
		String log = dataAndChartManager.updateWithCurrentStat();
		
		dataAndChartManager.getChart().repaint();
		getDataButton.setText(log);

	}
	
}
