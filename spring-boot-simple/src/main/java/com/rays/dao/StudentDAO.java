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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.dto.CollegeDTO;
import com.rays.dto.RoleDTO;
import com.rays.dto.StudentDTO;
import com.rays.dto.UserDTO;

@Repository
public class StudentDAO {
	
	@PersistenceContext
	public EntityManager entityManager;
	
	@Autowired
	CollegeDAO collegeDAO;
	
	public StudentDTO populate(StudentDTO dto) {
		   if (dto.getCollegeId() != null && dto.getCollegeId() > 0) {
			   System.out.println(dto.getCollegeId());
		      CollegeDTO collegedto = 	collegeDAO.findbyPk(dto.getCollegeId());
		      
		         dto.setCollegeName(collegedto.getName());
		      }
		   return dto;
	    } 
	
	public long add(StudentDTO dto) {
		dto = populate(dto);
		entityManager.persist(dto);
		return dto.getId();
	}
	
	public void update(StudentDTO dto) {
		dto = populate(dto);
		entityManager.merge(dto);
	}
	
	public void delete(StudentDTO dto) {
		entityManager.remove(dto);
	}
	public StudentDTO findByPk(long pk) {
		StudentDTO dto = entityManager.find(StudentDTO.class,pk);
		return dto;
	}
	
	public List<StudentDTO> search(StudentDTO dto, int pageNo, int pageSize){
		
		List<StudentDTO> list = null;
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<StudentDTO> cq = builder.createQuery(StudentDTO.class);
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		Root<StudentDTO> qroot =  cq.from(StudentDTO.class);
		
		if(dto != null) {
			if(dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				predicateList.add(builder.like(qroot.get("firstName"),dto.getFirstName()+ "%"));
			}
	    }
		 cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		    
		    TypedQuery<StudentDTO> tq =  entityManager.createQuery(cq);
		    
		    if(pageSize > 0) {
		    	tq.setFirstResult(pageNo * pageSize);
		    	tq.setMaxResults(pageSize);
		    }
		    
		    list = tq.getResultList();
		    return list;  
	}

}
