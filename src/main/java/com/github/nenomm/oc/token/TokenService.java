package com.github.nenomm.oc.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;


@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Transactional
	public int deleteExpiredTokens() {

		return tokenRepository.deleteExpiredTokens(OffsetDateTime.now());
	}
}
