package com.a2m.gen.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.TsstRole;

public interface TsstRoleRepository extends JpaRepository<TsstRole, String>{
	
	Optional<TsstRole> findById(String id);
	
	@Query(value = "SELECT MAX(ROLE_ID) FROM TSST_ROLE", nativeQuery = true)
	String getMaxRoleId();	
	
	@Query(value = "DELETE FROM TSST_ROLE WHERE ROLE_ID IN (:ids)",nativeQuery = true)
	int deleteTsstRoles(@Param("ids") String ids);
	
	@Query(value = "DELETE FROM TSST_ROLE_MENU ROLE_ID IN (:ids)", nativeQuery = true)
	int deleteRoleMenu(@Param("ids") String ids);
	
	@Query(value = "SELECT DISTINCT CONCAT(menu.URL, '$', lm.READ_YN, ':', lm.WRT_YN, ':', lm.MOD_YN, ':', lm.DEL_YN, ':', lm.PNT_YN, ':', lm.EXC_DN_YN, ':', lm.MNG_YN) ROLES"
			+ "		FROM TSST_USER_ROLE role"
			+ "		INNER JOIN TSST_ROLE_MENU lm ON role.ROLE_ID = lm.ROLE_ID"
			+ "		INNER JOIN TSST_MENU menu ON menu.MENU_ID = lm.MENU_ID"
			+ "		WHERE USER_UID = :userUid",nativeQuery = true)
	List<String> getRoles(@Param("userUid") String userUid) throws SQLException;
	
	void deleteByRoleId(String id);
}
