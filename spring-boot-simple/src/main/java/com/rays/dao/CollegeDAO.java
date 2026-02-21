package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rays.dto.CollegeDTO;
import com.rays.dto.UserDTO;

@Repository
public class CollegeDAO {

	@PersistenceContext
	public EntityManager entityManager;
	
 
	public long add(CollegeDTO dto) {
		entityManager.persist(dto);
		return dto.getId();
	}
	
	public void update(CollegeDTO dto) {
		entityManager.merge(dto);
	}
	
	public void delete(CollegeDTO dto) {
		entityManager.remove(dto);
	}
	
	public CollegeDTO findbyPk(long pk) {
		CollegeDTO dto = entityManager.find(CollegeDTO.class,pk);
		return dto;
	}
	
	public List<CollegeDTO> search(CollegeDTO dto, int pageNo, int pageSize){
		
		List<CollegeDTO> list = null;
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<CollegeDTO> cq = cb.createQuery(CollegeDTO.class);
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		Root<CollegeDTO> qr = cq.from(CollegeDTO.class);
		
		if(dto != null) {
			if(dto.getName() != null && dto.getName().length() > 0) {
				predicateList.add(cb.like(qr.get("name"),dto.getName() + "%"));
			}
		}
        cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		
		TypedQuery<CollegeDTO> tq =  entityManager.createQuery(cq);
		
		if(pageSize > 0 ) {
			tq.setFirstResult(pageNo * pageSize);
			tq.setMaxResults(pageSize);
		}
		 list = tq.getResultList();
		    return list;
	}
}
