/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbwebrce.pbandiutil.common.BeanUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterCondition<T extends GenericVO> extends
		AbstractFilterCondition<T> {

	public static final int MAX_IN_ELEMENTS = 20;

	protected FilterCondition() {
		
	}
	
	public FilterCondition(T valueObject) {
		try {
			FilterClosure c = new FilterClosure();
			forEachVOProperty(valueObject, c);

			initialize(valueObject, c);
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}

	public FilterCondition(Iterable<T> valueObjects) {
		try {
			Iterator<T> iterator = valueObjects.iterator();
			if (iterator.hasNext()) {
				T valueObject = iterator.next();
				InFilterClosure c = new InFilterClosure(valueObjects);
				forEachVOProperty(valueObject, c);

				initialize(valueObject, c);
			} else {
				// FIXME porcheria
				sortColumns = null;
				ascendentOrder = true;
				containedClass = null;
				clausesString = "'1' = '2'";
				tableNameString = "dual";
			}
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}

	class FilterClosure extends AbstractFilterClosure {

		public void execute(T valueObject, String aPropertyName)
				throws IntrospectionException, IllegalAccessException,
				InvocationTargetException {
			addCondition(valueObject, andConditionList, aPropertyName);
		}
	}

	class InFilterClosure extends AbstractFilterClosure {
		private Iterable<T> valueObjects;

		public InFilterClosure(Iterable<T> valueObjects) {
			this.valueObjects = valueObjects;
		}

		public void execute(T valueObject, String aPropertyName)
				throws IntrospectionException, IllegalAccessException,
				InvocationTargetException {
			addInCondition(valueObjects, andConditionList, aPropertyName);
		}
	}

	protected void addCondition(T valueObject,
			List<StringBuilder> andConditionList, String aPropertyName)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		// TODO conversione?
		Object aProperyValue;
		aProperyValue = (Object) BeanUtil.getPropertyValueByName(valueObject,
				aPropertyName);
		if (!GenericDAO.isNull(aProperyValue)) {
			// FIXME per le DATE l'equality condition non funziona (manca la trunc perch� abbia successo)!!!
			andConditionList.add(new StringBuilder(GenericDAO
					.equalityCondition(aPropertyName)));
			// TODO map types
			params.addValue(aPropertyName, aProperyValue);
		}
	}

	protected void addInCondition(Iterable<T> valueObjects,
			List<StringBuilder> andConditionList, String aPropertyName)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {

		List<List<StringBuilder>> orList = new ArrayList<List<StringBuilder>>();
		List<StringBuilder> currentInList = new ArrayList<StringBuilder>();
		orList.add(currentInList);

		int counter = 0;

		for (T valueObject : valueObjects) {
			Object aProperyValue;
			aProperyValue = (Object) BeanUtil.getPropertyValueByName(
					valueObject, aPropertyName);
			if (!GenericDAO.isNull(aProperyValue)) {
				currentInList.add(new StringBuilder(":" + aPropertyName
						+ counter));

				// TODO map types
				params.addValue(aPropertyName + counter, aProperyValue);
				counter++;
			}
			if (counter % MAX_IN_ELEMENTS == 0) {
				currentInList = new ArrayList<StringBuilder>();
				orList.add(currentInList);
			}
		}

		List<StringBuilder> listRendered = new ArrayList<StringBuilder>();
		for (List<StringBuilder> list : orList) {
			if (!list.isEmpty()) {
				StringBuilder sb = new StringBuilder(BeanMapper
						.getDBFieldNameByPropertyName(aPropertyName))
						.append(" in (");
				sb.append(GenericDAO.getCommaSeparatedString(list)).append(")");
				listRendered.add(sb);
			}
		}
		if (listRendered.size() == 1) {
			andConditionList.add(listRendered.get(0));
		} else if (listRendered.size() > 1){
			for (StringBuilder stringBuilder : listRendered) {
				stringBuilder = new StringBuilder(" (").append(stringBuilder)
						.append(") ");
			}
			andConditionList.add(new StringBuilder(" (").append(
					reduceList(listRendered, " or ")).append(") "));
		}
	}

	private static String reduceList(List<StringBuilder> aList, String separator) {
		if (aList.size() == 0) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (StringBuilder condition : aList) {
			stringBuilder.append(condition);
			stringBuilder.append(separator);
		}
		return stringBuilder.substring(0, stringBuilder.length()
				- separator.length());
	}
}
