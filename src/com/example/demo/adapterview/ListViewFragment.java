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
	
	private ListItem [] contents;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i(TAG, "@onCreate _____ __");
		
		//make data
		int size = 50;
		contents = new ListItem[size];
		long id = 1001;
		for(int i = 0; i < size; i ++){
			contents[i] = new ListItem(id ++, "title_"+i, "");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "@onCreateView  ______  ___");
		
		RelativeLayout listViewFragment = (RelativeLayout)inflater.inflate(R.layout.adapter_list_view, null);
		
		TextView displayer = (TextView) listViewFragment.findViewById(R.id.textView2);
		
		ListViewBaseAdapter adapter = new ListViewBaseAdapter(getActivity().getApplicationContext(),contents);
		ListView mListView = (ListView) listViewFragment.findViewById(R.id.listView1);
		mListView.setAdapter(adapter);
		mListView.setSelection((contents.length - 10 > 0) ? contents.length - 10 : 0);
		mListView.setOnItemClickListener(listViewItemClickListener);
		mListView.setTag(displayer);
		
		return listViewFragment;
	}
	
	private OnItemClickListener listViewItemClickListener = new OnItemClickListener(){
		
		
		@Override
		public void onItemClick(AdapterView<?> container, View itemView, int position,long id) {
			String message = "@onItemClick: position:" + position + ", id:" + id;
			
			//Log.i(TAG, message);
			
			TextView displayer = (TextView)container.getTag();
			
			displayer.setText(message);
			
			ListViewBaseAdapter adapter = (ListViewBaseAdapter)container.getAdapter();
			ListItem [] oldContents = adapter.getContents();
			
			if(1 == (position&1)){//奇数
				int len = oldContents.length - 5;
				ListItem [] newContents = new ListItem[(len > 10) ? len : 10];
				System.arraycopy(oldContents, 0, newContents, 0, newContents.length);	
				adapter.setContents(newContents);
			}else{//偶数
				oldContents[0].name = "name was changed";
				
				//验证结果：4.1 上面  此处修改通过notifyDataSetChanged有效
			}

			adapter.notifyDataSetChanged();
		}
	};
}
