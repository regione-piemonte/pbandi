/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.dto.cronoprogramma;

import java.io.Serializable;
import java.util.List;


public class ResponseGetFasiMonit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CronoprogrammaItem> item;
	private DatiCronoprogramma datiCrono;
	public List<CronoprogrammaItem> getItem() {
		return item;
	}
	public void setItem(List<CronoprogrammaItem> item) {
		this.item = item;
	}
	public DatiCronoprogramma getDatiCrono() {
		return datiCrono;
	}
	public void setDatiCrono(DatiCronoprogramma datiCrono) {
		this.datiCrono = datiCrono;
	}
	
	
	
}
