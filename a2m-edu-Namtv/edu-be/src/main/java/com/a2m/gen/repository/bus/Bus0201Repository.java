package com.a2m.gen.repository.bus;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.EventMgt;

@Repository
public interface Bus0201Repository extends JpaRepository<EventMgt, Long>{
	
	@Query(value = "SELECT ROW_NUMBER() OVER (ORDER BY GEN_EVENT_MGT.EVENT_ID DESC) AS IDX, EVENT_ID,"
			+ "EVENT_NM,"
			+ "DATE_FORMAT(FROM_DT, \"%Y-%m-%d\") AS FROM_DT,"
			+ "DATE_FORMAT(TO_DT, \"%Y-%m-%d\") AS TO_DT,"
			+ "DATE_FORMAT(INS_DT, \"%Y-%m-%d\") AS INS_DT,"
			+ "DATE_FORMAT(UPDATE_DT, \"%Y-%m-%d\") AS UPDATE_DT,"
			+ "DISCOUNT "
			+ "FROM GEN_EVENT_MGT WHERE LOWER(EVENT_NM) LIKE CONCAT('%', :keySearch,'%')",
			countQuery = "SELECT COUNT(*) FROM GEN_EVENT_MGT WHERE LOWER(EVENT_NM) LIKE CONCAT('%', :keySearch,'%')",
			nativeQuery = true)
	Page<Map> getPage(Pageable pageable, @Param("keySearch") String keySearch);

	
	
	@Query(value = "INSERT INTO GEN_EVENT_MGT "
			+ "(EVENT_ID, FROM_DT, TO_DT, INS_DT, DISCOUNT, EVENT_NM) "
			+ "VALUES ( :id, :fromDate, :toDate, NOW(), :discount, :eventNm);",nativeQuery = true)
	void insert(@Param("id") long id,
									@Param("fromDate") String fromDate,
									@Param("toDate") String toDate,
									@Param("eventNm") String eventNm,
									@Param("discount") int discount);
	
	@Query(value = "UPDATE GEN_EVENT_MGT SET "
			+ "FROM_DT = :fromDate, "
			+ "TO_DT = :toDate, "
			+ "DISCOUNT = :discount, "
			+ "EVENT_NM = :eventNm, "
			+ "UPDATE_DT = NOW() "
			+ "WHERE  EVENT_ID= :id",nativeQuery = true)
	void modify(@Param("id") long id,
				@Param("fromDate") String fromDate,
				@Param("toDate") String toDate,
				@Param("eventNm") String eventNm,
				@Param("discount") int discount);
	
}
