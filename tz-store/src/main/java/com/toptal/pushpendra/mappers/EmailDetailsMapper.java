package com.toptal.pushpendra.mappers;

import org.springframework.stereotype.Component;

import com.toptal.pushpendra.entities.EmailRequest;
import com.toptal.pushpendra.models.EmailData;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.utilities.Constants;

@Component
public class EmailDetailsMapper {

	public EmailData userDetailsToEmailData(JwtUserDetails details, EmailRequest request) {
		EmailData data = new EmailData();
		data.setUserFullName(details.getFirstName() + Constants.SINGLE_SPACE + details.getLastName());
		data.setUrl(Constants.BASE_URL + Constants.VERIFICATION_END_POINT + Constants.QUESTION_MARK
				+ Constants.ACCESS_TOKEN + Constants.EQUAL_SIGN + request.getToken());
		data.setRecipient(request.getEmailId());
		data.setSubject(Constants.VERIFICATION_SUBJECT);
		return data;
	}

	public EmailData userDetailsToEmailData(EmailRequest request) {
		EmailData data = new EmailData();
		data.setUrl(Constants.SIGNUP_URL);
		data.setRecipient(request.getEmailId());
		data.setSubject(Constants.INVITATION_SUBJECT);
		return data;
	}

}
