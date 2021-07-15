package com.sqlite.test.dele.Quiza.pojo;

public class Quiz {
	
	private String code, name, num_questions, timestamp;

	public Quiz(String code, String name, String num_questions, String timestamp) {
		super();
		this.code = code;
		this.name = name;
		this.num_questions = num_questions;
		this.timestamp = timestamp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum_questions() {
		return num_questions;
	}

	public void setNum_questions(String num_questions) {
		this.num_questions = num_questions;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
