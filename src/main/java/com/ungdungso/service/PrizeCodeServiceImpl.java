package com.ungdungso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ungdungso.model.PrizeCode;
import com.ungdungso.repository.PrizeCodePepository;



@Service
public class PrizeCodeServiceImpl implements PrizeCodeService {
	@Autowired
	PrizeCodePepository prizeCodePepository;

	@Override
	public List<PrizeCode> getPrizeCode(String mien) {
		mien="%"+mien;
		return (List<PrizeCode>) prizeCodePepository.queryGetPrizeCode(mien);
	}

}
