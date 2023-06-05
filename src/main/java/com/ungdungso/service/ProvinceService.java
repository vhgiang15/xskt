package com.ungdungso.service;

import java.util.List;

import com.ungdungso.model.Province;



public interface ProvinceService {

	public List<Province> getAllProvince();
	public List<Province> getAllProvinces(String name, int page);
	public Province getProvinceById(String idProvince);
	public List<Province> getProvincebyDate(String date);
	public List<Province> getProvinceByArea(String area);
	public List<Province> getProvinceByDateByArea(String date, String area);
	public String getThuById(String id);
	public String getAreaByName(String name);
	public String getIDByName(String name);
	public int countProvinceByName(String name);
	public void addProvince(Province province);
	public void updateProvince(Province province);
	public void deleteById(String id);
	public boolean isExistProvince(String provinceName);
	public Integer countProvinceStatus(String status);

}
