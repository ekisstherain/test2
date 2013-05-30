package com.gdie.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期范围
 * 
 * @author HHP
 * 
 */
public class DateScope implements Serializable{

	private Date startDate;

	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
