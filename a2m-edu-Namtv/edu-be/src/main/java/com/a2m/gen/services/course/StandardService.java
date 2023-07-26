package com.a2m.gen.services.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.StandardDao;
import com.a2m.gen.dao.SubjectDao;
import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectHistory;
import com.a2m.gen.entities.edu.AemCourseSubjectStandard;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardHistory;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardNote;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardNoteHistory;
import com.a2m.gen.entities.edu.EamStudentInfo;
import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.models.common.ApproveModel;
import com.a2m.gen.models.course.QuizModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectHistoryModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.course.SubjectStandardModel;
import com.a2m.gen.models.course.SubjectStandardHistoryModel;
import com.a2m.gen.models.course.SubjectStandardNoteModel;
import com.a2m.gen.models.course.SubjectStandardResultHistoryModel;
import com.a2m.gen.services.common.ApproveService;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.ExportToPDFService;
import com.a2m.gen.utils.CastUtil;

@Service
public class StandardService {
	@Autowired
	private CommonService commonService;

	@Autowired
	private StandardDao standardDao;

	@Autowired
	private ExportToPDFService exportToPDFService;

	@Value("${path.upload.dir}")
	private String pathUploadDir;

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private ApproveService approveService;

//	@Transactional
//	public Map<Object, Object> saveStandard(List<SubjectStandardModel> models) throws Exception {
//		Map<Object, Object> res = new HashMap<Object, Object>();
//		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
//
//		AemCourseSubject subjectEntity = subjectDao.getSubject(models.get(0).getSubjectId());
//
//		String SUB_DOCUMENT_STATUS = subjectEntity.getDocumentStatus();
//		Long approvalHisId = subjectEntity.getApprovalHistoryId();
//		String approvalStatus = "";
//		if (approvalHisId != null) {
//			ApproveModel apr = approveService.getApprove(approvalHisId);
//			approvalStatus = apr.getApprovalStatus();
//		}
//
//		switch (SUB_DOCUMENT_STATUS) {
//		case CommonContants.DOCUMENT_STATUS_APPROVAL:
//			handleSaveStandApproval(models, approvalStatus);
//			break;
//		case CommonContants.DOCUMENT_STATUS_DRAFT:
//			handleSaveStandDraft(models, SUB_DOCUMENT_STATUS);
//			break;
//		default:
//			break;
//		}
//
//		return res;
//	}
	
	@Transactional
	public AemCourseSubjectStandard saveStandard(SubjectStandardModel model) throws Exception {
		String userUid = commonService.getUserUid();

		AemCourseSubjectStandard db = new AemCourseSubjectStandard();
		if (!"".equals(CastUtil.castToString(model.getKey()))) {
			// update
			db = standardDao.getStandard(model);
			model.setUpdUid(userUid);
			db = standardDao.saveStandard(model, db);
			
		} else {
			// insert
			model.setInsUid(userUid);
			db = standardDao.saveStandard(model, db);
		}
		return db;
	}
	
	@Transactional
	public Map<Object, Object> saveStandardHistory(SubjectStandardHistoryModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();

		AemCourseSubjectStandardHistory db = new AemCourseSubjectStandardHistory();
		if (!"".equals(CastUtil.castToString(model.getKey()))) {
			if(model.getDeleteFlag()) {
				standardDao.deleteStandHistory(model.getKey());
			}else {
				// update
				db = standardDao.getStandardHistory(model);
				model.setUpdUid(userUid);
				db = standardDao.saveStandardHistory(model, db);
			}
		} else {
			// insert
			model.setInsUid(userUid);
			db = standardDao.saveStandardHistory(model, db);
		}
		return res;
	}
	
	@Transactional
	public Map<Object, Object> saveStandardHistory(List<SubjectStandardHistoryModel> models) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		List<Long> listKey = new ArrayList<>();
		
		for (SubjectStandardHistoryModel model : models) {
			AemCourseSubjectStandardHistory db = new AemCourseSubjectStandardHistory();
			if (!"".equals(CastUtil.castToString(model.getKey()))) {
				if(model.getDeleteFlag()) {
					standardDao.deleteStandHistory(model.getKey());
				}else {
					// update
					db = standardDao.getStandardHistory(model);
					model.setUpdUid(userUid);
					db = standardDao.saveStandardHistory(model, db);
				}
			} else {
				// insert
				model.setInsUid(userUid);
				db = standardDao.saveStandardHistory(model, db);
				listKey.add(db.getStandardHistoryId());
			}
		}
		
