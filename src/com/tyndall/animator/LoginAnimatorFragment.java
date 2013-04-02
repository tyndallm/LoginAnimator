package com.tyndall.animator;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LoginAnimatorFragment extends Fragment{
	
	private static final String TAG = LoginAnimatorFragment.class.getSimpleName();
	public ImageView vaultTumbler;
	public ImageView leftDoor;
	public ImageView rightDoor;
	public ImageView vaultBack;
	public ImageView vaultLogo;
	public boolean allowSpin = true;
	
	public float currentRotation = 0;
	
	public LoginAnimatorFragment(){
		
	}
	
	@Override
	// Android provides us with inflater, no context is necessary
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.login_screen_fragment, container, false);
		leftDoor = (ImageView) view.findViewById(R.id.left_door);
		rightDoor = (ImageView) view.findViewById(R.id.right_door);
		vaultBack = (ImageView) view.findViewById(R.id.vault_back);
		vaultLogo = (ImageView) view.findViewById(R.id.vault_logo);
		
		vaultTumbler = (ImageView) view.findViewById(R.id.vault_spinner);
		vaultTumbler.setOnTouchListener(new OnTouchListener(){
			   public boolean onTouch(View v, MotionEvent event){
				    double r = Math.atan2(event.getX() - vaultTumbler.getWidth() / 2, vaultTumbler.getHeight() / 2 - event.getY());
		            int rotation = (int) Math.toDegrees(r);


		            if (event.getAction() == MotionEvent.ACTION_DOWN)
		            {
		                //
		            }

		            if (event.getAction() == MotionEvent.ACTION_MOVE)
		            {
		            	if(allowSpin){
		            		turn(rotation);
		            	}
		                
		            }

		            if (event.getAction() == MotionEvent.ACTION_UP)
		            {
		            	if(allowSpin){
			                animateDoors();

		            	}
		            }

		            return true;
				   }
		});
		
		return view;
		
        
	}
	
	public void turn(double rotate)
	{
		float rotation = Double.valueOf(rotate * .05).floatValue();
		
		RotateAnimation anim = new RotateAnimation(currentRotation, currentRotation + rotation,
	            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
	    currentRotation = (currentRotation + rotation) % 360;

	    anim.setFillEnabled(true);
	    anim.setInterpolator(new AccelerateDecelerateInterpolator());
	    anim.setFillAfter(true);

	
	    vaultTumbler.startAnimation(anim);
	    
	}
	
	public void animateDoors(){
		allowSpin = false;
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		float screenXWidth = display.getWidth();
		float doorSlideDistance = (screenXWidth / 2);
		
		TranslateAnimation moveLeft = new TranslateAnimation(0, - doorSlideDistance + 50, 0, 0);
		TranslateAnimation moveRight = new TranslateAnimation(0,  doorSlideDistance - 50, 0, 0);

		moveLeft.setFillEnabled(true);
		moveRight.setFillEnabled(true);
		
		moveLeft.setDuration(800);
		moveRight.setDuration(800);
		
		moveLeft.setInterpolator(new AccelerateDecelerateInterpolator());
		moveRight.setInterpolator(new AccelerateDecelerateInterpolator());
		
		leftDoor.startAnimation(moveLeft);
		rightDoor.startAnimation(moveRight);
		vaultBack.startAnimation(moveRight);
		vaultTumbler.startAnimation(moveRight);
		vaultLogo.startAnimation(moveRight);
//		loginBox.startAnimation(moveLeft);
		
		moveLeft.setFillAfter(true);
		moveRight.setFillAfter(true);
		
		}
	

	
	


}
