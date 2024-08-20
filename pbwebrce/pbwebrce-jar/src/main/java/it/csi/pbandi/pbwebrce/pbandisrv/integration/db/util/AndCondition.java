/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.util;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo.GenericVO;

import java.util.List;

public class AndCondition<T extends GenericVO> extends AbstractMultiparameterCondition<T> {
	public AndCondition(Condition<T> c1, Condition<T> c2, Condition<T> ... conditions) {
		super(c1, c2, conditions);
	}

	@Override
	public String getClauses() {
		List<StringBuilder> stringConditionList = createStringConditionList();
		return GenericDAO.getAndClauses(stringConditionList);
	}
}
