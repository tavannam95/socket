package com.a2m.gen.services.edu;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.dao.ClassDao;
import com.a2m.gen.dao.ClassStudentMapDao;
import com.a2m.gen.dao.CourseDao;
import com.a2m.gen.dao.StudentCourseDao;
import com.a2m.gen.dao.StudentDao;
import com.a2m.gen.dao.TsstUserDao;
import com.a2m.gen.dto.TsstUserRoleDto;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.AemStudentCourseMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.EamStudentInfo;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.edu.CandidateModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.ClassStudentMapModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.EventModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.repository.edu.Edu01012Respository;
import com.a2m.gen.repository.edu.Edu0101Respository;
import com.a2m.gen.repository.sys.sys0102.Sys0102Repository;
import com.a2m.gen.repository.sys.sys0103.Sys0103Respository;
import com.a2m.gen.services.common.ComSeqService;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.ExcelService;
import com.a2m.gen.services.common.ExportToExcelService;
import com.a2m.gen.services.sys.Sys0103Service;
import com.a2m.gen.utils.CastUtil;

@Service
public class Edu0101ServiceImpl implements Edu0101Service {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private Edu0101Respository edu0101Respository;

	@Autowired
	private Edu01012Respository edu01012Respository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ComSeqService comSeqService;

	@Autowired
	private Sys0102Repository sys0102Repo;

	@Autowired
	private Sys0103Service sys0103Service;

	@Autowired
	private ClassStudentMapDao classStudentMapDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private ClassDao classDao;

	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private TsstUserDao tsstUserDao;

    @Autowired
    private ExcelService excelService;

	@Autowired 
	private StudentCourseDao studentCourseDao;
	
    @Autowired 
    private ExportToExcelService exportToExcelService;
	private Class<? extends ClassDao> AemClas;

    @Value("${path.upload.dir}")
    private String pathUploadDir;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
	@Transactional
	public TsstUser save(TsstUser tsstUser) throws Exception {
//		String studentCode = genStudentCode(tsstUser);
		Date dt = new Date();
		Long studentId = null;
		if (tsstUser.getUserUid() == null) {
			EamStudentInfo eamStudentInfo = tsstUser.getEamStudentInfo();
			eamStudentInfo.setTsstUser(tsstUser);
			tsstUser.setUserUid(comSeqService.getSeq("SEQ_USER_UID"));

			tsstUser.getEamStudentInfo().setTwoFAEnable(false);

			String password = tsstUser.getPassword();
			String encode = passwordEncoder.encode(password);
			tsstUser.setPassword(encode);
			tsstUser.setCreatedBy(commonService.getUserUid());
			tsstUser.setCreatedDate(new Date());
			tsstUser.setStatus(true);
			
			//gen studentcode
			eamStudentInfo.setStudentCode("0");
			entityManager.persist(eamStudentInfo);
			eamStudentInfo = setStudentCode(tsstUser,eamStudentInfo);
			//gen studentcode
			
			//check duplicate
			String userId = tsstUser.getUserId();
			TsstUserModel model = new TsstUserModel();
			model.setUserId(userId);
			List<TsstUser> tsstUserList = tsstUserDao.getTsstUserList(model);
			if(tsstUserList != null) {				 
				if(tsstUserList.size()==1) {
					return null;
				}
			}
			//check duplicate
			 
			Long eamStudentInfoId = eamStudentInfo.getStudentInfoId();
			edu0101Respository.saveStudent(tsstUser, eamStudentInfo);

			insertRoleForStudent(tsstUser.getUserUid());

			studentId = eamStudentInfo.getStudentInfoId();
		} else {
			EamStudentInfo eamStudentInfo = tsstUser.getEamStudentInfo();
			Long eamStudentInfoId = eamStudentInfo.getStudentInfoId();

			studentId = eamStudentInfo.getStudentInfoId();
			edu01012Respository.modify(eamStudentInfoId, 
					eamStudentInfo.getFullName(), 
					eamStudentInfo.getDob(),
					eamStudentInfo.getEmail(), 
					eamStudentInfo.getCellPhone(),
					eamStudentInfo.getAddress(),
					eamStudentInfo.getGender(),
					eamStudentInfo.getImgPath(),
					eamStudentInfo.getEmailVerifyKey(),
					eamStudentInfo.getTwoFAEnable(), 
					eamStudentInfo.getTwoFAKey(),
					eamStudentInfo.getOrganization(),
	                eamStudentInfo.getPosition(),
	                eamStudentInfo.getDeleteFlag(),
	                eamStudentInfo.getTypicalFlag(),
	                eamStudentInfo.getIdea()
	                );
			
			
			edu0101Respository.modify(tsstUser.getUserUid(), tsstUser.getUpdatedBy(),

					tsstUser.getUpdatedDate(), tsstUser.getPassword(),

					tsstUser.isStatus(), tsstUser.getUserId(),

					tsstUser.getUserType(),

					eamStudentInfoId);
		}
//		insert/ update in classstudentMap
		StudentModel studentModel = new StudentModel();
		studentModel.setKey(studentId);
		AemStudentEntity student = studentDao.getStudent(studentModel);	
		
//		1. lấy ra list class student map với studentId
		List<AemClassStudentMap> classStuMaps = student.getListOfClassStudentMap();

//		2. for list class truyền lên từ front end, lọc ra được class id nào đã tồn tại, class id nào xóa đi, class id nào cần thêm mới.
		String listClassCh = tsstUser.getListClassChecked();
		String[] ListClassChecked = listClassCh.split(",");
		List<String> listClassCheckedId = Arrays.asList(ListClassChecked);
		
		//Delete old List  Class Student
		List<Long> ListOldClassStudentIds = new ArrayList<Long>();
		for (AemClassStudentMap aemClassStudentMap : classStuMaps) {
			
			ListOldClassStudentIds.add(aemClassStudentMap.getTableId());
		}
		for (Long element : ListOldClassStudentIds) {
//			classStudentMapDao.deleteClassStudent(element);
			edu01012Respository.deleteClassStudentMap(CastUtil.castToString(element));
		}
		
		//Add  New List Class Student
		ClassStudentMapModel classStudentMapModel = new ClassStudentMapModel();
		student.setListOfClassStudentMap(new ArrayList<AemClassStudentMap>());
		StudentModel students = new StudentModel(student);
		classStudentMapModel.setStudentModel(students);
		if(listClassCheckedId.size()!=0) {			
			for (String element : listClassCheckedId) {	
				if(element!="" && element != null) {	
					edu01012Respository.insertClassStudentMap(element, CastUtil.castToString(studentId));
				}
			}
		}
		
//		saveStudentCourseMap(tsstUser,student,studentId);
		
		return tsstUser;

	}

