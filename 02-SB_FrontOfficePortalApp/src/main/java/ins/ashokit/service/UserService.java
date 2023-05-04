package ins.ashokit.service;

import ins.ashokit.binding.LoginForm;
import ins.ashokit.binding.SignUpForm;
import ins.ashokit.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginForm form);
	public boolean signup(SignUpForm form);
	public boolean unlockAccount(UnlockForm form);
	public boolean forgotpwd(String email);

}
