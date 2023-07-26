package com.a2m.gen.services.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.SubjectDao;
import com.a2m.gen.entities.common.ApproveEntity;
import com.a2m.gen.entities.edu.AemCourseSubjectHistory;
import com.a2m.gen.models.common.ApproveModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectHistoryModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.repository.common.ApproveDao;
import com.a2m.gen.services.course.Course0102Service;
import com.a2m.gen.services.course.Course0103Service;
import com.a2m.gen.services.course.StandardService;
import com.a2m.gen.utils.CastUtil;

@Service
public class ApproveService {

	@Autowired
	private CommonService commonService;

	@Autowired
	private ApproveDao dao;
	
	@Autowired
	private Course0102Service subjectService;
	
	@Autowired
	private StandardService standardService;
	
	@Autowired
	private Course0103Service chapterService;
	
	@Autowired
	private SubjectDao subjectDao;

	@Transactional
	public List<ApproveModel> searchListPeding(Long uid, Boolean isAdmin) {
		List<ApproveModel> listApproveModel = new ArrayList<ApproveModel>();

		try {
			List<ApproveEntity> listEntity = dao.searchListPeding(uid, isAdmin);
			for (ApproveEntity entity : listEntity) {
				ApproveModel model = new ApproveModel(entity);				
				listApproveModel.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listApproveModel;
	}
	
	@Transactional
	public List<ApproveModel> search(ApproveModel search) {
		List<ApproveModel> listApproveModel = new ArrayList<ApproveModel>();

		try {
			List<ApproveEntity> listEntity = dao.search(search);
			for (ApproveEntity entity : listEntity) {
				ApproveModel model = new ApproveModel(entity);				
				listApproveModel.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listApproveModel;
	}
	
	@Transactional
	public Map<Object, Object> saveApprove(ApproveModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		ApproveEntity db = new ApproveEntity();
		if(!"".equals(CastUtil.castToString(model.getKey()))) {
			//update
			db = dao.getApprove(model.getKey());
			model.setUpdUid(userUid);
			db = dao.saveApprove(model, db);
			
			if(model.getApprovalStatus().equals(CommonContants.APPROVAL_STATUS_APPROVAL)) { // case approval subject
				if(model.getRefTable().equals("EAM_COURSE_SUBJECT")) {
					migarateDataSubjectHisToMain(model.getRefId());
					migarateDataSubjectStandHisToMain(model.getRefId());
					migarateDataChapter(model.getRefId());
					removeHiligtSubject(model.getRefId());
				}
			}
		}else {
			//insert
			model.setInsUid(userUid);
			if(model.getEmpAprUid().equals(userUid)) { // case approval subject
				model.setApprovalStatus(CommonContants.APPROVAL_STATUS_APPROVAL);
				if(model.getRefTable().equals("EAM_COURSE_SUBJECT")) {
					migarateDataSubjectHisToMain(model.getRefId());
					migarateDataSubjectStandHisToMain(model.getRefId());
					migarateDataChapter(model.getRefId());
					removeHiligtSubject(model.getRefId());
				}
			}
			db = dao.saveApprove(model, db);
			
			model = new ApproveModel(db);
			dao.setApprovalHistoryId(model);
		}
		res.put(CommonContants.KEY, db.getApprovalHistoryId());
		res.put(CommonContants.DETAIL_KEY, model);
		return res;
	}
	
	@Transactional
	public Map<Object, Object> cancelSubmit(ApproveModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		ApproveEntity db = dao.deleteApproval(model.getKey());
		model.setKey(null);
		dao.setApprovalHistoryId(model);
		
		model = new ApproveModel();
		res.put(CommonContants.KEY, db.getApprovalHistoryId());
		res.put(CommonContants.DETAIL_KEY, model);
		return res;
	}
	
	private Boolean migarateDataChapter(Long subjectId) throws Exception {
		SbjChapterModel args = new SbjChapterModel();
		args.setKey(subjectId);
		args.getAll = true;
		List<SbjChapterModel> chapters = chapterService.getSbjChapter(args);
		for (SbjChapterModel sbjChapterModel : chapters) {
			sbjChapterModel.setDocumentStatus(CommonContants.DOCUMENT_STATUS_APPROVAL);
			chapterService.saveChapter(sbjChapterModel);
		}
		return true;
	}

	public Boolean migarateDataSubjectStandHisToMain(Long subjectId) throws Exception {		
		standardService.migarateDataSubjectStandHisToMain(subjectId);
		return true;
	}
	
	public void migarateDataSubjectHisToMain(Long subjectId) throws Exception{
		SubjectHistoryModel hisModel = subjectService.getSubjectHistory(subjectId);
		SubjectModel model = new SubjectModel(hisModel);
		model.setDocumentStatus(CommonContants.DOCUMENT_STATUS_APPROVAL);
		subjectService.saveSubjectOnly(model);
		
		removeHiligtSubject(subjectId);
	}
	
	public void removeHiligtSubject(Long subjectId) {
		//hilight when add new chapter
		SubjectHistoryModel param = new SubjectHistoryModel();
		param.setSubjectId(subjectId);
		AemCourseSubjectHistory subjectHistoryEntity = subjectDao.searchSubjectHistory(param);
		subjectHistoryEntity.setEditListChapter(false);
		subjectHistoryEntity.setEditStand(false);
		subjectHistoryEntity.setEditSubjectForm(false);
		subjectDao.mergeSubjectHistory(subjectHistoryEntity);
		//hilight when add new chapter
	}
	
	@Transactional
	public List<HashMap<String, Object>> getListApprover() {
		return dao.getListApprover();
	}
	
	public ApproveModel getApprove(Long key) {
		return dao.getApprove(key)!=null ? new ApproveModel(dao.getApprove(key)) : new ApproveModel();
	}

}