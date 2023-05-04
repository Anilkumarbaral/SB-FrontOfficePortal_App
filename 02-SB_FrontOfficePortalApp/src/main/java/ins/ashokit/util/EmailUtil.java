package ins.ashokit.util;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class EmailUtil {

	@Autowired
	JavaMailSender mailsender;

public boolean sendEmail(String to, String subject, String body) {

		boolean issent = false;
		try {
			MimeMessage MimeMessage = mailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(MimeMessage);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			mailsender.send(MimeMessage);
			issent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return issent;
	}

}
