/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbwebrce.pbandiutil.common.BeanUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AbstractComparisonCondition<T extends GenericVO> extends
		AbstractFilterCondition<T> {

	public AbstractComparisonCondition(T valueObject, boolean alsoConsiderEqual) {
		this(valueObject, alsoConsiderEqual, true);
	}

	public AbstractComparisonCondition(T valueObject,
			boolean alsoConsiderEqual, boolean useTrunc) {
		try {
			AbstractGreaterThanClosure c = new AbstractGreaterThanClosure(
					valueObject, alsoConsiderEqual, useTrunc);
			forEachVOProperty(valueObject, c);

			initialize(valueObject, c);
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}

	class AbstractGreaterThanClosure extends AbstractFilterClosure {
		private boolean considerEqual;
		private boolean useTrunc;

		public AbstractGreaterThanClosure(T valueObject, boolean considerEqual,
				boolean useTrunc) {
			this.considerEqual = considerEqual;
			this.useTrunc = useTrunc;
		}

		public void execute(T valueObject, String aPropertyName)
				throws IntrospectionException, IllegalAccessException,
				InvocationTargetException {
			addGreaterThanCondition(valueObject, considerEqual, useTrunc,
					andConditionList, aPropertyName);
		}
	}

	private void addGreaterThanCondition(T valueObject, boolean considerEqual,
			boolean useTrunc, List<StringBuilder> andConditionList,
			String aPropertyName) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Object propertyValue = (Object) BeanUtil.getPropertyValueByName(
				valueObject, aPropertyName);
		if (!GenericDAO.isNull(propertyValue)) {

			String condition;
			if (considerEqual) {
				condition = GenericDAO.greaterThanOrEqualCondition(
						aPropertyName, useTrunc);
			} else {
				condition = GenericDAO.greaterThanCondition(aPropertyName);
			}

			andConditionList.add(new StringBuilder(condition));
			params.addValue(aPropertyName, propertyValue);
		}
	}
}
