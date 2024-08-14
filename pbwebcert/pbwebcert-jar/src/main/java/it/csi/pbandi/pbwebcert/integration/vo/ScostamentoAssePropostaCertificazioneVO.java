/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;


public class ScostamentoAssePropostaCertificazioneVO extends PbandiRPropCertScostAsseVO {
	public String getDescLineaCompleta() {
		return descLineaCompleta;
	}

	public void setDescLineaCompleta(String descLineaCompleta) {
		this.descLineaCompleta = descLineaCompleta;
	}

	public String getDescTipoSoggFinanziatore() {
		return descTipoSoggFinanziatore;
	}

	public void setDescTipoSoggFinanziatore(String descTipoSoggFinanziatore) {
		this.descTipoSoggFinanziatore = descTipoSoggFinanziatore;
	}

	private String descLineaCompleta;
	private String descTipoSoggFinanziatore;
}
