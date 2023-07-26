package com.a2m.gen.services.gen;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dto.LicenseMgtDto;
import com.a2m.gen.entities.LicenseMgt;
import com.a2m.gen.repository.LicenseMgtRepository;
import com.a2m.gen.repository.gen.gen0601.Gen0601Repository;
import com.a2m.gen.services.common.CommonService;

@Service
public class Gen0601Service {
	@Autowired
	private Gen0601Repository gen0601Repo;
	@Autowired
	private LicenseMgtRepository licenseMgtRepo;
	@Autowired
	private CommonService commonService;
	
	public Map<String, Object> search(Map<String, Object> params) throws Exception{
		
		String userUid = commonService.getUserUid();
		params.put("userUid", userUid);
		List<LicenseMgtDto> licenses = gen0601Repo.search(params);
		int totalRecords = gen0601Repo.count(params);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", licenses);
		result.put("totalRecords", totalRecords);
		
		return result;
	}
	
	public LicenseMgt saveLicense(Map<String, Object> arg) throws Exception {
		Instant instant = Instant.now();
		LicenseMgt license = new LicenseMgt();
		int months = Integer.parseInt(arg.get("extendedTime").toString());
//		Instant expiredDate = instant.plus(Period.ofMonths(Integer.parseInt(arg.get("extendedTime").toString())));
		Instant expiredDate = instant.plus(months, ChronoUnit.DAYS);
		license.setProjectId(arg.get("projectId").toString());
		license.setStartDt(instant);
		license.setExpiredDt(expiredDate);
		license.setClientId(commonService.getUserUid());
		license.setInsDt(instant);
		license.setStatus(CommonContants.LICENSE_STATUS_NEW);
		return licenseMgtRepo.save(license);
	}
}
