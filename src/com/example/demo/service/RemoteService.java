package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.demo.service.aidl.IRemoteService;
import com.example.demo.service.aidl.IRemoteServiceCallback;
import com.example.demo.service.aidl.Person;
import com.example.demo.service.aidl.Result;

public class RemoteService extends Service {
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	private List<IRemoteServiceCallback> callbacks = new ArrayList<IRemoteServiceCallback>();
	
	private Thread doCallback;
	
	private IRemoteService.Stub mBinder = new IRemoteService.Stub(){
		public int getPid() throws android.os.RemoteException {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			return 12345;
		}
		
		public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws android.os.RemoteException {
			System.out.println(">> anInt[" + anInt + "],aBoolean[" + aBoolean + "],aFloat[" + aFloat + "],aDouble[" + aDouble + "],aString[" + aString + "]");
		}
		
		@Override
		public void registerCallback(IRemoteServiceCallback mCallback)
				throws RemoteException {
			
			synchronized (callbacks) {
				if(null == mCallback){
					System.out.println("@registerCallback >>  mCallback is null");
				}else{
					callbacks.add(mCallback);
				}
			}

			if(null == doCallback){
				doCallback = new DoCallbackThread();
				doCallback.setName("# do callback thread");
				doCallback.start();
			}
		}
		
		@Override
		public void unregisterCallback(IRemoteServiceCallback mCallback)
				throws RemoteException {
			synchronized (callbacks) {
				callbacks.remove(mCallback);
			}
		}
	};
	
	private volatile static boolean isStop = false;
	
	private class DoCallbackThread extends Thread{
		private int count = 1;
		
		@Override
		public void run() {
			isStop = false;
			
			while(! isStop){
				synchronized (callbacks) {
					Result result = null;
					
					int num = 1;
					for(IRemoteServiceCallback mCallback : callbacks){
						result = new Result();
						result.title = "title # " + count + " # " + num ++;
						result.count = count;
						
						Person mPerson = new Person();
						mPerson.id = 1001;
						mPerson.name = "tony_lei";
						result.mPerson = mPerson;
						try {
							if(null == mCallback){
								System.out.println("@run >> mCallback is null");
							}else{
								mCallback.onCompleted(result);
								System.out.println("@run >> callback...");
							}
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						
						num ++;
					}
				}
				
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) {
					isStop = true;
				}
				
				count ++;
			}//end while
			
			System.out.println("do run thread is end!");
		}
	}
	
	public void onDestroy() {
		isStop = true;
		doCallback.interrupt();
		doCallback = null;
		System.out.println(this.toString() + "  -> onDestroy");
	};
}
