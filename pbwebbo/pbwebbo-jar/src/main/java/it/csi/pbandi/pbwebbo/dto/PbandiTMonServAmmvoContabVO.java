/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.dto;

import java.time.LocalDate;
import java.util.Date;

public class PbandiTMonServAmmvoContabVO {
	
	private Long idMonitAmmVoCont;
	private int idServizio;
	private Long idUtente;
	private String modalitaChiamata;
	private String esito;
	private String codiceErrore;
	private String messaggioErrore;
	private LocalDate  dataInizioChiamata;
	private LocalDate dataFineChiamata;
	private String parametriInput;
	private String parametriOutput;
	private int idEntita;
	private int idTarget;
	private int numeroKo;
	private String categoriaServizio;
	
	
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
	public LocalDate getDataInizioChiamata() {
		return dataInizioChiamata;
	}
	public void setDataInizioChiamata(LocalDate dataInizioChiamata) {
		this.dataInizioChiamata = dataInizioChiamata;
	}
	public LocalDate getDataFineChiamata() {
		return dataFineChiamata;
	}
	public void setDataFineChiamata(LocalDate dataFineChiamata) {
		this.dataFineChiamata = dataFineChiamata;
	}
	public Long getIdMonitAmmVoCont() {
		return idMonitAmmVoCont;
	}
	public void setIdMonitAmmVoCont(Long idMonitAmmVoCont) {
		this.idMonitAmmVoCont = idMonitAmmVoCont;
	}
	public int getIdServizio() {
		return idServizio;
	}
	public void setIdServizio(int idServizio) {
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
	public String getParametriInput() {
		return parametriInput;
	}
	public void setParametriInput(String parametriInput) {
		this.parametriInput = parametriInput;
	}
	public String getParametriOutput() {
		return parametriOutput;
	}
	public void setParametriOutput(String parametriOutput) {
		this.parametriOutput = parametriOutput;
	}
	public int getIdEntita() {
		return idEntita;
	}
	public void setIdEntita(int idEntita) {
		this.idEntita = idEntita;
	}
	public int getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(int idTarget) {
		this.idTarget = idTarget;
	}
	public int getNumeroKo() {
		return numeroKo;
	}
	public void setNumeroKo(int numeroKo) {
		this.numeroKo = numeroKo;
	}
	public String getCategoriaServizio() {
		return categoriaServizio;
	}
	public void setCategoriaServizio(String categoriaServizio) {
		this.categoriaServizio = categoriaServizio;
	}
	
	
}
