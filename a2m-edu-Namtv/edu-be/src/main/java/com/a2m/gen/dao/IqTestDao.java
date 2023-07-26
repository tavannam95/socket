package com.a2m.gen.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.a2m.gen.entities.iqTest.AemIqTest;
import com.a2m.gen.entities.iqTest.AemIqTestMap;
import com.a2m.gen.entities.iqTest.AemNonUser;
import com.a2m.gen.entities.iqTest.IqTestAnswerEntity;
import com.a2m.gen.entities.iqTest.IqTestQuesEntity;
import com.a2m.gen.entities.iqTest.QuesStudentMapEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.iqTest.IqTestModel;
import com.a2m.gen.models.iqTest.IqTestQuesModel;
import com.a2m.gen.models.iqTest.NonUserModel;
import com.a2m.gen.models.iqTest.NumOfCategory;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.sys.Sys0103Service;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;


@Repository
public class IqTestDao {
    @Autowired 
    private CommonService commonService;
	 
    @Autowired
    private Sys0103Service sys0103Service;
	    
	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
    private EntityManager entityManager;
 
    public List<AemIqTest> getIqTests(ParamBaseModel args) {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AemIqTest> cq = cb.createQuery(AemIqTest.class);
        Root<AemIqTest> iqTest = cq.from(AemIqTest.class);  
        cq.orderBy(cb.desc(iqTest.get("iqTestId")));
        cq.select(iqTest);
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
           predicates.add(cb.like(cb.lower(iqTest.get("iqtest")), "%" + args.getSearchText().toLowerCase() + "%"));
        }
        if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
	        predicates.add(cb.equal(iqTest.get("status"), args.getSearchStatus()));
        }
        predicates.add(cb.equal(iqTest.get("deleteFlag"), false));
        cq.where(predicates.toArray(new Predicate[0]));
        
        if( args.getGetAll() != null && args.getGetAll() ) {
        	return entityManager.createQuery(cq)
           		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                   .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
                    .getResultList();
        }
        
        return entityManager.createQuery(cq)
        		.setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
                .getResultList();
    }
    
    public Long countAllIqTest(ParamBaseModel search){ 	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<AemIqTest> root = criteriaQuery.from(AemIqTest.class);
        criteriaQuery.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
            predicates.add(cb.like(cb.lower(root.get("iqtest")), "%" + search.getSearchText().toLowerCase() + "%"));
        }
        if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
	        predicates.add(cb.equal(root.get("status"), search.getSearchStatus()));
        }
        predicates.add(cb.equal(root.get("deleteFlag"), false));
        
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
    	return result;
    }
    
    public AemIqTest getIqTest(IqTestModel model) {
    	return this.entityManager.find(AemIqTest.class, model.getKey());
    }
  
    public IqTestQuesEntity getIqQuesById(IqTestQuesModel model) {
        return this.entityManager.find(IqTestQuesEntity.class, model.getKey());
    }
    
    //@Transactional
    public AemIqTest saveIqTest(IqTestModel model, AemIqTest db) {
        db.setIqTestId(model.getKey());
    	db.setIqTestNm(model.getIqTestNm());
    	db.setIqTestTime(model.getIqTestTime());
    	db.setStatus(model.getStatus());
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		if (model.getKey() != null) {
            entityManager.merge(db);
        } else {
            entityManager.persist(db);
        }
		return db;
    }

    //@Transactional
    public AemIqTestMap saveIqTestQues(IqTestQuesEntity questEntity, AemIqTest iqTestEntity, AemIqTestMap db, IqTestModel model) {
        db.setAemIqTest(iqTestEntity);
        db.setiqTestQuesEntity(questEntity);
        DatabaseUtil.setCommonFields(db, model);
        if (model.getKey() != null) {
            entityManager.merge(db);
        } else {
            entityManager.persist(db);
        }
        return db;
    }
    
    public List<IqTestQuesEntity> getListQuest(IqTestModel model) throws Exception{
        List<IqTestQuesEntity> list = new ArrayList<>();
       // List list = new ArrayList<>();
        List<NumOfCategory> listcategory = model.getListNumOfCategory();
        for (NumOfCategory categorys : listcategory) {
            List<IqTestQuesEntity> listTmp = new ArrayList<>();
            String category = categorys.getCategory();
            int num = Integer.parseInt(categorys.getNum());
            ParamBaseModel args = new ParamBaseModel();
            args.setRows(num);
            args.setSearchText(category);
            listTmp = queryQues(args);
            list.addAll(listTmp);
        }
        return list;
    }
    
    public List<IqTestQuesEntity> queryQues(ParamBaseModel args) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<IqTestQuesEntity> cq = cb.createQuery(IqTestQuesEntity.class);
        Root<IqTestQuesEntity> room = cq.from(IqTestQuesEntity.class);
        cq.orderBy(cb.desc(cb.function("RAND", null)));
        cq.select(room);
        
        
