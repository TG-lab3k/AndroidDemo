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

/**
 * @author tony
 *
 */
public class UserDictionaryCursorAdapter extends CursorAdapter {

	

	public UserDictionaryCursorAdapter(Context context, Cursor c,boolean autoRequery) {
		super(context, c, autoRequery);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View mView, Context mContext, Cursor mCursor) {
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context mContext, Cursor mCursor, ViewGroup mParent) {
		LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mItem = mInflater.inflate(R.layout.provider_user_dictionary_item, mParent);
		
		return mItem;
	}
}
