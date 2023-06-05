package com.ungdungso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ungdungso.model.PrizeValue;
import com.ungdungso.repository.PrizeValueReponsitory;

@Service
public class PrizeValueServiceImpl implements PrizeValueService {
	
	@Autowired
	PrizeValueReponsitory prizeValueReponsitory;

	@Override
	public List<String> getIdPrize() {
		return prizeValueReponsitory.querygetIdPrize();
	}

	@Override
	public PrizeValue getGiai(String idGiai) {
		return prizeValueReponsitory.findById(idGiai).get();
	}

	@Override
	public List<PrizeValue> getGiaTriGiaiMN() {
		
		return prizeValueReponsitory.querygetIDGiaTriGiaiMN();
	}

	@Override
	public List<PrizeValue> getGiaTriGiaiMB() {
		// TODO Auto-generated method stub
		return prizeValueReponsitory.querygetIDGiaTriGiaiMB();
	}
}
