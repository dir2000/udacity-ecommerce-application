package com.example.demo;

import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.security.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {
	@Autowired
	private MockMvc mockMvc;
	private String username = "Existed username";
	private String password = "Existed password";
	
	@Test
	public void loginWithRegisteredUser() throws Exception{
		CreateUserRequest request = createUserRequest(username, password);
		String registrationBody = objectToJSON(request);
		mockMvc.perform(MockMvcRequestBuilders.post(SecurityConstants.SIGN_UP_URL).content(registrationBody).contentType("application/json"))
				.andExpect(status().isOk());

		Map loginData = new HashMap();
		loginData.put("username", username);
		loginData.put("password", password);
		String loginBody = objectToJSON(loginData);
		mockMvc.perform(MockMvcRequestBuilders.post(SecurityConstants.SIGN_IN_URL).content(loginBody).contentType("application/json"))
				.andExpect(status().isOk());
	}

	private CreateUserRequest createUserRequest(String username, String password) {
		CreateUserRequest request = new CreateUserRequest();
		request.setUsername(username);
		request.setPassword(password);
		request.setConfirmPassword(password);
		return request;
	}

	private String objectToJSON(Object object) throws Exception{
		return new ObjectMapper().writeValueAsString(object);
	}

}
