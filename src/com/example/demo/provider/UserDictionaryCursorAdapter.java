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
		TextView mTextView = (TextView)mView.getTag();
		StringBuilder content = new StringBuilder();
		if(mCursor.moveToNext()){
			content.append(" _ID:").append(mCursor.getString(0));
			content.append(" WORD:").append(mCursor.getString(1));
			content.append(" LOCALE:").append(mCursor.getString(2));
		}else{
			content.append("no data");
		}
		mTextView.setText(content.toString());
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context mContext, Cursor mCursor, ViewGroup mParent) {
		LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mItem = mInflater.inflate(R.layout.provider_user_dictionary_item, null);
		
		//provider_user_dictionary_item_txtv
		TextView textView = (TextView)mItem.findViewById(R.id.provider_user_dictionary_item_txtv);
		StringBuilder content = new StringBuilder();
		if(mCursor.moveToNext()){
			content.append(" _ID:").append(mCursor.getString(0));
			content.append(" WORD:").append(mCursor.getString(1));
			content.append(" LOCALE:").append(mCursor.getString(2));
		}else{
			content.append("no data");
		}
		textView.setText(content.toString());
		mItem.setTag(textView);
		return mItem;
	}
}
