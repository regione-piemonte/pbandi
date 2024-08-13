/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandiutil.common;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.InvocationHandler;



public final class  PBandiInvocationHandler implements InvocationHandler, Serializable {

	private static final long serialVersionUID = 1L;
	transient private ProxySpecification spec = null;
	public <T extends Object> PBandiInvocationHandler (ProxySpecification<T> spec) {
		this.spec = spec;
	}
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result ;
		try {
			Object instance = spec.getInstance();
			spec.preInvoke(method, instance, args);
			result = method.invoke(instance, args);
			spec.postInvoke(method, instance, args);
		} catch (InvocationTargetException e) {
			Throwable ee = e.getTargetException();
			spec.onInvocationException(ee);
			throw ee;
		}
		return result;
	}	

}
