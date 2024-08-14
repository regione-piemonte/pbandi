/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.util.tablewriter;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.pbandi.pbwebcert.util.BeanUtil;

public class CsvDataWriter extends DataWriter {
	protected BufferedWriter bfw;
	@Autowired
	protected BeanUtil beanUtil;
	public CsvDataWriter(String tablename) throws IOException {
		bfw = 
			new BufferedWriter(new FileWriter(tablename +".csv"));
	}
	
	public CsvDataWriter() {
	}
	
	public void setOutputStream(OutputStream os) {
		super.setOutputStream(os);
		this.bfw = new BufferedWriter(new OutputStreamWriter(os));
	}

	public <T extends Object> void writeField(T data) throws IOException {
		String string = "";
		try {
			string = BeanUtil.convert(String.class, data);
		} catch (Exception e) {
		}
		if (string == null || string.equals("null")) {
			string = "";
		}
			
		bfw.write(string +";");
	}

	public void nextRecord() throws IOException {
		bfw.write("\n");
	}

	public void close() throws Exception {
		super.close();
		bfw.close();
	}
}
