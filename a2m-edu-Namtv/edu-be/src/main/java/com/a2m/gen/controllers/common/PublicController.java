package com.a2m.gen.controllers.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.TccoFile;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.CourseInfoModel;
import com.a2m.gen.models.course.CourseProgramModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.edu.CandidateModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.Edu010202RequestModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.EventModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.TccoFileService;
import com.a2m.gen.services.common.UserInfoService;
import com.a2m.gen.services.course.CourseInfoService;
import com.a2m.gen.services.course.CourseProgramService;
import com.a2m.gen.services.edu.CandidateService;
import com.a2m.gen.services.edu.Edu0102Service;
import com.a2m.gen.services.edu.Edu0203Service;
import com.a2m.gen.utils.CommonFileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

@RestController
@RequestMapping(value = "api/public")
public class PublicController {
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private Edu0102Service edu0102Service;
	
	@Autowired
	private TccoFileService tccoFileService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private Edu0203Service edu0203Service;
	
	 @Autowired 
	 private CourseInfoService courseInfoService;
	 
	 @Autowired 
	 private CourseProgramService courseProgramService;
	 
	 @Autowired
	 private UserInfoService userInfoService;
	
	@Value("${path.upload.dir}")
	private String pathUploadDir;
	
	@GetMapping(value = "/keyInitForRsa")
	public Map<String, Object> keyInitForRsa() throws NoSuchAlgorithmException{
		return commonService.keyInitForRsa();
	}
	
	@GetMapping(value = "/decryptAccessToken")
	public String decryptAccessToken(@RequestParam String accessToken, @RequestParam String privateKey) {
		return commonService.decryptAccessToken(accessToken, privateKey);
	}
	
