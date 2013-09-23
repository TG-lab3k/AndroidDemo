/**
 * 
 */
package com.example.demo.provider;

import com.example.demo.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author tony.lei
 *
 */
public class StructureCursorAdapter extends CursorAdapter {
	LayoutInflater mInflater;

	public StructureCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder)view.getTag();
		
		StringBuilder message = new StringBuilder("ID:").append(cursor.getString(0));
		message.append(",path:").append(cursor.getString(1));
		message.append(",name:").append(cursor.getString(2));
		message.append(",createAt:").append(cursor.getString(3));
		message.append(",fileSize:").append(cursor.getString(4));
		message.append(",extType:").append(cursor.getString(5));
		message.append(",hash:").append(cursor.getString(6));
		
		holder.contentTxtv.setText(message.toString());
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View itemView = mInflater.inflate(R.layout.cmm_list_item, null);
		ViewHolder holder = new ViewHolder();
		holder.contentTxtv = (TextView) itemView.findViewById(R.id.textView1);
		itemView.setTag(holder);
		return itemView;
	}
	
	private class ViewHolder{
		TextView contentTxtv;
	}
}
