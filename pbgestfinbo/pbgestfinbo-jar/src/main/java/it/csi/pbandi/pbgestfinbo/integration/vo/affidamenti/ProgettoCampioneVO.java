/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

public class ProgettoCampioneVO {
	
	private Long idProgetto;
	private String codiceVisualizzatoProgetto;
	private String denominazioneBeneficiario;
	private String nomeBandoLinea;
	private String dataCampionamento;
	private Long idSoggetto;
	private Long progrSoggProg; 
	private boolean isConfirmable; 
	
	
	
	
	
	public boolean isConfirmable() {
		return isConfirmable;
	}
	public void setConfirmable(boolean isConfirmable) {
		this.isConfirmable = isConfirmable;
	}
	public Long getProgrSoggProg() {
		return progrSoggProg;
	}
	public void setProgrSoggProg(Long progrSoggProg) {
		this.progrSoggProg = progrSoggProg;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}
	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	public String getDataCampionamento() {
		return dataCampionamento;
	}
	public void setDataCampionamento(String dataCampionamento) {
		this.dataCampionamento = dataCampionamento;
	}
	
	

}
