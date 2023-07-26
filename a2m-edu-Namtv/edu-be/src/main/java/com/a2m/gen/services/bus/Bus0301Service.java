package com.a2m.gen.services.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.a2m.gen.repository.bus.Bus0301Repository;

@Service
public class Bus0301Service {
	@Autowired
	Bus0301Repository bus0301Repository;

	public List<Map<String, Object>> getListRecord(Map param) {
		List<Map<String, Object>> listRecord = null;
		String fromDate = param.get("FROM_DT").toString().trim();
		String toDate = param.get("TO_DT").toString().trim();
		try {
			listRecord = bus0301Repository.getRecordByMonth(fromDate,toDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listRecord;
	}

}