		//hilight tab stand when edit
		Long subjectId = null;
		if(models.size()>0) {
			subjectId = models.get(0).getSubjectId();
			SubjectHistoryModel param = new SubjectHistoryModel();
			param.setSubjectId(subjectId);
			AemCourseSubjectHistory subjectHistoryEntity = subjectDao.searchSubjectHistory(param);
			subjectHistoryEntity.setEditStand(true);
			subjectDao.mergeSubjectHistory(subjectHistoryEntity);
		}
		
		//hilight tab stand when edit
		
		res.put("listKey", listKey);
		return res;
	}

//	public Map<Object, Object> handleSaveStandDraft(List<SubjectStandardModel> model, 
//		String SUB_DOCUMENT_STATUS) throws Exception{
//		
//		Map<Object, Object> res = new HashMap<Object, Object>();
//		
//			String userUid = commonService.getUserUid();
//    	
//        for (SubjectStandardModel standModel : model) {
//        	
//        	standModel.setIsApproval(false);
//        	standModel.setDocumentStatus(SUB_DOCUMENT_STATUS);
//        	
//            AemCourseSubjectStandard db = new AemCourseSubjectStandard();
//            if (!"".equals(CastUtil.castToString(standModel.getKey()))) {
//                db = standardDao.getStandard(standModel);
//                standModel.setUpdUid(userUid);
//                if (standModel.getDeleteFlag()) {
//                	standardDao.deleteStandHistory(standModel.getStandardHistoryId());
//                  standardDao.deleteStand(standModel.getKey());
//                } else {
//                    db = standardDao.saveStandard(standModel, db);
//                    
//                    SubjectStandardHistoryModel standHisModel = new SubjectStandardHistoryModel(db);
//                    
//                    updateStandardHistoryCustom(standHisModel);
//                }
//            } else {
//            	standModel.setInsUid(userUid);
//                db = standardDao.saveStandard(standModel, db);
//                
//                SubjectStandardHistoryModel standHisModel = new SubjectStandardHistoryModel(db);
//                
//                insertStandardHistory(standHisModel);
//            }
//
//            res.put(CommonContants.KEY, db.getStandId());
//        }
//        return res;
//    }
//
//	public Map<Object, Object> handleSaveStandApproval(List<SubjectStandardModel> models,
//			String approvalStatus) throws Exception {
//		
//		Map<Object, Object> res = new HashMap<Object, Object>();
//		
//		switch (approvalStatus) {
//		
//		case CommonContants.APPROVAL_STATUS_APPROVAL:
//			handleSaveStandApprovalApprove(models, approvalStatus);
//			break;
//		case CommonContants.APPROVAL_STATUS_PENDING:
//			handleSaveStandApprovalPending(models, approvalStatus);
//			break;
//		case CommonContants.APPROVAL_STATUS_REJECT:
//			handleSaveStandApprovalReject(models, approvalStatus);
//			break;
//
//		default:
//			break;
//		}
//
//		return res;
//	}
//	
//	
//			
//
//	public Map<Object, Object> handleSaveStandApprovalApprove(List<SubjectStandardModel> models, String approvalStatus) throws Exception {
//		Map<Object, Object> res = new HashMap<Object, Object>();
//		
//		for (SubjectStandardModel subjectStandardModel : models) {
//			SubjectStandardHistoryModel standHisModel = new SubjectStandardHistoryModel(subjectStandardModel);
//			standHisModel.setDocumentStatus(CommonContants.DOCUMENT_STATUS_DRAFT);
//			standHisModel.setIsApproval(false);
//			standHisModel.setKey(subjectStandardModel.getKey());
//			saveStandardHistory(standHisModel);
//		}
//		
//		return res;
//	}
//	
//	public Map<Object, Object> handleSaveStandApprovalPending(List<SubjectStandardModel> models, String approvalStatus) throws Exception {
//		Map<Object, Object> res = new HashMap<Object, Object>();
//		
//		for (SubjectStandardModel subjectStandardModel : models) {
//			SubjectStandardHistoryModel standHisModel = new SubjectStandardHistoryModel(subjectStandardModel);
//			standHisModel.setDocumentStatus(CommonContants.DOCUMENT_STATUS_DRAFT);
//			standHisModel.setIsApproval(false);
//			standHisModel.setKey(subjectStandardModel.getKey());
//			saveStandardHistory(standHisModel);
//		}
//		
//		return res;
//	}
//	
//	public Map<Object, Object> handleSaveStandApprovalReject(List<SubjectStandardModel> models, String approvalStatus) throws Exception {
//		Map<Object, Object> res = new HashMap<Object, Object>();
//		
//		for (SubjectStandardModel subjectStandardModel : models) {
//			SubjectStandardHistoryModel standHisModel = new SubjectStandardHistoryModel(subjectStandardModel);
//			standHisModel.setDocumentStatus(CommonContants.DOCUMENT_STATUS_DRAFT);
//			standHisModel.setIsApproval(false);
//			standHisModel.setKey(subjectStandardModel.getKey());
//			saveStandardHistory(standHisModel);
//		}
//		
//		return res;
//	}
//
//	public int updateStandardHistoryCustom(SubjectStandardHistoryModel model) {
//
//		int result = standardDao.updateStandardHistoryCustom(model);
//		return result;
//	}

