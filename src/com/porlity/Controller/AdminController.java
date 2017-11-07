package com.porlity.Controller;

import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.porlity.ejb.AdminService;
import com.porlity.entity.Admin;

@Controller
public class AdminController {
	@EJB(mappedName = "ejb:/PorlityClient//AdminServiceBean!com.porlity.ejb.AdminService")
	AdminService amServ;

	@RequestMapping("/listAdmin")
	public ModelAndView listAdmin(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("listAdmin.jsp");
		List<Admin> amList;
		try {
			amList = amServ.getAllAdmin();
			mv.addObject("amList", amList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;

	}

	@RequestMapping("/createAdmin")
	public ModelAndView createAdmin() {
		ModelAndView mv = new ModelAndView("adminForm.jsp");
		Admin am = new Admin();
		mv.addObject("adAdmin", am);
		return mv;
	}

	@RequestMapping("/saveAdmin")
	public String saveAd(@ModelAttribute("admin") Admin admin, BindingResult result,
			HttpServletRequest request) {
		// System.out.println("saving "+employee.getFirstname()+"
		// "+employee.getLastname());
		try {
			// employee is not existed, meaning it's new employee
			if (admin.getAdminId() == 0) {
				amServ.insert(admin);
				// employee is existed
			} else {
				amServ.update(admin);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:listAdmin.do";
	}

	@RequestMapping("/editAdmin")
	public ModelAndView editAd(HttpServletRequest request) {
		int paramId = Integer.parseInt(request.getParameter("id"));
		Admin foundAd;
		ModelAndView mv = new ModelAndView("adminForm.jsp");
		try {
			foundAm = amServ.findAdmin(paramId);
			mv.addObject("admin", foundAm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping("/deleteAdmin")
	public String deleteAm(HttpServletRequest request) {
		amServ.delete(Long.valueOf(request.getParameter("id")));
		return "redirect:listAdmin.do";
	}
}
