package pl.agiletrainers.android.frostyape;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.graphics.*;
import android.text.format.*;
import android.widget.*;
import java.util.*;
import org.achartengine.*;

public class WidgetProvider extends AppWidgetProvider
{

    @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
	    DataAndChartManager dataAndChartManager = new DataAndChartManager(context);
	
	    String log = dataAndChartManager.updateWithCurrentStat();
	    log += "\n";
		
		log += dataAndChartManager.createChartFromDB();
		
		BitmappableGraphicalView chart = dataAndChartManager.getChart();
		Bitmap bitmap = Bitmap.createBitmap(340, 280, Bitmap.Config.RGB_565);
		chart.saveToBitmap(bitmap);
				
		for (int widgetId : allWidgetIds) {
		
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
			
			Intent intent = new Intent(context, WidgetProvider.class);
			
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); 
     		remoteViews.setOnClickPendingIntent(R.id.widget_chart,pendingIntent);
								
			remoteViews.setImageViewBitmap(R.id.widget_chart, bitmap);
					
			remoteViews.setTextViewText(R.id.widget_text_view, log);
			
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	   
	}

	
}
