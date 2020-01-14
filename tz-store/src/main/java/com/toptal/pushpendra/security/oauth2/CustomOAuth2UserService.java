package com.toptal.pushpendra.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.toptal.pushpendra.exceptions.OAuth2AuthenticationProcessingException;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.models.OAuth2UserInfo;
import com.toptal.pushpendra.models.OAuth2UserInfoFactory;
import com.toptal.pushpendra.models.UserPrincipal;
import com.toptal.pushpendra.services.IUserDetailService;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private IUserDetailService userdetailService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		Optional<JwtUserDetails> userOptional = userdetailService.findByEmailId(oAuth2UserInfo.getEmail());
		JwtUserDetails user = userdetailService.createOrUpdateOauthUser(oAuth2UserRequest, userOptional.get(),
				oAuth2UserInfo);

		return UserPrincipal.create(user, oAuth2User.getAttributes());
	}

}
