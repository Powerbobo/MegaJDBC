package com.kh.jdbc.day02.student.view;

import java.util.*;

import com.kh.jdbc.day02.student.controller.StudentController;
import com.kh.jdbc.day02.student.model.vo.Student;

public class StudentView {
	
	private StudentController controller;
	
	public StudentView() {
		controller = new StudentController();
	}
	
	public void startProgram() {
		Student student = null;
		List<Student> sList = null;
		finish:
		while(true) {
			int choice = printMenu();
			switch(choice) {
				case 1 : 
					// SELECT * FROM STUDENT_TBL
					// 쿼리문을 보고 전달값과 리턴값을 예상해 볼 수 없음.
					// 쿼리문이 전체 정보이기 때문에 리턴 타입이 List 여야 함.
					sList = controller.printStudentList();
					if(!sList.isEmpty()) {	// 데이터가 비어있는지 여부 체크!, 데이터가 없을 때 출력하지 않음
						showAllStudents(sList);
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 2 : 
					// 학생 아이디로 조회
					// 아이디로 조회하는 쿼리문 생각해보기(리턴형은 무엇으로? 매개변수는 무엇으로?)
					// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
					// printStudentById() 메소드가 학생 정보를 조회, dao의 메소드는 selectOneById()로 명명
					// showStudent() 메소드로 학생 정보를 출력
					String studentId = inputStudentId();
					// studentId 는 PRIMARY KEY 이기 때문에 1개의 값만 존재해서 List로 리턴을 받지 않는다.
					student = controller.printStudentById(studentId);
					if(student != null) {
						showStudent(student);
					}else {
						displayError("학생 정보가 존재하지 않습니다.");
					}
					break;
				case 3 : 
					// 학생 이름으로 조회
					// 쿼리문 생각해보기 (매개변수 유무, 리턴형은?)
					// SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = 'admin'
					// printStudentByName, printStudentsByName (?)
					// selectOneByName, selectAllByName (?)
					// showStudent, showAllStudents (?)
					String studentName = inputStudentName();
					sList = controller.printStudentsByName(studentName);
					if(!sList.isEmpty()) {
						showAllStudents(sList);
					} else {
						displayError("학생 정보가 조회되지 않습니다.");
					}
					break;
				case 4 : 
					// ***** 실행해서 학생 정보를 추가하면 sql 에도 추가됨! *****
					// INSERT INTO STUDENT_TBL VALUES('admin', 'admin', '관리자', 'M', 30, 'admin@iei.or.kr'
					// , '01012345678', '서울시 강남구 역삼동 테헤란로 7', '기타,독서,운동', '16/03/15');
					// 담을 데이터가 많아서 Student 객체에 담아서 관리
					// 추가할 학생 정보 입력
					student = inputStudent();
					// 값을 받기만 하면 되는거라 후처리가 필요 없다.
					// sql 에서 추가할 때 ex. 1행 이가 추가되었습니다. 라고 뜸.
					// 행이 추가되는거라 int 타입으로 리턴받아야 함.
					int result = controller.insertStudent(student);	
					// result 값이 0 보다 커야 성공
					if(result > 0) {
						// 성공 메세지 출력
						displaySuccess("학생 정보 등록 성공!");
					} else {
						// 실패 메세지 출력
						displayError("학생 정보 등록 실패");
					}
					break;
				case 5 : 
					// UPDATE STUDENT_TBL SET STUDENT_PWD = 'pass11', EMAIL = 'khuser11@iei.or.kr'
					// , PHONE = '01022222222', HOBBY = '코딩, 수영' WHERE STUDENT_ID = 'khsuer03';
					student = mondifyStudent();
					result = controller.modifyStudent(student);
					if(result > 0) {
						// 성공 메세지 출력
						displaySuccess("학생 정보 수정 성공!");
					} else {
						// 실패 메세지 출력
						displayError("학생 정보 수정 실패");
					}
					break;
				case 6 : 
					// 쿼리문 생각해보기(매개변수 필요 유무, 반환형?)
					// DELETE FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser06';
					studentId = inputStudentId();
					// 1 행 이(가) 삭제되었습니다.
					// sql 에서 실행할 때 뜨는 메세지로, 1행 즉, 숫자가 반환된다.
					// 쿼리문을 보고 해당 매개변수의 존재여부, 갯수, 반환형을 미리 예측해서 설계해야한다.
					// 항시 설계 먼저 하고 코딩하기.
					result = controller.deleteStudent(studentId);
					if(result > 0) {
						displaySuccess("학생 정보 삭제 성공!");
					} else {
						// 실패 메세지 출력
						displayError("학생 정보 삭제 실패");
					}
					break;
				case 0 : 
					break finish;
			}
		}
		
	}


	// 메뉴 
	public int printMenu() {
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

	// 학생 아이디 검색
	private String inputStudentId() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 아이디로 조회 =====");
		System.out.print("학생 아이디 입력 : ");
		String studentId = sc.next();
		return studentId;
	}

	// 학생 이름으로 검색
	private String inputStudentName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 이름으로 조회");
		System.out.print("학생 이름 입력 : ");
		String studentName = sc.next();
		return studentName;
	}

	// 학생 정보 등록
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

	private Student mondifyStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===== 학생 정보 수정 =====");
		System.out.print("아이디 : ");
		String studentId = sc.next();
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
		Student student = new Student(studentId, studentPw, email, phone, address, hobby);
		return student;
	}

	// 성공 메세지 출력
	private void displaySuccess(String message) {
		System.out.println("[서비스 성공] "+message);
	}
	// 실패 메세지 출력
	private void displayError(String message) {
		System.out.println("[서비스 실패] "+message);
	}

	// 학생 정보 전체 출력
	private void showAllStudents(List<Student> sList) {
		System.out.println("===== 학생 전체 정보 출력 =====");
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

	// 학생 아이디로 정보 출력
	private void showStudent(Student student) {
		System.out.println("===== 학생 정보 출력(아이디로 조회) ======");
		System.out.printf("이름 : %s, 나이 : %d, 아이디: %s, 성별  %s, 이메일 : %s, 전화번호 : %s, 주소 : %s, 가입날짜 : %s\n"
				, student.getStudentName()
				, student.getAge()
				, student.getStudentId()
				, student.getGender()
				, student.getEmail()
				, student.getPhone()
				, student.getAddress()
				, student.getEnrollDate());
	}
}
