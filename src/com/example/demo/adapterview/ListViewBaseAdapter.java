/**
 * 
 */
package com.example.demo.adapterview;

import com.example.demo.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author tony.lei
 *
 */
public class ListViewBaseAdapter extends BaseAdapter {
	private final String TAG = ListViewBaseAdapter.class.getName();
	
	private LayoutInflater mInflater;
	private ListItem [] contents;
	
	public ListViewBaseAdapter(Context mContext, ListItem [] contents){
		this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.contents = contents;
	}
	
	public void setContents(ListItem [] contents){
		this.contents = contents;
	}
	
	public ListItem [] getContents(){
		return this.contents;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return (null == contents) ? 0 : contents.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return contents[position];
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return contents[position].id;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//position 当前item在整个Adapter提供数据中的位置
		
		Log.i(TAG, "@getView:  position:" + position);
		if(null == convertView){
			convertView = newView();
		}
		
		build(convertView,position);
		return convertView;
	}
	
	private View newView(){
		View itemView = this.mInflater.inflate(R.layout.cmm_list_item, null);
		ViewHolder holder = new ViewHolder();
		holder.itemTxtv = (TextView)itemView.findViewById(R.id.textView1);
		holder.itemTxtv.setHeight(60);
		itemView.setTag(holder);
		return itemView;
	}
	
	private void build(View itemView, int position){
		ListItem item = contents[position];
		String content = "id:" + item.id + ",  name:" + item.name;
		
		((ViewHolder)itemView.getTag()).itemTxtv.setText(content);
	}
	
	private class ViewHolder{
		TextView itemTxtv;
	}
}
