package com.ungdungso.config;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ungdungso.model.Province;
import com.ungdungso.model.LotteryResults;
import com.ungdungso.service.LotteryResultService;
import com.ungdungso.service.PrizeCodeService;
import com.ungdungso.service.ProvinceService;


@Component
@EnableScheduling /// https://gpcoder.com/3231-huong-dan-phan-tich-noi-dung-html-su-dung-thu-vien-jsoup/
public class ScheduleTask {
	@Autowired
	ProvinceService provinceService;
	@Autowired
	LotteryResultService lotteryResultService;
	@Autowired
	PrizeCodeService prizeCodeService;

	/*
	 * Tự động lấy KQXS miền Nam từ trang web Minh Ngọc vào lúc 17h00 hằng ngày và
	 * lưu vào database
	 */
	@Scheduled(cron = "0 50 16 * * ?") /// (cron = "0 0 17 * * ?")
	public void scheduleSaveMN() throws InterruptedException, IOException, ParseException {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		String ngayXoSoString = sdf.format(currentDate);
		Date ngayXoSo = sdf.parse(ngayXoSoString);
		List<Province> list = provinceService.getProvinceByDateByArea(sdf.format(currentDate), "MN");
		for (Province province : list) {
			String nameProvince = province.getNameProvince();
			String idProvince = province.getIdProvince();
			String mien = province.getArea();
			String data = crawData(province);
			if (!lotteryResultService.isExistKetQuaXoSo(ngayXoSo, idProvince)) {
				saveTicket(nameProvince, ngayXoSoString, mien, data);
			}
		}

	}

	/*
	 * Tự động lấy KQXS miền Bắc từ trang web Minh Ngọc vào lúc 18h45 hằng ngày và
	 * lưu vào database
	 */

	@Scheduled(cron = "0 45 18 * * ?")
	public void scheduleSaveMB() throws InterruptedException, IOException, ParseException {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		String ngayXoSoString = sdf.format(currentDate);
		Date ngayXoSo = sdf.parse(ngayXoSoString);
		List<Province> list = provinceService.getProvinceByDateByArea(sdf.format(currentDate), "MB");
		for (Province province : list) {
			String nameProvince = province.getNameProvince();
			String idProvince = province.getIdProvince();
			String mien = province.getArea();
			String data = crawData(province);
			if (!lotteryResultService.isExistKetQuaXoSo(ngayXoSo, idProvince)) {
				saveTicket(nameProvince, ngayXoSoString, mien, data);
			}
		}
	}

	/*
	 * Tự động lấy KQXS miền Trung từ trang web Minh Ngọc vào lúc 18h00 hằng ngày và
	 * lưu vào database
	 */
	@Scheduled(cron = "0 0 18 * * ?")
	public void scheduleSaveMT() throws InterruptedException, IOException, ParseException {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		String ngayXoSoString = sdf.format(currentDate);
		Date ngayXoSo = sdf.parse(ngayXoSoString);
		List<Province> list = provinceService.getProvinceByDateByArea(sdf.format(currentDate), "MT");
		for (Province province : list) {
			String nameProvince = province.getNameProvince();
			String idProvince = province.getIdProvince();
			String mien = province.getArea();
			String data = crawData(province);
			if (!lotteryResultService.isExistKetQuaXoSo(ngayXoSo, idProvince)) {
				saveTicket(nameProvince, ngayXoSoString, mien, data);
			}
		}
	}

	/* Hàm craw data từ trang web www.minhngoc.net.vn */
	public String crawData(Province province) throws IOException {
		String data = "";
		String mien = province.getArea();
		String provinceMinhNgoc = "";
		if (mien.equals("MB")) {
			provinceMinhNgoc = "mien-bac";
		} else {
			provinceMinhNgoc = province.getIdProvince();
		}
		String linkString = "https://www.minhngoc.net.vn/getkqxs/" + provinceMinhNgoc + ".js";
		Document doc = Jsoup.connect(linkString).get();
		String[] listNameClass = { "giaidb", "giai1", "giai2", "giai3", "giai4", "giai5", "giai6", "giai7", "giai8" };
		for (int i = 0; i < listNameClass.length; i++) {
			if (mien.equals("MB") && i == 8) {
				break;
			}
			Elements temp = doc.getElementsByClass(listNameClass[i]);
			String t1=temp.get(0).text().trim();
			t1.replaceAll(" ", "");
			System.out.println(t1);
			System.out.println(t1.length());
			System.out.println("-------------");
			data = data + "-" + temp.get(0).text().trim();
		}
		data = data.substring(1);
		String[] arrResult = data.split("-");
		data="";
		for (String result : arrResult) {
			result=result.trim();
			data=data+"-"+result;
		}
		
		data = data.substring(1);
		System.out.println(data);
		
		
		return data;
	}

	/*
	 * Hàm lưu data đã được lấy từ trang www.minhngoc.net.vn và chuẩn hóa thành KQXS
	 * của tỉnh. Sau đó lưu vào trong database
	 */
	public void saveTicket(String nameProvince, String ngayXoSoString, String mien, String data) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		Date ngayXoSo = sdf.parse(ngayXoSoString);
		String idProvince = provinceService.getIDByName(nameProvince);
		LotteryResults lotteryResults = new LotteryResults();
		lotteryResults.setIdProvince(idProvince);
		lotteryResults.setDatePrize(ngayXoSo);
		lotteryResults.setResult(data);
		lotteryResults.setStatusLottery("enable");
		lotteryResultService.addKetQuaXoSo(lotteryResults);
	}
}
