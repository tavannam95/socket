package com.a2m.gen.services.course;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.QuizDao;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.entities.edu.QuizItemAnswerEntity;
import com.a2m.gen.entities.edu.QuizItemAnswerResultEntity;
import com.a2m.gen.entities.edu.QuizItemEntity;
import com.a2m.gen.entities.edu.QuizItemResultEntity;
import com.a2m.gen.entities.edu.QuizStudentMapEntity;
import com.a2m.gen.models.course.AnswerCorrectLabel;
import com.a2m.gen.models.course.QuizItemAnswerModel;
import com.a2m.gen.models.course.QuizItemModel;
import com.a2m.gen.models.course.QuizItemResultModel;
import com.a2m.gen.models.course.QuizModel;
import com.a2m.gen.models.course.SubmitQuizModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.ExcelService;
import com.a2m.gen.services.common.ExportToPDFService;
import com.a2m.gen.services.sys.Sys0103Service;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Service
public class QuizService {
	@Autowired
	private CommonService commonService;

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private ExcelService excelService;

	@Autowired
	private Sys0103Service sys0103Service;
	
	@Autowired
	private ExportToPDFService exportToPDFService;
	
	@Value("${path.upload.dir}")
	private String pathUploadDir;

	public List<QuizModel> getQuizByChapter(QuizModel search) throws Exception {
		List<QuizModel> listQuiz = new ArrayList<QuizModel>();
		List<QuizEntity> lst = quizDao.getQuizByChapter(search);
		for (QuizEntity db : lst) {
			QuizModel model = new QuizModel(db);
			listQuiz.add(model);
		}
		return listQuiz;
	}

	public QuizModel getQuizById(Long Id) {
		QuizModel quizModel = new QuizModel();
		quizModel.setKey(Id);
		QuizEntity entity = quizDao.getQuizById(quizModel);
		QuizModel result = new QuizModel(entity);
		return result;

	}
	
	public Object getListStuSubtmited(HashMap<String, Object> parameter) {
		return quizDao.getListStuSubtmited(parameter);
	}

	public Object getListHisQuizStuSubtmited(HashMap<String, Object> parameter) {
		return quizDao.getListHisQuizStuSubtmited(parameter);
	}
	
	public Long countStuSubtmited(HashMap<String, Object> parameter) {
		return quizDao.countStuSubtmited(parameter);
	}
	
	public HashMap<String, Object> getStatisResult(HashMap<String, Object> parameter) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result = quizDao.getStatisResult(parameter);
		return result;
	}
	
	
	public Object getQuizSubmit(Long quizId, Long userInfoId) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		
