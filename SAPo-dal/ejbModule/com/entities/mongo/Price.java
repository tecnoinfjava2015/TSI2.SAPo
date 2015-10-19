package com.entities.mongo;

import java.util.Calendar;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 * @author jpmartinez
 *
 */
@Embedded
public class Price {
	private String value;
	@Reference private Currency currency;
	private Calendar date;
	
	public Price() {
		super();
	}

	public Price(String value, Currency currency, Calendar date) {
		super();
		this.value = value;
		this.currency = currency;
		this.date = date;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	
}
