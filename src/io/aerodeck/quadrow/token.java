package io.aerodeck.quadrow;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class token extends View {
    Paint paint = new Paint();

    public token(Context context) {
        super(context);            
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        canvas.drawCircle(50, 50, 25, paint);
    }

}