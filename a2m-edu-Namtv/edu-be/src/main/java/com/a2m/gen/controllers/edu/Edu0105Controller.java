package com.a2m.gen.controllers.edu;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.repository.edu.Edu0105Respository;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.edu.Edu0105Service;

@RestController
@RequestMapping(value = "/api/edu/edu0105")
public class Edu0105Controller {
	@Autowired
	private Edu0105Service edu0105Service;

	@Autowired
	private Edu0105Respository edu0105Respository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CommonService commonService;

	@PostMapping(value = "/save")
	@ResponseBody
	public TsstUser save(@RequestBody TsstUser tsstUser) throws Exception {
		TsstUser tsstUserResponse = edu0105Service.save(tsstUser);
		return tsstUserResponse;
	}

	@PutMapping(value = "/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") String userUid, @RequestBody TsstUser user)
			throws Exception {

		TsstUser currentUser = edu0105Service.findByUserUid(userUid);
		String newPassword = user.getNewPassword();
		if (user.getIsChangePassword()) {
			String encode = passwordEncoder.encode(newPassword);
			currentUser.setPassword(encode);
		}
		currentUser.setStatus(user.isStatus());
		currentUser.setUpdatedDate(new Date());
		currentUser.setUpdatedBy(commonService.getUserUid());

		currentUser.setEamTeacherInfo(user.getEamTeacherInfo());
		edu0105Service.updateUser(currentUser);
		return new ResponseEntity<TsstUser>(currentUser, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String userUid) {
		try {
			edu0105Service.delete(userUid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

//		@GetMapping("/modifyStatus")
//		public int modifyStt(@RequestParam Map param){
//			return edu0105Service.modifyStatus(param);
//		}

	@GetMapping(value = "/search")
	public ResponseEntity<?> searchByfullName(@RequestParam Map<String, Object> search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Page<TsstUser> paging = edu0105Service.searchByfullNameAndStatus(search);

		response.put("totalItems", paging.getTotalElements());
		response.put("listUser", paging.toList());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @PostMapping(value = "/deleteListCheck")
	@Transactional
    public ResponseEntity<?> deleteListCheck(@RequestBody List<TsstUserModel> listTsstModel) {
        Map<String, Object> response = new HashMap<>();
        try {
        	edu0105Service.teacherForDel(listTsstModel);
        } catch (Exception e) {
//            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//            response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
}
