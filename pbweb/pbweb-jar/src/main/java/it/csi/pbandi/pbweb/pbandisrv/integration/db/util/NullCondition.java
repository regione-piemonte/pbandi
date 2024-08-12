package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

import java.util.ArrayList;
import java.util.List;

public class NullCondition<T extends GenericVO> extends Condition<T> {

	private Class<T> containedClass;
	private String clausesString;

	public NullCondition(Class<T> voClass, String string, String... optionals) {
		List<StringBuilder> clauseList = new ArrayList<StringBuilder>();
		clauseList.add(new StringBuilder(GenericDAO.isNullCondition(string)));
		containedClass = voClass;
		for (String s : optionals) {
			clauseList.add(new StringBuilder(GenericDAO.isNullCondition(s)));
		}
		clausesString = GenericDAO.getAndClauses(clauseList);
	}

	@Override
	public String getClauses() {
		return clausesString;
	}

	@Override
	public Class<T> getContainedClass() {
		return containedClass;
	}

	@Override
	public String getTableName() {
		try {
			return containedClass.newInstance().getTableNameForValueObject();
		} catch (InstantiationException e) {
			GenericDAO.throwIntrospectionException(e);
		} catch (IllegalAccessException e) {
			GenericDAO.throwIntrospectionException(e);
		}
		return null;
	}

	@Override
	public String getSortColumns() {
		return null;
	}
}
