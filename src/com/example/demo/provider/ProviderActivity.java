package com.example.demo.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;

public class ProviderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ContentResolver mContentResolver = this.getContentResolver();
		
		this.getLayoutInflater();
	}
}
