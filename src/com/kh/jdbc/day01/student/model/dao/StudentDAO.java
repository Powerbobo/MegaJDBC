package com.kh.jdbc.day01.student.model.dao;

// sql 패키지 전부 import
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentDAO {
	
	// sql 전체 정보를 가지고 오는 메소드 만들었음.
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 및 5. 결과 받기
		 * 6. 자원해제(close())
		 */
		
		String driverName = "oracle.jdbc.driver.OracleDriver";
		// url -> jdbc:oracle:thin: + 주소 + 1521 + xe
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "STUDENT";		// oracle 계정 id
		String password = "STUDENT";	// oracle 계정 pw
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = null;
		Student student = null;
		

		
		try {
			// 1. 드라이버 등록
			Class.forName(driverName);
			
			// 2. DB 연결 생성(DriverManager)
			Connection conn = DriverManager.getConnection(url, user, password);
			
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			
			// 4. 쿼리문 실행 및 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query);	// 쿼리문이 SELET이기 때문에 ResultSet
														// INSERT 일 경우 int
			// 후처리
			sList = new ArrayList<Student>();
			while(rset.next()) {
				student = new Student();
				student.setStudentId(rset.getString("STUDENT_ID"));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sList;
	}

}
