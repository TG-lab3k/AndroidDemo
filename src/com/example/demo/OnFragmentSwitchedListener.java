/**
 * 
 */
package com.example.demo;

import android.support.v4.app.Fragment;

/**
 * 同一个activity中的fragment跳转监听
 * 
 * @author tony.lei
 *
 */
public interface OnFragmentSwitchedListener {

	public void onSwitch(SwitchedIntent intent);
	
	public class SwitchedIntent{
		public Fragment fragment;
		public String tag;
		
		public SwitchedIntent(){}
		
		public SwitchedIntent(Fragment fragment, String tag){
			this.fragment = fragment;
			this.tag = tag;
		}
	}
}
