package it.csi.pbandi.pbweb.pbandiutil.common;

import net.sf.cglib.proxy.Enhancer;


public class ProxyFactory  {
	private LoggerUtil logger;

	/**
	 * 
	 * @param spec
	 *            utilizzare proxyFactory.new ProxyObject()
	 * @param clazz
	 *            la classe dell'oggetto da richiamare
	 * @param classLoader
	 *            il class loader, solitamente getClass().getClassLoader()
	 * @return il risultato dell'invocazione remota
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getObject(final ProxySpecification<T> spec) {
		logger.begin();
		T newProxyInstance = null;
		try {
			newProxyInstance = (T) Enhancer.create(spec.getContainedClass(),new PBandiInvocationHandler(spec));
		} catch (Throwable t) {
			logger.fatal(
					"Impossibile creare un proxy per "
							+ spec.getContainedClass(), t);
		} finally {
			logger.end();
		}
		return newProxyInstance;
	}

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

}
