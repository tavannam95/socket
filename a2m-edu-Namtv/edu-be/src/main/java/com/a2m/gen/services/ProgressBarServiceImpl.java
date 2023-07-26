package com.a2m.gen.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.repository.ProgressBarRepository;

@Service
public class ProgressBarServiceImpl implements ProgressBarService{
    
    @Autowired
	private ProgressBarRepository progressBarRepository;
    

    public  Map<String, Object> getNameProgress(Map<String, Object> args) throws Exception {
        String courseId = args.get("courseId").toString();
        String subjectId = args.get("subjectId").toString();
        String chapterId = args.get("chapterId").toString();
        String lectureId = args.get("lectureId").toString();
        String quizId = args.get("quizId").toString();

        return progressBarRepository.getNameProgress(courseId, subjectId, chapterId, lectureId, quizId);
    }
}
