/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class Erogazione implements java.io.Serializable {

	private String descCausaleErogazione;
	private String numero;
	private String dataContabile;
	private Double importoErogazioneEffettiva;
	private String codCausaleErogazione;
	private Double percentualeErogazioneEffettiva;
	private Double importoCalcolato;
	private Double importoResiduo;
	private String codModalitaAgevolazione;
	private String descModalitaAgevolazione;
	private String codModalitaErogazione;
	private String descModalitaErogazione;
	private String codTipoDirezione;
	private String descTipoDirezione;
	private String note;
	private Double percentualeErogazioneIterFinanziario;
	private Double importoErogazioneDaIterFinanziario;
	private Long idErogazione;
	private static final long serialVersionUID = 1L;

	private Double percErogazione;
	private Double percLimite;
	
	
	public String getDescCausaleErogazione() {
		return descCausaleErogazione;
	}

	public void setDescCausaleErogazione(String descCausaleErogazione) {
		this.descCausaleErogazione = descCausaleErogazione;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDataContabile() {
		return dataContabile;
	}

	public void setDataContabile(String dataContabile) {
		this.dataContabile = dataContabile;
	}

	public Double getImportoErogazioneEffettiva() {
		return importoErogazioneEffettiva;
	}

	public void setImportoErogazioneEffettiva(Double importoErogazioneEffettiva) {
		this.importoErogazioneEffettiva = importoErogazioneEffettiva;
	}

	public String getCodCausaleErogazione() {
		return codCausaleErogazione;
	}

	public void setCodCausaleErogazione(String codCausaleErogazione) {
		this.codCausaleErogazione = codCausaleErogazione;
	}

	public Double getPercentualeErogazioneEffettiva() {
		return percentualeErogazioneEffettiva;
	}

	public void setPercentualeErogazioneEffettiva(Double percentualeErogazioneEffettiva) {
		this.percentualeErogazioneEffettiva = percentualeErogazioneEffettiva;
	}

	public Double getImportoCalcolato() {
		return importoCalcolato;
	}

	public void setImportoCalcolato(Double importoCalcolato) {
		this.importoCalcolato = importoCalcolato;
	}

	public Double getImportoResiduo() {
		return importoResiduo;
	}

	public void setImportoResiduo(Double importoResiduo) {
		this.importoResiduo = importoResiduo;
	}

	public String getCodModalitaAgevolazione() {
		return codModalitaAgevolazione;
	}

	public void setCodModalitaAgevolazione(String codModalitaAgevolazione) {
		this.codModalitaAgevolazione = codModalitaAgevolazione;
	}

	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}

	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}

	public String getCodModalitaErogazione() {
		return codModalitaErogazione;
	}

	public void setCodModalitaErogazione(String codModalitaErogazione) {
		this.codModalitaErogazione = codModalitaErogazione;
	}

	public String getDescModalitaErogazione() {
		return descModalitaErogazione;
	}

	public void setDescModalitaErogazione(String descModalitaErogazione) {
		this.descModalitaErogazione = descModalitaErogazione;
	}

	public String getCodTipoDirezione() {
		return codTipoDirezione;
	}

	public void setCodTipoDirezione(String codTipoDirezione) {
		this.codTipoDirezione = codTipoDirezione;
	}

	public String getDescTipoDirezione() {
		return descTipoDirezione;
	}

	public void setDescTipoDirezione(String descTipoDirezione) {
		this.descTipoDirezione = descTipoDirezione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getPercentualeErogazioneIterFinanziario() {
		return percentualeErogazioneIterFinanziario;
	}

	public void setPercentualeErogazioneIterFinanziario(Double percentualeErogazioneIterFinanziario) {
		this.percentualeErogazioneIterFinanziario = percentualeErogazioneIterFinanziario;
	}

	public Double getImportoErogazioneDaIterFinanziario() {
		return importoErogazioneDaIterFinanziario;
	}

	public void setImportoErogazioneDaIterFinanziario(Double importoErogazioneDaIterFinanziario) {
		this.importoErogazioneDaIterFinanziario = importoErogazioneDaIterFinanziario;
	}

	public Long getIdErogazione() {
		return idErogazione;
	}

	public void setIdErogazione(Long idErogazione) {
		this.idErogazione = idErogazione;
	}


	public String toString() {
		return super.toString();
	}

	public Double getPercErogazione() {
		return percErogazione;
	}

	public void setPercErogazione(Double percErogazione) {
		this.percErogazione = percErogazione;
	}

	public Double getPercLimite() {
		return percLimite;
	}

	public void setPercLimite(Double percLimite) {
		this.percLimite = percLimite;
	}
	
	
	
}
