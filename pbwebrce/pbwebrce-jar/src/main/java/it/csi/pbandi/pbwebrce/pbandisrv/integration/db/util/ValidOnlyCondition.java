/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.BeanMapper;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ValidOnlyCondition<T extends GenericVO> extends
		AbstractFilterCondition<T> {
	private static final String DT_INIZIO_VALIDITA = "dtInizioValidita";
	private static final String DT_FINE_VALIDITA = "dtFineValidita";

	protected boolean hasValidityEndDate = false;
	protected boolean hasValidityStartDate = false;

	public ValidOnlyCondition(Class<T> c) {
		AbstractFilterClosure closure = new AbstractFilterClosure() {

			public void execute(T valueObject, String aPropertyName)
					throws IntrospectionException, IllegalAccessException,
					InvocationTargetException {
				if (aPropertyName.equals(DT_FINE_VALIDITA)) {
					hasValidityEndDate = true;
				}
				if (aPropertyName.equals(DT_INIZIO_VALIDITA)) {
					hasValidityStartDate = true;
				}
			}
		};

		T valueObject;
		try {
			valueObject = c.newInstance();
			forEachVOProperty(valueObject, closure);

			containedClass = c;
			tableNameString = valueObject.getTableNameForValueObject();

			if (hasValidityEndDate && hasValidityStartDate) {
				List<StringBuilder> andConditionList = new ArrayList<StringBuilder>();
				andConditionList
						.add(new StringBuilder(
								"trunc(sysdate) >= nvl(trunc("
										+ BeanMapper
												.getDBFieldNameByPropertyName(DT_INIZIO_VALIDITA)
										+ "), trunc(sysdate) -1)"));
				andConditionList.add(new StringBuilder("trunc(sysdate) < nvl(trunc("
						+ BeanMapper
								.getDBFieldNameByPropertyName(DT_FINE_VALIDITA)
						+ "), trunc(sysdate) +1)"));
				clausesString = GenericDAO.getAndClauses(andConditionList);
			}
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}
}
