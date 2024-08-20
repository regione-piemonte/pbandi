/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi;

import java.math.BigDecimal;

public class IterAutorizzativiMiniVO {
	
	private String descrizioneIter;
	private String descrizioneBando;
	private String beneficiario;
	private String descrizioneProgetto;
	private String descrizioneStato;
	private String codiceVisualizzato; 
	
	
	private BigDecimal idBeneficiario;
	private BigDecimal idProgetto;
	private BigDecimal idSoggetto;
	private BigDecimal idBando;
	private BigDecimal idWorkFlow; 
	private boolean isPersonaGiuridica;
	private int idStatoIter; 
	private int idTipoIter;
	private BigDecimal idTarget; 
	
	private boolean abilitaGestioneIter;

	public boolean isAbilitaGestioneIter() {
		return abilitaGestioneIter;
	}

	public void setAbilitaGestioneIter(boolean abilitaGestioneIter) {
		this.abilitaGestioneIter = abilitaGestioneIter;
	}

	public BigDecimal getIdTarget() {
		return idTarget;
	}
	public void setIdTarget(BigDecimal idTarget) {
		this.idTarget = idTarget;
	}
	public BigDecimal getIdWorkFlow() {
		return idWorkFlow;
	}
	public void setIdWorkFlow(BigDecimal idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}
	public int getIdStatoIter() {
		return idStatoIter;
	}
	public void setIdStatoIter(int idStatoIter) {
		this.idStatoIter = idStatoIter;
	}
	public int getIdTipoIter() {
		return idTipoIter;
	}
	public void setIdTipoIter(int idTipoIter) {
		this.idTipoIter = idTipoIter;
	}
	public boolean isPersonaGiuridica() {
		return isPersonaGiuridica;
	}
	public void setPersonaGiuridica(boolean isPersonaGiuridica) {
		this.isPersonaGiuridica = isPersonaGiuridica;
	}
	public IterAutorizzativiMiniVO() {
		super();
	}
	public String getDescrizioneIter() {
		return descrizioneIter;
	}
	public void setDescrizioneIter(String descrizioneIter) {
		this.descrizioneIter = descrizioneIter;
	}
	public String getDescrizioneBando() {
		return descrizioneBando;
	}
	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public String getDescrizioneProgetto() {
		return descrizioneProgetto;
	}
	public void setDescrizioneProgetto(String descrizioneProgetto) {
		this.descrizioneProgetto = descrizioneProgetto;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public BigDecimal getIdBeneficiario() {
		return idBeneficiario;
	}
	public void setIdBeneficiario(BigDecimal idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdBando() {
		return idBando;
	}
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	

	


    
}
