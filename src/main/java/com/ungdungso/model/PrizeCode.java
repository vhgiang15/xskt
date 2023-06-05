package com.ungdungso.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="prizecode")
public class PrizeCode {
	@Id
	@Column( columnDefinition = "NVARCHAR(20)",name="namecode")	
	private String nameCode;
	
	@Column( columnDefinition = "NVARCHAR(20) NOT NULL",name="idprize")
	private String idPrize;

	public PrizeCode() {
		super();
	}

	public PrizeCode(String nameCode, String idPrize) {
		super();
		this.nameCode = nameCode;
		this.idPrize = idPrize;
	}

	public String getNameCode() {
		return nameCode;
	}

	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}

	public String getIdPrize() {
		return idPrize;
	}

	public void setIdPrize(String idPrize) {
		this.idPrize = idPrize;
	}
	

}