	@Transactional
    public List<TsstUser> saveForExcel(List<TsstUser> listTsstUser) throws Exception {
        for (TsstUser tsstUser : listTsstUser) {
            
            String fullName = commonService.unAccent(tsstUser.getEamStudentInfo().getFullName());
//            String userId = commonService.unAccent(fullName);
            fullName = fullName.trim();

            List<String> listString = new ArrayList<String>();
            for(String f : fullName.split("\\s", 0)) {
                listString.add(f);
            }
            String name = listString.get(listString.size()-1);
            for(int i = 0; i < (listString.size() - 1); i++) {
                Character s = listString.get(i).charAt(0);
                name = name + s;
            }
            String userId = name.toLowerCase();
            List<String> tsstUserCheck = edu0101Respository.checkUserId(userId);
            List<String> userIdList = new ArrayList<String>();
            int idx = 1;
            if(tsstUserCheck.size() != 0) {  
                for(String user: tsstUserCheck) {
                    String checkString = user.substring(userId.length());
                    Pattern pattern = Pattern.compile("\\d*");
                    Matcher matcher = pattern.matcher(checkString);
                    if (matcher.matches()) {
                        userIdList.add(user);
                    }
                }
                String nameTest = "";
                String subString1 = "";
                if(userIdList.size() > 0) {
//                	if(userIdList.size() > 1) {
                	System.out.println(userIdList.size());
                    	nameTest = userIdList.get(userIdList.size()-1);
//                    }else {
//                    	nameTest = userIdList.get(userIdList.size());	
//                    }
                	subString1 = nameTest.substring(userId.length());
                }else {
                	nameTest = userId;
                }
                if(nameTest.equals(userId)) {
                	userId = nameTest;
                }else{
                	if(subString1.equals("")) {
                        userId = userId + idx;
                    }else {
                        idx = Integer.parseInt(subString1) + idx;
                        userId = userId + idx;
                    }
                }
            }
            tsstUser.setUserId(userId);
            save(tsstUser);
        }
        return listTsstUser;
    }
    
//	public void saveStudentCourseMap(TsstUser tsstUser , AemStudentEntity student ,Long studentId) throws Exception{
//		List<AemStudentCourseMap> listStuCouMap = student.getListStudentCourseMap();
//		
//		//Delete old List   Student Course
//		edu01012Respository.deleteStuCouByStudenInfoId(studentId.toString());
//		
//		String listCourseCk = tsstUser.getListCourseChecked();
//		String[] listCourseChecked = listCourseCk.split(",");
//		List<String> listCourseCheckedId = Arrays.asList(listCourseChecked);
//		
//		//Add  New List Student Course
//		String ins_uid = tsstUser.getUserUid();
//		Date ins_dt = new Date();  
//		
//		for (String courseId : listCourseCheckedId) {
//			if(courseId!="" && courseId != null) {	
//				edu01012Respository.insertStuCouMap(studentId.toString(), courseId,ins_uid,ins_dt);
//			}
//		}
//	}
	
