/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter;

import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanTableAccessor implements TableAccessor {

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
