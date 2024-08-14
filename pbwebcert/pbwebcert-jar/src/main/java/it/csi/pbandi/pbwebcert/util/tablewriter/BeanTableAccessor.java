/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util.tablewriter;


import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbwebcert.util.BeanUtil;

public class BeanTableAccessor implements TableAccessor {
	@Autowired
	protected BeanUtil beanUtil;
	private int columnCount = 0;
	private List<String> labelList = new ArrayList<String>();
	private Iterable<?> data;

	public BeanTableAccessor(Iterable<?> i) throws IntrospectionException {
		data = i;
		Iterator<?> iterator = data.iterator();
		if (iterator.hasNext()) {
			
			Iterable<String> properties = new BeanUtil().enumerateProperties(iterator.next());
			for (String string : properties) {
				labelList.add(string);
			}
			columnCount = labelList.size();
		}
	}

	public int getColumnCount() {
		return columnCount;
	}

	public String getColumnLabel(int i) {
		return labelList.get(i);
	}

	public Iterator<RowAccessor> iterator() {
		return new Iterator<RowAccessor>() {
			private Iterator<?> innerIterator = data.iterator();

			public boolean hasNext() {
				return innerIterator.hasNext();
			}

			public RowAccessor next() {
				return innerIterator.hasNext() ? new RowAccessor() {
					Object element = innerIterator.next();

					public Object getColumnValue(int i) throws Exception {
						return BeanUtil.getPropertyValueByName(element,
								labelList.get(i));
					}
				} : null;
			}

			public void remove() {
				innerIterator.remove();
			}
		};
	}

}
