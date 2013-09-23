/**
 * 
 */
package com.example.demo.adapterview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demo.R;

/**
 * @author tony.lei
 *
 */
public class ListViewFragment extends Fragment {
	private static final String TAG = ListViewFragment.class.getName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		RelativeLayout listViewFragment = (RelativeLayout)inflater.inflate(R.layout.adapter_list_view, null);
		
		TextView displayer = (TextView) listViewFragment.findViewById(R.id.textView2);
		
		ListViewBaseAdapter adapter = new ListViewBaseAdapter(getActivity().getApplicationContext());
		ListView mListView = (ListView) listViewFragment.findViewById(R.id.listView1);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(listViewItemClickListener);
		mListView.setTag(displayer);
		
		return listViewFragment;
	}
	
	private OnItemClickListener listViewItemClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> container, View itemView, int position,long id) {
			String message = "@onItemClick: position:" + position + ", id:" + id;
			
			Log.i(TAG, message);
			
			TextView displayer = (TextView)container.getTag();
			
			displayer.setText(message);
			
			
		}
	};
}
