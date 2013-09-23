/**
 * 本包研究android中AdapterView特性
 * AdapterView见 http://developer.android.com/reference/android/widget/AdapterView.html 
 * 
 * java.lang.Object
   ×↳	android.view.View
 *	   ↳	android.view.ViewGroup
 *	 	   ↳	android.widget.AdapterView<T extends android.widget.Adapter>
 *
 * AdapterView中常见子类
 * GridView, ListView，Gallery, Spinner
 */
package com.example.demo.adapterview;

import android.os.Bundle;

import com.example.demo.BasicActivity;

/**
 * 本类为研究AdapterVie特性的入口程序
 * 
 * @author tony.lei
 *
 */
public class AdapterViewPortalActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
	}
}
