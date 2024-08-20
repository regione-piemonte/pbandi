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

public class FilterIgnoreCaseCondition<T extends GenericVO> extends
		AbstractFilterCondition<T> {

	public FilterIgnoreCaseCondition(T valueObject) {
		try {
			EqualsIgnoreCaseClosure c = new EqualsIgnoreCaseClosure();
			forEachVOProperty(valueObject, c);

			initialize(valueObject, c);
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}

	class EqualsIgnoreCaseClosure extends AbstractFilterClosure {
		public void execute(T valueObject, String aPropertyName)
				throws IntrospectionException, IllegalAccessException,
				InvocationTargetException {
			// TODO conversione?
			Object aProperyValue;
			aProperyValue = (Object) BeanUtil.getPropertyValueByName(
					valueObject, aPropertyName);
			if (!GenericDAO.isNull(aProperyValue)) {
				andConditionList.add(new StringBuilder(GenericDAO
						.equalsIgnoreCaseCondition(aPropertyName)));
				// TODO map types
				params.addValue(aPropertyName, aProperyValue);
			}
		}
	}
}
