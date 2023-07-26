package com.a2m.gen.controllers.edu;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.course.QuizItemModel;
import com.a2m.gen.models.edu.CandidateModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.repository.edu.Edu0101Respository;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.edu.Edu0101Service;

@RestController
@RequestMapping(value = "/api/edu/edu0101")
public class Edu0101Controller {
	@Autowired
	private Edu0101Service edu0101Service;

	@Autowired
	private Edu0101Respository edu0101Respository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CommonService commonService;

	@PostMapping(value = "/save")
	@ResponseBody
	public TsstUser save(@RequestBody TsstUser tsstUser) throws Exception {
		TsstUser tsstUserResponse = edu0101Service.save(tsstUser);
		return tsstUserResponse;
	}

    @PostMapping(value = "/saveForExcel")
    @ResponseBody
    public List<TsstUser> saveForExcel(@RequestBody List<TsstUser> listTsstUser) throws Exception {
        List<TsstUser> listTsstUserResponse = edu0101Service.saveForExcel(listTsstUser);
        return listTsstUserResponse;
    }

	@PutMapping(value = "/user/{id}")
//	@Transactional
	public ResponseEntity<?> updateUser(@PathVariable("id") String userUid, @RequestBody TsstUser user)
			throws Exception {

		TsstUser currentUser = edu0101Service.findByUserUid(userUid);
		try {
			String newPassword = user.getNewPassword();
			if (user.getIsChangePassword()) {
				String encode = passwordEncoder.encode(newPassword);
				currentUser.setPassword(encode);
			}
			currentUser.setStatus(user.isStatus());
			currentUser.setUpdatedDate(new Date());
			currentUser.setUpdatedBy(commonService.getUserUid());

			currentUser.setEamStudentInfo(user.getEamStudentInfo());

			currentUser.setListClassChecked(user.getListClassChecked());
			currentUser.setListCourseChecked(user.getListCourseChecked());
			edu0101Service.updateUser(currentUser);
		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}
		return new ResponseEntity<TsstUser>(currentUser, HttpStatus.OK);
	}

    @GetMapping(value = "/getTsstUserByUserInfoId/{id}")
    @Transactional
    public ResponseEntity<?> getTsstUserByUserInfoId(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            TsstUser detail = edu0101Service.getTsstUserByUserInfoId(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "/deleteListCheck")
	@Transactional
    public ResponseEntity<?> deleteListCheck(@RequestBody List<TsstUserModel> listTsstModel) {
        Map<String, Object> response = new HashMap<>();
        try {
        	edu0101Service.studentForDel(listTsstModel);
        } catch (Exception e) {
//            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//            response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
	@DeleteMapping(value = "/delete/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable("id") String userUid) {
		try {
			edu0101Service.delete(userUid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

//		@GetMapping("/modifyStatus")
//		public int modifyStt(@RequestParam Map param){
//			return edu0101Service.modifyStatus(param);
//		}

	@GetMapping(value = "/search")
	public ResponseEntity<?> searchByfullName(@RequestParam Map<String, Object> search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Page<TsstUser> paging = edu0101Service.searchByfullNameAndStatus(search);

		response.put("totalItems", paging.getTotalElements());
		response.put("listUser", paging.toList());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListStudentInprogress")
	public ResponseEntity<?> getListStudentInprogress(@RequestParam Map<String, Object> search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<StudentModel> lst = edu0101Service.getListStudentInprogress();
		Long count = edu0101Service.getCountStudentInprogress();
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getListStudentByUserUid")
	public ResponseEntity<?> getListStudentByUserUid(ParamSearchModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<StudentModel> lst = edu0101Service.getListStudentByUserUid(search.searchText);
		Long count = edu0101Service.getCountStudentByUserUid(search.searchText);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @PostMapping("/importExcel")
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) {
        String message = "";
        Map<String, Object> response = new HashMap<>();
        try {
            List<StudentModel> listStudent = edu0101Service.importExcelMulti(file);
            response.put(CommonContants.LIST_KEY, listStudent);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/exportExcel")
    public ResponseEntity<?> exportPDF(@RequestBody List<TsstUser> tsstUser) {
      System.out.println(" ===== exportPDF Controller =====");
        Map<String, Object> response = new HashMap<>();
        try {
            String  path = edu0101Service.exportExcel(tsstUser);
            response.put(CommonContants.DETAIL_KEY, path);
        } catch (Exception e) {
          e.printStackTrace();
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

	
    @PostMapping(value = "/exportExcelList")
    public ResponseEntity<?> exportExcelList(@RequestBody StudentModel search) {
        Map<String, Object> response = new HashMap<>();
        try {
            String  path = edu0101Service.exportList(search);
            response.put(CommonContants.DETAIL_KEY, path);
        } catch (Exception e) {
          e.printStackTrace();
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	
}
