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

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.dto.AttachmentDTO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;
import com.rays.service.AttachmentService;

@Repository
public class UserDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	RoleDAO roleDao;
	
	@Autowired
	AttachmentService attachmentService;

	public void populate(UserDTO dto) {
		if (dto.getRoleId() != null && dto.getRoleId() > 0) {
			RoleDTO roleDto = roleDao.findByPk(dto.getRoleId());
			dto.setRoleName(roleDto.getName());
		}

	}

	public long add(UserDTO dto) {
		populate(dto);
		entityManager.persist(dto);
		return dto.getId();
	}

	public void update(UserDTO dto) {
		populate(dto);
		entityManager.merge(dto);

	}

	public void delete(UserDTO dto) {
		if (dto.getImageId() != null && dto.getImageId() > 0) {
			AttachmentDTO adto = attachmentService.findById(dto.getImageId());
			if (adto != null) {
				attachmentService.delete(adto.getId());
			}
		}

		entityManager.remove(dto);
	}

	public UserDTO findByPk(long pk) {
		UserDTO dto = entityManager.find(UserDTO.class, pk);
		return dto;
	}

	public UserDTO findByUniqueKey(String attribute, String value) {

		List<UserDTO> list = null;

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<UserDTO> cq = builder.createQuery(UserDTO.class);

		Root<UserDTO> qRoot = cq.from(UserDTO.class);

		Predicate condition = builder.equal(qRoot.get(attribute), value);

		cq.where(condition);

		TypedQuery<UserDTO> tq = entityManager.createQuery(cq);

		list = tq.getResultList();

		UserDTO dto = null;

		if (list.size() > 0) {

			dto = list.get(0);

		}

		return dto;
	}

	public List<UserDTO> search(UserDTO dto, int pageNo, int pageSize) {

		List<UserDTO> list = null;

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<UserDTO> cq = builder.createQuery(UserDTO.class);

		List<Predicate> pList = new ArrayList<Predicate>();

		Root<UserDTO> qroot = cq.from(UserDTO.class);

		if (dto != null) {
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				pList.add(builder.like(qroot.get("firstName"), dto.getFirstName() + "%"));

			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				pList.add(builder.like(qroot.get("lastName"), dto.getLastName() + "%"));

			}
			if (dto.getRoleId() != null && dto.getRoleId() > 0) {
				pList.add(builder.equal(qroot.get("roleId"), dto.getRoleId()));

			}

		}
		cq.where(pList.toArray(new Predicate[pList.size()]));

		TypedQuery<UserDTO> tquery = entityManager.createQuery(cq);

		if (pageSize > 0) {
			tquery.setFirstResult(pageNo * pageSize);
			tquery.setMaxResults(pageSize);

		}
		list = tquery.getResultList();
		return list;
	}

}