/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.util.validation;

import it.csi.pbandi.pbweb.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.LoggerUtil;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ValidatorInput {

	
	static LoggerUtil  logger=new LoggerUtil();
	/**
	 * Controlla che tutti i parametri variabili in input non siano nulli,in tal
	 * caso lancia un'eccezione che contiene l'elenco dei parametri nulli
	 * 
	 * @param nameParameter
	 * @param obj
	 * @throws FormalParameterException
	 */
	public static void verifyNullValue(String[] nameParameter, Object... obj)
			throws FormalParameterException {

		boolean nameParameterNull = false;

		if (nameParameter == null || nameParameter.length != obj.length) {
			nameParameterNull = true;
		}
		StringBuilder elencocampinulli = new StringBuilder(
				"I seguenti parametri di input sono nulli: ");
		List<Object> listaParametriNulli = new ArrayList<Object>();
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] == null) {
				listaParametriNulli.add(obj[i]);
				if (!nameParameterNull)
					elencocampinulli.append("[" + nameParameter[i] + "] ");
			}
		}

		if (listaParametriNulli.size() > 0) {
			throw new FormalParameterException(elencocampinulli.toString());
		}
	}

	/**
	 * Controlla che tra i parametri variabili in input ce ne sia almeno uno non
	 * nullo,in caso contrario lancia un'eccezione che contiene l'elenco dei
	 * parametri nulli
	 * 
	 * @param nameParameter
	 * @param obj
	 * @throws FormalParameterException
	 */
	public static void verifyAtLeastOneNotNullValue(String[] nameParameter,
			Object... obj) throws FormalParameterException {

		boolean nameParameterNull = false;

		if (nameParameter == null || nameParameter.length != obj.length) {
			nameParameterNull = true;
		}
		StringBuilder elencocampinulli = new StringBuilder(
				"I seguenti parametri di input sono nulli: ");
		List<Object> listaParametriNulli = new ArrayList<Object>();
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] == null) {
				listaParametriNulli.add(obj[i]);
				if (!nameParameterNull)
					elencocampinulli.append("[" + nameParameter[i] + "] ");			}
		}

		if (listaParametriNulli.size() > 0) {
			if (listaParametriNulli.size() == obj.length)
				throw new FormalParameterException(elencocampinulli.toString());
		}
	}

	/**
	 * Controlla che l'array non sia nullo o vuoto
	 * 
	 * @param nomeArray
	 * @param array
	 * @throws FormalParameterException
	 */
	public static void verifyArrayNotEmpty(String nomeArray, Object[] array)
			throws FormalParameterException {

		if (array == null || array.length == 0)
			throw new FormalParameterException(
					"Il seguente parametro non � valorizzato : " + nomeArray);

	}

	/**
	 * Controlla che  almeno una  property del bean sia valorizzata (al momento
	 * si ferma al primo livello)
	 * 
	 * @param nameParameter
	 * @param beans
	 * @throws FormalParameterException
	 */
	public static void verifyAtLeastOneNotNullValue(
			Object bean) throws FormalParameterException {


		StringBuilder elencocampinulli = new StringBuilder(
				"I seguenti attributi sono nulli: ");
		StringBuilder elencoCampiNotNull = new StringBuilder(
				"I seguenti attributi sono valorizzati: ");
		
		List<Object> listaParametriNulli = new ArrayList<Object>();
		boolean ok=false;
			if (bean == null) {
				listaParametriNulli.add("Oggetto NULLO");
			} else {
				Set<String> props;
				try {
					props = BeanUtil.getProperties(bean);
					for (String name : props) {
						if(name.equalsIgnoreCase("class")) continue;
						
						Object value;
						try {
							value = BeanUtil.getPropertyValueByName(bean,
									name);
						} catch (Exception e) {
							value = null;
						}
						if (value == null) {
							listaParametriNulli.add(name);
							elencocampinulli.append("[" +name + "] ");
						}else{
							elencoCampiNotNull.append("<"+name+"> : "+value);
							ok=true;
						}
					}
				} catch (IntrospectionException e1) {

				}
			}
		logger.debug(elencoCampiNotNull.toString());
		if (listaParametriNulli.size() > 0) {
			logger.debug(listaParametriNulli.toString());
		}
		if(!ok)
			throw new FormalParameterException(elencocampinulli.toString());
	}

	/**
	 * Controlla che  tutte le property dei bean siano valorizzate (al momento
	 * si ferma al primo livello)
	 * 
	 * @param nameParameter
	 * @param beans
	 * @throws FormalParameterException
	 */
	public static void verifyBeansNotEmpty(String[] nameParameter,
			Object... beans) throws FormalParameterException {
		boolean nameParameterNull = false;

		if (nameParameter == null || nameParameter.length != beans.length) {
			nameParameterNull = true;
		}

		StringBuilder elencocampinulli = new StringBuilder(
				"I seguenti parametri di input sono nulli: ");
		List<Object> listaParametriNulli = new ArrayList<Object>();
		for (int i = 0; i < beans.length; i++) {
			if (beans[i] == null) {
				listaParametriNulli.add(beans[i]);
				if (!nameParameterNull)
					elencocampinulli.append("[" + nameParameter[i] + "] ");
			} else {
				Set<String> props;
				try {
					props = BeanUtil.getProperties(beans[i]);
					for (String name : props) {
						Object value;
						try {
							value = BeanUtil.getPropertyValueByName(beans[i],
									name);
						} catch (Exception e) {
							value = null;
						}
						if (value == null) {
							listaParametriNulli.add(beans[i]);
							if (!nameParameterNull)
								elencocampinulli.append("[" + nameParameter[i]
										+ "." + name + "] ");
						}
					}
				} catch (IntrospectionException e1) {
					// non riesco a fare l'introspezione del bean... � come se
					// fosse vuoto
					listaParametriNulli.add(beans[i]);
					if (!nameParameterNull)
						elencocampinulli.append("[" + nameParameter[i] + "] ");
				}
			}
		}

		if (listaParametriNulli.size() > 0) {
			throw new FormalParameterException(elencocampinulli.toString());
		}
	}
	
}
