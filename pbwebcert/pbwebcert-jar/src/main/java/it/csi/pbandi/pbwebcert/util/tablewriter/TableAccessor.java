/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util.tablewriter;

import java.util.Iterator;

public interface TableAccessor {
	public int getColumnCount();

	public String getColumnLabel(int i);

	public Iterator<RowAccessor> iterator();
	
	public interface RowAccessor {
		Object getColumnValue(int i) throws Exception;
	}
}
