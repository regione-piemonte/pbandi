package it.csi.pbandi.pbweb.pbandisrv.integration.db.util;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterConditionByKey<T extends GenericVO> extends
		FilterCondition<T> {

	public FilterConditionByKey(T valueObject) {
		try {
			final List<StringBuilder> andConditionListExt = new ArrayList<StringBuilder>();

			forEachVOKeyProperty(valueObject, new AbstractFilterClosure() {
				public void execute(T valueObject, String aPropertyName)
						throws IntrospectionException, IllegalAccessException,
						InvocationTargetException {
					addCondition(valueObject, andConditionListExt,
							aPropertyName);
				}
			});

			initialize(valueObject, new AbstractFilterClosure() {
				{
					andConditionList = andConditionListExt;
				}

				public void execute(T valueObject, String aPropertyName)
						throws IntrospectionException, IllegalAccessException,
						InvocationTargetException {
				}
			});
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}

	public FilterConditionByKey(final Iterable<T> valueObjects) {
		try {
			Iterator<T> iterator = valueObjects.iterator();
			if (iterator.hasNext()) {
				T valueObject = iterator.next();
				final List<StringBuilder> andConditionListExt = new ArrayList<StringBuilder>();

				forEachVOKeyProperty(valueObject, new AbstractFilterClosure() {
					public void execute(T valueObject, String aPropertyName)
							throws IntrospectionException,
							IllegalAccessException, InvocationTargetException {
						addInCondition(valueObjects, andConditionListExt,
								aPropertyName);
					}
				});

				initialize(valueObject, new AbstractFilterClosure() {
					{
						andConditionList = andConditionListExt;
					}

					public void execute(T valueObject, String aPropertyName)
							throws IntrospectionException,
							IllegalAccessException, InvocationTargetException {
					}
				});
			} else {
				// FIXME porcheria
				sortColumns = null;
				ascendentOrder = true;
				containedClass = null;
				clausesString = "'1' = '2'";
				tableNameString = "dual";
			}
		} catch (Exception e) {
			GenericDAO.throwIntrospectionException(e);
		}
	}
}
