package com.hackathon.babymedicalrecord;

import java.util.Calendar;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

public class BMRDataParse extends Service {
	
	//private Intent intent = new Intent("com.hackathon.babymedicalrecord");  
	private MobileServiceTable<BMRData> mBMRData;
	//private boolean initialised = false;
	
	public int month[];
	private int mMonth;
	
	private final int max_symptom = 20;
	int current_symptom;
	private BMRSymptom symptom_record[];
	
    public IBinder onBind(Intent intent) {
    	Log.e("WXJ", "onBind");
        return myBinder;  
    }  
    public class MyBinder extends Binder{  

        public BMRDataParse getService(){
        	Log.e("WXJ", "getService");
            return BMRDataParse.this;  
        }  
    }
    
    private MyBinder myBinder = new MyBinder();
    
	public void startService()
	{
		Log.e("WXJ", "start background service ");
		new Thread(new Runnable() {
			@Override
			public void run() {
				TraverseItemsFromTable();
            }  
        }).start();  
	}
	public BMRDataParse()
	{
		mBMRData = BMRUtil.getBMRDataTable();
		month = new int[13];
		symptom_record = new BMRSymptom[max_symptom];
		current_symptom = 0;
		
		//final Calendar c = Calendar.getInstance();
		//mMonth = c.get(Calendar.MONTH);
	}
	public BMRDataParse(String name){
        //super(name);
}
	/**
	 * Refresh the list with the items in the Mobile Service Table
	 */
	public void TraverseItemsFromTable() {
		
		Log.e("WXJ", "TraverseItemsFromTable ");

		// Get the items that weren't marked as completed and add them in the
		// adapter
		mBMRData.where().execute(new TableQueryCallback<BMRData>() {

			public void onCompleted(List<BMRData> result, int count, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {
					for (BMRData data : result) {
						
						//Log.e("WXJ", "record month data " + data.getSymptomMonth());
						int m = data.getSymptomMonth();
						
						if(m > 0 && m <12)
							month[m] += 1 ;
							//Log.e("WXJ", "data " + month[m]);
							
							if(mMonth == m)
							{
								insertNewSymptom(data.getSymptomName());
							}
						/*
						Log.e("WXJ", "getId() " + data.getId());
						Log.e("WXJ", "getName() " + data.getName());
						Log.e("WXJ", "getSymptomYear() " + data.getSymptomYear());
						Log.e("WXJ", "getSymptomMonth() " + data.getSymptomMonth());
						Log.e("WXJ", "getSymptomDay() " + data.getSymptomDay());
						Log.e("WXJ", "getSymptomName() " + data.getSymptomName());
						Log.e("WXJ", "getSymptomTemperature() " + data.getSymptomTemperature());
						Log.e("WXJ", "getSymptomDetail() " + data.getSymptomDetail());
						Log.e("WXJ", "getHospital() " + data.getHospital());
						Log.e("WXJ", "getDoctor() " + data.getDoctor());
						Log.e("WXJ", "getCost() " + data.getCost());
						Log.e("WXJ", "getDiagnosis() " + data.getDiagnosis());
						Log.e("WXJ", "getMedicine() " + data.getMedicine());
						//mAdapter.add(data);
						 */
					}
					//initialised = true;
					//Log.e("WXJ", "initialised = " + initialised);
					//intent.putExtra("binder", BMRDataParse.this);  
                    //sendBroadcast(intent);

				} else {
					//BMRUtil.createAndShowDialog(exception, "Error", BMRLoginActivity.this);
					Log.e("WXJ wow", "get data ERROR");
				}
			}
		});
		/*
		for(int i = 0; i <= 12; i++)
		{
			Log.e("WXJ wow", "month[" + i + "] = " + month[i]);
		}
		*/
	}
	
	public void insertNewSymptom(String symptom)
	{
		for(int i = 0; i < current_symptom; i++)
		{
			if(symptom_record[i].getSymptom().equals(symptom))
			{
				symptom_record[i].inc();
				return;
			}
		}
		//Log.e("WXJ", "insertNewSymptom is " + symptom_record[current_symptom]);
		symptom_record[current_symptom] = new BMRSymptom(symptom);
		symptom_record[current_symptom].inc();
		
		current_symptom += 1;
	}
	
	public int[] getMonthData()
	{
		//getCurrentMonthSymptom();
		return month;
	}
	public BMRSymptom[] getCurrentMonthSymptom()
	{
		/*
		for(int i = 0; i < current_symptom; i++)
		{
			Log.e("WXJ", "crrentMonth is " + mMonth);
			Log.e("WXJ", "Symptom " + symptom_record[i].getSymptom());
			Log.e("WXJ", "count " + symptom_record[i].getCount());
		}
		*/
		return symptom_record;
	}
}
