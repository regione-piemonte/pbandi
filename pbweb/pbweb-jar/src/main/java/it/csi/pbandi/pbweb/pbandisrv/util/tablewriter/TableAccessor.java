package it.csi.pbandi.pbweb.pbandisrv.util.tablewriter;

import java.util.Iterator;

public interface TableAccessor {
	public int getColumnCount();

	public String getColumnLabel(int i);

	public Iterator<RowAccessor> iterator();
	
	public interface RowAccessor {
		Object getColumnValue(int i) throws Exception;
	}
}
