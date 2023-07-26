package com.a2m.gen.repository.gen.gen0501;

import java.util.List;
import java.util.Map;

import com.a2m.gen.entities.ProjectMgt;

public interface Gen0501Repository {

	List<ProjectMgt> search(Map<String, Object> search);

	int count(Map<String, Object> search);

}
