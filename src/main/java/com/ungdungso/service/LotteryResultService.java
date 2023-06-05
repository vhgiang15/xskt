package com.ungdungso.service;

import java.util.Date;
import java.util.List;

import com.ungdungso.model.LotteryResults;



public interface LotteryResultService {
	public List<LotteryResults> getAllKetQuaXoSo();
	public LotteryResults getKetQuaXoSoByProvinceByDate(String idProvince, Date date);
	public List<String> getDateSaveKQXS(String idProvince);
	public List<Date> getListKQSX(String idProvince, Date datFrom,Date datTo, int page);
	public Integer getCountKQSX(String idProvince, Date dateFrom, Date dateTo);
	public void deleteKQXS(String idProvince, Date date);
	public void addKetQuaXoSo(LotteryResults ketQuaXoSo);
	public boolean isExistKetQuaXoSo(Date ngay,String idProvince);
	public boolean isExistKetQuaXoSoByProvince(String idProvince);
	public List<Date> getListKQSXTop4(String idProvince);
	public Integer countProvinceByDate(Date date);

}
