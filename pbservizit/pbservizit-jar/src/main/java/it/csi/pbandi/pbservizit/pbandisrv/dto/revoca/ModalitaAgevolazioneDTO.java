/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.revoca;

public class ModalitaAgevolazioneDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private Long idProgetto;
	private Long idModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private String descModalitaAgevolazione;
	private Double importoErogazioni;
	private Double quotaImportoAgevolato;
	private Double totaleImportoRevocato;
	private Double totaleImportoRecuperato;
	private CausaleErogazioneDTO[] causaliErogazioni;
	private RecuperoDTO[] recuperi;
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(Long idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public Double getImportoErogazioni() {
		return importoErogazioni;
	}
	public void setImportoErogazioni(Double importoErogazioni) {
		this.importoErogazioni = importoErogazioni;
	}
	public Double getQuotaImportoAgevolato() {
		return quotaImportoAgevolato;
	}
	public void setQuotaImportoAgevolato(Double quotaImportoAgevolato) {
		this.quotaImportoAgevolato = quotaImportoAgevolato;
	}
	public Double getTotaleImportoRevocato() {
		return totaleImportoRevocato;
	}
	public void setTotaleImportoRevocato(Double totaleImportoRevocato) {
		this.totaleImportoRevocato = totaleImportoRevocato;
	}
	public Double getTotaleImportoRecuperato() {
		return totaleImportoRecuperato;
	}
	public void setTotaleImportoRecuperato(Double totaleImportoRecuperato) {
		this.totaleImportoRecuperato = totaleImportoRecuperato;
	}
	public CausaleErogazioneDTO[] getCausaliErogazioni() {
		return causaliErogazioni;
	}
	public void setCausaliErogazioni(CausaleErogazioneDTO[] causaliErogazioni) {
		this.causaliErogazioni = causaliErogazioni;
	}
	public RecuperoDTO[] getRecuperi() {
		return recuperi;
	}
	public void setRecuperi(RecuperoDTO[] recuperi) {
		this.recuperi = recuperi;
	}
	
	
	

}
