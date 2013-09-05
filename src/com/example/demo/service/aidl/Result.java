package com.example.demo.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {
	public String title;
	
	public int count;
	
	public Person mPerson;
	
	public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>(){
		@Override
		public Result createFromParcel(Parcel source) {
			return new Result(source);
		}

		@Override
		public Result[] newArray(int size) {
			return new Result[size];
		}
	};
	
	public Result(){}
	
	public Result(Parcel source){
		readFromParcel(source);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeInt(count);
		dest.writeInt(mPerson.id);
		dest.writeString(mPerson.name);
	}

	public void readFromParcel(Parcel source){
		title = source.readString();
		count = source.readInt();
		mPerson = new Person();
		mPerson.id = source.readInt();
		mPerson.name = source.readString();
	}
}
