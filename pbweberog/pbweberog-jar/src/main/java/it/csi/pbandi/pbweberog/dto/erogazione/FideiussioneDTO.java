/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.erogazione;

public class FideiussioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private String descrizione = null;
	private String descrizioneTipoTrattamento = null;
	private String numero = null;
	private Double importo = null;
	private Double importoTotaleTipoTrattamento = null;
	private Long id = null;

	private java.util.Date dataDecorrenza = null;

	public void setDataDecorrenza(java.util.Date val) {
		dataDecorrenza = val;
	}

	public java.util.Date getDataDecorrenza() {
		return dataDecorrenza;
	}

	private java.util.Date dataScadenza = null;

	public void setDataScadenza(java.util.Date val) {
		dataScadenza = val;
	}

	public java.util.Date getDataScadenza() {
		return dataScadenza;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String val) {
		descrizione = val;
	}

	public String getDescrizioneTipoTrattamento() {
		return descrizioneTipoTrattamento;
	}

	public void setDescrizioneTipoTrattamento(String val) {
		descrizioneTipoTrattamento = val;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String val) {
		numero = val;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double val) {
		importo = val;
	}

	public Double getImportoTotaleTipoTrattamento() {
		return importoTotaleTipoTrattamento;
	}

	public void setImportoTotaleTipoTrattamento(Double val) {
		importoTotaleTipoTrattamento = val;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long val) {
		id = val;
	}

}