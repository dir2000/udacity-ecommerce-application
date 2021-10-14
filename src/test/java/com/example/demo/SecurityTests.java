package com.example.demo;

import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.security.SecurityConstants;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {
	@Autowired
	private MockMvc mockMvc;
	private String username = "Existed username";
	private String password = "Existed password";

	@BeforeAll
	public void beforeAll() throws Exception{
		CreateUserRequest request = createUserRequest(username, password);
		String body = objectToJSON(request);
		mockMvc.perform(MockMvcRequestBuilders.post(SecurityConstants.SIGN_UP_URL).content(body))
				.andExpect(status().isOk());
	}

	@Test
	public void contextLoads() {
	}

	private CreateUserRequest createUserRequest(String username, String password) {
		CreateUserRequest request = new CreateUserRequest();
		request.setUsername(username);
		request.setPassword(password);
		return request;
	}

	private String objectToJSON(Object object) throws Exception{
		return new ObjectMapper().writeValueAsString(object);
	}

}
