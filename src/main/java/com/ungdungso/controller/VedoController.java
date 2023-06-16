package com.ungdungso.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.ungdungso.model.LotteryResults;
import com.ungdungso.model.PrizeCode;
import com.ungdungso.model.Province;
import com.ungdungso.model.HistorySearch;
import com.ungdungso.service.HistorySearchService;
import com.ungdungso.service.LotteryResultService;
import com.ungdungso.service.PrizeCodeService;
import com.ungdungso.service.PrizeValueService;
import com.ungdungso.service.ProvinceService;


@Controller

public class VedoController {
	@Autowired
	ProvinceService provinceService;
	@Autowired
	LotteryResultService lotteryResultService;
	@Autowired
	PrizeValueService prizeValueService;
	@Autowired
	PrizeCodeService prizeCodeService;
	@Autowired
	HistorySearchService historySearchService;



	@GetMapping("/admin-ticket-manage")
	public ModelAndView tickketManage()

	{
		ModelAndView model = new ModelAndView();
		model.addObject("show", "none");
		Calendar dateFromCalendar = Calendar.getInstance();
		dateFromCalendar.add(Calendar.DAY_OF_MONTH, -30);
		Calendar dateToCalendar = Calendar.getInstance();
		Date dateFrom = dateFromCalendar.getTime();
		Date dateTo = dateToCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateFromS = sdf.format(dateFrom);
		String dateToS = sdf.format(dateTo);
		model.addObject("dateFrom", dateFromS);
		model.addObject("dateTo", dateToS);
		model.setViewName("admin/ticket-manage");
		return model;
	}

	// Lấy DS tỉnh Xổ số theo ngày được chọn ( giao diện user)
	
	@GetMapping("/xskt/load-province")
	public ModelAndView loadProvince1(@RequestParam("date") String date) throws IOException {
		ModelAndView model = new ModelAndView("client/load-province-bydate");
		List<Province> list = provinceService.getProvincebyDate(date);
		System.out.println(list.size());
		model.addObject("listProvince", list);
		return model;
	}

	@GetMapping("/admin-form-add-ticket")
	public ModelAndView formAddTicket() {
		ModelAndView model = new ModelAndView();
		model.addObject("show", "none");
		model.setViewName("admin/ticket-add");
		return model;

	}

	@GetMapping("/xskt/admin-search-ticket")
	public ModelAndView searchTicket(
			@RequestParam(value = "idProvince", defaultValue = "0") String idProvince,
			@RequestParam(value = "dateFrom", defaultValue = "0") String dateFrom,
			@RequestParam(value = "dateTo", defaultValue = "0") String dateTo,
			@RequestParam(value = "page", defaultValue = "1") int page) throws ParseException {
		ModelAndView model = new ModelAndView();
		System.out.println(idProvince);
		Calendar datFromCalendar = Calendar.getInstance();
		Calendar datToCalendar = Calendar.getInstance();
		if (idProvince.equals("0")) {
			model.addObject("message", "Bạn chưa chọn tỉnh");
			model.addObject("show", "show");
			model.setViewName("admin/alert");
			return model;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		Date datFrom=sdf.parse(dateFrom);
		Date datTo=sdf.parse(dateTo);
		datFromCalendar.setTime(sdf.parse(dateFrom));
		datToCalendar.setTime(sdf.parse(dateTo));
		
		long noDay = (datToCalendar.getTime().getTime() - datFromCalendar.getTime().getTime()) / (24 * 3600 * 1000);
		if (noDay > 60) {
			model.addObject("message", "Hệ thống chỉ cho phép tìm kiếm trong khoảng thời gian 60 ngày");
			model.addObject("show", "show");
			model.setViewName("admin/alert");
			return model;
		}

		List<Date> listDate= lotteryResultService.getListKQSX(idProvince, datFrom,datTo, page);
		
		int totalKQSX = lotteryResultService.getCountKQSX(idProvince, datFrom, datTo);
		int totalPage = totalKQSX / 5;
		if (totalKQSX % 5 != 0) {
			totalPage++;
		}

		if (listDate.size() >= 1) {
			ArrayList<String> listDateString = new ArrayList<>();
			for (int i = 0; i < listDate.size(); i++) {
				listDateString.add(sdf.format(listDate.get(i)));
			}
			Province province = provinceService.getProvinceById(idProvince);
			model.addObject("listDate", listDateString);
			model.addObject("province", province);
			model.addObject("indexPage", page);
			model.addObject("totalPage", totalPage);
			model.addObject("show", "none");
			model.setViewName("admin/ticket-search-result");
		} else {
			model.addObject("show", "show");
			model.addObject("message", "Không tìm thấy kết quả");
			model.setViewName("admin/alert");
		}
		return model;
	}

	@GetMapping("/xskt/admin-detail-KQXS")
	public ModelAndView detailKQXS(@RequestParam("idProvince") String idProvince,
			@RequestParam("dateKQXS") String dateKQXS) throws ParseException {
		ModelAndView model = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		Date dateC=sdf.parse(dateKQXS);
		listLotteryResult(model, idProvince, dateC);
		model.addObject("date", dateKQXS);
		model.addObject("idProvince", idProvince);
		model.addObject("show", "none");
		model.addObject("province", provinceService.getProvinceById(idProvince).getNameProvince());
		model.setViewName("admin/ticket-detail");
		return model;
	}

	@GetMapping("/xskt/admin-delete-KQXS")
	public void deleteKQXS(@RequestParam("idProvince") String idProvince, @RequestParam("dateKQXS") String dateKQXS)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		Date date= sdf.parse(dateKQXS);	
		System.out.println(idProvince);
		System.out.println(dateKQXS);
		lotteryResultService.deleteKQXS(idProvince, date);
		ModelAndView model = new ModelAndView();
		model.setViewName("admin/ticket-manage");
	}

