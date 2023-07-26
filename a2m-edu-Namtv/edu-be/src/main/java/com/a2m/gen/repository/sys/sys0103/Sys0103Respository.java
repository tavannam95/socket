package com.a2m.gen.repository.sys.sys0103;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserInfo;

public interface Sys0103Respository extends JpaRepository<TsstUser, String>{
    
	public TsstUser findByUserUid(
			String userUid
			);
	
//	@Query(value = "UPDATE TSST_USER SET STATUS= :status, UPDATED_DATE= NOW() WHERE  USER_UID= :id"
//			,nativeQuery = true)
//	void modifyStatus(@Param("id") String id, @Param("status") int status);

	@Query(value = "SELECT * FROM TSST_USER u left join TSST_USER_INFO i on i.USER_INFO_ID = u.USER_INFO_ID  WHERE u.USER_TYPE = 'EMP'"
			+ " AND (:fullName is null OR UPPER(i.FULL_NAME) LIKE UPPER(concat('%',:fullName,'%')))"
			+ "  AND (:status is null OR u.STATUS= :status)"
			, nativeQuery = true)
	Page<TsstUser> findByfullNameAndStatus(
			@Param("fullName") String fullName, @Param("status") String status, Pageable pageable);
	

	@Query(value = 
			"SELECT * FROM TSST_USER AS user LEFT JOIN TSST_USER_ROLE AS userRole  ON  user.USER_UID = userRole.USER_UID "
			+ "LEFT JOIN TSST_ROLE AS role ON userRole.ROLE_ID = role.ROLE_ID "
			+"WHERE userRole.ROLE_ID = 'R002'"
			, nativeQuery = true)
	List<TsstUser> findByUserAssist();

	@Query(value = 
			"SELECT * FROM TSST_USER AS tsst "
			+ "WHERE tsst.USER_INFO_ID = :userInfoId "
			+"AND tsst.USER_TYPE = :userType "
			, nativeQuery = true)
	TsstUser findByTsstUserList(@Param("userInfoId") String userInfoId, @Param("userType") String userType);
	
	@Modifying
    @Transactional
    @Query(value = "insert into TSST_USER (USER_UID, CREATED_BY, CREATED_DATE, PWD, STATUS, USER_ID, USER_TYPE, USER_INFO_ID) "
    + "VALUES (:#{#u.userUid},:#{#u.createdBy},  :#{#u.createdDate} ,:#{#u.password}, :#{#u.status}, :#{#u.userId}, :#{#u.userType}, :#{#stu.userInfoId})"
    		, nativeQuery = true)
	 int saveUser(@Param("u") TsstUser tsstUser, @Param("stu") TsstUserInfo tsstUserInfo);
	
	@Query(value = "UPDATE TSST_USER SET "
			+" UPDATED_BY= :UPDATED_BY,"
			
			+" UPDATED_DATE = :UPDATED_DATE, PWD= :PWD,"
			
			+" STATUS = :STATUS, USER_ID= :USER_ID,"
			
			+" USER_TYPE = :USER_TYPE, USER_INFO_ID= :USER_INFO_ID"
			
			+" WHERE  USER_UID= :id",nativeQuery = true)
	void modify(
			@Param("id") String id, 
			@Param("UPDATED_BY") String UPDATED_BY,
			@Param("UPDATED_DATE") Date UPDATED_DATE, 
			@Param("PWD") String PWD,
			@Param("STATUS") boolean STATUS, 
			@Param("USER_ID") String USER_ID, 
			@Param("USER_TYPE") String USER_TYPE, 
			@Param("USER_INFO_ID") Long USER_INFO_ID

	);
}
