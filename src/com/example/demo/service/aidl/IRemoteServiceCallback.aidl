package com.example.demo.service.aidl;

import com.example.demo.service.aidl.Result;

interface IRemoteServiceCallback{
	
	void onCompleted(in Result mResult);
}