//        where start
        List<Predicate> predicates = new ArrayList<>();
        if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
            predicates.add(cb.equal(room.get("quesCategory"), args.getSearchText()));
        }
        cq.where(predicates.toArray(new Predicate[0]));
//        where end
        
        return entityManager.createQuery(cq)
                .setMaxResults(CastUtil.castToInteger(args.getRows()))
                .setFirstResult(0)
                .getResultList();
    }

    public boolean deleteIqQuesMap(Long id) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<AemIqTestMap> cd = cb.createCriteriaDelete(AemIqTestMap.class);
        Root<AemIqTestMap> sample = cd.from(AemIqTestMap.class);
        cd.where(cb.equal(sample.get("tableId"), id));
        this.entityManager.createQuery(cd).executeUpdate();
        return true;
    }
    
    public AemIqTest deleteIqQues(IqTestModel model, AemIqTest db) {
        DatabaseUtil.setCommonFields(db, model);
        db.setIqTestId(model.getKey());
        db.setIqTestNm(model.getIqTestNm());
        db.setIqTestTime(model.getIqTestTime());
        db.setStatus(model.getStatus());
        db.setDeleteFlag(true);
        
        entityManager.merge(db);
        return db;
    }

    public IqTestAnswerEntity getAnswerById(Long key) {
        return this.entityManager.find(IqTestAnswerEntity.class, key);
    }

    public AemNonUser getEamNonUserEntity(Long key) {
        return this.entityManager.find(AemNonUser.class, key);
    }

    public QuesStudentMapEntity submit(QuesStudentMapEntity quesStudentMapEntity) {
        return this.entityManager.merge(quesStudentMapEntity);
    }

    public AemNonUser saveUser(NonUserModel model, AemNonUser entity) {
        entity.setNonUserId(model.getKey());
        entity.setFullName(model.getFullName());
        entity.setEmail(model.getEmail());
        entity.setPhone(model.getPhone());
        entity.setStatus(true);
        entity.setInsDt(model.getInsDate());
        entity.setDeleteFlag(false);
        if (model.getKey() != null) {
            entityManager.merge(entity);
        } else {
            entityManager.persist(entity);
        }
        return entity;
    }
    

    public Object getListStuSubtmited1(HashMap<String, Object> parameter){
        List result = new ArrayList<>();
        
        Query query  = this.entityManager.createNativeQuery(
                "SELECT " 
                + "map.USER_UID" 
                + ",map.NON_USER_ID"
                + ",tsst.USER_TYPE"
                + ",stu.FULL_NAME as stuName"
                + ",tea.FULL_NAME as teaName"
                + ",emp.FULL_NAME as empName"
                + ",non.FULL_NAME as nonName"
                + ",map.IQTEST_ID" 
                + ",map.INS_DT" 
                + ",map.TOTAL_CORRECT_ANSWER as correct"
                + ",map.TOTAL_WRONG_ANSWER"
                + ",map.TOTAL_NOT_CHOOSE "
                
                + "FROM EAM_QUES_STUDENT_MAP map "
                + "LEFT JOIN TSST_USER tsst ON tsst.USER_UID = map.USER_UID "
                + "LEFT JOIN EAM_STUDENT_INFO stu ON stu.STUDENT_INFO_ID = tsst.USER_INFO_ID "
                + "LEFT JOIN EAM_TEACHER_INFO tea ON tea.TEACHER_INFO_ID = tsst.USER_INFO_ID "
                + "LEFT JOIN TSST_USER_INFO emp ON emp.USER_INFO_ID = tsst.USER_INFO_ID "
                + "LEFT JOIN EAM_QUES_NON_USER non ON non.NON_USER_ID = map.NON_USER_ID "
                + "WHERE IQTEST_ID = :iqtestId "
//                + "AND UPPER(stu.FULL_NAME) like CONCAT('%',UPPER(:fullName),'%') "      
//                + "UPPER(tea.FULL_NAME) like CONCAT('%',UPPER(:fullName),'%') "    
//                + "UPPER(tea.FULL_NAME) like CONCAT('%',UPPER(:fullName),'%') "    
//                + "UPPER(non.FULL_NAME) like CONCAT('%',UPPER(:fullName),'%') "    
                + "ORDER BY correct DESC " 
              + "LIMIT " + parameter.get("rows") + " "
              + "OFFSET " + (Integer.valueOf((String) parameter.get("rows")) * Integer.valueOf((String) parameter.get("page")))
             
                ).setParameter("iqtestId", parameter.get("iqtestId"));
//                .setParameter("fullName", parameter.get("searchText"));
        
        List list = new ArrayList<>();
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        for (Object object : list) {
            Object[] objects = (Object[]) object;
            String userUid = null;
            BigInteger nonUserId = null;
            if(objects[0] != null) {
                userUid = (String) objects[0];
            }
            if(objects[1] != null) {
                nonUserId = (BigInteger) objects[1];
            }
            String userType = (String) objects[2];
            String nameStu = (String) objects[3];
            String nameTea = (String) objects[4];
            String nameEmp = (String) objects[5];
            String nameNon = (String) objects[6];
            BigInteger iqTestId = (BigInteger) objects[7];
            Date insDate = (Date) objects[8];
            BigInteger totalCorrect = (BigInteger) objects[9];
            BigInteger totalWrong = (BigInteger) objects[10];
            BigInteger totalNotChoose = (BigInteger) objects[11];
            
            
            HashMap<String, Object> map = new HashMap<String, Object>();
            
            map.put("userType", userType);
            if(userType != null) {
                map.put("userId", userUid);
                if(userType.equals("STU")) {
                    map.put("fullName", nameStu);
                }else if(userType.equals("TEA")) {
                    map.put("fullName", nameTea);
                }else{
                    map.put("fullName", nameEmp);
                }
            }else {
                map.put("userId", nonUserId);
                map.put("fullName", nameNon);
            }
            map.put("iqTestId", iqTestId);
            map.put("insDate", insDate);
            map.put("totalCorrect", totalCorrect.longValue());
            map.put("totalWrong", totalWrong.longValue());      
            map.put("totalNotChoose", totalNotChoose.longValue());      
            result.add(map);
        }
        
        
        return result;
    }
    
    public Long countStuSubtmited(HashMap<String, Object> parameter) {
        String searchByClass = "";
        String getInfo = "";
        
        if( CastUtil.castToString(parameter.get("classIds")).length() >0 ) {
            searchByClass = "AND CLASS_ID IN (" +CastUtil.castToString(parameter.get("classIds"))+ ") ";
        }
        
        Query query  = this.entityManager.createNativeQuery(
                "SELECT COUNT(*) "      //0 
                + "FROM EAM_QUES_STUDENT_MAP map "
                + "LEFT JOIN TSST_USER tsst ON tsst.USER_UID = map.USER_UID "
                + "LEFT JOIN EAM_STUDENT_INFO stu ON stu.STUDENT_INFO_ID = tsst.USER_INFO_ID "
                + "LEFT JOIN EAM_TEACHER_INFO tea ON tea.TEACHER_INFO_ID = tsst.USER_INFO_ID "
                + "LEFT JOIN TSST_USER_INFO emp ON emp.USER_INFO_ID = tsst.USER_INFO_ID "
                + "LEFT JOIN EAM_QUES_NON_USER non ON non.NON_USER_ID = map.NON_USER_ID "
                + "WHERE IQTEST_ID = :iqTestId "
//                + "AND UPPER(stu.FULL_NAME) like CONCAT('%',UPPER(:fullName),'%') "               
                + "ORDER BY map.INS_DT DESC "

                ).setParameter("iqTestId", parameter.get("iqtestId"));
//                .setParameter("fullName", parameter.get("fullName"));
        
        Object singleResult = new ArrayList<>();
        try {
            singleResult = query.getSingleResult();
            return ((BigInteger) singleResult) .longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return Long.valueOf(0);
    }
    
    
    public List getListStuSubtmited(HashMap<String, Object> parameter) {
        List result = new ArrayList<>();
        String iqtestId = CastUtil.castToString(parameter.get("iqtestId"));
        String fullNm = CastUtil.castToString(parameter.get("fullname"));
        
        Query query  = this.entityManager.createNativeQuery(
                        "SELECT * FROM EAM_QUES_STUDENT_MAP map "
                    +" WHERE map.IQTEST_ID = :iqtestId "
                        )
                .setParameter("iqtestId", CastUtil.castToString(parameter.get("iqtestId")));
        
        List list = new ArrayList<>();
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        for (Object object : list) {
            Object[] objects = (Object[]) object;
            BigInteger  quesStudentId = (BigInteger) objects[0];
            BigInteger  iqTestId = (BigInteger) objects[1];
            BigInteger userUid = (BigInteger) objects[2];
            BigInteger  nonUserId = (BigInteger) objects[3];
            BigInteger totalCorrect = (BigInteger) objects[4];
            Date insertDate = (Date) objects[9];
            HashMap<String, Object> map = new HashMap<String, Object>();
            
            map.put("quesStudentId", quesStudentId);
            map.put("iqTestId", iqTestId);
            map.put("userUid", userUid);
            map.put("nonUserId", nonUserId);
            map.put("insertDate", insertDate);
            map.put("totalCorrect", totalCorrect);          
            result.add(map);
        }
        
        
        return result;
    }
    
    public Object getListQuestionStatistical(HashMap<String, Object> parameter) {
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        Query query  = this.entityManager.createNativeQuery(
                "SELECT "
                + "coalesce(sum(r.RESULT = 'correct'), 0) as correct " //0
                + ",coalesce(sum(r.RESULT != ''), 0) as total " //1
                + ",q.QUES_CONTENT as content " //2
                
                + "FROM EAM_QUES_ITEM_RESULT r "
                + "LEFT JOIN EAM_IQ_TEST_MAP e ON r.QUES_ID = e.QUES_ID "
                + "LEFT JOIN EAM_IQ_QUES q ON q.QUES_ID = e.QUES_ID "
                
                + "WHERE e.IQTEST_ID = :iqTestId "
                
                + "GROUP BY r.QUES_ID "
                + "ORDER BY r.QUES_ID "
                
//paging                + "LIMIT " + parameter.get("rows") + " "
//              + "OFFSET " + (Integer.valueOf((String) parameter.get("rows")) * Integer.valueOf((String) parameter.get("page")))           
             
                ).setParameter("iqTestId", parameter.get("iqTestId"));
        
        List list = new ArrayList<>();
        try {
            list = query.getResultList();
            for (Object object : list) {
                Object[] objects = (Object[]) object;
                BigDecimal correct = (BigDecimal) objects[0];
                BigDecimal total = (BigDecimal) objects[1];
                String content = (String) objects[2];
                
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("correct", correct.longValue());
                map.put("total", total.longValue());
                map.put("content", content);
                
                result.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public Long countQuestionStatistical(HashMap<String, Object> parameter) {       
        Query query  = this.entityManager.createNativeQuery(
                "SELECT "
                + "COUNT(*) as count " //0
                
                + "FROM EAM_IQ_TEST_MAP r "
                
                + "WHERE r.IQTEST_ID = :iqTestId "      
             
                ).setParameter("iqTestId", parameter.get("iqTestId"));
        
        Object singleResult = new ArrayList<>();
        try {
            singleResult = query.getSingleResult();
            return ((BigInteger) singleResult) .longValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return Long.valueOf(0);
    }
    
}
