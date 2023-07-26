package com.a2m.gen.repository.bus;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.LicenseMgt;

@Repository
public interface Bus0101Repository extends JpaRepository<LicenseMgt, Long>{

	
	@Query(value = "SELECT ROW_NUMBER() OVER (ORDER BY GEN_LICENSE_MGT.UPDATE_DT DESC, GEN_LICENSE_MGT.INS_DT DESC) AS IDX, LICENSE_ID, CLIENT_ID, INS_DT, EXPIRED_DT, UPDATE_DT, TSST_USER_INFO.FULL_NAME, GEN_LICENSE_MGT.STATUS, GEN_LICENSE_MGT.PRICE "
			+ " FROM GEN_LICENSE_MGT INNER JOIN TSST_USER_INFO"
			+ " ON GEN_LICENSE_MGT.CLIENT_ID = TSST_USER_INFO.USER_INFO_ID WHERE LOWER(TSST_USER_INFO.FULL_NAME) LIKE CONCAT('%', :keySearch,'%') AND (:status is null OR GEN_LICENSE_MGT.STATUS LIKE CONCAT('%', :status,'%'))"
			,countQuery = "SELECT COUNT(*)"
					+ " FROM GEN_LICENSE_MGT INNER JOIN TSST_USER_INFO"
					+ " ON GEN_LICENSE_MGT.CLIENT_ID = TSST_USER_INFO.USER_INFO_ID WHERE LOWER(TSST_USER_INFO.FULL_NAME) LIKE CONCAT('%', :keySearch,'%') AND (:status is null OR GEN_LICENSE_MGT.STATUS LIKE CONCAT('%', :status,'%'))"
			,nativeQuery = true)
	Page<Map> getPage(Pageable pageable, @Param("keySearch") String keySearch, @Param("status") String status);
	
	
	
	
	@Query(value = "SELECT DISTINCT TSST_USER_INFO.FULL_NAME, TSST_USER.USER_INFO_ID AS CLIENT_ID  FROM TSST_USER"
			+" INNER JOIN TSST_USER_ROLE"
			+" ON TSST_USER.USER_UID = TSST_USER_ROLE.USER_UID"
			+" INNER JOIN TSST_USER_INFO ON TSST_USER_INFO.USER_INFO_ID = TSST_USER.USER_INFO_ID"
			+ " WHERE TSST_USER_ROLE.ROLE_ID != 'R001' \r\n"
			+ " AND TSST_USER_ROLE.ROLE_ID != 'R002'\r\n"
			+ " AND TSST_USER_ROLE.ROLE_ID != 'R003'\r\n"
			+ " AND TSST_USER.STATUS = 1",nativeQuery = true)
	List<Map<Object, Object>> getListCustomer();
	
	
	
	@Query(value = "INSERT INTO GEN_LICENSE_MGT (LICENSE_ID, CLIENT_ID,INS_DT,EXPIRED_DT,TOKEN, STATUS, PRICE) \r\n"
			+ "VALUES (:id, :clientId, :insDate, :expDate, :token, 1, :price);",nativeQuery = true)
	void insert(@Param("id") long id,
				@Param("clientId") long clientId,
				@Param("insDate") String insDate,
				@Param("expDate") String expDate,
				@Param("token") String token,
				@Param("price") Double price
				);
	
	
	
	
	@Query(value = "UPDATE GEN_LICENSE_MGT SET INS_DT = :insDate, EXPIRED_DT= :expDate, UPDATE_DT= NOW(), PRICE = :price WHERE  LICENSE_ID= :id",nativeQuery = true)
	void modifyLicense(@Param("id") long id, @Param("insDate") String insDate, @Param("expDate") String expDate,@Param("price") Double price);
	
	
	
	
	@Query(value = "UPDATE GEN_LICENSE_MGT SET STATUS= :status, UPDATE_DT= NOW() WHERE  LICENSE_ID= :id",nativeQuery = true)
	void modifyStatus(@Param("id") long id, @Param("status") int status);
}
