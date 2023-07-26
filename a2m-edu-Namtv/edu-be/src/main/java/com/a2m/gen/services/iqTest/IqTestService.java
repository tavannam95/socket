package com.a2m.gen.services.iqTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.IqQuesDao;
import com.a2m.gen.dao.IqTestDao;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.AemTeacherEntity;
import com.a2m.gen.entities.edu.EamStudentInfo;
import com.a2m.gen.entities.edu.EamTeacherInfo;
import com.a2m.gen.entities.iqTest.AemIqTest;
import com.a2m.gen.entities.iqTest.AemIqTestMap;
import com.a2m.gen.entities.iqTest.AemNonUser;
import com.a2m.gen.entities.iqTest.IqTestAnswerEntity;
import com.a2m.gen.entities.iqTest.IqTestQuesEntity;
import com.a2m.gen.entities.iqTest.QuesItemAnswerResultEntity;
import com.a2m.gen.entities.iqTest.QuesItemResultEntity;
import com.a2m.gen.entities.iqTest.QuesStudentMapEntity;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.iqTest.IqTestAnswerModel;
import com.a2m.gen.models.iqTest.IqTestModel;
import com.a2m.gen.models.iqTest.IqTestQuesMapModel;
import com.a2m.gen.models.iqTest.IqTestQuesModel;
import com.a2m.gen.models.iqTest.NonUserModel;
import com.a2m.gen.models.iqTest.QuesItemResultModel;
import com.a2m.gen.models.iqTest.QuesStudentMapModel;
import com.a2m.gen.models.iqTest.SubmitQuesModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.common.TccoSTDService;
import com.a2m.gen.services.sys.Sys0103Service;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Service
public class IqTestService {
    @Autowired
    private CommonService commonService;

    @Autowired
    private IqTestDao iqTestDao;

    @Autowired
    private IqQuesDao iqQuesDao;

    @Autowired
    private TccoSTDService tccoSTDService;

    @Autowired
    private Sys0103Service sys0103Service;
    
    public List<IqTestModel> getIqTestList(IqTestModel search) throws Exception {
        List<IqTestModel> listTest = new ArrayList<IqTestModel>();
        List<AemIqTest> lst = iqTestDao.getIqTests(search);
        for (AemIqTest db : lst) {
            IqTestModel model = new IqTestModel(db);
            listTest.add(model);
        }
        return listTest;
    }

    public IqTestModel getIqTestById(Long Id) {
        List<IqTestQuesModel> listQues = new ArrayList<IqTestQuesModel>();
        IqTestModel IqTestModel = new IqTestModel();
        IqTestModel.setKey(Id);
        AemIqTest entity = iqTestDao.getIqTest(IqTestModel);
        IqTestModel result = new IqTestModel(entity, true);
        List<IqTestQuesMapModel> listIqTestQues = result.getListIqTestMap();
        for(IqTestQuesMapModel element : listIqTestQues) {
            IqTestQuesModel iqQues = element.getIqTestQuesModel();
            listQues.add(iqQues);
        }
        result.setListQues(listQues);
        
        return result;
    }

    
    @Transactional
    public Map<Object, Object> saveIqTest(IqTestModel model) throws Exception {
        Map<Object, Object> res = new HashMap<Object, Object>();
        res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        String userUid = commonService.getUserUid();
        model.setInsUid(userUid);
        AemIqTest db = new AemIqTest();
        if (!"".equals(CastUtil.castToString(model.getKey()))) {
            model.setUpdUid(userUid);

            iqTestDao.saveIqTest(model, db);
            if(model.getSaveForListQues() != true) {
                saveMap(model, db);
            }else {
                saveQues(model, db);
            }
        } else {
            AemIqTest iqTestEntity = iqTestDao.saveIqTest(model, db);
            if(model.getSaveForListQues() != true) {
                saveMap(model, iqTestEntity);
            }else {
                saveQues(model, iqTestEntity);
            }
        }

        res.put(CommonContants.KEY, db.getIqTestId());
        return res;
    }
    
