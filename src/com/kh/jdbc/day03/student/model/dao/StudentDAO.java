package com.kh.jdbc.day03.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day03.student.model.vo.Student;

// 역할 -> 데이터 접근 계층
public class StudentDAO {
	private final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	// localhost = 127.0.0.1 -> 컴퓨터의 DB 주소
	private final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private final String USER = "STUDENT";
	private final String PASSWORD = "STUDENT";

	public List<Student> selectAll() {
		String query = "SELECT * FROM STUDENT_TBL";
		List<Student> sList = new ArrayList<Student>(); // DB 반환값들을 저장하기 위해 선언
		Connection conn = null;	// finally 로 자원 종료하기 위해서 전역변수로 선언
		Statement stmt = null;
		ResultSet rset = null;

		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			// 후처리
			while (rset.next()) { // 반환 값이 있을 때 필수!, 반환 값 1개 일 경우 if문 사용
				Student student = rsetToStudent(rset);
				sList.add(student);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sList;
	}

	
	// 이름으로 고객 정보 조회
	public List<Student> selectOneByName(String studentName) {
		List<Student> sList = new ArrayList<Student>();
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = ?";
		Student student = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);	// 쿼리문 준비(쿼리문 넣기)
			pstmt.setString(1,  studentName);						// setString()으로 확인
			rset = pstmt.executeQuery();					// 쿼리문 실행
//			Statement stmt = conn.createStatement();
			// SELECT * FROM STUDENT_TBL WHERE STDUENT_NAME
//			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sList;
	}
	
	// 아이디로 고객 정보 조회
	public Student selectOneById(String studentId) {
		// PreparedStatement 사용
		// 1. 위치홀더 세팅
		// 2. PreparedStatement 객체 생성 with query
		// 3. 입력값 세팅
		// 4. 쿼리문 실행 및 결과 받기(feat. method())
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		Student student = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			rset = pstmt.executeQuery();
//			Statement stmt = conn.createStatement();
			// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID
//			ResultSet rset = stmt.executeQuery(query);
			// 후처리
			if(rset.next()) {
				student = rsetToStudent(rset);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return student;
	}
	
	// 로그인
	public Student selectLoginInfo(Student student) {
		// PreparedStatement -> SQL Injection 공격을 막기 위해서 사용함!
		// PreparedStatement 를 사용할 때는 쿼리문 찢을 필요 없이 ? 만 넣어주면 됨.
		// ? 의 명칭 -> 위치홀더
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = ? AND STUDENT_PWD = ?";
		Student result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);	// 쿼리문을 미리 넣어놓기
			// 시작은 1로 하고 마지막 수는 물음표의 갯수와 같아.(물음표 = 위치홀더)
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			rset = pstmt.executeQuery();					// 쿼리문 실행만 시킴
//			Statement stmt = conn.createStatement();
//			ResultSet rset = stmt.executeQuery(query);
			// 후처리
			if(rset.next()) {
				result = rsetToStudent(rset);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	// 고객 정보 등록
	public int insertStudent(Student student) {
		// INSERT INTO STUDENT_TBL VALUES(1,2,3,4,5,6,7,8,9,SYSDATE)
		String query = "INSERT INTO STUDENT_TBL VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPwd());
			pstmt.setString(3, student.getStudentName());
			pstmt.setString(4, String.valueOf(student.getGender()));
//			pstmt.setString(4, student.getGender()+"");
			pstmt.setInt(5, student.getAge());
			pstmt.setString(6, student.getEmail());
			pstmt.setString(7, student.getPhone());
			pstmt.setString(8, student.getAddress());
			pstmt.setString(9, student.getHobby());
			result = pstmt.executeUpdate();	// *********** 쿼리문 실행 빼먹지 않기 ***********
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate(query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 데이터 삭제
	public int deleteStudent(String studentId) {
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = ?";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, studentId);
			result = pstmt.executeUpdate();
//			Statement stmt = conn.createStatement();
			// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID
//			result = stmt.executeUpdate(query);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 데이터 수정
	public int updateStudent(Student student) {
		String query = "UPDATE STUDENT_TBL SET STUDENT_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE STUDENT_ID = ?";
		int result = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, student.getStudentPwd());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPhone());
			pstmt.setString(4, student.getAddress());
			pstmt.setString(5, student.getHobby());
			pstmt.setString(6, student.getStudentId());
			result = pstmt.executeUpdate();
//			Statement stmt = conn.createStatement();
			// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID
//			result = stmt.executeUpdate(query);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	// 후처리 메소드
	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		student.setStudentId(rset.getString(1)); // 컬럼명 대신 컬럼의 순번으로 대체할 수 있음!
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
