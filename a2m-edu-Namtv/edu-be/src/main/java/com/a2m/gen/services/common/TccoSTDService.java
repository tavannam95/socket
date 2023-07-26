package com.a2m.gen.services.common;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.entities.TccoStd;
import com.a2m.gen.repository.common.TccoSTDRepository;

@Service
public class TccoSTDService {
	@Autowired
	private TccoSTDRepository tccoSTDRepository;

	public List<TccoStd> getCommCdByUpCommCd(String upCommCd) {
		return tccoSTDRepository.findByUpCommCd(upCommCd);
	}
	
	public Optional<TccoStd> getCommCdByCommCd(String commCd) {
		return tccoSTDRepository.findById(commCd);
	}
	public String getCommNmByCommCd(String commCd) {
        return tccoSTDRepository.getCommNmByCommCd(commCd);
    }
}