    @Transactional
    public void saveMap(IqTestModel model, AemIqTest iqTestEntity) throws Exception {
        List<IqTestQuesEntity> listQues = iqTestDao.getListQuest(model);
        for (IqTestQuesEntity quesEntity : listQues) {
            AemIqTestMap mapEntity = new AemIqTestMap();
            iqTestDao.saveIqTestQues(quesEntity, iqTestEntity, mapEntity, model);
        }
    }
    
    @Transactional
    public void saveQues(IqTestModel model, AemIqTest iqTestEntity) throws Exception {
        List<IqTestQuesModel> listQues = new ArrayList<>();
            listQues = model.getListQues();
        for (IqTestQuesModel ques : listQues) {
            AemIqTestMap mapEntity = new AemIqTestMap();
            IqTestQuesEntity quesEntity = iqTestDao.getIqQuesById(ques);
            iqTestDao.saveIqTestQues(quesEntity, iqTestEntity, mapEntity, model);
        }
    }

    @Transactional
    public Map<Object, Object> saveUser(NonUserModel model) throws Exception {
        Map<Object, Object> res = new HashMap<Object, Object>();
        res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        AemNonUser db = new AemNonUser();
        iqTestDao.saveUser(model, db);
        DatabaseUtil.setCommonFields(db, model);
        res.put(CommonContants.KEY, db.getNonUserId());
        return res;
    }
    
    @Transactional
    public void deleteIqQuesMap(IqTestQuesModel model, IqTestQuesEntity quesEntity) throws Exception {
        List<IqTestAnswerModel> listAnswerModel = model.getAnswerDelList();
        String userUid = commonService.getUserUid();
        for (int i = 0; i < listAnswerModel.size(); i++) {
            IqTestAnswerModel answerModel = listAnswerModel.get(i);
            answerModel.setInsUid(userUid);
            Long idAnswer = answerModel.getKey();
            iqTestDao.deleteIqQuesMap(idAnswer);
            
        }
    }
    
    @Transactional
    public void delete(Long id) {
        IqTestModel IqTestQuesModel = getIqTestById(id);
        AemIqTest db = new AemIqTest();
        db = iqTestDao.deleteIqQues(IqTestQuesModel, db);
    }

    public Object getListStuSubtmited(HashMap<String, Object> parameter){
        return iqTestDao.getListStuSubtmited1(parameter);
    }
    
    public Long countStuSubtmited(HashMap<String, Object> parameter) {
        return iqTestDao.countStuSubtmited(parameter);
    }
    

    public Object getListQuestionStatistical(HashMap<String, Object> parameter) {
        return iqTestDao.getListQuestionStatistical(parameter);
    }

    
//    public List<IqTestModel> getListStuSubtmited (Long key) {
//        List result = new ArrayList<>();
//        IqTestModel iqTestModel = new IqTestModel();
//        iqTestModel.setKey(key);
//        AemIqTest aemIqTest =  iqTestDao.getIqTest(iqTestModel);       
//        List<QuesStudentMapEntity> userEntity = aemIqTest.getQuesStudentMapEntities();
//        for(QuesStudentMapEntity user : userEntity) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            Date insDate = user.getInsDt();
//            //String nonUserId = user.getNonUser();
//            TsstUser tsstUser = user.getTsstUser();
//            AemNonUser nonUser = user.getNonUser();
//            Long totalCorrectAnswer = user.getTotalCorrectAnswer();
//            Long totalNotchoose = user.getTotalNotChoose();
//            Long totalWrongAnswer = user.getTotalWrongAnswer();
//            Long total = totalCorrectAnswer + totalNotchoose + totalWrongAnswer;
//            String fullName = "";
//            Long nonUserId = null;
//            String userUid = "";
//            if(tsstUser != null) {
//                
//                String userType = tsstUser.getUserType();
//                //Long userInfoId = tsstUser.get
//                if(userType.equals("EMP")) {
//                    TsstUserInfo tsstUserInfo = tsstUser.getTsstUserInfo();
//                    fullName = tsstUserInfo.getFullName();
//                }
//                if(userType.equals("TEA")) {
//                    EamTeacherInfo tsstUserInfo = tsstUser.getEamTeacherInfo();
//                    fullName = tsstUserInfo.getFullName();
//                }
//                if(userType.equals("STU")) {
//                    EamStudentInfo tsstUserInfo = tsstUser.getEamStudentInfo();
//                    fullName = tsstUserInfo.getFullName();
//                }
//                userUid = tsstUser.getUserUid();
//                map.put("fullName", fullName);
//                map.put("userUid", userUid);
//                map.put("totalCorrectAnswer", totalCorrectAnswer);
//                map.put("total", total);
//                map.put("insDate", insDate);
//                result.add(map);
//            }
//            if(nonUser != null) {
//                nonUserId = nonUser.getNonUserId();
//                fullName = nonUser.getFullName();
//                map.put("nonUserId", nonUserId);
//                map.put("fullName", fullName);
//                map.put("totalCorrectAnswer", totalCorrectAnswer);
//                map.put("total", total);
//                map.put("insDate", insDate);
//                result.add(map);
//            }
//            
//        }
//        
//        return result;
//    }
    
