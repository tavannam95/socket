package com.a2m.gen.services.common;

import java.io.File;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.dao.StudentDao;
import com.a2m.gen.entities.TccoFile;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.repository.TsstRoleRepository;
import com.a2m.gen.services.sys.Sys0103Service;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.CommonFileUtils;
import com.a2m.gen.utils.HashUtils;

import io.jsonwebtoken.Jwts;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

@Service
public class CommonService {
	
	private static final String AUTHORIZATION = "authorization";
	
	@Value("${a2m.jwt.secret}")
	private String jwtSecret;
	
	@Autowired
	private TsstRoleRepository roleRepo;
	
	@Autowired
	private TccoFileService tccoFileService;
	
	@Autowired
	private Sys0103Service tsstUserService;
	 
	 @Autowired
	 private StudentDao studentDao;
	
	@Value("${path.upload.dir}")
	private String pathUploadDir;
	
	@Transactional
	public TsstUser findByUserUid(String userUid) {
		TsstUser tsstUser = tsstUserService.findByUserUid(userUid);
		return tsstUser;
	}
	
	public String getUserUidFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String getUserUid() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String accessToken = getTokenFromRequest(request);
		String userUid = getUserUidFromToken(accessToken);
		if (CastUtil.castToString(userUid).equals("")) {
			throw new Exception("USER UID is null!");
		}
		return userUid;
	}
	
	public String getTokenFromRequest(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        String headerValue = "";
        while (headers.hasMoreElements()) {
            headerValue = headers.nextElement();
        }
        if (headerValue != null && !"".equals(headerValue)) {
            String els[] = headerValue.split(" ");
            if (els.length > 1) {
                return els[1];
            }
        }
        return "";
    }
	
	public List<String> getRoles() throws Exception {
		String userUid = getUserUid();
		List<String> roles = roleRepo.getRoles(userUid);

		Map<String, String> temp = new HashMap<String, String>();
		for (String s : roles) {
			if (s != null) {
			String key = s.split("\\$")[0];
			if (key != null) {
				String value = temp.get(key);
				if (value == null || value.isEmpty()) {
					temp.put(key, s);
				} else {
					if (s.split("\\$").length > 1 && s.split("\\$")[1] != null && s.split("\\$")[1].contains("Y")) {
						temp.put(key, s);
					}
				}
			}
			}
		}

		List<String> result = new ArrayList<String>();
		
		for (Map.Entry<String, String> entry : temp.entrySet()) {
			String v = entry.getValue();
			if (v.startsWith("/")) {
				v.substring(1);
			}
			result.add(v);
		}

		return result;
	}
	
	public Map<String, Object> keyInitForRsa() throws NoSuchAlgorithmException{
		return HashUtils.genPublicKeyAndPrivateKey();
	}
	
	public String decryptAccessToken(String accessToken, String privateKey) {
		try {
			return HashUtils.decrypt(accessToken, privateKey);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public TccoFile setDefaultValues(String userUid, String atchFleSeq, MultipartFile multipartFile) throws Exception {
	  System.out.println("===== setDefaultValues ====");
		TccoFile oldTccoFile = CastUtil.castToString(atchFleSeq).equals("") ? null : tccoFileService.findBySequence(atchFleSeq);
		TccoFile tccoFile = new TccoFile();

		if (oldTccoFile == null) {
			tccoFile.setAtchFleSeq(UUID.randomUUID().toString());

			tccoFile.setCreatedDate(new Date());
			tccoFile.setCreatedBy(userUid);
			tccoFile.setUpdatedDate(new Date());
			tccoFile.setUpdatedBy(userUid);
		} else {
			tccoFile.setCreatedDate(oldTccoFile.getCreatedDate());
			tccoFile.setCreatedBy(oldTccoFile.getCreatedBy());
			tccoFile.setUpdatedDate(new Date());
			tccoFile.setUpdatedBy(oldTccoFile.getCreatedBy());
			tccoFile.setAtchFleSeq(atchFleSeq);
		}

		Long fleSz = multipartFile.getSize();
		String fleNm = multipartFile.getOriginalFilename();
		String fileTp = CommonFileUtils.getExt(fleNm);
		String newFleNm = CommonFileUtils.replaceFileName(tccoFile.getAtchFleSeq(), fleNm);

		tccoFile.setFleSz(fleSz.toString());
		tccoFile.setFleNm(fleNm);
		tccoFile.setNewFleNm(newFleNm);
		tccoFile.setFleTp(fileTp);
		tccoFile.setFlePath(newFleNm);

		return tccoFile;
	}
	
	public boolean  deleteFile(String filePath) { 
	    File myObj = new File(pathUploadDir+filePath); 
	    if (myObj.delete()) { 
	      return true;
	    } else {
	      return false;
	    } 
	  } 
	
	public List<Map<String,Object>> getListTypical(ParamBaseModel search) throws Exception {
		List<StudentModel> studentModels = new ArrayList<StudentModel>();
		List<AemStudentEntity> lst = studentDao.getList(search);
		List<Map<String, Object>> stdudentMaps = new ArrayList<>();
		for(AemStudentEntity db :lst) {
			if(db.getTypicalFlag()==true && !db.getDeleteFlag()) {				
				Map<String, Object> stdudentMap = new HashMap<>();
				stdudentMap.put("no", db.getStudentInfoId());
				stdudentMap.put("name", db.getFullName());
				stdudentMap.put("feeling", db.getIdea());
				stdudentMap.put("rate", 5);
				stdudentMap.put("img", db.getImgPath());
				stdudentMap.put("location", "Student");
				stdudentMaps.add(stdudentMap);
			}
		}
		return stdudentMaps;
	}
	
	 public static String unAccent(String s) {
	        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
	        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "d");
	    }
	 
}
