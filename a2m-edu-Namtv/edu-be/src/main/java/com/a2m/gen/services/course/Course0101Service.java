package com.a2m.gen.services.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.LectureDao;
import com.a2m.gen.entities.edu.AemCouSbjChtrLecture;
import com.a2m.gen.entities.edu.AemCscLectureFile;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.LectureFileModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.tags.TagService;
import com.a2m.gen.utils.CastUtil;


@Service
public class Course0101Service {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private LectureDao lectureDao;
	 
	 @Autowired 
	 private TagService tagService;
	
	public List<LectureModel> getLectures(ParamSearchModel search) throws Exception {
		List<LectureModel> lectureModels = new ArrayList<LectureModel>();
		List<AemCouSbjChtrLecture> lst = lectureDao.getLectures(search);
		for(AemCouSbjChtrLecture db :lst) {
			List<AemCscLectureFile> aemCscLectureFiles = lectureDao.getLectureFiles(db.getLectureId());
			
			List<LectureFileModel> listFile = new ArrayList<LectureFileModel>();
			for (AemCscLectureFile aemCscLectureFile : aemCscLectureFiles) {
				LectureFileModel fileModel = new LectureFileModel(aemCscLectureFile);
				listFile.add(fileModel);
			}
			
			
			LectureModel model = new LectureModel(db, listFile);			
			lectureModels.add(model);
		}
		return lectureModels;
	}

	public Map<Object, Object> saveLecture(LectureModel lectureModel) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		
		AemCouSbjChtrLecture db = new AemCouSbjChtrLecture();
		if(!"".equals(CastUtil.castToString(lectureModel.getKey()))) {
			//update
			db = lectureDao.getLecture(lectureModel);			
			lectureModel.setUpdUid(userUid);
			
			//file
			List<LectureFileModel> ListLectureFileModel = lectureModel.getLectureFileModel();
			for (LectureFileModel lectureFileModel : ListLectureFileModel) {
				//insert file
				if(lectureFileModel.getCrud().equals("C")) {
					lectureDao.insertLectureFile(lectureFileModel, new AemCscLectureFile(), lectureModel.getKey());
				}
				//drop file
				if(lectureFileModel.getCrud().equals("D")) {
					//lectureDao.insertLectureFile(lectureFileModel, new AemCscLectureFile(), lectureModel.getKey());
					lectureDao.dropLectureFile(lectureFileModel, new AemCscLectureFile(), lectureModel.getKey());
				}
				
			}
			
			tagService.handleTag(lectureModel.getTags());
			db = lectureDao.saveLecture(lectureModel, db);
			res.put(CommonContants.KEY, db.getLectureId());
		}else {
			//insert
			lectureModel.setInsUid(userUid);
			tagService.handleTag(lectureModel.getTags());
			db =  lectureDao.saveLecture(lectureModel, db);
			
					
			//file
			List<LectureFileModel> ListLectureFileModel = lectureModel.getLectureFileModel();
			for (LectureFileModel lectureFileModel : ListLectureFileModel) {
				//insert file
				if(lectureFileModel.getCrud().equals("C")) {
					lectureModel = new LectureModel(db);
					lectureDao.insertLectureFile(lectureFileModel, new AemCscLectureFile(), lectureModel.getKey());
				}
			}
			
			res.put(CommonContants.KEY, db.getLectureId());
		}
		
		return res;
	}

	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		LectureModel lectureModel = new LectureModel();
		lectureModel.setKey(CastUtil.castToLong(id));
		AemCouSbjChtrLecture db = new AemCouSbjChtrLecture();
		db = lectureDao.getLecture(lectureModel);
		lectureModel = new LectureModel(db);
		lectureDao.deleteLecture(lectureModel, db);
	}

    public LectureModel getLectureById (Long Id) {
        LectureModel lectureModel = new LectureModel();
        lectureModel.setKey(Id);
        AemCouSbjChtrLecture aemLecture =  lectureDao.getLecture(lectureModel);
        
        List<AemCscLectureFile> aemCscLectureFiles = lectureDao.getLectureFiles(aemLecture.getLectureId());
		
		List<LectureFileModel> listFile = new ArrayList<LectureFileModel>();
		for (AemCscLectureFile aemCscLectureFile : aemCscLectureFiles) {
			LectureFileModel fileModel = new LectureFileModel(aemCscLectureFile);
			listFile.add(fileModel);
		}
        
        LectureModel result = new LectureModel(aemLecture, listFile);
        return result;
        
    }
    
    public List<LectureModel> searchByTag(ParamSearchModel model)throws Exception {
    	List<LectureModel> result = new ArrayList<>();
    	result = lectureDao.searchByTag(model);
    	return result;
    }
    
    public Long countLectureByTag(ParamSearchModel model) {
    	Long result  = lectureDao.countLectureByTag(model);
    	return result;
    	}
    
    public Map getInfoCourse(Long id) {
    	Map result = lectureDao.getInfoCourse(id);
    	return result;
    }

}
