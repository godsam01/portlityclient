package com.porlity.Controller;

import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.porlity.ejb.TeacherService;
import com.porlity.entity.Teacher;

@Controller
public class TeacherController {
	@EJB(mappedName = "ejb:/PorlityClient//TeacherServiceBean!com.porlity.ejb.TeacherService")
	TeacherService tcServ;

	@RequestMapping("/listTeacher")
	public ModelAndView listTeacher(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("listTeacher.jsp");
		List<Teacher> tcList;
		try {
			tcList = tcServ.getAllStudent();
			mv.addObject("tcList", tcList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;

	}

	@RequestMapping("/createTeacher")
	public ModelAndView createTeacher() {
		ModelAndView mv = new ModelAndView("teacherForm.jsp");
		Teacher tc = new Teacher();
		mv.addObject("tcStudent", tc);
		return mv;
	}

	@RequestMapping("/saveTeacher")
	public String saveTc(@ModelAttribute("teacher") Teacher teacher, BindingResult result,
			HttpServletRequest request) {
		// System.out.println("saving "+employee.getFirstname()+"
		// "+employee.getLastname());
		try {
			// employee is not existed, meaning it's new employee
			if (teacher.getTeacherId() == 0) {
				tcServ.insert(teacher);
				// employee is existed
			} else {
				tcServ.update(teacher);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:listStudent.do";
	}

	@RequestMapping("/editTeacher")
	public ModelAndView editTc(HttpServletRequest request) {
		int paramId = Integer.parseInt(request.getParameter("id"));
		Teacher foundTc;
		ModelAndView mv = new ModelAndView("teacherForm.jsp");
		try {
			foundTc = tcServ.findTeacher(paramId);
			mv.addObject("teacher", foundTc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping("/deleteTeacher")
	public String deleteTc(HttpServletRequest request) {
		tcServ.delete(Long.valueOf(request.getParameter("id")));
		return "redirect:listTeacher.do";
	}
}
