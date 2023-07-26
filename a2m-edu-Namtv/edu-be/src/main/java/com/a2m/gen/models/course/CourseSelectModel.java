/**
 * 
 */
package com.a2m.gen.models.course;

/**
 * @author ThanhNV
 *
 * 9 thg 5, 2023
 */
public class CourseSelectModel {
	private Long key;
	private String courseNm;
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public String getCourseNm() {
		return courseNm;
	}
	public void setCourseNm(String courseNm) {
		this.courseNm = courseNm;
	}
	
	public CourseSelectModel() {

	}
	
	public CourseSelectModel(Long key, String courseNm) {
		this.key = key;
		this.courseNm = courseNm;
	}
	
}
