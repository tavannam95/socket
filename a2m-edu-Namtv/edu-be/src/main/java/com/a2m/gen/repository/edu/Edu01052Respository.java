package com.a2m.gen.repository.edu;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.EamTeacherInfo;

public interface Edu01052Respository extends JpaRepository<EamTeacherInfo, Long>{	
	
//	@Modifying
//    @Transactional
//    @Query(value = "insert into EAM_TEACHER_INFO ("
//    + "TEACHER_INFO_ID, FULL_NAME, DOB, EMAIL, CELL_PHONE, ADDRESS, GENDER, IMG_PATH, EMAIL_VERIFY_KEY, 2FA_ENABLE, 2FA_KEY, ORGANIZATION, POSITION) "
//    + "VALUES ("
//    + " :#{#stu.teacherInfoId}, :#{#stu.fullName}, :#{#stu.dob}, :#{#stu.email} ,:#{#stu.cellPhone}, :#{#stu.address}, :#{#stu.gender},"
//    + " :#{#stu.imgPath}, :#{#stu.emailVerifyKey}, :#{#stu.twoFAEnable}, :#{#stu.twoFAKey}, :#{#stu.organization},"
//    + " :#{#stu.position})"
//    		, nativeQuery = true)
//	Integer saveTeacherInfo(@Param("stu") EamTeacherInfo eamTeacherInfo);
	
	
//	@Modifying(flushAutomatically  = true)
//    @Transactional
//    @Query(value = "update EAM_TEACHER_INFO e set "
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
//    		+ " where e.TEACHER_INFO_ID = :#{#stu.teacherInfoId}"
//    		, nativeQuery = true)
//	Integer update(@Param("stu") EamTeacherInfo eamTeacherInfo);
	
	@Query(value = "UPDATE EAM_TEACHER_INFO SET "
			+" FULL_NAME = :fullName, DOB= :dob,"
			
			+" EMAIL = :email, CELL_PHONE= :cellPhone,"
			
			+" ADDRESS = :address, GENDER= :gender,"
			
			+" IMG_PATH = :imgPath, EMAIL_VERIFY_KEY= :emailVerifyKey,"
			
			+" 2FA_ENABLE = :twoFAEnable, 2FA_KEY= :twoFAKey,"
			+" ORGANIZATION = :organization, POSITION= :position,"
            +" DELETE_FLAG = :DELETE_FLAG"
			+" WHERE  TEACHER_INFO_ID= :id",nativeQuery = true)
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
            @Param("DELETE_FLAG") Boolean DELETE_FLAG
	);
	
}
