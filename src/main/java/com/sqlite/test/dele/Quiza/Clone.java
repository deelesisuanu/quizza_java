package com.sqlite.test.dele.Quiza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.sqlite.test.dele.Quiza.model.Helper;
import com.sqlite.test.dele.Quiza.pojo.Quiz;

import controllers.Services;
import controllers.Tables;

public class Clone {

	private Services services;
	private Helper helper;
	private Scanner scanner;

	public Clone() {
		services = new Services();
		helper = new Helper();
	}

	public void runAp() {
		scanner = new Scanner(System.in);
		helper.outputMessage("Welcome to " + helper.getAppName(), false);
		helper.outputMessage(">>>>>>>>>>>>>>>>>>", false);
		helper.outputMessage("What would you like to do today?", true);
		helper.outputMessage("1.\tManage " + helper.getAppName() + " Features", true);
		helper.outputMessage("2.\tSelect " + helper.getAppName() + " Quiz.", true);
		try {
			int optionSelected = scanner.nextInt();
			if (optionSelected == 1) {
				priveledges();
			}
			else if (optionSelected == 2) {
				takeQuiz();
			}
			else {}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void priveledges() {
		scanner = new Scanner(System.in);
		helper.outputMessage("\nEnter Your Access Token", true);
		try {
			String optionSelected = scanner.nextLine();
			boolean equalsCorrectly = ( optionSelected.equals(helper.accessToken()));
			if(equalsCorrectly) {
				/**
				 * Manage Quizzes
				 * Manage Question
				 */
				helper.outputMessage("Select Option:", true);
				helper.outputMessage("1.\tCreate " + helper.getAppName() + " Quiz", true);
				helper.outputMessage("2.\tView " + helper.getAppName() + " Quizzes", true);
				try {
					int selectedOption = scanner.nextInt();
					if (selectedOption == 1) {
						createQuiz();
					}
					else if (selectedOption == 2) {
						viewQuizzes();
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void takeQuiz() {}
	
	public void createQuiz() {
		scanner = new Scanner(System.in);
		helper.outputMessage("Enter Quiz Name:", true);
		String quizName = scanner.nextLine();
		if(quizName.isEmpty()) {
			helper.outputMessage("Quiz name is required", false);
			createQuiz();
			return;
		}
		helper.outputMessage("Enter Number of Quiz Questions:", true);
		int numberAccepted = scanner.nextInt();
		String numQuestions = numberAccepted + "";
		if(numQuestions.isEmpty()) {
			helper.outputMessage("Number of Quiz Questions is required", false);
			createQuiz();
			return;
		}
		String code = helper.randomAlphaNumeric(8);
		String currentTime = helper.currentTimeStamp();
		currentTime = currentTime.replace(" ", "_");
		Quiz quiz = new Quiz(code, quizName, numQuestions, currentTime);
		try {
			int isCreated = services.createQuiz(quiz);
			if(isCreated <= 0) helper.outputMessage("There was a problem with this transaction", true);
			else helper.outputMessage("Quiz Created Successfully", false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void viewQuizzes() {
		try {
			String[] cols = {"*"};
			ResultSet result = services.listOptions(Tables.quiz.name(), cols, false, null);
			int index = 1;
			while(result.next()) {
				
				String code = result.getString("code");
				String name = result.getString("name");
				String numQuestions = result.getString("num_questions");
				String timestamp = result.getString("timestamp");
				
				helper.outputMessage(index + ":\t Name: " + name
						+ "\tCode: " + code, true);
				
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
