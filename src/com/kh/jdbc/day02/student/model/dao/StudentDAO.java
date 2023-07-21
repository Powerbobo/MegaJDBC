package com.kh.jdbc.day02.student.model.dao;

// sql 패키지 전부 import
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentDAO {
	
	// JDBC URL, 계정명, 비밀번호 전역변수 선언 -> 멤버변수, 어떤 메소드든 가져다 사용 가능
	// url -> jdbc:oracle:thin: + 주소 + 1521 + xe
	// 상수이고, 보안문제로 private final -> 변하지 않을 상수로 선언
	// private final 의 변수는 대문자여야함.
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER = "STUDENT";		// oracle 계정 id
	private final String PASSWORD = "STUDENT";	// oracle 계정 pw
	
	// sql 전체 정보를 가지고 오는 메소드 만들었음.
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5. 결과 받기
		 * 6. 자원해제(close())
		 */
		
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		Student student = null;
		

		
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			
			// 2. DB 연결 생성(DriverManager)
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			
			// 4. 쿼리문 실행 및 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query);	// 쿼리문이 SELET이기 때문에 ResultSet
														// INSERT 일 경우 int
			// 후처리
			sList = new ArrayList<Student>();
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
				
				// Student 객체를 만들고어서 rset 변수에 저장한 데이터를 담아서 객체화 시키고,
				// student 객체를 setter 메소드로 List 에 담아 저장한다. 
				// 저장한 데이터를 getter 메소드를 사용해 가져올 수 있다.
			}
			
			// 6. 자원해제(close())
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}
	// 이름으로 데이터 찾기
	public List<Student> selectAllByName(String studentName) {
		// SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = 'admin'
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME ='" +studentName+"'";
		Student student = null;
		// 리턴용 리스트 선언
		List<Student> sList = new ArrayList<Student>();
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			// 후처리
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}
	// 아이디로 데이터 찾기
	public Student selectOneById(String studentId) {
					 // SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
		// 따옴표 잘 기억하기
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID ='" + studentId + "'";
		Student student = null;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			// 후처리
			// 결과값이 1개만 나올때는 while(rset.next()) 가 아니라 if 문 사용해야 한다.
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
		}
		return student;
	}
	
	// 학생 정보 등록하기 (INSERT)
	public int insertStudent(Student student) {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5.결과 받기
		 * 6. 자원해제
		 */
		// INSERT INTO STUDENT_TBL VALUES('admin', 'admin', '관리자', 'M', 30, 'admin@iei.or.kr', 
		// '01012345678', '서울시 강남구 역삼동 테헤란로 7', '기타,독서,운동', '16/03/15');
		String query = "INSERT INTO STUDENT_TBL VALUES("
				+ "'"+student.getStudentId()+"', "
						+ "'"+student.getStudentPwd()+"', "
								+ "'"+student.getStudentName()+"', "
										+ "'"+student.getGender()+"', "
												+ student.getAge()+", "	// 정수이기때문에 "" 지워도 상관없음!
														+ "'"+student.getEmail()+"', "
																+ "'"+student.getPhone()+"', "
																		+ "'"+student.getAddress()+"', "
																				+ "'"+student.getHobby()+"', "
																						+ "SYSDATE)";
		int result = -1;	// 동작하지 않으면 -1
		
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVER_NAME);
			// 2. DB 연결 생성
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			// 4. 쿼리문 실행 및 결과받기
			result = stmt.executeUpdate(query);	// DML(INSERT, UPDATE, DELETE)용
			// ResultSet rset = stmt.executeQuery();	// executeQuery() -> SELECT용
			// 6. 연결 닫기
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 데이터 업데이트
	public int updateStudent(Student student) {
		// UPDATE STUDENT_TBL SET STUDENT_PWD = 'pass11', EMAIL = 'khuser11@iei.or.kr', PHONE = '01022222222', HOBBY = '코딩, 수영' WHERE STUDENT_ID = 'khsuer03';
		String query = "UPDATE STUDENT_TBL SET "
					+ "STUDENT_PWD = '"+ student.getStudentPwd()+"', "
						+ "EMAIL = '"+ student.getEmail()+"', "
							+ "PHONE = '"+ student.getPhone()+"', "
								+ "ADDRESS = '"+ student.getAddress()+"', "
									+ "HOBBY = '"+ student.getHobby()+"' "
										+ "WHERE STUDENT_ID ='" + student.getStudentId()+"'";
		int result = -1;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	// 데이터 삭제
	public int deleteStudent(String studentId) {
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID ='"+ studentId+"'" ;
		int result = 0;
		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	// 후처리 메소드화
	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		student.setStudentId(rset.getString("STUDENT_ID"));	// rset.getString("STUDENT_ID") -> checked Exception
		student.setStudentPwd(rset.getString("STUDENT_PWD"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		// 문자는 문자열에서 문자로 잘라서 사용, chatAt() 메소드 사용
		student.setGender(rset.getString("GENDER").charAt(0));
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		return student;
	}

}
