package com.ungdungso.service;

import java.util.List;

import com.ungdungso.model.PrizeValue;



public interface PrizeValueService {
	public List<String> getIdPrize();
	public PrizeValue getGiai(String idGiai);
	public List<PrizeValue> getGiaTriGiaiMN();
	public List<PrizeValue> getGiaTriGiaiMB();

}
