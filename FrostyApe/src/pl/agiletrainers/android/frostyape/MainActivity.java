package pl.agiletrainers.android.frostyape;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

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
		
    @Override
	public void onResume() {
		super.onResume();
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		try {
			dataAndChartManager.getChart().repaint();
		} catch (NullPointerException npe) {
			String log = dataAndChartManager.createChartFromDB(false);
			lolTextView.setText(log);		   
			layout.addView(dataAndChartManager.getChart());
		} 
	}

	public void onClick(View view) {
		
		String log = dataAndChartManager.updateWithCurrentStat();
		
		dataAndChartManager.getChart().repaint();
		lolButton.setText(log);

	}
	
}
