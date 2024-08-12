package it.csi.pbandi.pbweb.pbandisrv.business.manager;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbweb.pbandisrv.dto.manager.ConfigurationDTO;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.TimerUtil;

public class TimerManager {
	
	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private TimerUtil timerUtil;
	
	@Autowired
	private BeanUtil beanUtil;

	@Autowired
	private ConfigurationManager configurationManager;

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setTimerUtil(TimerUtil timerUtil) {
		this.timerUtil = timerUtil;
	}

	public TimerUtil getTimerUtil() {
		return timerUtil;
	}

	public void setConfigurationManager(
			ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

	public Long start() {
		return getTimerUtil().start();
	}

	public Boolean checkTimeout(Long time) {
		try {
			StackTraceElement trace[] = new Throwable().getStackTrace();
			String methodName = trace[1].getMethodName();
			
			return timerUtil.checkTimeout(getTimeout(methodName), time);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return true;
		}
	}

	public Long getTimeout(String methodName) {
		logger.debug("Timeout for methodName=[" + methodName + "]");
		ConfigurationDTO configuration = configurationManager
				.getConfiguration();
		Long result = getBeanUtil().getPropertyValueByName(configuration, methodName + "Timeout", Long.class);
		result = result == null ? configuration.getGenericTimeout()	: result;
		
		logger.info("Timeout configuration value: " + result);
		return result;
	}

	public Long getTimeout() {
		StackTraceElement trace[] = new Throwable().getStackTrace();
		String methodName = trace[1].getMethodName();

		return getTimeout(methodName);
	}
	
}
