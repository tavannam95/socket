package com.a2m.gen.repository.common;

import java.sql.SQLException;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TsstUser;

@Repository
public interface ComSeqRepository extends CrudRepository<TsstUser, String>{
	
	@Query(value = "CALL proc_gen_id(:seqName, @result);", nativeQuery = true)
	Object setSeq(@Param("seqName") String seqName) throws SQLException;
	
	@Query(value = "SELECT @result", nativeQuery = true)
	String getSeq() throws SQLException;
}
