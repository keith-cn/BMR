package com.hackathon.babymedicalrecord;

public class BMRUser {

	/**
	 * Item Id
	 */
	@com.google.gson.annotations.SerializedName("id")
	private String mId;

	@com.google.gson.annotations.SerializedName("name")
	private String mName;

	@com.google.gson.annotations.SerializedName("password")
	private String mPassword;

	@com.google.gson.annotations.SerializedName("baby_name")
	private String mBabyName;

	/**
	 * true is Male, false is Female.
	 */
	@com.google.gson.annotations.SerializedName("gender")
	private boolean mGender;

	@com.google.gson.annotations.SerializedName("baby_year")
	private int mBabyYear;
	@com.google.gson.annotations.SerializedName("baby_month")
	private int mBabyMonth;
	@com.google.gson.annotations.SerializedName("baby_day")
	private int mBabyDay;

	/**
	 * BMRUser constructor
	 */
	public BMRUser() {

	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Initializes a new BMRUser
	 * 
	 * @param name
	 *            The item name
	 * @param id
	 *            The item id
	 */
	public BMRUser(String id, String name, String password, 
			String baby_name, boolean gender,
			int year, int month, int day) {
		this.setId(id);
		this.setName(name);
		this.setPassword(password);
		this.setBabyName(baby_name);
		this.setGender(gender);
		this.setBabyYear(year);
		this.setBabyYear(month);
		this.setBabyYear(day);
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

	public String getPassword() {
		return mPassword;
	}

	public final void setPassword(String password) {
		mPassword = password;
	}

	public String getBabyName() {
		return mBabyName;
	}

	public final void setBabyName(String name) {
		mBabyName = name;
	}

	public boolean getGender() {
		return mGender;
	}

	public void setGender(boolean gender) {
		mGender = gender;
	}

	public int getBabyYear() {
		return mBabyYear;
	}

	public void setBabyYear(int year) {
		mBabyYear = year;
	}

	public int getBabyMonth() {
		return mBabyMonth;
	}

	public void setBabyMonth(int month) {
		mBabyMonth = month;
	}

	public int getBabyDay() {
		return mBabyDay;
	}

	public void setBabyDay(int day) {
		mBabyDay = day;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof BMRUser && ((BMRUser) o).mId == mId;
	}
}
