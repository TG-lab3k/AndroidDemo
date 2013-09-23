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
import android.support.v4.app.FragmentManager;

import com.example.demo.BasicActivity;
import com.example.demo.R;

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
		setContentView(R.layout.adapter_portal);
		
		FragmentManager fm = this.getSupportFragmentManager();
		fm.beginTransaction().add(R.id.tab1, new ListViewFragment(), "fragment_listview").commit();
	}
}
