package pl.agiletrainers.android.frostyape;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;
import android.text.format.*;

public class WidgetProvider extends AppWidgetProvider {

    @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		String textToDisplay = ":D ";
		ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		for (int widgetId : allWidgetIds) {
		
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
			
			Intent intent = new Intent(context, WidgetProvider.class);
			
			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); 
			remoteViews.setOnClickPendingIntent(R.id.widget_text_view,pendingIntent);
						
			Time time = new Time();
			time.setToNow();
			textToDisplay += ", " + widgetId + ": " + time.format2445();
			remoteViews.setTextViewText(R.id.widget_text_view, textToDisplay);
			
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	
	}
	
	
}
