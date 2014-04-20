package pl.agiletrainers.android.frostyape;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.view.View;

public class DataMgmtActivity extends Activity
{

	private TextView outputTextView;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datamgmt);

	    outputTextView = (TextView) findViewById(R.id.output_text_view);		

		output("Activity freshly creared");
		
	}
	
	
	protected void output(String s) {
		outputTextView.setText(s);
	}
	
	public void onClick(View view) {
		output("button pressed!");
	}
}
