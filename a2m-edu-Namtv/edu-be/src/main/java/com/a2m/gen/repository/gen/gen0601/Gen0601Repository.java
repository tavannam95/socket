package com.a2m.gen.repository.gen.gen0601;

import java.util.List;
import java.util.Map;

import com.a2m.gen.dto.LicenseMgtDto;

public interface Gen0601Repository {
	List<LicenseMgtDto> search(Map<String, Object> params);
	int count(Map<String, Object> params);
}
