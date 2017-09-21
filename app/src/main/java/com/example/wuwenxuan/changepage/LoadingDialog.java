package com.example.wuwenxuan.changepage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;


public class LoadingDialog extends Dialog {
	private Context context = null;
	private static LoadingDialog loadingDialog = null;

	
	public LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	public LoadingDialog(Context context) {
		super(context);
		this.context = context;
	}
	public static LoadingDialog createDialog(Context context){
		loadingDialog = new LoadingDialog(context,R.style.LoadingStyle);
		loadingDialog.setContentView(R.layout.loading);
		loadingDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		loadingDialog.setCanceledOnTouchOutside(false);
		return loadingDialog;
	}
 
    public void onWindowFocusChanged(boolean hasFocus){
    	if (loadingDialog == null){
    		return;
    	}
        ImageView imageView = (ImageView) loadingDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }
 
    
    public LoadingDialog setTitile(String strTitle){
    	return loadingDialog;
    }
}