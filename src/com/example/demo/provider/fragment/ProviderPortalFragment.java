/**
 * 
 */
package com.example.demo.provider.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.demo.OnFragmentSwitchedListener;
import com.example.demo.OnFragmentSwitchedListener.SwitchedIntent;
import com.example.demo.R;

/**
 * @author tony.lei
 *
 */
public class ProviderPortalFragment extends Fragment {
	private static final String TAG = ProviderPortalFragment.class.getName();
	
	private OnFragmentSwitchedListener switchedListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		switchedListener = (OnFragmentSwitchedListener)activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "@onCreateView: create view");
		
		RelativeLayout portalRLayout = (RelativeLayout)inflater.inflate(R.layout.provider_portal, null);
		Spinner navigationSpinner = (Spinner)portalRLayout.findViewById(R.id.provider_portal_navigation_spinner);
		navigationSpinner.setOnItemSelectedListener(naviSpinnerItemSelectedListener);
		return portalRLayout;
	}	
	
	private OnItemSelectedListener naviSpinnerItemSelectedListener = new OnItemSelectedListener(){
		@Override
		public void onItemSelected(AdapterView<?> mParent, View mItem, int position,long id) {
			String itemValue = (String)mParent.getItemAtPosition(position);
			
			Log.d(TAG, "@onItemSelected: id:" + id + ",position:" + position + ", itemValue:" + itemValue);
			
			
			SwitchedIntent intent = null;
			switch(position){
			case 1 :
				intent = new SwitchedIntent(StructuredProviderClientFragment.instance(),"structure");
				break;
			
			case 2 :
				intent = new SwitchedIntent(UserDictionaryFragment.instance(),"user_dictionary");
				break;
				
			default :
				return;
			}
			
			((Spinner)mParent).setSelection(0);
			switchedListener.onSwitch(intent);
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> mParent) {
			//do nothing
		}
	};
}
