package com.github.nenomm.oc;

import com.github.nenomm.oc.city.CityDTO;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

public class TestConstants {

	public static final String AUTH_HEADER = "Authorization";

	public static final String TEST_TOKEN = "testToken";

	public static final String TEST_USER_UUID = "testUUID";

	public static MediaType CONTENT_TYPE = new MediaType("application", "hal+json", Charset.forName("UTF-8"));

	public static CityDTO CITY_DTO = new CityDTO("TestCity", "TestDescription", 10);
}
