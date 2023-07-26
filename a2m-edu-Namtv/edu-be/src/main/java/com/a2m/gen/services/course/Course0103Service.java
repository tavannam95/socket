package com.a2m.gen.services.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.SbjChapterDao;
import com.a2m.gen.dao.SubjectDao;
import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectHistory;
import com.a2m.gen.entities.edu.AemCsChapterFile;
import com.a2m.gen.entities.edu.AemCscLectureFile;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.ChapterFileModel;
import com.a2m.gen.models.course.LectureFileModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.models.course.QuizModel;
import com.a2m.gen.models.course.SbjChapterModel;
import com.a2m.gen.models.course.SubjectHistoryModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.DocUserMapModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.tags.TagService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.entities.edu.AemDoUserMapEntity;


@Service
public class Course0103Service {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private SbjChapterDao sbjChapterDao;
	 
	 @Autowired 
	 private SubjectDao subjectDao;

	 @Autowired 
	 private QuizService quizService;

	 @Autowired 
	 private Course0101Service course0101Service;
	 
	 @Autowired 
	 private TagService tagService;
	
	public List<SbjChapterModel> getSbjChapter(SbjChapterModel search) throws Exception {
		List<SbjChapterModel> sbjChapterModels = new ArrayList<SbjChapterModel>();
		List<AemCouSbjChapter> lst = sbjChapterDao.getChapters(search);
		for(AemCouSbjChapter db :lst) {
			SbjChapterModel model = new SbjChapterModel(db); 
//	        String userUid = sbjChapterDao.getUserIdByTeachId(model.getSubjectId());
//	        model.setCourseUid(userUid);
			sbjChapterModels.add(model); 
		}
		return sbjChapterModels;
	}
    
    public List<SbjChapterModel> getSbjChapterSelect(ParamBaseModel search) throws Exception {
        List<SbjChapterModel> sbjChapterModels = new ArrayList<SbjChapterModel>();
        List<AemCouSbjChapter> lst = sbjChapterDao.getChapterSelect(search);
        for(AemCouSbjChapter db :lst) {
            SbjChapterModel model = new SbjChapterModel(db); 
            sbjChapterModels.add(model); 
        }
        return sbjChapterModels;
    }

	public Map<Object, Object> saveChapter(SbjChapterModel SbjChapterModel) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		AemCouSbjChapter db = new AemCouSbjChapter();
		if(!"".equals(CastUtil.castToString(SbjChapterModel.getKey()))) {
			//update
			db = sbjChapterDao.getChapter(SbjChapterModel);
			SbjChapterModel.setUpdUid(userUid);
			sbjChapterDao.save(SbjChapterModel, db);
			//tag
			tagService.handleTag(SbjChapterModel.getTags());
			//file
			List<ChapterFileModel> chapterFileModels = SbjChapterModel.getChapterFileModels();
			for (ChapterFileModel chapterFileModel : chapterFileModels) {
				//insert file
				if(chapterFileModel.getCrud().equals("C")) {
					sbjChapterDao.insertChapterFile(chapterFileModel, new AemCsChapterFile(), SbjChapterModel.getKey());
				}
				//drop file
				if(chapterFileModel.getCrud().equals("D")) {
					//sbjChapterDao.insertLectureFile(lectureFileModel, new AemCscLectureFile(), lectureModel.getKey());
					sbjChapterDao.dropChapterFile(chapterFileModel, new AemCsChapterFile(), SbjChapterModel.getKey());
				}
				
			}
			
		}else {
			//insert
			SbjChapterModel.setInsUid(userUid);
			db = sbjChapterDao.save(SbjChapterModel, db);
			
			//hilight when add new chapter
			SubjectHistoryModel param = new SubjectHistoryModel();
			param.setSubjectId(SbjChapterModel.getSubjectId());
			AemCourseSubjectHistory subjectHistoryEntity = subjectDao.searchSubjectHistory(param);
			subjectHistoryEntity.setEditListChapter(true);
			subjectDao.mergeSubjectHistory(subjectHistoryEntity);
			//hilight when add new chapter
			
			// tags
			tagService.handleTag(SbjChapterModel.getTags());
			//file
			List<ChapterFileModel> chapterFileModels = SbjChapterModel.getChapterFileModels();
			for (ChapterFileModel chapterFileModel : chapterFileModels) {
				//insert file
				if(chapterFileModel.getCrud().equals("C")) {
					SbjChapterModel = new SbjChapterModel(db);
					sbjChapterDao.insertChapterFile(chapterFileModel, new AemCsChapterFile(),SbjChapterModel.getKey());
				}
			}
		}
		
