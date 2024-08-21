/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.util;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;

import java.util.List;

public class OrCondition<T extends GenericVO> extends AbstractMultiparameterCondition<T> {

	public OrCondition(Condition<T> c1, Condition<T> c2, Condition<T> ... conditions) {
		super(c1, c2, conditions);
	}
	
	@Override
	public String getClauses() {
		List<StringBuilder> stringConditionList = createStringConditionList();
		return GenericDAO.getOrClauses(stringConditionList);
	}

}
