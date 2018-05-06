package com.github.nenomm.oc;

import com.github.nenomm.oc.city.CityDTO;
import com.github.nenomm.oc.token.TokenDTO;
import com.github.nenomm.oc.user.UserDTO;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

public class TestConstants {

	public static final String AUTH_HEADER = "Authorization";

	public static final String TEST_TOKEN = "testToken";

	public static final String TEST_USER_UUID = "testUUID";

	public static MediaType CONTENT_TYPE = new MediaType("application", "hal+json", Charset.forName("UTF-8"));

	public static CityDTO CITY_DTO = new CityDTO("TestCity", "TestDescription", 10);

	public static UserDTO NEW_USER_DTO = new UserDTO("newUser@test.net", "Pass123");

	public static UserDTO BAD_USER_DTO = new UserDTO("badUser(at)test.net", "Pass123");

	public static TokenDTO OK_TOKEN_DTO = new TokenDTO(TEST_TOKEN);

	public static TokenDTO UNKNOWN_TOKEN_DTO = new TokenDTO("huh?");
}