		res.put(CommonContants.KEY, db.getChapterId());
		return res;
	}

	public Map<Object, Object> saveOrdNo(SbjChapterModel SbjChapterModel) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		AemCouSbjChapter db = new AemCouSbjChapter();
		List<QuizModel> listQuizModel = SbjChapterModel.getListQuiz();
		List<LectureModel> listLectureModel = SbjChapterModel.getLectureModels();
		if(listQuizModel.size() != 0) {
			for(QuizModel quiz : listQuizModel) {
				quizService.saveQuiz(quiz);
			}
		}
		if(listLectureModel.size() != 0) {
			for(LectureModel lecture : listLectureModel) {
				course0101Service.saveLecture(lecture);
			}
		}
		res.put(CommonContants.KEY, db.getChapterId());
		return res;
	}

	public Map<Object, Object> saveViewDoc(DocUserMapModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		AemDoUserMapEntity db = new AemDoUserMapEntity();
		if(!"".equals(CastUtil.castToString(model.getKey()))) {
			//update
			db = sbjChapterDao.getViewDoc(model);
			model.setUpdUid(userUid);
			sbjChapterDao.saveViewDoc(model, db);
			
		}else {
			//insert
			model.setInsUid(userUid);
			db = sbjChapterDao.saveViewDoc(model, db);
		}
		res.put(CommonContants.KEY, db.getTableId());
		return res;
	}
	
	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
	    SbjChapterModel sbjChapterModel = new SbjChapterModel();
	    sbjChapterModel.setKey(CastUtil.castToLong(id));
	    AemCouSbjChapter db = new AemCouSbjChapter();
        db = sbjChapterDao.getChapter(sbjChapterModel);
        sbjChapterModel = new SbjChapterModel(db);
        sbjChapterDao.deleteChapter(sbjChapterModel, db);
	}

    public SbjChapterModel getChapterById (Long Id) {
        SbjChapterModel chapterModel = new SbjChapterModel();
        chapterModel.setKey(Id);
        AemCouSbjChapter aemChapter =  sbjChapterDao.getChapter(chapterModel);
        
        List<ChapterFileModel> fileModels = new ArrayList<>();
        List<AemCsChapterFile> chapterFiles = sbjChapterDao.getChapterFiles(Id);
        for (AemCsChapterFile aemCsChapterFile : chapterFiles) {
			ChapterFileModel fileModel = new ChapterFileModel(aemCsChapterFile);
			fileModels.add(fileModel);
		}
        
        
        SbjChapterModel result = new SbjChapterModel(aemChapter);
        result.setChapterFileModels(fileModels);
        return result;
        
    }

    public SbjChapterModel getChapterBySubjectId (SbjChapterModel chapterModel) {
        AemCouSbjChapter aemChapter =  sbjChapterDao.getChapter(chapterModel);
        
        SbjChapterModel result = new SbjChapterModel(aemChapter, true);
        return result;
    }
    
    public Map getInfoCourse(Long id) {
    	Map result = sbjChapterDao.getInfoCourse(id);
    	return result;
    }
    
    public List<SbjChapterModel> searchByTag(ParamSearchModel model)throws Exception {
    	List<SbjChapterModel> result = new ArrayList<>();
    	 List<AemCouSbjChapter> searchByTag = sbjChapterDao.searchByTag(model);
    	for (AemCouSbjChapter aemCouSbjChapter : searchByTag) {
    		SbjChapterModel chapterModel = new SbjChapterModel(aemCouSbjChapter);
    		result.add(chapterModel);
    	}
    	return result;
    }
    
    

}
