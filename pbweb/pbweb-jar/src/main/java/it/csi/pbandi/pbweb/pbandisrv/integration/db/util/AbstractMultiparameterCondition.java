/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.dao.PbandiDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public abstract class AbstractMultiparameterCondition<T extends GenericVO>
		extends Condition<T> {

	protected List<Condition<T>> conditionsList;
	private MapSqlParameterSource mapSqlParameterSource;
	private List<Map<String, String>> substitutionMapList;

	public AbstractMultiparameterCondition(Condition<T> c1, Condition<T> c2,
			Condition<T>... conditions) {
		conditionsList = new ArrayList<Condition<T>>();
		conditionsList.add(c1);
		conditionsList.add(c2);
		for (Condition<T> condition : conditions) {
			if (condition != null)
				conditionsList.add(condition);
		}
	}

	@Override
	final public Class<T> getContainedClass() {
		return conditionsList.get(0).getContainedClass();
	}

	@Override
	final public String getTableName() {
		return conditionsList.get(0).getTableName();
	}

	@Override
	final public MapSqlParameterSource getParameters() {
		if (mapSqlParameterSource == null) {
			mapSqlParameterSource = new MapSqlParameterSource();

			substitutionMapList = GenericDAO.disambiguateParameters(conditionsList,
					mapSqlParameterSource);
		}
		return mapSqlParameterSource;
	}

	final protected List<StringBuilder> createStringConditionList() {
		if (substitutionMapList == null) {
			getParameters();
		}

		List<StringBuilder> stringConditionList = new ArrayList<StringBuilder>();

		int counter = 0;
		for (Condition<T> c : conditionsList) {
			String clauses = PbandiDAO.substituteMarkers(c.getClauses(),
					substitutionMapList.get(counter));
			if (!GenericDAO.isEmpty(clauses)) {
				StringBuilder newConditions = new StringBuilder(" ( " + clauses
						+ " ) ");
				stringConditionList.add(newConditions);
			}
			counter++;
		}

		return stringConditionList;
	}

	@Override
	final public String getSortColumns() {
		return conditionsList.get(0).getSortColumns();
	}

	@Override
	final public boolean isAscendentOrder() {
		return conditionsList.get(0).isAscendentOrder();
	}

}