package pl.agiletrainers.android.frostyape;

import android.appwidget.*;
import android.content.*;
import android.widget.*;

public class WidgetProvider extends AppWidgetProvider {

    @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		String textToDisplay = ":D ";
		ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		for (int widgetId : allWidgetIds) {
			textToDisplay += ", " + widgetId;
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		
			remoteViews.setTextViewText(R.id.widget_text_view, textToDisplay);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	
	}
	
	
}
