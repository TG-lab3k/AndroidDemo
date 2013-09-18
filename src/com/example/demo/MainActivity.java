package com.example.demo;

import com.example.demo.service.TestServiceActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BasicActivity {
	public static class RequestCode{
		public static final int MAIN_2_TEST_SERVICE = 0x1001;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button mTestServiceBtn = (Button)this.findViewById(R.id.test_service_btn);
		mTestServiceBtn.setOnClickListener(mTestServiceListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case  RequestCode.MAIN_2_TEST_SERVICE :
		    //do nothing
			break;
			
		default :
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
	
	private OnClickListener mTestServiceListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			MainActivity.this.startActivityForResult(new Intent(MainActivity.this,TestServiceActivity.class), RequestCode.MAIN_2_TEST_SERVICE);
		}
	};
}
