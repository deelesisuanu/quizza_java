package com.sqlite.test.dele.Quiza.model;

import java.util.Date;
import java.util.Scanner;

public class Helper {
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public String removeFirstandLast(String str) {
		StringBuilder sb = new StringBuilder(str);
		sb.deleteCharAt(str.length() - 1);
		sb.deleteCharAt(0);
		return sb.toString();
	}

	public String removeLast(String str) {
		StringBuilder sb = new StringBuilder(str);
		sb.deleteCharAt(str.length() - 1);
		return sb.toString();
	}

	public void outputMessage(String msg, boolean error) {
		if (error)
			System.err.println(msg);
		else
			System.out.println(msg);
	}

	public String getAppName() {
		return "Quizza";
	}

	public String accessToken() {
		return "123456";
	}

	public String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public String currentTimeStamp() {
		Date date = new Date();
		return date.toString();
	}
	
	public String buildQuestionObject(Scanner scanner, String displayMsg) {
		outputMessage(displayMsg, false);
		String built = scanner.nextLine();
		built = built.replace(" ", "_");
		return built;
	}
	
	public String getIndexValue(int position, String[] arr) {
		if(arr.length > position && arr[position] != null) {
			for (int i = 0; i < arr.length; i++) {
				if(position == i) return arr[i];
			}
		}
		return "";
	}
	
	public void closeApp() {
		System.exit(0);
	}

}
