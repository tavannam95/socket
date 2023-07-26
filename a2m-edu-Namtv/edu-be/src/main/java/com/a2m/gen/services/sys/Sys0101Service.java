package com.a2m.gen.services.sys;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.dto.TsstMenuDto;
import com.a2m.gen.entities.TsstMenu;
import com.a2m.gen.repository.TsstMenuRepository;
import com.a2m.gen.repository.sys.sys0101.Sys0101Repository;

@Service
public class Sys0101Service {
	
	private DecimalFormat myFormatter = new DecimalFormat("00");
	
	@Autowired
	private Sys0101Repository sys0101Repo;
	@Autowired
	private TsstMenuRepository tsstMenuRepo;
	
	@Autowired	
	private ModelMapper modelMapper;
	
	public List<TsstMenu> getAllMenu(){
		return tsstMenuRepo.findAll();
	}
	
	public List<TsstMenu> getMenuByUser(Map<String, Object> arg) throws SQLException {
		return sys0101Repo.getMenuByUser(arg);
	}
	
	public List<TsstMenu> search(Map<String, Object> arg) throws SQLException {
		return sys0101Repo.searchTsstMenu(arg);
	}
	
	public TsstMenu addMenu(TsstMenuDto arg) throws SQLException{
		String menuId = getMenuId(arg);
		arg.setMenuId(menuId);
		arg.setCreatedBy(arg.getSessUserId());
		Integer maxOrdNo = getMaxOrdNo();
		maxOrdNo = maxOrdNo == null ? 1 : maxOrdNo+1;
		arg.setOrdNo(maxOrdNo);
		if(!StringUtils.isNotEmpty(arg.getUpMenuId())) {
			arg.setLev(1);
		}
		else {
			arg.setLev(2);
		}
		TsstMenu tsstMenu = modelMapper.map(arg, TsstMenu.class);
		return tsstMenuRepo.save(tsstMenu);
	}
	
	public int updateMenu(TsstMenuDto arg) throws SQLException{
//		TsstMenu tsstMenu = modelMapper.map(arg, TsstMenu.class);
		return sys0101Repo.updateTsstMenu(arg);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteMenu(String meuId) throws SQLException {
		sys0101Repo.deleteTsstRoleMenu(meuId);
		return tsstMenuRepo.deleteByMenuId(meuId);
	}
	
	public String getMenuId(TsstMenuDto arg) throws SQLException{
		String upMenuId = arg.getUpMenuId();
		String menuId = "";
		if(StringUtils.isNotEmpty(upMenuId)) {
			String maxMenuId = getMaxMenuId(upMenuId);
			if(!StringUtils.isNotEmpty(maxMenuId)){
//				if cannot find upMenu then throw SQLException
				throw new SQLException();
			}
			else if(maxMenuId.length() == 6) {
				menuId = upMenuId + "_01";
			}
			else {
				String temp = maxMenuId.substring(7, 9);
				menuId = upMenuId + "_" +  myFormatter.format(Integer.parseInt(temp)+1);
			}
		}
		else {
			String maxMenuId = getMaxMenuId(null);
			if(StringUtils.isNotEmpty(maxMenuId)){
				menuId = "MNU" + "_" +  myFormatter.format(Integer.parseInt(maxMenuId.substring(4, 6))+1);
			}
			else {
				menuId = "MNU_01";
			}
		}
		return menuId;
	}
	
	public String getMaxMenuId(String menuId) throws SQLException{
		return sys0101Repo.getMaxMenuId(menuId);
	};
	
	public Integer getMaxOrdNo() throws SQLException{
		return sys0101Repo.getMaxOrdNo();
	};
	
	public int activeOrBlockMenu(Map<String, Object> arg) throws SQLException {
		return sys0101Repo.activeOrBlockMenu(arg);
	}
}
