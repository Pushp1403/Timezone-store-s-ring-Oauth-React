package com.toptal.pushpendra.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.models.OAuth2UserInfo;

@Service
public interface IUserDetailService extends UserDetailsService {

	JwtUserDetails registerNewUser(JwtUserDetails user);

	JwtUserDetails updateUserDetails(JwtUserDetails user);

	boolean deleteUser(String username);

	List<JwtUserDetails> getFilteredResults(Map<String, String> requestParams);

	@Override
	JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	void updateLockStatus(String username, boolean status);

	Optional<JwtUserDetails> findByEmailId(String email);

	JwtUserDetails createOrUpdateOauthUser(OAuth2UserRequest oAuth2UserRequest, JwtUserDetails user,
			OAuth2UserInfo oAuth2UserInfo);

	void updateUserProfilePictureUrl(String string);

	void saveAuthToken(String username, String token);

	void deleteToken(String username, String token);

	boolean hasActiveSesion(String username, String jwtToken);

}
