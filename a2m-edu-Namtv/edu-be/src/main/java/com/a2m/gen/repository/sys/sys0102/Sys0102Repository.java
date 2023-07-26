package com.a2m.gen.repository.sys.sys0102;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.a2m.gen.dto.TsstUserRoleDto;
import com.a2m.gen.entities.TsstRole;
import com.a2m.gen.entities.TsstRoleMenu;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserRole;

public interface Sys0102Repository {
	
	List<TsstRole> searchTsstRole(Map<String, Object> arg) throws SQLException;
    
    int deleteTsstRoles(String ids) throws SQLException;
    
    List<TsstUserRole> searchTsstUserRole(Map<String, Object> arg) throws SQLException;
    
    TsstUserRole getTsstUserRole(TsstUserRoleDto userRole) throws SQLException;
    
    int updateTsstUserRole(TsstUserRole userRole) throws SQLException;
    
    int insertTsstUserRole(TsstUserRoleDto userRole) throws SQLException;
    
    int deleteTsstUserRole(TsstUserRoleDto userRole) throws SQLException;
    
    List<TsstRoleMenu> searchTsstRoleMenu(Map<String, Object> arg) throws SQLException;
    
    TsstRoleMenu getTsstRoleMenu(TsstRoleMenu arg) throws SQLException;
    
    int updateTsstRoleMenu(TsstRoleMenu arg) throws SQLException;
    
    int insertTsstRoleMenu(TsstRoleMenu arg) throws SQLException;
    
    int deleteAccessTokenByRoleId(Map<String, Object> arg) throws SQLException;
    
    int deleteAccessTokenByUserId(Map<String, Object> arg) throws SQLException;
    
    List<TsstUser> getAllUser(Map<String, Object> arg);
    
	int updateTsstRole(TsstRole tsstRole) throws SQLException;;
    
}
