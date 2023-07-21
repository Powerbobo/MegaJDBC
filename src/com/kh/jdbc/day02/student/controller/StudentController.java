package com.kh.jdbc.day02.student.controller;

import java.util.List;

import com.kh.jdbc.day01.student.model.dao.StudentDAO;
import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentController {
	
	StudentDAO studentDao;
	
	public StudentController() {
		studentDao = new StudentDAO();
	}
	
	/**
	 * 학생 정보 전체 불러오기
	 * @return
	 */
	public List<Student> printStudentList() {
		List<Student> sList = studentDao.selectAll();
		return sList;
	}
	/**
	 * 학생 아이디로 데이터 조회
	 * @param studentId
	 * @return
	 */
	public Student printStudentById(String studentId) {
		Student student = studentDao.selectOneById(studentId);
		return student;
	}

	public List<Student> printStudentsByName(String studentName) {
		List<Student> sList = studentDao.selectAllByName(studentName);
		return sList;
	}

	/**
	 * 학생 정보 등록
	 * @param student
	 * @return
	 */
	public int insertStudent(Student student) {
		int result = studentDao.insertStudent(student);
		return result;
	}
	/**
	 * 데이터 업데이트
	 * @param student
	 * @return
	 */
	public int modifyStudent(Student student) {
		int result = studentDao.updateStudent(student);
		return result;
	}

	/**
	 * 아이디로 조회해서 데이터 삭제
	 * @param studentId
	 * @return
	 */
	public int deleteStudent(String studentId) {
		int result = studentDao.deleteStudent(studentId);
		return result;
	}

}
