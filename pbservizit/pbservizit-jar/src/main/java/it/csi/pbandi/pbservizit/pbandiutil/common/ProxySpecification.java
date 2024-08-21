/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandiutil.common;

import java.lang.reflect.Method;

public abstract class ProxySpecification<T>  {
	private Class<T> containedClass;

	public final void setContainedClass(Class<T> containedClass) {
		this.containedClass = containedClass;
	}

	/**
	 * Utilizzato per indicare la classe dell'istanza da invocare
	 * 
	 * @return la classe dell'istanza
	 */
	public final Class<T> getContainedClass() {
		return this.containedClass;
	}

	/**
	 * Utilizzato per indicare il metodo di instanziazione dell'oggetto da
	 * rendere proxy
	 * 
	 * @return l'istanza dell'oggetto
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public abstract T getInstance() throws InstantiationException,
			IllegalAccessException;

	public void preInvoke(Method method, T instance, Object[] args) {
	};

	public void postInvoke(Method method, T instance, Object[] args) {
	};

	public void onInvocationException(Throwable exception) throws Exception {
	};
	
}
