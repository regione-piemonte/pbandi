/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandiutil.common;

import org.springframework.beans.factory.annotation.Autowired;

public class TimerUtil {
	
	@Autowired
	private LoggerUtil logger;

	public Long start() {
		return System.currentTimeMillis();
	}

	public Boolean checkTimeout(Long timeout, Long time) {
		long currentTime = System.currentTimeMillis();
		boolean notTimedOut = (currentTime - time) <= timeout;

		logger.debug("Start time: " + time);
		logger.debug("Current time: " + currentTime);
		logger.debug("Differenza di tempo ammesso: " + timeout);
		logger.debug("Timeout non superato: " + notTimedOut);

		return notTimedOut;
	}

	public boolean isTimeoutNotOccurred(Long timeoutDuration, Long startTime) {
		return checkTimeout(timeoutDuration, startTime);
	}

	public boolean isTimeoutOccurred(Long timeoutDuration, Long startTime) {
		return !checkTimeout(timeoutDuration, startTime);
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

}
