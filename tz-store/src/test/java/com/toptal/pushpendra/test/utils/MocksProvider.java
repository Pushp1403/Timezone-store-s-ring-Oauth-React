package com.toptal.pushpendra.test.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.toptal.pushpendra.models.AuthorityBO;
import com.toptal.pushpendra.models.JwtTokenRequest;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.models.TimeZoneBO;

@Component
public class MocksProvider {

	public JwtUserDetails createMockUser(String role) {
		JwtUserDetails details = new JwtUserDetails();
		details.setAuthorities(this.createAuthorities(role));
		details.setEmailId("pushp1403@gmail.com");
		details.setFirstName("Pushpendra");
		details.setLastName("Sharma");
		details.setPassword("testPassword");
		details.setUsername("psharma");
		details.setProvider("local");
		details.setEmailVerified(true);
		return details;
	}

	private List<AuthorityBO> createAuthorities(String role) {
		List<AuthorityBO> authorityBOs = new ArrayList<AuthorityBO>();
		AuthorityBO bo = new AuthorityBO();
		bo.setUsername("psharma");
		bo.setRole(role);
		authorityBOs.add(bo);
		return authorityBOs;
	}
	
	public JwtTokenRequest createLoginRequest() {
		JwtTokenRequest request = new JwtTokenRequest("psharma", "testPassword");
		return request;
	}

	public TimeZoneBO createTimeZone() {
		TimeZoneBO bo = new TimeZoneBO();
		bo.setName("testTimeZone");
		bo.setCity("Glasgow");
		bo.setDifferenceFromGMT(9300f);
		bo.setTimeZoneRegion("UK/EUROPE");
		return bo;
	}
}
