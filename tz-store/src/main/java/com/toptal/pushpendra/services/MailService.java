package com.toptal.pushpendra.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.toptal.pushpendra.entities.EmailRequest;
import com.toptal.pushpendra.mappers.EmailDetailsMapper;
import com.toptal.pushpendra.models.EmailData;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.repositories.IEmailRequestRepository;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class MailService {

	@Autowired
	private Configuration config;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private EmailDetailsMapper emailDetailsMapper;

	@Autowired
	private IEmailRequestRepository emailRequestRepository;

	public void sendRegistratiionVerificationEmail(JwtUserDetails details) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException, MessagingException {
		EmailRequest request = emailRequestRepository.findByEmailId(details.getEmailId());
		EmailData data = emailDetailsMapper.userDetailsToEmailData(details, request);
		Template template = config.getTemplate("emailconfirmation.ftl");
		sendmail(template, data);
	}

	private void sendmail(Template template, EmailData data) throws IOException, TemplateException, MessagingException {
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
		MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(data.getRecipient());
		helper.setText(text);
		helper.setSubject(data.getSubject());
		emailSender.send(message);
	}

	public void sendInvitationEmail(String email) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException, MessagingException {
		EmailRequest request = emailRequestRepository.findByEmailId(email);
		EmailData data = emailDetailsMapper.userDetailsToEmailData(request);
		Template template = config.getTemplate("emailnvitation.ftl");
		sendmail(template, data);
	}

}
