/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.vo;

public class RegolaAssociataBandoLineaVO extends PbandiRRegolaBandoLineaVO {

	private String descRegolaComposta;
	private String tipoAssociazione;
	private String descBreveRegola;
	private String nomeBandolinea;

	public void setDescRegolaComposta(String descRegolaComposta) {
		this.descRegolaComposta = descRegolaComposta;
	}

	public String getDescRegolaComposta() {
		return descRegolaComposta;
	}

	public void setTipoAssociazione(String tipoAssociazione) {
		this.tipoAssociazione = tipoAssociazione;
	}

	public String getTipoAssociazione() {
		return tipoAssociazione;
	}

	public void setDescBreveRegola(String descBreveRegola) {
		this.descBreveRegola = descBreveRegola;
	}

	public String getDescBreveRegola() {
		return descBreveRegola;
	}

	public void setNomeBandolinea(String nomeBandolinea) {
		this.nomeBandolinea = nomeBandolinea;
	}

	public String getNomeBandolinea() {
		return nomeBandolinea;
	}
}
