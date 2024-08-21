/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.recupero;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRecuperoVO;

public class RecuperoProgettoVO extends PbandiTRecuperoVO {
	
	private String descModalitaAgevolazione;
	private String descBreveModalitaAgevolaz;
	private String codiceVisualizzato;
	private String descBreveTipoRecupero;
	private String descTipoRecupero;
	
	
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public void setDescBreveTipoRecupero(String descBreveTipoRecupero) {
		this.descBreveTipoRecupero = descBreveTipoRecupero;
	}
	public String getDescBreveTipoRecupero() {
		return descBreveTipoRecupero;
	}
	public void setDescTipoRecupero(String descTipoRecupero) {
		this.descTipoRecupero = descTipoRecupero;
	}
	public String getDescTipoRecupero() {
		return descTipoRecupero;
	}

}
