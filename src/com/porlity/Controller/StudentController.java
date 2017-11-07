package com.porlity.Controller;

import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.porlity.ejb.StudentService;
import com.porlity.entity.Student;

@Controller
public class StudentController {
	@EJB(mappedName = "ejb:/PorlityClient//StudentServiceBean!com.porlity.ejb.StudentService")
	StudentService stdServ;

	@RequestMapping("/listStudent")
	public ModelAndView listStudent(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("listStudent.jsp");
		List<Student> stdList;
		try {
			stdList = stdServ.getAllStudent();
			mv.addObject("stdList", stdList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;

	}

	@RequestMapping("/createStudent")
	public ModelAndView createStudent() {
		ModelAndView mv = new ModelAndView("studentForm.jsp");
		Student std = new Student();
		mv.addObject("stdStudent", std);
		return mv;
	}

	@RequestMapping("/saveStudent")
	public String saveStd(@ModelAttribute("student") Student student, BindingResult result,
			HttpServletRequest request) {
		// System.out.println("saving "+employee.getFirstname()+"
		// "+employee.getLastname());
		try {
			// employee is not existed, meaning it's new employee
			if (student.getStudentId() == 0) {
				stdServ.insert(student);
				// employee is existed
			} else {
				stdServ.update(student);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:listStudent.do";
	}

	@RequestMapping("/editStudent")
	public ModelAndView editStd(HttpServletRequest request) {
		int paramId = Integer.parseInt(request.getParameter("id"));
		Student foundStd;
		ModelAndView mv = new ModelAndView("studentForm.jsp");
		try {
			foundStd = stdServ.findStudent(paramId);
			mv.addObject("student", foundStd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping("/deleteStudent")
	public String deleteStd(HttpServletRequest request) {
		stdServ.delete(Long.valueOf(request.getParameter("id")));
		return "redirect:listStudent.do";
	}
}


