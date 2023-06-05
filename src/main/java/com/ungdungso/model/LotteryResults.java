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
@Table(name="lotteryresults")
public class LotteryResults {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idlottery")
	private BigInteger idLottery;
	
	@Column( columnDefinition = "DATE",name="dateprize")
	private Date datePrize;
	
	@Column(columnDefinition = "NVARCHAR(20) NOT NULL",name="idprovince")
	private String idProvince;
	
	@Column( columnDefinition = "NVARCHAR(162) NOT NULL",name="result")	
	private String result;
	
	@Column( columnDefinition = "NVARCHAR(20) NOT NULL",name="statuslottery")	
	private String statusLottery;

	public LotteryResults() {
		super();
	}

	public LotteryResults(BigInteger idLottery, Date datePrize, String idProvince, String result, String statusLottery) {
		super();
		this.idLottery = idLottery;
		this.datePrize = datePrize;
		this.idProvince = idProvince;
		this.result = result;
		this.statusLottery = statusLottery;
	}

	public BigInteger getIdLottery() {
		return idLottery;
	}


	public Date getDatePrize() {
		return datePrize;
	}

	public void setDatePrize(Date ngayXoSo) {
		this.datePrize = ngayXoSo;
	}

	public String getIdProvince() {
		return idProvince;
	}

	public void setIdProvince(String idProvince) {
		this.idProvince = idProvince;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatusLottery() {
		return statusLottery;
	}

	public void setStatusLottery(String statusLottery) {
		this.statusLottery = statusLottery;
	}
	
	

}
