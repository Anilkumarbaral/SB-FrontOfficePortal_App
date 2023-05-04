package ins.ashokit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ins.ashokit.binding.LoginForm;
import ins.ashokit.binding.SignUpForm;
import ins.ashokit.binding.UnlockForm;
import ins.ashokit.service.UserService;

@Controller
public class UserController {
	
	@Autowired
    private HttpSession session;
	@Autowired
	private UserService userservice;

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";

	}

	@PostMapping("/signup")
	public String handlingSignup(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = userservice.signup(form);

		if (status) {
			model.addAttribute("succmsg", "chek your Email");

		} else {
			model.addAttribute("errmsg", "this email id already registered ..give the uniq email id....");
		}
		return "signup";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";

	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginform, Model model) {
		System.out.println(loginform);
		String status = userservice.login(loginform);
		if (status.contains("success")) {

			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg", status);
		return "login";

	}

	@GetMapping("/forgot")
	public String forgetPage() {

		return "forgotPwd";
	}

	@PostMapping("/forgotpwd")
	public String forgotpwd(@RequestParam("email") String email, Model model) {

		System.out.println(email);
		boolean status = userservice.forgotpwd(email);
		if(status) {
			model.addAttribute("success","pwd sent your email check");
		}else {
			model.addAttribute("errMsg","invalid email id");
		}
		
		return "forgotPwd";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);
		model.addAttribute("unlock", unlockFormObj);

		return "unlock";

	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {
		System.out.println(unlock);
		if (unlock.getNewpdw().equals(unlock.getConfirmpdw())) {
			boolean status = userservice.unlockAccount(unlock);
			if (status) {
				model.addAttribute("succMsg", "ur account successfully unlocked..");

			} else {
				model.addAttribute("errMsg", "Given temp password is incorrect check your mail ..");
			}
		} else {

			model.addAttribute("errMsg", "new pwd and confirm pwd should be same me..");
		}

		return "unlock";
	}

}
