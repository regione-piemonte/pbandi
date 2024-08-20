/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

public class DimensioneImpresaVO {
	
	private String dataValutazione;
	private String esito;
	public String getDataValutazione() {
		return dataValutazione;
	}
	public void setDataValutazione(String dataValutazione) {
		this.dataValutazione = dataValutazione;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	@Override
	public String toString() {
		return "DimensioneImpresaVO [dataValutazione=" + dataValutazione + ", esito=" + esito + "]";
	}

	
}
