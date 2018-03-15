package ma.najeh.ibnouzouhr.web;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import ma.najeh.ibnouzouhr.model.Student;
import ma.najeh.ibnouzouhr.service.TeacherService;
import ma.najeh.ibnouzouhr.util.FileUtil;
import ma.najeh.ibnouzouhr.util.XLSUtil;
import ma.najeh.ibnouzouhr.validator.TeacherValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ma.najeh.ibnouzouhr.model.Teacher;


import javax.validation.Valid;

import static ma.najeh.ibnouzouhr.constant.Constant.URL.*;

/***
 * @author bouazizi
 * 
 */

@Controller
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private TeacherValidator teacherValidator;

	@GetMapping(ADMIN_TEACHERS_ALL)
	public String all(Map<String, Object> model) {
		Iterable<Teacher> teachers = teacherService.findAll();
		model.put("teachers", teachers);
		return "/teachers/all";
	}

	@GetMapping(ADMIN_TEACHERS_ADD)
	public String add(Model model) {
		model.addAttribute("teacher",new Teacher());
		return "/teachers/add";
	}

	@PostMapping(ADMIN_TEACHERS_ADD)
	public String save(@Valid @ModelAttribute Teacher teacher, BindingResult bindingResult,Model model) {
		teacherValidator.validate(teacher,bindingResult);
		model.addAttribute("teacher",teacher);
		if (bindingResult.hasErrors()){
			return "/teachers/add";
		}
		model.addAttribute("teacher",teacherService.save(teacher));

		return "/teachers/add";
	}

	@GetMapping(ADMIN_TEACHERS_DELETE)
	public ModelAndView delete(ModelMap model, @PathVariable(value = "id") Long id) {
		teacherService.delete(id);
		return new ModelAndView("redirect:/admin/teachers/all", model);
	}

	@GetMapping(ADMIN_TEACHERS_EDIT)
	public String edit(Map<String, Object> model, @PathVariable(value = "id") Long id) {
		Teacher teacher = teacherService.findOne(id);
		model.put("teacher", teacher);
		return "/teachers/edit";
	}

	@PostMapping(ADMIN_TEACHERS_UPDATE)
	public ModelAndView update(ModelMap model, Teacher teacher) {
		teacherService.update(teacher);
		return new ModelAndView("redirect:"+ADMIN_TEACHERS_ALL, model);
	}

	@PostMapping(ADMIN_TEACHERS_ADD_XLS)
	public String save(@RequestParam(name = "teachers_xls_file") MultipartFile file) throws IOException {

		if (!file.isEmpty()) {
			File f = FileUtil.multipartToFile(file);
			Set<Teacher> teachers = XLSUtil.getTeachers(f);
			teacherService.save(teachers);
			f.delete();
		}

		return "redirect:" + ADMIN_TEACHERS_ALL;

	}

}
