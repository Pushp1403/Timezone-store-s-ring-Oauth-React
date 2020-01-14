package com.toptal.pushpendra.test.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.pushpendra.BaseTest;
import com.toptal.pushpendra.TzStoreApplication;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.test.utils.MocksProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TzStoreApplication.class)
public class UserControllerTest extends BaseTest {

	@Autowired
	private MocksProvider mocksProvider;

	private static final String UPDATE_USER_PATH = "/api/user/updateUser/";
	private static final String DELETE_USER_PATH = "/api/user/deleteUser/";
	private static final String RETRIVEVE_USERS = "/api/user/getUsers";
	private static final String ADD_NEW_USER = "/api/user/addNewUser";

	@Test
	public void testUserCanNotUpdate() {
		// Arrange
		String token = authenticate("ROLE_USER");
		JwtUserDetails user = mocksProvider.createMockUser("ROLE_USER");
		user.setFirstName("updatedFirstName");
		try {
			// Act
			ResultActions results = mockMvc
					.perform(put(UPDATE_USER_PATH + user.getUsername()).header("Authorization", "Bearer " + token)
							.contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)));

			// Assert
			results.andExpect(status().isForbidden());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAdminCanUpdateAndDeleteUser() {
		// Arrange
		JwtUserDetails user = mocksProvider.createMockUser("ROLE_ADMIN");
		user.setFirstName("updatedFirstName");
		String token = authenticate("ROLE_ADMIN");
		try {
			// Act
			ResultActions results = mockMvc
					.perform(put(UPDATE_USER_PATH + user.getUsername()).header("Authorization", "Bearer " + token)
							.contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)));

			HashMap<String, Object> details = new ObjectMapper()
					.readValue(results.andReturn().getResponse().getContentAsString(), HashMap.class);

			// Assert
			results.andExpect(status().isOk());
			assertEquals(details.get("firstName"), "updatedFirstName");

			// Cleanup
			mockMvc.perform(delete(DELETE_USER_PATH + "psharma").header("Authorization", "Bearer " + token))
					.andExpect(status().isOk());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRetrieveUser() {
		// Arrange
		String token = authenticate("ROLE_ADMIN");
		try {
			// Act
			ResultActions results = mockMvc.perform(get(RETRIVEVE_USERS).header("Authorization", "Bearer " + token));
			results.andExpect(status().isOk());
			ArrayList<HashMap<String, String>> data = new ObjectMapper()
					.readValue(results.andReturn().getResponse().getContentAsString(), ArrayList.class);

			// Assert
			assertNotNull(data);
			assertTrue(data.size() > 0);
			assertEquals("psharma", data.get(0).get("username"));

			// Clean
			mockMvc.perform(delete(DELETE_USER_PATH + "psharma").header("Authorization", "Bearer " + token))
					.andExpect(status().isOk());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAddUser() {
		// Arrange
		JwtUserDetails details = mocksProvider.createMockUser("ROLE_USER");
		details.setUsername("psharma2");
		details.setEmailId("psharma2@gmail.com");
		String token = authenticate("ROLE_ADMIN");

		// Act
		try {
			ResultActions results = mockMvc.perform(post(ADD_NEW_USER).contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(details)).header("Authorization", "Bearer " + token));
			HashMap<String, String> user = new ObjectMapper()
					.readValue(results.andReturn().getResponse().getContentAsString(), HashMap.class);

			// Assert
			results.andExpect(status().isOk());
			assertNotNull(user);
			assertEquals("psharma2", user.get("username"));

			// Cleanup
			mockMvc.perform(delete(DELETE_USER_PATH + "psharma2").header("Authorization", "Bearer " + token))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
