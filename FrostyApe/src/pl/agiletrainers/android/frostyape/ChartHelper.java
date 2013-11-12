package pl.agiletrainers.android.frostyape;

import android.content.Context;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.chart.TimeChart;

public class ChartHelper {

	private XYSeries numConvSeries;
	private XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	private XYSeriesRenderer currentRenderer;
	private BitmappableGraphicalView chart;

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
	
	
	public BitmappableGraphicalView getChart(Context context, boolean widgetLookAndFeel) {
		
		initChart();
		
		TimeChart timeChart = new TimeChart(dataset, renderer);
		
		setLookAndFeelOptions(timeChart, widgetLookAndFeel);
		
		chart = new BitmappableGraphicalView(context, timeChart);
		return chart;
	}

	private void setLookAndFeelOptions(TimeChart timeChart, boolean widgetLookAndFeel) {
		if (!widgetLookAndFeel) 
			return;
		renderer.setShowLegend(false);
	}

}
