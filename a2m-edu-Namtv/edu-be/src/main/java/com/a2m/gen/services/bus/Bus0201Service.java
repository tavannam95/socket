package com.a2m.gen.services.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.a2m.gen.controllers.bus.Bus0201Controller;
import com.a2m.gen.entities.EventMgt;
import com.a2m.gen.repository.bus.Bus0201Repository;

@Service
public class Bus0201Service {
	@Autowired Bus0201Repository bus0201Repository;
	
	
	public Map<Object,Object> getPage(int page, int size, String keySearch){
		Pageable pageable = PageRequest.of(page, size);
		Page<Map> pageData = null;
		try {
			pageData = bus0201Repository.getPage(pageable, keySearch);
		} catch (Exception e) {
			// TODO: handle exception
		}
		List<Map> lstData = pageData.toList();
		long totalElement = pageData.getTotalElements();
		Map<Object, Object> data = new HashMap<>();
		data.put("totalEvent", totalElement);
		data.put("list", lstData);
		return data;
	}
	
	public int insert(Map param) {
		long id = Long.parseLong(param.get("id").toString().trim());
		String name = param.get("eventName").toString().trim();
		String fromDate = param.get("fromDate").toString().trim();
		String toDate = param.get("toDate").toString().trim();
		int discount = Integer.parseInt(param.get("discount").toString().trim());
		int res = 0;
		try {
			bus0201Repository.insert(id, fromDate, toDate, name, discount);
			res = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
	
	public int modify(Map param) {
		long id = Long.parseLong(param.get("id").toString().trim());
		String name = param.get("eventName").toString().trim();
		String fromDate = param.get("fromDate").toString().trim();
		String toDate = param.get("toDate").toString().trim();
		int discount = Integer.parseInt(param.get("discount").toString().trim());
		int res = 0;
		try {
			bus0201Repository.modify(id, fromDate, toDate, name, discount);
			res = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
	public int delete(Map param) {
		long id = Long.parseLong(param.get("id").toString().trim());
		int res = 0;
		try {
			bus0201Repository.deleteById(id);
			res = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
}
