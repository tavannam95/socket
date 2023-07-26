package com.a2m.gen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TableInfo;

@Repository
public interface TableInfoRepository extends JpaRepository<TableInfo, Long> {
	@Query(value = "DELETE FROM GEN_TABLE_INFO WHERE TARGET_ID = ?1", nativeQuery = true)
	Optional<Integer> deleteByTargetId(String targetId);
}
