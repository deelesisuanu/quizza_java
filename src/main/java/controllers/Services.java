package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;

import com.sqlite.test.dele.Quiza.model.*;
import com.sqlite.test.dele.Quiza.pojo.Questions;
import com.sqlite.test.dele.Quiza.pojo.Quiz;

public class Services {
	
	/**
	 * 
	 * CREATE A QUIZ
	 * LIST QUIZZES
	 * CREATE A QUESTION AND ATTACH TO A QUIZ
	 * REMOVE QUESTION
	 * REMOVE QUIZ
	 * 		// -> QUESTIONS ***
	 * 
	 * RETRIEVE QUIZ AND ITS QUESTIONS
	 * 
	 */
	
	private Model model;
	private Map<String, String> map;
	private JSONArray arr;
	private Helper helper;
	
	public Services() {
		model = new Model("quizza");
		map = new HashMap<String, String>();
		arr = new JSONArray();
		createAll();
	}
	
	public int createQuiz(Quiz quiz) throws SQLException {
		clear();
		map.put("code", quiz.getCode());
		map.put("name", quiz.getName());
		map.put("num_questions", quiz.getNum_questions());
		map.put("timestamp", quiz.getTimestamp());
		arr.add(map);
		return model.insertToTable(Tables.quiz.name(), arr);
	}
	
	public ResultSet listOptions(String table, String[] selectCols, boolean where, JSONArray whereArray) throws SQLException {
		return model.getTableData(table, selectCols, where, whereArray);
	}
	
	public void createQuestion(Questions questions) {
		clear();
		map.put("quiz_code", questions.getQuiz_code());
		map.put("question", questions.getQuestion());
		map.put("option1", questions.getOption1());
		map.put("option2", questions.getOption2());
		map.put("option3", questions.getOption3());
		map.put("option4", questions.getOption4());
		map.put("answer", questions.getAnswer());
		arr.add(map);
		try {
			int isCreated = model.insertToTable(Tables.questions.name(), arr);
			if(isCreated <= 0) helper.outputMessage("There was a problem with this transaction", true);
			else helper.outputMessage("Question Created Successfully", false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeQuiz() {
	}
	
	public void removeQuestion() {
	}
	
	public void removeAll() {
	}
	
	public void listAll() {
	}
	
	public void createAll() {
		createQuizTable();
		createQuestionTable();
	}
	
	public void createQuizTable() {
		clear();
		map.put("code", "TEXT NOT NULL");
		map.put("name", "TEXT NOT NULL");
		map.put("num_questions", "TEXT NOT NULL");
		map.put("timestamp", "TEXT NOT NULL");
		arr.add(map);
		try {
			model.createTable(Tables.quiz.name(), arr);
		} catch (SQLException e) {
			helper.outputMessage(e.getMessage(), true);
			e.printStackTrace();
		}
	}
	
	public void createQuestionTable() {
		clear();
		map.put("quiz_code", "TEXT NOT NULL");
		map.put("question", "TEXT NOT NULL");
		map.put("option1", "TEXT NOT NULL");
		map.put("option2", "TEXT NOT NULL");
		map.put("option3", "TEXT NOT NULL");
		map.put("option4", "TEXT NOT NULL");
		map.put("answer", "TEXT NOT NULL");
		arr.add(map);
		try {
			model.createTable(Tables.questions.name(), arr);
		} catch (Exception e) {
			helper.outputMessage(e.getMessage(), true);
			e.printStackTrace();
		}
	}
	
	public void clear() {
		arr.clear();
		map.clear();
	}

}
