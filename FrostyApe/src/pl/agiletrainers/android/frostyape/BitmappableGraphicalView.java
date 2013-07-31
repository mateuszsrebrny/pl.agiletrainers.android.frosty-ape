package pl.agiletrainers.android.frostyape;
import org.achartengine.*;
import android.content.*;
import org.achartengine.chart.*;
import android.graphics.*;

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