	public EamStudentInfo setStudentCode(TsstUser tsstUser,EamStudentInfo eamStudentInfo) {

//		get class code
		String listClassChecked = tsstUser.getListClassChecked();
		String classId = listClassChecked.split(",")[0];
		ClassModel  classModel = new ClassModel();
		classModel.setKey(Long.parseLong(classId));
		AemClass classEntity = classDao.getClassEntity(classModel);
		String classCode = classEntity.getClassCode();
		
//      get course code
      String courseId = classEntity.getCourse().getCourseId().toString();
      Edu0102RequestModel courseModel = new Edu0102RequestModel();
      courseModel.setKey(Long.parseLong(courseId));
      AemCourse course = courseDao.getCourse(courseModel);
      String courseCode = course.getCourseCode();
      
//		get StudentId
	    Long studentInfoId = eamStudentInfo.getStudentInfoId();
	    
	    String studentId = String.format("%05d", studentInfoId);
	    
	    String studentCode = courseCode+"-"+classCode+"-"+studentId;
	    eamStudentInfo.setStudentCode(studentCode);

	    entityManager.persist(eamStudentInfo);
		
		return eamStudentInfo;
	}

	public TsstUser findByUserUid(String userUid) {
		TsstUser tsstUser = edu0101Respository.findByUserUid(userUid);
		return tsstUser;
	}

//	public List<TsstUser> getListUser(String userUid) {
//		return edu0101Respository.findByUserUId(userUid);
//	}

	public void updateUser(TsstUser tsstUser) throws Exception {
		save(tsstUser);

	}

	public Boolean studentForDel(List<TsstUserModel> listTsstModel) throws Exception {
		for(TsstUserModel student : listTsstModel) {
			String id = student.getUserUid().toString();
			delete(id);
		}
		return true;
	}

	public void delete(String userUid) throws Exception {
		Optional<TsstUser> user = edu0101Respository.findById(userUid);
		EamStudentInfo eamStudentInfo = user.get().getEamStudentInfo();
        Long eamStudentInfoId = eamStudentInfo.getStudentInfoId();
        
        eamStudentInfo.setDeleteFlag(true);
        edu01012Respository.modify(eamStudentInfoId,
                eamStudentInfo.getFullName(), 
                eamStudentInfo.getDob(),
                eamStudentInfo.getEmail(), 
                eamStudentInfo.getCellPhone(),
                eamStudentInfo.getAddress(),
                eamStudentInfo.getGender(),
                eamStudentInfo.getImgPath(),
                eamStudentInfo.getEmailVerifyKey(),
                eamStudentInfo.getTwoFAEnable(), 
                eamStudentInfo.getTwoFAKey(),
                eamStudentInfo.getOrganization(),
                eamStudentInfo.getPosition(),
                eamStudentInfo.getDeleteFlag(),
		        eamStudentInfo.getTypicalFlag(),
		        eamStudentInfo.getIdea());
//        Long studentId = eamStudentInfo.getStudentInfoId();
        //String str = Long.toString(studentId);
		//deleteRoleForStudent(str);
		//edu0101Respository.delete(user.get());
        StudentModel studentModel = new StudentModel();
        studentModel.setKey(eamStudentInfoId);
        AemStudentEntity student = studentDao.getStudent(studentModel);
        List<AemClassStudentMap> studentMap = student.getListOfClassStudentMap();
        for (AemClassStudentMap element : studentMap) {
            studentDao.deleteClassStudentMap(element.getTableId());
       }
//        Delete StudentCourse Map
//        List<AemStudentCourseMap> studentCourseMaps = student.getListStudentCourseMap();
//        for (AemStudentCourseMap aemStudentCourseMap : studentCourseMaps) {
//        	studentCourseDao.delete(aemStudentCourseMap.getTableId());
//		}
	}

//	public int modifyStatus(Map param) {
//		String id = param.get("USER_UID").toString().trim();
//		int stt = Integer.parseInt(param.get("STATUS").toString().trim());
//		int res = 0;
//		try {
//			edu0101Respository.modifyStatus(id, stt);
//			res = 1;
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return res;
//	}

