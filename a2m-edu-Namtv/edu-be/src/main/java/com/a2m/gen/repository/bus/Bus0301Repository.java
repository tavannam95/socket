package com.a2m.gen.repository.bus;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.ProjectMgt;
import com.a2m.gen.entities.TsstUser;

public interface Bus0301Repository extends JpaRepository<TsstUser, String> {

	@Query(value = "SELECT    COUNT(*) AS TOTAL_REGIS ,DATE_FORMAT(INS_DT ,\"%M\") AS START_MONTH, SUM(PRICE) AS TOTAL_PRICE\r\n"
			+ "			FROM  data_delivery.GEN_LICENSE_MGT WHERE (:fromDate is null or INS_DT>=:fromDate) OR (:toDate is null or INS_DT<=:toDate)  GROUP BY  MONTH(INS_DT)", nativeQuery = true)
	List<Map<String, Object>> getRecordByMonth(
			@Param("fromDate") String fromDate,
			@Param("toDate") String toDate);
}
