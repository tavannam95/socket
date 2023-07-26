package com.a2m.gen.services.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.CourseDao;
import com.a2m.gen.dao.CourseSubjectMapDao;
import com.a2m.gen.dao.SubjectDao;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectHistory;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.common.ApproveModel;
import com.a2m.gen.models.course.AnswerCorrectLabel;
import com.a2m.gen.models.course.ChapterFileModel;
import com.a2m.gen.models.course.CourseSelectModel;
import com.a2m.gen.models.course.CourseSubjectMapModel;
import com.a2m.gen.models.course.QuizItemAnswerModel;
import com.a2m.gen.models.course.QuizItemModel;
import com.a2m.gen.models.course.QuizModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectHistoryModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.course.SubjectStandardHistoryModel;
import com.a2m.gen.models.course.SubjectStandardNoteModel;
import com.a2m.gen.models.course.SubjectStandardResultHistoryModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.TeacherModel;
import com.a2m.gen.services.common.ApproveService;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.ExportToPDFService;
import com.a2m.gen.services.common.ExportToWordService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.ModelUtil;

@Service
public class Course0102Service {
	@Autowired
	private CommonService commonService;

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private CourseSubjectMapDao csmDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private ExportToPDFService exportToPDFService;

	@Autowired
	private ExportToWordService exportToWordService;

	@Autowired
	private ApproveService approveService;

	@Autowired
	private Course0103Service chapterService;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private QuizService quizService;

	@Autowired
	private StandardService standardService;

	@Value("${path.upload.dir}")
	private String pathUploadDir;

	public List<SubjectModel> getSubjects(ParamBaseModel search) throws Exception {
		List<SubjectModel> subjectModels = new ArrayList<SubjectModel>();
		List<AemCourseSubject> lst = subjectDao.getSubjects(search);
		for (AemCourseSubject db : lst) {

			Long approvalHistoryId = db.getApprovalHistoryId();
			ApproveModel apr = new ApproveModel();
			if (approvalHistoryId != null) {
				apr = approveService.getApprove(approvalHistoryId);
			}

			SubjectModel model = new SubjectModel(db);

			if (approvalHistoryId == null) {
				model.setDocumentStatus(CommonContants.DOCUMENT_STATUS_DRAFT);
			}

			model.setApproveHistory(apr);
			subjectModels.add(model);
		}
		return subjectModels;
	}

	@Transactional
	public Map<Object, Object> saveSubject(SubjectHistoryModel modelHistory) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();

		AemCourseSubject dbSubject = new AemCourseSubject();
		Long subjectId = null;
		if (!"".equals(CastUtil.castToString(modelHistory.getKey()))) {
			// update
			if (modelHistory.getListCourse().size() > 0) {
				saveCourseSubjectMap(modelHistory.getListCourse(), modelHistory.getSubjectId());
			}

			saveSubjectHistory(modelHistory);
			subjectId = modelHistory.getSubjectId();
		} else {
			// insert
			modelHistory.setInsUid(userUid);
			SubjectModel subjectModel = new SubjectModel(modelHistory);
			dbSubject = subjectDao.saveSubject(subjectModel, dbSubject);
			String subjectCode = getSubjectCode(dbSubject);

			dbSubject.setSubjectCode(subjectCode);
			entityManager.merge(dbSubject);

			if (modelHistory.getCourseId() != null) {
				// course0102 clone
				saveCourseSubject(modelHistory.getCourseId(), dbSubject);
			} else if (modelHistory.getListCourse().size() > 0) {
				// course0105 clone
				saveCourseSubjectMap(modelHistory.getListCourse(), dbSubject.getSubjectId());
			}

			modelHistory.setSubjectId(dbSubject.getSubjectId());
			modelHistory.setSubjectCode(subjectCode);
			saveSubjectHistory(modelHistory);

			subjectId = dbSubject.getSubjectId();
		}

