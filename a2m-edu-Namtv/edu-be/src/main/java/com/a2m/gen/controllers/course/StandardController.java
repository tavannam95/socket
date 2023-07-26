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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.StandardDao;
import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.course.SubjectStandardHistoryModel;
import com.a2m.gen.models.course.SubjectStandardModel;
import com.a2m.gen.models.course.SubjectStandardNoteModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.course.StandardService;

@RestController
@RequestMapping(value = "/api/course/standard")
public class StandardController {
    @Autowired
    private CommonService commonService;

//	 @Autowired 
//	 private StandardDao standardDao;
//	 
    @Autowired
    private StandardService standardService;

//    @PostMapping(value = "/saveOld")
//    @ResponseBody
//    @Transactional(rollbackFor = { Exception.class })
//    public Map<Object, Object> saveOld(@RequestBody List<SubjectStandardModel> arg) throws Exception {
//        Map<Object, Object> res = new HashMap<Object, Object>();
//        try {
//            res = standardService.saveStandard(arg);
//
//        } catch (Exception e) {
//            res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//            res.put(CommonContants.MESSAGES_KEY, e.toString());
//        }
//        return res;
//    }
    
    @PostMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestBody List<SubjectStandardHistoryModel> arg) throws Exception {
        Map<Object, Object> res = new HashMap<Object, Object>();
        try {
            res = standardService.saveStandardHistory(arg);

        } catch (Exception e) {
            res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            res.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return res;
    }

    @GetMapping(value = "/getStandardBySubjectId/{id}/{standType}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getStandardBySubjectId(@PathVariable("id") Long Id,
            @PathVariable("standType") String standType) {
        Map<String, Object> response = new HashMap<>();
        try {

            List<SubjectStandardModel> detail = standardService.getStandardById(standType, Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getStandardHistoryBySubjectId/{id}/{standType}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getStandardHistoryBySubjectId(@PathVariable("id") Long Id,
            @PathVariable("standType") String standType) {
        Map<String, Object> response = new HashMap<>();
        try {
        	
        	SubjectStandardHistoryModel param = new SubjectStandardHistoryModel();
    		param.setSubjectId(Id);
    		param.setStandType(standType);
        	
            List<SubjectStandardHistoryModel> detail = standardService.getStandardHistory(param);
            
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getListStandardBySubjectId/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getStandardBySubjectId(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {

            List<SubjectStandardModel> detail = standardService.getListStandardById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getListStandHistoryBySubjectId/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getListStandHistoryBySubjectId(@PathVariable("id") Long subjectId) {
        Map<String, Object> response = new HashMap<>();
        try {

            List<SubjectStandardHistoryModel> detail = standardService.getListStandHistoryBySubjectId(subjectId);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/saveNote")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> saveNote(@RequestBody List<SbjChapterModel> arg) throws Exception {
        Map<Object, Object> res = new HashMap<Object, Object>();
        try {
            res = standardService.saveStandardNote(arg);

        } catch (Exception e) {
            res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            res.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return res;
    }
    
    @PostMapping(value = "/saveNoteHistory")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> saveNoteHistory(@RequestBody List<SbjChapterModel> arg) throws Exception {
        Map<Object, Object> res = new HashMap<Object, Object>();
        try {
            res = standardService.saveStandardNoteHistory(arg);

        } catch (Exception e) {
            res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            res.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return res;
    }

    @GetMapping(value = "/getListStandardNoteBySubjectId/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getListStandardNoteBySubjectId(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {

            List<SubjectStandardNoteModel> detail = standardService.getListStandardNoteById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/exportPDF/{id}")
    public ResponseEntity<?> exportPDF(HttpServletRequest request, @PathVariable("id") String Id) {
        System.out.println(" ===== exportPDF Controller =====");
        Map<String, Object> response = new HashMap<>();
        try {
            String path = standardService.exportPDFStandSummaryBySubjectId(Id);
            response.put(CommonContants.DETAIL_KEY, path);
        } catch (Exception e) {
            e.printStackTrace();
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/exportDoc/{id}")
    public ResponseEntity<?> exportDoc(HttpServletRequest request, @PathVariable("id") String Id) {
        System.out.println(" ===== exportPDF Controller =====");
        Map<String, Object> response = new HashMap<>();
        try {
            String path = standardService.exportPDFStandSummaryBySubjectId(Id);
            response.put(CommonContants.DETAIL_KEY, path);
        } catch (Exception e) {
            e.printStackTrace();
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getLectureScheduleBySubjectId/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getLectureScheduleBySubjectId(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<SbjChapterModel> list = standardService.getLectureScheduleBySubjectId(Id);
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
            response.put(CommonContants.LIST_KEY, list);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getLectureScheduleHistoryBySubjectId/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getLectureScheduleHistoryBySubjectId(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<SbjChapterModel> list = standardService.getLectureScheduleHistoryBySubjectId(Id);
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
            response.put(CommonContants.LIST_KEY, list);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
