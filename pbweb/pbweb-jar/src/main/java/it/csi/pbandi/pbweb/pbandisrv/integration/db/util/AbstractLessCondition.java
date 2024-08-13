/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AbstractLessCondition<T extends GenericVO> extends
		AbstractFilterCondition<T> {

	public AbstractLessCondition(T valueObject, boolean alsoConsiderEqual) {
		this(valueObject, alsoConsiderEqual, true);
	}

	public AbstractLessCondition(T valueObject, boolean alsoConsiderEqual,
			boolean useTrunc) {
		try {
			AbstractLessThanClosure c = new AbstractLessThanClosure(
					valueObject, alsoConsiderEqual, useTrunc);
			forEachVOProperty(valueObject, c);

			initialize(valueObject, c);
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}

	class AbstractLessThanClosure extends AbstractFilterClosure {
		private boolean considerEqual;
		private boolean useTrunc;

		public AbstractLessThanClosure(T valueObject, boolean considerEqual, boolean useTrunc) {
			this.considerEqual = considerEqual;
			this.useTrunc = useTrunc;
		}

		public void execute(T valueObject, String aPropertyName)
				throws IntrospectionException, IllegalAccessException,
				InvocationTargetException {
			addLessThanCondition(valueObject, considerEqual, useTrunc, andConditionList,
					aPropertyName);
		}
	}

	private void addLessThanCondition(T valueObject, boolean considerEqual,
			boolean useTrunc, List<StringBuilder> andConditionList, String aPropertyName)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Object propertyValue = (Object) BeanUtil.getPropertyValueByName(
				valueObject, aPropertyName);
		if (!GenericDAO.isNull(propertyValue)) {

			String condition;
			if (considerEqual) {
				condition = GenericDAO.lessThanOrEqualCondition(aPropertyName, useTrunc);
			} else {
				condition = GenericDAO.lessThanCondition(aPropertyName);
			}

			andConditionList.add(new StringBuilder(condition));
			params.addValue(aPropertyName, propertyValue);
		}
	}
}
