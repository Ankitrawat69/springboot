package com.rays.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.dto.UserDTO;

@Repository
public class UserDAO {
	
	@PersistenceContext
	public EntityManager entityManager;
	
	
	public long add(UserDTO dto) {
		entityManager.persist(dto);
		return dto.getId();
	}
	
	public void update(UserDTO dto) {
		entityManager.merge(dto);
	}
	
	public void delete(UserDTO dto) {
		entityManager.remove(dto);
	}
	public UserDTO findByPk(long pk) {
		UserDTO dto = entityManager.find(UserDTO.class,pk);
		return dto;
	}
	
	public List<UserDTO> search(UserDTO dto,int pageNo,int pageSize){
		
		List<UserDTO> list = null;
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<UserDTO> cq = builder.createQuery(UserDTO.class);
		
		Root<UserDTO> qr = cq.from(UserDTO.class);
		
		cq.select(qr);
		
		TypedQuery<UserDTO> tq =  entityManager.createQuery(cq);
		
		if(pageSize > 0) {
			tq.setFirstResult(pageNo);
			tq.setMaxResults(pageSize);
		}
		 list = tq.getResultList();
		    return list;
	}
}
