package com.a2m.gen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.repository.TableInfoRepository;

@Service
public class GenTableInfoService {
	@Autowired
	private TableInfoRepository genTableInfoRepositoryl;
}
