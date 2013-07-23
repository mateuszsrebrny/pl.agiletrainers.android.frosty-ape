package pl.agiletrainers.android.frostyape;

import android.content.*;
import java.util.*;
import org.achartengine.*;
import org.achartengine.model.*;
import org.achartengine.renderer.*;

public class ChartHelper {

	private XYSeries numConvSeries;
	private XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	private XYSeriesRenderer currentRenderer;
	private GraphicalView chart;

    public ChartHelper() {
		
	}

	public void addConversationsStatistic(ConversationsStatistic convStat) {
		numConvSeries.add(convStat.getTimeMilis(), convStat.getNumConversations());
	}
	

	private void initChart() {
		numConvSeries = new XYSeries("numCoversations");
		dataset.addSeries(numConvSeries);
		currentRenderer = new XYSeriesRenderer();
		renderer.addSeriesRenderer(currentRenderer);
		renderer.setYAxisMin(0);
	}
	
	
	public GraphicalView getChart(Context context) {
		
		initChart();
		chart = ChartFactory.getTimeChartView(context, dataset, renderer, null);
		return chart;
	}

}
