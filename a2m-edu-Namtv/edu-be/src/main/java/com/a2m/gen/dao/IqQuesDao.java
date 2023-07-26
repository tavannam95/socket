package com.a2m.gen.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TccoStd;
import com.a2m.gen.entities.iqTest.IqTestAnswerEntity;
import com.a2m.gen.entities.iqTest.IqTestQuesEntity;
import com.a2m.gen.models.iqTest.IqTestAnswerModel;
import com.a2m.gen.models.iqTest.IqTestQuesModel;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Repository
public class IqQuesDao {
//	@Autowired
//	private CommonService commonService;

	@Autowired
	private EntityManager entityManager;
	
//	@Autowired
//    private TccoSTDService tccoSTDService;

	public List<IqTestQuesEntity> getIqQuestList(IqTestQuesModel args) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<IqTestQuesEntity> cq = cb.createQuery(IqTestQuesEntity.class);
		Root<IqTestQuesEntity> IqTest = cq.from(IqTestQuesEntity.class);

		cq.orderBy(cb.desc(IqTest.get("quesId")));
		cq.select(IqTest);
		
//        where start
		List<Predicate> predicates = new ArrayList<>();
//		 if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
//		 	predicates.add(cb.like(cb.lower(IqTest.get("quizNm")), "%" + args.getSearchText().toLowerCase() + "%"));
//           	 predicates.add(cb.equal(IqTest.get("lectureNm"), args.getSearchText()));
//		 }

		if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
			predicates.add(cb.equal(IqTest.get("status"), args.getSearchStatus()));
		}

		predicates.add(cb.equal(IqTest.get("deleteFlag"), false));
		cq.where(predicates.toArray(new Predicate[0]));
//        where end

		return entityManager.createQuery(cq)
//        		=== start paging
				.setMaxResults(CastUtil.castToInteger(args.getRows()))
				.setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
				.getResultList();
	}

	public Long countIqTQues(IqTestQuesModel args) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<IqTestQuesEntity> root = criteriaQuery.from(IqTestQuesEntity.class);
		criteriaQuery.select(cb.count(root));

//      where start
		List<Predicate> predicates = new ArrayList<>();
// 		if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
// 			predicates.add(cb.like(cb.lower(root.get("quizNm")), "%" + args.getSearchText().toLowerCase() + "%"));
// //          predicates.add(cb.equal(lecture.get("lectureNm"), args.getSearchText()));
// 		}

		if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
			predicates.add(cb.equal(root.get("status"), args.getSearchStatus()));
		}

		predicates.add(cb.equal(root.get("deleteFlag"), false));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result;
	}

    public IqTestQuesEntity getIqQuesById(IqTestQuesModel model) {
        return this.entityManager.find(IqTestQuesEntity.class, model.getKey());
    }

    public TccoStd getCommNmByCommCd(IqTestQuesModel model) {
        return this.entityManager.find(TccoStd.class, model.getQuesCategory());
    }
    
	public IqTestQuesEntity saveIqQues(IqTestQuesModel model, IqTestQuesEntity entity) {
		entity.setQuesId(model.getKey());
		entity.setQuesNm(model.getQuesNm());
		entity.setQuesContent(model.getQuesContent());
		entity.setQuesCategory(model.getQuesCategory());
		entity.setQuesType(model.getQuesType());
		entity.setQuesLevel(model.getLev());
		entity.setStatus(model.getStatus());

		DatabaseUtil.setCommonFields(entity, model); // change loginUserId when use
		if (model.getKey() != null) {
			entityManager.merge(entity);
		} else {
			entityManager.persist(entity);
		}
		return entity;
	}

	public IqTestAnswerEntity saveIqAnswer(IqTestAnswerModel model, IqTestQuesEntity db, IqTestAnswerEntity entity) {
	    //deleteAnswerByQuesId(db);
	    DatabaseUtil.setCommonFields(entity, model); // change loginUserId when use
	    entity.setAnswerId(model.getKey());
	    entity.setIqTestQues(db);
	    entity.setAnswerContent(model.getAnswerContent());
	    entity.setAnswerCorrect(model.getAnswerCorrect());
	    //entity.setDeleteFlag(model.getDeleteFlag());

		
		if (model.getKey() != null) {
			entityManager.merge(entity);
		} else {
			entityManager.persist(entity);
		}

		return entity;
	}

    public boolean deleteIqAnswer(Long id) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<IqTestAnswerEntity> cd = cb.createCriteriaDelete(IqTestAnswerEntity.class);
        Root<IqTestAnswerEntity> sample = cd.from(IqTestAnswerEntity.class);
        
        cd.where(cb.equal(sample.get("answerId"), id));
        this.entityManager.createQuery(cd).executeUpdate();
        return true;
    }
    
	public IqTestQuesEntity deleteIqQues(IqTestQuesModel model, IqTestQuesEntity entity) {

		DatabaseUtil.setCommonFields(entity, model);

		entity.setQuesId(model.getKey());
        entity.setQuesNm(model.getQuesNm());
        entity.setQuesContent(model.getQuesContent());
        entity.setQuesCategory(model.getQuesCategory());
        entity.setQuesType(model.getQuesType());
        entity.setQuesLevel(model.getLev());
        entity.setStatus(model.getStatus());


		entity.setDeleteFlag(true);
		entityManager.merge(entity);
		 return entity;
	}
    
}
