package com.rays.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.StudentDAO;
import com.rays.dto.StudentDTO;

@Service
@Transactional
public class StudentService {
	
	@Autowired
	public StudentDAO studentDao;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public long add(StudentDTO dto) {
		long pk = studentDao.add(dto);
		return pk;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(StudentDTO dto) {
		studentDao.update(dto);
	}
	
	public void delete(long id) {
		try {
			StudentDTO dto = findById(id);
			studentDao.delete(dto);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public StudentDTO findById(long pk) {
		StudentDTO dto = studentDao.findByPk(pk);
		return dto;
	}
	
	@Transactional(readOnly = true)
	public List<StudentDTO> search(StudentDTO dto, int pageNo, int pageSize){
		return studentDao.search(dto, pageNo, pageSize);
	}


}
