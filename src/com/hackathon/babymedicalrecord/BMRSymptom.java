package com.hackathon.babymedicalrecord;

import android.util.Log;

public class BMRSymptom {
	private String symptom;
	private int count;
	
	public BMRSymptom(String symptom)
	{
		this.symptom = new String(symptom);
		count = 0;
	}
	public void inseartSymptom(String symptom)
	{
		Log.e("WXJ", "before insertNewSymptom is " + symptom);
		this.symptom = new String(symptom);
		Log.e("WXJ", "before insertNewSymptom is " + symptom);
	}
	public void inc()
	{
		count += 1;
	}
	public String getSymptom()
	{
		return symptom;
	}
	public int getCount()
	{
		return count;
	}
}
