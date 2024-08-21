/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbservizit.pbandisrv.integration.util.BeanMapper;

import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;

public abstract class GenericVO implements Cloneable {
	private boolean ascendentOrder;
	private List<String> orderPropertyNamesList;
	private static LoggerUtil loggerUtil = new LoggerUtil();

	abstract public boolean isPKValid();

	@SuppressWarnings("unchecked")
	abstract public List getPK();

	private static BidiMap mapTableName = new TreeBidiMap();

	/**
	 * A partire dal nome della classe restituisce il nome della tabella oppure
	 * la query che il VO rappresenta se esiste un file sql con nome
	 * corrispondente usa una cache per velocizzare le operazioni
	 * 
	 * @return tableName
	 */
	public String getTableNameForValueObject() {
		return getTableNameForValueObject(this.getClass());
	}

	public static String getTableNameForValueObject(
			Class<? extends GenericVO> voClass) {
		return getQuery(generateQueryIdentifierKey(voClass));
	}

	private static String generateQueryIdentifierKey(
			Class<? extends GenericVO> voClass) {
		return ObjectUtil.getSimpleName(voClass);
	}

	public boolean hasQueryResource() {
		InputStream inputStream = getQueryResourceAsStream(generateQueryIdentifierKey(this
				.getClass()));
		boolean result = false;
		if (inputStream != null) {
			result = true;
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
		return result;
	}

	private static String getQuery(String queryIdentifierKey) {
		String tableName;
		tableName = (String) mapTableName.get(queryIdentifierKey);

		if (tableName == null) {
			InputStream resourceAsStream = getQueryResourceAsStream(queryIdentifierKey);

			if (resourceAsStream != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(resourceAsStream));
				StringBuilder stringBuilder = new StringBuilder();
				String s;
				try {
					while ((s = reader.readLine()) != null) {
						stringBuilder.append(s + "\n");
					}
					tableName = "(\n" + stringBuilder.toString() + ")";
				} catch (IOException e) {
					loggerUtil.error(
							"ATTENZIONE!!!! Errore nel reperire il file "
									+ queryIdentifierKey + ".sql "
									+ e.getMessage(), e);
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						loggerUtil.error(e.getMessage(), e);
					}
				}
			}

			if (tableName == null) {
				try {
					tableName = getTableName(queryIdentifierKey.substring(0,
							queryIdentifierKey.length() - 2));
				} catch (RuntimeException e) {
					String message = "Errore nel determinare l'identificativo della tabella: "
							+ queryIdentifierKey;
					loggerUtil.error(message, e);
					throw new RuntimeException(message, e);
				}
			}
			mapTableName.put(queryIdentifierKey, tableName);
		}
		return tableName;
	}

	private static InputStream getQueryResourceAsStream(
			String queryIdentifierKey) {
		//InputStream resourceAsStream = GenericVO.class.getClassLoader().getResourceAsStream(queryIdentifierKey + ".sql");
		InputStream resourceAsStream = GenericVO.class.getClassLoader().getResourceAsStream(Constants.PATH_FILE_SQL+queryIdentifierKey + ".sql");
		
		return resourceAsStream;
	}

	/**
	 * A partire dal nome della classe restituisce il nome della tabella
	 * 
	 * @param voClassName
	 * @return
	 */
	private static String getTableName(String voClassName) {
		String tableName;
		StringBuffer sb = new StringBuffer();
		sb.append(Character.toUpperCase(voClassName.charAt(0)));
		for (int i = 1; i < voClassName.length(); i++) {
			char currentChar = voClassName.charAt(i);
			if (Character.isUpperCase(currentChar)) {
				sb.append('_');
			}
			sb.append(Character.toUpperCase(currentChar));
		}
		tableName = sb.toString();
		return tableName;
	}

	@SuppressWarnings("unchecked")
	public static <T extends GenericVO> Class<T> getVOClassByTableName(
			String chiave) throws ClassNotFoundException {
		String className = (String) mapTableName.inverseBidiMap().get(chiave);

		boolean doCache = false;
		if (className == null) {
			doCache = true;
			className = BeanMapper.getPropertyNameByDBFieldName(chiave) + "VO";
			className = className.substring(0, 1).toUpperCase()
					+ className.substring(1);
		}

		Class<T> result = (Class<T>) GenericVO.class.getClassLoader()
				.loadClass(
						GenericVO.class.getPackage().getName() + "."
								+ className);

		if (doCache) {
			mapTableName.put(className, chiave);
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && this.toString().equals(obj.toString());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	public Object clone() {
		BeanUtil beanUtil = new BeanUtil();
		return beanUtil.transform(this, this.getClass());
	}

	public final void setDescendentOrder(String... propertyNames) {
		setAscendentOrder(false);
		addOrderProperties(propertyNames);
	}

	public final void setAscendentOrder(String... propertyNames) {
		ascendentOrder = true;
		addOrderProperties(propertyNames);
	}

	private void addOrderProperties(String... propertyNames) {
		setOrderPropertyNamesList(new ArrayList<String>());
		if (propertyNames != null) {
			for (String propertyName : propertyNames) {
				getOrderPropertyNamesList().add(propertyName);
			}
		}
	}

	public void setAscendentOrder(boolean ascendentOrder) {
		this.ascendentOrder = ascendentOrder;
	}

	public boolean isAscendentOrder() {
		return ascendentOrder;
	}

	public void setOrderPropertyNamesList(List<String> orderPropertyNamesList) {
		this.orderPropertyNamesList = orderPropertyNamesList;
	}

	public List<String> getOrderPropertyNamesList() {
		return orderPropertyNamesList;
	}

}
