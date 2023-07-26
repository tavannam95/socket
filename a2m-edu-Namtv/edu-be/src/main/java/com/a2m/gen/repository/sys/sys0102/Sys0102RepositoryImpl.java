package com.a2m.gen.repository.sys.sys0102;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.dto.TsstUserRoleDto;
import com.a2m.gen.entities.TsstRole;
import com.a2m.gen.entities.TsstRoleMenu;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserRole;

@Repository
@Transactional
public class Sys0102RepositoryImpl implements Sys0102Repository{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<TsstRole> searchTsstRole(Map<String, Object> arg) throws SQLException {
		String sql ="SELECT * "
				+ "FROM TSST_ROLE "
				+ "WHERE (:roleId is null OR LOWER(ROLE_ID) LIKE LOWER(CONCAT('%', :roleId, '%')) ) "
				+ "		AND (:roleNm is null OR LOWER(ROLE_NM) LIKE LOWER(CONCAT('%', :roleNm, '%')) ) "
				+ "		AND (:useYn is null OR USE_YN = :useYn) ";
		
		String tab = arg.get("TAB")!=null?(String) arg.get("TAB"):"";
		
		if( tab.equals("ROLE_USER") || tab.equals("USER_ROLE") ) {
		  sql  += "		AND ROLE_ID != 'R014'";
		}
				
		Query query = em.createNativeQuery(sql, TsstRole.class).setParameter("roleId", arg.get("roleId"))
				.setParameter("roleNm", arg.get("roleNm"))
				.setParameter("useYn", arg.get("useYn"));
		List<TsstRole> results = query.getResultList();
		return results;
	}

	@Override
	public int deleteTsstRoles(String ids) throws SQLException {
		Query query = em.createNativeQuery("DELETE FROM TSST_ROLE WHERE ROLE_ID IN (:ids)").setParameter("ids", ids);
		return query.executeUpdate();
	}

	@Override
	public List<TsstUserRole> searchTsstUserRole(Map<String, Object> arg) throws SQLException {
		Query query = em.createNativeQuery("SELECT * FROM TSST_USER_ROLE WHERE (:roleId is null OR ROLE_ID = :roleId) AND (:userUid is null OR USER_UID = :userUid)", TsstUserRole.class)
				.setParameter("roleId", arg.get("roleId"))
				.setParameter("userUid", arg.get("userUid"));
		return query.getResultList();
	}

	@Override
	public TsstUserRole getTsstUserRole(TsstUserRoleDto userRole) throws SQLException {
		Query query = em.createNativeQuery("SELECT * FROM TSST_USER_ROLE WHERE ROLE_ID = :roleId AND USER_UID = :userUid", TsstUserRole.class)
				.setParameter("roleId", userRole.getRoleId())
				.setParameter("userUid", userRole.getUserUid());
		List<TsstUserRole> rs =query.getResultList();
		if (rs.size() > 0) {
			return rs.get(0);
		}
		return null;
	}

	@Override
	public int updateTsstUserRole(TsstUserRole userRole) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertTsstUserRole(TsstUserRoleDto userRole) throws SQLException {
		Query query = em.createNativeQuery("INSERT INTO TSST_USER_ROLE(ROLE_ID, USER_UID) VALUES(:roleId, :userUid)")
				.setParameter("roleId", userRole.getRoleId())
				.setParameter("userUid", userRole.getUserUid());
		return query.executeUpdate();
	}

	@Override
	public int deleteTsstUserRole(TsstUserRoleDto userRole) throws SQLException {
		Query query = em.createNativeQuery("DELETE FROM TSST_USER_ROLE WHERE ROLE_ID = :roleId AND USER_UID = :userUid")
				.setParameter("roleId", userRole.getRoleId())
				.setParameter("userUid", userRole.getUserUid());
		return query.executeUpdate();
	}

	@Override
	public List<TsstRoleMenu> searchTsstRoleMenu(Map<String, Object> arg) throws SQLException {
		String sql = "SELECT *"
				+ "	FROM TSST_ROLE_MENU"
				+ "	WHERE (:menuId is null OR LOWER(MENU_ID) LIKE LOWER(CONCAT('%', :menuId, '%'))) "
				+ "		AND (:roleId is null OR LOWER(ROLE_ID) LIKE LOWER(CONCAT('%', :roleId, '%')))";
		Query query = em.createNativeQuery(sql,TsstRoleMenu.class)
				.setParameter("menuId", arg.get("menuId"))
				.setParameter("roleId", arg.get("roleId"));
		return query.getResultList();
	}

	@Override
	public TsstRoleMenu getTsstRoleMenu(TsstRoleMenu arg) throws SQLException {
		Query query = em.createNativeQuery("SELECT * FROM TSST_ROLE_MENU WHERE ROLE_ID = :roleId AND MENU_ID = :menuId", TsstRoleMenu.class)
				.setParameter("roleId", arg.getRoleId())
				.setParameter("menuId", arg.getMenuId());
		try {
			return (TsstRoleMenu) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int updateTsstRoleMenu(TsstRoleMenu arg) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertTsstRoleMenu(TsstRoleMenu arg) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAccessTokenByRoleId(Map<String, Object> arg) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAccessTokenByUserId(Map<String, Object> arg) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TsstUser> getAllUser(Map<String, Object> arg) {
		String sql = "SELECT T1.* "
				+ "	FROM TSST_USER T1 "
				+ "		LEFT JOIN TSST_USER_INFO T2 ON T1.USER_INFO_ID = T2.USER_INFO_ID "
                + "     LEFT JOIN EAM_TEACHER_INFO T3 ON T1.USER_INFO_ID = T3.TEACHER_INFO_ID "
				+ "	WHERE (T1.USER_TYPE = 'EMP' OR T1.USER_TYPE = 'TEA') AND (:fullName is null or LOWER(T2.FULL_NAME) LIKE LOWER(CONCAT('%', :fullName, '%'))) "
				+ "		AND (:userId is null or LOWER(T1.USER_ID) LIKE LOWER(CONCAT('%', :userId, '%')))";
		Query query = em.createNativeQuery(sql, TsstUser.class).setParameter("fullName", arg.get("fullName"))
				.setParameter("userId", arg.get("userId"));
		List<TsstUser> result = query.getResultList();
		return result;
	}
	
	@Override
	public int updateTsstRole(TsstRole tsstRole) {
		String sql = "UPDATE TSST_ROLE "
				+ "	SET ROLE_NM = :roleNm, "
				+ "		CREATED_BY= :createdBy, "
				+ "		CREATED_DATE = :createdDate, "
				+ "		DESCRIPTION = :description, "
				+ "		UPDATED_BY = :updatedBy, "
				+ "		UPDATED_DATE = :updatedDate, "
				+ "		USE_YN = :useYn"
				+ "	WHERE ROLE_ID = :roleId";
		Query query = em.createNativeQuery(sql).setParameter("roleNm", tsstRole.getRoleNm())
				.setParameter("createdBy", tsstRole.getCreatedBy())
				.setParameter("createdDate", tsstRole.getCreatedDate())
				.setParameter("description", tsstRole.getDescription())
				.setParameter("updatedBy", tsstRole.getUpdatedBy())
				.setParameter("updatedDate", tsstRole.getUpdatedDate())
				.setParameter("roleId", tsstRole.getRoleId())
				.setParameter("useYn", tsstRole.getUseYn());
		return query.executeUpdate();
	}

}
