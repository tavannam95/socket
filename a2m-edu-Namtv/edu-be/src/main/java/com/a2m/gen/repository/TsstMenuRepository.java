package com.a2m.gen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a2m.gen.entities.TsstMenu;

@Repository
public interface TsstMenuRepository extends JpaRepository<TsstMenu, String>{
	int deleteByMenuId(String menuId);
}
