/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.request;

import java.io.Serializable;
import java.util.List;

import it.csi.pbandi.pbweberog.dto.disimpegni.RigaModificaRevocaItem;
import it.csi.pbandi.pbweberog.dto.revoca.RigaRevocaItem;

public class RequestSalvaRevoche implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProgetto;
	private String note;
	private String estremi;
	private String dtRevoca;
	private String dtDecorRevoca;
	private Long idMotivoRevoca;
	private String ordineRecupero;
	private Long idModalitaRecupero;
	
	private String codCausaleDisimpegno;
	private Long idAnnoContabile;
	
	
	private List<RigaRevocaItem> revoche;
	private List<RigaModificaRevocaItem> disimpegni;
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getEstremi() {
		return estremi;
	}
	public void setEstremi(String estremi) {
		this.estremi = estremi;
	}
	public String getDtRevoca() {
		return dtRevoca;
	}
	public void setDtRevoca(String dtRevoca) {
		this.dtRevoca = dtRevoca;
	}
	public String getDtDecorRevoca() {
		return dtDecorRevoca;
	}
	public void setDtDecorRevoca(String dtDecorRevoca) {
		this.dtDecorRevoca = dtDecorRevoca;
	}
	public Long getIdMotivoRevoca() {
		return idMotivoRevoca;
	}
	public void setIdMotivoRevoca(Long idMotivoRevoca) {
		this.idMotivoRevoca = idMotivoRevoca;
	}
	public String getOrdineRecupero() {
		return ordineRecupero;
	}
	public void setOrdineRecupero(String ordineRecupero) {
		this.ordineRecupero = ordineRecupero;
	}
	public Long getIdModalitaRecupero() {
		return idModalitaRecupero;
	}
	public void setIdModalitaRecupero(Long idModalitaRecupero) {
		this.idModalitaRecupero = idModalitaRecupero;
	}
	public List<RigaRevocaItem> getRevoche() {
		return revoche;
	}
	public void setRevoche(List<RigaRevocaItem> revoche) {
		this.revoche = revoche;
	}
	public String getCodCausaleDisimpegno() {
		return codCausaleDisimpegno;
	}
	public void setCodCausaleDisimpegno(String codCausaleDisimpegno) {
		this.codCausaleDisimpegno = codCausaleDisimpegno;
	}
	public List<RigaModificaRevocaItem> getDisimpegni() {
		return disimpegni;
	}
	public void setDisimpegni(List<RigaModificaRevocaItem> disimpegni) {
		this.disimpegni = disimpegni;
	}
	public Long getIdAnnoContabile() {
		return idAnnoContabile;
	}
	public void setIdAnnoContabile(Long idAnnoContabile) {
		this.idAnnoContabile = idAnnoContabile;
	}
	
	
	
}
