package com.toptal.pushpendra.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toptal.pushpendra.entities.User;
import com.toptal.pushpendra.entities.UserDetail;
import com.toptal.pushpendra.entities.UserToken;
import com.toptal.pushpendra.exceptions.OAuth2AuthenticationProcessingException;
import com.toptal.pushpendra.mappers.UserDetailsMapper;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.models.OAuth2UserInfo;
import com.toptal.pushpendra.models.UserFilterModel;
import com.toptal.pushpendra.repositories.IUserDetailsRepository;
import com.toptal.pushpendra.repositories.IUserRepository;
import com.toptal.pushpendra.repositories.IUserTokenRepository;
import com.toptal.pushpendra.utilities.Constants;
import com.toptal.pushpendra.utilities.FilterProvider;

@Service
@Transactional
public class UserDetailServiceImpl implements IUserDetailService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserDetailsRepository userDetailsRepository;

	@Autowired
	private UserDetailsMapper userDetailsMapper;

	@Autowired
	private IUserTokenRepository tokenRepository;

	@Override
	public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User details = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(Constants.USER_NOT_EXIST));
		JwtUserDetails user = userDetailsMapper.userEntityToJwtUserDetails(details);
		return user;
	}

	@Override
	public JwtUserDetails registerNewUser(JwtUserDetails user) {
		User userToSave = userDetailsMapper.jwtUserDetailsToUserEntity(user);
		userToSave = userRepository.save(userToSave);
		JwtUserDetails jwtUserDetails = userDetailsMapper.userEntityToJwtUserDetails(userToSave);
		jwtUserDetails.setPassword(null);
		return jwtUserDetails;
	}

	@Override
	public JwtUserDetails updateUserDetails(JwtUserDetails user) {
		User existingUser = userRepository.getOne(user.getUsername());
		if (null != user.getPassword())
			existingUser.setPassword(user.getPassword());
		existingUser.setLocked(user.isLocked());
		UserDetail detailsToSave = userDetailsMapper.jwtUserDetailsToEntity(user);
		existingUser.setRoles(userDetailsMapper.authorityBoToAuthorityEntity(user.getAuthorities()));
		existingUser.setUserDetails(detailsToSave);
		userRepository.save(existingUser);
		user.setPassword(null);
		return user;
	}

	@Override
	public boolean deleteUser(String username) {
		userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(Constants.USER_NOT_EXIST));
		userRepository.deleteById(username);
		return true;
	}

	@Override
	public List<JwtUserDetails> getFilteredResults(Map<String, String> requestParams) {
		UserFilterModel model = userDetailsMapper.createFilterModel(requestParams);

		Specification<UserDetail> userDetailSpecification = FilterProvider.userDetailsSpecification(model);
		Pageable page = PageRequest.of(model.getNextPage(), model.getPageSize(), Sort.unsorted());

		Page<UserDetail> users = userDetailsRepository.findAll(userDetailSpecification, page);

		if (users.hasContent()) {
			List<JwtUserDetails> userList = users.stream().map(user -> {
				JwtUserDetails details = new JwtUserDetails();
				userDetailsMapper.populateWithUserDetails(details, user);
				User userEntity = userRepository.getOne(user.getUsername());
				details.setAuthorities(userDetailsMapper.authorityEntitytoAuthorityBo(userEntity.getRoles()));
				details.setProvider(userEntity.getProvider());
				details.setLocked(userEntity.isLocked());
				details.setEmailVerified(userEntity.isEmailVerified());
				return details;
			}).collect(Collectors.toList());
			return userList;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public void updateLockStatus(String username, boolean status) {
		User user = userRepository.getOne(username);
		user.setLocked(status);
		userRepository.save(user);
	}

	@Override
	public Optional<JwtUserDetails> findByEmailId(String email) {
		Optional<User> user = userRepository.findByEmailId(email);
		if (user.isPresent())
			return Optional.of(userDetailsMapper.userEntityToJwtUserDetails(user.get()));
		return Optional.of(new JwtUserDetails());
	}

	@Override
	public JwtUserDetails createOrUpdateOauthUser(OAuth2UserRequest oAuth2UserRequest, JwtUserDetails user,
			OAuth2UserInfo oAuth2UserInfo) {
		if (null != user && null != user.getEmailId()) {
			if (!user.getProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your "
								+ user.getProvider() + " account to login.");
			}
			return user;
			// if (userDetailsMapper.needUpdate(user, oAuth2UserInfo))
			// user = updateUserDetails(userDetailsMapper.oAuthUserToJwtUser(user,
			// oAuth2UserInfo));
		} else {
			user = registerNewUser(userDetailsMapper.oAuthUserToJwtUser(oAuth2UserRequest, oAuth2UserInfo));
		}
		return user;
	}

	@Override
	public void updateUserProfilePictureUrl(String url) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetail details = userDetailsRepository.getOne(username);
		details.setProfilePictureUrl(url);
		userDetailsRepository.save(details);
	}

	@Override
	public void saveAuthToken(String username, String token) {
		UserToken userToken = tokenRepository.findByUsername(username);
		if (null == userToken) {
			userToken = new UserToken();
			userToken.setUsername(username);
		}
		userToken.setToken(token);
		tokenRepository.save(userToken);
	}

	@Override
	public void deleteToken(String username, String token) {
		tokenRepository.deleteByUsernameAndToken(username, token);
	}

	@Override
	public boolean hasActiveSesion(String username, String jwtToken) {
		return tokenRepository.existsByUsernameAndToken(username, jwtToken);
	}

}
