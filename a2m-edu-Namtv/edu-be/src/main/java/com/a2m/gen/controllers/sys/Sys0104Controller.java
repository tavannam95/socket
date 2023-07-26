package com.a2m.gen.controllers.sys;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.constants.CommonContants.State;
import com.a2m.gen.dto.TccoStdDto;
import com.a2m.gen.dto.request.SaveTccoStdRequest;
import com.a2m.gen.entities.TccoStd;
import com.a2m.gen.mappers.TccoStdMapper;
import com.a2m.gen.services.sys.Sys0104Service;
import com.a2m.gen.utils.AjaxResult;

@RestController
@RequestMapping(value = "api/sys/sys0104")
public class Sys0104Controller {
	@Autowired
	private Sys0104Service sys0104Service;
	@Autowired(required = false)
	private TccoStdMapper tccoStdMapper;
	
	@PostMapping(value = "create")
	public ResponseEntity<?> create(@RequestBody TccoStd tccoStd){
		AjaxResult ajaxResult = new AjaxResult();
		try {
			sys0104Service.save(tccoStd);
			ajaxResult.setStatus(true);
			ajaxResult.setMessage("Save successful");
			return ResponseEntity.ok(ajaxResult);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setStatus(false);
			ajaxResult.setMessage("Save failed");
			return ResponseEntity.ok(ajaxResult);
		}
	}
	
	@PostMapping(value = "save")
	public ResponseEntity<?> save(@RequestBody @Valid SaveTccoStdRequest arg){
		AjaxResult ajaxResult = new AjaxResult();
		try {
			List<TccoStdDto> grid = arg.getTssoStdDtos();
			for (TccoStdDto tccoStdRequest : grid) {
				if (StringUtils.isNotEmpty(tccoStdRequest.getCommCd())) {
					if (State.UPDATE.getValue().equals(tccoStdRequest.getState())) {
						sys0104Service.update(tccoStdMapper.toEntity(tccoStdRequest));
					} else if (State.DELETE.getValue().equals(tccoStdRequest.getState())) {
						sys0104Service.deleteCommCd(tccoStdRequest.getCommCd());
					}
				} else {
					if (State.CREATE.getValue().equals(tccoStdRequest.getState())) {
						sys0104Service.save(tccoStdMapper.toEntity(tccoStdRequest));
					}

				}
			}
			
//			if (StringUtils.isNotEmpty(arg.getIdsDel())) {
//			String escapedXml = StringEscapeUtils.escapeXml10(arg.getIdsDel());
//			if (!escapedXml.equals(arg.getIdsDel())) {
//				throw new Exception("data invalid");
//			}
//			this.service.deleteTsstRoles(com.a2m.imware.common.StringUtils.getForInQuery(arg.getIdsDel(), ","));
//		}
			
			ajaxResult.setStatus(true);
			ajaxResult.setMessage("Save successful");
			return ResponseEntity.ok(ajaxResult);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setStatus(false);
			ajaxResult.setMessage("Save failed");
			return ResponseEntity.ok(ajaxResult);
		}
		

	}
	
	@GetMapping(value = "search")
	public ResponseEntity<?> search(@RequestParam Map<String, Object> params){
		return ResponseEntity.ok(sys0104Service.search(params));
	}
	
	@GetMapping(value = "delete")
	public ResponseEntity<?> delete(@RequestParam String commCd){
		AjaxResult ajaxResult = new AjaxResult();
		try {
			sys0104Service.deleteCommCd(commCd);
			ajaxResult.setStatus(true);
			ajaxResult.setMessage("Delete successful");
			return ResponseEntity.ok(ajaxResult);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setStatus(false);
			ajaxResult.setMessage("Delete failed");
			return ResponseEntity.ok(ajaxResult);
		}
	}
}
