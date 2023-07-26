package com.a2m.gen.repository.edu;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.EamStudentInfo;

public interface Edu01012Respository extends JpaRepository<EamStudentInfo, Long>{	
	
//	@Modifying
//    @Transactional
//    @Query(value = "insert into EAM_STUDENT_INFO ("
//    + "STUDENT_INFO_ID, FULL_NAME, DOB, EMAIL, CELL_PHONE, ADDRESS, GENDER, IMG_PATH, EMAIL_VERIFY_KEY, 2FA_ENABLE, 2FA_KEY, ORGANIZATION, POSITION) "
//    + "VALUES ("
//    + " :#{#stu.studentInfoId}, :#{#stu.fullName}, :#{#stu.dob}, :#{#stu.email} ,:#{#stu.cellPhone}, :#{#stu.address}, :#{#stu.gender},"
//    + " :#{#stu.imgPath}, :#{#stu.emailVerifyKey}, :#{#stu.twoFAEnable}, :#{#stu.twoFAKey}, :#{#stu.organization},"
//    + " :#{#stu.position})"
//    		, nativeQuery = true)
//	Integer saveStudentInfo(@Param("stu") EamStudentInfo eamStudentInfo);
	
	
//	@Modifying(flushAutomatically  = true)
//    @Transactional
//    @Query(value = "update EAM_STUDENT_INFO e set "
//    		+ " e.FULL_NAME = :#{#stu.fullName}, "
//    		+ " e.DOB = :#{#stu.dob}, "
//    		+ " e.EMAIL = :#{#stu.email}, "
//    		+ " e.CELL_PHONE = :#{#stu.cellPhone}, "
//    		+ " e.ADDRESS = :#{#stu.address}, "
//    		+ " e.GENDER = :#{#stu.gender}, "
//    		+ " e.IMG_PATH = :#{#stu.imgPath}, "
//    		+ " e.EMAIL_VERIFY_KEY = :#{#stu.emailVerifyKey}, "
//    		+ " e.2FA_ENABLE = :#{#stu.twoFAEnable}, "
//    		+ " e.2FA_KEY = :#{#stu.twoFAKey}, "
//    		+ " e.ORGANIZATION = :#{#stu.organization}, "
//    		+ " e.POSITION = :#{#stu.position}, "
//    		+ " where e.STUDENT_INFO_ID = :#{#stu.studentInfoId}"
//    		, nativeQuery = true)
//	Integer update(@Param("stu") EamStudentInfo eamStudentInfo);
	
	@Query(value = "UPDATE EAM_STUDENT_INFO SET "
			+" FULL_NAME = :fullName, DOB= :dob,"
			
			+" EMAIL = :email, CELL_PHONE= :cellPhone,"
			
			+" ADDRESS = :address, GENDER= :gender,"
			
			+" IMG_PATH = :imgPath, EMAIL_VERIFY_KEY= :emailVerifyKey,"
			
			+" 2FA_ENABLE = :twoFAEnable, 2FA_KEY= :twoFAKey,"
			+" ORGANIZATION = :organization, POSITION= :position,"
			+" DELETE_FLAG = :deleteFlag,"
			+" TYPICAL_FLAG = :typicalFlag,"
			+" IDEA = :idea"
			+" WHERE  STUDENT_INFO_ID= :id",nativeQuery = true)
	void modify(
			@Param("id") Long id, 
			@Param("fullName") String fullName, 
			@Param("dob") Date dob, 
			@Param("email") String email, 
			@Param("cellPhone") String cellPhone, 
			
			@Param("address") String address, 
			@Param("gender") Boolean gender, 
			
			@Param("imgPath") String imgPath, 
			@Param("emailVerifyKey") String emailVerifyKey, 
			
			@Param("twoFAEnable") Boolean twoFAEnable,
			@Param("twoFAKey") String twoFAKey, 
			
			@Param("organization") String organization, 
			@Param("position") String position,
			@Param("deleteFlag") Boolean deleteFlag,
			@Param("typicalFlag") Boolean typicalFlag,
			@Param("idea") String  idea
	);
	
	@Query(value = "DELETE FROM EAM_CLASS_STUDENT_MAP WHERE TABLE_ID = :tabledId", nativeQuery = true)
	Optional<Integer> deleteClassStudentMap(@Param("tabledId") String tabledId);
	
	@Query(value = "INSERT INTO EAM_CLASS_STUDENT_MAP (CLASS_ID, STUDENT_INFO_ID) VALUES (:classId, :studentId)", nativeQuery = true)
	Optional<Integer> insertClassStudentMap(@Param("classId") String classId, @Param("studentId") String studentId);
	
	
	@Query(value = "DELETE FROM EAM_STUDENT_COURSE_MAP WHERE STUDENT_INFO_ID = :studentInfoId", nativeQuery = true)
	Optional<Integer> deleteStuCouByStudenInfoId(@Param("studentInfoId") String studentInfoId);
	
	@Query(value = "INSERT INTO EAM_STUDENT_COURSE_MAP ( STUDENT_INFO_ID,COURSE_ID,INS_UID,INS_DT) VALUES (:studentInfoId, :courseId, :ins_uid, :ins_dt)", nativeQuery = true)
	Optional<Integer> insertStuCouMap( @Param("studentInfoId") String studentInfoId,@Param("courseId") String courseId,@Param("ins_uid") String ins_uid,@Param("ins_dt") Date ins_dt);
	
}
