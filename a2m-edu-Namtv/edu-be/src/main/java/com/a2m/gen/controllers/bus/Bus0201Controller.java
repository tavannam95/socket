package com.a2m.gen.controllers.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.entities.EventMgt;
import com.a2m.gen.services.bus.Bus0201Service;

@RestController
@RequestMapping("api/bus/bus0201")
public class Bus0201Controller {
	@Autowired Bus0201Service bus0201Service;
	
	@GetMapping("/page")
	public Map<Object,Object> getPage(@RequestParam Map param){
		Map result = new HashMap<>();
		try {
			int page = Integer.parseInt(param.get("page").toString());
			int size = Integer.parseInt(param.get("row").toString());
			String keySearch = param.get("keySearch").toString();
			result = bus0201Service.getPage(page, size,keySearch);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	@GetMapping("/insert")
	public int insert(@RequestParam Map param) {
		return bus0201Service.insert(param);
	}
	
	@GetMapping("/modify")
	public int modify(@RequestParam Map param) {
		return bus0201Service.modify(param);
	}
	
	@GetMapping("/delete")
	public int delete(@RequestParam Map param) {
		return bus0201Service.delete(param);
	}
}