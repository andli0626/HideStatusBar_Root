package com.example.systembardemo;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SystembarActivity extends Activity {

	private Button show;
	private Button hide;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//去除标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_systembar);
        show=(Button)findViewById(R.id.button1);
        show.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showBar();
			}
		});
        hide=(Button)findViewById(R.id.button2);
        hide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeBar();
			}
		});
    }
    
    private void closeBar(){
    	try{
    	    //REQUIRES ROOT
    	    Build.VERSION_CODES vc = new Build.VERSION_CODES();
    	    Build.VERSION vr = new Build.VERSION();
    	    String ProcID = "79"; //HONEYCOMB AND OLDER

    	    //v.RELEASE  //4.0.3
    	    if(vr.SDK_INT >= vc.ICE_CREAM_SANDWICH){
    	        ProcID = "42"; //ICS AND NEWER
    	    }

    	    //REQUIRES ROOT
    	    Process proc = Runtime.getRuntime().exec(new String[]{"su","-c","service call activity "+ ProcID +" s16 com.android.systemui"}); //WAS 79
    	    proc.waitFor();

    	}catch(Exception ex){
    	    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
    	}
    }
    
    private void showBar(){
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{
                    "am","startservice","-n","com.android.systemui/.SystemUIService"});
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
