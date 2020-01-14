package com.toptal.pushpendra.test.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.pushpendra.BaseTest;
import com.toptal.pushpendra.TzStoreApplication;
import com.toptal.pushpendra.models.TimeZoneBO;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = TzStoreApplication.class)
public class TimezoneControllerTests extends BaseTest {

	private static final String CREATE_TIMEZONE = "/api/timeZone/createNewTimeZone";
	private static final String UPDATE_TIMEZONE = "/api/timeZone/updateTimeZone";
	private static final String DELETE_TIMEZONE = "/api/timeZone/deleteTimeZone/";
	private static final String RETRIEVE_TIMEZONES = "/api/timeZone/retrievetimezones";

	private String token;
	private String timezoneId;

	@Before
	public void setUp() {
		this.token = authenticate("ROLE_USER");
	}

	@After
	public void cleanUp() {
		try {
			mockMvc.perform(delete(DELETE_TIMEZONE + this.timezoneId).header("Authorization", "Bearer " + this.token));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCreateTimeZone() {
		// Arrange
		TimeZoneBO timezone = mocksProvider.createTimeZone();
		try {
			// Act
			ResultActions results = performSecurePostRequest(CREATE_TIMEZONE, asJsonString(timezone), this.token);
			HashMap<String, Object> data = new ObjectMapper()
					.readValue(results.andReturn().getResponse().getContentAsString(), HashMap.class);

			// Assert
			results.andExpect(status().isOk());
			assertNotNull(data);
			assertNotNull(data.get("timeZoneId"));
			assertEquals("testTimeZone", data.get("name"));

			// cleanup
			this.timezoneId = (String) data.get("timeZoneId");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateTimeZone() {
		// Arrange
		TimeZoneBO timezone = mocksProvider.createTimeZone();
		try {
			// Act
			ResultActions results = performSecurePostRequest(CREATE_TIMEZONE, asJsonString(timezone), this.token);
			HashMap<String, Object> data = new ObjectMapper()
					.readValue(results.andReturn().getResponse().getContentAsString(), HashMap.class);

			timezone.setTimeZoneId((Integer) (data.get("timeZoneId")));
			timezone.setName("updatedName");
			timezone.setCity("NewCity");

			ResultActions updatedResults = performSecurePutRequest(UPDATE_TIMEZONE, asJsonString(timezone), this.token);
			HashMap<String, String> updatedData = new ObjectMapper()
					.readValue(updatedResults.andReturn().getResponse().getContentAsString(), HashMap.class);

			// Assert
			updatedResults.andExpect(status().isOk());
			assertEquals("updatedName", updatedData.get("name"));
			assertEquals("NewCity", updatedData.get("city"));

			// cleanup
			this.timezoneId = (String) data.get("timeZoneId");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRetrieveTimeZones() {
		// Arrange
		TimeZoneBO timezone = mocksProvider.createTimeZone();
		try {
			// Act
			performSecurePostRequest(CREATE_TIMEZONE, asJsonString(timezone), this.token);
			ResultActions results = performSecurePostRequest(RETRIEVE_TIMEZONES, asJsonString(timezone), this.token);
			ArrayList<HashMap<String, Object>> data = new ObjectMapper()
					.readValue(results.andReturn().getResponse().getContentAsString(), ArrayList.class);

			// Assert
			results.andExpect(status().isOk());
			assertNotNull(data);
			assertTrue(data.size() == 1);
			assertEquals(data.get(0).get("name"), "testTimeZone");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
