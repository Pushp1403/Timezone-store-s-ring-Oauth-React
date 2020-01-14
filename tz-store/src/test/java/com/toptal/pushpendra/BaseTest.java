package com.toptal.pushpendra;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.pushpendra.entities.User;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.repositories.IUserRepository;
import com.toptal.pushpendra.security.JwtTokenUtil;
import com.toptal.pushpendra.test.utils.MocksProvider;

public class BaseTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	public MocksProvider mocksProvider;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	public JwtTokenUtil tokenUtils;

	protected static final String REGISTRATION_PATH = "/auth/registerUser";
	protected static final String AUTHENTICATION_PATH = "/auth/authenticate";

	public ResultActions performPostRequest(String path, String data) throws Exception {
		return mockMvc.perform(
				post(path).content(data).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	public ResultActions performPutRequest(String path, String data) throws Exception {
		return mockMvc.perform(
				put(path).content(data).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	public ResultActions performSecurePutRequest(String path, String data, String token) throws Exception {
		return mockMvc.perform(put(path).header("Authorization", "Bearer " + token).content(data)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	public ResultActions performSecurePostRequest(String path, String data, String token) throws Exception {
		return mockMvc.perform(post(path).header("Authorization", "Bearer " + token).content(data)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void registerNewUser(String role) {
		JwtUserDetails user = mocksProvider.createMockUser(role);
		try {
			performPostRequest(REGISTRATION_PATH, asJsonString(user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerNewUnVerifiedUser(String role) {
		JwtUserDetails user = mocksProvider.createMockUser(role);
		user.setEmailVerified(false);
		try {
			performPostRequest(REGISTRATION_PATH, asJsonString(user));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUpAuthorizedUser(String role) {
		registerNewUser(role);
		User entity = userRepository.getOne("psharma");
		entity.setEmailVerified(true);
		userRepository.save(entity);
	}

	@SuppressWarnings("unchecked")
	public String authenticate(String role) {
		try {
			registerNewUser(role);
			ResultActions result = performPostRequest(AUTHENTICATION_PATH,
					asJsonString(mocksProvider.createLoginRequest()));
			result.andExpect(status().isOk());
			MvcResult res = result.andReturn();
			HashMap<String, String> token = (HashMap<String, String>) new ObjectMapper()
					.readValue(res.getResponse().getContentAsString(), Map.class);
			return token.get("token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
