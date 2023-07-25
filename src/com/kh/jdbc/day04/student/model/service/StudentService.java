package com.kh.jdbc.day04.student.model.service;

import java.sql.*;
import java.util.*;

import com.kh.jdbc.day04.student.common.JDBCTemplate;
import com.kh.jdbc.day04.student.model.dao.StudentDAO;
import com.kh.jdbc.day04.student.model.vo.Student;

public class StudentService {
	private StudentDAO sDao;
	private JDBCTemplate jdbcTemplate;
	
	public StudentService() {
		sDao = new StudentDAO();
//		jdbcTemplate = new JDBCTemplate();	// 생성자가 private이기 때문에 사용 못함!
		jdbcTemplate = JDBCTemplate.getInstance();
	}

	public List<Student> selectAll() {
		Connection conn = jdbcTemplate.createConnection();
		List<Student> sList = sDao.selectAll(conn);
		jdbcTemplate.close();
		return sList;
	}

	public Student selectOneById(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		Student student = sDao.selectOneById(conn, studentId);
		jdbcTemplate.close();
		return student;
	}

	public List<Student> selectAllByName(String studentName) {
		Connection conn = jdbcTemplate.createConnection();
		List<Student> sList = sDao.selectAllByName(conn, studentName);
		jdbcTemplate.close();
		return sList;
	}

	public int deleteStudent(String studentId) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.deleteStudent(conn, studentId);
		jdbcTemplate.close();
		return result;
	}

	public int insertStudent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.insertStudent(conn, student);
		jdbcTemplate.close();
		return result;
	}

	public int updateStduent(Student student) {
		Connection conn = jdbcTemplate.createConnection();
		int result = sDao.updateStduent(conn, student);
		jdbcTemplate.close();
		return result;
	}
}