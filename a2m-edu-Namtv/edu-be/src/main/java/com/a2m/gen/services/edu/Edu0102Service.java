package com.a2m.gen.services.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.CourseDao;
import com.a2m.gen.dao.SubjectDao;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.EamStudentInfo;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.common.ApproveModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.ClassStudentMapModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.ApproveService;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;

@Service
public class Edu0102Service {
	@Autowired
	private CommonService commonService;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private ApproveService approveService;
	
    @Autowired
    private EntityManager entityManager;

	public List<Edu0102RequestModel> getCourses(ParamSearchModel search) throws Exception {
		List<Edu0102RequestModel> Edu0102RequestModels = new ArrayList<Edu0102RequestModel>();
		List<AemCourse> lst = courseDao.getCourses(search);
		for (AemCourse db : lst) {
			Edu0102RequestModel model = new Edu0102RequestModel(db);
			Edu0102RequestModels.add(model);
		}
		return Edu0102RequestModels;
	}

    public Object getCoursesByCourseView(ParamBaseModel search) throws Exception {
        return courseDao.getCourseByUserUid(search);
    }
    
	public Map<Object, Object> saveCourse(Edu0102RequestModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();

		AemCourse db = new AemCourse();

		AemCourse saveCourse = new AemCourse();
		if (!"".equals(CastUtil.castToString(model.getKey()))) {
			db = courseDao.getCourse(model);
			model.setUpdUid(userUid);
			 saveCourse = courseDao.saveCourse(model, db);
//			setCourseCode(saveCourse);
		} else {
			model.setInsUid(userUid);
			 saveCourse = courseDao.saveCourse(model, db);
			setCourseCode(saveCourse);
		}
		res.put(CommonContants.KEY, db.getCourseId());
		Edu0102RequestModel courseModel = new Edu0102RequestModel(saveCourse);
		res.put(CommonContants.DETAIL_KEY, courseModel);
		return res;
	}
	
	public void setCourseCode(AemCourse aemCourse) {

//		get Course Code
	    Long courseId = aemCourse.getCourseId();
	    
	    String strCourseId = String.format("%05d", courseId);
	    
	    String courseCode = "COU"+ strCourseId;
	    aemCourse.setCourseCode(courseCode);
	    
	    entityManager.persist(aemCourse);
		
	}

	public Long getCountCourse(ParamBaseModel search) {
		return courseDao.countAllCourse(search);
	}

	public AemCourse delete(Long id) {
		AemCourse deleteCourse = courseDao.deleteCourse(id);
		return deleteCourse;
	}

	public Edu0102RequestModel getCourseById(Long Id) {
		Edu0102RequestModel courseModel = new Edu0102RequestModel();
		courseModel.setKey(Id);
		AemCourse aemCourse = courseDao.getCourse(courseModel);
		Edu0102RequestModel result = new Edu0102RequestModel(aemCourse, true);
		setListMap(courseModel, result, aemCourse);
		return result;
	}

	@Transactional
	public Edu0102RequestModel getCourseByCondition(Edu0102RequestModel model) {
		AemCourse aemCourse = courseDao.getCourse(model);

		Edu0102RequestModel result = new Edu0102RequestModel(aemCourse, true);
		
		result = setListMap(model, result, aemCourse);

		return result;
	}

	public Edu0102RequestModel setListMap(Edu0102RequestModel courseModel, Edu0102RequestModel result, AemCourse aemCourse) {
		List<SubjectModel> subjectModels = result.getSubjectModels();
		
		List<AemCourseSubjectMap> listOfCourseSubjectMap = aemCourse.getListOfCourseSubjectMap();
		Collections.sort(listOfCourseSubjectMap, new Comparator<AemCourseSubjectMap>() {
			public int compare(AemCourseSubjectMap o1, AemCourseSubjectMap o2) {
				if (o1.getTableId() == o2.getTableId())
					return 0;
				return o1.getTableId() < o2.getTableId() ? -1 : 1;
			}
		});
		for (AemCourseSubjectMap aemCourseSubjectMap : listOfCourseSubjectMap) {
			AemCourseSubject subject = aemCourseSubjectMap.getAemCouSubject();

			Long approvalHistoryId = subject.getApprovalHistoryId();
			ApproveModel apr = new ApproveModel();
			if (approvalHistoryId != null) {
				apr = approveService.getApprove(approvalHistoryId);
			}

			SubjectModel subjectModel = new SubjectModel(subject, true);
			if (approvalHistoryId == null) {
				subjectModel.setDocumentStatus(CommonContants.DOCUMENT_STATUS_DRAFT);
			}
			
			String searchText = courseModel.getSearchText();
			Boolean passSearchText = true;
			Boolean searchStatus = courseModel.getSearchStatus();
			Boolean passSearchStatus = true;
			if(searchText != null && !searchText.equals("")) {
				if( !subjectModel.getSubjectNm().toUpperCase().contains(searchText.toUpperCase()) ) {
					passSearchText = false;
				}
			}
			if(searchStatus != null && !CastUtil.castToString(searchStatus).equals("")) {
				if( !subjectModel.getStatus() == searchStatus  ) {
					passSearchStatus = false;
				}
			}
				
			if( passSearchText && passSearchStatus) {
				subjectModels.add(subjectModel);
			}
			
			subjectModel.setApproveHistory(apr);
			
		}
		return result;
	}

	public List<SubjectModel> getListSubjectByCourseId(Long Id) {
		List<SubjectModel> list = new ArrayList<SubjectModel>();

		Edu0102RequestModel courseModel = new Edu0102RequestModel();
		courseModel.setKey(Id);
		AemCourse aemCourse = courseDao.getCourse(courseModel);
		List<AemCourseSubjectMap> listMap = aemCourse.getListOfCourseSubjectMap();
		for (AemCourseSubjectMap element : listMap) {
			AemCourseSubject entity = element.getAemCouSubject();
			SubjectModel model = new SubjectModel(entity);
			list.add(model);
		}
		return list;
	}

	public List<Edu0102RequestModel> getListCourseByUserUid(String userUid) throws Exception {
		List<AemCourse> aemCourses = courseDao.getListCourseByUserUid(userUid);
		List<Edu0102RequestModel> listCourse = new ArrayList<>();
		for (AemCourse aemCourse : aemCourses) {
			Edu0102RequestModel course = new Edu0102RequestModel(aemCourse);
			listCourse.add(course);
		}
		return listCourse;
	}

	public List<Edu0102RequestModel> getListCourseInprogress() {
		List<Edu0102RequestModel> result = new ArrayList<>();
		List<AemCourse> aemCourses = courseDao.getListCourseInprogress();
		for (AemCourse aemCourse : aemCourses) {
			Edu0102RequestModel model = new Edu0102RequestModel(aemCourse);
			result.add(model);
		}
		return result;
	}

}
