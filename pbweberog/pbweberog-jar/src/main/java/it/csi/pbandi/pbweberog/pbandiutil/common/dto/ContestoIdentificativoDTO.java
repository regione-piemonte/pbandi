/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.pbandiutil.common.dto;

import java.math.BigDecimal;

public class ContestoIdentificativoDTO {
	private Long idUtente;

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}

	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public String getCodUtenteFlux() {
		return codUtenteFlux;
	}

	public void setCodUtenteFlux(String codUtenteFlux) {
		this.codUtenteFlux = codUtenteFlux;
	}

	public void setIdIstanzaAttivitaProcesso(String idIstanzaAttivitaProcesso) {
		this.idIstanzaAttivitaProcesso = idIstanzaAttivitaProcesso;
	}

	public String getIdIstanzaAttivitaProcesso() {
		return idIstanzaAttivitaProcesso;
	}

	public void setIdentitaIride(String identitaIride) {
		this.identitaIride = identitaIride;
	}

	public String getIdentitaIride() {
		return identitaIride;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getRuoloHelp() {
		return ruoloHelp;
	}

	public void setRuoloHelp(String ruoloHelp) {
		this.ruoloHelp = ruoloHelp;
	}

	private String codFiscale;
	private String cognome;
	private String nome;
	private String descBreveTipoAnagrafica;
	private BigDecimal idSoggetto;
	private String idIstanzaAttivitaProcesso;
	private String codUtenteFlux;
	private String identitaIride;
	private String taskName;
	private String ruoloHelp;

}