package com.example.demo.provider;

import com.example.demo.BasicActivity;

import android.content.ContentResolver;
import android.os.Bundle;

public class ProviderActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ContentResolver mContentResolver = this.getContentResolver();
		
		this.getLayoutInflater();
	}
}
