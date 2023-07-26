package com.a2m.gen.repository.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TccoStd;

@Repository
public interface TccoSTDRepository extends JpaRepository<TccoStd, String> {
	@Query(value = "SELECT * FROM TCCO_STD WHERE UP_COMM_CD = ?1 AND USE_YN = 'Y'", nativeQuery = true)
	List<TccoStd> findByUpCommCd(String upCommCd);
	
	Optional<TccoStd> findByCommCd(String commCd);
	
	@Query(value = "SELECT MAX(COMM_CD) FROM TCCO_STD WHERE :upCommCd is null OR UP_COMM_CD = :upCommCd", nativeQuery = true)
	String getMaxCommCd(@Param("upCommCd") String upCommCd);
	
	@Query(value = "SELECT MAX(com.COMM_CD) FROM TCCO_STD com WHERE com.UP_COMM_CD is NULL or com.UP_COMM_CD ='' ", nativeQuery = true)
	String getMaxCommCd2();
	
	@Query(value = "SELECT COUNT(*) FROM TCCO_STD WHERE COMM_CD = :commCd",nativeQuery = true)
	int countCommCd(@Param("commCd")String commCd);
	
	@Query(value = "DELETE FROM TCCO_STD WHERE COMM_CD = :commCd", nativeQuery = true)
	void deleteByCommCd(@Param("commCd")String commCd);

    @Query(value = "SELECT COMM_NM FROM TCCO_STD WHERE COMM_CD = :commCd",nativeQuery = true)
    String getCommNmByCommCd(@Param("commCd") String commCd);
    
}
