package com.a2m.gen.controllers.gen;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.entities.ProjectMgt;
import com.a2m.gen.services.gen.Gen0501Service;

@RestController
@RequestMapping("/api/gen0501")
public class Gen0501Controller {
	@Autowired
	Gen0501Service gen0501Service;

	@GetMapping(value = "/getListProject/{id}")
	public ResponseEntity<?> getListProject(@PathVariable("id") String userUid) {
		return ResponseEntity.ok(gen0501Service.getListProject(userUid));
	}

	@PostMapping(value = "/save")
	public ResponseEntity<?> addNewProject(@RequestBody ProjectMgt project) throws Exception {
		try {
			gen0501Service.addNewProject(project);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> saveProject(@RequestBody ProjectMgt project) {
		try {
			gen0501Service.saveProject(project);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/checkTargetExists/{id}")
	public ResponseEntity<?> checkTargetExists(@PathVariable("id") String projectId) {
		return ResponseEntity.ok(gen0501Service.checkTargetExists(projectId));
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> deleteProject(@RequestParam String projectId, @RequestParam String dataFilePath) {
		try {
			gen0501Service.deleteProject(projectId, dataFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/search")
	public Map<String, Object> search(@RequestParam Map<String, Object> search) {
		return gen0501Service.search(search);
	}

}
