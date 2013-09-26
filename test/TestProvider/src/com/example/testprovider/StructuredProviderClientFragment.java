/**
 * 
 */
package com.example.testprovider;

import com.example.testprovider.ProviderContract.MediaContainer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * MyStructuredProvider client
 * 
 * @author tony.lei
 */
public class StructuredProviderClientFragment extends Fragment {
	private static final String TAG = StructuredProviderClientFragment.class.getName();
	
	private static Fragment fragment;
	
	private StructureCursorAdapter structureCursorAdapter;
	
	public static Fragment instance(){
		if(null == fragment){
			fragment = new StructuredProviderClientFragment();
		}
		
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout fragmentView = (RelativeLayout)inflater.inflate(R.layout.provider_custom_structured, null);
		
		Button saveBtn = (Button)fragmentView.findViewById(R.id.button1);
		saveBtn.setOnClickListener(saveBtnClickListener);
		
		DataHolderView holderView =new DataHolderView();
		holderView.pathEditText = (EditText)fragmentView.findViewById(R.id.editText1);
		holderView.nameEditText = (EditText)fragmentView.findViewById(R.id.editText2);
		holderView.fileSizeEditText = (EditText)fragmentView.findViewById(R.id.editText3);
		holderView.extTypeEditText = (EditText)fragmentView.findViewById(R.id.editText4);
		saveBtn.setTag(holderView);
		
		//StructureCursorAdapter
		ListView listView = (ListView)fragmentView.findViewById(R.id.listView1);
		ContentResolver resolver = getActivity().getContentResolver();
		String type = resolver.getType(ProviderContract.CONTENT_URI);
		
		Log.i(TAG, "@onCreateView: Uri[" + ProviderContract.CONTENT_URI.toString() + "], type:" + type);
		
		Cursor cursor = getActivity().getContentResolver().query(ProviderContract.CONTENT_URI, null, null, null, null);
		structureCursorAdapter = new StructureCursorAdapter(getActivity().getApplicationContext(),cursor,false);
		listView.setAdapter(structureCursorAdapter);
		return fragmentView;
	}
	
	private OnClickListener saveBtnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			//save data
			DataHolderView holderView = (DataHolderView)v.getTag();
			
			ContentResolver contentResolver = getActivity().getContentResolver();
			
			ContentValues values = new ContentValues();
			values.put(MediaContainer.PATH, holderView.pathEditText.getText().toString());
			values.put(MediaContainer.NAME, holderView.nameEditText.getText().toString());
			values.put(MediaContainer.FILE_SIZE, holderView.fileSizeEditText.getText().toString());
			values.put(MediaContainer.EXT_TYPE, holderView.extTypeEditText.getText().toString());
			
			Uri resultUri = contentResolver.insert(ProviderContract.CONTENT_URI, values);
			
			Log.i(TAG, "@saveBtnClickListener.onClick: resultUri:" + resultUri.toString());
			
			String type = contentResolver.getType(ProviderContract.CONTENT_URI);
			
			Log.i(TAG, "@saveBtnClickListener.onClick: Uri[" + ProviderContract.CONTENT_URI.toString() + "], type:" + type);
			
			Cursor cursor = contentResolver.query(ProviderContract.CONTENT_URI, null, null, null, null);
			structureCursorAdapter.swapCursor(cursor);
			structureCursorAdapter.notifyDataSetChanged();
		}
	};
	
	private class DataHolderView{
		EditText pathEditText;
		EditText nameEditText;
		EditText fileSizeEditText;
		EditText extTypeEditText;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		fragment = null;
	}
}
