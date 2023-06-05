package com.ungdungso.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="province")
public class Province {
	@Id
	@Column(columnDefinition = "NVARCHAR(20)",name="idprovince" )
	private String idProvince; // trung voi ma tinh vơi trang web minhngoc.net

	@Column(columnDefinition = "NVARCHAR(20) NOT NULL",name="nameprovince")
	private String nameProvince;

	@Column(columnDefinition = "NVARCHAR(4) NOT NULL",name="dayofweek")
	private String dayOfWeek;

	@Column(columnDefinition = "NVARCHAR(20) NOT NULL",name="area")
	private String area;////

	@Column(columnDefinition = "NVARCHAR(10) NOT NULL",name="status")
	private String status; ///

	public Province() {
		super();
	}
	public Province(String idProvince, String nameProvince, String dayOfWeek, String area, String status) {
		super();
		this.idProvince = idProvince;
		this.nameProvince = nameProvince;
		this.dayOfWeek = dayOfWeek;
		this.area = area;
		this.status = status;
	}
	public String getIdProvince() {
		return idProvince;
	}
	public void setIdProvince(String idProvince) {
		this.idProvince = idProvince;
	}
	public String getNameProvince() {
		return nameProvince;
	}
	public void setNameProvince(String nameProvince) {
		this.nameProvince = nameProvince;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	
}
