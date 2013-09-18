package com.example.demo.provider;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.demo.BasicActivity;
import com.example.demo.R;
import com.example.demo.provider.fragment.UserDictionaryFragment;

public class ProviderPortalActivity extends BasicActivity {

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
		
		fm.beginTransaction().add(R.id.fragment_container, new UserDictionaryFragment(), "fragment_user_dictionary").commit();
		//fm.beginTransaction().add(new UserDictionaryFragment(), "fragment_user_dictionary").commit();
	}
}
