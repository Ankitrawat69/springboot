package com.rays.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.management.relation.RoleList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.ORSResponse;
import com.rays.dto.TestDTO;

@RestController
@RequestMapping(value = "ors")
public class OrsCtl {
	
	@GetMapping("display1")
	public TestDTO display1() {
		
		TestDTO dto = new TestDTO();
		dto.setId(1);
		dto.setFirstName("Ankit");
		dto.setLastName("Rawat");
		dto.setLogin("ankit@gmail.com");
		dto.setPassword("ankit@123");
		dto.setAddress("Indore");
		 return dto;
	}
	
	@GetMapping("display2")
	public ORSResponse display2() {
		ORSResponse res = new ORSResponse();
		   return res;
	}
	
	@GetMapping("display3")
	public ORSResponse display3() {
		
		ORSResponse res = new ORSResponse();
		Map errors = new HashMap();
		errors.put("firstName", "first name is required");
		errors.put("lastName", "last name is required");
		errors.put("loginId", "login id is required");
		errors.put("password", "password is required");
		
		res.addInputError(errors);
		return res;
	}
	
	@GetMapping("display4")
	public ORSResponse display4() {

		ORSResponse res = new ORSResponse();

		TestDTO dto = new TestDTO();
		dto.setId(1);
		dto.setFirstName("ankit");
		dto.setLastName("rawat");
		dto.setLogin("ankit@gmail.com");
		dto.setPassword("ankit@123");
		

		res.setSuccess(true);
		res.addData(dto);

		return res;
  }
	
	@GetMapping("display5")
	public ORSResponse display5() {
		
		ORSResponse res = new ORSResponse();
		
		RoleList roleList = new RoleList();
		 roleList.add("admin");
		 roleList.add("student");
		 roleList.add("Faculty");
		 roleList.add("kiosk");
		 roleList.add("College");
		 
		 res.addResult("roleList", roleList);
		 res.setSuccess(true);
		 return res;
	}
}
