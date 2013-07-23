package pl.agiletrainers.android.frostyape;

import android.appwidget.*;
import android.content.*;
import android.widget.*;

public class WidgetProvider extends AppWidgetProvider {

    @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] apoWidgetIds) {
		
		ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		for (int widgetId : allWidgetIds) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
	
	
}
