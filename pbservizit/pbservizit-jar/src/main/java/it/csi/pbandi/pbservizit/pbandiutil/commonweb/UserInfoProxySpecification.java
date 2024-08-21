/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.commonweb;


import it.csi.pbandi.pbservizit.pbandiutil.common.ProxySpecification;
import it.csi.util.beanlocatorfactory.ServiceBeanLocator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class  UserInfoProxySpecification<T> extends  ProxySpecification<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Map<String, String> MAP_USER_INFO_TO_CONTESTO_IDENTIFICATIVO = new HashMap<String, String>();
	private boolean dirty = true;
	private T instance = null;
	private UserInfoHelper userInfoHelper = (UserInfoHelper) ServiceBeanLocator.getBeanByName("userInfoHelper");
	

	static {
		MAP_USER_INFO_TO_CONTESTO_IDENTIFICATIVO
				.put("idSoggetto", "idSoggetto");
		MAP_USER_INFO_TO_CONTESTO_IDENTIFICATIVO.put("codiceRuolo",
				"descBreveTipoAnagrafica");
	}



	@Override
	public T getInstance() throws InstantiationException,
			IllegalAccessException {
		if (instance == null) {
			this.instance = this.getContainedClass().newInstance();
		}
		return instance;
	}

	@Override
	public void preInvoke(Method method, T instance, Object[] args) {
		String methodName = method.getName();
		if (methodName.startsWith("set")
				&& !methodName.equals("setIdIride")) {
			dirty = true;
		} else if (dirty && methodName.equals("getIdIride")) {
			dirty = false;

			userInfoHelper.creaContestoIdentificativo(instance, null);
		}
	}

	@Override
	public void onInvocationException(Throwable exception) throws Exception {
		exception.printStackTrace();
		super.onInvocationException(exception);
	}
	
	
}
