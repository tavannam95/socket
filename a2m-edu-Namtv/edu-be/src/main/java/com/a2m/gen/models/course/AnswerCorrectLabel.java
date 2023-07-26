package com.a2m.gen.models.course;

public class AnswerCorrectLabel {
	private Long index;
	private String label;
	
	public AnswerCorrectLabel(Long index, String label) {
		this.index = index;
		this.label = label;
	}
	public AnswerCorrectLabel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}	
}
