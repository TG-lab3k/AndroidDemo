/**
 * 
 */
package com.example.demo.ui.settings;

import com.example.demo.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * @author tony.ley
 *
 */
public class SettingsFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}

}
