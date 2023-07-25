package com.kh.jdbc.day04.student.view;

import java.util.List;
import java.util.Scanner;

import com.kh.jdbc.day04.student.controller.StudentController;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentView {
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();	// 초기화
	}

	public void startProgram() {
		List<Student> sList = null;
		String studentId = "";
		int result = 0;
		end :
		while(true) {
			int choice = printMenu();
			switch(choice) {
				case 1 : 
					// 학생 전체 조회
					// 쿼리문 -> SELECT * FROM STUDENT_TBL 
					// SELECT CONUT(*) FROM STUDENT_TBL -> SELECT 가 int 로 반환되는 경우
					sList = controller.selectAll();
					if(!sList.isEmpty()) {
						printAllStudent(sList);
					} else {
						displayError("데이터 조회에 실패하였습니다.");
					}
					break;
				case 2 : 
					// 아이디로 조회
					// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
					studentId = inputStdId("검색");
					Student student = controller.selectOneById(studentId);
					if(student != null) {
						printStudent(student);
					}
					break;
				case 3 : 
					// SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '관리자'
					// 이름으로 조회
					String studentName = inputStdName();
					sList = controller.selectAllByName(studentName);
					if(!sList.isEmpty()) {
						printAllStudent(sList);
					} else {
						displayError("데이터 조회에 실패하였습니다.");
					}
					break;
				case 4 : 
					// 데이터 등록
					student = inputStudent();
					result = controller.insertStudent(student);
					if(result > 0) {
						displaySuccess("데이터 등록에 성공하였습니다.");
					} else {
						displayError("데이터 등록을 완료하지 못했습니다.");
					}
					break;
				case 5 : 
					// 데이터 수정
					studentId = inputStdId("수정");
					student = controller.selectOneById(studentId);
					if(student != null) {
						// 수정 정보 입력받기
						student = modifyStudent(student);
						result = controller.updateStduent(student);
						if(result > 0) {
							displaySuccess("데이터 수정에 성공하였습니다.");
						} else {
							displayError("데이터 수정에 실패하였습니다.");
						}
					} else {
						displayError("데이터를 찾지 못하였습니다.");
					}
					break;
				case 6 : 
					// 데이터 삭제
					studentId = inputStdId("삭제");
					// DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
					result = controller.deleteStudent(studentId);
					if(result > 0) {
						displaySuccess("데이터 삭제를 완료하였습니다.");
					} else {
						displayError("데이터 삭제를 완료하지 못했습니다.");
					}
					break;
				case 0 : break end;
			}
		}
	}
	
	// 데이터 등록
	private Student inputStudent() {
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
	
	// 수정할 데이터 입력
	private Student modifyStudent(Student student) {
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
		student.setStudentPwd(studentPw);
		student.setEmail(email);
		student.setPhone(phone);
		student.setAddress(address);
		student.setHobby(hobby);
		return student;
	}

	// 메인 메뉴
	private int printMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생관리 프로그램 =====");
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
	
	// 아이디로 고객 정보 조회
	private void printStudent(Student student) {
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

	
	// 성공 메세지
	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] : "+ message);
		
	}

	// 실패 메세지
	private void displayError(String message) {
		System.out.println("[서비스 실패] : "+ message);
	}
	
}
