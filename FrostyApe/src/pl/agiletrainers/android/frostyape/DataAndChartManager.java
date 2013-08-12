package pl.agiletrainers.android.frostyape;
import android.content.*;
import java.util.*;
import org.achartengine.*;

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

	public String createChartFromDB()
	{
		chart = chartHelper.getChart(context);
		return addDataFromDB();
	}


	private String addDataFromDB() {

		ArrayList<ConversationsStatistic> allStats = db.getAllStats();
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

			chartHelper.addConversationsStatistic(convStat);

		}
		return "size: "+ size + ", notNeeded: " + notNeededCount;
	}
	
}
