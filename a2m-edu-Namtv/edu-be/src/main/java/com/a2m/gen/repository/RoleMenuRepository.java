package com.a2m.gen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a2m.gen.entities.TsstRoleMenu;
import com.a2m.gen.entities.TsstRoleMenuId;

public interface RoleMenuRepository extends JpaRepository<TsstRoleMenu, TsstRoleMenuId>{
	void deleteByRoleId(String roleId);
	void deleteByMenuId(String menuId);
}
