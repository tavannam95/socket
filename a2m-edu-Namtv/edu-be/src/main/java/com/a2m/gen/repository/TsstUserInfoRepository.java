package com.a2m.gen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TsstUserInfo;

@Repository
public interface TsstUserInfoRepository extends JpaRepository<TsstUserInfo, Long>{
	
}
