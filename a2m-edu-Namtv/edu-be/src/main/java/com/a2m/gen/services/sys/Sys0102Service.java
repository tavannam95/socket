package com.a2m.gen.services.sys;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.dto.TsstRoleDto;
import com.a2m.gen.dto.TsstUserDto;
import com.a2m.gen.dto.TsstUserRoleDto;
import com.a2m.gen.entities.TsstRole;
import com.a2m.gen.entities.TsstRoleMenu;
import com.a2m.gen.entities.TsstUserRole;
import com.a2m.gen.mappers.TsstRoleMapper;
import com.a2m.gen.mappers.TsstUserMapper;
import com.a2m.gen.repository.RoleMenuRepository;
import com.a2m.gen.repository.TsstRoleRepository;
import com.a2m.gen.repository.sys.sys0102.Sys0102Repository;

@Service
public class Sys0102Service {
	@Autowired
	private Sys0102Repository sys0102Repo;
	@Autowired
	private TsstRoleRepository tsstRoleRepo;
	@Autowired(required=false)
	private TsstRoleMapper roleMapper;
	
	@Autowired(required = false)
	private TsstUserMapper userMapper;
	@Autowired
	private RoleMenuRepository roleMenuRepo;
	
	private DecimalFormat myFormatter = new DecimalFormat("000");

    public List<TsstRoleDto> searchTsstRole(Map<String, Object> arg) throws SQLException{
		return roleMapper.toDto(sys0102Repo.searchTsstRole(arg));
	}

	public TsstRoleDto getTsstRole(String id) throws SQLException{
		Optional<TsstRole> tsstRole = tsstRoleRepo.findById(id)
;
		if (!tsstRole.isEmpty()) {
			return roleMapper.toDto(tsstRole.get());
		}
		return null;
		
	}

	public void deleteTsstRole(String id) throws SQLException{
		tsstRoleRepo.deleteById(id)
;
	}

	public boolean insertTsstRole(TsstRoleDto arg) throws SQLException{
		arg.setRoleId(getMaxRoleId());
		tsstRoleRepo.save(roleMapper.toEntity(arg));
		return true;
	}

	public boolean updateTsstRole(TsstRoleDto arg) throws SQLException{
		//tsstRoleRepo.save(roleMapper.toEntity(arg));
		sys0102Repo.updateTsstRole(roleMapper.toEntity(arg));
		return true;
	}

	public String getMaxRoleId() throws SQLException{
		String maxRoleId = tsstRoleRepo.getMaxRoleId();
		maxRoleId = maxRoleId.replaceAll("R", "");
		String newRoleId = "R" + myFormatter.format((Integer.parseInt(maxRoleId)+1));
		return newRoleId;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteTsstRoles(String ids) throws SQLException{
		List<String> idList = new ArrayList<String>(Arrays.asList(ids.split(",")));
		for (String id : idList) {
			roleMenuRepo.deleteByRoleId(id)
;
			tsstRoleRepo.deleteByRoleId(id)
;
		}
//		tsstRoleRepo.deleteRoleMenu(ids);
//		tsstRoleRepo.deleteTsstRoles(ids);
	}
	
	public List searchTsstUserRole(Map arg) throws SQLException{
		return sys0102Repo.searchTsstUserRole(arg);
	}
	
	public List<TsstUserDto> getAllUser(Map<String, Object> arg) {
		return userMapper.toDto(sys0102Repo.getAllUser(arg));
	}
	
	public TsstUserRole getTsstUserRole(TsstUserRoleDto userRole) throws SQLException{
		return sys0102Repo.getTsstUserRole(userRole);
	}
//	
//	public int updateTsstUserRole(TsstUserRole userRole) throws SQLException{
//		return sys0102Repo.updateTsstUserRole(userRole);
//	}
//	
	public int insertTsstUserRole(TsstUserRoleDto userRole) throws SQLException{
		return sys0102Repo.insertTsstUserRole(userRole);
	}
	
	public int deleteTsstUserRole(TsstUserRoleDto userRole) throws SQLException{
		return sys0102Repo.deleteTsstUserRole(userRole);
	}
	
	public List searchTsstRoleMenu(Map<String, Object> arg) throws SQLException{
		return sys0102Repo.searchTsstRoleMenu(arg);
	}
    
	public TsstRoleMenu getTsstRoleMenu(TsstRoleMenu arg) throws SQLException{
		return sys0102Repo.getTsstRoleMenu(arg);
	}
	
	public void updateTsstRoleMenu(TsstRoleMenu arg) throws SQLException{
		roleMenuRepo.save(arg);
	}
    
	public void insertTsstRoleMenu(TsstRoleMenu arg) throws SQLException{
		roleMenuRepo.save(arg);
	}
}