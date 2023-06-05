package com.ungdungso.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.ungdungso.model.LotteryResults;
import com.ungdungso.model.PrizeCode;
import com.ungdungso.model.PrizeValue;
import com.ungdungso.model.Province;
import com.lowagie.text.DocumentException;
import com.ungdungso.business.UserPDFExporterByDate;
import com.ungdungso.model.Account;
import com.ungdungso.service.AccountService;
import com.ungdungso.service.LotteryResultService;
import com.ungdungso.service.PrizeCodeService;
import com.ungdungso.service.PrizeValueService;
import com.ungdungso.service.ProvinceService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.RandomStringUtils;

@Controller
public class HomeController {
	@Autowired
	AccountService accountService;
	@Autowired
	PrizeValueService prizeValueService;
	@Autowired
	ProvinceService provinceService;
	@Autowired
	PrizeCodeService prizeCodeService;
	@Autowired
	LotteryResultService lotteryResultService;

	@GetMapping(value = { "/", "/index" })
	public ModelAndView home(Authentication authentication) throws IOException, ParseException {
		ModelAndView model = new ModelAndView("client/index");
		if (authentication != null) {
			User userDetails = (User) authentication.getPrincipal();
			String user = userDetails.getUsername();
			model.addObject("userName", user);
			System.out.println(user);
			model.addObject("lichsudove", "show");
		} else {
			model.addObject("userName", "guest");
			model.addObject("lichsudove", "none");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		Calendar currentCalendar = Calendar.getInstance();
		Calendar checkPointCalendar = Calendar.getInstance();
		checkPointCalendar.set(Calendar.HOUR_OF_DAY, 16);
		checkPointCalendar.set(Calendar.MINUTE, 45);
		checkPointCalendar.set(Calendar.SECOND, 0);
		String nowOrYesterday="HÔM NAY";
		if (currentCalendar.getTimeInMillis() < checkPointCalendar.getTimeInMillis()) {
			currentCalendar.add(Calendar.DAY_OF_MONTH, -1);
			nowOrYesterday="HÔM QUA";
		}
		model.addObject("nowOrYesterday", nowOrYesterday);	
		String dateString = sdf.format(currentCalendar.getTime());
		Date dateKQXS = sdf.parse(dateString);
		model.addObject("date", dateString);
		
		//Trả KQXS của tỉnh Miền Nam
		List<Province> listMN = provinceService.getProvinceByDateByArea(dateString,"MN");
		model.addObject("countProvinceMN",listMN.size());
		for(int i=0; i<listMN.size(); i++) {
			model.addObject("provinceMN"+i, listMN.get(i).getNameProvince());
			model.addObject("idMN"+i, listMN.get(i).getIdProvince());
			String idProvince= listMN.get(i).getIdProvince();
			LotteryResults listQuaXoSo=lotteryResultService.getKetQuaXoSoByProvinceByDate(idProvince, dateKQXS);	
			if(listQuaXoSo==null) {				
				model.addObject("thongBaoMN","đang cập nhật");
				break;}		
			listLotteryResult(model, idProvince, dateKQXS,i);		
		}
		
		//Trả KQXS của tỉnh Miền Trung
		List<Province> listMT = provinceService.getProvinceByDateByArea(dateString,"MT");
		model.addObject("countProvinceMT",listMT.size());
		for(int i=0; i<listMT.size(); i++) {
			model.addObject("provinceMT"+i, listMT.get(i).getNameProvince());
			model.addObject("idMT"+i, listMT.get(i).getIdProvince());
			String idProvince= listMT.get(i).getIdProvince();
			
			LotteryResults listQuaXoSo=lotteryResultService.getKetQuaXoSoByProvinceByDate(idProvince, dateKQXS);		
			if(listQuaXoSo==null) {
				model.addObject("thongBaoMT","đang cập nhật");
				break;
				}
			listLotteryResult(model, idProvince, dateKQXS,i);	
		}
				
		//Trả KQXS của tỉnh Miền Bắc
		List<Province> listMB = provinceService.getProvinceByDateByArea(dateString,"MB");
		model.addObject("countProvinceMB",listMB.size());
		for(int i=0; i<listMB.size(); i++) {
			model.addObject("provinceMB"+i, listMB.get(i).getNameProvince());
			model.addObject("idMB"+i, listMB.get(i).getIdProvince());
			String idProvince= listMB.get(i).getIdProvince();
			LotteryResults listQuaXoSo=lotteryResultService.getKetQuaXoSoByProvinceByDate(idProvince, dateKQXS);	
			if(listQuaXoSo==null) {
				model.addObject("thongBaoMB","đang cập nhật");
				break;}
			listLotteryResult(model, idProvince, dateKQXS,i);				
		}
		setDislayLeftSilde(model);
		return model;
	}
	
	private ModelAndView listLotteryResult(ModelAndView model, String idProvince, Date datePrize,Integer number) {
		String area=provinceService.getProvinceById(idProvince).getArea();
		List<PrizeCode> listPrizeCodes=prizeCodeService.getPrizeCode(area);
		LotteryResults lotteryResults = lotteryResultService.getKetQuaXoSoByProvinceByDate(idProvince, datePrize);
		String[] arrResult = lotteryResults.getResult().split("-");
		for(int i=0; i<listPrizeCodes.size(); i++)
		{
			model.addObject(listPrizeCodes.get(i).getNameCode()+number, arrResult[i]);
		}
		return model;
	}

	@GetMapping("/do-ve-so")
	public ModelAndView doVeSo(Authentication authentication) {
		ModelAndView model = new ModelAndView("client/do-ve-so");
		if (authentication != null) {
			User userDetails = (User) authentication.getPrincipal();
			String user = userDetails.getUsername();
			model.addObject("userName", user);
			model.addObject("lichsudove", "show");
		} else {
			model.addObject("userName", "guest");
			model.addObject("lichsudove", "none");
		}
		setDislayLeftSilde(model);
		return model;
	}
	
	
	
	@GetMapping("/admin-dashboard")
	public ModelAndView dashboard(Authentication authentication) throws ParseException {
		ModelAndView model = new ModelAndView("admin/dashboard");
		int totalUser= accountService.totalUser();
		int countUserEnable=accountService.countUserEnable();
		model.addObject("totalUser", totalUser);
		model.addObject("countUserEnable", countUserEnable);
		model.addObject("countUserDisable",totalUser-countUserEnable);
		List<Province> listProvincesMN=provinceService.getProvinceByArea("MN");
		List<Province> listProvincesMT=provinceService.getProvinceByArea("MT");
		List<Province> listProvincesMB=provinceService.getProvinceByArea("MB");
		int countProvinceMN=listProvincesMN.size();
		int countProvinceMT=listProvincesMT.size();
		int countProvinceMB=listProvincesMB.size();
		model.addObject("countProvinceMN", countProvinceMN);
		model.addObject("countProvinceMT", countProvinceMT);
		model.addObject("countProvinceMB", countProvinceMB);
		Calendar yesterdayCalendar = Calendar.getInstance();
		yesterdayCalendar.add(Calendar.DAY_OF_MONTH, -1);		
		Calendar todayCalendar = Calendar.getInstance();
		Date yesterday = yesterdayCalendar.getTime();
		Date today = todayCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String todayS = sdf.format(today);
		String yesterdayS = sdf.format(yesterday);
		today=sdf.parse(todayS);
		yesterday=sdf.parse(yesterdayS);
		int countProvinceEnable=provinceService.countProvinceStatus("enable");
		int countProvinceDisable=provinceService.countProvinceStatus("disable");
		model.addObject("provinceEnable", countProvinceEnable);
		model.addObject("provinceDisable", countProvinceDisable);
		int countProvinceToday=provinceService.getProvincebyDate(todayS).size();
		int countProvinceYesterday=provinceService.getProvincebyDate(yesterdayS).size();
		int countKQSXtoday=lotteryResultService.countProvinceByDate(today);
		int countKQSXyesterday=lotteryResultService.countProvinceByDate(yesterday);		
		model.addObject("countProvinceToday", countProvinceToday);
		model.addObject("countProvinceYesterday", countProvinceYesterday);
		model.addObject("countKQXSToday", countKQSXtoday);
		model.addObject("countKQSXyesterday", countKQSXyesterday);
		model.addObject("countNoneKQXSToday",countProvinceToday- countKQSXtoday);
		model.addObject("countNoneKQSXyesterday",countProvinceYesterday-countKQSXyesterday);
		return model;
	}
	
	@GetMapping(value="/403")
	public ModelAndView accessDenied(Principal principal)
	{
		ModelAndView model= new ModelAndView("client/403Page");
		if(principal!=null) {
			User loginedUser=(User)((Authentication) principal).getPrincipal();
			String userInfo =loginedUser.getUsername();
			model.addObject("userInfo",userInfo);
			String message= "Hi "+ userInfo+ " không có quyền truy cập vào trang web này";
			model.addObject("message",message);	
		}
		return model;
	}
	
	
	// Gọi form đăng nhập gốc
	@GetMapping("/login-form")
	public ModelAndView loginForm( Authentication authentication, @RequestParam(value = "error", defaultValue = "0", required = true) String error) {
		ModelAndView model = new ModelAndView("client/login-form");
		if (authentication != null) {
			User userDetails = (User) authentication.getPrincipal();
			String user = userDetails.getUsername();
			model.addObject("userName", user);
			model.addObject("lichsudove", "show");
		} else {
			model.addObject("userName", "guest");
			model.addObject("lichsudove", "none");
		}
		setDislayLeftSilde(model);
				
		if (error.equals("failed")) {
			model.addObject("show", "show");
			model.addObject("message", "UserName or password không đúng !");
		} else {
			model.addObject("show", "none");
		}
		return model;
	}


	// Đăng ký mới user
	@RequestMapping("/register-user")
	public ModelAndView registerUser(@RequestParam(value = "fullName", required = true, defaultValue = "null") String fullName,
			@RequestParam(value = "user", required = true, defaultValue = "null") String user,
			@RequestParam(value = "phone", required = true, defaultValue = "null") String phone,
			@RequestParam(value = "mail", required = true, defaultValue = "null") String mail) {
		ModelAndView model = new ModelAndView();
		String pass = RandomStringUtils.randomAlphanumeric(8);
		String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt(12));
		Account account = new Account(user, fullName, phone, mail, hashPass, "ROLE_USER", "enable");		
		System.out.println(fullName);
		System.out.println(user);
		System.out.println(phone);
		System.out.println(mail);
		if(fullName.equals("null")||user.equals("null")||phone.equals("null")||mail.equals("null")) {
			model.addObject("show", "show");
			model.addObject("message", "Thông tin chưa đầy đủ");
			model.setViewName("client/alert");
			return model;		
		}
		
		if (accountService.isExistUserName(user)) {
			model.addObject("show", "show");
			model.addObject("message", "Username đã tồn tại");
			model.setViewName("client/alert");
			return model;
		}
		if (accountService.isExistUserMail(mail)) {
			model.addObject("show", "show");
			model.addObject("message", "Mail đã tồn tại");
			model.setViewName("client/alert");
			return model;
		}
		if (accountService.isExistUserPhone(phone)) {
			model.addObject("show", "show");
			model.addObject("message", "Số điện thoại đã tồn tại ");
			model.setViewName("client/alert");
			return model;
		}
		accountService.addAccount(account);
		model.addObject("show", "show");
		model.addObject("message", "Đăng ký thành công");
		model.setViewName("client/alert");
		return model;
	}
	
	
	//Gọi view cơ cấu giải
	@GetMapping("/co-cau-giai")
	public ModelAndView coCauGiai(Authentication authentication) {
		ModelAndView model = new ModelAndView("client/co-cau-giai");
		if (authentication != null) {
			User userDetails = (User) authentication.getPrincipal();
			String user = userDetails.getUsername();
			model.addObject("userName", user);
			model.addObject("lichsudove", "show");
		} else {
			model.addObject("userName", "guest");
			model.addObject("lichsudove", "none");
		}
		List<PrizeValue> listMN = prizeValueService.getGiaTriGiaiMN();
		List<PrizeValue> listMB = prizeValueService.getGiaTriGiaiMB();
		model.addObject("coCauGiaiMNMT", listMN);
		model.addObject("coCauGiaiMB", listMB);
		
		setDislayLeftSilde(model);
		return model;
	}

