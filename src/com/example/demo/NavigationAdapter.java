/**
 * 
 */
package com.example.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author tony.lei
 *
 */
public class NavigationAdapter extends BaseAdapter {
	private AdapterContent [] contents;
	
	private Builder itemBuilder;
	
	public NavigationAdapter(Context mContext, AdapterContent [] contents){
		this.contents = contents;
		itemBuilder = new Builder(mContext);
	}

	@Override
	public int getCount() {
		return contents.length;
	}

	@Override
	public Object getItem(int position) {
		return contents[position];
	}

	@Override
	public long getItemId(int position) {
		return contents[position].id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(null == convertView){
			convertView = itemBuilder.build(position,contents);
		}else{
			itemBuilder.rebuild(convertView,position,contents);
		}
		return convertView;
	}
	
	class Builder{
		private LayoutInflater mInflater;
		
		Builder(Context mContext){
			mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		public View build(int position,AdapterContent [] contents){
			//main_navigation_item.xml
			View mItemView = mInflater.inflate(R.layout.main_navigation_item, null);
			
			AdapterContent content = contents[position];
			
			ImageView icon = (ImageView)mItemView.findViewById(R.id.navigation_icon_imgv);
			icon.setImageResource(content.iconID);
			
			TextView text = (TextView)mItemView.findViewById(R.id.navigation_title_txtv);
			text.setText(content.title);
			
			mItemView.setTag(new Holder(icon,text));
			return mItemView;
		}
		
		public void rebuild(View convertView,int position,AdapterContent [] contents){
			Holder mHolder = (Holder)convertView.getTag();
			AdapterContent content = contents[position];
			mHolder.iconImgv.setImageResource(content.iconID);
			mHolder.txtv.setText(content.title);
		}
	}
	
	class Holder{
		ImageView iconImgv;
		TextView txtv;
		
		Holder(ImageView iconImgv,TextView txtv){
			this.iconImgv = iconImgv;
			this.txtv = txtv;
		}
	}
	
	static class AdapterContent{
		long id;
		int iconID;
		String title;
		Class<? extends BasicActivity> clazz;
		
		private AdapterContent(long id,int iconID,String title,Class<? extends BasicActivity> clazz){
			this.id = id;
			this.iconID = iconID;
			this.title = title;
			this.clazz = clazz;
		}
		
		public static AdapterContent trans(long id,int iconID,String title,Class<? extends BasicActivity> clazz){
			return new AdapterContent(id,iconID,title,clazz);
		}
	}
}
