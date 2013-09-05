package com.example.demo.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.service.aidl.IRemoteService;
import com.example.demo.service.aidl.IRemoteServiceCallback;
import com.example.demo.service.aidl.Result;

public class TestServiceActivity extends Activity {
	
	private TextView mRemoteServiceTxtv;
	
	private Button remoteServiceBtn;
	
	private IRemoteService mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.test_service);
		
		remoteServiceBtn = (Button) this.findViewById(R.id.remote_service_btn);
		remoteServiceBtn.setOnClickListener(mBindListener);
		mRemoteServiceTxtv = (TextView) this.findViewById(R.id.remote_service_txtv);
		mRemoteServiceTxtv.setText("No message from remote service");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(KeyEvent.KEYCODE_BACK == keyCode){
			setResult(RESULT_OK);
			finish();
			return true;
		}else{
			return super.onKeyDown(keyCode, event);	
		}
	}
	
	private OnClickListener mBindListener = new OnClickListener() {
		boolean isBind = false;
		
		public void onClick(android.view.View v) {
			if(isBind){
				//unbind
				TestServiceActivity.this.unbindService(mServiceConnection);
				mService = null;
				mRemoteServiceTxtv.setText("The Remote Service Unbind.");
				remoteServiceBtn.setText("Bind Remote Service");
			}
			
			else{
				//bind
				TestServiceActivity.this.bindService(new Intent("call_back_remote_service"), mServiceConnection, Context.BIND_AUTO_CREATE);
				mRemoteServiceTxtv.setText("Binding...");
			}
			
			isBind = (!isBind);
		}
	};
	
	private ServiceConnection mServiceConnection = new ServiceConnection(){
		
		@Override
		public void onServiceConnected(android.content.ComponentName name, android.os.IBinder service) {
			mService = IRemoteService.Stub.asInterface(service);
			//mHandler.sendEmptyMessage(MSG_BIND_REMOTE_SERVICE);
			/*
			try {
				mRemoteServiceTxtv.setText("UID:" + mService.getPid());
			} catch (RemoteException e) {
				mRemoteServiceTxtv.setText(e.toString());
			}
			
			*/
			
			//Main线程
			try {
				if(null == callback){
					System.out.println("@onServiceConnected >> callback is null");
				}else{
					mService.registerCallback(callback);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			remoteServiceBtn.setText("Unbind Remote Service");
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			System.out.println(">> onServiceDisconnected ...");
			try {
				mService.unregisterCallback(callback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			mService = null;
			mHandler.sendEmptyMessage(MSG_UNBIND_REMOTE_SERVICE);
		}
	};
	
	private IRemoteServiceCallback callback = new IRemoteServiceCallback.Stub() {
		
		@Override
		public void onCompleted(Result mResult) throws RemoteException {
			Message msg = mHandler.obtainMessage();
			msg.obj	= mResult;
			msg.what = MSG_RESV_REMOTE_SERVICE;
			mHandler.sendMessage(msg);
			
			//Binder线程
			System.out.println("IRemoteServiceCallback.onCompleted currentThread # " + Thread.currentThread().getName());
		}
	};
	
	private static final int MSG_BIND_REMOTE_SERVICE = 1; 
	
	private static final int MSG_UNBIND_REMOTE_SERVICE = 2;
	
	private static final int MSG_RESV_REMOTE_SERVICE = 3;
	
	private Handler mHandler = new Handler() {
		
		@Override
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case MSG_BIND_REMOTE_SERVICE :
				try {
					mRemoteServiceTxtv.setText("UID:" + mService.getPid());
				} catch (RemoteException e) {
					mRemoteServiceTxtv.setText(e.toString());
				}
				remoteServiceBtn.setText("Unbind Remote Service");
				break;
				
			case MSG_UNBIND_REMOTE_SERVICE :
				mRemoteServiceTxtv.setText("The Remote Service Unbind.");
				remoteServiceBtn.setText("Bind Remote Service");
				break;
				
			case MSG_RESV_REMOTE_SERVICE :
				Result mResult = (Result)msg.obj;
				mRemoteServiceTxtv.setText("Title[" + mResult.title + "] count=" + mResult.count + "; Person[" + mResult.mPerson.name + "],id=" + mResult.mPerson.id);
				break;
				
			default :
				super.handleMessage(msg);
			}
		};
	};
}
