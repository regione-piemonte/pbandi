/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter;

import it.csi.pbandi.pbservizit.pbandisrv.dto.certificazione.ProgettoCertificazioneIntermediaFinaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ReportPropostaCertificazionePorFesrVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.tablewriter.TableAccessor.RowAccessor;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableWriter {

	private DataWriter writer;
	private boolean writeHeader = true;
	private int rowCount;
	private TableAccessor tableAccessor;
	private List<Long> linesToJump;
	
	public TableWriter(DataWriter dw, TableAccessor t) {
		writer = dw;
		tableAccessor = t;
		linesToJump = new ArrayList<Long>();
	}

	public TableWriter(DataWriter dw, TableAccessor t, List<Long> linesToJump) {
		writer = dw;
		tableAccessor = t;
		this.linesToJump = linesToJump;
	}
	
	public TableWriter(DataWriter dw, TableAccessor t, List<Long> linesToJump, boolean writeHeader) {
		writer = dw;
		tableAccessor = t;
		this.linesToJump = linesToJump;
		this.writeHeader = writeHeader;
	}

	public void write() throws Exception {
		rowCount = 0;

		// Jump the first n-lines
		for(int i=1; linesToJump.size() >= i; i++) {
			if(linesToJump.contains(new Long(i))) {
				writer.nextRecord();
				rowCount++;
			} else {
				break;
			}
		}
		
		int columnCount = tableAccessor.getColumnCount();
		if (writeHeader) {
			for (int i = 0; i < columnCount; i++) {
				writer.writeField(tableAccessor.getColumnLabel(i));
			}
			writer.nextRecord();
		}

		Iterator<RowAccessor> iterator = tableAccessor.iterator();
		while (iterator.hasNext()) {
			if (!linesToJump.contains(new Long(rowCount + 2))) {
				RowAccessor rowAccessor = iterator.next();
				for (int i = 0; i < columnCount; i++) {
					writer.writeField(rowAccessor.getColumnValue(i));
				}
			}
			writer.nextRecord();
			rowCount++;
		}

		writer.close();
	}

	public void writeTo(OutputStream os) throws Exception {
		writer.setOutputStream(os);
		this.write();
	}

	public static <T extends Object> byte[] writeTableToByteArray(String key,
			DataWriter dw, Iterable<T> i) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		new TableWriter(dw, new LabelTableAccessorDecorator(key,
				new BeanTableAccessor(i))).writeTo(byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	public static String writeTableToString(String key, Iterable<?> i)
			throws Exception {
		StringDataWriter stringDataWriter = new StringDataWriter();
		new TableWriter(stringDataWriter, new LabelTableAccessorDecorator(key,
				new BeanTableAccessor(i))).write();
		String string = stringDataWriter.toString();
		return string;
	}
	
	public static String writeHtmlTableToString(String key, Iterable<?> i)
	throws Exception {
		HtmlTableDataWriter htmlTableDataWriter = new HtmlTableDataWriter();
		new TableWriter(htmlTableDataWriter, new LabelTableAccessorDecorator(key,
				new BeanTableAccessor(i))).write();
		String string = htmlTableDataWriter.toString();
		return string;
	}

	public static byte[] writeTableToByteArray(String key,
			ExcelDataWriter dw,
			List<ReportPropostaCertificazionePorFesrVO> i,
			ArrayList<Long> linesToJump)  throws Exception{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		new TableWriter(dw, new LabelTableAccessorDecorator(key, new BeanTableAccessor(i)), linesToJump).writeTo(byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}
	
	public static byte[] writeTableToByteArray(String key,
			ExcelDataWriter dw,
			List<? extends Object> i,
			ArrayList<Long> linesToJump, Boolean writeHeader)  throws Exception{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		new TableWriter(dw, new LabelTableAccessorDecorator(key, new BeanTableAccessor(i)), linesToJump, writeHeader).writeTo(byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

	public static byte[] writeTableToByteArrayCC(String key,
			ExcelDataWriter dw,
			List<ProgettoCertificazioneIntermediaFinaleDTO> i,
			ArrayList<Long> linesToJump)  throws Exception{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		new TableWriter(dw, new LabelTableAccessorDecorator(key, new BeanTableAccessor(i)), linesToJump).writeTo(byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}

}
