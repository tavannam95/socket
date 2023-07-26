package com.a2m.gen.models.course;

public class AnswerCorrectLabelDefault {
	private String label;
	private String value;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public AnswerCorrectLabelDefault() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AnswerCorrectLabelDefault(String value) {
		this.label = "đáp án " +value;
		this.value = value;
	}
}