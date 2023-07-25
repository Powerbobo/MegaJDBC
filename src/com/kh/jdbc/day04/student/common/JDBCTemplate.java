package com.kh.jdbc.day04.student.common;

import java.sql.*;

public class JDBCTemplate {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";
	// 디자인 패턴 : 각기 다른 소프트웨어 모듈이나 기능을 가진 응용 SW를
	// 개발할 때 공통되는 설계 문제를 해결하기 위하여 사용되는 패턴.
	// ==> 효율적인 방식을 위함!
	// 패턴의 종류 : 생성패턴, 구조패턴, 행위패턴, ...
	// 1. 생성패턴 : 싱글톤 패턴, 추상팩토리, 팩토리 메소드, ...
	// 2. 구조패턴 : 컴포지트, 데코레이트, ...
	// 3. 행위패턴 : 옵저버, 스테이트, 전략, 템플릿 메소드, ...
	
	/* 싱글톤 패턴
	 * 
	 * public class Singletone {
	 * 		private static Singletone instance;
	 * 
	 * 		private Singletone() {}
	 * 
	 * 		public static Singletone getInstance() {
	 * 			if (instance == null) {
	 * 				instance = new Singletone();
	 * 			}
	 * 		}
	 * }
	 */
	
	
	// 싱글톤패턴(디자인 패턴) -> 재사용하기 위해서 사용
	// 무조건 딱 한번만 생성되고 없을때에만 생성한다.
	// 이미 존재하면 존재하는 객체를 사용함.
	private static JDBCTemplate instance;	// 필드 선언
	private static Connection conn;
	
	private JDBCTemplate() {}	// 다른곳에서 객체 생성하지 못하게 하도록
								// 생성자를 private로 선언해버림
	public static JDBCTemplate getInstance() {
		// 이미 만들어져있는지 체크하고
		if(instance == null) {
			// 안만들어져 있으면 만들어서 사용
			instance = new JDBCTemplate();
		} 
		// 만들어져 있으면 그거 사용해
		return instance;
	}	// 여기까지 싱글톤 패턴
	
	
	// DBCP(DataBase Connection pool)
	public Connection createConnection() {
		try {
			if(conn == null || conn.isClosed()) {	// conn 생성이 안되어있을 경우
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close() {
		if(conn != null) {	// conn 연결 끊기
			try {
				if(!conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
