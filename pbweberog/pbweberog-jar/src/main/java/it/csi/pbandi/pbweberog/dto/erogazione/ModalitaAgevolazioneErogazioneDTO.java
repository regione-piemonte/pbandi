/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;


public class ModalitaAgevolazioneErogazioneDTO implements java.io.Serializable {
	
	static final long serialVersionUID = 1;
	private Long idModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private String descModalitaAgevolazione;
	private Double importoTotaleRecupero;
	private CausaleErogazioneDTO[] causaliErogazione;
	private Double ultimoImportoAgevolato;
	private Double importoTotaleErogazioni;
	private Double importoResiduoDaErogare;
	private Double importoRevocato; 
	
	
	
	
	
	public Double getImportoRevocato() {
		return importoRevocato;
	}
	public void setImportoRevocato(Double importoRevocato) {
		this.importoRevocato = importoRevocato;
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
	public Double getImportoTotaleRecupero() {
		return importoTotaleRecupero;
	}
	public void setImportoTotaleRecupero(Double importoTotaleRecupero) {
		this.importoTotaleRecupero = importoTotaleRecupero;
	}
	public CausaleErogazioneDTO[] getCausaliErogazione() {
		return causaliErogazione;
	}
	public void setCausaliErogazione(CausaleErogazioneDTO[] causaliErogazione) {
		this.causaliErogazione = causaliErogazione;
	}
	public Double getUltimoImportoAgevolato() {
		return ultimoImportoAgevolato;
	}
	public void setUltimoImportoAgevolato(Double ultimoImportoAgevolato) {
		this.ultimoImportoAgevolato = ultimoImportoAgevolato;
	}
	public Double getImportoTotaleErogazioni() {
		return importoTotaleErogazioni;
	}
	public void setImportoTotaleErogazioni(Double importoTotaleErogazioni) {
		this.importoTotaleErogazioni = importoTotaleErogazioni;
	}
	public Double getImportoResiduoDaErogare() {
		return importoResiduoDaErogare;
	}
	public void setImportoResiduoDaErogare(Double importoResiduoDaErogare) {
		this.importoResiduoDaErogare = importoResiduoDaErogare;
	}
	
	
	
	
}
