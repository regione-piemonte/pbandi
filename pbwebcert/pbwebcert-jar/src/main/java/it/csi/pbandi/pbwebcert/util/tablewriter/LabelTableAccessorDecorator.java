/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util.tablewriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

public class LabelTableAccessorDecorator implements TableAccessor {
	private static ResourceBundle resourceBundle;

	private BeanTableAccessor decoratedTableAccessor;
	private String key;
	private SortedMap<String, Integer> labelMap;
	private List<String> labelList;

	static {
		resourceBundle = ResourceBundle.getBundle("beanLabels");
	}

	public LabelTableAccessorDecorator(String key,
			BeanTableAccessor beanTableAccessor) {
		decoratedTableAccessor = beanTableAccessor;
		this.key = key;

		labelList = new ArrayList<String>();
		InputStream resourceAsStream = LabelTableAccessorDecorator.class.getClassLoader().getResourceAsStream("beanLabelsOrder.properties");

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(resourceAsStream));
		String s;
		try {
			while ((s = reader.readLine()) != null) {
				String label = s.trim();
				if (label.startsWith(key)) {
					labelList.add(label.substring(key.length()+1));
				}
			}
		} catch (IOException e) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}

		
		Enumeration<String> keys = resourceBundle.getKeys();

		labelMap = new TreeMap<String, Integer>();
		while (keys.hasMoreElements()) {
			String aKey = keys.nextElement();
			if (aKey.startsWith(key)) {
				String aKeyColumnName = aKey.substring(key.length() + 1, aKey.length());
				for (int i = 0; i < decoratedTableAccessor.getColumnCount(); i++) {
					String columnLabel = decoratedTableAccessor
							.getColumnLabel(i);
					if (aKeyColumnName.equals(columnLabel)) {
						labelMap.put(columnLabel, i);
						break;
					}
				}
			}
		}
	}

	public int getColumnCount() {
		return labelList.size();
	}

	public String getColumnLabel(int i) {
		String label = labelList.get(i);
		String string = resourceBundle.getString(key + "." + label);
		return string.equals("") ? label : string;
	}

	public Iterator<RowAccessor> iterator() {
		return new Iterator<RowAccessor>() {
			private Iterator<RowAccessor> innerIterator = decoratedTableAccessor
					.iterator();

			public boolean hasNext() {
				return innerIterator.hasNext();
			}

			public RowAccessor next() {
				return innerIterator.hasNext() ? new RowAccessor() {
					RowAccessor innerRowAccessor = innerIterator.next();

					public Object getColumnValue(int i) throws Exception {
						return innerRowAccessor.getColumnValue(labelMap
								.get(labelList.get(i)));
					}
				} : null;
			}

			public void remove() {
				innerIterator.remove();
			}
		};
	}
}
