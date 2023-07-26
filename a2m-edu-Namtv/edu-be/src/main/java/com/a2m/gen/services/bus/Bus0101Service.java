package com.a2m.gen.services.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.a2m.gen.entities.LicenseMgt;
import com.a2m.gen.entities.TargetMgt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.a2m.gen.repository.bus.Bus0101Repository;

@Service
public class Bus0101Service {
	@Autowired Bus0101Repository bus0101Repository;
	
//	public List<Map<Object, Object>> getList() {
//		return bus0101Repository.getList();
//	}
	
	public Map<Object,Object> getPaging(int page, int size, String keySearch, String status) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Map> pageData = null;
		try {
			pageData = bus0101Repository.getPage(pageable, keySearch, status);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		List<Map> lstData = pageData.toList();
		long totalElement = pageData.getTotalElements();
		Map<Object,Object> data = new HashMap<>();
		data.put("totalItems", totalElement);
		data.put("list", lstData);
		return data;
	}
	
	public List<Map<Object, Object>> getListCustomer() {
		List<Map<Object, Object>> listCustomer = null;
		try {
			listCustomer = bus0101Repository.getListCustomer();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listCustomer;
	}
	
	public int insert(Map param) {
		long id = Long.parseLong(param.get("LICENSE_ID").toString().trim());
		long clientId = Long.parseLong(param.get("CLIENT_ID").toString().trim());
		String insDate = param.get("INS_DT").toString().trim();
		String expDate = param.get("EXPIRED_DT").toString().trim();
		String token = param.get("TOKEN").toString().trim();
		Double price = Double.parseDouble(param.get("PRICE").toString().trim());
		int res = 0;
		try {
			bus0101Repository.insert(id, clientId, insDate, expDate, token, price);
			res = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
	public int modify(Map param) {
		long id = Long.parseLong(param.get("LICENSE_ID").toString().trim());
		String insDate = param.get("INS_DT").toString().trim();
		String expDate = param.get("EXPIRED_DT").toString().trim();
		Double price = Double.parseDouble(param.get("PRICE").toString().trim());
		int res = 0;
		try {
			bus0101Repository.modifyLicense(id, insDate, expDate, price);
			res = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
	public int modifyStatus(Map param) {
		long id = Long.parseLong(param.get("LICENSE_ID").toString().trim());
		int stt = Integer.parseInt(param.get("STATUS").toString().trim());
		int res = 0;
		try {
			bus0101Repository.modifyStatus(id, stt);
			res = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}
}
