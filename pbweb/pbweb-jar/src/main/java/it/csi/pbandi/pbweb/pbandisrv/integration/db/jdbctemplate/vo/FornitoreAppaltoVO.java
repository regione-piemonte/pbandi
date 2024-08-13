/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRFornitoreAppaltoVO;

public class FornitoreAppaltoVO extends PbandiRFornitoreAppaltoVO {
	
	private String denominazioneFornitore;
	
	private String codiceFiscaleFornitore;
	
	private String nomeFornitore;
	
	private String cognomeFornitore;
	
	private String descTipoPercettore;
	
	private String descTipoSoggetto;

	public String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}

	public void setCodiceFiscaleFornitore(String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}

	public String getNomeFornitore() {
		return nomeFornitore;
	}

	public void setNomeFornitore(String nomeFornitore) {
		this.nomeFornitore = nomeFornitore;
	}

	public String getCognomeFornitore() {
		return cognomeFornitore;
	}

	public void setCognomeFornitore(String cognomeFornitore) {
		this.cognomeFornitore = cognomeFornitore;
	}

	public String getDescTipoPercettore() {
		return descTipoPercettore;
	}

	public void setDescTipoPercettore(String descTipoPercettore) {
		this.descTipoPercettore = descTipoPercettore;
	}

	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}

	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}

}
