package ins.ashokit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ins.ashokit.Entity.UserdtlsEntity;
import ins.ashokit.binding.LoginForm;
import ins.ashokit.binding.SignUpForm;
import ins.ashokit.binding.UnlockForm;
import ins.ashokit.repo.UserDtlsRepo;
import ins.ashokit.util.EmailUtil;
import ins.ashokit.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private HttpSession session;

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtil email;

	@Override
	public boolean signup(SignUpForm form) {

		UserdtlsEntity findByEmail = userDtlsRepo.findByEmail(form.getEmail());

		if (findByEmail != null) {
			return false;
		}

		// Todo:copying data from binding obj to entity object
		UserdtlsEntity entity = new UserdtlsEntity();
		BeanUtils.copyProperties(form, entity);

		// Todo: generate random password
		String temppwd = PwdUtils.generateRandomPwd();
		entity.setPwd(temppwd);

		// Todo: send account status as Locked

		entity.setAcc_status("LOCKED");

		// Todo:insert record

		userDtlsRepo.save(entity);

		// Todo:send the mail to unlock the account
		String to = form.getEmail();
		String subject = "Unlock your account | ashok IT";
		StringBuffer body = new StringBuffer("");
		body.append("<h2>use below temporary password  to unclok your account</h2>");
		body.append("temporary password: " + temppwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:9090/unlock?email=" + to + "\">click here to unlock");
		email.sendEmail(to, subject, body.toString());
		return true;
	}

	@Override
	public String login(LoginForm form) {
		
		UserdtlsEntity status = userDtlsRepo.findByEmailAndPwd(form.getEmail(),form.getPwd());
		if(status==null) {
			return "invalid creditntials";
		}
		if(status.getAcc_status().equals("LOCKED")) {
			return "your status is locked";
		}
		//create session and store user data
		 session.setAttribute("userId", status.getUserId());
		return "success";
	}

	@Override
	public boolean forgotpwd(String emailform) {
		//check the record with given email
		UserdtlsEntity findByEmail = userDtlsRepo.findByEmail(emailform);
		//if not record is not available then send  errr msg
		if(findByEmail==null) {			
			return false;
		}		
		//if record available send email for recovery pwd
		String subject="recovery pwd";
		String body="Your pwd::"+findByEmail.getPwd();		
		email.sendEmail(emailform, subject, body);
		
		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		UserdtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTemppdw())) {
			entity.setPwd(form.getNewpdw());
			entity.setAcc_status("UNLOCKED");
			userDtlsRepo.save(entity);
			return true;
		} else {

			return false;
		}

	}

}
