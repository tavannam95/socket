package com.a2m.gen.repository.gen.gen0601;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.a2m.gen.dto.LicenseMgtDto;

@Repository
public class Gen0601Repository0601Impl implements Gen0601Repository{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<LicenseMgtDto> search(Map<String, Object> params) {
		String sql = "SELECT T1.LICENSE_ID as licenseId, "
				+ "		T1.CLIENT_ID as clientId, "
				+ "		T1.EXPIRED_DT as expiredDt,"
				+ "		T1.INS_DT as insDt,"
				+ "		T1.START_DT as startDt,"
				+ "		T1.TOKEN as token,"
				+ "		T1.UPDATE_DT as updateDt,"
				+ "		T1.STATUS as status,"
				+ "		T1.PRICE as price,"
				+ "		T3.PROJECT_ID as projectId,"
				+ "		T3.PROJECT_NAME as projectName"
				+ "	FROM GEN_LICENSE_MGT T1 "
				+ "		INNER JOIN TSST_USER T2 ON T1.CLIENT_ID = T2.USER_UID "
				+ "		INNER JOIN GEN_PROJECT_MGT T3 ON T1.PROJECT_ID = T3.PROJECT_ID"
				+ "	WHERE T2.USER_UID = :userUid"
				+ "		AND (:projectName is null OR LOWER(T3.PROJECT_NAME) LIKE LOWER(CONCAT('%', :projectName, '%')) ) "
				+ "		AND (:status is null OR T1.STATUS = :status) "
				+ "		AND (:fromDate is null OR DATE_FORMAT(T1.START_DT, '%Y-%m-%d')>= DATE_FORMAT(:fromDate, '%Y-%m-%d') )"
				+ " 	AND (:toDate is null OR DATE_FORMAT(:toDate, '%Y-%m-%d')>= DATE_FORMAT(T1.START_DT, '%Y-%m-%d') )"
				+ " LIMIT :offset, :size";
		Query query = em.createNativeQuery(sql, LicenseMgtDto.class).setParameter("userUid", params.get("userUid"))
				.setParameter("projectName", params.get("projectName"))
				.setParameter("status", params.get("status"))
				.setParameter("fromDate", params.get("fromDate"))
				.setParameter("toDate", params.get("toDate"))
				.setParameter("offset", Integer.parseInt(params.get("offset").toString()))
				.setParameter("size", Integer.parseInt(params.get("size").toString()));
		List<LicenseMgtDto> result = query.getResultList();
		return result;
	}

	@Override
	public int count(Map<String, Object> params) {
		String sql = "SELECT COUNT(*) "
				+ "	FROM GEN_LICENSE_MGT T1 "
				+ "		INNER JOIN TSST_USER T2 ON T1.CLIENT_ID = T2.USER_UID "
				+ "		INNER JOIN GEN_PROJECT_MGT T3 ON T1.PROJECT_ID = T3.PROJECT_ID"
				+ "	WHERE T2.USER_UID = :userUid"
				+ "		AND (:projectName is null OR LOWER(T3.PROJECT_NAME) LIKE LOWER(CONCAT('%', :projectName, '%')) ) "
				+ "		AND (:status is null OR T1.STATUS = :status) "
				+ "		AND (:fromDate is null OR DATE_FORMAT(T1.START_DT, '%Y-%m-%d')>= DATE_FORMAT(:fromDate, '%Y-%m-%d') )"
				+ " 	AND (:toDate is null OR DATE_FORMAT(:toDate, '%Y-%m-%d')>= DATE_FORMAT(T1.START_DT, '%Y-%m-%d') )";
		Query query = em.createNativeQuery(sql).setParameter("userUid", params.get("userUid"))
				.setParameter("projectName", params.get("projectName"))
				.setParameter("status", params.get("status"))
				.setParameter("fromDate", params.get("fromDate"))
				.setParameter("toDate", params.get("toDate"));
		int value = ((Number) query.getSingleResult()).intValue();
		return value;
	}

}
