package com.github.nenomm.oc.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nenomm.oc.OpenCityApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.github.nenomm.oc.TestConstants.BAD_USER_DTO;
import static com.github.nenomm.oc.TestConstants.NEW_USER_DTO;
import static com.github.nenomm.oc.TestConstants.OK_TOKEN_DTO;
import static com.github.nenomm.oc.TestConstants.UNKNOWN_TOKEN_DTO;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenCityApp.class)
public class UserManagementIntegrationTest {

	private MockMvc restMockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	protected WebApplicationContext wac;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		this.restMockMvc = webAppContextSetup(wac).apply(springSecurity()).build();
	}

	@Test
	@Transactional
	public void checkRegisterUserBadEmail() throws Exception {

		this.restMockMvc
				.perform(post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsString(BAD_USER_DTO)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	public void checkRegisterUser() throws Exception {

		this.restMockMvc
				.perform(post("/v1/users")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsString(NEW_USER_DTO)))
				.andExpect(status().isCreated());
	}

	@Test
	@Transactional
	public void checkAcquireTokenMissingUser() throws Exception {

		this.restMockMvc
				.perform(post("/v1/token")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsString(NEW_USER_DTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void checkAcquireToken() throws Exception {

		checkRegisterUser();

		this.restMockMvc
				.perform(post("/v1/token")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsString(NEW_USER_DTO)))
				.andExpect(status().isOk());
	}

	@Test
	@Transactional
	public void checkLogoutUnknownToken() throws Exception {

		this.restMockMvc
				.perform(delete("/v1/token")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsString(UNKNOWN_TOKEN_DTO)))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void checkLogout() throws Exception {

		this.restMockMvc
				.perform(delete("/v1/token")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsString(OK_TOKEN_DTO)))
				.andExpect(status().isOk());
	}
}
