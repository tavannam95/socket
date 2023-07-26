package com.a2m.gen.dao;

import java.util.ArrayList;
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

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassAnnouncement;
import com.a2m.gen.entities.edu.AemClassAnnouncementLog;
import com.a2m.gen.models.edu.ClassAnnouncementLogModel;
import com.a2m.gen.models.edu.ClassAnnouncementModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;
import com.a2m.gen.utils.DatabaseUtil;

@Repository
public class ClassAnnouncementDao {
	@Autowired
	private CommonService commonService;

	@Autowired
	private ClassDao classDao;

	@Autowired
//    @PersistenceContext( unitName= A2mConst.JPA_UNIT_NAME_1)
	private EntityManager entityManager;

	public List<AemClassAnnouncement> getList(ClassAnnouncementModel args) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AemClassAnnouncement> cq = cb.createQuery(AemClassAnnouncement.class);
		Root<AemClassAnnouncement> root = cq.from(AemClassAnnouncement.class);
		cq.orderBy(cb.desc(root.get("classAnnoucementId")));
		cq.select(root);

//        where start
		List<Predicate> predicates = new ArrayList<>();
		if (args.getSearchText() != null && !"".equals(args.getSearchText())) {
			predicates
					.add(cb.like(cb.lower(root.get("title")), "%" + args.getSearchText().toLowerCase() + "%"));
		}
		if (args.getSearchStatus() != null && !"".equals(args.getSearchStatus())) {
			predicates.add(cb.equal(root.get("status"), args.getSearchStatus()));
		}
		if (args.getClassModel() != null && !"".equals(args.getClassModel())) {
			predicates.add(cb.equal(root.get("aemClassAnnou"), args.getClassModel().getKey()));
		}

		predicates.add(cb.equal(root.get("deleteFlag"), false));
		cq.where(predicates.toArray(new Predicate[0]));
//        where end

		if (args.getAll!=null) {
			return entityManager.createQuery(cq).getResultList();
		}
		return entityManager.createQuery(cq)
//        		=== start paging
				.setMaxResults(CastUtil.castToInteger(args.getRows()))
				.setFirstResult(CastUtil.castToInteger(args.getPage()) * CastUtil.castToInteger(args.getRows()))
//        		=== end paging
				.getResultList();
	}
	
	
	
	public Long count(ClassAnnouncementModel search) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<AemClassAnnouncement> root = criteriaQuery.from(AemClassAnnouncement.class);
		criteriaQuery.select(cb.count(root));

//      where start
		List<Predicate> predicates = new ArrayList<>();
		if (search.getSearchText() != null && !"".equals(search.getSearchText())) {
			predicates
					.add(cb.like(cb.lower(root.get("title")), "%" + search.getSearchText().toLowerCase() + "%"));
		}
		if (search.getSearchStatus() != null && !"".equals(search.getSearchStatus())) {
			predicates.add(cb.equal(root.get("status"), search.getSearchStatus()));
		}
		
		if (search.getClassModel() != null && !"".equals(search.getClassModel())) {
			predicates.add(cb.equal(root.get("aemClassAnnou"), search.getClassModel().getKey()));
		}
		
		predicates.add(cb.equal(root.get("deleteFlag"), false));
//      where end
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		Long result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result;
	}

	public AemClassAnnouncement getByModel(ClassAnnouncementModel model) {
		return this.entityManager.find(AemClassAnnouncement.class, model.getKey());
	}
	
	public AemClassAnnouncementLog getAemClassAnnouncementLog(ClassAnnouncementLogModel model) {
		return this.entityManager.find(AemClassAnnouncementLog.class, model.getKey());
	}

	public AemClassAnnouncement save(ClassAnnouncementModel model, AemClassAnnouncement db) {


		db.setClassAnnoucementId(model.getKey());
		db.setTitle(model.getTitle());
		db.setContent(model.getContent());
		db.setStartDate(model.getStartDate());
		db.setEndDate(model.getEndDate());
		ClassModel classModel = model.getClassModel();
		AemClass aemClass = classDao.getClassEntity(classModel);
		db.setAemClassAnnou(aemClass); 
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
	}
	
	@SuppressWarnings("unchecked")
	public List<AemClassAnnouncementLog> checkLog(Long classAnnoucementId, String userUid){
		List<AemClassAnnouncementLog> resultList = new ArrayList<>();

		String queryString = "SELECT l.* FROM EAM_CLASS_ANNOUNCEMENT_LOG l "
				+ "WHERE l.CLASS_ANNOUNCEMENT_ID = :classAnnoucementId "
				+ "AND l.USER_UID_LOG =:userUid ";

		Query query = this.entityManager.createNativeQuery(queryString,AemClassAnnouncementLog.class)
				
				.setParameter("userUid", userUid)
				.setParameter("classAnnoucementId", classAnnoucementId);
		
		resultList = query.getResultList();
		return resultList;
	}
	
	public AemClassAnnouncementLog log(ClassAnnouncementLogModel model, AemClassAnnouncementLog db) {
		
		db.setAnnoucementLogId(model.getKey());
		db.setClassAnnoucementId(model.getClassAnnoucementId());
		db.setUserUidLog(model.getUserUidLog()); 
		DatabaseUtil.setCommonFields(db, model); // change loginUserId when use
		entityManager.persist(db);
		return db;
	}

	public boolean delete(Long id) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaDelete<AemClassAnnouncement> cd = cb.createCriteriaDelete(AemClassAnnouncement.class);
		Root<AemClassAnnouncement> sample = cd.from(AemClassAnnouncement.class);

		cd.where(cb.equal(sample.get("classAnnoucementId"), id));
		this.entityManager.createQuery(cd).executeUpdate();
		return true;
	}

//	public List<AemClassAnnouncement> getListByClassId(String classId) throws Exception {
//
//		
//
//		List<AemClassAnnouncement> resultList = new ArrayList<>();
//
//		String queryString = "";
//
//
//
//		Query query = this.entityManager.createNativeQuery(queryString,AemClass.class).setParameter("userUid", userUid).setParameter("timeStamp", timeStamp);
////		List<AemClass> resultList = query.getResultList();
//
//
//		return resultList;
//	}

}
