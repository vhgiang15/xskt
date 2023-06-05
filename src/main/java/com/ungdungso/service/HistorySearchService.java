package com.ungdungso.service;

import java.util.Date;
import java.util.List;

import com.ungdungso.model.HistorySearch;



public interface HistorySearchService {
	public void saveHistorySearch(HistorySearch historySearch);
	public List<HistorySearch> getHistorySearch(String userName, Date dateFrom, Date dateTo, int page);
	public int countHistorySearch(String userName, Date dateFrom, Date dateTo);

}
