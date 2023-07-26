package com.a2m.gen.repository.signUp;

import java.util.List;
import java.util.Date;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserInfo;

@Repository
public interface SignUpRepository extends JpaRepository<TsstUser,Long>{
	
	@Query(value = "INSERT INTO TSST_USER_INFO "
			+ "(FULL_NAME, DOB, EMAIL, CELL_PHONE, ADDRESS, GENDER, EMAIL_VERIFY_KEY, 2FA_ENABLE, 2FA_KEY, ORGANIZATION)"
			+ " VALUES "
			+ "( :fullName, :dob, :email, :cellPhone, :address, :gender, :emailKey, 0, :twoFaKey, :organization)", nativeQuery = true)
	Map insertTsstUserInfo(@Param("fullName") String fullName,
							@Param("dob") Date dob,
							@Param("email") String email,
							@Param("cellPhone") String cellPhone,
							@Param("address") String address,
							@Param("gender") boolean gender,
							@Param("emailKey") String emailKey,
							@Param("twoFaKey") String twoFaKey,
							@Param("organization") String organization);
	
	@Query(value = "SELECT LAST_INSERT_ID() AS USER_INFO_ID;",nativeQuery = true)
	String getLastInsertedId();
	
	@Query(value = "INSERT INTO TSST_USER "
			+ "(USER_UID, USER_ID, USER_INFO_ID, PWD, CREATED_DATE, STATUS)"
			+ " VALUES "
			+ "( :userUid, :userId, :userInfoId, :password, NOW(), 0);", nativeQuery = true)
	void insertTsstUser(@Param("userUid") String userUid,
							@Param("userId") String userId,
							@Param("userInfoId") Long userInfoId,
							@Param("password") String password);
	
	
	@Query(value = "SELECT COMM_CD, COMM_NM FROM TCCO_STD WHERE UP_COMM_CD = '04'",nativeQuery = true)
	List<Map<Object, Object>> getListPosition();
	
	@Query(value = "INSERT INTO TSST_USER_ROLE (ROLE_ID, USER_UID) VALUES (:roleUser, :userUid)", nativeQuery = true)
	void setRoleUser(@Param("roleUser") String roleUser, @Param("userUid") String userUid);
	
	
	
	
	
}
