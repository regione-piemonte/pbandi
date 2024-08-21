/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione;

public class FideiussioneTipoTrattamentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Double idTipoTrattamento;
	private String descrizioneTipoTrattamento;
	private Double importo;
	private String descBreveTipoTrattamento;
	public Double getIdTipoTrattamento() {
		return idTipoTrattamento;
	}
	public void setIdTipoTrattamento(Double idTipoTrattamento) {
		this.idTipoTrattamento = idTipoTrattamento;
	}
	public String getDescrizioneTipoTrattamento() {
		return descrizioneTipoTrattamento;
	}
	public void setDescrizioneTipoTrattamento(String descrizioneTipoTrattamento) {
		this.descrizioneTipoTrattamento = descrizioneTipoTrattamento;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public String getDescBreveTipoTrattamento() {
		return descBreveTipoTrattamento;
	}
	public void setDescBreveTipoTrattamento(String descBreveTipoTrattamento) {
		this.descBreveTipoTrattamento = descBreveTipoTrattamento;
	}

	
}
