package com.a2m.gen.repository.edu;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.EamTeacherInfo;

public interface Edu0105Respository extends JpaRepository<TsstUser, String>{
	
	public TsstUser findByUserUid(String userUid);
	
//	@Query(value = "UPDATE TSST_USER SET STATUS= :status, UPDATED_DATE= NOW() WHERE  USER_UID= :id"
//			,nativeQuery = true)
//	void modifyStatus(@Param("id") String id, @Param("status") int status);

	@Query(value = "SELECT * FROM TSST_USER u left join EAM_TEACHER_INFO i on i.TEACHER_INFO_ID = u.USER_INFO_ID  WHERE u.USER_TYPE = 'TEA'"
			+ " AND (:fullName is null OR UPPER(i.FULL_NAME) LIKE UPPER(concat('%',:fullName,'%')))"
			+ " AND (:status is null OR u.STATUS= :status)"
            + " AND i.DELETE_FLAG = 0"
			+ " ORDER BY i.TEACHER_INFO_ID DESC"
			, nativeQuery = true)
	Page<TsstUser> findByfullNameAndStatus(
			@Param("fullName") String fullName, @Param("status") String status, Pageable pageable);
	
	@Modifying
    @Transactional
    @Query(value = "insert into TSST_USER (USER_UID, CREATED_BY, CREATED_DATE, PWD, STATUS, USER_ID, USER_TYPE, USER_INFO_ID) "
    + "VALUES (:#{#u.userUid},:#{#u.createdBy},  :#{#u.createdDate} ,:#{#u.password}, :#{#u.status}, :#{#u.userId}, :#{#u.userType}, :#{#stu.teacherInfoId})"
    		, nativeQuery = true)
	 int saveTeacher(@Param("u") TsstUser tsstUser, @Param("stu") EamTeacherInfo eamTeacherInfo);


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