package io.aerodeck.quadrow;

import io.aerodeck.quadrow.util.SystemUiHider;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class FullscreenActivity extends Activity {
    
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final boolean TOGGLE_ON_CLICK = true;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    private SystemUiHider mSystemUiHider;
    float[] directionx = {0,0};
	float[] directiony = {0,0};
    static int i = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });
        
        findViewById(R.id.new_game).setOnTouchListener(mDelayHideTouchListener);
        final Button newGameButton = (Button) findViewById(R.id.new_game);
    	final Button loginButton = (Button) findViewById(R.id.login);
    	
    	newGameButton.setOnClickListener(
    			new View.OnClickListener()
    	        {
    	            public void onClick(View view){
    	            	setContentView(R.layout.game_board);
    	            	newGame();
    	            }
    	        });
    	loginButton.setOnClickListener(
    			new View.OnClickListener()
    	        {
    	            public void onClick(View view){
    	            	setContentView(R.layout.login);
    	            	login();
    	            }
    	        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
    }

    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    
    public void login(){
    	final Button submitButton = (Button) findViewById(R.id.submit);
    	final Button backButton = (Button) findViewById(R.id.backButton);
    	final EditText fullNameText = (EditText) findViewById(R.id.name);
    	final EditText emailText = (EditText) findViewById(R.id.email);
    	final EditText usernameText = (EditText) findViewById(R.id.username);
    	final EditText passwordText = (EditText) findViewById(R.id.password);
    	final EditText confirmPasswordText = (EditText) findViewById(R.id.confirm_password);
    	
    	submitButton.setOnClickListener(
    			new View.OnClickListener()
    	        {
    	            public void onClick(View view){
    	            	String fullName = fullNameText.getText().toString();
    	            	String email = emailText.getText().toString();
    	            	String username = usernameText.getText().toString();
    	            	String password = passwordText.getText().toString();
    	            	String confirmPassword = confirmPasswordText.getText().toString();
    	            	login.main(fullName, username, email, password, confirmPassword);
    	            }
    	        });
    	backButton.setOnClickListener(
    			new View.OnClickListener()
    	        {
    	            public void onClick(View view){
    	            	setContentView(R.layout.activity_fullscreen);
    	            	onCreate(null);
    	            }
    	        });
    }
    
	public void newGame(){
    	final Button backButton = (Button) findViewById(R.id.backButton);
    	final Button rightButton = (Button) findViewById(R.id.rightButton);
    	final Button leftButton = (Button) findViewById(R.id.leftButton);
    	final ImageView chip = (ImageView) findViewById(R.id.chip);
    	final ImageView board = (ImageView) findViewById(R.id.board);
    	
        chip.setImageResource(R.raw.red);
        board.setImageResource(R.raw.quadrow_board);
        
        //Deprecated methods used to support prior versions of Android.
        Display display = getWindowManager().getDefaultDisplay(); 
        final int width = display.getWidth();
        final int height = display.getHeight();
        //final float center = width/2;
		final float distance = width/7;
		i = 0;
        
        backButton.setOnClickListener(
    			new View.OnClickListener()
    	        {
    	            public void onClick(View view){
    	            	setContentView(R.layout.activity_fullscreen);
    	            	onCreate(null);
    	            }
    	        });
        
        rightButton.setOnClickListener(
			new View.OnClickListener()
        	{
            	public void onClick(View view){
            		if(i <= 2){
            			i++;
            			TranslateAnimation translate = new TranslateAnimation(distance*(i-1), distance*i, 0, 0);
	                	translate.setDuration(500);
	                	translate.reset();  
	                	translate.setFillAfter(true);
	                	chip.clearAnimation();
	                	chip.startAnimation(translate);
            		}
            	}
        	});
		leftButton.setOnClickListener(
			new View.OnClickListener()
        	{
            	public void onClick(View view){
            		if(i >= -2 ){
            			i--;
            			TranslateAnimation translate = new TranslateAnimation(distance*(i+1), distance*i, 0, 0);
	                	translate.setDuration(500);
	                	translate.reset();  
	                	translate.setFillAfter(true);
	                	chip.clearAnimation();
	                	chip.startAnimation(translate);
            		}
            	}
        	});
		chip.setOnTouchListener(new View.OnTouchListener() {
        	@Override
        	public boolean onTouch(View v, MotionEvent event) {
        		switch(event.getAction()){
        		case MotionEvent.ACTION_DOWN:   
        			break;
        		case MotionEvent.ACTION_UP:
        			
        			float x = event.getRawX();
        			float y = event.getRawY();
        			
        			directionx[0] = directionx[1];
        	    	directionx[1] = x;
        	    	float direcx = directionx[1] - directionx[0];
        	    	
        	    	directiony[0] = directiony[1];
        	    	directiony[1] = y;
        	    	float direcy = directiony[0] - directiony[1];
        	    	
        	    	if (direcx > 0){
        	    		if(i <= 2){
                			i++;
                			TranslateAnimation translate = new TranslateAnimation(distance*(i-1), distance*i, 0, 0);
    	                	translate.setDuration(1000);
    	                	translate.reset();  
    	                	translate.setFillAfter(true);
    	                	chip.clearAnimation();
    	                	chip.startAnimation(translate);
                		}
        	    	}
        	    	else if (direcx < 0){
        	    		if(i >= -2 ){
                			i--;
                			TranslateAnimation translate = new TranslateAnimation(distance*(i+1), distance*i, 0, 0);
    	                	translate.setDuration(1000);
    	                	translate.reset();  
    	                	translate.setFillAfter(true);
    	                	chip.clearAnimation();
    	                	chip.startAnimation(translate);
                		}
        	    	}
        	    	if (direcy > 0){
                			TranslateAnimation translate = new TranslateAnimation(distance*i, distance*i, 0, (height-302));
    	                	translate.setDuration(1000);
    	                	translate.reset();  
    	                	translate.setFillAfter(true);
    	                	chip.clearAnimation();
    	                	chip.startAnimation(translate);
        	    	}
	                break;
        		default:
        			break;
        		}
        		return true;
        	}
        });
    }
}