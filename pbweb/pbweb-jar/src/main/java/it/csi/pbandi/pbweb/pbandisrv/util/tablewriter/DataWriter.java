package it.csi.pbandi.pbweb.pbandisrv.util.tablewriter;

import java.io.IOException;
import java.io.OutputStream;

public abstract class DataWriter {
	protected OutputStream os = null;
	private boolean closed = false;

	public abstract <T extends Object> void writeField(T string)
			throws IOException;

	public abstract void nextRecord() throws IOException;

	public void close() throws Exception {
		if (!this.closed) {
			if (os != null) {
				os.close();
			}
			this.closed = true;
		} else {
			throw new Exception();
		}
	}

	public void setOutputStream(OutputStream os) {
		this.os = os;
	}
}