    //@Transactional
    public Object getIqTestSubmit(Long iqTestId) throws Exception {
        List<IqTestQuesModel> listQues = new ArrayList<IqTestQuesModel>();
        Map<Object, Object> res = new HashMap<Object, Object>();
        res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        IqTestModel iqTestModel = new IqTestModel();
        iqTestModel.setKey(iqTestId);
        AemIqTest entity = iqTestDao.getIqTest(iqTestModel);
        List<QuesStudentMapEntity> quesStudentMapEntities = entity.getQuesStudentMapEntities();
//        quesStudentMapEntities.removeIf(map -> !map.getTsstUser().getUserUid().equals(userInfoId));
        IqTestModel result = new IqTestModel(entity, true);
        List<IqTestQuesMapModel> listIqTestQues = result.getListIqTestMap();
        for(IqTestQuesMapModel element : listIqTestQues) {
            IqTestQuesModel iqQues = element.getIqTestQuesModel();
            listQues.add(iqQues);
        }
        if(quesStudentMapEntities.size()==0) {
            result.setListQues(listQues);
            return result;
        }else {
            SubmitQuesModel response = new SubmitQuesModel(quesStudentMapEntities.get(quesStudentMapEntities.size()-1));
            List<QuesStudentMapModel> listUser = new ArrayList<QuesStudentMapModel>();
            listUser = result.getListUser();
            response.setIqTestTime(result.getIqTestTime());
            response.setListUser(listUser);
            response.setListQues(listQues);
            return response;
        }
       
    }
    
