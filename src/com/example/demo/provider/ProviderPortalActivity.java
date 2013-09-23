package com.example.demo.provider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.demo.BasicActivity;
import com.example.demo.OnFragmentSwitchedListener;
import com.example.demo.R;
import com.example.demo.provider.fragment.ProviderPortalFragment;

public class ProviderPortalActivity extends BasicActivity implements OnFragmentSwitchedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provider_portal_layout);
		
		//ContentResolver mContentResolver = this.getContentResolver();
		
		//this.getLayoutInflater();
		
		FragmentManager fm = this.getSupportFragmentManager();
		//FragmentTransaction fTransaction = fm.beginTransaction();
		
		//fTransaction.add(new UserDictionaryFragment(), "fragment_user_dictionary");
		//fTransaction.commit();
		
		//fm.beginTransaction().add(R.id.fragment_container, new UserDictionaryFragment(), "fragment_user_dictionary").commit();
		fm.beginTransaction().add(R.id.fragment_container, new ProviderPortalFragment(), "fragment_portal").commit();
	
		//android.R.id.content
		//fm.beginTransaction().add(android.R.id.content, new ProviderPortalFragment(), "fragment_portal").commit();
	}
	
	@Override
	public void onSwitch(Intent intent) {
		//intent
	}
}
