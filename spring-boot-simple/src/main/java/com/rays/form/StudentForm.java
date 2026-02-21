package com.rays.form;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;
import com.rays.dto.CollegeDTO;
import com.rays.dto.StudentDTO;

public class StudentForm extends BaseForm {
	
	@NotEmpty(message = "First name is required")
	private String firstName;
	
	@NotEmpty(message = "Last name is required")
	private String lastName;
	
	@NotNull(message = "Dob is required")
	private Date dob;
	
	@NotEmpty(message = "PhoneNo is required")
	private String phoneNo;

	@NotEmpty(message = "email is required")
	private String email;
	
	@NotNull(message = "collegeId is required")
	private Long collegeId;
	
	private String collegeName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	
	@Override
	public BaseDTO getDto() {
	  StudentDTO dto = (StudentDTO)	initDTO(new StudentDTO());
	  dto.setFirstName(firstName);
	  dto.setLastName(lastName);
	  dto.setEmail(email);
	  dto.setDob(dob);
	  dto.setPhoneNo(phoneNo);
	  dto.setCollegeId(collegeId);
	  dto.setCollegeName(collegeName);
	  return dto;
	}
	
}
