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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.github.nenomm.oc.TestConstants.AUTH_HEADER;
import static com.github.nenomm.oc.TestConstants.CITY_DTO;
import static com.github.nenomm.oc.TestConstants.CONTENT_TYPE;
import static com.github.nenomm.oc.TestConstants.TEST_TOKEN;
import static com.github.nenomm.oc.TestConstants.TEST_USER_UUID;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenCityApp.class)
public class FavoritesIntegrationTest {

	private MockMvc restMockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	protected WebApplicationContext wac;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		this.restMockMvc = webAppContextSetup(wac).apply(springSecurity()).build();

		prepareCity();
	}

	@Transactional
	public void prepareCity() throws Exception {

		this.restMockMvc
				.perform(post("/v1/cities").contentType(MediaType.APPLICATION_JSON_UTF8)
						.header(AUTH_HEADER, TEST_TOKEN)
						.content(mapper.writeValueAsString(CITY_DTO)))
				.andExpect(status().isCreated()).andExpect(content().contentType(CONTENT_TYPE));

	}

	@Test
	@Transactional
	public void checkAddFavoriteNoToken() throws Exception {

		this.restMockMvc
				.perform(post("/v1/users/{user_uuid}/favorites", TEST_USER_UUID)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(mapper.writeValueAsString(CITY_DTO)))
				.andExpect(status().isForbidden());
	}

	@Test
	@Transactional
	public void checkAddFavoriteTestToken() throws Exception {

		this.restMockMvc
				.perform(post("/v1/users/{user_uuid}/favorites", TEST_USER_UUID)
						.contentType(MediaType.APPLICATION_JSON_UTF8).header(AUTH_HEADER, TEST_TOKEN)
						.content(mapper.writeValueAsString(CITY_DTO)))
				.andExpect(status().isOk());

		this.restMockMvc
				.perform(get("/v1/users/{user_uuid}/favorites", TEST_USER_UUID)
						.contentType(MediaType.APPLICATION_JSON_UTF8).header(AUTH_HEADER, TEST_TOKEN))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0]['name']", equalTo(CITY_DTO.getName())));
	}

	@Test
	@Transactional
	public void checkRemoveFavoriteTestToken() throws Exception {

		checkAddFavoriteNoToken();

		this.restMockMvc
				.perform(delete("/v1/users/{user_uuid}/favorites", TEST_USER_UUID)
						.contentType(MediaType.APPLICATION_JSON_UTF8).header(AUTH_HEADER, TEST_TOKEN)
						.content(mapper.writeValueAsString(CITY_DTO)))
				.andExpect(status().isOk());

		this.restMockMvc
				.perform(get("/v1/users/{user_uuid}/favorites", TEST_USER_UUID)
						.contentType(MediaType.APPLICATION_JSON_UTF8).header(AUTH_HEADER, TEST_TOKEN))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", empty()));

	}
}
