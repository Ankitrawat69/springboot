package com.rays.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rays.dto.RoleDTO;

@Repository
public class RoleDAO {
	
	@PersistenceContext
	public EntityManager entityManager;
	
	public long add(RoleDTO dto) {
		entityManager.persist(dto);
		return dto.getId();
	}
	
	public void update(RoleDTO dto) {
		entityManager.merge(dto);
	}
	
	public void delete(RoleDTO dto) {
		entityManager.remove(dto);
	}
	public RoleDTO findByPk(long pk) {
		RoleDTO dto = entityManager.find(RoleDTO.class,pk);
		return dto;
	}
	
	public List<RoleDTO> search(RoleDTO dto, int pageNo, int pageSize){
		
		List<RoleDTO> list = null;
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
	    CriteriaQuery<RoleDTO> cq = builder.createQuery(RoleDTO.class);
	    
	    Root<RoleDTO> qroot =  cq.from(RoleDTO.class);
	    
	    cq.select(qroot);
	    
	    TypedQuery<RoleDTO> tq =  entityManager.createQuery(cq);
	    
	    if(pageSize > 0) {
	    	tq.setFirstResult(pageNo * pageSize);
	    	tq.setMaxResults(pageSize);
	    }
	    
	    list = tq.getResultList();
	    return list;
	}
	
}