	public Page<TsstUser> searchByfullNameAndStatus(Map<String, Object> tsstUser) {
		int page = Integer.parseInt(tsstUser.get("page").toString());
		int rows = Integer.parseInt(tsstUser.get("rows").toString());
		String fullName = (tsstUser.get("fullName") != null) ? tsstUser.get("fullName").toString() : null;
		String status = (tsstUser.get("status") != null) ? tsstUser.get("status").toString() : null;
		String classId = (tsstUser.get("classId") != null) ? tsstUser.get("classId").toString() : null;
		//Boolean deleteFlag = false;
		Pageable pageable = PageRequest.of(page, rows);
		//Page<TsstUser> aaa = edu0101Respository.findByfullNameAndStatus(fullName, status, pageable, deleteFlag);
		return edu0101Respository.findByfullNameAndStatus(fullName, status, classId, pageable);
	}

	public void insertRoleForStudent(String studentUserUid) throws SQLException {
		TsstUserRoleDto userRole = new TsstUserRoleDto();
		userRole.setRoleId("R014");
		userRole.setUserUid(studentUserUid);

		insertTsstUserRole(userRole);
	}

//	public void deleteRoleForStudent(String studentUserUid) throws SQLException {
//		TsstUserRoleDto userRole = new TsstUserRoleDto();
//		userRole.setRoleId("R014");
//		userRole.setUserUid(studentUserUid);
//		
//		deleteTsstUserRole(userRole);
//	}

	public int insertTsstUserRole(TsstUserRoleDto userRole) throws SQLException {
		return sys0102Repo.insertTsstUserRole(userRole);
	}

	public int deleteTsstUserRole(TsstUserRoleDto userRole) throws SQLException {
		return sys0102Repo.deleteTsstUserRole(userRole);
	}
	
	public List<AemStudentEntity> getList(ParamSearchModel model){
		return	studentDao.getList(model);
	}
	
	public List<StudentModel> getListStudentInprogress (){
		List<StudentModel> result = new ArrayList<>();
		List<AemStudentEntity> list = studentDao.getListStudentInprogress();
		if(list.size()>0) {
			for (AemStudentEntity aemStudentEntity : list) {
				StudentModel model = new StudentModel(aemStudentEntity);
				result.add(model);
			}
		}
		return result;
	}
	
	public Long getCountStudentInprogress (){
		return studentDao.getCountStudentInprogress();
	}
	
	public List<StudentModel> getListStudentByUserUid (String userUid) throws Exception{
		List<StudentModel> result = new ArrayList<>();
		List<AemStudentEntity> list = studentDao.getListStudentByUserUid(userUid);
		if(list.size()>0) {
			for (AemStudentEntity aemStudentEntity : list) {
				StudentModel model = new StudentModel(aemStudentEntity);
				result.add(model);
			}
		}
		return result;
	} 
	
	public Long getCountStudentByUserUid (String userUid){
		return studentDao.getCountStudentByUserUid(userUid);
	}

    public static final int STUDENT_NAME = 2;
    public static final int DOB = 3;
    public static final int EMAIL = 4;
    public static final int PHONE = 5;
    public static final int ADDRESS = 6;
    public static final int GENDER = 7;

    public String getCellValue(List<Cell> cells, int col) {

        return excelService.getCellValue(cells.get(col - 1));
    }

    @Transactional
    public List<StudentModel> importExcelMulti(MultipartFile file) {
        List<List<Cell>> rows = excelService.ReadExcel(file, true);
        List<StudentModel> ListStudent = new ArrayList<StudentModel>();
        try {

            for (int i = 0; i < rows.size(); i++) {
                List<Cell> cells = rows.get(i);
                        
                StudentModel StudentModel = new StudentModel();
                if(cells.size() >= STUDENT_NAME) {
                    StudentModel.setFullName(getCellValue(cells, STUDENT_NAME));
                }
                if(cells.size() >= DOB) {
                    String date = getCellValue(cells, DOB);
                    date += " 07:00:00";
                    Date bod = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);
                    StudentModel.setDob(bod);
                }
                if(cells.size() >= EMAIL) {
                    StudentModel.setEmail(getCellValue(cells, EMAIL));
                }
                if(cells.size() >= PHONE) {
                    StudentModel.setCellPhone(String.valueOf(getCellValue(cells, PHONE)));
                }
                if(cells.size() >= ADDRESS) {
                    StudentModel.setAddress(getCellValue(cells, ADDRESS));
                }
                if(cells.size() >= GENDER) {
                    String string = getCellValue(cells, GENDER);
                    if(string != null) {
                        Boolean gender = null;
                        if(string == "Nam") gender = true;
                        if(string == "Nữ") gender = false;
                        StudentModel.setGender(gender);
                    }
                }
                                
                if(!StudentModel.getFullName().equals("")) ListStudent.add(StudentModel);
                if(getCellValue(cells, 1).equals("")) break;
            }
    
            return ListStudent;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ListStudent;
    }

