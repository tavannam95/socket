package com.a2m.gen.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a2m.gen.entities.TccoStd;
import com.a2m.gen.services.common.TccoSTDService;

@RestController
@RequestMapping(value = "api/tcco-std")
public class TccoStdController {
	@Autowired
	private TccoSTDService service;
	
	@GetMapping(value = "/getCommCdByUpCommCd")
	public List<TccoStd> getCommCdByUpCommCd(@RequestParam String upCommCd) {
		return service.getCommCdByUpCommCd(upCommCd);
	}
	
	@GetMapping(value = "/getCommCdByCommCd")
	public Optional<TccoStd> getCommCdByCommCd(@RequestParam String commCd) {
		return service.getCommCdByCommCd(commCd);
	}

    @GetMapping(value = "/getCommNmByCommCd")
    public String getCommNmByCommCd(@RequestParam String commCd) {
        return service.getCommNmByCommCd(commCd);
    }
}
