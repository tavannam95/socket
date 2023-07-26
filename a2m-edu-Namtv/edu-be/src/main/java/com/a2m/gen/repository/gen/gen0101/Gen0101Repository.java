package com.a2m.gen.repository.gen.gen0101;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TargetMgt;

@Repository
public interface Gen0101Repository extends JpaRepository<TargetMgt, String> {
	@Query(value="SELECT * FROM GEN_TARGET_MGT WHERE PROJECT_ID = :projectId",nativeQuery=true)
	List<TargetMgt> findByProjectId(@Param("projectId") String projectId);

	@Query(value = "DELETE FROM GEN_TARGET_MGT WHERE PROJECT_ID = :projectId", nativeQuery = true)
	Optional<Integer> deleteByProjectId(@Param("projectId") String projectId);

	@Query(value = "SELECT COUNT(*) FROM GEN_TARGET_MGT WHERE PROJECT_ID = :projectId", nativeQuery = true)
	long countByProjectId(@Param("projectId") String projectId);

//	Paging
	@Query(value = "SELECT * FROM GEN_TARGET_MGT WHERE (:userUid is null OR USER_UID = :userUid)"
			+ "  AND (:targetName is null OR UPPER(TARGET_NAME) LIKE UPPER(concat('%',:targetName,'%')))"
			+ "  AND (:status is null OR STATUS= :status)"
			, nativeQuery = true)
	Page<TargetMgt> findByUserUidAndTargetNameAndStatus(@Param("userUid") String userUid,
			@Param("targetName") String targetName, @Param("status") String status, Pageable pageable);

}
