package com.example.demo.provider;

import android.net.Uri;


/**
 * 
 * @author tony.lei
 *
 */
public final class StructuredContract {
	static final String TABLE_NAME = "t_media_container";

	public static final String TYPE_MEDIA = "vnd.android.cursor.dir/vnd.demo.provider.media";
	public static final String TYPE_MEDIA_ID = "vnd.android.cursor.item/vnd.demo.provider.id";
	
	public static final String AUTHORITY = "com.example.demo.provider.structuredProvider";
	
	public static final Uri CONTENT_URI = new Uri.Builder().scheme("content").authority(StructuredContract.AUTHORITY).path(TABLE_NAME).build();
}
