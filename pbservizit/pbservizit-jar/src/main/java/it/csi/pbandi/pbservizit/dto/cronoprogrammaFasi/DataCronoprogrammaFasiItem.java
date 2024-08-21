/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi;

import java.util.Date;
import java.util.List;

public class DataCronoprogrammaFasiItem implements java.io.Serializable {	
	
	List<List<CronoprogrammaFasiItem>> data;
	
	public DataCronoprogrammaFasiItem(List<List<CronoprogrammaFasiItem>> data) {
		this.data = data;
	}

	public List<List<CronoprogrammaFasiItem>> getData() {
		return data;
	}

	public void setData(List<List<CronoprogrammaFasiItem>> data) {
		this.data = data;
	}
	
	
	
	
	

	
	
	
}
