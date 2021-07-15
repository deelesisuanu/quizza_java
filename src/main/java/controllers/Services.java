package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	public Quiz getQuiz(int findIndex) throws SQLException {
		Quiz quiz = new Quiz();
		String[] cols = {"*"};
		ResultSet result = listOptions(Tables.quiz.name(), cols, false, null);
		int index = 1;
		while(result.next()) {
			String code = result.getString("code");
			String name = result.getString("name");
			String numQuestions = result.getString("num_questions");
			String timestamp = result.getString("timestamp");
			if(index == findIndex) {
				quiz.setCode(code);
				quiz.setName(name);
				quiz.setNum_questions(numQuestions);
				quiz.setTimestamp(timestamp);
			}
			index++;
		}
		return quiz;
	}
	
	public ArrayList<Questions> allQuestions(String quizCode) throws SQLException {
		ArrayList<Questions> questions = new ArrayList<Questions>();
		String[] cols = {"*"};
		ResultSet result = listOptions(Tables.questions.name(), cols, false, null);
		while(result.next()) {
			String quiz_code = result.getString("quiz_code");
			String question = result.getString("question");
			question = question.replace("_", " ");
			String option1 = result.getString("option1");
			option1 = option1.replace("_", " ");
			String option2 = result.getString("option2");
			option2 = option2.replace("_", " ");
			String option3 = result.getString("option3");
			option3 = option3.replace("_", " ");
			String option4 = result.getString("option4");
			option4 = option4.replace("_", " ");
			String answer = result.getString("answer");
			answer = answer.replace("_", " ");
			Questions questionObj = new Questions(quiz_code, question, option1, option2, option3, option4, answer);
			questions.add(questionObj);
		}
		return questions;
	}
	
	public int createQuestion(Questions questions) throws SQLException {
		clear();
		map.put("quiz_code", questions.getQuiz_code());
		map.put("question", questions.getQuestion());
		map.put("option1", questions.getOption1());
		map.put("option2", questions.getOption2());
		map.put("option3", questions.getOption3());
		map.put("option4", questions.getOption4());
		map.put("answer", questions.getAnswer());
		arr.add(map);
		return model.insertToTable(Tables.questions.name(), arr);
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
