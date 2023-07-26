package com.a2m.gen.controllers.gen;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.entities.TargetMgt;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.gen.Gen0101Service;

@RestController
@RequestMapping(value = "/api/gen0101")
@CrossOrigin
public class Gen0101Controller {
	@Autowired
	private Gen0101Service gen0101Service;

	@Autowired
	private CommonService commonService;

	@PutMapping(value = "/update")
	public ResponseEntity<?> updateTarget(@RequestBody TargetMgt targetMgt) throws Exception {
		try {
			gen0101Service.updateTarget(targetMgt);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<?> saveTarget(@RequestBody TargetMgt targetMgt) throws Exception {
		try {
			targetMgt.setUserUid(commonService.getUserUid());
			gen0101Service.saveTarget(targetMgt);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteTarget(@PathVariable("id") String targetId) {
		try {
			gen0101Service.deleteTarget(targetId);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/search")
	public ResponseEntity<?> search(@RequestParam Map<String, Object> search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			Page<TargetMgt> paging = gen0101Service.search(search);
			response.put("totalItems", paging.getTotalElements());
			response.put("listTarget", paging.toList());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(search, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
