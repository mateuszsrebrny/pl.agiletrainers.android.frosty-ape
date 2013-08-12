package pl.agiletrainers.android.frostyape;

import org.achartengine.GraphicalView;

import android.content.Context;

import org.achartengine.chart.AbstractChart;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BitmappableGraphicalView extends GraphicalView 
{
   public BitmappableGraphicalView(Context context, AbstractChart abstractChart) {
	   super(context, abstractChart);
   }
   
   public void saveToBitmap(Bitmap bitmap) {
	   Canvas canvas = new Canvas(bitmap);
	   onDraw(canvas);
   }
}