//		String userUid = commonService.getUserUid();
//		TsstUser user = edu0101Service.findByUserUid(userUid);
//		if(!user.getUserType().equals("STU")) {
//			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
//			res.put(CommonContants.MESSAGES_KEY,"NOT A STUDENT");
//			return res;
//		}
//		AemStudentEntity studentEntity = quizDao.getEamStudentEntity(user.getEamStudentInfo().getStudentInfoId());
		
		QuizModel quizModel = new QuizModel();
		quizModel.setKey(quizId);
		QuizEntity entity = quizDao.getQuizById(quizModel);
		
		List<QuizStudentMapEntity> quizStudentMapEntities = entity.getQuizStudentMapEntities();
		quizStudentMapEntities.removeIf(map -> !map.getStudentEntity().getStudentInfoId().equals(userInfoId));
		
		if(quizStudentMapEntities.size()==0) {
			QuizModel result = new QuizModel(entity);
			return result;
		}else {
			SubmitQuizModel response = new SubmitQuizModel(quizStudentMapEntities.get(quizStudentMapEntities.size()-1));
			return response;
		}
		
		
		//return res;
	}

	public Object getQuizSubmitStu(Long quizId, Long userInfoId, Long quizStudentId) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		QuizModel quizModel = new QuizModel();
		quizModel.setKey(quizId);
		QuizEntity entity = quizDao.getQuizById(quizModel);
		
		List<QuizStudentMapEntity> quizStudentMapEntities = entity.getQuizStudentMapEntities();
		quizStudentMapEntities.removeIf(map -> !map.getStudentEntity().getStudentInfoId().equals(userInfoId));
		quizStudentMapEntities.removeIf(map -> !map.getQuizStudentId().equals(quizStudentId));
		if(quizStudentMapEntities.size()==0) {
			QuizModel result = new QuizModel(entity);
			return result;
		}else {
			SubmitQuizModel response = new SubmitQuizModel(quizStudentMapEntities.get(quizStudentMapEntities.size()-1));
			return response;
		}
		
		
		//return res;
	}

	@Transactional
	public Map<Object, Object> saveQuiz(QuizModel quizModel) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();

		QuizEntity db = new QuizEntity();
		if (!"".equals(CastUtil.castToString(quizModel.getKey()))) {
			// update
//			db = quizDao.getChapter(SbjChapterModel);
//			SbjChapterModel.setUpdUid(userUid);
			db = quizDao.saveQuiz(quizModel, db);
			quizModel.setUpdUid(userUid);
			saveQuizItem(quizModel);
			// quizDao.deleteQuizItemByChapterId(quizModel);
		} else {
			// insert
			quizModel.setInsUid(userUid);
			db = quizDao.saveQuiz(quizModel, db);
			quizModel.setKey(db.getQuizId());
			saveQuizItem(quizModel);
		}
		res.put(CommonContants.KEY, db.getQuizId());
		return res;
	}

	public static final int QUIZ_ITEM_CONTENT = 1;
	public static final int ANSWER_A = 2;
	public static final int ANSWER_B = 3;
	public static final int ANSWER_C = 4;
	public static final int ANSWER_D = 5;
	public static final int ANSWER_CORRECT = 6;
	public static final int QUIZ_ITEM_TYPE = 7;

	public String getCellValue(List<Cell> cells, int col) {

		return excelService.getCellValue(cells.get(col - 1));
	}

	@Transactional
	public List<QuizItemModel> importExcel(MultipartFile file) {
		List<List<Cell>> rows = excelService.ReadExcel(file, true);
		List<QuizItemModel> listQuizItem = new ArrayList<QuizItemModel>();

		for (int i = 0; i < rows.size(); i++) {
			List<Cell> cells = rows.get(i);

			QuizItemModel quizItemModel = new QuizItemModel();
			quizItemModel.setQuizItemContent(getCellValue(cells, QUIZ_ITEM_CONTENT));
			quizItemModel.setAnswerA(getCellValue(cells, ANSWER_A));
			quizItemModel.setAnswerB(getCellValue(cells, ANSWER_B));
			quizItemModel.setAnswerC(getCellValue(cells, ANSWER_C));
			quizItemModel.setAnswerD(getCellValue(cells, ANSWER_D));
			quizItemModel.setAnswerCorrectString(getCellValue(cells, ANSWER_CORRECT));
			quizItemModel.addAnswerCorrect(getCellValue(cells, ANSWER_CORRECT));
			quizItemModel.setQuizItemType(getCellValue(cells, QUIZ_ITEM_TYPE));

			quizItemModel.setCrudType("C");
			if ((!quizItemModel.getAnswerA().equals("") || !quizItemModel.getAnswerB().equals("")
					|| !quizItemModel.getAnswerC().equals("") || !quizItemModel.getAnswerD().equals("")
					|| !quizItemModel.getQuizItemContent().equals("")
					|| !quizItemModel.getAnswerCorrectString().equals("")
					|| !quizItemModel.getQuizItemType().equals(""))) {
				
				quizItemModel.setQuizItemType(QUIZ_ITEM_DEFAUT_TYPE);
				listQuizItem.add(quizItemModel);
			}

		}

		return listQuizItem;
	}
	
	@Transactional
	public List<QuizItemModel> importExcelMulti(MultipartFile file) {
		List<List<Cell>> rows = excelService.ReadExcel(file, true);
		List<QuizItemModel> listQuizItem = new ArrayList<QuizItemModel>();
		try {

			for (int i = 0; i < rows.size(); i++) {
				List<Cell> cells = rows.get(i);
				
				QuizItemModel quizItemModel = new QuizItemModel();
				quizItemModel.setQuizItemContent(getCellValue(cells, QUIZ_ITEM_CONTENT));
				quizItemModel.setCrudType("C");
				quizItemModel.setQuizItemType(QUIZ_ITEM_MULTI_CHOISE_TYPE);
				
				List<QuizItemAnswerModel> listAnswer = new ArrayList<QuizItemAnswerModel>();
				List<AnswerCorrectLabel> listAnswerCorrect = new ArrayList<AnswerCorrectLabel>();
				quizItemModel.setListAnswer(listAnswer);
				quizItemModel.setListAnswerCorrect(listAnswerCorrect);
								
				int startAnsCol = 2;
				int indexAnswer = 0;
				while(startAnsCol <= cells.size()){
					String answerContent = getCellValue(cells, startAnsCol);
					if(answerContent.equals("")) break;
					
					Boolean isCorrectAnswer = false;
					if(!answerContent.equals("") && startAnsCol+1 <= cells.size()) {
						isCorrectAnswer = CastUtil.castToLong( getCellValue(cells, startAnsCol+1)) >= 1;
					}
					
					QuizItemAnswerModel answerModel = new QuizItemAnswerModel();
						answerModel.setAnswerContent(answerContent);
						answerModel.setIsAnswerCorrect(isCorrectAnswer);
						listAnswer.add(answerModel);
					if(isCorrectAnswer) {
						AnswerCorrectLabel  correct = new AnswerCorrectLabel(CastUtil.castToLong(indexAnswer), ""+(indexAnswer+1));
						listAnswerCorrect.add(correct);
					}
					
					startAnsCol += 2;
					indexAnswer++;
					System.out.println(answerContent + ":" +isCorrectAnswer);
				}
				
				if(!quizItemModel.getQuizItemContent().equals("")) listQuizItem.add(quizItemModel);
				if(getCellValue(cells, 1).equals("")) break;
			}
	
			return listQuizItem;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listQuizItem;
	}
	
	public static final String QUIZ_ITEM_MULTI_CHOISE_TYPE = "08-01";
	public static final String QUIZ_ITEM_ESSAY_TYPE = "08-02";
	public static final String QUIZ_ITEM_DEFAUT_TYPE = "08-03";
	
	@Transactional
	public void saveQuizItem(QuizModel quizModel) throws Exception {
		List<QuizItemModel> listQuizItem = quizModel.getListQuizItem();

		for (int i = 0; i < listQuizItem.size(); i++) {
			QuizItemModel quizItemModel = listQuizItem.get(i);
			switch (quizItemModel.getQuizItemType()) {
			case QUIZ_ITEM_DEFAUT_TYPE:
				saveQuizItemDefault(quizItemModel, quizModel);
				break;
			case QUIZ_ITEM_MULTI_CHOISE_TYPE:
				saveQuizItemMultiChoiseFlexible(quizItemModel, quizModel);
				break;
			case QUIZ_ITEM_ESSAY_TYPE:
				saveQuizItemEssayType(quizItemModel, quizModel);
				break;
			default:
				break;
			}
			
		}

	}
	
	@Transactional
	public void saveQuizItemEssayType(QuizItemModel quizItemModel, QuizModel quizModel) throws Exception {
		if (!quizItemModel.getCrudType().equals("D")) {
			QuizItemEntity db = new QuizItemEntity();

			quizItemModel.setAnswerCorrectString(quizItemModel.getAnswerCorrect());

			quizItemModel.setQuizId(quizModel.getKey());

			db = quizDao.saveQuizItem(quizItemModel, db);
		}

		if (quizItemModel.getCrudType().equals("D")) {
			QuizItemEntity db = new QuizItemEntity();

			quizItemModel.setAnswerCorrectString(quizItemModel.getAnswerCorrect());

			quizItemModel.setQuizId(quizModel.getKey());

			quizDao.deleteQuizItem(quizItemModel);
		}
	}
	
	@Transactional
	public void saveQuizItemMultiChoiseFlexible(QuizItemModel quizItemModel, QuizModel quizModel) throws Exception {
		if (!quizItemModel.getCrudType().equals("D")) {
			QuizItemEntity db = new QuizItemEntity();

			quizItemModel.setAnswerCorrectString(quizItemModel.getAnswerCorrect());

			quizItemModel.setQuizId(quizModel.getKey());

			db = quizDao.saveQuizItem(quizItemModel, db);
			
			if(quizItemModel.getKey() != null) {
				deleteQuizItemAnswer(quizItemModel.getKey());
			}
			
			insertQuizItemAnswer(db, quizItemModel, quizItemModel.getListAnswer());
		}

		if (quizItemModel.getCrudType().equals("D")) {
			QuizItemEntity db = new QuizItemEntity();

			quizItemModel.setAnswerCorrectString(quizItemModel.getAnswerCorrect());

			quizItemModel.setQuizId(quizModel.getKey());
			
			deleteQuizItemAnswer(quizItemModel.getKey());
			db = quizDao.deleteQuizItem(quizItemModel);
		}
		
	}
	
	@Transactional
	public void insertQuizItemAnswer(QuizItemEntity db, QuizItemModel quizItemModel, List<QuizItemAnswerModel> listAnswer) {
		for(int i = 0; i <listAnswer.size(); i++) {
			QuizItemAnswerModel answerModel = listAnswer.get(i);
			answerModel.setQuizItemModel(quizItemModel);
			QuizItemAnswerEntity entity = new QuizItemAnswerEntity();
			entity.setQuizItemEntity(db);
			quizDao.insertQuizItemAnswer(answerModel, entity);
		}
	}
	
	@Transactional
	public void deleteQuizItemAnswer(Long quizItemId) {
		quizDao.deleteQuizItemAnswer(quizItemId);
	}
	
	@Transactional
	public void saveQuizItemDefault(QuizItemModel quizItemModel, QuizModel quizModel) throws Exception {
		if (!quizItemModel.getCrudType().equals("D")) {
			QuizItemEntity db = new QuizItemEntity();

			quizItemModel.setAnswerCorrectString(quizItemModel.getAnswerCorrect());

			quizItemModel.setQuizId(quizModel.getKey());

			db = quizDao.saveQuizItem(quizItemModel, db);
		}

		if (quizItemModel.getCrudType().equals("D")) {
			QuizItemEntity db = new QuizItemEntity();

			quizItemModel.setAnswerCorrectString(quizItemModel.getAnswerCorrect());

			quizItemModel.setQuizId(quizModel.getKey());

			quizDao.deleteQuizItem(quizItemModel);
		}
	}

	@Transactional
	public void delete(String id) {
		QuizModel quizModel = getQuizById(CastUtil.castToLong(id));
		QuizEntity db = new QuizEntity();
		db = quizDao.deleteQuiz(quizModel, db);
	}

	@Transactional
	public Map<Object, Object> submit(SubmitQuizModel submitRequest) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		List<QuizItemResultModel> quizItemResultModels = submitRequest.getListQuizItemResult();
		List<QuizItemResultEntity> listQuizItemResultEntity = new ArrayList<QuizItemResultEntity>();
		List<QuizItemModel> listQuizItem = submitRequest.getListQuizItem();
		String userUid = commonService.getUserUid();
		TsstUser user = sys0103Service.findByUserUid(userUid);
		if(!user.getUserType().equals("STU")) {
			res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_NG);
			res.put(CommonContants.MESSAGES_KEY,"NOT A STUDENT");
			return res;
		}
		AemStudentEntity studentEntity = quizDao.getEamStudentEntity(user.getEamStudentInfo().getStudentInfoId());
		
		QuizStudentMapEntity quizStudentMapEntity = new QuizStudentMapEntity();
		
		for (int i = 0; i < quizItemResultModels.size(); i++) {
			QuizItemResultModel itemResultModel = quizItemResultModels.get(i);
			if(itemResultModel.getQuizItemType().equals(QUIZ_ITEM_DEFAUT_TYPE)) {
				QuizItemResultEntity itemResultEtity = new QuizItemResultEntity();
				itemResultEtity.setQuizItemStudentId(null);
				itemResultEtity.setQuizStudentMapEntity(quizStudentMapEntity);
				itemResultEtity.setQuizItemEntity(quizDao.getQuizItemById(itemResultModel.getQuizItemId()));
				itemResultEtity.setResult(itemResultModel.getResult());
				itemResultEtity.setStatus(true);
				itemResultEtity.setChooseAnswer(itemResultModel.getChooseAnswerToString());
				itemResultModel.setInsUid(userUid);
				DatabaseUtil.setCommonFields(itemResultEtity, itemResultModel);
				listQuizItemResultEntity.add(itemResultEtity);
			}
			if(itemResultModel.getQuizItemType().equals(QUIZ_ITEM_ESSAY_TYPE)) {
				QuizItemResultEntity itemResultEtity = new QuizItemResultEntity();
				itemResultEtity.setQuizItemStudentId(null);
				itemResultEtity.setQuizStudentMapEntity(quizStudentMapEntity);
				itemResultEtity.setQuizItemEntity(quizDao.getQuizItemById(itemResultModel.getQuizItemId()));
				itemResultEtity.setResult(itemResultModel.getResult());
				itemResultEtity.setStatus(true);
				itemResultEtity.setChooseAnswer(itemResultModel.getAnswerCorrectString());
				itemResultModel.setInsUid(userUid);
				DatabaseUtil.setCommonFields(itemResultEtity, itemResultModel);
				listQuizItemResultEntity.add(itemResultEtity);
			}		
		}
		
		submitQuizItemMulti(listQuizItem, quizStudentMapEntity, listQuizItemResultEntity, userUid);
		
		quizStudentMapEntity.setListQuizItemResultEntity(listQuizItemResultEntity);
		quizStudentMapEntity.setQuizEntity(quizDao.getQuizById(submitRequest.getQuizId()));
		quizStudentMapEntity.setStudentEntity(studentEntity);
		quizStudentMapEntity.setTotalCorrectAnswer(submitRequest.getTotalCorrectAnswer());
		quizStudentMapEntity.setTotalWrongAnswer(submitRequest.getTotalWrongAnswer());
		quizStudentMapEntity.setTotalNotChoose(submitRequest.getTotalNotChoose());
		quizStudentMapEntity.setStatus(true);
		submitRequest.setInsUid(userUid);
		DatabaseUtil.setCommonFields(quizStudentMapEntity, submitRequest);
		quizDao.submit(quizStudentMapEntity);
		return res;
	}
	
	public void submitQuizItemMulti(List<QuizItemModel> listQuizItem, QuizStudentMapEntity quizStudentMapEntity,
			List<QuizItemResultEntity> listQuizItemResultEntity, String userUid) {
		for (int i = 0; i < listQuizItem.size(); i++) {
			QuizItemModel quizItem = listQuizItem.get(i);
			if(quizItem.getQuizItemType().equals(QUIZ_ITEM_MULTI_CHOISE_TYPE)) {
				QuizItemResultEntity itemResultEtity = new QuizItemResultEntity();
				itemResultEtity.setQuizItemStudentId(null);
				itemResultEtity.setQuizStudentMapEntity(quizStudentMapEntity);
				itemResultEtity.setQuizItemEntity(quizDao.getQuizItemById(quizItem.getKey()));
				itemResultEtity.setResult(quizItem.getResult());
				itemResultEtity.setStatus(true);
				itemResultEtity.setChooseAnswer(null);
				quizItem.setInsUid(userUid);
				DatabaseUtil.setCommonFields(itemResultEtity, quizItem);
				
				List<QuizItemAnswerModel> listAnswer =  quizItem.getListAnswer();
				List<QuizItemAnswerResultEntity> listAnswerEntity = new ArrayList<QuizItemAnswerResultEntity>();
				for(int j = 0 ; j<listAnswer.size(); j++) {
					QuizItemAnswerResultEntity answerResultEntity = new QuizItemAnswerResultEntity();
					answerResultEntity.setQuizItemAnswerResultId(null);
					answerResultEntity.setQuizItemResultEntity(itemResultEtity);
					
					QuizItemAnswerEntity answer = quizDao.getAnswerById(listAnswer.get(j).getKey());
					answerResultEntity.setQuizItemAnswerEntity(answer);
					
					answerResultEntity.setIsChoose(listAnswer.get(j).getIsChoose());
					listAnswerEntity.add(answerResultEntity);
				}
				
				itemResultEtity.setListQuizItemAnswerResult(listAnswerEntity);
				listQuizItemResultEntity.add(itemResultEtity);
			}			
		}
	}
	
	public String exportPDF(Long id) throws Exception {
		QuizModel model =  getQuizById(id);
		List<String> pathList = new ArrayList<>();
		List<String> listDelete  = new ArrayList<>();
		
		String quizNm = model.getQuizNm();
		
		String path = exportToPDFService.exportQuizToPDF(model); 
		String pathCorrect = exportToPDFService.exportRightAnswerToPDF(model);
		
		System.out.println(" ===== path PDF =====");
		System.out.println(" ===== pathCorrect PDF =====");
		
		
		pathList.add(pathUploadDir+path+".pdf");
		pathList.add(pathUploadDir+pathCorrect+".pdf");
		
		listDelete.add(path+".pdf");
		listDelete.add(pathCorrect+".pdf");
		
		String fileName = exportToPDFService.archiveZip(pathList, listDelete);
		System.out.println("===== fileName PDF ======");
		return fileName+"!#@"+quizNm;
	} 

	public Object getScaleCorrectByQuizItem(Long id) {
		
		return quizDao.getScaleCorrectByQuizItem(id);
	}
	

	public Object getListQuestionStatistical(HashMap<String, Object> parameter) {
		return quizDao.getListQuestionStatistical(parameter);
	}

}
