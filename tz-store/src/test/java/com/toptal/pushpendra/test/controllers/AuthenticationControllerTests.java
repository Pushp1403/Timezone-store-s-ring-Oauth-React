package com.toptal.pushpendra.test.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.toptal.pushpendra.BaseTest;
import com.toptal.pushpendra.TzStoreApplication;
import com.toptal.pushpendra.models.JwtTokenRequest;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.test.utils.MocksProvider;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TzStoreApplication.class)
public class AuthenticationControllerTests extends BaseTest {

	@Autowired
	private MocksProvider mocksProvider;

	private static final String AUTHENTICATION_PATH = "/auth/authenticate";

	@Test
	public void testRegistrationWithMissingDetails() {
		JwtUserDetails user = mocksProvider.createMockUser("ROLE_USER");
		user.setFirstName(null);
		try {
			ResultActions result = performPostRequest(REGISTRATION_PATH, asJsonString(user));
			result.andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAuthenticationWithMissingDetails() {
		JwtTokenRequest request = mocksProvider.createLoginRequest();
		request.setUsername(null);
		try {
			ResultActions results = performPostRequest(AUTHENTICATION_PATH, asJsonString(request));
			results.andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testAuthenticationForNonExistingUser() {
		JwtTokenRequest loginRequest = mocksProvider.createLoginRequest();
		loginRequest.setUsername("notExist");
		try {
			ResultActions results = performPostRequest(AUTHENTICATION_PATH, asJsonString(loginRequest));
			results.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testLoginWithoutEmailVerification() {
		JwtTokenRequest loginRequest = mocksProvider.createLoginRequest();
		try {
			registerNewUnVerifiedUser("ROLE_USER");
			ResultActions authenticationResults = performPostRequest(AUTHENTICATION_PATH, asJsonString(loginRequest));
			authenticationResults.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
