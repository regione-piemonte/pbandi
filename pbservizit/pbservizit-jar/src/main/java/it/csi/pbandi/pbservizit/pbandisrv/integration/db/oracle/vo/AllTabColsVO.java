/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.oracle.vo;


import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;

import java.math.BigDecimal;
import java.util.List;

public class AllTabColsVO extends GenericVO {

	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

	private String tableName;
	private String owner;
	private String dataType;
	private BigDecimal dataLength;
	private BigDecimal dataPrecision;
	private BigDecimal dataScale;
	private BigDecimal columnId;

	private String columnName;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BigDecimal getDataLength() {
		return dataLength;
	}

	public void setDataLength(BigDecimal dataLength) {
		this.dataLength = dataLength;
	}

	public BigDecimal getColumnId() {
		return columnId;
	}

	public void setColumnId(BigDecimal columnId) {
		this.columnId = columnId;
	}

	public void setDataPrecision(BigDecimal dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public BigDecimal getDataPrecision() {
		return dataPrecision;
	}

	public void setDataScale(BigDecimal dataScale) {
		this.dataScale = dataScale;
	}

	public BigDecimal getDataScale() {
		return dataScale;
	}
}