//	public AemCourseSubjectStandardHistory insertStandardHistory(SubjectStandardHistoryModel model) {
//		AemCourseSubjectStandardHistory db = new AemCourseSubjectStandardHistory();
//
//		db = standardDao.saveStandardHistory(model, db);
//		return db;
//	}

	@Transactional
	public void delete(String subjectId, String courseId) {
//        
	}

	public List<SubjectStandardModel> getStandardById(String standType, Long subjectId) {
		List<SubjectStandardModel> result = new ArrayList<SubjectStandardModel>();
		AemCourseSubject aemSubject = standardDao.getSubject(subjectId);
		List<AemCourseSubjectStandard> standEntity = new ArrayList<AemCourseSubjectStandard>();
		if (aemSubject != null) {
			standEntity = aemSubject.getListStand();
			if (standEntity != null) {
				for (AemCourseSubjectStandard db : standEntity) {
					SubjectStandardModel model = new SubjectStandardModel(db);
					String type = model.getStandType();
					if (type.equals(standType) && !model.getDeleteFlag()) {
						result.add(model);
					}
				}
			}
		}

		return result;

	}
	
	public List<SubjectStandardHistoryModel> getStandardHistory(SubjectStandardHistoryModel param) {
		List<SubjectStandardHistoryModel> result = new ArrayList<SubjectStandardHistoryModel>();
		
		List<AemCourseSubjectStandardHistory> listStandHis = standardDao.getListStandardHistory(param);

		if (listStandHis != null) {
			for (AemCourseSubjectStandardHistory dbHis : listStandHis) {
				SubjectStandardHistoryModel model = new SubjectStandardHistoryModel(dbHis);
				String type = model.getStandType();
				result.add(model);
			}
		}
		

		return result;

	}

	public List<SubjectStandardHistoryModel> getStandardHistoryBySubjectId(SubjectStandardHistoryModel model) {
		List<SubjectStandardHistoryModel> result = new ArrayList<SubjectStandardHistoryModel>();

		List<AemCourseSubjectStandardHistory> listStandHisEntity = new ArrayList<AemCourseSubjectStandardHistory>();
		// get data db
		listStandHisEntity = standardDao.getListStandardHistory(model);

		for (AemCourseSubjectStandardHistory standHisEntity : listStandHisEntity) {
			SubjectStandardHistoryModel tmpModel = new SubjectStandardHistoryModel(standHisEntity);

			result.add(tmpModel);
		}

		return result;
	}
	
	public List<SubjectStandardHistoryModel> migarateDataSubjectStandHisToMain(Long subjectId) throws Exception{
		
		//delete stand and note main
		deleteStandardStandartNote(subjectId);
		
		SubjectStandardHistoryModel param = new SubjectStandardHistoryModel();
		param.setSubjectId(subjectId);		
		List<SubjectStandardHistoryModel> listStandHis = getStandardHistoryBySubjectId(param);
		for (SubjectStandardHistoryModel standHis : listStandHis) {
			
			//save standard
			SubjectStandardModel stand = new SubjectStandardModel(standHis, null);
			AemCourseSubjectStandard standEntity = saveStandard(stand);
			Long newStandId = standEntity.getStandId();
			
			SubjectStandardResultHistoryModel condition = new SubjectStandardResultHistoryModel();
			condition.setStandHistoryId(standHis.getKey());			
			List<AemCourseSubjectStandardNoteHistory> listNoteHis = standardDao.getListStandardNoteHistory(condition);			
			for (AemCourseSubjectStandardNoteHistory noteHis : listNoteHis) {
				SubjectStandardNoteModel note = new SubjectStandardNoteModel(noteHis, null, newStandId);
				saveStandardNote(note);
				
			}
		}
		return listStandHis;
	}
	
	public void deleteStandardStandartNote(Long subjectId) {
		List<SubjectStandardModel> listStand = getListStandardById(subjectId);
		for (SubjectStandardModel stand : listStand) {
			standardDao.deleteNote(stand.getKey());
			standardDao.deleteStand(stand.getKey());
		}
	}

