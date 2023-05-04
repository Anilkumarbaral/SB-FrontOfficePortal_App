package ins.ashokit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ins.ashokit.binding.DashboardResponse;
import ins.ashokit.binding.EnquiryForm;
import ins.ashokit.binding.EnquirySearchCriteria;
import ins.ashokit.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enquiryService;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		// todo : to get fetch the dashboard data
		Integer userid = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enquiryService.getDashboardData(userid);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@PostMapping("/addEnq")
	public String adenquirypage(@ModelAttribute("enqformObj") EnquiryForm enqformObj, Model model) {
		System.out.println(enqformObj);
		// todo:save data logic
		boolean status = enquiryService.upsertEnquiry(enqformObj);

		if (status) {
			model.addAttribute("succMsg", "Data Saved..");
		} else {
			model.addAttribute("errMsg", "some problem occured");
		}
		List<String> courseName = enquiryService.getCourseName();
		List<String> enqStatus = enquiryService.getEnqStatus();
		model.addAttribute("courseName", courseName);
		model.addAttribute("enqStatus", enqStatus);
		return "add-enquiry";
	}

	@GetMapping("/enquiry")
	public String enquiryPage(Model model) {
		// todo:get courses for dropdown mode

		List<String> courseName = enquiryService.getCourseName();

		// todo:get equiry satus for dropdown mode
		List<String> enqStatus = enquiryService.getEnqStatus();

		// crate binding model obj
		EnquiryForm enqformObj = new EnquiryForm();

		model.addAttribute("courseName", courseName);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("enqformObj", enqformObj);

		return "add-enquiry";
	}

	@GetMapping("/enquires")
	public String viewEnquiryPage(EnquirySearchCriteria criteria,Model model) {
		
		

		return "view-enquiries";
	}

}
