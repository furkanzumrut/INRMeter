package com.android.inrmeter.model;

import java.util.Date;

import android.text.format.Time;

public class Inr {
	
	private Double inrValue;
	private String inrDate;
	private String inrTime;
	private Integer inrId;

	
	public Inr(Double inrValue, String inrDate, String inrTime) {
		super();
		this.inrValue = inrValue;
		this.inrDate = inrDate;
		this.inrTime = inrTime;
	}
	public Inr() {
		// TODO Auto-generated constructor stub
	}
	public Double getInrValue() {
		return inrValue;
	}
	public void setInrValue(Double inrValue) {
		this.inrValue = inrValue;
	}
	public String getInrDate() {
		return inrDate;
	}
	public void setInrDate(String inrDate) {
		this.inrDate = inrDate;
	}
	public Integer getInrId() {
		return inrId;
	}
	public void setInrId(Integer inrId) {
		this.inrId = inrId;
	}
	public String getInrTime() {
		return inrTime;
	}
	public void setInrTime(String inrTime) {
		this.inrTime = inrTime;
	}


}
