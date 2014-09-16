package pl.agiletrainers.android.frostyape;
import java.util.List;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.model.*;
import java.util.*;
import android.graphics.*;
import java.text.*;

public class LinearTimeChart extends TimeChart {
	
	/** The constant to identify this chart type. */
	public static final String TYPE = "LinearTime";
	
	/** The starting point for labels. */
	private Double mStartPoint;


	
	
	
	/**
	 * Builds a new time chart instance.
	 * 
	 * @param dataset the multiple series dataset
	 * @param renderer the multiple series renderer
	 */
	public LinearTimeChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
		super(dataset, renderer);
	}

	/**
	 * Returns the chart type identifier.
	 * 
	 * @return the chart type
	 */
	public String getChartType() {
		return TYPE;

	}

	/**

	 * The graphical representation of the labels on the X axis.

	 * 

	 * @param xLabels the X labels values

	 * @param xTextLabelLocations the X text label locations

	 * @param canvas the canvas to paint to

	 * @param paint the paint to be used for drawing

	 * @param left the left value of the labels area

	 * @param top the top value of the labels area

	 * @param bottom the bottom value of the labels area

	 * @param xPixelsPerUnit the amount of pixels per one unit in the chart labels

	 * @param minX the minimum value on the X axis in the chart

	 * @param maxX the maximum value on the X axis in the chart

	 */

	@Override
	protected void drawXLabels(List<Double> xLabels, Double[] xTextLabelLocations, Canvas canvas, Paint paint, int left, int top, int bottom, double xPixelsPerUnit, double minX, double maxX) {

		int length = xLabels.size();

		if (length > 0) {

			boolean showLabels = mRenderer.isShowLabels();

			boolean showGridY = mRenderer.isShowGridY();

			DateFormat format = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM);

			
			
			//super.getDateFormat(xLabels.get(0), xLabels.get(length - 1));

			for (int i = 0; i < length; i++) {

				long label = Math.round(xLabels.get(i));

				float xLabel = (float) (left + xPixelsPerUnit * (label - minX));

				if (showLabels) {

					paint.setColor(mRenderer.getXLabelsColor());
					canvas.drawLine(xLabel, bottom, xLabel, bottom + mRenderer.getLabelsTextSize() / 3, paint);

					XYValueSeries valueSeries = (XYValueSeries)mDataset.getSeriesAt(0);
					//int index = valSeries.getIndexForKey(xLabels.get(i));
					//double val = valSeries.getValue(index);
					long realLabel = label;
					int xCount = valueSeries.getItemCount();
					for (int ii = 0 ; ii < xCount ; ++ii) {
						double x = valueSeries.getX(ii);
						double v = valueSeries.getValue(ii);
						if (x >= label) {
							realLabel = Math.round(v);
							ii = xCount;
						}
					}
				
					
					
					
					//long realLabel = label+100000000;
					
					
					
					drawText(canvas, format.format(new Date(realLabel)), xLabel,
                             bottom + mRenderer.getLabelsTextSize() * 4 / 3 + mRenderer.getXLabelsPadding(), paint, mRenderer.getXLabelsAngle());

				}

				if (showGridY) {

					paint.setColor(mRenderer.getGridColor());

					canvas.drawLine(xLabel, bottom, xLabel, top, paint);

				}

			}

		}

		drawXTextLabels(xTextLabelLocations, canvas, paint, true, left, top, bottom, xPixelsPerUnit, minX, maxX);

	}


	
	
	
	
/*
	@Override
    protected List<Double> getXLabels(double min, double max, int count) {
		List<Double> xLabels = super.getXLabels(min, max, count);
		
		XYValueSeries valueSeries = (XYValueSeries)mDataset.getSeriesAt(0);
		
		
		List<Double> result = new ArrayList<Double>();

		
		
		
		for (Double xLabel : xLabels) {
			
			int xCount = valueSeries.getItemCount();
			for (int i = 0 ; i < xCount ; ++i) {
				double x = valueSeries.getX(i);
				double v = valueSeries.getValue(i);
				if (v >= xLabel.doubleValue()) {
			    	//result.add(v);
					i = xCount;
				}
			}
			result.add(xLabel.doubleValue()+1000);
			//int index = valueSeries.getIndexForKey(Math.round(x));
			//double value = valueSeries.getValue(index);
			//result.add(valueSeries.getValue(index));
		}
		
		return result;
	}
*/
/*
	@Override
	protected List<Double> getXLabels(double min, double max, int count) {
        final List<Double> result = new ArrayList<Double>();
		if (!mRenderer.isXRoundedLabels()) {
			if (mDataset.getSeriesCount() > 0) {
				XYValueSeries series = (XYValueSeries) mDataset.getSeriesAt(0);
				int length = series.getItemCount();

				int intervalLength = 0;

				int startIndex = -1;

				for (int i = 0; i < length; i++) {

					double value = series.getX(i);

					if (min <= value && value <= max) {

						intervalLength++;

						if (startIndex < 0) {

							startIndex = i;

						}

					}

				}

				if (intervalLength < count) {

					for (int i = startIndex; i < startIndex + intervalLength; i++) {

						result.add(series.getValue(i));

					}

				} else {

					float step = (float) intervalLength / count;

					int intervalCount = 0;

					for (int i = 0; i < length && intervalCount < count; i++) {

						double value = series.getX(Math.round(i * step));

						if (min <= value && value <= max) {

							result.add(series.getValue(Math.round(i*step)));

							intervalCount++;

						}

					}

				}

				return result;

			} else {

				return super.getXLabels(min, max, count);

			}

		}

		if (mStartPoint == null) {

			mStartPoint = min - (min % DAY) + DAY + new Date(Math.round(min)).getTimezoneOffset() * 60 * 1000;

		}

		if (count > 25) {

			count = 25;

		}


		final double cycleMath = (max - min) / count;

		if (cycleMath <= 0) {

			return result;

	    }

		double cycle = DAY;


		if (cycleMath <= DAY) {

			while (cycleMath < cycle / 2) {

				cycle = cycle / 2;

			}

		} else {

			while (cycleMath > cycle) {

				cycle = cycle * 2;

			}

		}


		double val = mStartPoint - Math.floor((mStartPoint - min) / cycle) * cycle;

		int i = 0;

		XYValueSeries series = (XYValueSeries)mDataset.getSeriesAt(0);
		
		while (val < max && i++ <= count) {

			result.add(val);
            //result.add(series.getValue(Integer.parseInt(""+ Math.round(val))));
			val += cycle;

		}


		return result;

	}
*/
	
}