		res.put(CommonContants.KEY, subjectId);
		return res;
	}

	public Boolean saveCourseSubjectMap(List<CourseSelectModel> listCourse, Long subjectId) throws Exception {

		csmDao.deleteBySubjectId(subjectId);

		for (CourseSelectModel course : listCourse) {
			String userUid = commonService.getUserUid();
			CourseSubjectMapModel model = new CourseSubjectMapModel();
			model.setInsUid(userUid);

			csmDao.saveCourseSubject2(course.getKey(), model, subjectId);
		}

		return true;
	}

	public String getSubjectCode(AemCourseSubject aemCourseSubject) {

//		get Subject Code
		Long subjectId = aemCourseSubject.getSubjectId();

		String strSubjectId = String.format("%05d", subjectId);

		String subjectCode = "SUB" + strSubjectId;
//	    aemCourseSubject.setSubjectCode(subjectCode);

		return subjectCode;

//	    entityManager.persist(aemCourseSubject);

	}

	public AemCourseSubjectHistory saveSubjectHistory(SubjectHistoryModel model) throws Exception {
		String userUid = commonService.getUserUid();
		AemCourseSubjectHistory db = new AemCourseSubjectHistory();
		if (!"".equals(CastUtil.castToString(model.getKey()))) { // update
			db = subjectDao.getSubjectHistory(model);
			model.setUpdUid(userUid);
			db = subjectDao.saveSubjectHistory(model, db);
		} else { // insert
			model.setInsUid(userUid);
			db = subjectDao.saveSubjectHistory(model, db);
		}

		return db;
	}

	public AemCourseSubject saveSubjectOnly(SubjectModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();

		AemCourseSubject db = new AemCourseSubject();

		if (!"".equals(CastUtil.castToString(model.getKey()))) { // update
			db = subjectDao.getSubject(model);

			model.setUpdUid(userUid);
			db = subjectDao.saveSubject(model, db);

		} else { // insert
			model.setInsUid(userUid);
			db = subjectDao.saveSubject(model, db);
		}

		return db;
	}

	public void sendRequestApproval(SubjectModel model, AemCourseSubject db) throws Exception {
		ApproveModel aprModel = new ApproveModel();
		aprModel.setKey(model.getApproveHistory().getKey());
		if (model.getApproveHistory().getApprovalStatus().equals(CommonContants.APPROVAL_STATUS_APPROVAL)) {
			aprModel.setKey(null);
		}
		aprModel.setApprovalStatus(CommonContants.APPROVAL_STATUS_PENDING);
		aprModel.setRefId(db.getSubjectId());
		aprModel.setRefTable("EAM_COURSE_SUBJECT");
		aprModel.setDocumentUrl(model.getApproveHistory().getDocumentUrl());
		aprModel.setViewedAprHistory(false);
		aprModel.setEmpAprUid(model.getApproveHistory().getEmpAprUid());
		approveService.saveApprove(aprModel);
	}

	public void saveCourseSubject(Long courseId, AemCourseSubject db) throws Exception {
		String userUid = commonService.getUserUid();
		CourseSubjectMapModel model = new CourseSubjectMapModel();
		model.setInsUid(userUid);

		csmDao.saveCourseSubject2(courseId, model, db.getSubjectId());

	}

	@Transactional
	public Map<Object, Object> saveCSM(SubjectModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();

		List<String> listSubjectId = model.getListSubjectId();
		Long courseId = model.getCourseId();

//        CourseSubjectMapModel courseSubjectModel = new CourseSubjectMapModel();
//        courseSubjectModel.setInsUid(userUid);
//        
//        Edu0102RequestModel courseSearch = new Edu0102RequestModel();
//        courseSearch.setKey(courseId);
//        AemCourse course = courseDao.getCourse(courseSearch);
//        courseSubjectModel.setAemCourse(course);

		for (String element : listSubjectId) {
			AemCourseSubjectMap dbMap = new AemCourseSubjectMap();
			SubjectModel subjectModel = new SubjectModel();

			Long newKey = cloneSubject(CastUtil.castToLong(element), courseId);
		}
		return res;
	}
	
	@Transactional
	public Map<Object, Object> doChooseSubject(SubjectModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		List<String> listSubjectId = model.getListSubjectId();
		Long courseId = model.getCourseId();
		
		for (String element : listSubjectId) {
			
			CourseSubjectMapModel couSubModel = new CourseSubjectMapModel();
			couSubModel.setInsUid(userUid);

			csmDao.saveCourseSubject2(courseId, couSubModel, CastUtil.castToLong(element));
		}
		return res;
	}

	public Map<Long, Long> cloneStandard(Long oldSubjectId, Long newSubjectId) throws Exception {
		SubjectStandardHistoryModel param = new SubjectStandardHistoryModel();
		param.setSubjectId(oldSubjectId);
		List<SubjectStandardHistoryModel> listStand = standardService.getStandardHistory(param);
		List<Long> listOldKey = new ArrayList<>();
		for (SubjectStandardHistoryModel stand : listStand) {
			listOldKey.add(stand.getKey());
			stand.setKey(null);
			stand.setSubjectId(newSubjectId);
		}

		Map<Object, Object> saveResponse = standardService.saveStandardHistory(listStand);
		List<Long> listNewKey = (List<Long>) saveResponse.get("listKey");

		Map<Long, Long> response = new HashMap<Long, Long>();
		for (int i = 0; i < listNewKey.size(); i++) {
			Map value = new HashMap<Long, Long>();
			response.put(listOldKey.get(i), listNewKey.get(i));
		}
		return response;
	}

	public Long cloneSubject(Long oldSubjectId, Long courseId) throws Exception {
		SubjectHistoryModel subjectClone = getSubjectHistory(oldSubjectId);
		subjectClone.setCourseId(courseId);
		subjectClone.setKey(null);
		subjectClone.setSubjectId(null);
		subjectClone.setSubjectNm(subjectClone.getSubjectNm() + " - Copy");
		subjectClone.setListCourse(new ArrayList<CourseSelectModel>());
		subjectClone.setCourseId(null);

		Map<Object, Object> newSubject = saveSubject(subjectClone);
		Long newKey = CastUtil.castToLong(newSubject.get(CommonContants.KEY));

		Map<Long, Long> oldNewCoupleChapKey = cloneChapter(newKey, oldSubjectId, courseId);
		Map<Long, Long> oldNewCoupleStandKey = cloneStandard(oldSubjectId, newKey);
		cloneStandNote(oldNewCoupleChapKey, oldNewCoupleStandKey, oldSubjectId);
		return newKey;
	}

	public void cloneStandNote(Map<Long, Long> oldNewCoupleChapKey, Map<Long, Long> oldNewCoupleStandKey,
			Long oldSubjectId) throws Exception {
		List<SbjChapterModel> list = standardService.getLectureScheduleHistoryBySubjectId(oldSubjectId);
		for (SbjChapterModel sbjChapterModel : list) {
			List<SubjectStandardNoteModel> listStandNote = sbjChapterModel.getListStandNote();

			List<SubjectStandardResultHistoryModel> listStandNoteHis = sbjChapterModel.getListStandNoteHis();

			for (SubjectStandardResultHistoryModel noteHis : listStandNoteHis) {
				noteHis.setKey(null);
				noteHis.setChapterId(oldNewCoupleChapKey.get(noteHis.getChapterId()));
				noteHis.setStandHistoryId(oldNewCoupleStandKey.get(noteHis.getStandHistoryId()));

				SubjectStandardNoteModel note = new SubjectStandardNoteModel(noteHis);
				listStandNote.add(note);
			}
		}

		standardService.saveStandardNoteHistory(list);
	}

	public Map<Long, Long> cloneChapter(Long newSubjectId, Long oldSubjectId, Long courseId) throws Exception {
		SbjChapterModel search = new SbjChapterModel();
		search.setKey(oldSubjectId);
		search.setGetAll(true);
		List<SbjChapterModel> lst = chapterService.getSbjChapter(search);
		Map oldNewKey = new HashMap<Long, Long>();

		for (SbjChapterModel oldChap : lst) {
			SbjChapterModel newChap = chapterService.getChapterById(oldChap.getKey());

			newChap.setKey(null);
			newChap.setSubjectId(newSubjectId);

			List<ChapterFileModel> chapterFileModels = newChap.getChapterFileModels();
			for (ChapterFileModel files : chapterFileModels) {
				files.setCrud("C");
			}

			Map<Object, Object> saveChapterResult = chapterService.saveChapter(newChap);
			Long newChapterId = CastUtil.castToLong(saveChapterResult.get(CommonContants.KEY));

			oldNewKey.put(oldChap.getKey(), newChapterId);

			QuizModel quizModel = new QuizModel();
			quizModel.setChapterId(newChapterId);
			quizModel.setSubjectId(newSubjectId);

			cloneQuiz(oldChap.getKey(), quizModel);
		}

		return oldNewKey;
	}

	public Long cloneQuiz(Long OldChapterId, QuizModel quiz) throws Exception {
		QuizModel search = new QuizModel();
		search.setGetAll(true);
		search.setChapterId(OldChapterId);
		List<QuizModel> lst = quizService.getQuizByChapter(search);

		for (QuizModel quizModel : lst) {
			QuizModel detail = quizService.getQuizById(quizModel.getKey());
			detail.setChapterId(quiz.getChapterId());
			detail.setSubjectId(quiz.getSubjectId());
			
			// modify before save
			detail.setKey(null);
			List<QuizItemModel> listQuizItem = detail.getListQuizItem();
			for (QuizItemModel quizItem : listQuizItem) {
				quizItem.setKey(null);

				List<QuizItemAnswerModel> listAnswer = quizItem.getListAnswer();
				for (QuizItemAnswerModel answer : listAnswer) {
					answer.setKey(null);
				}
			}

			// save
			quizService.saveQuiz(detail);
		}

		Long newKey = Long.valueOf(1);
		return newKey;
	}

	@Transactional
	public void deleteSubject(String id) {
		subjectDao.deleteSubject(id);
	}
	
	@Transactional
	public void setTrueDeleteFlag(String subjectId) {
        SubjectModel search = new SubjectModel();
        search.setKey(Long.parseLong(subjectId));
         AemCourseSubject subject = subjectDao.getSubject(search);
         
         //AemCourseSubjectMap subjectMap = subject.getListOfCouSubjectMap();
         List<AemCourseSubjectMap> subjectMap = subject.getListOfCouSubjectMap();
         for (AemCourseSubjectMap element : subjectMap) {
             csmDao.delete(element.getTableId());
         }
         
         subjectDao.deleteSubject(subjectId);
	}

	@Transactional
	public void delete(String subjectId, String courseId) {
//        SubjectModel subjectModel = new SubjectModel();
//        subjectModel.setKey(Long.parseLong(id));
//         AemCourseSubject subject = subjectDao.getSubject(subjectModel);
//         //AemCourseSubjectMap subjectMap = subject.getListOfCouSubjectMap();
//         List<AemCourseSubjectMap> subjectMap = subject.getListOfCouSubjectMap();
//         for (AemCourseSubjectMap element : subjectMap) {
//             csmDao.delete(element.getTableId());
//        }
		Long courseIdLong = CastUtil.castToLong(courseId);
		List<AemCourseSubjectMap> listCouSubMap = csmDao.getListCouSubMapBySubId(CastUtil.castToLong(subjectId));

		for (AemCourseSubjectMap element : listCouSubMap) {
			Long couId = element.getAemCourse().getCourseId();
			if (couId.equals(courseIdLong)) {
				csmDao.delete(element.getTableId());
			}
		}

	}

	public SubjectModel getSubjectById(Long Id) {
		SubjectModel param = new SubjectModel();
		param.setKey(Id);
		AemCourseSubject aemSubject = subjectDao.getSubject(param);

		SubjectModel result = new SubjectModel(aemSubject);
		String CourseUid = subjectDao.getUserIdByTeachId(result.getKey());
		result.setCourseUid(CourseUid);
		TeacherModel teacherModel = result.getTeacherModel();
		String userUid = subjectDao.getUserIdByTeachId(teacherModel.getKey());
		teacherModel.setUserUid(userUid);

		result.setApprovalHistoryId(aemSubject.getApprovalHistoryId());

		// course selected
		List<AemCourseSubjectMap> courseSubMap = csmDao.getListCouSubMapBySubId(Id);
		List<CourseSelectModel> listCourse = new ArrayList<CourseSelectModel>();
		for (AemCourseSubjectMap aemCourseSubjectMap : courseSubMap) {
			AemCourse dbCourse = aemCourseSubjectMap.getAemCourse();
			CourseSelectModel model = new CourseSelectModel(dbCourse.getCourseId(), dbCourse.getCourseNm());
			listCourse.add(model);
		}
		result.setListCourse(listCourse);
		return result;

	}

	public SubjectHistoryModel getSubjectHistory(Long subjectId) {
		SubjectHistoryModel param = new SubjectHistoryModel();
		param.setSubjectId(subjectId);
		AemCourseSubjectHistory entity = subjectDao.searchSubjectHistory(param);
		if (entity.equals(null))
			return null;
		SubjectHistoryModel result = new SubjectHistoryModel(entity);

		ModelUtil.setCommonFields(entity, result);

		TeacherModel teacher = new TeacherModel(subjectDao.getTeacher(entity.getTeacherInfoId()));
		String userUid = subjectDao.getUserIdByTeachId(teacher.getKey());
		teacher.setUserUid(userUid);
		result.setTeacherModel(teacher);

		TsstUser u = subjectDao.getTsstUserByUserUid(entity.getUserUid());
		result.setTsstUser(u);

		AemCourseSubject aemSubject = subjectDao.getSubject(subjectId);
		result.setApprovalHistoryId(aemSubject.getApprovalHistoryId());

		// course selected
		List<AemCourseSubjectMap> courseSubMap = csmDao.getListCouSubMapBySubId(subjectId);
		List<CourseSelectModel> listCourse = new ArrayList<CourseSelectModel>();
		for (AemCourseSubjectMap aemCourseSubjectMap : courseSubMap) {
			AemCourse dbCourse = aemCourseSubjectMap.getAemCourse();
			CourseSelectModel model = new CourseSelectModel(dbCourse.getCourseId(), dbCourse.getCourseNm());
			listCourse.add(model);
		}
		result.setListCourse(listCourse);
		return result;

	}

	@Transactional
	public List<HashMap<String, Object>> getLectureScheduleBySubjectId(String id) {
		return subjectDao.getLectureScheduleBySubjectId(id);

	}

	@Transactional
	public String exportPDFScheduleBySubjectId(String id) throws Exception {
		List<HashMap<String, Object>> listChapter = subjectDao.getLectureScheduleBySubjectId(id);

		String path = exportToPDFService.exportPDFScheduleBySubject(listChapter);

		// return fileName+"!#@"+quizNm;
		SubjectModel subjectModel = new SubjectModel();
		subjectModel.setKey(Long.valueOf(id));
		AemCourseSubject aemSubject = subjectDao.getSubject(subjectModel);
		return path + "!#@" + aemSubject.getSubjectNm();
	}

	@Transactional
	public String exportPDF(String id) throws Exception {
		List<HashMap<String, Object>> listChapter = subjectDao.getLectureScheduleBySubjectId(id);
		SubjectModel subjectModel = new SubjectModel();
		subjectModel.setKey(Long.valueOf(id));
		AemCourseSubject aemSubject = subjectDao.getSubject(subjectModel);
		subjectModel = new SubjectModel(aemSubject);
		String path = exportToPDFService.exportPDFSubject(listChapter, subjectModel);

		// return fileName+"!#@"+quizNm;
//		SubjectModel subjectModel = new SubjectModel();
//		subjectModel.setKey(Long.valueOf(id));
//		AemCourseSubject aemSubject =  subjectDao.getSubject(subjectModel);
		return path + "!#@" + aemSubject.getSubjectNm();
	}

	@Transactional
	public String exportWord(String id) throws Exception {
		List<HashMap<String, Object>> listChapter = subjectDao.getLectureScheduleBySubjectId(id);
		SubjectModel subjectModel = new SubjectModel();
		subjectModel.setKey(Long.valueOf(id));
		AemCourseSubject aemSubject = subjectDao.getSubject(subjectModel);
		subjectModel = new SubjectModel(aemSubject);
//        String path = exportToPDFService.exportPDFSubject(listChapter, subjectModel);
		String pathWord = exportToWordService.exportWordSubject(listChapter, subjectModel);
		// return fileName+"!#@"+quizNm;
//      SubjectModel subjectModel = new SubjectModel();
//      subjectModel.setKey(Long.valueOf(id));
//      AemCourseSubject aemSubject =  subjectDao.getSubject(subjectModel);
		return pathWord + "!#@" + aemSubject.getSubjectNm();
	}
}
