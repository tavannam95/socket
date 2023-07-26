package com.a2m.gen.services.gen;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.ProjectMgt;
import com.a2m.gen.entities.TargetMgt;
import com.a2m.gen.entities.TccoFile;
import com.a2m.gen.repository.ProjectMgtRepository;
import com.a2m.gen.repository.TableInfoRepository;
import com.a2m.gen.repository.common.TccoFileRepository;
import com.a2m.gen.repository.gen.gen0101.Gen0101Repository;
import com.a2m.gen.repository.gen.gen0501.Gen0501Repository;
import com.a2m.gen.services.common.ComSeqService;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CommonFileUtils;

@Service
public class Gen0501Service {
	@Autowired
	Gen0501Repository gen0501Repository;
	@Autowired
	Gen0101Repository gen0101Repository;
	@Autowired
	TableInfoRepository tableInfoRepository;
	@Autowired
	private ComSeqService comSeqService;
	@Autowired
	private ProjectMgtRepository projectRepo;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TccoFileRepository tccoFileRepository;

	public List<ProjectMgt> getListProject(String userUID) {
		return projectRepo.findByCreatedBy(userUID);
	}

	public ProjectMgt addNewProject(ProjectMgt project) throws Exception {
		project.setProjectId(comSeqService.getSeq("SEQ_PROJECT_ID"));
		project.setTopic(project.getProjectId());
		project.setStatus(CommonContants.PROJECT_STATUS_NEW);
		project.setCreatedDate(new Date());
		project.setCreatedBy(commonService.getUserUid());
		projectRepo.save(project);

		return project;
	}

	public boolean checkTargetExists(String projectId) {
		long count = gen0101Repository.countByProjectId(projectId);
		if (count == 0) {
			return false;
		}
		return true;
	}

	@Transactional(rollbackFor = Exception.class)
	public ProjectMgt saveProject(ProjectMgt project) {
		List<TargetMgt> listTargetUpdate = project.getTargets();
		for (TargetMgt targetMgt : listTargetUpdate) {
			targetMgt.setProject(project);
		}
		return projectRepo.save(project);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteProject(String projectId, String dataFilePath) {
		TccoFile tccoFile = tccoFileRepository.findByAtchFleSeq(dataFilePath);
		List<TargetMgt> listTarget = gen0101Repository.findByProjectId(projectId);
		for (TargetMgt target: listTarget) {
			tableInfoRepository.deleteByTargetId(target.getTargetId());
		}
		projectRepo.deleteById(projectId);
		tccoFileRepository.deleteById(dataFilePath);
		CommonFileUtils.deleteFile(tccoFile.getNewFleNm());
	}

	public Map<String, Object> search(Map<String, Object> search) {
		Map<String, Object> result = new HashMap<>();
		result.put("count", gen0501Repository.count(search));
		result.put("data", gen0501Repository.search(search));
		return result;
	}

}
