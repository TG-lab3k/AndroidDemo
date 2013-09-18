package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.demo.NavigationAdapter.AdapterContent;
import com.example.demo.provider.ProviderPortalActivity;
import com.example.demo.service.ServicePortalActivity;

public class MainActivity extends BasicActivity {
	public static class RequestCode{
		public static final int MAIN_2_TEST_SERVICE				= 0x1001;
		public static final int MAIN_2_PORTAL_ACTIVITY			= 0x1002;
	}
	
	private static final AdapterContent [] ARRAY_NAVIGATION = {
			AdapterContent.trans(1002L,R.drawable.ic_launcher, "Service", ServicePortalActivity.class),
			AdapterContent.trans(1003L,R.drawable.ic_launcher, "Provider", ProviderPortalActivity.class),
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView navigationListView = (ListView)this.findViewById(R.id.main_navigation_lstv);
		navigationListView.setOnItemClickListener(mNavigationOnItemClickListener);
		navigationListView.setAdapter(new NavigationAdapter(getApplicationContext(),ARRAY_NAVIGATION));
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
	
	private OnItemClickListener mNavigationOnItemClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View item, int position,long id) {
			AdapterContent adaContent = (AdapterContent)parent.getItemAtPosition(position);
			startActivityForResult(new Intent(MainActivity.this.getApplicationContext(), adaContent.clazz), RequestCode.MAIN_2_PORTAL_ACTIVITY);
		}
	};
}
