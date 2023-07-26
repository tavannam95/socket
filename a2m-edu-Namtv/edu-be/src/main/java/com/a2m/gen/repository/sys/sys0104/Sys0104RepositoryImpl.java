package com.a2m.gen.repository.sys.sys0104;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TccoStd;

@Repository
public class Sys0104RepositoryImpl implements Sys0104Repository{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<TccoStd> search(Map<String, Object> params) {
		String sql = "SELECT * FROM TCCO_STD "
				+ "WHERE (:commCd is null OR COMM_CD = :commCd OR :commCd = '')"
				+ "		AND "
				+ "		("
				+ "			:commNm is null "
				+ "			OR LOWER(COMM_NM) LIKE LOWER(CONCAT('%', :commNm, '%')) "
				+ "			OR LOWER(COMM_NM_EN) LIKE LOWER(CONCAT('%', :commNm, '%')) "
				+ "			OR LOWER(COMM_NM_VI) LIKE LOWER(CONCAT('%', :commNm, '%')) "
				+ "		)"
				+ "		AND (:lev is null OR LEV = :lev OR :lev = '') "
				+ "		AND (:upCommCd is null OR UP_COMM_CD =:upCommCd OR :upCommCd = '') "
				+ "		AND (:useYn is null OR USE_YN = :useYn OR :useYn = '') "
				+ "ORDER BY COMM_CD ASC, LEV ASC";
		Query query = em.createNativeQuery(sql, TccoStd.class).setParameter("commCd", params.get("commCd"))
				.setParameter("commNm", params.get("commNm"))
				.setParameter("lev", params.get("lev"))
				.setParameter("upCommCd", params.get("upCommCd"))
				.setParameter("useYn", params.get("useYn"));
		List<TccoStd> result = query.getResultList();
		return result;
	}

}
