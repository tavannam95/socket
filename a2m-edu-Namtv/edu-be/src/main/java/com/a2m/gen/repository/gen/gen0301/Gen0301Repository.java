package com.a2m.gen.repository.gen.gen0301;

import com.a2m.gen.entities.TsstUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

@Repository
public interface Gen0301Repository extends JpaRepository<TsstUserInfo, Long> {
    @Query(value = "SELECT USER_ID, data_delivery.TSST_USER_INFO.USER_INFO_ID, data_delivery.TSST_USER_INFO.FULL_NAME, DATE_FORMAT(data_delivery.TSST_USER_INFO.DOB, \"%Y-%m-%d\") AS DOB ,  data_delivery.TSST_USER_INFO.EMAIL,  data_delivery.TSST_USER_INFO.CELL_PHONE, data_delivery.TSST_USER_INFO.ADDRESS,  data_delivery.TSST_USER_INFO.GENDER,  data_delivery.TSST_USER_INFO.IMG_PATH, data_delivery.TSST_USER_INFO.2FA_ENABLE AS FA_ENABLE FROM data_delivery.TSST_USER_INFO JOIN data_delivery.TSST_USER on data_delivery.TSST_USER.USER_INFO_ID = data_delivery.TSST_USER_INFO.USER_INFO_ID WHERE data_delivery.TSST_USER_INFO.USER_INFO_ID = :id",nativeQuery = true)
    Map getInfoById(@Param("id") long id);
    
    
    @Query(value = "SELECT COMM_NM FROM data_delivery.TSST_USER_INFO INNER JOIN data_delivery.TCCO_STD on data_delivery.TSST_USER_INFO.POSITION = data_delivery.TCCO_STD.COMM_CD WHERE data_delivery.TSST_USER_INFO.USER_INFO_ID = :id",nativeQuery = true)
    Map getPositonName(@Param("id") long id);
    
    @Query(value = "SELECT 2FA_KEY AS FA_KEY, USER_ID FROM data_delivery.TSST_USER_INFO JOIN data_delivery.TSST_USER on data_delivery.TSST_USER.USER_INFO_ID = data_delivery.TSST_USER_INFO.USER_INFO_ID WHERE data_delivery.TSST_USER_INFO.USER_INFO_ID = :id",nativeQuery = true)
    Map getFaKeyById(@Param("id") long id);

    @Query(value = "UPDATE TSST_USER_INFO " +
            "SET " +
            "FULL_NAME= :fullName,"+
            "DOB= :dob,"+
            "CELL_PHONE= :cellPhone,"+
            "ADDRESS= :address,"+
            "GENDER= :gender, " +
            "ORGANIZATION = :organization,"+
            "INTRO = :introduce,"+
            "IMG_PATH = :imgPath"+
            " WHERE  USER_INFO_ID = :id", nativeQuery = true)
    void modifyInfoEmp(@Param("id") long id,
                   @Param("fullName") String fullName,
                   @Param("dob") String dob,
                   @Param("cellPhone") String cellPhone,
                   @Param("address") String address,
                   @Param("gender") int gender,
                   @Param("organization") String organization,
                   @Param("introduce") String introduce,
    			   @Param("imgPath") String imgPath);
    
    @Query(value = "UPDATE EAM_TEACHER_INFO " +
            "SET " +
            "FULL_NAME= :fullName,"+
            "DOB= :dob,"+
            "CELL_PHONE= :cellPhone,"+
            "ADDRESS= :address,"+
            "GENDER= :gender, " +
            "IMG_PATH= :imgPath, " +
            "ORGANIZATION = :organization,"+
            "INTRO = :introduce"+
            " WHERE  TEACHER_INFO_ID = :id", nativeQuery = true)
    void modifyInfoTea(@Param("id") long id,
                   @Param("fullName") String fullName,
                   @Param("dob") String dob,
                   @Param("cellPhone") String cellPhone,
                   @Param("address") String address,
                   @Param("gender") int gender,
                   @Param("imgPath") String imgPath,
                   @Param("organization") String organization,
                   @Param("introduce") String introduce);
    
    @Query(value = "UPDATE EAM_STUDENT_INFO " +
            "SET " +
            "FULL_NAME= :fullName,"+
            "DOB= :dob,"+
            "CELL_PHONE= :cellPhone,"+
            "ADDRESS= :address,"+
            "GENDER= :gender, " +
            "IMG_PATH= :imgPath, " +
            "ORGANIZATION = :organization,"+
            "INTRO = :introduce"+
            " WHERE  STUDENT_INFO_ID = :id", nativeQuery = true)
    void modifyInfoStu(@Param("id") long id,
                   @Param("fullName") String fullName,
                   @Param("dob") String dob,
                   @Param("cellPhone") String cellPhone,
                   @Param("address") String address,
                   @Param("gender") int gender,
                   @Param("imgPath") String imgPath,
                   @Param("organization") String organization,
                   @Param("introduce") String introduce);
    
    @Query(value = "SELECT EMAIL_VERIFY_KEY  FROM data_delivery.TSST_USER_INFO WHERE USER_INFO_ID = :id",nativeQuery = true)
    Map getEmailVerifyKeyById(@Param("id") long id);
    
    @Query(value = "UPDATE data_delivery.TSST_USER_INFO " +
            "SET " +
            "EMAIL= :email"+
            " WHERE  USER_INFO_ID = :id", nativeQuery = true)
    void modifyEmail(@Param("id") long id, @Param("email") String email);
    
    @Query(value = "UPDATE data_delivery.TSST_USER_INFO " +
            "SET " +
            "2FA_ENABLE= :faEnable"+
            " WHERE  USER_INFO_ID = :id", nativeQuery = true)
    void modify2FaEnable(@Param("id") long id, @Param("faEnable") int faEnable);
    
    @Query(value = "UPDATE TSST_USER " +
            "SET " +
            "PWD= :password"+
            " WHERE  USER_INFO_ID = :id", nativeQuery = true)
    void changePassword(@Param("id") long id, @Param("password") String password);
    
    @Query(value = "SELECT PWD FROM TSST_USER"
            + " WHERE  USER_INFO_ID = :id", nativeQuery = true)
    Map getPasswordById(@Param("id") long id);
    
    
    
}