package com.rays.ctl;

import java.io.OutputStream;
import java.net.http.HttpResponse;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.AttachmentDTO;
import com.rays.dto.UserDTO;
import com.rays.form.UserForm;
import com.rays.service.AttachmentService;
import com.rays.service.UserService;

@RestController
@RequestMapping(value = "User")
public class UserCtl extends BaseCtl {

	@Autowired
	UserService userService;

	@Autowired
	public AttachmentService attachmentService;

	@PostMapping("save")
	public ORSResponse save(@RequestBody @Valid UserForm form, BindingResult bindingResult) {

		ORSResponse res = new ORSResponse();

		res = validate(bindingResult);
		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = new UserDTO();

		dto = (UserDTO) form.getDto();

		long id = userService.add(dto);

		res.setSuccess(true);
		res.addMessage("User addedd successfully");
		res.addData(dto);

		return res;

	}

	@PostMapping("update")
	public ORSResponse update(@RequestBody UserForm form) {

		ORSResponse res = new ORSResponse();
		UserDTO dto = new UserDTO();

		dto = (UserDTO) form.initDTO(dto);
		dto = (UserDTO) form.getDto();

		userService.update(dto);

		res.setSuccess(true);
		res.addMessage("User update successfully");

		return res;

	}

	@PostMapping("delete/{ids}")
	public ORSResponse delete(@PathVariable(required = false) long[] ids) {
		ORSResponse res = new ORSResponse();

		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				userService.delete(id);
				res.addMessage("User deleted successfully");
				res.setSuccess(true);
			}
		} else {
			res.addMessage("select at least one record");
		}
		return res;
	}

	@GetMapping("get/{id}")
	public ORSResponse get(@PathVariable(required = false) long id) {

		ORSResponse res = new ORSResponse();

		UserDTO dto = userService.findById(id);

		if (dto != null) {
			res.addData(dto);
			res.setSuccess(true);
		}
		return res;
	}

	@RequestMapping(value = "/search/{pageNo}", method = { RequestMethod.GET, RequestMethod.POST })
	public ORSResponse search(@RequestBody UserForm form, @PathVariable(required = false) int pageNo) {

		ORSResponse res = new ORSResponse();

		UserDTO dto = (UserDTO) form.getDto();

		int pageSize = 5;

		List<UserDTO> list = userService.search(dto, pageNo, pageSize);

		if (list.size() > 0) {
			res.setSuccess(true);
			res.addData(list);
			return res;
		}
		return res;
	}

	@PostMapping("/profilePic/{userId}")
	public ORSResponse uploadPic(@PathVariable long userId, @RequestParam("file") MultipartFile file,
			HttpServletRequest req) {

		AttachmentDTO attachmentdto = new AttachmentDTO(file);

		attachmentdto.setDescription("profile pic");

		attachmentdto.setUserId(userId);

		UserDTO userDto = userService.findById(userId);

		if (userDto.getImageId() != null && userDto.getImageId() > 0) {
			attachmentdto.setId(userDto.getImageId());
		}

		long imageId = attachmentService.save(attachmentdto);

		if (userDto.getImageId() == null) {
			userDto.setImageId(imageId);
			userService.update(userDto);
		}
		ORSResponse res = new ORSResponse();
		res.addResult("imageId", imageId);
		res.addResult("userId", userId);
		res.setSuccess(true);

		return res;
	}

	@GetMapping("/profilePic/{userId}")
	public void downloadPic(@PathVariable Long userId, HttpServletResponse response) {

		try {

			UserDTO userDto = userService.findById(userId);

			AttachmentDTO attachmentDTO = null;

			if (userDto != null) {
				attachmentDTO =attachmentService.findById(userDto.getImageId());
			}
			if (attachmentDTO != null) {
				response.setContentType(attachmentDTO.getType());
				OutputStream out = response.getOutputStream();
				out.write(attachmentDTO.getDoc());
				out.close();
			} else {

				response.getWriter().write("ERROR: File not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}