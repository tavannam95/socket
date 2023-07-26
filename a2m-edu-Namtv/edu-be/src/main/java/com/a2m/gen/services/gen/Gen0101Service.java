package com.a2m.gen.services.gen;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.entities.ProjectMgt;
import com.a2m.gen.entities.TableInfo;
import com.a2m.gen.entities.TargetMgt;
import com.a2m.gen.repository.ProjectMgtRepository;
import com.a2m.gen.repository.TableInfoRepository;
import com.a2m.gen.repository.gen.gen0101.Gen0101Repository;

@Service
public class Gen0101Service {
	@Autowired
	Gen0101Repository gen0101Repository;
	@Autowired
	private ProjectMgtRepository projectRepo;
	@Autowired
	private TableInfoRepository tableInfoRepository;

	@Transactional(rollbackFor = Exception.class)
	public void saveTarget(TargetMgt targetMgt) throws Exception {
		Optional<ProjectMgt> project = projectRepo.findByProjectId(targetMgt.getProject().getProjectId());
		if (!project.isEmpty()) {
			targetMgt.setCreatedDate(new Date());
			targetMgt.setStatus(true);
			gen0101Repository.save(targetMgt);
			for (TableInfo tableInfo : targetMgt.getTableInfos()) {
				if (tableInfo.getTableId() != null) {
					tableInfo.setUpdatedDate(null);
				} else {
					tableInfo.setCreatedDate(new Date());
				}
				tableInfo.setTarget(targetMgt);
				tableInfoRepository.save(tableInfo);
			}
		} else {
			throw new Exception(String.format("Project ID %s not found", targetMgt.getProject().getProjectId()));
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateTarget(TargetMgt targetMgt) throws Exception {
		Optional<ProjectMgt> project = projectRepo.findByProjectId(targetMgt.getProject().getProjectId());
		if (!project.isEmpty()) {
			targetMgt.setUpdatedDate(new Date());
			gen0101Repository.save(targetMgt);
			for (TableInfo tableInfo : targetMgt.getTableInfos()) {
				if (tableInfo.getTableId() != null) {
					tableInfo.setUpdatedDate(null);
				} else {
					tableInfo.setCreatedDate(new Date());
				}
				tableInfo.setTarget(targetMgt);
				tableInfoRepository.save(tableInfo);
			}
		} else {
			throw new Exception(String.format("Project ID %s not found", targetMgt.getProject().getProjectId()));
		}
	}

	public void deleteTarget(String targetId) throws Exception {
		gen0101Repository.deleteById(targetId);
		tableInfoRepository.deleteByTargetId(targetId);
	}

	public Page<TargetMgt> search(Map<String, Object> target) throws SQLException {
		int page = Integer.parseInt(target.get("page").toString());
		int rows = Integer.parseInt(target.get("rows").toString());
		String userUid = ((target.get("userUid")) != null) ? target.get("userUid").toString() : null;
		String targetName = (target.get("targetName") != null) ? target.get("targetName").toString() : null;
		String status = (target.get("status") != null) ? target.get("status").toString() : null;

		Pageable pageable = PageRequest.of(page, rows);
		return gen0101Repository.findByUserUidAndTargetNameAndStatus(userUid, targetName, status, pageable);
	}
}
