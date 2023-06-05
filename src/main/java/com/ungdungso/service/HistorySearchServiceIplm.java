package com.ungdungso.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ungdungso.model.HistorySearch;
import com.ungdungso.repository.HistorySearchRepository;


@Service
public class HistorySearchServiceIplm implements HistorySearchService{
	@Autowired
	HistorySearchRepository historySearchRepository;

	@Override
	public void saveHistorySearch(HistorySearch historySearch) {
		historySearchRepository.save(historySearch);
	}

	@Override
	public List<HistorySearch> getHistorySearch(String userName, Date dateFrom, Date dateTo, int page) {
		return historySearchRepository.querygetListHistorySearch(userName, dateFrom, dateTo, page*5-5);
	}

	@Override
	public int countHistorySearch(String userName, Date dateFrom, Date dateTo) {
		return historySearchRepository.countHistorySearch(userName, dateFrom, dateTo);
	}

}
