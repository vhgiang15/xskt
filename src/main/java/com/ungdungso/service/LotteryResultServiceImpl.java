package com.ungdungso.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ungdungso.model.LotteryResults;
import com.ungdungso.repository.LotteryResultReponsitory;



@Service
public class LotteryResultServiceImpl implements LotteryResultService {

	@Autowired
	LotteryResultReponsitory lotteryResultReponsitory;

	@Override
	public List<LotteryResults> getAllKetQuaXoSo() {
		return (List<LotteryResults>) lotteryResultReponsitory.findAll();
	}

	@Override
	public void addKetQuaXoSo(LotteryResults ketQuaXoSo) {
		lotteryResultReponsitory.save(ketQuaXoSo);

	}

	@Override
	public boolean isExistKetQuaXoSo(Date ngay, String idProvince) {
		System.out.println(ngay);
		System.out.println(idProvince);
		LotteryResults lotteryResults = lotteryResultReponsitory.getKetQuaXoSoByProvinceAndDate(ngay, idProvince);
		return (lotteryResults != null);

	}

	@Override
	public List<String> getDateSaveKQXS(String idProvince) {
		return lotteryResultReponsitory.querygetDateSaveKQSX(idProvince);
	}

	@Override
	public List<Date> getListKQSX(String id, Date dateFrom, Date dateTo, int page) {
		return (List<Date>) lotteryResultReponsitory.querygetListKQSX(id, dateFrom, dateTo, page * 5 - 5);
	}

	@Override
	public void deleteKQXS(String idProvince, Date date) {
		LotteryResults lotteryResults = lotteryResultReponsitory.getKetQuaXoSoByProvinceAndDate(date, idProvince);
		lotteryResults.setStatusLottery("disable");
		lotteryResultReponsitory.save(lotteryResults);
	}

	@Override
	public Integer getCountKQSX(String id, Date dateFrom, Date dateTo) {
		return lotteryResultReponsitory.queryCountKQSX(id, dateFrom, dateTo);
	}

	@Override
	public boolean isExistKetQuaXoSoByProvince(String id) {
		return (lotteryResultReponsitory.queryCountProvince(id) == 1);
	}

	@Override
	public LotteryResults getKetQuaXoSoByProvinceByDate(String idProvince, Date date) {
		return lotteryResultReponsitory.getKetQuaXoSoByProvinceAndDate(date, idProvince);
	}

	@Override
	public List<Date> getListKQSXTop4(String idProvince) {
		return lotteryResultReponsitory.querygetListKQSXTop4(idProvince);
	}

	@Override
	public Integer countProvinceByDate(Date date) {
		// TODO Auto-generated method stub
		return lotteryResultReponsitory.queryProvinceByDate(date);
	}

}
