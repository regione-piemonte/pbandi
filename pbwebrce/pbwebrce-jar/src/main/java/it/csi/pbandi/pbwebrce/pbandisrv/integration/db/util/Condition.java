/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.GenericVO;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public abstract class Condition<T extends GenericVO> {

	public static final <T extends GenericVO> Condition<T> not(Condition<T> c) {
		return new NotCondition<T>(c);
	}

	public static final <T extends GenericVO> Condition<T> filterBy(
			T valueObject) {
		return new FilterCondition<T>(valueObject);
	}

	public static final <T extends GenericVO> Condition<T> filterByKeyOf(
			T valueObject) {
		return new FilterConditionByKey<T>(valueObject);
	}

	public static final <T extends GenericVO> Condition<T> filterBy(
			Iterable<T> valueObject) {
		return new FilterCondition<T>(valueObject);
	}

	public static final <T extends GenericVO> Condition<T> filterByKeyOf(
			Iterable<T> valueObject) {
		return new FilterConditionByKey<T>(valueObject);
	}

	public static final <T extends GenericVO> Condition<T> isFieldNull(
			Class<T> voClass, String string, String... optionals) {
		return new NullCondition<T>(voClass, string, optionals);
	}

	public static final <T extends GenericVO> Condition<T> validOnly(Class<T> c) {
		return new ValidOnlyCondition<T>(c);
	}

	public final Condition<T> negate() {
		return new NotCondition<T>(this);
	}

	public final Condition<T> and(Condition<T> c, Condition<T>... conditions) {
		return new AndCondition<T>(this, c, conditions);
	}

	public final Condition<T> or(Condition<T> c, Condition<T>... conditions) {
		return new OrCondition<T>(this, c, conditions);
	}

	public abstract Class<T> getContainedClass();

	public abstract String getTableName();

	public MapSqlParameterSource getParameters() {
		return new MapSqlParameterSource();
	}

	public String getClauses() {
		return null;
	}

	public static final <T extends GenericVO> void forEachVOProperty(
			T valueObject, Closure<T> c) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Set<String> propertyNames = GenericDAO
				.extractMappableProperties(valueObject);
		for (String aPropertyName : propertyNames) {
			c.execute(valueObject, aPropertyName);
		}
	}

	public static final <T extends GenericVO> void forEachVOKeyProperty(
			T valueObject, Closure<T> c) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		for (Object aPropertyName : valueObject.getPK()) {
			c.execute(valueObject, (String) aPropertyName);
		}
	}

	public interface Closure<T extends GenericVO> {
		public abstract void execute(T valueObject, String aPropertyName)
				throws IntrospectionException, IllegalAccessException,
				InvocationTargetException;
	}

	public abstract String getSortColumns();

	public boolean isAscendentOrder() {
		return false;
	}
}
