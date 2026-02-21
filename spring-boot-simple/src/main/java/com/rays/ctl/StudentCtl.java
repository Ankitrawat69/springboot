package com.rays.ctl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.CollegeDTO;
import com.rays.dto.StudentDTO;
import com.rays.form.CollegeForm;
import com.rays.form.StudentForm;
import com.rays.service.StudentService;

@RestController
@RequestMapping(value = "Student")
public class StudentCtl extends BaseCtl {
	
	@Autowired
	StudentService studentService;

	@PostMapping("save")
	public ORSResponse save(@RequestBody @Valid StudentForm form, BindingResult bindingResult ) {
		
		ORSResponse res = new ORSResponse();
		
		res = validate(bindingResult);
		 if(!res.isSuccess()) {
			 return res;
		 }
		 
		 StudentDTO dto = new StudentDTO();
		 dto = (StudentDTO) form.getDto();
		 
		 long id = studentService.add(dto);
		 
		 res.addMessage("student added successfully");
		 res.setSuccess(true);
		 res.addData(dto);
		 
		 return res;
	}
	
	@PostMapping("update")
	public ORSResponse update(@RequestBody StudentForm form) {
		
		ORSResponse res = new ORSResponse();
		StudentDTO dto = new StudentDTO();
		
	    dto = (StudentDTO) form.initDTO(dto);
	    dto = (StudentDTO) form.getDto();
	    
	    studentService.update(dto);
	    res.setSuccess(true);
		res.addMessage("Student update successfully");

		return res;
	}
		
	@PostMapping("delete/{ids}")
	public ORSResponse delete(@PathVariable(required = false)long []ids) {
		ORSResponse res = new ORSResponse();
		if(ids != null && ids.length > 0){
			for(long id : ids) {
			  studentService.delete(id);
			  res.setSuccess(true);
			  res.addMessage("student deleted successfully");
			}
		}else {
			res.addMessage("select atleast one record");
		}
		return res;
	}
	
	@GetMapping("get/{id}")
	public ORSResponse get(@PathVariable(required = false)long id) {
		
		ORSResponse res = new ORSResponse();
	    StudentDTO dto = studentService.findById(id);
	    
	    if(dto != null) {
	    	res.addData(dto);
	    	res.setSuccess(true);
	    }
	    return res;
	}
	@RequestMapping(value = "search/{pageNo}", method = {RequestMethod.GET, RequestMethod.POST})
	public ORSResponse search(@RequestBody CollegeForm form, @PathVariable (required = false)  int pageNo) {
		
		ORSResponse res = new ORSResponse();
	    StudentDTO dto = (StudentDTO) form.getDto();
	    
	    int pageSize = 5;
	    
	    List<StudentDTO> list = studentService.search(dto, pageNo, pageSize);
	        if(list.size() > 0) {
	        	res.setSuccess(true);
	        	res.addData(list);
	        	return res;
	        }
	        return res;
   }
}
