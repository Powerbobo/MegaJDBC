package com.kh.jdbc.day03.student.controller;

import java.util.List;

import com.kh.jdbc.day03.student.model.dao.StudentDAO;
import com.kh.jdbc.day03.student.model.vo.Student;

// 역할 -> 보통 반환값 처리
public class StudentController {
	private StudentDAO sDao;
	// 생성자
	public StudentController() {
		sDao = new StudentDAO();
	}

	// 아이디로 고객정보 조회
	public Student printStudentById(String studentId) {
		Student student = sDao.selectOneById(studentId);
		return student;
	}

	// 매개변수/리턴값이 있는지 여부 -> 쿼리문을 생객해보자!
	// SELECT * FROM STUDENT_TBL
	public List<Student> selectAllStudent() {
		List<Student> sList = sDao.selectAll();
		return sList;
	}
	
	// 이름으로 고객정보 조회
	public List<Student> selectAllByName(String studentName) {
		List<Student> sList = sDao.selectOneByName(studentName);
		return sList;
	}
	
	// 고객 정보 등록
	public int insertStudent(Student student) {
		int result = sDao.insertStudent(student);
		return result;
	}
	// 고객 정보 삭제
	public int deleteStudent(String studentId) {
		int result = sDao.deleteStudent(studentId);
		return result;
	}
	// 고객 정보 수정
	public int updateStudent(Student student) {
		int result = sDao.updateStudent(student);
		return 0;
	}
	// 로그인
	public Student studentLogin(Student student) {
		Student result = sDao.selectLoginInfo(student);	// student 변수명 겹쳐서 변경
		return result;
	}

}