    public String exportExcel(List<TsstUser> tsstUser) throws Exception {
        String path = exportToExcelService.exportExcel(tsstUser);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return path +"!#@"+ currentDateTime;
    }

    public String exportList(StudentModel searchModel) throws Exception {
    	List<StudentModel> listModel = new ArrayList<>();
    	List<TsstUserModel> listTsstUserModel = new ArrayList<>();
    	if(searchModel.getIsExportAll()==true) {
 		List<AemStudentEntity> students = studentDao.getList(searchModel);
	 		for (AemStudentEntity student : students) {
	 			StudentModel model = new StudentModel(student);
					listModel.add(model);
				}
    		
    	}else {
       	 listModel = getListModelByListId(searchModel.getListCheckedId());
		}
    	for(StudentModel model : listModel) {
    		TsstUserModel tsstUserModel = new TsstUserModel();
    		tsstUserModel.setUserInfoId(model.getKey());
    		tsstUserModel.setUserType("STU");
    		listTsstUserModel.add(tsstUserModel);
    	}
		List<TsstUser> LisstTsstUser = sys0103Service.findByTsstUserList(listTsstUserModel);
        String path = exportExcelList(LisstTsstUser);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        return path +"!#@"+ currentDateTime;
    }

    public List<StudentModel> getListModelByListId( List<Integer> list) {
    	List<StudentModel> resultList = new ArrayList<>();
    	for (Integer key : list) {
    		StudentModel model = new StudentModel();
    		model.setKey(key.longValue());
    		AemStudentEntity student = studentDao.getStudent(model);
			StudentModel resultModel = new StudentModel(student);
			resultList.add(resultModel);
		}
    	return resultList ;
    }
    
    public String exportExcelList(List<TsstUser> list) throws FileNotFoundException, IOException {
        String randomUID = UUID.randomUUID().toString();

        String afterDest = randomUID.replaceAll("[\\-\\+\\.\\!\\@\\#\\$\\%\\&\\*\\(\\)\\`\\~\\;\\/\\|\\?\\^:,]", "");
        // Creating a PdfDocument object
        String dest = pathUploadDir + afterDest + ".xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        writeHeaderLine(workbook);
        writeDataLines(workbook, list);
        try (FileOutputStream out = new FileOutputStream(dest)) {
            workbook.write(out);
        }
        workbook.close();
        return afterDest;
    }

    private void writeHeaderLine(XSSFWorkbook workbook) {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        style.setFont(font);

        createCell(row, 0, "No.", style); 
        createCell(row, 1, "Full Name", style);       
        createCell(row, 2, "E-mail", style);    
        createCell(row, 3, "Phone", style);
        createCell(row, 4, "userName", style);
        createCell(row, 5, "Class", style);
         
    }
    
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    
    private void writeDataLines(XSSFWorkbook workbook, List<TsstUser> list) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        int idx = 1;
        for (TsstUser user : list) {
        	String StudentInfoId = user.getEamStudentInfo().getStudentInfoId().toString();
        	String className = "";
        	List<String> ListClassNm = edu0101Respository.getClassCourseForStudentId(StudentInfoId);
        	for(String classNm : ListClassNm) {
        		className += classNm + ", ";
        	}
            Row row = sheet.createRow(rowCount++);
            
            int columnCount = 0;
            createCell(row, columnCount++, idx, style);
            createCell(row, columnCount++, user.getEamStudentInfo().getFullName(), style);
            createCell(row, columnCount++, user.getEamStudentInfo().getEmail(), style);
            createCell(row, columnCount++, user.getEamStudentInfo().getCellPhone(), style);
            createCell(row, columnCount++, user.getUserId(), style);
            createCell(row, columnCount++, className, style);
//            createCell(row, columnCount++, stu.getClass(), style);
            
             idx++;
        }
    }

	@Transactional
	public TsstUser getTsstUserByUserInfoId(Long userUid) {
		String userInfoId = userUid.toString();
		String userType = "STU";
		TsstUser tsstUser = edu0101Respository.getTsstUserByUserInfoId(userInfoId, userType);
		return tsstUser;
	}

}
