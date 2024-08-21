/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.dto.cronoprogrammaFasi;

import java.util.LinkedList;
import java.util.List;

public class CronoprogrammaListFasiItemAllegati implements java.io.Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CronoprogrammaFasiItem> fasiItemList;
	private List<AllegatiCronoprogrammaFasi> allegatiList;
	
	
	
	
	public CronoprogrammaListFasiItemAllegati() {		
		this.fasiItemList = new LinkedList<CronoprogrammaFasiItem>();
		this.allegatiList = new LinkedList<AllegatiCronoprogrammaFasi>();
	}
	
	
	public List<CronoprogrammaFasiItem> getFasiItemList() {
		return fasiItemList;
	}
	public void setFasiItemList(List<CronoprogrammaFasiItem> fasiItemList) {
		this.fasiItemList = fasiItemList;
	}
	public List<AllegatiCronoprogrammaFasi> getAllegatiList() {
		return allegatiList;
	}
	public void setAllegatiList(List<AllegatiCronoprogrammaFasi> allegatiList) {
		this.allegatiList = allegatiList;
	}
		
	public boolean addItem (CronoprogrammaFasiItem allegatiList) {
		return fasiItemList.add(allegatiList);
	}
	
	

	
	
	
}
