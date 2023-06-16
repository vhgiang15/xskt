package com.ungdungso.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.ungdungso.model.Province;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.ungdungso.service.LotteryResultService;
import com.ungdungso.service.ProvinceService;

@Controller
public class ProvinceController {
	@Autowired
	ProvinceService provinceService;
	@Autowired
	LotteryResultService lotteryResultService;
	
	//Gọi form để thực hiện add thêm 1 tỉnh
	@GetMapping("/admin-form-add-province") 
	public ModelAndView formAddProvince() {
		ModelAndView model = new ModelAndView();
		model.addObject("show", "none");
		model.setViewName("admin/form-add-province");
		return model;
	}
	
	
	// Goi form tìm kiếm tỉnh
	@GetMapping("/admin-form-search-province")
	public ModelAndView formUpdateProvince() {
		ModelAndView model = new ModelAndView();
		model.addObject("show", "none");
		model.setViewName("admin/form-search-province");
		return model;

	}
	
	
	// Thực hiện thêm dữ liệu 1 tỉnh mới vào database
	@PostMapping(value = { "/admin-add-province" })
	public ModelAndView addProvince(
			@RequestBody String bodytext,
			@RequestParam(value = "name-province", required = true) String name,
			@RequestParam(value = "idProvince", required = true) String idProvince,
			@RequestParam("area") String area,
			@RequestParam(value ="ma-minh-ngoc", defaultValue = "") String maMinhNgoc) {
		ModelAndView model = new ModelAndView();
		int temp=bodytext.indexOf("thu=");
		String dayOfWeek = "";
		if (temp==0) {
			model.addObject("show", "show");
			model.addObject("message", "Chưa chọn ngày Xổ số trong tuần");
			model.setViewName("admin/form-add-province");
			return model;
		} else {
			dayOfWeek=bodytext.substring(temp);
			dayOfWeek=dayOfWeek.replace("thu=","");
			dayOfWeek=dayOfWeek.replace("&", "");
		}
		Province newProvince= new Province(idProvince, name, dayOfWeek, area, "enable");
		if (provinceService.isExistProvince(name)) {
			model.addObject("show", "show");
			model.addObject("message", "Thêm mới thất bại. Tỉnh " + name + " đã tồn tại");
			model.addObject("province_name", name);
			model.setViewName("admin/form-add-province");
			return model;
		} else {
			provinceService.addProvince(newProvince);
			model.addObject("show", "show");
			model.addObject("message", "Thêm mới tỉnh " + name + " thành công");
			model.addObject("province_name", name);
			model.setViewName("admin/form-add-province");
			return model;
		}
	}


	// Thực hiện tìm kiếm tỉnh theo tiêu chí của form tìm kiếm và trả kết quả về cho view
	@GetMapping("/xskt/admin-search-province")
	public ModelAndView searchProvince(
			@RequestParam(value = "keysearch", defaultValue = "") String provinceName,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		ModelAndView model = new ModelAndView();
		provinceName.trim();
		provinceName = "%" + provinceName + "%";
		List<Province> listProvince = provinceService.getAllProvinces(provinceName, page);
		int totalProvince = provinceService.countProvinceByName(provinceName);
		int totalPage = totalProvince / 5;
		if (totalProvince % 5 != 0) {
			totalPage++;
		}
		model.addObject("listProvince", listProvince);
		model.addObject("indexPage", page);
		model.addObject("totalPage", totalPage);
		model.addObject("show", "none");
		model.setViewName("admin/form-search-province-result");
		return model;

	}

	// Trả về các thông tin liên quan đến tỉnh đã được chọn
	@GetMapping("/xskt/admin-load-info-province")
	public ModelAndView loadInfoProvince(@RequestParam(value = "idProvince", defaultValue = "0") String idProvince) {
		ModelAndView model = new ModelAndView();
		
		System.out.println(idProvince);
		System.out.println("--------------");
		Province currentProvince = provinceService.getProvinceById(idProvince);
		model.setViewName("admin/form-info-province");
		model.addObject("province", currentProvince);
		return model;
	}


	// Cập nhật thông tin của 1 tỉnh
	@PostMapping("/admin-update-province")
	public ModelAndView updateProvince(
			@RequestBody String bodytext,
			@RequestParam("idProvince") String idProvince,
			@RequestParam("available") String available) {
		ModelAndView model = new ModelAndView();
		int indexThu= bodytext.indexOf("thu=");
		
		String thuXoSo = "";
		if (indexThu==0) {
			model.addObject("show", "show");
			model.addObject("message", "Chưa chọn ngày Xổ số trong tuần");
			model.setViewName("admin/form-search-province");
			return model;
		} else {
			thuXoSo=bodytext.substring(indexThu);
			thuXoSo=thuXoSo.replace("thu=","");
			thuXoSo=thuXoSo.replace("&", "");
		}
		Province oldProvince = provinceService.getProvinceById(idProvince);
		oldProvince.setStatus(available);
		oldProvince.setDayOfWeek(thuXoSo);
		provinceService.updateProvince(oldProvince);
		model.addObject("show", "show");
		model.addObject("message", "Cập nhật tỉnh thành công");
		model.setViewName("admin/form-search-province");
		return model;
	}

	// Thực thi thao tác xóa 1 tỉnh
	@GetMapping("/xskt/admin-delete-province")
	public ModelAndView deleteProvince(@RequestParam("idProvince") String idProvince) throws IOException {
		ModelAndView model = new ModelAndView();
		if (lotteryResultService.isExistKetQuaXoSoByProvince(idProvince)) {
			model.addObject("message", "Xóa tỉnh thất bại. Do đã tồn tại KQXS");
			model.addObject("show", "show");
			model.setViewName("admin/alert");
		} else {
			provinceService.deleteById(idProvince);
			model.addObject("message", "Xóa thành công");
			model.addObject("show", "show");
			model.setViewName("admin/alert");
		}
		return model;

	}

	//Load DS tỉnh theo vùng miền được chọn
	@GetMapping("/xskt/admin-load-province")
	public ModelAndView loadProvinceAdmin(@RequestParam("mien") String mien) throws IOException {
		ModelAndView model = new ModelAndView("admin/load-province-byarea");
		List<Province> list = provinceService.getProvinceByArea(mien);
		model.addObject("listProvince",list);
		return model;
	}
	
	@GetMapping("/admin-list-province")
	public String listProvince() {
		return "admin/list-province";
	}
	

	@GetMapping("/load-date")
	public void loadDate(@RequestParam("province") String idProvince, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		List<String> list = lotteryResultService.getDateSaveKQXS(idProvince);
		if (list.size() >= 1) {
			out.println("");
		}
		out.println("<select class='form-select' name='date'>");
		for (String o : list) {
			out.println("<option value=" + o + ">" + o + "</option>");
		}
		out.println("</select>");
	}

}
