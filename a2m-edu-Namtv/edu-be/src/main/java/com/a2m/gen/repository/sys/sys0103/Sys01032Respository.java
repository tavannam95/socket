package com.a2m.gen.repository.sys.sys0103;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserInfo;

public interface Sys01032Respository extends JpaRepository<TsstUser, String>{
	
	@Query(value = "UPDATE TSST_USER_INFO SET "
			+" FULL_NAME = :fullName, DOB= :dob,"
			
			+" EMAIL = :email, CELL_PHONE= :cellPhone,"
			
			+" ADDRESS = :address, GENDER= :gender,"
			
			+" IMG_PATH = :imgPath, EMAIL_VERIFY_KEY= :emailVerifyKey,"
			
			+" 2FA_ENABLE = :twoFAEnable, 2FA_KEY= :twoFAKey,"
			+" ORGANIZATION = :organization, POSITION= :position"
			+" WHERE  USER_INFO_ID= :id",nativeQuery = true)
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
			@Param("position") String position
	);
}
