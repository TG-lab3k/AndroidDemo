package com.example.demo.provider;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.example.demo.util.StringUtil;

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
 * 本节内容请参考 http://developer.android.com/guide/topics/providers/content-provider-creating.html 
 * 
 * Your implementation of these methods should account for the following:
 * a. All of these methods except onCreate() can be called by multiple threads at once, so they must be thread-safe.
 * b. Avoid doing lengthy operations in onCreate()
 * 
 * provider相关的几个要点
 * a. Implementing a Contract Class
 * b. Implementing Content Provider Permissions
 * 
 * 
 * permissions
 * All applications can read from or write to your provider, even if the underlying data is private, because by default your provider does not have permissions set. To change this, set permissions for your provider in your manifest file, using attributes or child elements of the <provider> element. You can set permissions that apply to the entire provider, or to certain tables, or even to certain records, or all three.
 * Provider默认是没有权限保护的，所以的应用程序都可以访问
 * 可以添加属性或者<provider>的子节点来添加权限控制，权限控制分为三层
 * 1. 控制整个provider
 * 2. 控制表级别
 * 3. 控制行级别
 * 
 * @author tony.lei
 *
 */
public class StructuredProvider extends ContentProvider {
	private static final String TAG = StructuredProvider.class.getName();
	
	//URI ID
	private static final int MEDIA = 0x101;
	private static final int MEDIA_ID = 0x102;
	
	//authority
	
	
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static{
		sUriMatcher.addURI(StructuredContract.AUTHORITY, StructuredContract.TABLE_NAME, MEDIA);
		sUriMatcher.addURI(StructuredContract.AUTHORITY, StructuredContract.TABLE_NAME+"/#", MEDIA_ID);
	}
	
	//保存数据的数据表
	public static class MediaContainer{
		
		public static final String ID = "_id";
		public static final String PATH = "path";
		public static final String NAME = "name";
		public static final String CREATE_DATE = "create_at";
		public static final String FILE_SIZE = "file_size";
		public static final String EXT_TYPE = "ext_type";
		public static final String HASH = "hash";
		
		private static String createTable(){
			StringBuilder builder = new StringBuilder("CREATE TABLE ").append(StructuredContract.TABLE_NAME).append("(");
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
	
	//SQLite
	private class SQLiteOpenHelperImpl extends SQLiteOpenHelper{
		private static final String DATABASE_NAME = "demo_structured_provider.db";
		private static final int DATABASE_VERSION = 1;
		
		public SQLiteOpenHelperImpl(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			//db.beginTransaction();
			db.execSQL("DROP TABLE IF EXISTS " + StructuredContract.TABLE_NAME);
			String createTable = MediaContainer.createTable();
			
			Log.i(TAG, "@SQLiteOpenHelperImpl.onCreate: create table[" + createTable + "], CurrentThread's Name:" + Thread.currentThread().getName());
			//main thread
			
			db.execSQL(createTable);
			//db.endTransaction();
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	
			//db.beginTransaction();
			db.execSQL("DROP TABLE IF EXISTS " + StructuredContract.TABLE_NAME);
			String createTable = MediaContainer.createTable();
			db.execSQL(createTable);
			
			Log.i(TAG, "@SQLiteOpenHelperImpl.onUpgrade: upgrade table[" + createTable + "]");
			
			//db.endTransaction();
		}
	}
	
	private SQLiteOpenHelper dbHelper;
	
	@Override
	public boolean onCreate() {
		Log.i(TAG, "@onCreate ___________  create structure provider, CurrentThread's Name:" + Thread.currentThread().getName());
		//main thread
		
		dbHelper = new SQLiteOpenHelperImpl(getContext());
		return true;
	}

	@Override
	public String getType(Uri mUri) {
		String mType = null;
		int match = sUriMatcher.match(mUri);
		switch(match){
		case MEDIA :
			mType = StructuredContract.TYPE_MEDIA;
			break;
			
		case MEDIA_ID :
			mType = StructuredContract.TYPE_MEDIA_ID;
			break;
		
		default :
			mType = null;
			break;
		}
		return mType;
	}
	
	@Override
	public String[] getStreamTypes(Uri uri, String mimeTypeFilter) {
		return super.getStreamTypes(uri, mimeTypeFilter);
	}

	@Override
	public Uri insert(Uri mUri, ContentValues mContentValues) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Uri resUri = mUri;
		
		int match = sUriMatcher.match(mUri);
		switch(match){
		case MEDIA :
			mContentValues.put(MediaContainer.CREATE_DATE, new Date().getTime());
			try {
				mContentValues.put(MediaContainer.HASH, StringUtil.hash(mContentValues.getAsString(MediaContainer.PATH)));
			} catch (NoSuchAlgorithmException e) {
				mContentValues.put(MediaContainer.HASH, "");
			}
			long id = db.insert(StructuredContract.TABLE_NAME, "", mContentValues);
			resUri = ContentUris.withAppendedId(mUri, id);
			break;
		}
		
		Log.i(TAG, "@insert __ mUri[" + mUri.toString() + "],match[" + match + "],resUri[" + resUri.toString() + "], CurrentThread's Name:" + Thread.currentThread().getName());
		//main thread
		
		return resUri;
	}

	@Override
	public Cursor query(Uri mUri, String[] projection, String selection, String[] selectionArgs,String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int match = sUriMatcher.match(mUri);
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(StructuredContract.TABLE_NAME);
		Cursor resCursor = null;
		switch(match){
		case MEDIA :
			resCursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		}
		
		Log.i(TAG, "@query __ mUri[" + mUri.toString() + "],match[" + match + "], CurrentThread's Name:" + Thread.currentThread().getName());
		//main thread
		
		return resCursor;
	}

	@Override
	public int update(Uri mUri, ContentValues mContentValues, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int match = sUriMatcher.match(mUri);
		int count = 0;
		switch(match){
		case MEDIA :
			count = db.update(StructuredContract.TABLE_NAME, mContentValues, selection, selectionArgs);
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
			count = db.delete(StructuredContract.TABLE_NAME, selection, selectionArgs);
			break;
		}
		
		Log.i(TAG, "@delete __ mUri[" + mUri.toString() + "],match[" + match + "],count[" + count + "]");
		
		return count;
	}
}
