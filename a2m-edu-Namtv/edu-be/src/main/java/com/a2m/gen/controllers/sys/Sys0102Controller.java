package com.a2m.gen.controllers.sys;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.ValidationCode;
import com.a2m.gen.dto.TsstRoleDto;
import com.a2m.gen.dto.TsstUserRoleDto;
import com.a2m.gen.dto.request.SaveRoleRequest;
import com.a2m.gen.entities.TsstRoleMenu;
import com.a2m.gen.entities.TsstUserRole;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.sys.Sys0102Service;
import com.a2m.gen.utils.GenStringUtils;

@RestController
@RequestMapping(value = "api/sys/sys0102")
public class Sys0102Controller {
	
	@Autowired
	private Sys0102Service sys0102Service;
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/search")
	public List<TsstRoleDto> search(@RequestParam Map<String, Object> arg) throws SQLException {
		return sys0102Service.searchTsstRole(arg);
	}
	
	@GetMapping("/search/user-role")
	public List searchTsstUserRole(@RequestParam Map<Object, Object> arg) throws SQLException {
		List aaa = sys0102Service.searchTsstUserRole(arg);
		return sys0102Service.searchTsstUserRole(arg);
	}
	

	@PostMapping("/save")
	@Transactional(rollbackFor = { Exception.class })
	public Object save(@RequestBody @Valid SaveRoleRequest arg) throws Exception {
		String userUid = commonService.getUserUid();
		arg.setSessUserId(userUid);
		List<TsstRoleDto> grid = arg.getTsstRole();
		Date currentDate = new Date();
		for (TsstRoleDto tsstRoleRequest : grid) {
			if(StringUtils.isNotEmpty(tsstRoleRequest.getRoleId())) {
				if("U".equals(tsstRoleRequest.getState())) {
					tsstRoleRequest.setUpdatedBy(arg.getSessUserId());
					tsstRoleRequest.setUpdatedDate(currentDate);
					sys0102Service.updateTsstRole(tsstRoleRequest);
				}
			}
			else {
				tsstRoleRequest.setCreatedBy(arg.getSessUserId());
				tsstRoleRequest.setCreatedDate(currentDate);
				sys0102Service.insertTsstRole(tsstRoleRequest);
			}
		}
		if(StringUtils.isNotEmpty(arg.getIdsDel())) {
			String escapedXml = StringEscapeUtils.escapeXml10(arg.getIdsDel());
			if(!escapedXml.equals(arg.getIdsDel())) {
				throw new Exception("data invalid");
			}
			this.sys0102Service.deleteTsstRoles(arg.getIdsDel());
//			this.sys0102Service.deleteTsstRoles(GenStringUtils.getForInQuery(arg.getIdsDel(), ","));
		}
		Map result = new HashMap<String, String>();
		result.put("RESULT", "Y");
		return result;

	}
	
	@GetMapping(value = "getAllUser")
	public List getAllUser(@RequestParam Map<String, Object> arg) {
		return sys0102Service.getAllUser(arg);
	}
	
	@PostMapping("/saveUserRole")
	@Transactional(rollbackFor = { Exception.class })
	public Object saveUserRole(@RequestBody @Valid List<TsstUserRoleDto> arg) throws SQLException {
		if(arg.size() == 0) {
			return null;
		}
		
		for (TsstUserRoleDto item : arg) {
			if(item.isChecked()) {
				TsstUserRole userRole = sys0102Service.getTsstUserRole(item);
				if (userRole == null) {
					sys0102Service.insertTsstUserRole(item);
				}
			}else {
				sys0102Service.deleteTsstUserRole(item);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("RESULT", "Y");
		return result;
	}
	
	@GetMapping("/search/menu-role")
	public List searchTsstRoleMenu(@RequestParam Map<String, Object> arg) throws SQLException {
		return sys0102Service.searchTsstRoleMenu(arg);
	}
	
	@PostMapping("/save/menu-role")
	@Transactional(rollbackFor = { Exception.class })
	public Object saveMenuRole(@RequestBody @Valid List<TsstRoleMenu> arg) throws Exception {
		String userUid = commonService.getUserUid();
		for (TsstRoleMenu item : arg) {
			TsstRoleMenu getMenuRole = sys0102Service.getTsstRoleMenu(item);
			if(getMenuRole != null) {
				item.setCreatedBy(userUid);
				sys0102Service.updateTsstRoleMenu(item);
			}
			else {
				item.setCreatedBy(userUid);
				sys0102Service.insertTsstRoleMenu(item);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("RESULT", "Y");
		return result;
	}
}
