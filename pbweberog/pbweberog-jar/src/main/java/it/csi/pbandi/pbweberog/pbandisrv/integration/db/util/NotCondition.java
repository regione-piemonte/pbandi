/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandisrv.integration.db.util;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import it.csi.pbandi.pbweberog.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweberog.pbandisrv.integration.db.vo.GenericVO;

public class NotCondition<T extends GenericVO> extends Condition<T> {
	private Condition<T> condition;

	public NotCondition(Condition<T> c) {
		condition = c;
	}

	@Override
	public MapSqlParameterSource getParameters() {
		return condition.getParameters();
	}
	
	@Override
	public String getClauses() {
		String clauses = condition.getClauses();

		return GenericDAO.isEmpty(clauses) ? null : ("not (" + clauses + ")");
	}

	@Override
	public Class<T> getContainedClass() {
		return condition.getContainedClass();
	}

	@Override
	public String getTableName() {
		return condition.getTableName();
	}

	@Override
	public String getSortColumns() {
		return condition.getSortColumns();
	}

}
