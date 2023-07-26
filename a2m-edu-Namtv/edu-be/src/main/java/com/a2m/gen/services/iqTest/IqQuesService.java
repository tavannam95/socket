package com.a2m.gen.services.iqTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.IqQuesDao;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.entities.edu.QuizItemResultEntity;
import com.a2m.gen.entities.edu.QuizStudentMapEntity;
import com.a2m.gen.entities.iqTest.IqTestAnswerEntity;
import com.a2m.gen.entities.iqTest.IqTestQuesEntity;
import com.a2m.gen.entities.iqTest.QuesStudentMapEntity;
import com.a2m.gen.models.course.QuizItemModel;
import com.a2m.gen.models.course.QuizItemResultModel;
import com.a2m.gen.models.course.QuizModel;
import com.a2m.gen.models.course.SubmitQuizModel;
import com.a2m.gen.models.iqTest.IqTestAnswerModel;
import com.a2m.gen.models.iqTest.IqTestQuesModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.TccoSTDService;
import com.a2m.gen.services.sys.Sys0103Service;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Service
public class IqQuesService {
	@Autowired
	private CommonService commonService;

	@Autowired
	private IqQuesDao iqTestDao;
	
	@Autowired
    private TccoSTDService tccoSTDService;

    @Autowired
    private Sys0103Service sys0103Service;
    
	public List<IqTestQuesModel> getIqQuesList(IqTestQuesModel search) throws Exception {
		List<IqTestQuesModel> listQues = new ArrayList<IqTestQuesModel>();
		List<IqTestQuesEntity> lst = iqTestDao.getIqQuestList(search);
		for (IqTestQuesEntity db : lst) {
			IqTestQuesModel model = new IqTestQuesModel(db);
			listQues.add(model);
		}
		return listQues;
	}

	public IqTestQuesModel getIqQuesById(Long Id) {
		IqTestQuesModel IqTestQuesModel = new IqTestQuesModel();
		IqTestQuesModel.setKey(Id);
		IqTestQuesEntity entity = iqTestDao.getIqQuesById(IqTestQuesModel);
		IqTestQuesModel result = new IqTestQuesModel(entity);

		return result;
	}
	
	@Transactional
	public Map<Object, Object> saveIqQues(IqTestQuesModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		model.setInsUid(userUid);
		IqTestQuesEntity db = new IqTestQuesEntity();
		if (!"".equals(CastUtil.castToString(model.getKey()))) {
			// update
		    model.setUpdUid(userUid);
			iqTestDao.saveIqQues(model, db);
			saveIqAnswer(model, db);
			deleteIqAnswer(model, db);
		} else {
			// insert
			//model.setInsUid(userUid);
			db = iqTestDao.saveIqQues(model, db);
			saveIqAnswer(model, db);
		}
		
		res.put(CommonContants.KEY, db.getQuesId());
		return res;
	}

    public void saveIqAnswer(IqTestQuesModel model, IqTestQuesEntity quesEntity) throws Exception {
        List<IqTestAnswerModel> listAnswerModel = model.getAnswerList();
        String userUid = commonService.getUserUid();
        for (int i = 0; i < listAnswerModel.size(); i++) {
            IqTestAnswerEntity db = new IqTestAnswerEntity();
            IqTestAnswerModel answerModel = listAnswerModel.get(i);
            answerModel.setInsUid(userUid);
            iqTestDao.saveIqAnswer(answerModel, quesEntity, db);
        }
	}
	
    public void deleteIqAnswer(IqTestQuesModel model, IqTestQuesEntity quesEntity) throws Exception {
        List<IqTestAnswerModel> listAnswerModel = model.getAnswerDelList();
        String userUid = commonService.getUserUid();
        for (int i = 0; i < listAnswerModel.size(); i++) {
            IqTestAnswerModel answerModel = listAnswerModel.get(i);
            answerModel.setInsUid(userUid);
            Long idAnswer = answerModel.getKey();
            iqTestDao.deleteIqAnswer(idAnswer);
            
        }
    }
    
	@Transactional
	public void delete(String id) {
		IqTestQuesModel IqTestQuesModel = getIqQuesById(CastUtil.castToLong(id));
		IqTestQuesEntity db = new IqTestQuesEntity();
		db = iqTestDao.deleteIqQues(IqTestQuesModel, db);
	}

}
