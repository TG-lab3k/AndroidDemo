package com.example.demo.provider.fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.provider.UserDictionaryCursorAdapter;

/**
 * Fragment for displaying content UserDictionary
 * 
 * @author tony
 *
 */
public class UserDictionaryFragment extends Fragment {
	private static final String TAG = UserDictionaryFragment.class.getName();
	
	ListView content;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Log.d(TAG, "@onActivityCreated __");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		Log.d(TAG, "@onCreateView begin ..");
		//return super.onCreateView(inflater, container, savedInstanceState);
		
		View contentView = inflater.inflate(R.layout.provider_user_dictionary, null);
		
		Button selectButton = (Button) contentView.findViewById(R.id.provider_user_dict_select_btn);
		selectButton.setOnClickListener(sctBtnOnClickListener);
		
		content = (ListView) contentView.findViewById(R.id.provider_user_dict_content_listview);
		
		Button toContactsBtn = (Button) contentView.findViewById(R.id.provider_to_contacts_btn);
		toContactsBtn.setOnClickListener(toContactsOnClickListener);
		return contentView;
	}
	
	private OnClickListener sctBtnOnClickListener = new OnClickListener(){
		
		@SuppressLint("ShowToast")
		@Override
		public void onClick(View v) {
			ContentResolver resolver = getActivity().getContentResolver();
			
			String[] mProjection ={
				    UserDictionary.Words._ID,    // Contract class constant for the _ID column name
				    UserDictionary.Words.WORD,   // Contract class constant for the word column name
				    UserDictionary.Words.LOCALE  // Contract class constant for the locale column name
			};
			
			String mSelectionClause = null;
			String[] mSelectionArgs = null;
			String sortOrder = null;
			Cursor mCursor = resolver.query(UserDictionary.Words.CONTENT_URI, mProjection, mSelectionClause, mSelectionArgs, sortOrder);
			if(null == mCursor){
				Toast.makeText(getActivity(), "The cursor is null", Toast.LENGTH_LONG);
			}else if(0 == mCursor.getCount()){
				Toast.makeText(getActivity(), "The cursor count is 0", Toast.LENGTH_LONG);
			}else{
				Log.i(TAG, "The cursor count is:" + mCursor.getCount());
				Toast.makeText(getActivity(), "The cursor count is:" + mCursor.getCount(), Toast.LENGTH_LONG);
				UserDictionaryCursorAdapter mCursorAdapter = new UserDictionaryCursorAdapter(getActivity().getApplicationContext(), mCursor, false);
				content.setAdapter(mCursorAdapter);
			}
		}
	};
	
	private OnClickListener toContactsOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
		}
	};
}