	@ResponseBody
	@RequestMapping(value = "/imageView/{fileSequence}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.ALL_VALUE }, method = RequestMethod.GET)
	public ResponseEntity<byte[]> imageViewV2(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileSequence") String fileSequence) {
 
		if (StringUtils.isEmpty(fileSequence))
			return null;

		// trim .jpg extension
		fileSequence = fileSequence.split("\\.")[0];

		InputStream is = null;
		try {
			TccoFile tccoFile = tccoFileService.findBySequence(fileSequence);
			if (tccoFile == null || StringUtils.isEmpty(tccoFile.getNewFleNm()))
				return null;
			
			String path = CommonFileUtils.getPathDefaultUploaddir() + File.separator + tccoFile.getNewFleNm();
			File fileToDownload = new File(path);
			if (!fileToDownload.exists() || !fileToDownload.isFile())
				return null;
			
			// get your file as InputStream
			is = new FileInputStream(fileToDownload);
			// copy it to response's OutputStream
			byte[] contents = org.apache.commons.io.IOUtils.toByteArray(is);
			
			HttpHeaders headers = setImageContentType(null, tccoFile);
		    return new ResponseEntity<>(contents, headers, HttpStatus.OK);
		    
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	private HttpHeaders setImageContentType(HttpHeaders headers, TccoFile tccoFile) {
		if(headers == null) {
			headers = new HttpHeaders();
		}
		
		if(tccoFile.getFleTp() != null) {
			String fileType = tccoFile.getFleTp().toLowerCase();
			if("png".equals(fileType)) {
				headers.setContentType(MediaType.IMAGE_PNG);
			}
			else if("jpg".equals(fileType)) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			}
			else if("gif".equals(fileType)) {
				headers.setContentType(MediaType.IMAGE_GIF);
			}
		}
		return headers;
	}
	
	@GetMapping("/downloadFile/{atchFleSeq}")
    @ResponseBody
    public byte[] get(@PathVariable String atchFleSeq) throws Exception {
        if (StringUtils.isEmpty(atchFleSeq))
            return new byte[0];

        TccoFile tccoFile = tccoFileService.findBySequence(atchFleSeq);
        if(tccoFile==null || StringUtils.isEmpty(tccoFile.getNewFleNm())) {
            return new byte[0];
        }

        String newFleNm = tccoFile.getNewFleNm();

        byte []bytes = CommonFileUtils.getFileByPath(pathUploadDir.concat(newFleNm));
        return bytes;
    }
	
    @PostMapping(value = "/candidate/save")
    @ResponseBody
    @Transactional(rollbackFor = { Exception.class })
    public Map<Object, Object> save(@RequestParam(required=false,name="candidate") String candidate,@RequestParam(required=false,name="file") MultipartFile file) throws Exception {
    	Map<Object, Object> res = new HashMap<Object, Object>();
    	System.out.println(candidate.toString());
    	
    	Gson gson = new Gson();
    	CandidateModel  candidateModel = gson.fromJson(candidate, CandidateModel.class);
    	
    	//Start Save File in Sever and TCCO File
    	if(file!=null) {	
    		String userUid = "";
    		String seq = UUID.randomUUID().toString();
    		TccoFile tccoFile = commonService.setDefaultValues(userUid, seq, file);
    		CommonFileUtils.save(pathUploadDir.concat(tccoFile.getNewFleNm()), file);
    		tccoFile = tccoFileService.saveTccoFile(tccoFile);
    		String filePath = tccoFile.getAtchFleSeq();
    		System.out.println("TEST "+tccoFile.getAtchFleSeq());
    		candidateModel.setCandidateFilePath(tccoFile.getAtchFleSeq());
    	}
		//End Save File in Sever and TCCO File
		
    	try {
    		candidateService.saveCandidate(candidateModel);
    	} catch (Exception e) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return res;
    }
    
	@GetMapping(value = "/searchCourses")
	public ResponseEntity<?> search(ParamSearchModel search) {
		Map<String, Object> response = new HashMap<>();
		try {
//		search.setDisplayStatus(true);
		List<Edu0102RequestModel> lst = edu0102Service.getCourses(search);	
		Long count = edu0102Service.getCountCourse(search);
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		response.put(CommonContants.COUNT_ITEMS_KEY, count);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
    @GetMapping(value = "/getCourseById/{id}")
    @Transactional(rollbackFor = { Exception.class })
    public ResponseEntity<?> getCourseById(@PathVariable("id") Long Id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Edu0102RequestModel detail = edu0102Service.getCourseById(Id);
            response.put(CommonContants.DETAIL_KEY, detail);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	@GetMapping(value = "/getListEvent")
	public ResponseEntity<?> getListEvent(ParamBaseModel search)  {
		Map<String, Object> response = new HashMap<>();
		try {
		List<EventModel> lst = edu0203Service.get(search);	
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getListTypical")
	public ResponseEntity<?> getListTypical (ParamBaseModel search) {
		Map<String, Object> response = new HashMap<>();
		try {
		List<Map<String,Object>> lst = commonService.getListTypical(search);	
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.LIST_KEY, lst);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
    @GetMapping(value = "/getCourseInfo")
    public ResponseEntity<?> getCourseInfo(ParamSearchModel args) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
        List<CourseInfoModel> courseInfoModels = courseInfoService.getListCourseInFoByCourseId(args);
        List<CourseProgramModel> courseProgramModels = courseProgramService.getListCourseProgramByCourseId(args);
        
        Edu010202RequestModel res = new Edu010202RequestModel();
        res.setCourseProgram(courseProgramModels);
        if(courseInfoModels.size()>0) {
        	for (CourseInfoModel infoModel : courseInfoModels) {
				res.setCourseInfo(infoModel);
			}
        }
        	
        response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        response.put(CommonContants.LIST_KEY, res);
//        response.put(CommonContants.COUNT_ITEMS_KEY, totalItems);
        } catch (Exception e) {
            response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
            response.put(CommonContants.MESSAGES_KEY, e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
    
	@GetMapping(value = "/getPublicUserInfo")
	public ResponseEntity<?> getPublicUserInfo (ParamSearchModel search) {
		Map<String, Object> response = new HashMap<>();
		try {
		Map<String,Object> userInfo = userInfoService.getUserInfo2(search.getInsUid())	;
		response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		response.put(CommonContants.DETAIL_KEY, userInfo);
		} catch (Exception e) {
			response.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			response.put(CommonContants.MESSAGES_KEY, e.toString());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	

	@GetMapping(value = "/getById/{id}")
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity<?> getById(@PathVariable("id") Long Id) {
		Map<String, Object> response = new HashMap<>();
		try {
			EventModel eventModel = edu0203Service.getbyId(Id);
			response.put(CommonContants.DETAIL_KEY, eventModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
