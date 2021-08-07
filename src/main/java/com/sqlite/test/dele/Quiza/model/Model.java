package com.sqlite.test.dele.Quiza.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;

public class Model {
	
	private String dbURL;
	private Connection connection;
	private Statement statement;
	private PreparedStatement preparedStatement;
	private Helper helper;
	
	public Model(String dataBase) {
		helper = new Helper();
		dbURL = "jdbc:sqlite:"+dataBase+".db";
		try {
			connection = connectDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection connectDB() throws SQLException {
		return (Connection) DriverManager.getConnection(dbURL);
	}
	
	public void createTable(String tableName, JSONArray array) throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName;
		sql += "(id INTEGER PRIMARY KEY AUTOINCREMENT, ";
		int length = array.size();
		if(length > 0) {
			for (int i = 0; i < length; i++) {
				String query = array.get(i).toString();
				query = helper.removeFirstandLast(query);
				query = query.replace("=", " ");
				sql += query + ");";
			}
		}
		statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
	}
	
	public int insertToTable(String tableName, JSONArray array) throws SQLException {
		String sql = "INSERT INTO " + tableName + " (";
		statement = null;
		int length = array.size();
		if(length > 0) {
			for (int i = 0; i < length; i++) {
				String query = array.get(i).toString();
				query = helper.removeFirstandLast(query);
				query = query.replace(",", "");
				query = query.replace(" ", ";");
				String[] queryParts = query.split("\\;");
				int queryLength = queryParts.length;
				String[] innerQuery = new String[queryLength];
				int innerCount = 0;
				for (int j = 0; j < queryLength; j++) {
					innerQuery[innerCount] = queryParts[j];
					innerCount++;
				}
				int innerQueryLength = innerQuery.length;
				for (int k = 0; k < innerQueryLength; k++) {
					String start = StringUtils.substringBefore(innerQuery[k], "=");
					sql += start;
					if(k == innerQueryLength - 1) sql += ")";
					else sql += ", ";
				}
				sql += " VALUES (";
				for (int l = 0; l < innerQueryLength; l++) {
					String end = StringUtils.substringAfter(innerQuery[l], "=");
					sql += "'" + end + "'";
					if(l == innerQueryLength - 1) sql += ")";
					else sql += ", ";
				}
			}
		}
		statement = connection.createStatement();
		return statement.executeUpdate(sql);
	}
	
	public int updateTableData(String tableName, JSONArray array, boolean where, JSONArray whereArray) throws SQLException {
		where = true;
		String sql = "UPDATE " + tableName + " SET ";
		statement = null;
		int length = array.size();
		if(length > 0) {
			for (int i = 0; i < length; i++) {
				String query = array.get(i).toString();
				query = helper.removeFirstandLast(query);
				query = query.replace(",", "");
				query = query.replace(" ", ";");
				String[] queryParts = query.split("\\;");
				int queryLength = queryParts.length;
				String[] innerQuery = new String[queryLength];
				int innerCount = 0;
				for (int j = 0; j < queryLength; j++) {
					innerQuery[innerCount] = queryParts[j];
					innerCount++;
				}
				int innerQueryLength = innerQuery.length;
				for (int k = 0; k < innerQueryLength; k++) {
					String start = StringUtils.substringBefore(innerQuery[k], "=");
					String end = StringUtils.substringAfter(innerQuery[k], "=");
					sql += start + " = '" + end + "'";
					if(k == innerQueryLength - 1);
					else sql += ", ";
				}
			}
		}
		List<String> ending = new ArrayList<String>();
		if(where) {
			sql += " WHERE ";
			int whereSize = whereArray.size();
			for (int i = 0; i < whereSize; i++) {
				String query = whereArray.get(i).toString();
				query = helper.removeFirstandLast(query);
				query = query.replace(",", "");
				query = query.replace(" ", ";");
				String[] queryParts = query.split("\\;");
				int queryLength = queryParts.length;
				String[] innerQuery = new String[queryLength];
				int innerCount = 0;
				for (int j = 0; j < queryLength; j++) {
					innerQuery[innerCount] = queryParts[j];
					innerCount++;
				}
				int innerQueryLength = innerQuery.length;
				for (int k = 0; k < innerQueryLength; k++) {
					String start = StringUtils.substringBefore(innerQuery[k], "=");
					sql += start + " = ?";
					if(k == innerQueryLength - 1) sql += "";
					else sql += " AND ";
				}
				for (int l = 0; l < innerQueryLength; l++) {
					String end = StringUtils.substringAfter(innerQuery[l], "=");
					ending.add(end);
				}
			}
		}
		preparedStatement = connection.prepareStatement(sql);
		int index = 1;
		for (int i = 0; i < ending.size(); i++) {
			String string = ending.get(i);
			preparedStatement.setString(index, string);
		}
		helper.outputMessage(sql, false);
		return preparedStatement.executeUpdate();
	}
	
	public int removeFromTable(String tableName, boolean where, JSONArray whereArray) throws SQLException {
		String sql = "DELETE FROM " + tableName;
		List<String> ending = new ArrayList<String>();
		if(where) {
			sql += " WHERE ";
			int whereSize = whereArray.size();
			for (int i = 0; i < whereSize; i++) {
				String query = whereArray.get(i).toString();
				query = helper.removeFirstandLast(query);
				query = query.replace(",", "");
				query = query.replace(" ", ";");
				String[] queryParts = query.split("\\;");
				int queryLength = queryParts.length;
				String[] innerQuery = new String[queryLength];
				int innerCount = 0;
				for (int j = 0; j < queryLength; j++) {
					innerQuery[innerCount] = queryParts[j];
					innerCount++;
				}
				int innerQueryLength = innerQuery.length;
				for (int k = 0; k < innerQueryLength; k++) {
					String start = StringUtils.substringBefore(innerQuery[k], "=");
					sql += start + " = ?";
					if(k == innerQueryLength - 1) sql += "";
					else sql += " AND ";
				}
				for (int l = 0; l < innerQueryLength; l++) {
					String end = StringUtils.substringAfter(innerQuery[l], "=");
					ending.add(end);
				}
			}
		}
		preparedStatement = connection.prepareStatement(sql);
		int index = 1;
		for (int i = 0; i < ending.size(); i++) {
			String string = ending.get(i);
			preparedStatement.setString(index, string);
		}
		return preparedStatement.executeUpdate();
	}
	
	public ResultSet getTableData(String tableName, String[] selectCols, boolean where, JSONArray whereArray) throws SQLException {
		String sql = "SELECT ";
		for (int i = 0; i < selectCols.length; i++) {
			String col = selectCols[i];
			sql += col + ",";
		}
		sql = helper.removeLast(sql);
		sql += " FROM " + tableName + " ";
		List<String> ending = new ArrayList<String>();
		if(where) {
			sql += "WHERE ";
			int whereSize = whereArray.size();
			for (int i = 0; i < whereSize; i++) {
				String query = whereArray.get(i).toString();
				query = helper.removeFirstandLast(query);
				query = query.replace(",", "");
				query = query.replace(" ", ";");
				String[] queryParts = query.split("\\;");
				int queryLength = queryParts.length;
				String[] innerQuery = new String[queryLength];
				int innerCount = 0;
				for (int j = 0; j < queryLength; j++) {
					innerQuery[innerCount] = queryParts[j];
					innerCount++;
				}
				int innerQueryLength = innerQuery.length;
				for (int k = 0; k < innerQueryLength; k++) {
					String start = StringUtils.substringBefore(innerQuery[k], "=");
					sql += start + " = ?";
					if(k == innerQueryLength - 1) sql += "";
					else sql += " AND ";
				}
				for (int l = 0; l < innerQueryLength; l++) {
					String end = StringUtils.substringAfter(innerQuery[l], "=");
					ending.add(end);
				}
			}
		}
		preparedStatement = connection.prepareStatement(sql);
		int index = 1;
		for (int i = 0; i < ending.size(); i++) {
			String string = ending.get(i);
			preparedStatement.setString(index, string);
		}
		return (ResultSet) preparedStatement.executeQuery();
	}
	
}
