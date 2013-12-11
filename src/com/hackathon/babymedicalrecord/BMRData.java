package com.hackathon.babymedicalrecord;

import java.io.Serializable;

public class BMRData implements Serializable {

	/**
	 * Item Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	private String mId;

	@com.google.gson.annotations.SerializedName("name")
	private String mName;
	
	@com.google.gson.annotations.SerializedName("symptom_year")
	private int mSymptomYear;
	@com.google.gson.annotations.SerializedName("symptom_month")
	private int mSymptomMonth;
	@com.google.gson.annotations.SerializedName("symptom_day")
	private int mSymptomDay;

	@com.google.gson.annotations.SerializedName("symptom_name")
	private String mSymptomName;

	@com.google.gson.annotations.SerializedName("symptom_temperature")
	private String mSymptomTemperature;

	@com.google.gson.annotations.SerializedName("symptom_detail")
	private String mSymptomDetail;
	
	@com.google.gson.annotations.SerializedName("hospital")
	private String mHospital;
	@com.google.gson.annotations.SerializedName("doctor")
	private String mDoctor;
	@com.google.gson.annotations.SerializedName("cost")
	private float mCost;
	@com.google.gson.annotations.SerializedName("diagnosis")
	private String mDiagnosis;
//	@com.google.gson.annotations.SerializedName("consultation")
//	private String mConsultation;
	@com.google.gson.annotations.SerializedName("medicine")
	private String mMedicine;
	
	
	
	/**
	 * BMRUser constructor
	 */
	public BMRData() {

	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof BMRData && ((BMRData) o).mId == mId;
	}
	

	/**
	 * Returns the item id
	 */
	public String getId() {
		return mId;
	}

	/**
	 * Sets the item id
	 * 
	 * @param id
	 *            id to set
	 */
	public final void setId(String id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public final void setName(String name) {
		mName = name;
	}

	public int getSymptomYear() {
		return mSymptomYear;
	}

	public void setSymptomYear(int mSymptomYear) {
		this.mSymptomYear = mSymptomYear;
	}

	public int getSymptomMonth() {
		return mSymptomMonth;
	}

	public void setSymptomMonth(int mSymptomMonth) {
		this.mSymptomMonth = mSymptomMonth;
	}

	public int getSymptomDay() {
		return mSymptomDay;
	}

	public void setSymptomDay(int mSymptomDay) {
		this.mSymptomDay = mSymptomDay;
	}

	public String getSymptomName() {
		return mSymptomName;
	}

	public void setSymptomName(String mSymptomName) {
		this.mSymptomName = mSymptomName;
	}

	public String getSymptomTemperature() {
		return mSymptomTemperature;
	}

	public void setSymptomTemperature(String mSymptomTemperature) {
		this.mSymptomTemperature = mSymptomTemperature;
	}

	public String getSymptomDetail() {
		return mSymptomDetail;
	}

	public void setSymptomDetail(String mSymptomDetail) {
		this.mSymptomDetail = mSymptomDetail;
	}

	public String getHospital() {
		return mHospital;
	}

	public void setHospital(String mHospital) {
		this.mHospital = mHospital;
	}

	public String getDoctor() {
		return mDoctor;
	}

	public void setDoctor(String mDoctor) {
		this.mDoctor = mDoctor;
	}

	public float getCost() {
		return mCost;
	}

	public void setCost(float mCost) {
		this.mCost = mCost;
	}

	public String getDiagnosis() {
		return mDiagnosis;
	}

	public void setDiagnosis(String mDiagnosis) {
		this.mDiagnosis = mDiagnosis;
	}

//	public String getConsultation() {
//		return mConsultation;
//	}
//
//	public void setConsultation(String mConsultation) {
//		this.mConsultation = mConsultation;
//	}

	public String getMedicine() {
		return mMedicine;
	}

	public void setMedicine(String mMedicine) {
		this.mMedicine = mMedicine;
	}
	
	
}
