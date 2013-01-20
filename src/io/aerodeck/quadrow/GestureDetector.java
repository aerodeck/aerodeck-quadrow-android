package io.aerodeck.quadrow;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;


class gestureDetector extends SimpleOnGestureListener {
   
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	FullscreenActivity main = new FullscreenActivity();
	
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX) {
        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            return false; // Right to left
        }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            //main.animate();
        	return false; // Left to right
        }
        return false;
    }
}