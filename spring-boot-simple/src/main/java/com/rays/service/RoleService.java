package com.rays.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.RoleDAO;
import com.rays.dto.RoleDTO;


@Service
@Transactional
public class RoleService {
	
	@Autowired
	public RoleDAO roleDao;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public long add(RoleDTO dto) {
		long pk = roleDao.add(dto);
		return pk;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(RoleDTO dto) {
		roleDao.update(dto);
	}
	
	public void delete(long id) {
		try {
			RoleDTO dto = findById(id);
			roleDao.delete(dto);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	public RoleDTO findById(long pk) {
		RoleDTO dto = roleDao.findByPk(pk);
		return dto;
	}

}
