package com.github.nenomm.oc.config;

import com.github.nenomm.oc.city.City;
import com.github.nenomm.oc.city.CityRepository;
import com.github.nenomm.oc.token.Token;
import com.github.nenomm.oc.token.TokenRepository;
import com.github.nenomm.oc.user.Password;
import com.github.nenomm.oc.user.User;
import com.github.nenomm.oc.user.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
//@Profile("test")
public class DbInitializer {

	private Logger logger = LoggerFactory.getLogger(DbInitializer.class);

	private CityRepository cityRepository;

	private UserRepository userRepository;

	private TokenRepository tokenRepository;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	AppProperties appProperties;

	@Autowired
	public DbInitializer(CityRepository cityRepository, UserRepository userRepository, TokenRepository tokenRepository) {
		this.cityRepository = cityRepository;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
	}

	@PostConstruct
	private void initialize() {
		logger.info("Starting DB init...");

		Resource citiesSource = resourceLoader.getResource("classpath:default_city_data.csv");
		if (citiesSource.exists()) {
			readDefaultCities(citiesSource).forEach(cityRepository::save);
		}

		User testUser = new User("test@test.net", Password.getNew("Test123"), "testUUID");
		userRepository.save(testUser);

		Token testToken = new Token(testUser, OffsetDateTime.now().plusSeconds(appProperties.getToken().getLifetime()), "testToken");
		tokenRepository.save(testToken);

		logger.info("DB init finished.");
	}

	private List<City> readDefaultCities(Resource citiesSource) {
		List<City> cities = new ArrayList<City>(10);

		try {
			Reader in = new FileReader(citiesSource.getFile());
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

			for (CSVRecord record : records) {
				cities.add(new City(record.get(0), record.get(1), Integer.parseInt(record.get(2))));
			}
		} catch (IOException e) {
			logger.error("Error while loading default city file.");
		}

		return cities;
	}
}
