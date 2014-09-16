package pl.agiletrainers.android.frostyape;
import android.content.Context;
import java.util.ArrayList;
import java.util.*;

public class DataAndChartManager
{

	private Context context;

	private ChartHelper chartHelper;

	private ConversationsStatisticsDBHelper db;

	private BitmappableGraphicalView chart;

    public DataAndChartManager(Context context) {
		this.context = context;
		chartHelper = new ChartHelper();
		db = new ConversationsStatisticsDBHelper(context);
        chart = null;
	}

	public String updateWithCurrentStat() {
		GMailStatsRetriever gmailRetriever = new GMailStatsRetriever();
		ConversationsStatistic convStat = gmailRetriever.retrieve(context);

		String timeString = convStat.getTime().format("[%Y.%m.%d %H:%M]");
		int numConversations = convStat.getNumConversations();
		int numUnreadConversations = convStat.getNumUnreadConversations();

		db.insertConversationsStatistic(convStat);

		try {
			chartHelper.addConversationsStatistic(convStat);
		} catch (NullPointerException npe) {}		
		
		return timeString + " Inbox (unread/all): " + numUnreadConversations + " / " + numConversations;	
		
	}
	
	public BitmappableGraphicalView getChart()
	{
		return chart;
	}

	public String createChartFromDB(boolean widgetLookAndFeel)
	{
		chart = chartHelper.getChart(context, widgetLookAndFeel);
		return addDataFromDB();
	}


	private String addDataFromDB() {

		ArrayList<ConversationsStatistic> allStats = db.getAllStats();
		
		//Sorting 
		Collections.sort(allStats, new Comparator<ConversationsStatistic>() { 
		                                @Override 
										public int compare(ConversationsStatistic c1, ConversationsStatistic c2) { 
										    return Long.compare(c1.getTimeMilis(), c2.getTimeMilis()); 
									    }
									});
									
		int size = allStats.size();
		
		ConversationsStatistic prevStat = null;
		ConversationsStatistic prevPrevStat = null;
		int notNeededCount = 0;

		for (int i = 0; i < size ; ++i) {
			ConversationsStatistic convStat = allStats.get(i);

			try {

				prevStat = allStats.get(i-1);
				prevPrevStat = allStats.get(i+1);
				if (prevPrevStat.getNumConversations() == prevStat.getNumConversations()
					&& prevStat.getNumConversations() == convStat.getNumConversations()) {

					notNeededCount++;
					continue;
				}

			} catch (Exception e) {

			}

			chartHelper.addConversationsStatistic(Double.parseDouble(""+i), convStat);

		}
		return "size: "+ size + ", notNeeded: " + notNeededCount;
	}
	
}
