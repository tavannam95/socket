package com.a2m.gen.repository.edu;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.EamStudentInfo;

public interface Edu0101Respository extends JpaRepository<TsstUser, String>{

	public TsstUser findByUserUid(String userUid);
	
//	@Query(value = "UPDATE TSST_USER SET STATUS= :status, UPDATED_DATE= NOW() WHERE  USER_UID= :id"
//			,nativeQuery = true)
//	void modifyStatus(@Param("id") String id, @Param("status") int status);

	@Query(value = 
			"SELECT * FROM TSST_USER AS tsst "
			+ "WHERE tsst.USER_INFO_ID = :userInfoId "
			+"AND tsst.USER_TYPE = :userType "
			, nativeQuery = true)
	TsstUser getTsstUserByUserInfoId(@Param("userInfoId") String userInfoId, @Param("userType") String userType);
	
	@Query(value = "SELECT * FROM TSST_USER u left join EAM_STUDENT_INFO i on i.STUDENT_INFO_ID = u.USER_INFO_ID "
			+ "left join EAM_CLASS_STUDENT_MAP c on i.STUDENT_INFO_ID = c.STUDENT_INFO_ID"
			+ " WHERE u.USER_TYPE = 'STU'"
			+ " AND (:fullName is null OR UPPER(i.FULL_NAME) LIKE UPPER(concat('%',:fullName,'%')))"
			+ " AND (:status is null OR u.STATUS= :status)"
			+ " AND (:classId is null OR c.CLASS_ID= :classId)"
			+ " AND i.DELETE_FLAG = 0 "
			+ " GROUP BY u.USER_ID "
			+ " ORDER BY i.STUDENT_INFO_ID DESC"
			, nativeQuery = true)
	Page<TsstUser> findByfullNameAndStatus(
			@Param("fullName") String fullName, @Param("status") String status, @Param("classId") String classId, Pageable pageable);

    @Query(value = "SELECT u.USER_ID FROM TSST_USER u "
//            + "WHERE (:userId is null OR UPPER(u.USER_ID) LIKE UPPER(concat('%',:userId,'%'))) "
            + "WHERE (:userId is null OR u.USER_ID LIKE concat('%',:userId,'%')) "
//            + "OR (:email is null OR UPPER(i.EMAIL) LIKE UPPER(concat('%',:email,'%'))) 
            +"ORDER BY u.USER_ID "
            , nativeQuery = true)
    List<String> checkUserId(@Param("userId") String userId);
//    String checkUserId(@Param("userId") String userId, @Param("email") String email);
    @Query(value = "select c.CLASS_NM from EAM_CLASS_STUDENT_MAP s "
    		+ "left join EAM_CLASS c on s.CLASS_ID = c.CLASS_ID "
          + "WHERE s.STUDENT_INFO_ID = :studentInfoId "
          , nativeQuery = true)
  List<String> getClassCourseForStudentId(@Param("studentInfoId") String studentInfoId);
	@Modifying
    @Transactional
    @Query(value = "insert into TSST_USER (USER_UID, CREATED_BY, CREATED_DATE, PWD, STATUS, USER_ID, USER_TYPE, USER_INFO_ID) "
    + "VALUES (:#{#u.userUid},:#{#u.createdBy},  :#{#u.createdDate} ,:#{#u.password}, :#{#u.status}, :#{#u.userId}, :#{#u.userType}, :#{#stu.studentInfoId})"
    		, nativeQuery = true)
	 int saveStudent(@Param("u") TsstUser tsstUser, @Param("stu") EamStudentInfo eamStudentInfo);


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