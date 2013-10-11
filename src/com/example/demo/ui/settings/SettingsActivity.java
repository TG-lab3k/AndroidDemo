/**
 * 
 */
package com.example.demo.ui.settings;


import java.util.List;

import com.example.demo.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Button;

/**
 * @author tony.ley
 *
 */
public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(hasHeaders()){
			Button mButton = new Button(this);
			mButton.setText("Some action");
			setListFooter(mButton);
		}
	}
	
	
	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}
}
