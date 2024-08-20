/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.amministrativoContabile;

import java.io.Serializable;
import java.util.Date;

public class MonitoringAmministrativoContabileDTO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private Long idMonit;
	private Long idServizio;
	private Long idUtente;
	private String modalitaChiamata;
	private String esito;
	private String codiceErrore;
	private String messaggioErrore;
	private Date datetimeInizioChiamata;
	private Date datetimeFineChiamata;
	private String parametriDiInput;
	private String parametriDiOutput;
	private Long idEntita;
	private Long idTarget;
	
	public Long getIdMonit() {
		return idMonit;
	}
	public void setIdMonit(Long idMonit) {
		this.idMonit = idMonit;
	}
	public Long getIdServizio() {
		return idServizio;
	}
	public void setIdServizio(Long idServizio) {
		this.idServizio = idServizio;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public String getModalitaChiamata() {
		return modalitaChiamata;
	}
	public void setModalitaChiamata(String modalitaChiamata) {
		this.modalitaChiamata = modalitaChiamata;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getCodiceErrore() {
		return codiceErrore;
	}
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	public Date getDatetimeInizioChiamata() {
		return datetimeInizioChiamata;
	}
	public void setDatetimeInizioChiamata(Date datetimeInizioChiamata) {
		this.datetimeInizioChiamata = datetimeInizioChiamata;
	}
	public Date getDatetimeFineChiamata() {
		return datetimeFineChiamata;
	}
	public void setDatetimeFineChiamata(Date datetimeFineChiamata) {
		this.datetimeFineChiamata = datetimeFineChiamata;
	}
	public String getParametriDiInput() {
		return parametriDiInput;
	}
	public void setParametriDiInput(String parametriDiInput) {
		this.parametriDiInput = parametriDiInput;
	}
	public String getParametriDiOutput() {
		return parametriDiOutput;
	}
	public void setParametriDiOutput(String parametriDiOutput) {
		this.parametriDiOutput = parametriDiOutput;
	}
	public Long getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(Long idEntita) {
		this.idEntita = idEntita;
	}
	public Long getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(Long idTarget) {
		this.idTarget = idTarget;
	}

}
