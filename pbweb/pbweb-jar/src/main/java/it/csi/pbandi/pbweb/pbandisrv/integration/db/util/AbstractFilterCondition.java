/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.BeanMapper;
import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public abstract class AbstractFilterCondition<T extends GenericVO> extends
		Condition<T> {
	protected MapSqlParameterSource params = new MapSqlParameterSource();
	protected String clausesString;
	protected String tableNameString;
	protected Class<T> containedClass;
	protected boolean ascendentOrder;
	protected String sortColumns;

	abstract class AbstractFilterClosure implements Closure<T> {
		protected List<StringBuilder> andConditionList = new ArrayList<StringBuilder>();

		public List<StringBuilder> getAndConditionList() {
			return andConditionList;
		}

	}

	@Override
	public Class<T> getContainedClass() {
		return containedClass;
	}

	@Override
	public String getTableName() {
		return tableNameString;
	}

	@Override
	public MapSqlParameterSource getParameters() {
		return params;
	}

	@Override
	public String getClauses() {
		return clausesString;
	}

	@Override
	public String getSortColumns() {
		return sortColumns;
	}

	@Override
	public boolean isAscendentOrder() {
		return ascendentOrder;
	}

	@SuppressWarnings("unchecked")
	protected void initialize(T valueObject, AbstractFilterClosure c)
			throws IntrospectionException {
		ascendentOrder = valueObject.isAscendentOrder();
		List<String> orderPropertyNamesList = valueObject
				.getOrderPropertyNamesList();
		List<StringBuilder> orderColumnNamesList = new ArrayList<StringBuilder>();
		if (orderPropertyNamesList != null) {
			orderPropertyNamesList.retainAll(BeanUtil
					.getProperties(valueObject));
			for (String propName : orderPropertyNamesList) {
				orderColumnNamesList.add(new StringBuilder(BeanMapper
						.getDBFieldNameByPropertyName(propName)));
			}
		}
		sortColumns = GenericDAO.getCommaSeparatedString(orderColumnNamesList);

		containedClass = (Class<T>) valueObject.getClass();
		clausesString = GenericDAO.getAndClauses(c.getAndConditionList());
		tableNameString = valueObject.getTableNameForValueObject();
	}
}