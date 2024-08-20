/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbwebrce.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbwebrce.pbandiutil.common.StringUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

public class LikeEndsWithCondition<T extends GenericVO> extends
		AbstractFilterCondition<T> {

	public LikeEndsWithCondition(T valueObject) {
		try {
			LikeStartsWithClosure c = new LikeStartsWithClosure();
			forEachVOProperty(valueObject, c);

			initialize(valueObject, c);
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}

	class LikeStartsWithClosure extends AbstractFilterClosure {
		public void execute(T valueObject, String aPropertyName)
				throws IntrospectionException, IllegalAccessException,
				InvocationTargetException {
			// TODO conversione?
			Object aPropertyValue;
			aPropertyValue = (Object) BeanUtil.getPropertyValueByName(
					valueObject, aPropertyName);
			if (!GenericDAO.isNull(aPropertyValue)
					&& !(aPropertyValue.getClass().equals(
							java.lang.String.class) && (StringUtil
							.isEmpty((String) aPropertyValue)))) {
				andConditionList.add(new StringBuilder(GenericDAO
						.likeEndsWithCondition(aPropertyName)));
				// TODO map types
				params.addValue(aPropertyName, aPropertyValue);
			}
		}
	}
}
