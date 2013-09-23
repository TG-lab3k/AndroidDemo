package com.example.demo.provider;

import java.util.Date;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * 结构化数据ContentProvider，使用Sqlite数据库保存数据,给第三方应用提供数据
 * 
 * @author tony.lei
 *
 */
public class MyStructuredProvider extends ContentProvider {
	private static final String TAG = MyStructuredProvider.class.getName();
	
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
	public static class MediaContainer{
		private static final String TABLE_NAME = "t_media_container";
		
		public static final Uri CONTENT_URI = new Uri.Builder().scheme("content://").authority(AUTHORITY).path(TABLE_NAME).build();
		
		public static final String ID = "id";
		public static final String PATH = "path";
		public static final String NAME = "name";
		public static final String CREATE_DATE = "create_at";
		public static final String FILE_SIZE = "file_size";
		public static final String EXT_TYPE = "ext_type";
		public static final String HASH = "hash";
		
		private static String createTable(){
			StringBuilder builder = new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_NAME).append(";");
			builder.append("CREATE TABLE ").append(TABLE_NAME).append("(");
			builder.append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
			builder.append(PATH).append(" TEXT,");
			builder.append(NAME).append(" TEXT,");
			builder.append(CREATE_DATE).append(" INTEGER,");
			builder.append(FILE_SIZE).append(" BIGINT,");
			builder.append(EXT_TYPE).append(" INTEGER,");
			builder.append(MEDIA_ID).append(" INTEGER,");
			builder.append(HASH).append(" TEXT,");
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
		Log.i(TAG, "@onCreate ___________  create structure provider");
		
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
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Uri resUri = mUri;
		
		int match = sUriMatcher.match(mUri);
		switch(match){
		case MEDIA :
			mContentValues.put(MediaContainer.CREATE_DATE, new Date().getTime());
			long id = db.insert(MediaContainer.TABLE_NAME, "", mContentValues);
			resUri = ContentUris.withAppendedId(mUri, id);
			break;
		}
		
		Log.i(TAG, "@insert __ mUri[" + mUri.toString() + "],match[" + match + "],resUri[" + resUri.toString() + "]");
		
		return resUri;
	}

	@Override
	public Cursor query(Uri mUri, String[] projection, String selection, String[] selectionArgs,String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int match = sUriMatcher.match(mUri);
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MediaContainer.TABLE_NAME);
		Cursor resCursor = null;
		switch(match){
		case MEDIA :
			resCursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		}
		
		Log.i(TAG, "@query __ mUri[" + mUri.toString() + "],match[" + match + "]");
		
		return resCursor;
	}

	@Override
	public int update(Uri mUri, ContentValues mContentValues, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int match = sUriMatcher.match(mUri);
		int count = 0;
		switch(match){
		case MEDIA :
			count = db.update(MediaContainer.TABLE_NAME, mContentValues, selection, selectionArgs);
			break;
		}
		
		Log.i(TAG, "@update __ mUri[" + mUri.toString() + "],match[" + match + "],count[" + count + "]");
		
		return count;
	}

	@Override
	public int delete(Uri mUri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int match = sUriMatcher.match(mUri);
		int count = 0;
		switch(match){
		case MEDIA :
			count = db.delete(MediaContainer.TABLE_NAME, selection, selectionArgs);
			break;
		}
		
		Log.i(TAG, "@delete __ mUri[" + mUri.toString() + "],match[" + match + "],count[" + count + "]");
		
		return count;
	}
}
