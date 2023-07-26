package com.a2m.gen.repository.community;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.community.PostEntity;
import com.a2m.gen.models.community.PostModel;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

	@Query(value = "SELECT post.*"

			+ "	FROM COMMUNITY_POST post"		
			+ "	WHERE post.POST_CONTENT = :#{#search.postContent}", nativeQuery = true)	
	List<PostEntity> searchPost(@Param("search") PostModel search);

}

