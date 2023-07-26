package com.a2m.gen.controllers.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.TargetMgt;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.repository.sys.sys0103.Sys0103Respository;
import com.a2m.gen.services.TsstUserService;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.sys.Sys0103Service;

@RestController
@RequestMapping(value = "/api/sys/sys0103")
public class Sys0103Controller {
	@Autowired
	private Sys0103Service sys0103Service;

	@Autowired
	private Sys0103Respository sys0103Respository;
	@Autowired

	private PasswordEncoder passwordEncoder;

	@Autowired
	private CommonService commonService;

	@Autowired
	private TsstUserService tsstUserService;
	

	@PostMapping(value = "/save")
	@ResponseBody
	public TsstUser save(@RequestBody TsstUser tsstUser) throws Exception {
		TsstUser tsstUserResponse = sys0103Service.save(tsstUser);
		return tsstUserResponse;
	}

	@PutMapping(value = "/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") String userUid, @RequestBody TsstUser user)
			throws Exception {

		TsstUser currentUser = sys0103Service.findByUserUid(userUid);
		String newPassword = user.getNewPassword();
		if (user.getIsChangePassword()) {
			String encode = passwordEncoder.encode(newPassword);
			currentUser.setPassword(encode);
		}
		currentUser.setStatus(user.isStatus());
		currentUser.setUpdatedDate(new Date());
		currentUser.setUpdatedBy(commonService.getUserUid());

		currentUser.setTsstUserInfo(user.getTsstUserInfo());
		sys0103Service.updateUser(currentUser);
		return new ResponseEntity<TsstUser>(currentUser, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String userUid) {
		try {
			sys0103Service.delete(userUid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

//		@GetMapping("/modifyStatus")
//		public int modifyStt(@RequestParam Map param){
//			return sys0103Service.modifyStatus(param);
//		}

	@GetMapping(value = "/search")
	public ResponseEntity<?> searchByfullName(@RequestParam Map<String, Object> search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Page<TsstUser> paging = sys0103Service.searchByfullNameAndStatus(search);

		response.put("totalItems", paging.getTotalElements());
		response.put("listUser", paging.toList());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	

	@GetMapping(value = "/getTsstUserList")
	public ResponseEntity<?> getTsstUserList(TsstUserModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<TsstUserModel> lst = tsstUserService.getTsstUser(search);	
			response.put(CommonContants.LIST_KEY, lst);
			} catch (Exception e) {
				response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
				response.put(CommonContants.MESSAGES_KEY, e.toString());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findByUserAssist")
	public ResponseEntity<?> findByUserAssist() throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<TsstUser> lst = sys0103Service.findByUserAssist();	
			response.put(CommonContants.LIST_KEY, lst);
			} catch (Exception e) {
				response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
				response.put(CommonContants.MESSAGES_KEY, e.toString());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findByTsstUserList")
	public ResponseEntity<?> findByTsstUserList(List<TsstUserModel> model) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<TsstUser> lst = sys0103Service.findByTsstUserList(model);	
			response.put(CommonContants.LIST_KEY, lst);
			} catch (Exception e) {
				response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
				response.put(CommonContants.MESSAGES_KEY, e.toString());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
