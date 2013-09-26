package com.example.testprovider;

import android.net.Uri;


/**
 * 
 * @author tony.lei
 *
 */
public final class ProviderContract {
	static final String TABLE_NAME = "t_media_container";

	public static final String TYPE_MEDIA = "vnd.android.cursor.dir/vnd.demo.provider.media";
	public static final String TYPE_MEDIA_ID = "vnd.android.cursor.item/vnd.demo.provider.id";
	
	public static final String AUTHORITY = "com.example.demo.provider.structuredProvider";
	
	public static final Uri CONTENT_URI = new Uri.Builder().scheme("content").authority(ProviderContract.AUTHORITY).path(TABLE_NAME).build();
	
	//保存数据的数据表
	public static class MediaContainer{
		
		public static final String ID = "_id";
		public static final String PATH = "path";
		public static final String NAME = "name";
		public static final String CREATE_DATE = "create_at";
		public static final String FILE_SIZE = "file_size";
		public static final String EXT_TYPE = "ext_type";
		public static final String HASH = "hash";
		
		static String createTable(){
			StringBuilder builder = new StringBuilder("CREATE TABLE ").append(ProviderContract.TABLE_NAME).append("(");
			builder.append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
			builder.append(PATH).append(" TEXT, ");
			builder.append(NAME).append(" TEXT, ");
			builder.append(CREATE_DATE).append(" INTEGER, ");
			builder.append(FILE_SIZE).append(" BIGINT, ");
			builder.append(EXT_TYPE).append(" INTEGER, ");
			builder.append(HASH).append(" TEXT);");
			return builder.toString();
		}
	}
}
