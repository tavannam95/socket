package com.a2m.gen.controllers.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.entities.TargetMgt;
import com.a2m.gen.services.bus.Bus0101Service;

@RestController
@RequestMapping("api/bus/bus0101")
public class Bus0101Controller {
	@Autowired Bus0101Service bus0101Service;
	
//	@GetMapping("/list")
//	public List<Map<Object, Object>> getList(@RequestParam Map param){
//		return bus0101Service.getList();
//	}
	
	@GetMapping("/page")
	public Map<Object, Object> getPage(@RequestParam Map param){
		Map result = new HashMap<>();
		try {
			int page = Integer.parseInt(param.get("page").toString());
			int row = Integer.parseInt(param.get("row").toString());
			String keySearch = param.get("keySearch").toString().trim();
			String status = param.get("status").toString().trim();
			result = bus0101Service.getPaging(page, row, keySearch, status);
		} catch (Exception e) {
			// TODO: handle exception
		}
//		int status = Integer.parseInt();
		return result;
	}
	
	@GetMapping("/listCustomer")
	public List<Map<Object, Object>> getListCustomer(){
		return bus0101Service.getListCustomer();
	}
	@GetMapping("/insert")
	public int insert(@RequestParam Map param){
		return bus0101Service.insert(param);
	}
	
	@GetMapping("/modify")
	public int modify(@RequestParam Map param){
		return bus0101Service.modify(param);
	}
	@GetMapping("/modifyStatus")
	public int modifyStt(@RequestParam Map param){
		return bus0101Service.modifyStatus(param);
	}
}
