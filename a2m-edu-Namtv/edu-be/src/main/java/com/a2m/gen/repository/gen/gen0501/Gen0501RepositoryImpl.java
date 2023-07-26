package com.a2m.gen.repository.gen.gen0501;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.ProjectMgt;

@Repository
public class Gen0501RepositoryImpl implements Gen0501Repository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<ProjectMgt> search(Map<String, Object> search) {
		String sql = "SELECT * FROM GEN_PROJECT_MGT " + "	WHERE (:createdBy is null OR CREATED_BY = :createdBy)"
				+ "		AND (:projectName is null OR LOWER(PROJECT_NAME) LIKE LOWER(CONCAT('%', :projectName, '%')) ) "
				+ "		AND (:status is null OR STATUS = :status) "
				+ "		AND (:fromDate is null OR DATE_FORMAT(CREATED_DATE, '%Y-%m-%d')>= DATE_FORMAT(:fromDate, '%Y-%m-%d') )"
				+ " 	AND (:toDate is null OR DATE_FORMAT(:toDate, '%Y-%m-%d')>= DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') )"
				+ " LIMIT :offset, :size";
		Query query = em.createNativeQuery(sql, ProjectMgt.class).setParameter("createdBy", search.get("createdBy"))
				.setParameter("projectName", search.get("projectName")).setParameter("status", search.get("status"))
				.setParameter("fromDate", search.get("fromDate")).setParameter("toDate", search.get("toDate"))
				.setParameter("offset", Integer.parseInt(search.get("offset").toString()))
				.setParameter("size", Integer.parseInt(search.get("size").toString()));
//		query = this.setParamsToQuery(search, query);
		return query.getResultList();
	}

	private Query setParamsToQuery(Map<String, Object> params, Query query) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query;
	}

	@Override
	public int count(Map<String, Object> search) {
		String sql = "SELECT COUNT(*) FROM GEN_PROJECT_MGT "
				+ "	WHERE (:createdBy is null OR CREATED_BY = :createdBy)"
				+ "		AND (:projectName is null OR LOWER(PROJECT_NAME) LIKE LOWER(CONCAT('%', :projectName, '%')) ) "
				+ "		AND (:status is null OR STATUS = :status) "
				+ "		AND (:fromDate is null OR DATE_FORMAT(CREATED_DATE, '%Y-%m-%d')>= DATE_FORMAT(:fromDate, '%Y-%m-%d') )"
				+ " 	AND (:toDate is null OR DATE_FORMAT(:toDate, '%Y-%m-%d')>= DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') )";
		Query query = em.createNativeQuery(sql).setParameter("createdBy", search.get("createdBy"))
				.setParameter("projectName", search.get("projectName")).setParameter("status", search.get("status"))
				.setParameter("fromDate", search.get("fromDate")).setParameter("toDate", search.get("toDate"));
//		query = this.setParamsToQuery(search, query);
		int result = ((Number) query.getSingleResult()).intValue();
		return result;
	}
}
