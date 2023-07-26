package com.a2m.gen.repository.sys.sys0101;

import java.util.List;
import java.util.Map;

import com.a2m.gen.dto.TsstMenuDto;
import com.a2m.gen.entities.TsstMenu;

public interface Sys0101Repository {
	public List<TsstMenu> getAllMenu();
	public String getMaxMenuId(String menuId);
	public Integer getMaxOrdNo();
	public int deleteTsstRoleMenu(String menuId);
	int updateTsstMenu(TsstMenuDto tsstMenu);
	public List<TsstMenu> getMenuByUser(Map<String, Object> args);
	int activeOrBlockMenu(Map<String, Object> args);
	List<TsstMenu> searchTsstMenu(Map<String, Object> args);
}
