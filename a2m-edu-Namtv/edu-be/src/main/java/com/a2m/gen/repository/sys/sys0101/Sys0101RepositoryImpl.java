package com.a2m.gen.repository.sys.sys0101;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.dto.TsstMenuDto;
import com.a2m.gen.entities.TsstMenu;

@Repository
@Transactional
public class Sys0101RepositoryImpl implements Sys0101Repository{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<TsstMenu> getAllMenu() {
		Query query = em.createNativeQuery(
				"SELECT * FROM TSST_MENU ORDER BY ORD_NO, LEV ASC");
		List<TsstMenu> menuList = query.getResultList();
		return menuList;
	}

	@Override
	public String getMaxMenuId(String menuId) {
		Query query = em.createNativeQuery("SELECT MAX(tm.MENU_ID) FROM TSST_MENU tm WHERE :menuId is null OR tm.MENU_ID LIKE CONCAT(:menuId, '%')")
				.setParameter("menuId", menuId);			
		String result = (String) query.getSingleResult();
		return result;
	}

	@Override
	public Integer getMaxOrdNo() {
		Query query = em.createNativeQuery(
				"SELECT MAX(ORD_NO) FROM TSST_MENU");
		Integer result = (Integer) query.getSingleResult();
		return result;
	}

	@Override
	public int deleteTsstRoleMenu(String menuId) {
		Query query = em.createNativeQuery("DELETE FROM TSST_ROLE_MENU WHERE MENU_ID = :menuId").setParameter("menuId", menuId);
		return query.executeUpdate();
	}

//	@Override
//	public List<TsstMenu> getMenuByUser(Map<String, Object> args) {
//		Query query = em.createNativeQuery(
//					"SELECT DISTINCT T1.* FROM TSST_MENU T1 "
//					+"	LEFT JOIN TSST_ROLE_MENU T2 ON T1.MENU_ID = T2.MENU_ID "
//					+"	LEFT JOIN TSST_USER_ROLE T3 ON T2.ROLE_ID = T3.ROLE_ID "
//					+"WHERE  (:useYn is null OR T1.USE_YN = :useYn)"
//					+ "	AND (:userUid is null OR T3.USER_UID = :userUid ) "
//					+"	ORDER BY T1.ORD_NO ASC, T1.LEV ASC", TsstMenu.class)
//				.setParameter("userUid", args.get("userUid"))
//				.setParameter("useYn", args.get("useYn"));
//		return query.getResultList();
//	}
	
	@Override //thanh nv refactor
	public List<TsstMenu> getMenuByUser(Map<String, Object> args) {
		Query query = em.createNativeQuery(
					"SELECT DISTINCT T1.* FROM TSST_MENU T1 "
					+"	LEFT JOIN TSST_ROLE_MENU T2 ON T1.MENU_ID = T2.MENU_ID "
					+"	LEFT JOIN TSST_USER_ROLE T3 ON T2.ROLE_ID = T3.ROLE_ID "
					+"WHERE  T1.USE_YN = :useYn"
					+ "	AND T3.USER_UID = :userUid"
					+ " AND T2.READ_YN = 'Y'"
					+"	ORDER BY T1.ORD_NO ASC, T1.LEV ASC", TsstMenu.class)
				.setParameter("userUid", args.get("userUid"))
				.setParameter("useYn", args.get("useYn"));
		return query.getResultList();
	}

	@Override
	public int activeOrBlockMenu(Map<String, Object> args) {
		Query query = em.createNativeQuery("UPDATE UPDATE TSST_MENU SET USE_YN = :useYn WHERE MENU_ID = :menuId")
				.setParameter("useYn", args.get("useYn")).setParameter("menuId", args.get("menuId"));
		return query.executeUpdate();
	}

	@Override
	public List<TsstMenu> searchTsstMenu(Map<String, Object> args) {
		String sql = "SELECT * FROM TSST_MENU "
				+ "WHERE (:menuId is null OR MENU_ID = :menuId OR :menuId = '')"
				+ "		AND "
				+ "		("
				+ "			:menuNm is null "
				+ "			OR LOWER(MENU_NM) LIKE LOWER(CONCAT('%', :menuNm, '%')) "
				+ "			OR LOWER(MENU_NM_EN) LIKE LOWER(CONCAT('%', :menuNm, '%')) "
				+ "			OR LOWER(MENU_NM_VI) LIKE LOWER(CONCAT('%', :menuNm, '%')) "
				+ "		)"
				+ "		AND (:lev is null OR LEV = :lev OR :lev = '') "
				+ "		AND (:upMenuId is null OR UP_MENU_ID=:upMenuId OR :upMenuId = '') "
				+ "		AND (:useYn is null OR USE_YN = :useYn OR :useYn = '') "
				+ "		AND (:url is null OR URL = :url OR :url = '' ) "
				+ "		AND (:ordNo is null OR ORD_NO = :ordNo OR :ordNo = '') "
				+ "		AND (:menuType is null OR MENU_TYPE = :menuType OR :menuType ='' ) "
				+ "ORDER BY MENU_ID ASC, ORD_NO ASC,LEV ASC";
		Query query = em.createNativeQuery(sql, TsstMenu.class).setParameter("menuId", args.get("menuId"))
				.setParameter("menuNm", args.get("menuNm"))
				.setParameter("lev", args.get("lev"))
				.setParameter("upMenuId", args.get("upMenuId"))
				.setParameter("useYn", args.get("useYn"))
				.setParameter("url", args.get("url"))
				.setParameter("ordNo", args.get("ordNo"))
				.setParameter("menuType", args.get("menuType"));
		List<TsstMenu> result = query.getResultList();
		return result;
	}

	@Override
	public int updateTsstMenu(TsstMenuDto tsstMenu) {
		String sql = "UPDATE TSST_MENU "
				+ "SET MENU_NM = :menuNm, MENU_NM_EN = :menuNmEn, MENU_NM_VI = :menuNmVi, URL = :url, USE_YN = :useYn, ORD_NO = :ordNo "
				+ "WHERE MENU_ID = :menuId";
		Query query = em.createNativeQuery(sql).setParameter("menuNm", tsstMenu.getMenuNm())
				.setParameter("menuNmEn", tsstMenu.getMenuNmEn())
				.setParameter("menuNmVi", tsstMenu.getMenuNmVi())
				.setParameter("url", tsstMenu.getUrl())
				.setParameter("useYn", tsstMenu.getUseYn())
				.setParameter("ordNo", tsstMenu.getOrdNo())
				.setParameter("menuId", tsstMenu.getMenuId());
		return query.executeUpdate();
	}
	
}
