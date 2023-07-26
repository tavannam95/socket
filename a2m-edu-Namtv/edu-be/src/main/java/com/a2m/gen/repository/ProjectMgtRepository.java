package com.a2m.gen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.a2m.gen.entities.ProjectMgt;

public interface ProjectMgtRepository extends JpaRepository<ProjectMgt, String>{
	
	@Query(value = "SELECT * FROM GEN_PROJECT_MGT WHERE CREATED_BY = ?1", nativeQuery = true)
	List<ProjectMgt> findByCreatedBy(String userUid);
	
	Optional<ProjectMgt> findByProjectId(String projectId);
	
}