    @Transactional
    public Map<Object, Object> submit(SubmitQuesModel submitRequest) throws Exception {
        Map<Object, Object> res = new HashMap<Object, Object>();
        res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
        List<QuesItemResultModel> QuesItemResultModels = submitRequest.getListQuesResult();
        List<QuesItemResultEntity> listQuesItemResultEntity = new ArrayList<QuesItemResultEntity>();
        List<IqTestQuesModel> listQuesItem = submitRequest.getListQues();
        TsstUser user = null;
        String userUid = "";
        AemNonUser nonUserEntity = null;
        if(submitRequest.getNonUserId() != null) {
            nonUserEntity = iqTestDao.getEamNonUserEntity(submitRequest.getNonUserId());
        }else {
            userUid = commonService.getUserUid();
            user = sys0103Service.findByUserUid(userUid);
        }
        QuesStudentMapEntity QuesStudentMapEntity = new QuesStudentMapEntity();
        
        for (int i = 0; i < QuesItemResultModels.size(); i++) {
            QuesItemResultModel itemResultModel = QuesItemResultModels.get(i);
            if(itemResultModel.getQuesType().equals("09-01")) {
                QuesItemResultEntity itemResultEtity = new QuesItemResultEntity();
                itemResultEtity.setQuesItemStudentId(null);
                itemResultEtity.setQuesStudentMapEntity(QuesStudentMapEntity);
                itemResultEtity.setIqTestQuesEntity(iqTestDao.getIqQuesById(itemResultModel));
                itemResultEtity.setResult(itemResultModel.getResult());
                itemResultEtity.setStatus(true);
                itemResultEtity.setChooseAnswer(itemResultModel.getAnswerCorrectString());
                itemResultModel.setInsUid(userUid);
                DatabaseUtil.setCommonFields(itemResultEtity, itemResultModel);
                listQuesItemResultEntity.add(itemResultEtity);
            }       
        }
        
        submitQuesItemMulti(listQuesItem, QuesStudentMapEntity, listQuesItemResultEntity, userUid);
        
        QuesStudentMapEntity.setListQuesItemResultEntity(listQuesItemResultEntity);
        QuesStudentMapEntity.setIqTestEntity(iqTestDao.getIqTest(submitRequest));
        if(submitRequest.getNonUserId()== null) {
            QuesStudentMapEntity.setTsstUser(user);
            QuesStudentMapEntity.setNonUser(null);
        }else {
            QuesStudentMapEntity.setTsstUser(null);
            QuesStudentMapEntity.setNonUser(nonUserEntity);
        }
        QuesStudentMapEntity.setTotalCorrectAnswer(submitRequest.getTotalCorrectAnswer());
        QuesStudentMapEntity.setTotalWrongAnswer(submitRequest.getTotalWrongAnswer());
        QuesStudentMapEntity.setTotalNotChoose(submitRequest.getTotalNotChoose());
        QuesStudentMapEntity.setStatus(true);
        submitRequest.setInsUid(userUid);
        DatabaseUtil.setCommonFields(QuesStudentMapEntity, submitRequest);
        iqTestDao.submit(QuesStudentMapEntity);
        return res;
    }
    
    public void submitQuesItemMulti(List<IqTestQuesModel> listQuesItem, QuesStudentMapEntity quesStudentMapEntity,
            List<QuesItemResultEntity> listQuesItemResultEntity, String userUid) {
        for (int i = 0; i < listQuesItem.size(); i++) {
            IqTestQuesModel QuesItem = listQuesItem.get(i);
            if(QuesItem.getQuesType().equals("09-02")) {
                QuesItemResultEntity itemResultEtity = new QuesItemResultEntity();
                itemResultEtity.setQuesItemStudentId(null);
                itemResultEtity.setQuesStudentMapEntity(quesStudentMapEntity);
                itemResultEtity.setIqTestQuesEntity(iqTestDao.getIqQuesById(QuesItem));
                itemResultEtity.setResult(QuesItem.getResult());
                itemResultEtity.setStatus(true);
                itemResultEtity.setChooseAnswer(null);
                QuesItem.setInsUid(userUid);
                DatabaseUtil.setCommonFields(itemResultEtity, QuesItem);
                
                List<IqTestAnswerModel> listAnswer =  QuesItem.getAnswerList();
                List<QuesItemAnswerResultEntity> listAnswerEntity = new ArrayList<QuesItemAnswerResultEntity>();
                for(int j = 0 ; j<listAnswer.size(); j++) {
                    QuesItemAnswerResultEntity answerResultEntity = new QuesItemAnswerResultEntity();
                    answerResultEntity.setQuesItemAnswerResultId(null);
                    answerResultEntity.setQuesItemResultEntity(itemResultEtity);
                    
                    IqTestAnswerEntity answer = iqTestDao.getAnswerById(listAnswer.get(j).getKey());
                    answerResultEntity.setIqTestAnswerEntity(answer);
                    
                    answerResultEntity.setIsChoose(listAnswer.get(j).getIsChoose());
                    listAnswerEntity.add(answerResultEntity);
                }
                
                itemResultEtity.setListQuesItemAnswerResult(listAnswerEntity);
                listQuesItemResultEntity.add(itemResultEtity);
            }           
        }
    }
    
}
