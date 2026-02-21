package com.rays.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.CollegeDAO;
import com.rays.dto.CollegeDTO;
import com.rays.dto.UserDTO;

@Service
@Transactional
public class CollegeService {
	
	@Autowired
	CollegeDAO collegeDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public long add(CollegeDTO dto) {
		long pk = collegeDAO.add(dto);
		return pk;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(CollegeDTO dto) {
		collegeDAO.update(dto);
	}
	
	@Transactional(readOnly = true)
	public CollegeDTO findById(long pk) {
		CollegeDTO dto = collegeDAO.findbyPk(pk);
		return dto;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {
		try {
			CollegeDTO dto = findById(id);
				collegeDAO.delete(dto);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Transactional(readOnly = true)
	public List<CollegeDTO> search(CollegeDTO dto, int pageNo, int pageSize){
		return collegeDAO.search(dto, pageNo, pageSize);
	}

}
