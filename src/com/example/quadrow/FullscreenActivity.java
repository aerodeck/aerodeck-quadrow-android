package com.example.quadrow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quadrow.util.SystemUiHider;
import com.example.quadrow.R;

public class FullscreenActivity extends Activity {
    
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final boolean TOGGLE_ON_CLICK = true;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    private SystemUiHider mSystemUiHider;

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
        main();
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
    
    public void main(){
    	final Button newGameButton = (Button) findViewById(R.id.new_game);
    	final Button loginButton = (Button) findViewById(R.id.login);
    	//final login login = new login();
    	
    	newGameButton.setOnClickListener(
    			new View.OnClickListener()
    	        {
    	            public void onClick(View view){
    	            	setContentView(R.layout.game_board);
    	            	delayedHide(0);
    	            	mSystemUiHider.hide();
    	            	newGame();
    	            }
    	        });
    	loginButton.setOnClickListener(
    			new View.OnClickListener()
    	        {
    	            public void onClick(View view){
    	            	setContentView(R.layout.login);
    	            	delayedHide(0);
    	            	mSystemUiHider.hide();
    	            	login();
    	            }
    	        });
    }
    public void login(){
    	final Button submitButton = (Button) findViewById(R.id.submit);
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
    }
    public void newGame(){
    	
    }
}