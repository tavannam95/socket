package com.a2m.gen.controllers.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.QuizDao;
import com.a2m.gen.models.course.QuizItemModel;
import com.a2m.gen.models.course.QuizModel;
import com.a2m.gen.models.course.SubmitQuizModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.course.QuizService;
import com.a2m.gen.utils.CastUtil;

@RestController
@RequestMapping(value = "/api/course/quiz")
public class QuizController {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuizService quizService;
	
	@Autowired
	private CommonService commonService;

	@PostMapping(value = "/save")
	@ResponseBody
	@Transactional(rollbackFor = { Exception.class })
	public Map<Object, Object> save(@RequestBody QuizModel quizModel) throws Exception {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
			response = quizService.saveQuiz(quizModel);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return response;
	}
	
	@PostMapping(value = "/submit")
	@ResponseBody
	@Transactional(rollbackFor = { Exception.class })
	public Map<Object, Object> submit(@RequestBody SubmitQuizModel submitRequest) throws Exception {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
			response = quizService.submit(submitRequest);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return response;
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
			quizService.delete(id);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/getQuizByChapter")
	public ResponseEntity<?> search(QuizModel search) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			List<QuizModel> lst = quizService.getQuizByChapter(search);
			Long totalItems = quizDao.countQuizByChapter(search);

			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
			response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getQuizById/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getQuizById(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			QuizModel detail = quizService.getQuizById(Id);
			response.put(CommonContants.DETAIL_KEY, detail);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getQuizSubmit")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getQuizSubmit(@RequestParam HashMap<String, Long> parameter) {
		Map<String, Object> response = new HashMap<>();
		try {
			Long quizId = CastUtil.castToLong(parameter.get("quizId"));
			Long userInfoId = CastUtil.castToLong(parameter.get("userInfoId"));
			Object detail = quizService.getQuizSubmit(quizId, userInfoId);
			response.put(CommonContants.DETAIL_KEY, detail);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getQuizSubmitStu")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getQuizSubmitStu(@RequestParam HashMap<String, Long> parameter) {
		Map<String, Object> response = new HashMap<>();
		try {
			Long quizId = CastUtil.castToLong(parameter.get("quizId"));
			Long userInfoId = CastUtil.castToLong(parameter.get("userInfoId"));
			Long quizStudentId = CastUtil.castToLong(parameter.get("quizStudentId"));
			Object detail = quizService.getQuizSubmitStu(quizId, userInfoId, quizStudentId);
			response.put(CommonContants.DETAIL_KEY, detail);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListStuSubtmited")
	public ResponseEntity<?> getListStuSubtmited(@RequestParam HashMap<String, Object> parameter) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			Object lst = quizService.getListStuSubtmited(parameter);
			Long totalItems = quizDao.countStuSubtmited(parameter);
			response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListHisQuizStuSubtmited")
	public ResponseEntity<?> getListHisQuizStuSubtmited(@RequestParam HashMap<String, Object> parameter) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			Object lst = quizService.getListHisQuizStuSubtmited(parameter);
			Long totalItems = quizDao.countHisQuizStuSubtmited(parameter);
			response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getStatisResult")
	public ResponseEntity<?> getStatisResult(@RequestParam HashMap<String, Object> parameter) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			HashMap<String, Object> map = quizService.getStatisResult(parameter);
			//Long totalItems = quizDao.countStuSubtmited(parameter);
			//response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.RESULT_KEY, map);
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
			List<QuizItemModel> listQuizItem = quizService.importExcelMulti(file);
			response.put(CommonContants.LIST_KEY, listQuizItem);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/exportPDF/{id}")
	public ResponseEntity<?> exportPDF(HttpServletRequest request, @PathVariable("id") Long Id) {
  	  System.out.println(" ===== exportPDF Controller =====");
		Map<String, Object> response = new HashMap<>();
		try {
			String  path = quizService.exportPDF(Id);
			response.put(CommonContants.DETAIL_KEY, path);
		} catch (Exception e) {
		  e.printStackTrace();
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/deleteFile/{filePath}")
	public ResponseEntity<?> deletePDF(@PathVariable("filePath") String filePath) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		try {
			commonService.deleteFile(filePath);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
			e.printStackTrace();
		}	
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping(value = "/getScaleCorrectByQuizItem/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getScaleCorrectByQuizItem(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Object obj = quizService.getScaleCorrectByQuizItem(Id);
			response.put(CommonContants.DETAIL_KEY, obj);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListQuestionStatistical")
	public ResponseEntity<?> getListQuestionStatistical(@RequestParam HashMap<String, Object> parameter) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			Object lst = quizService.getListQuestionStatistical(parameter);
			Long totalItems = quizDao.countQuestionStatistical(parameter);
			response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
			response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
