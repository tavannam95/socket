package com.a2m.gen.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemCourse;

public interface ProgressBarRepository extends JpaRepository<AemCourse, String> {
    
        @Query(value = "SELECT COURSE_NM"
                + " ,(SELECT SUBJECT_NM FROM EAM_COURSE_SUBJECT s WHERE s.SUBJECT_ID = :SUBJECT_ID) as SUBJECT_NM"
                
                + " ,(SELECT CHAPTER_NM FROM EAM_COURSE_SUBJECT_CHAPTER c WHERE c.CHAPTER_ID = :CHAPTER_ID) as CHAPTER_NM"
                
                + " ,(SELECT LECTURES_NM FROM EAM_COURSE_SUBJECT_CHAPTER_LECTURES l WHERE l.LECTURES_ID = :LECTURES_ID) as LECTURE_NM"
                
                + " ,(SELECT QUIZ_NM FROM EAM_QUIZ q WHERE q.QUIZ_ID = :QUIZ_ID) as QUIZ_NM"
                
                +" FROM EAM_COURSE cou WHERE cou.COURSE_ID = :COURSE_ID",nativeQuery = true)
        
        Map<String, Object> getNameProgress(
            @Param("COURSE_ID") String COURSE_ID, 
            @Param("SUBJECT_ID") String SUBJECT_ID,
            @Param("CHAPTER_ID") String CHAPTER_ID, 
            @Param("LECTURES_ID") String LECTURES_ID,
            @Param("QUIZ_ID") String QUIZ_ID
        );
}
