package com.a2m.gen.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TsstUser;

@Repository
public interface TsstUserRepository extends JpaRepository<TsstUser, String>{
	
	@Query(value = "SELECT T1.USER_TYPE as userType, T1.USER_UID as userUid, T1.USER_ID as userId, T1.USER_INFO_ID as userInfoId, T1.CREATED_DATE as createdDate,"
			+ "		T2.FULL_NAME as fullName, T2.DOB as dob, T2.EMAIL as email, T2.CELL_PHONE as cellPhone, T2.ADDRESS as address, "
			+ "		T2.GENDER as gender, T2.IMG_PATH as imgPath, T2.2FA_ENABLE as twoFAEnable, T2.ORGANIZATION as organization, T2.INTRO as introduce , "
			+ "		T3.COMM_CD as commCd, T3.COMM_NM as commNm, T3.COMM_NM_EN as commNmEN, T3.COMM_NM_VI as commNmVi "
			+ "	FROM TSST_USER T1"
			+ "		INNER JOIN TSST_USER_INFO T2 ON T1.USER_INFO_ID = T2.USER_INFO_ID"
			+ "		LEFT JOIN TCCO_STD T3 ON T2.POSITION = T3.COMM_CD "
			+ "	WHERE T1.USER_UID = :userUid", nativeQuery = true)	
	Map<String,Object> getUserEMPInfo(@Param("userUid") String userUid);
	
	@Query(value = "SELECT T1.USER_TYPE as userType, T1.USER_UID as userUid, T1.USER_ID as userId, T1.USER_INFO_ID as userInfoId, T1.CREATED_DATE as createdDate,"
			+ "		T2.FULL_NAME as fullName, T2.DOB as dob, T2.EMAIL as email, T2.CELL_PHONE as cellPhone, T2.ADDRESS as address, "
			+ "		T2.GENDER as gender, T2.IMG_PATH as imgPath, T2.2FA_ENABLE as twoFAEnable, T2.ORGANIZATION as organization, T2.INTRO as introduce , "
			+ "		T3.COMM_CD as commCd, T3.COMM_NM as commNm, T3.COMM_NM_EN as commNmEN, T3.COMM_NM_VI as commNmVi "
			+ "	FROM TSST_USER T1"
			+ "		INNER JOIN EAM_TEACHER_INFO T2 ON T1.USER_INFO_ID = T2.TEACHER_INFO_ID"
			+ "		LEFT JOIN TCCO_STD T3 ON T2.POSITION = T3.COMM_CD "
			+ "	WHERE T1.USER_UID = :userUid", nativeQuery = true)	
	Map<String,Object> getUserTEAInfo(@Param("userUid") String userUid);
	
	@Query(value = "SELECT T1.USER_TYPE as userType, T1.USER_UID as userUid, T1.USER_ID as userId, T1.USER_INFO_ID as userInfoId, T1.CREATED_DATE as createdDate,"
			+ "		T2.FULL_NAME as fullName, T2.DOB as dob, T2.EMAIL as email, T2.CELL_PHONE as cellPhone, T2.ADDRESS as address, "
			+ "		T2.GENDER as gender, T2.IMG_PATH as imgPath, T2.2FA_ENABLE as twoFAEnable, T2.ORGANIZATION as organization, T2.INTRO as introduce , "
			+ "		T3.COMM_CD as commCd, T3.COMM_NM as commNm, T3.COMM_NM_EN as commNmEN, T3.COMM_NM_VI as commNmVi "
			+ "	FROM TSST_USER T1"
			+ "		INNER JOIN EAM_STUDENT_INFO T2 ON T1.USER_INFO_ID = T2.STUDENT_INFO_ID"
			+ "		LEFT JOIN TCCO_STD T3 ON T2.POSITION = T3.COMM_CD "
			+ "	WHERE T1.USER_UID = :userUid", nativeQuery = true)	
	Map<String,Object> getUserSTUInfo(@Param("userUid") String userUid);
	
	@Query(value = "SELECT * FROM TSST_USER WHERE USER_UID = :userUid", nativeQuery = true)
	TsstUser findByUserUid(@Param("userUid") String userUid);
}
