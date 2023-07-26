package com.a2m.gen.controllers.sys;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.constants.CommonContants.State;
import com.a2m.gen.dto.TsstMenuDto;
import com.a2m.gen.dto.request.SaveMenuRequest;
import com.a2m.gen.entities.TsstMenu;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.services.sys.Sys0101Service;
import com.a2m.gen.utils.AjaxResult;

@RestController
@RequestMapping(value = "/api/sys/sys0101")
public class Sys0101Controller {
	@Autowired
	private Sys0101Service sys0101Service;
	@Autowired
	private CommonService commonService;
	
	@GetMapping("/getMenuByUser")
	public List<TsstMenu> getMenuByUser(@RequestParam Map<String, Object> arg) throws Exception {
		String userUid = commonService.getUserUid();
		arg.put("userUid", userUid);
		return sys0101Service.getMenuByUser(arg);
	}
	
	@GetMapping("/getAllMenu")
	public List<TsstMenu> getAllMenu() throws SQLException {
		return sys0101Service.getAllMenu();
	}
	
	@GetMapping("/search")
	public List<TsstMenu> search(@RequestParam Map<String, Object> arg) throws SQLException {
		return sys0101Service.search(arg);
	}
	
	@PostMapping("/create")
	@Transactional(rollbackFor = { SQLException.class, Exception.class })
	public Object addUser(@RequestBody @Valid TsstMenuDto arg) throws Exception {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			String userUid = commonService.getUserUid();
			arg.setSessUserId(userUid);
			arg.setCreatedDate(new Date());
			arg.setUseYn(CommonContants.USE_N);
			sys0101Service.addMenu(arg);
			ajaxResult.setMessage("Update Successful");
			ajaxResult.setStatus(true);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setMessage("Update failed");
			ajaxResult.setStatus(false);
		}
		
		return ResponseEntity.ok(ajaxResult);
	}

	@PutMapping("/update")
	@Transactional(rollbackFor = { SQLException.class, Exception.class })
	public ResponseEntity<?> updateMenu(@RequestBody @Valid TsstMenuDto arg) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			String userUid = commonService.getUserUid();
			arg.setSessUserId(userUid);
			sys0101Service.updateMenu(arg);
			ajaxResult.setMessage("Update Successful");
			ajaxResult.setStatus(true);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setMessage("Update failed");
			ajaxResult.setStatus(false);
		}
		
		return ResponseEntity.ok(ajaxResult);
	}
	
	@GetMapping("delete")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> deleteMenu(@RequestParam String menuId){
		AjaxResult ajaxResult = new AjaxResult();
		try {
			sys0101Service.deleteMenu(menuId);
			ajaxResult.setMessage("Delete Successful");
			ajaxResult.setStatus(true);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setMessage("Delete failed");
			ajaxResult.setStatus(false);
		}
		return ResponseEntity.ok(ajaxResult);
	}
	
	@PostMapping("/save")
	@Transactional(rollbackFor = { Exception.class })
	public Object save(@RequestBody @Valid SaveMenuRequest arg) throws Exception {  
		String userUid = commonService.getUserUid();
		arg.setSessUserId(userUid);
		List<TsstMenuDto> grid = arg.getTsstMenu();
		Date currentDate = new Date();
		for (TsstMenuDto menuRequest : grid) {
			if (StringUtils.isNotEmpty(menuRequest.getMenuId())) {
				if (State.UPDATE.getValue().equals(menuRequest.getState())) {
					menuRequest.setUpdatedBy(arg.getSessUserId());
					menuRequest.setUpdatedDate(currentDate);
					sys0101Service.updateMenu(menuRequest);
				} else if (State.DELETE.getValue().equals(menuRequest.getState())) {
					sys0101Service.deleteMenu(menuRequest.getMenuId());
				}
			} else {
				if (State.CREATE.getValue().equals(menuRequest.getState())) {
					menuRequest.setCreatedBy(arg.getSessUserId());
					menuRequest.setCreatedDate(currentDate);
					sys0101Service.addMenu(menuRequest);
				}

			}
		}
		if (StringUtils.isNotEmpty(arg.getIdsDel())) {
			String escapedXml = StringEscapeUtils.escapeXml10(arg.getIdsDel());
			if (!escapedXml.equals(arg.getIdsDel())) {
				throw new Exception("data invalid");
			}
//			this.service.deleteTsstRoles(com.a2m.imware.common.StringUtils.getForInQuery(arg.getIdsDel(), ","));
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("RESULT", "Y");
		return result;

	}
	
	@PutMapping("/update/active")
	@Transactional(rollbackFor = { SQLException.class, Exception.class })
	public ResponseEntity<?> active(@RequestBody @Valid Map<String, Object> arg) throws SQLException {
		AjaxResult ajaxResult = new AjaxResult();
		arg.put("useYn", "Y");
		int updated = sys0101Service.activeOrBlockMenu(arg);
		if (updated > 0) {
			ajaxResult.setMessage("Block Successfull");
			ajaxResult.setStatus(true);
			return ResponseEntity.ok(ajaxResult);
		}
		ajaxResult.setMessage("Block failed");
		ajaxResult.setStatus(true);
		return ResponseEntity.badRequest().body(ajaxResult);
	}

	@PutMapping("/update/block")
	@Transactional(rollbackFor = { SQLException.class, Exception.class })
	public ResponseEntity<?> block(@RequestBody @Valid Map<String, Object> arg) throws SQLException {
		AjaxResult ajaxResult = new AjaxResult();
		arg.put("useYn", "N");
		int updated = sys0101Service.activeOrBlockMenu(arg);
		if (updated > 0) {
			ajaxResult.setMessage("Block Successfull");
			ajaxResult.setStatus(true);
			return ResponseEntity.ok(ajaxResult);
		}
		ajaxResult.setMessage("Block failed");
		ajaxResult.setStatus(true);
		return ResponseEntity.badRequest().body(ajaxResult);
	}
	
	@GetMapping(value = "/getRoles")
	public List<String> getRoles() throws Exception{
		return commonService.getRoles();
	}
}
