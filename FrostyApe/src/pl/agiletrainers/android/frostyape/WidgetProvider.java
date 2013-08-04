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

    private int convStatsCountForDebug;

    @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	
       	ConversationsStatisticsDBHelper db = new ConversationsStatisticsDBHelper(context);
		GMailStatsRetriever gmailRetriever = new GMailStatsRetriever();
		ConversationsStatistic convStat = gmailRetriever.retrieve(context);
		db.insertConversationsStatistic(convStat);
				
		ChartHelper chartHelper = new ChartHelper();
		BitmappableGraphicalView chart = chartHelper.getChart(context);

		addDataFromDB(db, chartHelper);
		
		String textToDisplay = getStatusToDisplay(convStat);
		
		for (int widgetId : allWidgetIds) {
		
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
			
			Intent intent = new Intent(context, WidgetProvider.class);
			
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); 
     		remoteViews.setOnClickPendingIntent(R.id.widget_chart,pendingIntent);
						
			
			Bitmap bitmap = Bitmap.createBitmap(340, 280, Bitmap.Config.ARGB_8888);
			
			chart.saveToBitmap(bitmap);
			
			remoteViews.setImageViewBitmap(R.id.widget_chart, bitmap);
			
			
		
			remoteViews.setTextViewText(R.id.widget_text_view, textToDisplay);
			
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	   
	}

	private String getStatusToDisplay(ConversationsStatistic convStat)	{		
		    
		Time time = new Time();
		time.setToNow();
		
		String status = time.format("[%Y.%m.%d %H:%M]\n");
		
		status += "Inbox (unread/all): ";
		status += convStat.getNumUnreadConversations();
		status += " / " + convStat.getNumConversations();
		status += ", dbSize: " + convStatsCountForDebug;
		return status;
	}
	
	
	


	private void addDataFromDB(ConversationsStatisticsDBHelper db, ChartHelper chartHelper) {

		ArrayList<ConversationsStatistic> allStats = db.getAllStats();
		int size = allStats.size();
		for (int i = 0; i < size ; ++i) {
			ConversationsStatistic convStat = allStats.get(i);
			chartHelper.addConversationsStatistic(convStat);
		}
		convStatsCountForDebug = size;
		//logOnTextView("size: "+ size);
		
	}
	
	
	
	
}
