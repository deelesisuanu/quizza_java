package com.sqlite.test.dele.Quiza;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.sqlite.test.dele.Quiza.model.Helper;
import com.sqlite.test.dele.Quiza.pojo.Set;

@RunWith(JUnit4.class)
public class HelperTest {
	
	/**
	 * removeFirstandLast
	 * removeLast
	 * getAppName
	 * getToken
	 */
	
	private static Helper helper;
	private static Set set;
	private String word = "significance";
	private String word2 = "alphabet";
	
	@BeforeClass
	public static void setup() {
		helper = new Helper();
		set = new Set();
	}
	
	@Before
	public void set1() {
		set.setExpected("ignificanc");
	}
	
	@Test
	public void removeFirstLast() {
		System.out.println(set.getExpected() + " : 1");
		assertEquals(set.getExpected(), helper.removeFirstandLast(word));
	}
	
	@Before
	public void set2() {
		set.setExpected("significanc");
	}
	
	@Test
	public void removeLast() {
		System.out.println(set.getExpected() + " : 2");
		assertEquals(set.getExpected(), helper.removeLast(word));
	}
	
	@Before
	public void set3() {
		set.setExpected("Quizza");
	}
	
	@Test
	public void getAppName() {
		System.out.println(set.getExpected() + " : 3");
		assertEquals(set.getExpected(), helper.getAppName());
	}
	
	@Before
	public void set4() {
		set.setExpected("123456");
	}
	
	@Test
	public void getUserToken() {
		System.out.println(set.getExpected() + " : 4");
		assertEquals(set.getExpected(), helper.accessToken());
	}

}
