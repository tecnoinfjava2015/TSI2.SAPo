package com.utilities;

import java.util.Calendar;
import java.util.Date;

public class IntervalDates {
	private Calendar date1;
	private Calendar date2;
	private Date fromDate;
	private Date toDate;
	
	public IntervalDates(){
		this.date1 = null;
		this.date2 = null;
	}
	
	public IntervalDates(Calendar date1, Calendar date2){
		this.date1 = date1;
		this.date2 = date2;
	}
	
	public Calendar getDate1() {
		return date1;
	}
	public void setDate1(Calendar date1) {
		this.date1 = date1;
	}
	public Calendar getDate2() {
		return date2;
	}
	public void setDate2(Calendar date2) {
		this.date2 = date2;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
}
