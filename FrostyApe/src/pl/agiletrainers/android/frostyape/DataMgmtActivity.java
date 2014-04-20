package pl.agiletrainers.android.frostyape;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.view.View;

public class DataMgmtActivity extends Activity
{

	private TextView outputTextView;

	private ConversationsStatisticsDBHelper db;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datamgmt);

	    outputTextView = (TextView) findViewById(R.id.output_text_view);		

		db = new ConversationsStatisticsDBHelper(this);
		
		output("Activity freshly creared");
		
	}
	
	
	protected void output(String s) {
		outputTextView.setText(s);
	}
	
	public void onClick(View view) {
	    String out = "button pressed!";
		out += " count: " + db.getCount();
		output(out);
	}
}