	//Gọi view giới thiệu trang web
	@GetMapping("/gioithieu")
	public ModelAndView gioiThieu(Authentication authentication) {
		ModelAndView model = new ModelAndView("client/gioithieu");
		if (authentication != null) {
			User userDetails = (User) authentication.getPrincipal();
			String user = userDetails.getUsername();
			model.addObject("userName", user);
			model.addObject("lichsudove", "show");
		} else {
			model.addObject("userName", "guest");
			model.addObject("lichsudove", "none");
		}
		setDislayLeftSilde(model);
		return model;
	}
	
	@GetMapping("/xskt/{id}")
	public ModelAndView chiTietKQXSTinh(Authentication authentication,
			@PathVariable("id") String idProvince) {
		ModelAndView model = new ModelAndView("client/chi-tiet-kqxs-tinh");
		String mien = provinceService.getProvinceById(idProvince).getArea();
		model.addObject("mien", mien);
		if (authentication != null) {
			User userDetails = (User) authentication.getPrincipal();
			String user = userDetails.getUsername();
			model.addObject("userName", user);
			model.addObject("lichsudove", "show");
		} else {
			model.addObject("userName", "guest");
			model.addObject("lichsudove", "none");
		}
		List<Date> listDates = lotteryResultService.getListKQSXTop4(idProvince);
		
		if (mien.equals("MN")) {
			model.addObject("showMN", "show");
			model.addObject("showMT", "none");
			model.addObject("showMB", "none");
		} else if (mien.equals("MT")) {
			model.addObject("showMN", "none");
			model.addObject("showMT", "show");
			model.addObject("showMB", "none");
		} else {
			model.addObject("showMN", "none");
			model.addObject("showMT", "none");
			model.addObject("showMB", "show");
		}
		
		List<Province> listProvinceMN = provinceService.getProvinceByArea("MN");
		List<Province> listProvinceMT = provinceService.getProvinceByArea("MT");
		List<Province> listProvinceMB = provinceService.getProvinceByArea("MB");
		model.addObject("listProvinceMN", listProvinceMN);
		model.addObject("listProvinceMT", listProvinceMT);
		model.addObject("listProvinceMB", listProvinceMB);	
		for (int i = 0; i < 4; i++) {
			Date date = listDates.get(i);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
			String dateString = sdf.format(date);
			model.addObject("date" + String.valueOf(i), dateString);		
			listLotteryResult(model, idProvince, date, i);
		}
		
		model.addObject("province", provinceService.getProvinceById(idProvince).getNameProvince());
		return model;
	}
	
