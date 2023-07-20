package com.kh.jdbc.day01.basic;

// sql 에 있는 모든걸 쓰기 위해서 * 로 표기
import java.sql.*;

public class JDBCRun {

	public static void main(String[] args) {
		/*
		 * JDBC 코딩 절차
		 * 1. 드라이버 등록 (Referenced Libraries -> ojdbc6.jar 파일과 관련이 있다.)
		 * 2. DBMS 연결 생성
		 * 3. Statement 객체 생성(쿼리문 실행 준비)
		 *  - new Statement(); 가 아니라 연결을 통해 객체 생성함.
		 * 4. SQL 전송(쿼리문 실행)
		 * 5. 결과 받기(ResultSet으로 바로 받아버림)
		 * 6. 자원해제(close())
		 * 
		 */
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "KH";
		String password = "KH";
		String qaery = "SELECT EMP_NAME, SALARY FROM EMPLOYEE";	// sql 에서 쿼리문 복사할 때 ; 빼고 복사해야 함!
		
		try {
			// 1. 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");	// Class.forName(""); -> 이 코드가 있음으로써 자료파일을 쓸 수 있음! 
																// checked exception -> try-catch문 사용해야 함.
			// 2. DBMS 연결 생성
			Connection conn = DriverManager.getConnection(url, user, password);	// checked exception -> try-catch문 사용해야 함.
			
			// 3. 쿼리문 실행준비(Statement 객체 생성)
			Statement stmt = conn.createStatement();	// new 사용하지 않음, 쿼리문 실행 준비 끝
			
			// 4. 쿼리문 실행(SELECT면 ResultSet), 5. 결과값 받기(resultset은 테이블형태)
			ResultSet rset = stmt.executeQuery(qaery);
			
			// 후처리 필요 - 디비에서 가져온 데이터 사용하기 위함.
			while(rset.next()) {	// 다음 데이터가 있을때까지 true, 없으면 false 로 종료
				System.out.printf("직원명 : %s, 급여 : %s\n", rset.getString("EMP_NAME"), rset.getInt(2));	// 컬럼의 데이터 타입으로 가져와야 함.
			}
			
			// 6. 자원해제
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {					
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}

}
