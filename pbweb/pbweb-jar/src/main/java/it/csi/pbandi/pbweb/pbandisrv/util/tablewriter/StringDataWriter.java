/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.util.tablewriter;

import it.csi.pbandi.pbweb.pbandiutil.common.BeanUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Paolo Sacconier
 */
public class StringDataWriter extends DataWriter {
	private static final String PADDING = " ";
	private static final String RECORD_SEPARATOR = "\n";
	private static final String FIELD_SEPARATOR = "   ";

	private String stringData = null;
	private List<List<String>> allRecords = new ArrayList<List<String>>();
	private List<String> currentRecord;
	private List<Integer> maxColumnLength = new ArrayList<Integer>();
	private int currentColumnIndex;
	private boolean closed = false;

	public StringDataWriter() {
		switchRecord();
	}

	private void switchRecord() {
		currentColumnIndex = 0;
		currentRecord = new ArrayList<String>();
		allRecords.add(currentRecord);
	}

	public <T extends Object> void writeField(T data) throws IOException {
		checkIfClosed();

		String string;
		try {
			string = BeanUtil.convert(String.class, data);
		} catch (Exception e) {
			string = "";
		}
		
		if (string == null) {
			string = "";
		}

		while (maxColumnLength.size() <= currentColumnIndex) {
			maxColumnLength.add(0);
		}
		if (maxColumnLength.get(currentColumnIndex) < string.length()) {
			maxColumnLength.set(currentColumnIndex, string.length());
		}

		currentRecord.add(string);
		currentColumnIndex++;
	}

	private void checkIfClosed() throws IOException {
		if (closed) {
			throw new IOException();
		}
	}

	public void nextRecord() throws IOException {
		checkIfClosed();
		switchRecord();
	}

	public void close() throws IOException {
		closed = true;
		toString();
		return;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		if (stringData == null) {
			for (List<String> record : allRecords) {
				int currentColumnIndex = 0;
				for (String field : record) {
					buffer.append(field);
					insertPadding(buffer, maxColumnLength
							.get(currentColumnIndex)
							- field.length());
					buffer.append(FIELD_SEPARATOR);
					currentColumnIndex++;
				}
				buffer.append(RECORD_SEPARATOR);
			}
			stringData = buffer.toString();

			currentRecord = null;
			maxColumnLength = null;
			allRecords = null;
		}
		return stringData;
	}

	private void insertPadding(StringBuilder buffer, int num) {
		for (int i = 0; i < num; i++) {
			buffer.append(PADDING);
		}
	}
}
