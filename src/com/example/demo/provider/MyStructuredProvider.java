package com.example.demo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * 结构化数据ContentProvider，使用Sqlite数据库保存数据
 * 
 * @author tony.lei
 *
 */
public class MyStructuredProvider extends ContentProvider {
	//URI ID
	private static final int MEDIA = 0x101;
	private static final int MEDIA_ID = 0x102;
	
	//type
	private static final String TYPE_MEDIA = "vnd.android.cursor.media/vnd.demo.media";
	private static final String TYPE_MEDIA_ID = "vnd.android.cursor.media/vnd.demo.id";
	
	//authority
	private static final String AUTHORITY = "com.example.demo.provider.structuredProvider";
	
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static{
		sUriMatcher.addURI(AUTHORITY, MediaContainer.TABLE_NAME, MEDIA);
		sUriMatcher.addURI(AUTHORITY, MediaContainer.TABLE_NAME+"/#", MEDIA_ID);
	}
	
	//保存数据的数据表
	private static class MediaContainer{
		private static final String TABLE_NAME = "t_media_container";
		
		static final String ID = "id";
		static final String PARENT_ID = "parent_id";
		static final String PATH = "path";
		static final String NAME = "name";
		static final String CREATE_DATE = "create_at";
		static final String IS_ROOT = "is_root";
		static final String IS_DIR = "is_dir";
		static final String FILE_SIZE = "file_size";
		static final String EXT_TYPE = "ext_type";
		static final String MIME_TYPE = "mime_type";
		static final String MEDIA_ID = "media_id";
		static final String HASH = "hash";
		static final String SUFFIX = "suffix";
		
		static String createTable(){
			StringBuilder builder = new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_NAME).append(";");
			builder.append("CREATE TABLE ").append(TABLE_NAME).append("(");
			builder.append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
			builder.append(PARENT_ID).append(" INTEGER,");
			builder.append(PATH).append(" TEXT,");
			builder.append(NAME).append(" TEXT,");
			builder.append(CREATE_DATE).append(" INTEGER,");
			builder.append(IS_ROOT).append(" INTEGER,");
			builder.append(IS_DIR).append(" INTEGER,");
			builder.append(FILE_SIZE).append(" BIGINT,");
			builder.append(EXT_TYPE).append(" INTEGER,");
			builder.append(MIME_TYPE).append(" TEXT,");
			builder.append(MEDIA_ID).append(" INTEGER,");
			builder.append(HASH).append(" TEXT,");
			builder.append(SUFFIX).append(" TEXT,");
			return builder.toString();
		}
	}
	
	//SQLite
	private class SQLiteOpenHelperImpl extends SQLiteOpenHelper{
		private static final String DATABASE_NAME = "android_demo_structured_provider.db";
		private static final int DATABASE_VERSION = 1;
		
		public SQLiteOpenHelperImpl(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(MediaContainer.createTable());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	
			if(newVersion > oldVersion){
				db.execSQL(MediaContainer.createTable());
			}
		}
	}
	
	private SQLiteOpenHelper dbHelper;
	
	@Override
	public boolean onCreate() {
		dbHelper = new SQLiteOpenHelperImpl(getContext());
		return true;
	}

	@Override
	public String getType(Uri mUri) {
		String mType = null;
		int match = sUriMatcher.match(mUri);
		switch(match){
		case MEDIA :
			mType = TYPE_MEDIA;
			break;
			
		case MEDIA_ID :
			mType = TYPE_MEDIA_ID;
			break;
		
		default :
			mType = null;
			break;
		}
		return mType;
	}

	@Override
	public Uri insert(Uri mUri, ContentValues mContentValues) {
		return null;
	}

	@Override
	public Cursor query(Uri mUri, String[] projection, String selection, String[] selectionArgs,String sortOrder) {
		return null;
	}

	@Override
	public int update(Uri mUri, ContentValues mContentValues, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int delete(Uri mUri, String selection, String[] selectionArgs) {
		return 0;
	}
}