//	public List<SubjectStandardHistoryModel> migarateDataSubjectStandHisToMain(
//			List<SubjectStandardHistoryModel> listStandHis) {
//		
//		//remove all history
//		if(listStandHis.size() == 0 ) return new ArrayList<SubjectStandardHistoryModel>();
//		SubjectStandardHistoryModel standHisFirst = listStandHis.get(0);
//		standardDao.deleteStandHisWithSubjectIdAndType(standHisFirst.getSubjectId(), standHisFirst.getStandType());
//		
//		//get list main
//		AemCourseSubject aemSubject = standardDao.getSubject(standHisFirst.getSubjectId());
//		List<AemCourseSubjectStandard> listStandEntity = new ArrayList<AemCourseSubjectStandard>();
//		
//		if (aemSubject != null) {
//			listStandEntity = aemSubject.getListStand();
//			
//		}
//		List<SubjectStandardModel> listStandModel = new ArrayList<SubjectStandardModel>();
//		for (AemCourseSubjectStandard standTmp : listStandEntity) {
//			SubjectStandardModel stm = new SubjectStandardModel(standTmp);
//			listStandModel.add(stm);
//		}
//		
//		List<Long> listIdStandUndelete = new ArrayList<Long>();
//		
//		for (SubjectStandardHistoryModel hisModel : listStandHis) {
//			
//			if (hisModel.getStandId() != null) {
//				
//				listIdStandUndelete.add( hisModel.getStandId() );
//				
//				AemCourseSubjectStandard standEntity = standardDao.getStandard(hisModel.getStandId());
//				
//				SubjectStandardModel subStandModel = new SubjectStandardModel();
//				subStandModel.setSubjectId(hisModel.getSubjectId());
//				subStandModel.setIsApproval(hisModel.getIsApproval());
//				subStandModel.setDocumentStatus(hisModel.getDocumentStatus());
//				subStandModel.setStandCode(hisModel.getStandCode());
//				subStandModel.setStandContent(hisModel.getStandContent());
//				subStandModel.setStandType(hisModel.getStandType());
//				subStandModel.setStatus(hisModel.getStatus());
//				subStandModel.setInsDate(hisModel.getInsDate());
//				subStandModel.setInsUid(hisModel.getInsUid());
//				subStandModel.setUpdUid(hisModel.getUpdUid());
//				subStandModel.setUpdDate(hisModel.getUpdDate());
//				subStandModel.setDeleteFlag(hisModel.getDeleteFlag());
//				
//				//his -> main
//				standEntity = standardDao.saveStandard(subStandModel, standEntity);
//				
//				//reInsert history
//				SubjectStandardHistoryModel standHisModel = new SubjectStandardHistoryModel(standEntity);
//				hisModel.setDocumentStatus(CommonContants.DOCUMENT_STATUS_APPROVAL);
//				hisModel.setIsApproval(true);
//				standardDao.saveStandardHistory(standHisModel, new AemCourseSubjectStandardHistory());
//			} else {
//				AemCourseSubjectStandard standEntity = new AemCourseSubjectStandard();
//				
//				SubjectStandardModel subStandModel = new SubjectStandardModel();
//				subStandModel.setSubjectId(hisModel.getSubjectId());
//				subStandModel.setIsApproval(hisModel.getIsApproval());
//				subStandModel.setDocumentStatus(hisModel.getDocumentStatus());
//				subStandModel.setStandCode(hisModel.getStandCode());
//				subStandModel.setStandContent(hisModel.getStandContent());
//				subStandModel.setStandType(hisModel.getStandType());
//				subStandModel.setStatus(hisModel.getStatus());
//				subStandModel.setInsDate(hisModel.getInsDate());
//				subStandModel.setInsUid(hisModel.getInsUid());
//				subStandModel.setUpdUid(hisModel.getUpdUid());
//				subStandModel.setUpdDate(hisModel.getUpdDate());
//				subStandModel.setDeleteFlag(hisModel.getDeleteFlag());
//				//his -> new
//				standEntity = standardDao.saveStandard(subStandModel, standEntity);
//				
//				//reInsert history
//				SubjectStandardHistoryModel standHisModel = new SubjectStandardHistoryModel(standEntity);
//				hisModel.setDocumentStatus(CommonContants.DOCUMENT_STATUS_APPROVAL);
//				hisModel.setIsApproval(true);
//				standardDao.saveStandardHistory(standHisModel, new AemCourseSubjectStandardHistory());
//			}
//		}
//		
//		for (SubjectStandardModel stm : listStandModel) {
//			Long id = stm.getKey();
//			if(!listIdStandUndelete.contains(id)) {
//				standardDao.deleteStand(id);
//			}
//		}
//
//		return listStandHis;
//	}

	public List<SubjectStandardModel> getListStandardById(Long subjectId) {
		List<SubjectStandardModel> result = new ArrayList<SubjectStandardModel>();
		AemCourseSubject aemSubject = standardDao.getSubject(subjectId);
		List<AemCourseSubjectStandard> standEntity = new ArrayList<AemCourseSubjectStandard>();
		standEntity = aemSubject.getListStand();
		Collections.sort(standEntity, new Comparator<AemCourseSubjectStandard>() {
			@Override
			public int compare(AemCourseSubjectStandard u1, AemCourseSubjectStandard u2) {
				return u1.getStandType().compareTo(u2.getStandType());
			}
		});
		if (standEntity != null) {
			for (AemCourseSubjectStandard db : standEntity) {
				SubjectStandardModel model = new SubjectStandardModel(db);
				if (!model.getDeleteFlag()) {
					result.add(model);
				}
			}
		}
		return result;
	}
	
	public List<SubjectStandardHistoryModel> getListStandHistoryBySubjectId(Long subjectId) {
		List<SubjectStandardHistoryModel> result = new ArrayList<SubjectStandardHistoryModel>();
		
		SubjectStandardHistoryModel param = new SubjectStandardHistoryModel();
		param.setSubjectId(subjectId);
		List<AemCourseSubjectStandardHistory> listStandHisEntity = standardDao.getListStandardHistory(param);
		
		Collections.sort(listStandHisEntity, new Comparator<AemCourseSubjectStandardHistory>() {
			@Override
			public int compare(AemCourseSubjectStandardHistory u1, AemCourseSubjectStandardHistory u2) {
				return u1.getStandType().compareTo(u2.getStandType());
			}
		});
		if (listStandHisEntity != null) {
			for (AemCourseSubjectStandardHistory db : listStandHisEntity) {
				SubjectStandardHistoryModel model = new SubjectStandardHistoryModel(db);
				SubjectStandardResultHistoryModel standNoteHisModel = new SubjectStandardResultHistoryModel();
				List<SubjectStandardResultHistoryModel> listStandNoteHis  = new ArrayList<SubjectStandardResultHistoryModel>();
				standNoteHisModel.setStandHistoryId(db.getStandardHistoryId());
				List<AemCourseSubjectStandardNoteHistory> listStandNoteHisEntity = standardDao.getListStandardNoteHistoryByStandId(standNoteHisModel);
				for(AemCourseSubjectStandardNoteHistory note : listStandNoteHisEntity) {
				    SubjectStandardResultHistoryModel standNoteModel = new SubjectStandardResultHistoryModel(note);
				    listStandNoteHis.add(standNoteModel);
				}
				model.setListStandNote(listStandNoteHis);
				if (!model.getDeleteFlag()) {
					result.add(model);
				}
			}
		}
		return result;
	}

    public List<SubjectStandardResultHistoryModel> getListStandResultHistoryByChapterId(Long chapterId) {
        List<SubjectStandardResultHistoryModel> result = new ArrayList<SubjectStandardResultHistoryModel>();
        
        SubjectStandardResultHistoryModel param = new SubjectStandardResultHistoryModel();
        param.setChapterId(chapterId);
        List<AemCourseSubjectStandardNoteHistory> listStandNoteHisEntity = standardDao.getListStandardNoteHistory(param);
        
        Collections.sort(listStandNoteHisEntity, new Comparator<AemCourseSubjectStandardNoteHistory>() {
            @Override
            public int compare(AemCourseSubjectStandardNoteHistory u1, AemCourseSubjectStandardNoteHistory u2) {
                return u1.getStandType().compareTo(u2.getStandType());
            }
        });
        if (listStandNoteHisEntity != null) {
            for (AemCourseSubjectStandardNoteHistory db : listStandNoteHisEntity) {
                SubjectStandardResultHistoryModel model = new SubjectStandardResultHistoryModel(db);
                result.add(model);
            }
        }
        return result;
    }

	@Transactional
	public Map<Object, Object> saveStandardNote(List<SbjChapterModel> model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		for (SbjChapterModel element : model) {
			List<SubjectStandardNoteModel> listStandNote = element.getListStandNote();
			for (SubjectStandardNoteModel standNote : listStandNote) {
				AemCourseSubjectStandardNote db = saveStandardNote(standNote);
				res.put(CommonContants.KEY, db.getNoteId());

			}
		}

		return res;
	}
	
	public AemCourseSubjectStandardNote saveStandardNote(SubjectStandardNoteModel standNote) throws Exception {
		String userUid = commonService.getUserUid();
		AemCourseSubjectStandardNote db = new AemCourseSubjectStandardNote();
		if (!"".equals(CastUtil.castToString(standNote.getKey()))) {
			standNote.setUpdUid(userUid);
			db = standardDao.getStandardNote(standNote);
			db = standardDao.saveStandardNote(standNote, db);
		} else {
			standNote.setInsUid(userUid);
			db = standardDao.saveStandardNote(standNote, db);
		}

		return db;
	}
	
	@Transactional
	public Map<Object, Object> saveStandardNoteHistory(List<SbjChapterModel> model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		for (SbjChapterModel element : model) {
			List<SubjectStandardNoteModel> listStandNote = element.getListStandNote();
			for (SubjectStandardNoteModel standNote : listStandNote) {
				AemCourseSubjectStandardNoteHistory db = new AemCourseSubjectStandardNoteHistory();
				if (!"".equals(CastUtil.castToString(standNote.getKey()))) {
					standNote.setUpdUid(userUid);
					db = standardDao.getStandardNoteHistory(standNote);
					db = standardDao.saveStandardNoteHistory(standNote, db);
				} else {
					standNote.setInsUid(userUid);
					db = standardDao.saveStandardNoteHistory(standNote, db);
				}

				res.put(CommonContants.KEY, db.getNoteHistoryId());

			}
		}

		return res;
	}

	public List<SubjectStandardNoteModel> getListStandardNoteById(Long subjectId) {
		List<SubjectStandardNoteModel> result = new ArrayList<SubjectStandardNoteModel>();
		AemCourseSubject aemSubject = standardDao.getSubject(subjectId);
		SubjectModel subjectModel = new SubjectModel(aemSubject);
		List<SbjChapterModel> listChapter = subjectModel.getChapterModels();
		for (SbjChapterModel chapter : listChapter) {
			List<SubjectStandardModel> listStand = chapter.getListStand();
			List<SubjectStandardNoteModel> listStandNote = chapter.getListStandNote();
			for (SubjectStandardNoteModel note : listStandNote) {
				for (SubjectStandardModel stand : listStand) {
					if (note.getStandId() == stand.getKey()) {
						stand.setStandResult(note.getStandResult());
					}
				}
			}
		}
		return result;
	}

	@Transactional
	public String exportPDFStandSummaryBySubjectId(String id) throws Exception {
		Long subjectId = Long.parseLong(id);
		AemCourseSubject aemSubject = standardDao.getSubject(subjectId);
		SubjectModel subjectModel = new SubjectModel(aemSubject);
		String subjectNm = subjectModel.getSubjectNm();
		String path = exportToPDFService.exportPDFStandSummaryBySubject(subjectModel);

		return path + "!#@" + subjectNm;
	}

	@Transactional
	public List<SbjChapterModel> getLectureScheduleBySubjectId(Long id) {
		List<SbjChapterModel> result = new ArrayList<SbjChapterModel>();
		AemCourseSubject aemSubject = standardDao.getSubject(id);
		SubjectModel subjectModel = new SubjectModel(aemSubject);
		List<SbjChapterModel> listChapter = subjectModel.getChapterModels();
		for (SbjChapterModel chapter : listChapter) {
			List<SubjectStandardNoteModel> listStandNote = chapter.getListStandNote();
			Collections.sort(listStandNote, new Comparator<SubjectStandardNoteModel>() {
				@Override
				public int compare(SubjectStandardNoteModel u1, SubjectStandardNoteModel u2) {
					return u1.getStandType().compareTo(u2.getStandType());
				}
			});
			result.add(chapter);
		}
		return result;
	}

    @Transactional
    public List<SbjChapterModel> getLectureScheduleHistoryBySubjectId(Long id) {
        List<SbjChapterModel> result = new ArrayList<SbjChapterModel>();
        AemCourseSubject aemSubject = standardDao.getSubject(id);
        SubjectModel subjectModel = new SubjectModel(aemSubject, true);
        List<SbjChapterModel> listChapter = subjectModel.getChapterModels();
        for (SbjChapterModel chapter : listChapter) {
        	//getstandResult
        	chapter = getstandResult(chapter);
            List<SubjectStandardResultHistoryModel> listStandNote = chapter.getListStandNoteHis();
            Collections.sort(listStandNote, new Comparator<SubjectStandardResultHistoryModel>() {
                @Override
                public int compare(SubjectStandardResultHistoryModel u1, SubjectStandardResultHistoryModel u2) {
                    return u1.getStandType().compareTo(u2.getStandType());
                }
            });
            result.add(chapter);
        }
        return result;
    }
    public SbjChapterModel getstandResult(SbjChapterModel chapterModel){
        List<SubjectStandardResultHistoryModel> result = new ArrayList<SubjectStandardResultHistoryModel>();
        result = chapterModel.getListStandNoteHis();
        List<SubjectStandardHistoryModel> listStandHistoryBySubjectId = getListStandHistoryBySubjectId(chapterModel.getSubjectId());
        List<SubjectStandardResultHistoryModel> listStandNoteHistoryByChapterId = getListStandResultHistoryByChapterId(chapterModel.getKey());
        
        if (listStandNoteHistoryByChapterId.size() != 0) {
            for (SubjectStandardResultHistoryModel note : listStandNoteHistoryByChapterId) {
                for(SubjectStandardHistoryModel standard : listStandHistoryBySubjectId) {
                    if(standard.getKey() == note.getStandHistoryId()) {
                        if(standard.getDeleteFlag()) {
                            note.setDeleteFlag(true);
                        }else {
                            note.setDeleteFlag(false);
                        }
                    }
                }
                if(!note.getDeleteFlag()) {
                    result.add(note);
                }
            }
            for (SubjectStandardHistoryModel stand : listStandHistoryBySubjectId) {
                List<SubjectStandardResultHistoryModel> standNote = stand.getListStandNote();
                if (standNote.size() == 0) {
                    SubjectStandardResultHistoryModel noteModels = new SubjectStandardResultHistoryModel();
                    noteModels.setChapterId(chapterModel.getKey());
                    noteModels.setStandType(stand.getStandType());
                    noteModels.setStandHistoryId(stand.getStandHistoryId());
                    noteModels.setStatus(true);
                    noteModels.setStandResult("13-01");
                    if(!stand.getDeleteFlag()) {
                        result.add(noteModels);
                    }
                }
            }
        } else {
            for (SubjectStandardHistoryModel stand : listStandHistoryBySubjectId) {
                SubjectStandardResultHistoryModel noteModel = new SubjectStandardResultHistoryModel();
                noteModel.setChapterId(chapterModel.getKey());
                noteModel.setStandHistoryId(stand.getStandHistoryId());
                noteModel.setStandType(stand.getStandType());
                noteModel.setStatus(true);
                noteModel.setStandResult("13-01");
                if(!stand.getDeleteFlag()) {
                    result.add(noteModel);
                }
            }
        }
        
        return chapterModel;
    }

}
