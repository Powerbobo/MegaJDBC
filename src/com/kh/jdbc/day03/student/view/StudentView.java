package com.kh.jdbc.day03.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day03.student.controller.StudentController;
import com.kh.jdbc.day03.student.model.vo.Student;

public class StudentView {
	// 컨트롤러 연결
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}
	
	public void studentProgram() {
		Student student = null;
		List<Student> sList  = null;
		int result = 0;
		theEnd :
		while(true) {
			int input = printMenu();
			switch(input) {
				case 1 : 
					// 전체 정보 조회하기
					sList = controller.selectAllStudent();
					printAllStudent(sList);
					break;
				case 2 : 
					// 아이디로 정보 조회하기
					// SELECT * FROM STDUENT_TBL WHERE STUDENT_ID = 'user11'
					String studentId = inputStdId("검색");
					student = controller.printStudentById(studentId);
					printOneStudent(student);
					break;
				case 3 : 
					// 이름으로 정보 조회하기
					// SELECT * FROM STDUENT_TBL WHERE STUDENT_NAME = '관리자'
					String studentName = inputStdName();
					sList = controller.selectAllByName(studentName);
					printAllStudent(sList);
					break;
				case 4 : 
					// 학생 정보 등록
					// INSERT INTO STUDENT_TBL VALUES(1,2,3,4,5,6,7,8,9,SYSDATE)
					student = inputStudentInfo();
					result = controller.insertStudent(student);
					break;
				case 5 : 
					// 데이터 수정
					// UPDATE SET 
					studentId = inputStdId("수정");
					student = controller.printStudentById(studentId);	// 아이디 중복 여부 확인
					if(student != null) {
						// 있는거
						student = modifyStudent();
						student.setStudentId(studentId);
						result = controller.updateStudent(student);
					} else {
						// 없는거
						displayError("해당 정보가 존재하지 않습니다.");
					}
					break;
				case 6 : 
					// 데이터 삭제
					// DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
					studentId = inputStdId("삭제");
					result = controller.deleteStudent(studentId);
					if(result > 0) {
						// 성공
						displaySuccess("삭제 성공");
					} else {
						// 실패
						displayError("해당 정보가 존재하지 않습니다.");
					}
					break;
				case 9 :
					// 학생 로그인
					// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'user11' AND STUDENT_PW = '1234'
					student = inputLoginInfo();
					student = controller.studentLogin(student);
					if(student != null) {
						// 로그인 성공
						displaySuccess("로그인 성공");
					} else {
						// 로그인 실패
						displayError("해당 정보가 존재하지 않습니다.");
					}
					break;
				case 0 : 
					break theEnd;
			}
		}
	}

	// 로그인 성공
	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : "+ message);
		
	}

	// 로그인 실패
	private void displayError(String message) {
		System.out.println("[서비스 실패] : "+ message);
	}
	
	// 수정할 데이터 입력
	private Student modifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();	// 공백 제거, 엔터 제거
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.next();
		Student student = new Student(studentPw, email, phone, address, hobby);
		return student;
	}
	// 학생 정보 등록
	private Student inputStudentInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.print("아이디 : ");
		String studentId = sc.next();
		System.out.print("비밀번호 : ");
		String studentPw = sc.next();
		System.out.print("이름 : ");
		String studentName = sc.next();
		System.out.print("성별 : ");
		char gender = sc.next().charAt(0);
		System.out.print("나이 : ");
		int age = sc.nextInt();
		System.out.print("이메일 : ");
		String email = sc.next();
		System.out.print("전화번호 : ");
		String phone = sc.next();
		System.out.print("주소 : ");
		sc.nextLine();	// 공백 제거, 엔터 제거
		String address = sc.nextLine();
		System.out.print("취미(,로 구분) : ");
		String hobby = sc.nextLine();
		Student student = new Student(studentId, studentPw, studentName, gender, age, email, phone, address, hobby);
		return student;
	}
	
	// 아이디, 비밀번호 입력
	private Student inputLoginInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 로그인 =====");
		System.out.print("아이디 : ");
		String studentID = sc.nextLine();
		System.out.print("비밀번호 : ");
		String studentPw = sc.nextLine();
		Student student = new Student(studentID, studentPw);
		System.out.println(student.toString());
		return student;
	}

	// 이름 검색
	private String inputStdName() {
		Scanner sc = new Scanner(System.in);
		System.out.print("검색할 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
	}
	
	// 아이디 검색
	private String inputStdId(String category) {
		Scanner sc = new Scanner(System.in);
		System.out.print(category + "할 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}

	// 아이디로 고객 정보 조회
	private void printOneStudent(Student student) {
		System.out.println("====== 학생 정보 조회(아이디) ======");
		System.out.printf("이름 : %s, 나이 : %d, 아이디: %s, 성별  %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
				, student.getStudentName()
				, student.getAge()
				, student.getStudentId()
				, student.getGender()
				, student.getEmail()
				, student.getPhone()
				, student.getAddress()
				, student.getHobby()
				, student.getEnrollDate());
	}


	// 학생 전체 정보 출력
	private void printAllStudent(List<Student> sList) {
		System.out.println("====== 학생 전체 조회 ======");
		for(Student student : sList) {
			System.out.printf("이름 : %s, 나이 : %d, 아이디: %s, 성별  %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 취미 : %s, 가입날짜 : %s\n"
					, student.getStudentName()
					, student.getAge()
					, student.getStudentId()
					, student.getGender()
					, student.getEmail()
					, student.getPhone()
					, student.getAddress()
					, student.getHobby()
					, student.getEnrollDate());
		}
	}
	// 메인 메뉴
	private int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생관리 프로그램 =====");
		System.out.println("9. 학생 로그인");
		System.out.println("1. 학생 전체 조회");
		System.out.println("2. 학생 아이디로 조회");
		System.out.println("3. 학생 이름으로 조회");
		System.out.println("4. 학생 정보 등록");
		System.out.println("5. 학생 정보 수정");
		System.out.println("6. 학생 정보 삭제");
		System.out.println("0. 프로그램 종료");
		System.out.print("메뉴 선택 : ");
		int input = sc.nextInt();
		return input;
	}
}
