package com.a2m.gen.services;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.a2m.gen.entities.edu.AemCourse;

public interface ProgressBarService {

    Map<String, Object> getNameProgress(Map<String, Object> args) throws Exception;
}
