package pl.agiletrainers.android.frostyape;

import android.content.Context;
import org.achartengine.model.XYValueSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class ChartHelper {

	private XYValueSeries numConvSeries;
	private XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	private XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	private XYSeriesRenderer currentRenderer;
	private BitmappableGraphicalView chart;

    public ChartHelper() {
		
	}

	public void addConversationsStatistic(double x, ConversationsStatistic convStat)
	{
	    numConvSeries.add(x, convStat.getNumConversations(), convStat.getTimeMilis());
	}

	public void addConversationsStatistic(ConversationsStatistic convStat) {
		double maxX = numConvSeries.getMaxX();
		this.addConversationsStatistic(maxX, convStat);
	}
	

	private void initChart() {
		numConvSeries = new XYValueSeries("numCoversations");
		dataset.addSeries(numConvSeries);
		currentRenderer = new XYSeriesRenderer();
		renderer.addSeriesRenderer(currentRenderer);
		renderer.setYAxisMin(0);
		//renderer.setXRoundedLabels(true);
	}
	
	
	public BitmappableGraphicalView getChart(Context context, boolean widgetLookAndFeel) {
		
		initChart();
		
		LinearTimeChart linearTimeChart = new LinearTimeChart(dataset, renderer);
		
		setLookAndFeelOptions(linearTimeChart, widgetLookAndFeel);
		
		chart = new BitmappableGraphicalView(context, linearTimeChart);
		return chart;
	}

	private void setLookAndFeelOptions(LinearTimeChart timeChart, boolean widgetLookAndFeel) {
		renderer.setShowGrid(true);
		if (!widgetLookAndFeel) 
			return;
		renderer.setShowLegend(false);
	}

}