	@GetMapping("/xskt/admin-deleteFast-KQXS")
	public void deleteFast(@RequestParam("idProvince") String idProvince, @RequestParam("result") String result)
			throws ParseException {
		String[] date = result.split(",");
		for (int i = 1; i < date.length; i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
			Date dateC=sdf.parse(date[i]);
			lotteryResultService.deleteKQXS(idProvince, dateC);
		}
	}

	@GetMapping("/xskt/admin-update-ticket-form")
	public ModelAndView updateFormKQXS(@RequestParam("idProvince") String idProvince,
			@RequestParam("dateKQXS") String dateKQXS) throws ParseException {
		ModelAndView model = new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		System.out.println(idProvince);
		Date date=sdf.parse(dateKQXS);
		listLotteryResult(model, idProvince, date);
		model.addObject("idProvince", idProvince);
		model.addObject("date", dateKQXS);
		model.addObject("province", provinceService.getProvinceById(idProvince).getNameProvince());
		model.addObject("show", "none");
		model.setViewName("admin/ticket-update-form");
		return model;
	}
	
	
	private ModelAndView listLotteryResult(ModelAndView model, String idProvince, Date datePrize) {
		
		String area=provinceService.getProvinceById(idProvince).getArea();
		model.addObject("mien", area);
		List<PrizeCode> listPrizeCodes=prizeCodeService.getPrizeCode(area);
		LotteryResults lotteryResults = lotteryResultService.getKetQuaXoSoByProvinceByDate(idProvince, datePrize);
		
		String[] arrResult = lotteryResults.getResult().split("-");
		for(int i=0; i<listPrizeCodes.size(); i++)
		{
			model.addObject(listPrizeCodes.get(i).getNameCode(), arrResult[i]);
		}
		return model;
	}

	@PostMapping("/admin-update-ticket")
	public ModelAndView updateKQXS(@RequestBody String bodytext, 
			@RequestParam(value = "idProvince", required = true) String idProvince,
			@RequestParam(value = "date", required = true) String dateKQXS ) throws ParseException {
			ModelAndView model= new ModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
		String newResultString;
		Date date=sdf.parse(dateKQXS);
		String yearString=dateKQXS.substring(6);
		System.out.println(yearString);
		LotteryResults lotteryResults =lotteryResultService.getKetQuaXoSoByProvinceByDate(idProvince, date);
		String areaString=provinceService.getProvinceById(idProvince).getArea();
		System.out.println(bodytext);
		String pattern1="[&][G][0-9]+[M][BTN][=]";
		String temp=bodytext.replaceAll(pattern1,"-");
		int indexString=temp.indexOf("F"+yearString);
		temp=temp.substring(indexString+5);
		System.out.println(temp);
		if(areaString.equals("MB"))
		{
			newResultString=bodytext.substring(5,10)+temp;
		} else {
			newResultString=bodytext.substring(5,11)+temp;
		}
		System.out.println(newResultString);
		lotteryResults.setResult(newResultString);
		lotteryResultService.addKetQuaXoSo(lotteryResults);
		model.addObject("show", "show");
		model.addObject("message", "Cập nhật thành công");
		model.setViewName("admin/ticket-manage");	
		return model;
	}

