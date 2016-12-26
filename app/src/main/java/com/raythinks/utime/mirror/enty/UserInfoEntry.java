package com.raythinks.utime.mirror.enty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.TextUtils;

public class UserInfoEntry extends BaseInfo {
	private String id;// "402881e6522f68bd01522f9c03130015",
	private String signature;// "这是一个美丽的日子",
	private String password;// "123456",
	private String status;// "N",
	private String createTime;// 1452497700000,
	private String phone;// "15828304797",
	private String sex;// 1,
	private String age;// 30,
	private String birthday;// "1981-01-11",
	private String updateTime;// 1452499800000,
	private String nickName;// "摩登九九",
	private String qq;// "5245682",
	private String wechat;// "256475",
	private String weibo;// "",
	private String headImg;// "",
	private String provinces;// "四川",
	private String place;// "成都",
	private String registeredTime;// null
	private String motto;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		if (TextUtils.isEmpty(signature)) {
			signature = "这家伙很懒，什么都没有";
		}
		return signature;
	}

	/**
	 * @param signature
	 *            the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(String birthday) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new Date();
		java.util.Date mydate;
		try {
			mydate = myFormatter.parse(birthday);
			long day = (date.getTime() - mydate.getTime())
					/ (24 * 60 * 60 * 1000) + 1;
			String year = (day / 365) + "";
			this.age = year;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.birthday = birthday;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the wechat
	 */
	public String getWechat() {
		return wechat;
	}

	/**
	 * @param wechat
	 *            the wechat to set
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	/**
	 * @return the weibo
	 */
	public String getWeibo() {
		return weibo;
	}

	/**
	 * @param weibo
	 *            the weibo to set
	 */
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	/**
	 * @return the headImg
	 */
	public String getHeadImg() {
		return headImg;
	}

	/**
	 * @param headImg
	 *            the headImg to set
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	/**
	 * @return the provinces
	 */
	public String getProvinces() {
		return provinces;
	}

	/**
	 * @param provinces
	 *            the provinces to set
	 */
	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the registeredTime
	 */
	public String getRegisteredTime() {
		return registeredTime;
	}

	/**
	 * @param registeredTime
	 *            the registeredTime to set
	 */
	public void setRegisteredTime(String registeredTime) {
		this.registeredTime = registeredTime;
	}

	public String getMotto() {
		if (TextUtils.isEmpty(motto)) {
			motto = "未设置";
		}
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

}