	private ModelAndView setDislayLeftSilde(ModelAndView model) {
		List<Province> listProvinceMN = provinceService.getProvinceByArea("MN");
		List<Province> listProvinceMT = provinceService.getProvinceByArea("MT");
		List<Province> listProvinceMB = provinceService.getProvinceByArea("MB");
		model.addObject("listProvinceMN", listProvinceMN);
		model.addObject("listProvinceMT", listProvinceMT);
		model.addObject("listProvinceMB", listProvinceMB);
		model.addObject("showMN", "show");
		model.addObject("showMT", "none");
		model.addObject("showMB", "none");
		return model;
	}
	@GetMapping("/user/exportToPdfMN/{dateString}")
    public void exportToPDFMN(HttpServletResponse response, 
    		@PathVariable("dateString") String dateString) throws ParseException, DocumentException, IOException {
        response.setContentType("application/pdf; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=XSKTMN_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        List<Province> provinceList=provinceService.getProvinceByDateByArea(dateString,"MN");
        List<LotteryResults> lotteryResultsList= new ArrayList<>();
        for (Province province : provinceList) {
        	LotteryResults lotteryResults=lotteryResultService.getKetQuaXoSoByProvinceByDate(province.getIdProvince(),sdf.parse(dateString));
            lotteryResultsList.add(lotteryResults);	
		}
        List<PrizeValue> lisPrizeValue = prizeValueService.getGiaTriGiaiMN();
       UserPDFExporterByDate userPDFExporterByDate =new UserPDFExporterByDate(provinceList, lotteryResultsList,lisPrizeValue,dateString );
       userPDFExporterByDate.exportMN(response);       
	}
	
	
	@GetMapping("/user/exportToPdfMT/{dateString}")
    public void exportToPDFMT(HttpServletResponse response, 
    		@PathVariable("dateString") String dateString) throws ParseException, DocumentException, IOException {
        response.setContentType("application/pdf; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=XSKTMT_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);  
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        List<Province> provinceList=provinceService.getProvinceByDateByArea(dateString,"MT");
        System.out.println(provinceList.size());
        List<LotteryResults> lotteryResultsList= new ArrayList<>();
        
        for (Province province : provinceList) {
        	LotteryResults lotteryResults=lotteryResultService.getKetQuaXoSoByProvinceByDate(province.getIdProvince(),sdf.parse(dateString));
            lotteryResultsList.add(lotteryResults);	
		}
        List<PrizeValue> lisPrizeValue = prizeValueService.getGiaTriGiaiMN();
       UserPDFExporterByDate userPDFExporterByDate =new UserPDFExporterByDate(provinceList, lotteryResultsList,lisPrizeValue,dateString );
       userPDFExporterByDate.exportMT(response);       
	}
	
	
	@GetMapping("/user/exportToPdfMB/{dateString}")
    public void exportToPDFMB(HttpServletResponse response, 
    		@PathVariable("dateString") String dateString) throws ParseException, DocumentException, IOException {
        response.setContentType("application/pdf; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=XSKTMB_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);  
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        List<Province> provinceList=provinceService.getProvinceByDateByArea(dateString,"MB");
        System.out.println(provinceList.size());
        List<LotteryResults> lotteryResultsList= new ArrayList<>();
        
        for (Province province : provinceList) {
        	LotteryResults lotteryResults=lotteryResultService.getKetQuaXoSoByProvinceByDate(province.getIdProvince(),sdf.parse(dateString));
            lotteryResultsList.add(lotteryResults);	
		}
        List<PrizeValue> lisPrizeValue = prizeValueService.getGiaTriGiaiMN();
       UserPDFExporterByDate userPDFExporterByDate =new UserPDFExporterByDate(provinceList, lotteryResultsList,lisPrizeValue,dateString );
       userPDFExporterByDate.exportMB(response);       
	}
	
	
	
	
	
}
