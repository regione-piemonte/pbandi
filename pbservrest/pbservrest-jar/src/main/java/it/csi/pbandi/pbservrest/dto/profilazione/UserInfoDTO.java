/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservrest.dto.profilazione;

public class UserInfoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private java.lang.Long idUtente = null;

//	private RuoloDTO[] ruoli = null;

//	private BeneficiarioDTO[] beneficiari = null;

	private java.lang.Long idSoggetto = null;

	private java.lang.String cognome = null;

	private java.lang.String nome = null;

	private java.lang.String codiceFiscale = null;

	private java.lang.Boolean isIncaricato = null;

	private java.lang.String ruoloHelp = null;

	public java.lang.Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(java.lang.Long idUtente) {
		this.idUtente = idUtente;
	}

//	public RuoloDTO[] getRuoli() {
//		return ruoli;
//	}
//
//	public void setRuoli(RuoloDTO[] ruoli) {
//		this.ruoli = ruoli;
//	}
//
//	public BeneficiarioDTO[] getBeneficiari() {
//		return beneficiari;
//	}
//
//	public void setBeneficiari(BeneficiarioDTO[] beneficiari) {
//		this.beneficiari = beneficiari;
//	}

	public java.lang.Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(java.lang.Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public java.lang.String getCognome() {
		return cognome;
	}

	public void setCognome(java.lang.String cognome) {
		this.cognome = cognome;
	}

	public java.lang.String getNome() {
		return nome;
	}

	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(java.lang.String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public java.lang.Boolean getIsIncaricato() {
		return isIncaricato;
	}

	public void setIsIncaricato(java.lang.Boolean isIncaricato) {
		this.isIncaricato = isIncaricato;
	}

	public java.lang.String getRuoloHelp() {
		return ruoloHelp;
	}

	public void setRuoloHelp(java.lang.String ruoloHelp) {
		this.ruoloHelp = ruoloHelp;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "UserInfoDTO [idUtente=" + idUtente );
		//+ ", ruoli=" + Arrays.toString(ruoli) 
		//+ ", beneficiari="	+ Arrays.toString(beneficiari));
		sb.append(", idSoggetto=" + idSoggetto + ", cognome=" + cognome + ", nome="	+ nome + ", codiceFiscale=" + codiceFiscale );
		sb.append(", isIncaricato=" + isIncaricato + ", ruoloHelp="	+ ruoloHelp + "]");
		return sb.toString();
	}
	
}
