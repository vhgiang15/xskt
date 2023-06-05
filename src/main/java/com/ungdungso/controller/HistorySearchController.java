package com.ungdungso.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ungdungso.model.HistorySearch;
import com.ungdungso.model.Province;
import com.ungdungso.service.HistorySearchService;
import com.ungdungso.service.ProvinceService;


@Controller
public class HistorySearchController {
	@Autowired
	HistorySearchService historySearchService;
	@Autowired
	ProvinceService provinceService;
	@RequestMapping("/user-lich-su-do")
	public ModelAndView lichSuDo(Authentication authentication) {
		ModelAndView model= new ModelAndView("client/lich-su-do");
		User userDetails=(User) authentication.getPrincipal();
		String user=userDetails.getUsername();		
		Calendar dateFromCalendar = Calendar.getInstance();
		dateFromCalendar.add(Calendar.DAY_OF_MONTH, -10);
		Calendar dateToCalendar = Calendar.getInstance();
		Date dateFrom = dateFromCalendar.getTime();
		Date dateTo = dateToCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateFromS = sdf.format(dateFrom);
		String dateToS = sdf.format(dateTo);
		model.addObject("dateFrom", dateFromS);
		model.addObject("dateTo", dateToS);
		model.addObject("userName", user);
		List<Province> listProvinceMN= provinceService.getProvinceByArea("MN");
		List<Province> listProvinceMT= provinceService.getProvinceByArea("MT");
		List<Province> listProvinceMB= provinceService.getProvinceByArea("MB");
		model.addObject("listProvinceMN", listProvinceMN);
		model.addObject("listProvinceMT", listProvinceMT);
		model.addObject("listProvinceMB", listProvinceMB);
		model.addObject("showMN", "show");
		model.addObject("showMT", "none");
		model.addObject("showMB", "none");
		return model;
	}
	
	@GetMapping("/xskt/tra-cuu-lich-su-do")
	public ModelAndView ketQualichSuDo(Authentication authentication,
			@RequestParam(value = "dateFrom", defaultValue = "0", required = true) String dateFromString,
			@RequestParam(value = "dateTo", defaultValue = "0", required = true) String dateToString,
			@RequestParam(value = "page", defaultValue = "1", required = true) int page) throws ParseException {
		ModelAndView model= new ModelAndView("client/result-lich-su-do");
		System.out.print("----------------giang");
		User userDetails=(User) authentication.getPrincipal();
		String user=userDetails.getUsername();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		Date dateFrom=sdf.parse(dateFromString);
		Date dateTo=sdf.parse(dateToString);
		List<HistorySearch> list= historySearchService.getHistorySearch(user, dateFrom, dateTo, page);
		int totalHistory = historySearchService.countHistorySearch(user, dateFrom, dateTo);	
		int totalPage = totalHistory / 5;
		if (totalHistory % 5 != 0) {
			totalPage++;
		}
		model.addObject("listHistory", list);
		model.addObject("indexPage", page);
		model.addObject("totalPage", totalPage);
		return model;
	}
	
	

}
