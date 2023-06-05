package com.ungdungso.model;

import java.math.BigInteger;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="historysearch")
public class HistorySearch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name="id")
	private BigInteger id; //Giá trị lớn nhất là 9.223.372.036.854.775.807 (2^63 -1)
	
	@Column( columnDefinition = "NVARCHAR(20) NOT NULL", name="username")	
	private String userName;
	
	@Column( columnDefinition = "NVARCHAR(20) NOT NULL",name="provincename")	
	private String provinceName;
	
	@Column( columnDefinition = "NVARCHAR(6) NOT NULL",name="numberlottery")	
	private String numberLottery;
	
	@Column( columnDefinition = " DATE",name="dateprize")	
	private Date datePrize; // ngày xổ số
	
	@Column( columnDefinition = "DATE",name="datesearch")	
	private Date dateSearch;  //ngày dò vé
	
	@Column( columnDefinition = "NVARCHAR(30) NOT NULL", name="result")	
	private String result;
	
	@Column( columnDefinition = "NVARCHAR(30) NOT NULL", name="valueresult")	
	private String valueResult;


		
	public HistorySearch() {
		super();
	}
	
	
	public HistorySearch(BigInteger id, String userName, String provinceName, String numberLottery, Date datePrize,
			Date dateSearch, String result, String valueResult) {
		super();
		this.id = id;
		this.userName = userName;
		this.provinceName = provinceName;
		this.numberLottery = numberLottery;
		this.datePrize = datePrize;
		this.dateSearch = dateSearch;
		this.result = result;
		this.valueResult = valueResult;
	}

	public BigInteger getId() {
		return id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getProvinceName() {
		return provinceName;
	}


	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}


	public String getNumberLottery() {
		return numberLottery;
	}


	public void setNumberLottery(String numberLottery) {
		this.numberLottery = numberLottery;
	}


	public Date getDatePrize() {
		return datePrize;
	}


	public void setDatePrize(Date datePrize) {
		this.datePrize = datePrize;
	}


	public Date getDateSearch() {
		return dateSearch;
	}


	public void setDateSearch(Date dateSearch) {
		this.dateSearch = dateSearch;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getValueResult() {
		return valueResult;
	}


	public void setValueResult(String valueResult) {
		this.valueResult = valueResult;
	}


/*	public String getdatePrizeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		String date = sdf.format(this.datePrize); 
		return date;	
	}

	
	public String getNgayDoVeString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		String date = sdf.format(this.dateSearch); 
		return date;
	}
	*/

	
	
	
	

}
