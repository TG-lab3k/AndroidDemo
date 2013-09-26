package com.example.testprovider;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		getSupportFragmentManager().beginTransaction().add(R.id.container, StructuredProviderClientFragment.instance()).commit();
	}
}
