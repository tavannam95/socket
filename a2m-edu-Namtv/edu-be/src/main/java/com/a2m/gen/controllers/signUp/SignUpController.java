package com.a2m.gen.controllers.signUp;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.services.SignUpService;
import com.a2m.gen.utils.AjaxResult;

@RestController
@RequestMapping("")
public class SignUpController {
	@Autowired SignUpService signUpService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> insertUser(@RequestBody TsstUser param) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			signUpService.insertUser(param);
			ajaxResult.setStatus(true);
			ajaxResult.setMessage("Sign-up successfull");
		} catch (SQLException e) {
			e.printStackTrace();
			ajaxResult.setStatus(false);
			ajaxResult.setMessage("Sign-up failed");
		}
		return ResponseEntity.ok(ajaxResult);
	}
//	@GetMapping("/api/public/lstPos")
//	List<Map<Object, Object>> getListPos(){
//		return signUpService.getListPosition();
//	}
}
