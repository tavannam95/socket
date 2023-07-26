package com.a2m.gen.controllers.bus;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.services.bus.Bus0301Service;

@RestController
@RequestMapping("api/bus/bus0301")
public class Bus0301Controller {
	@Autowired
	Bus0301Service bus0301Service;

	@GetMapping("/getRecordByMonth")
	public List<Map<String, Object>> getRecordByMonth(@RequestParam Map param) {
		return bus0301Service.getListRecord(param);
	}

}