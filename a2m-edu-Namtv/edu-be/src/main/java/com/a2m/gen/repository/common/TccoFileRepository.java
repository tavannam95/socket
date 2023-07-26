package com.a2m.gen.repository.common;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.TccoFile;

public interface TccoFileRepository extends JpaRepository<TccoFile, String> {

	@Query(value = "SELECT * FROM TCCO_FILE WHERE ATCH_FLE_SEQ = :atchFleSeq", nativeQuery = true)
	TccoFile findByAtchFleSeq(@Param("atchFleSeq") String atchFleSeq);
}
