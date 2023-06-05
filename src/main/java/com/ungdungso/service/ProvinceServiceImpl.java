package com.ungdungso.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ungdungso.model.Province;
import com.ungdungso.repository.ProvinceRepository;


@Service

public class ProvinceServiceImpl implements ProvinceService {
	@Autowired
	ProvinceRepository provinceRepository;

	@Override
	public List<Province> getAllProvince() {
		return (List<Province>) provinceRepository.findAll();
	}

	@Override
	public Province getProvinceById(String id) {
	
		return provinceRepository.getProvinceByID(id);
	}

	@Override
	public void addProvince(Province province) {
		provinceRepository.save(province);	
	}

	@Override
	public void updateProvince(Province province) {
		// TODO Auto-generated method stub
		provinceRepository.save(province);		
	}

	@Override
	public void deleteById(String id) {

		Province province=provinceRepository.getProvinceByID(id);
		provinceRepository.delete(province);
	}

	@Override
	public boolean isExistProvince(String provinceName) {
		if(provinceRepository.getProvinceByName(provinceName)!=null) {return true; }
		return false;
	}

	@Override
	public List<Province> getAllProvinces(String name, int page) {
		return (List<Province>) provinceRepository.getAllByName(name,page*5-5);
	}

	@Override
	public List<Province> getProvincebyDate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
			Calendar cal = Calendar.getInstance();
		    cal.setTime(sdf.parse(date));
			int thu=cal.get(Calendar.DAY_OF_WEEK); 	 
			
			String [] dayStrings= {"CN","T2","T3","T4","T5","T6","T7"};
			String dayOfWeek=dayStrings[thu-1];
			List<Province> list= provinceRepository.getAllByDate("%"+dayOfWeek+"%");
			return list;
		} catch (Exception e) {
		}
		return null;		
	}

	@Override
	public String getThuById(String id) {
		return provinceRepository.queryGetThuById(id);
	}

	@Override
	public String getIDByName(String name) {
		return provinceRepository.queryGetIdByName(name);
	}

	@Override
	public List<Province> getProvinceByArea(String area) {
		return (List<Province>) provinceRepository.queryGetProvinceByArea(area);
	}

	@Override
	public int countProvinceByName(String name) {
		return (int)provinceRepository.countProvinceByName(name);
	}

	@Override
	public List<Province> getProvinceByDateByArea(String date, String area) {	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		Calendar cal = Calendar.getInstance();
	    try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int thu=cal.get(Calendar.DAY_OF_WEEK); 	 
		String [] dayStrings= {"CN","T2","T3","T4","T5","T6","T7"};
		String dayOfWeek=dayStrings[thu-1];
		List<Province> list= provinceRepository.querygetAllByDateByArea("%"+dayOfWeek+"%",area);
		return list;
	}

	@Override
	public String getAreaByName(String name) {
		return provinceRepository.queryGetAreaByName(name);
	}

	@Override
	public Integer countProvinceStatus(String status) {
		// TODO Auto-generated method stub
		return provinceRepository.queryCountProvincStatus(status);
	}
}
