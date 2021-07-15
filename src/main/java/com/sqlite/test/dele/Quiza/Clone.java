package com.sqlite.test.dele.Quiza;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.sqlite.test.dele.Quiza.model.Helper;
import com.sqlite.test.dele.Quiza.pojo.Questions;
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
			} else if (optionSelected == 2) {
				viewQuizzes(2);
			} else {
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void priveledges() {
		scanner = new Scanner(System.in);
		helper.outputMessage("\nEnter Your Access Token", true);
		try {
			String optionSelected = scanner.nextLine();
			boolean equalsCorrectly = (optionSelected.equals(helper.accessToken()));
			if (equalsCorrectly) {
				/**
				 * Manage Quizzes Manage Question
				 */
				helper.outputMessage("Select Option:", true);
				helper.outputMessage("1.\tCreate " + helper.getAppName() + " Quiz", true);
				helper.outputMessage("2.\tView " + helper.getAppName() + " Quizzes", true);
				try {
					int selectedOption = scanner.nextInt();
					if (selectedOption == 1) {
						createQuiz();
					} else if (selectedOption == 2) {
						viewQuizzes(1);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void createQuiz() {
		scanner = new Scanner(System.in);
		helper.outputMessage("Enter Quiz Name:", true);
		String quizName = scanner.nextLine();
		if (quizName.isEmpty()) {
			helper.outputMessage("Quiz name is required", false);
			createQuiz();
			return;
		}
		helper.outputMessage("Enter Number of Quiz Questions:", true);
		int numberAccepted = scanner.nextInt();
		String numQuestions = numberAccepted + "";
		if (numQuestions.isEmpty()) {
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
			if (isCreated <= 0)
				helper.outputMessage("There was a problem with this transaction", true);
			else
				helper.outputMessage("Quiz Created Successfully", false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void viewQuizzes(int switcher) {
		scanner = new Scanner(System.in);
		displayQuizOptions();
		helper.outputMessage("Please Choose any of the above:", false);
		int selectedIndex = scanner.nextInt();
		try {
			Quiz quiz = services.getQuiz(selectedIndex);
			String selectedName = quiz.getName();
			String selectedCode = quiz.getCode();
			if (selectedCode == null) {
				helper.outputMessage("Invalid Option Selected:", true);
				viewQuizzes(switcher);
				return;
			}
			if(switcher == 1) showQuestionOptions(selectedName, selectedCode);
			else if(switcher == 2) displayUserQuestions(selectedName, selectedCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void displayUserQuestions(String quizName, String quizCode) {
		try {
			ArrayList<Questions> questionList = services.allQuestions(quizCode);
			int loopIndex = 1;
			for (Questions question : questionList) {
				scanner = new Scanner(System.in);
				String userQuestion = question.getQuestion();
				String userOption1 = question.getOption1();
				String userOption2 = question.getOption2();
				String userOption3 = question.getOption3();
				String userOption4 = question.getOption4();
				String[] possibleAnswers = {userOption1, userOption2, userOption3, userOption4};
				String userAnswer = question.getAnswer();
				helper.outputMessage("Question: " + userQuestion
						+ "\n1:" + userOption1
						+ "\n2:" + userOption2
						+ "\n3:" + userOption3
						+ "\n4:" + userOption4, true);
				String userInput = scanner.nextLine();
				int actualUserInput = Integer.parseInt(userInput);
				actualUserInput -= 1;
				String userInputtedAnswer = helper.getIndexValue(actualUserInput, possibleAnswers);
				String showMessage = "YOU ARE ";
				showMessage += ( ( userInputtedAnswer.equals(userAnswer) ) ? "CORRECT" : "WRONG" );
				helper.outputMessage(showMessage, false);
				loopIndex++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void displayQuizOptions() {
		try {
			String[] cols = { "*" };
			ResultSet result = services.listOptions(Tables.quiz.name(), cols, false, null);
			int index = 1;
			while (result.next()) {
				String code = result.getString("code");
				String name = result.getString("name");
				String numQuestions = result.getString("num_questions");
				String timestamp = result.getString("timestamp");
				helper.outputMessage(index + ":\t Name: " + name + "\tCode: " + code + "\tNumber of Questions: "
						+ numQuestions + "\tDate Created: " + timestamp, true);
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showQuestionOptions(String quizName, String quizCode) {
		scanner = new Scanner(System.in);
		helper.outputMessage("Select Options for " + quizName, false);
		helper.outputMessage("1.\t Remove Quiz", false);
		helper.outputMessage("2.\t Edit Quiz", false);
		helper.outputMessage("3.\t Manage Questions", false);
		int selectedOpt = scanner.nextInt();
		switch (selectedOpt) {
			case 1:
				removeQuiz(quizCode);
				break;
			case 2:
				editQuiz(quizCode);
				break;
			case 3:
				manageQuestions(quizCode, quizName);
				break;
			default:
				break;
		}
	}
	
	private void removeQuiz(String quizCode) {}
	
	private void editQuiz(String quizCode) {}
	
	private void manageQuestions(String quizCode, String quizName) {
		scanner = new Scanner(System.in);
		helper.outputMessage("Manage Questions for " + quizName, false);
		helper.outputMessage("1.\t Add Questions", false);
		helper.outputMessage("2.\t View Questions", false);
		int selectedOpt = scanner.nextInt();
		if(selectedOpt == 1) addQuestion(quizName, quizCode);
		if(selectedOpt == 2) displayQuestionOptions();
	}
	
	private void displayQuestionOptions() {
		try {
			String[] cols = { "*" };
			ResultSet result = services.listOptions(Tables.questions.name(), cols, false, null);
			int index = 1;
			while (result.next()) {
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
				helper.outputMessage(index + ":\t Question: " + question 
						+ "\tAnswer: " + answer, true);
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addQuestion(String quizName, String quizCode) {
		scanner = new Scanner(System.in);
		String question = helper.buildQuestionObject(scanner, "Enter Question:");
		if(question.isEmpty()) return;
		String option1 = helper.buildQuestionObject(scanner, "Enter Option 1:");
		if(option1.isEmpty()) return;
		String option2 = helper.buildQuestionObject(scanner, "Enter Option 2:");
		if(option2.isEmpty()) return;
		String option3 = helper.buildQuestionObject(scanner, "Enter Option 3:");
		if(option3.isEmpty()) return;
		String option4 = helper.buildQuestionObject(scanner, "Enter Option 4:");
		if(option4.isEmpty()) return;
		String answer = helper.buildQuestionObject(scanner, "Enter Answer:");
		if(answer.isEmpty()) return;
		Questions questionObj = new Questions(quizCode, question, option1, option2, option3, option4, answer);
		addingQuestion(questionObj);
		helper.outputMessage("Would you like to add another? (yes/no)", false);
		String anotherGo = scanner.nextLine();
		anotherGo = anotherGo.toLowerCase();
		if(anotherGo.equals("yes")) addQuestion(quizName, quizCode);
		else if(anotherGo.equals("no")) manageQuestions(quizCode, quizName);
		else helper.closeApp();
	}
	
	private void addingQuestion(Questions question) {
		try {
			int isCreated = services.createQuestion(question);
			if (isCreated <= 0)
				helper.outputMessage("There was a problem with this transaction", true);
			else
				helper.outputMessage("Question Created Successfully", false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
