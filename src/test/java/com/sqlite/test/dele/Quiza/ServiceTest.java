package com.sqlite.test.dele.Quiza;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sqlite.test.dele.Quiza.pojo.Questions;
import com.sqlite.test.dele.Quiza.pojo.Quiz;

import controllers.Services;

public class ServiceTest {
	
	private static Services services;
	
	@BeforeClass
	public static void setup() {
		services = new Services();
	}
	
	@Test
	public void createQuiz() throws SQLException {
		Quiz quiz = new Quiz("212dsds", "LearnUnit", "5", "June 1");
		assertEquals(1, services.createQuiz(quiz));
	}
	
	@Test
	public void createQuestion() throws SQLException {
		Questions question = new Questions("212dsds", "What is Car", 
				"Vehicle", "River", "Atmosphere", 
				"Rubber", "Vehicle");
		assertEquals(1, services.createQuestion(question));
	}

}
