/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb;

import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.util.beanlocatorfactory.ServiceBeanLocator;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Cache<T> implements Serializable {
	private Map<String, Date> lastCallTimeMap = new HashMap<String, Date>();
	private Map<String, T> callCache = new HashMap<String, T>();
	private long cacheTimeout;
	transient protected LoggerUtil logger = (LoggerUtil) ServiceBeanLocator.getBeanByName("loggerUtil");

	public T get(Object... params) throws Exception {
		logger.begin();

		StringBuffer callId = new StringBuffer();
		for (Object object : params) {
			callId.append(object.toString());
		}

		String callIdString = callId.toString();
		logger.debug("call id: " + callIdString);

		Date lastCallTime = lastCallTimeMap.get(callIdString);
		logger.debug("last call time: " + lastCallTime);
		T result;
		if (lastCallTime == null
				|| lastCallTime.before(new Date(new Date().getTime()
						- getCacheTimeout()))) {
			logger.debug("cache miss (timeout value in ms: "
					+ getCacheTimeout() + "): invoking method...");
			result = getOnMiss(params);
			callCache.put(callIdString, result);
			lastCallTimeMap.put(callIdString, new Date());
		} else {
			logger.debug("cache hit (timeout value in ms: "
					+ getCacheTimeout() + ")");
			result = callCache.get(callIdString);
		}

		logger.debug("value read: " + result);

		logger.end();
		return result;
	}

	protected abstract T getOnMiss(final Object... params) throws Exception;
	
	

	public void setCacheTimeout(long cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}

	public long getCacheTimeout() {
		return cacheTimeout;
	}
}