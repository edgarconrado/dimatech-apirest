package com.jacarandalab.dimatech.apirest.helper;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.jacarandalab.dimatech.apirest.models.entity.Email;

@Service
public class MailHelper {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private  SpringTemplateEngine templateEngine;

	public void sendEmailDimatech(Email email) {

		// Send Email
		MimeMessageHelper mimeMessageHelper;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {

			Context context = new Context();
			context.setVariable("name", "Edgar");
			context.setVariable("signature", "Edgar C");
			context.setVariable("location", "Jiquilpan");
			context.setVariable("imageResourceName", "imageResourceName");

			String process = templateEngine.process("requerimiento-temlate", context);

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
			mimeMessageHelper.setTo(email.getTo());
			mimeMessageHelper.setFrom(email.getFrom());
			mimeMessageHelper.setSubject(email.getSubject());

			//mimeMessageHelper.addAttachment("logo.png", new ClassPathResource("static/img/logo512.png"));

			mimeMessageHelper.setText(process, true);

			javaMailSender.send(mimeMessage);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