	@RequestMapping("/xskt/admin-save-ticket")
	public ModelAndView saveTicket(@RequestParam(value = "data", required = true) String data) throws ParseException {
		if(data.substring(data.length()-1).equals("-")) {
			data=data.substring(0,data.length()-1);	
		}
		System.out.println(data);
		
		ModelAndView model = new ModelAndView();
		String[] arrData = data.split("-");
		String ngayXoSoString;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		Date ngayXoSo= new Date();

		
		String nameProvince;
		if (arrData[0].contains("/")) {
			ngayXoSoString = arrData[0].substring(arrData[0].length() - 10);
			ngayXoSo= sdf.parse(ngayXoSoString);
			nameProvince = arrData[0].substring(5, arrData[0].length() - 11);

		} else {
			ngayXoSoString = arrData[2].substring(6);
			ngayXoSo= sdf.parse(ngayXoSoString);
			nameProvince = arrData[0].substring(5);
		}	
	
		if(nameProvince.equals("Miền Bắc")) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(sdf.parse(ngayXoSoString));
			int thu=cal.get(Calendar.DAY_OF_WEEK);
			switch (thu) {
			case 1:  //CN
				nameProvince="Thái Bình";
				break;
			case 2:
				nameProvince="Hà Nội";  //T2
				break;
			case 3:
				nameProvince="Quảng Ninh";
				break;
			case 4:
				nameProvince="Bắc Ninh";
				break;
			case 5:
				nameProvince="Hà Nội";
				break;
			case 6:
				nameProvince="Hải Phòng";
				break;
			case 7:
				nameProvince="Nam Định";
				break;
			}		
		}
			
		String idProvince = provinceService.getIDByName(nameProvince);		
		if (lotteryResultService.isExistKetQuaXoSo(ngayXoSo, idProvince)) {// if(ketQuaXoSoService.isExistKetQuaXoSo(ngayXoSo,idProvince))
			model.addObject("show", "show");
			model.addObject("message",
					"Lưu không thành công. KQXS " + nameProvince + " ngày " + ngayXoSoString + " đã lưu trước đó");
			model.setViewName("admin/alert");
			System.out.println("da ton tai");
		} else {
			LotteryResults lotteryResults = new LotteryResults();
			String resultString="";
			for (int i = 3; i < arrData.length; i++) {
				if(i==arrData.length-1) {
					resultString=resultString+arrData[i].trim();
				} else
				resultString=resultString+arrData[i].trim()+"-";
			}
			lotteryResults.setIdProvince(idProvince);
			lotteryResults.setDatePrize(ngayXoSo);
			lotteryResults.setStatusLottery("enable");
			lotteryResults.setResult(resultString);
			System.out.println("check....................");
			lotteryResultService.addKetQuaXoSo(lotteryResults);
			model.addObject("show", "show");
			model.addObject("message", "Lưu KQXS " + nameProvince + " ngày " + ngayXoSoString + " thành công");
			model.setViewName("admin/alert");
		}
		return model;
	}
	
	@GetMapping("/xskt/tracuuvedo")
	public ModelAndView traCuuVeDo(@RequestParam(value = "date", defaultValue = "0", required = true) String date,
			@RequestParam(value = "province", defaultValue = "0", required = true) String idProvince,
			@RequestParam(value = "sove", defaultValue = "0", required = true) String sove,
			Authentication authentication) throws ParseException {
		ModelAndView model = new ModelAndView("client/result-ticket");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		Date datePrize= sdf.parse(date);
		System.out.println(idProvince);
		LotteryResults lotteryResults = lotteryResultService.getKetQuaXoSoByProvinceByDate(idProvince, datePrize);
		Province province=provinceService.getProvinceById(idProvince);
		String namePrize = "Không trúng giải";
		String value = "0";
		String valueFormat = "";
		String area=provinceService.getProvinceById(idProvince).getArea();
		String[] arrResult = lotteryResults.getResult().split("-");
		List<PrizeCode> listPrizeCodes=prizeCodeService.getPrizeCode(area);
		for(int i=0; i<arrResult.length; i++){
			String regString = ".*(" + arrResult[i] + ")";
			if (sove.matches(regString)) {
				String idPrizeString=listPrizeCodes.get(i).getIdPrize();
				namePrize = prizeValueService.getGiai(idPrizeString).getNamePrize();
				value = prizeValueService.getGiai(idPrizeString).getValue();
				Double valueDouble = Double.parseDouble(value);
				Locale localeVN = new Locale("vi", "VN");
				NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
				valueFormat = currencyVN.format(valueDouble);
				break;
			}
		}
		listLotteryResult(model, idProvince, datePrize);
		model.addObject("sove", sove);
		model.addObject("province", provinceService.getProvinceById(idProvince).getNameProvince());
		model.addObject("date", date);
		model.addObject("tenGiaiThuong", namePrize);
		model.addObject("giaTri", valueFormat);

		// Lưu lịch sử dò vé số
		if (authentication != null) {
			User userDetails = (User) authentication.getPrincipal();
			String user = userDetails.getUsername();
			HistorySearch historySearch = new HistorySearch();
			historySearch.setUserName(user);
			historySearch.setProvinceName(province.getNameProvince());
			historySearch.setNumberLottery(sove);
			historySearch.setDatePrize(datePrize);
			Date currDate= new Date();
			historySearch.setDateSearch(currDate);
			historySearch.setResult(namePrize);
			historySearch.setValueResult(value);
			historySearchService.saveHistorySearch(historySearch);
		} else {
			return model;
		}
		return model;

	}
	

	
	
}
