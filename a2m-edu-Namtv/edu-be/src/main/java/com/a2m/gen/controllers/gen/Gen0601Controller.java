package com.a2m.gen.controllers.gen;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.entities.LicenseMgt;
import com.a2m.gen.services.gen.Gen0601Service;
import com.a2m.gen.utils.AjaxResult;

@RestController
@RequestMapping(value = "api/gen/gen0601")
public class Gen0601Controller {
	@Autowired
	private Gen0601Service gen0601Service;
	
	@GetMapping(value = "search")
	public ResponseEntity<?> search(@RequestParam Map<String, Object> params) {
		try {
			return ResponseEntity.ok(gen0601Service.search(params));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(null);
		}
	}
	
	@PostMapping(value = "saveLicense")
	public ResponseEntity<?> saveLicense(@RequestBody Map<String, Object> arg) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			LicenseMgt license =  gen0601Service.saveLicense(arg);
			ajaxResult.setMessage("Successful");
			ajaxResult.setStatus(true);
			ajaxResult.setResponseData(license);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setMessage("Failed");
			ajaxResult.setStatus(false);
		}
		return ResponseEntity.ok(ajaxResult);
	}
}
