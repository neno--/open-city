package com.github.nenomm.oc.token;

import com.github.nenomm.oc.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredTokensTask {

	private Logger logger = LoggerFactory.getLogger(DeleteExpiredTokensTask.class);

	@Autowired
	TokenService tokenService;

	// todo: this scheduler does not sync in cluster. multiple nodes will repeat the same task.
	@Scheduled(initialDelayString = "${oc.token.clean-up-job-initial-delay}",
			fixedRateString = "${oc.token.clean-up-job-fixed-rate}")
	public void deleteExpiredTokens() {

		logger.info("deleting expired tokens from the database");

		int count = tokenService.deleteExpiredTokens();

		if (count > 0) {
			logger.info("deleted {} expired tokens", count);
		}
	}
}
