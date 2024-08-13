/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import java.io.Serializable;
import java.util.Map;

public class IstanzaAttivitaCorrenteDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	public IstanzaAttivitaCorrenteDTO() {	}

	private String codCausaleErogazione;
	private boolean fake = true;
	private Long idDichiarazione;
	private Long idProgetto;
	private boolean isNeoflux = false;
	private Map<String, String> metadati;
	private String taskIdentity;
	private String taskName;
	private String testoNotifica;
	private String urlMiniApp;

	public void setUrlMiniApp(String urlMiniApp) {
		this.urlMiniApp = urlMiniApp;
	}

	public String getUrlMiniApp() {
		return urlMiniApp;
	}

	public void setTaskIdentity(String taskIdentity) {
		this.taskIdentity = taskIdentity;
	}

	public String getTaskIdentity() {
		return taskIdentity;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	@Deprecated
	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdDichiarazione(Long idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}

	@Deprecated
	public Long getIdDichiarazione() {
		return idDichiarazione;
	}

	public void setCodCausaleErogazione(String codCausaleErogazione) {
		this.codCausaleErogazione = codCausaleErogazione;
	}

	@Deprecated
	public String getCodCausaleErogazione() {
		return codCausaleErogazione;
	}

	public void setMetadati(Map<String, String> metadati) {
		this.metadati = metadati;
	}

	public Map<String, String> getMetadati() {
		return metadati;
	}

	public void setTestoNotifica(String testoNotifica) {
		this.testoNotifica = testoNotifica;
	}

	public String getTestoNotifica() {
		return testoNotifica;
	}

	public boolean isNeoflux() {
		return isNeoflux;
	}

	public void setNeoflux(boolean isNeoflux) {
		this.isNeoflux = isNeoflux;
	}

	@Override
	public String toString() {
		return "IstanzaAttivitaCorrenteDTO [codCausaleErogazione=" + codCausaleErogazione + ", fake=" + fake
				+ ", idDichiarazione=" + idDichiarazione + ", idProgetto=" + idProgetto + ", isNeoflux=" + isNeoflux
				+ ", metadati=" + metadati + ", taskIdentity=" + taskIdentity + ", taskName=" + taskName
				+ ", testoNotifica=" + testoNotifica + ", urlMiniApp=" + urlMiniApp + "]";
	}

}
