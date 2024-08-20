/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.util.Date;
import java.util.List;

public class ContestazioniVO {

	private Long idGestioneRevoca;
	private Long numeroRevoca;
	private Date dataNotifica;
	private String numeroProtocollo;
	private String causaRevoca;

	private Long idContestazione;
	private Long numeroContestazione;
	private Long idStatoContestazione;
	private String descStatoContestazione;
	private Date dtStatoContestazione;
	private Date dataScadenzaContestazione; //DT_NOTIFICA + GG_RISPOSTA

	private Boolean isAbilitatoIntegra; //Stato Contestazione = aperta
	private Boolean isAbilitatoInvia; //Stato Contestazione = aperta | associata la lettera
	private Boolean isAbilitatoElimina; //Stato Contestazione = aperta
	private Boolean isAbilitatoContestaz; // idControdeduzione = null

	//utils
	private List<AllegatiContestazioniVO> allegati;

	public Long getIdGestioneRevoca() {
		return idGestioneRevoca;
	}

	public void setIdGestioneRevoca(Long idGestioneRevoca) {
		this.idGestioneRevoca = idGestioneRevoca;
	}

	public Long getNumeroRevoca() {
		return numeroRevoca;
	}

	public void setNumeroRevoca(Long numeroRevoca) {
		this.numeroRevoca = numeroRevoca;
	}

	public Date getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getCausaRevoca() {
		return causaRevoca;
	}

	public void setCausaRevoca(String causaRevoca) {
		this.causaRevoca = causaRevoca;
	}

	public Long getIdContestazione() {
		return idContestazione;
	}

	public void setIdContestazione(Long idContestazione) {
		this.idContestazione = idContestazione;
	}

	public Long getNumeroContestazione() {
		return numeroContestazione;
	}

	public void setNumeroContestazione(Long numeroContestazione) {
		this.numeroContestazione = numeroContestazione;
	}

	public Long getIdStatoContestazione() {
		return idStatoContestazione;
	}

	public void setIdStatoContestazione(Long idStatoContestazione) {
		this.idStatoContestazione = idStatoContestazione;
	}

	public String getDescStatoContestazione() {
		return descStatoContestazione;
	}

	public void setDescStatoContestazione(String descStatoContestazione) {
		this.descStatoContestazione = descStatoContestazione;
	}

	public Date getDtStatoContestazione() {
		return dtStatoContestazione;
	}

	public void setDtStatoContestazione(Date dtStatoContestazione) {
		this.dtStatoContestazione = dtStatoContestazione;
	}

	public Date getDataScadenzaContestazione() {
		return dataScadenzaContestazione;
	}

	public void setDataScadenzaContestazione(Date dataScadenzaContestazione) {
		this.dataScadenzaContestazione = dataScadenzaContestazione;
	}

	public List<AllegatiContestazioniVO> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatiContestazioniVO> allegati) {
		this.allegati = allegati;
	}

	public Boolean getAbilitatoIntegra() {
		return isAbilitatoIntegra;
	}

	public void setAbilitatoIntegra(Boolean abilitatoIntegra) {
		isAbilitatoIntegra = abilitatoIntegra;
	}

	public Boolean getAbilitatoInvia() {
		return isAbilitatoInvia;
	}

	public void setAbilitatoInvia(Boolean abilitatoInvia) {
		isAbilitatoInvia = abilitatoInvia;
	}

	public Boolean getAbilitatoElimina() {
		return isAbilitatoElimina;
	}

	public void setAbilitatoElimina(Boolean abilitatoElimina) {
		isAbilitatoElimina = abilitatoElimina;
	}

	public Boolean getAbilitatoContestaz() {
		return isAbilitatoContestaz;
	}

	public void setAbilitatoContestaz(Boolean abilitatoContestaz) {
		isAbilitatoContestaz = abilitatoContestaz;
	}
}
