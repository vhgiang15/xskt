package com.ungdungso.model;

import java.text.NumberFormat;
import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="prizevalue")
public class PrizeValue {
	@Id
	@Column( columnDefinition = "NVARCHAR(20) NOT NULL",name="idprize")
	private String idPrize;
	
	@Column( columnDefinition = "NVARCHAR(20) NOT NULL",name="nameprize")
	private String namePrize;

	@Column( columnDefinition = "NVARCHAR(20) NOT NULL",name="value")
	private String value;
	
		
	public PrizeValue() {
		super();
	}



	public PrizeValue(String idPrize, String namePrize, String value) {
		super();
		this.idPrize = idPrize;
		this.namePrize = namePrize;
		this.value = value;
	}



	public String getIdPrize() {
		return idPrize;
	}



	public void setIdPrize(String idPrize) {
		this.idPrize = idPrize;
	}



	public String getNamePrize() {
		return namePrize;
	}



	public void setNamePrize(String namePrize) {
		this.namePrize = namePrize;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String giaTriGiaiFormat()
	{
		Double giaTriDouble = Double.parseDouble(this.value);
		Locale localeVN = new Locale("vi", "VN");
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
		return currencyVN.format(giaTriDouble);
		
	}

}
