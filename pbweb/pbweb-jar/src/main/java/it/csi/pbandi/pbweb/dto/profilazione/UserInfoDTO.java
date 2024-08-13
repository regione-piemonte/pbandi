/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.profilazione;

import java.util.Arrays;

public class UserInfoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;

	private Long idUtente = null;

	private RuoloDTO[] ruoli = null;

	private BeneficiarioDTO[] beneficiari = null;

	private Long idSoggetto = null;

	private String cognome = null;

	private String nome = null;

	private String codiceFiscale = null;

	private Boolean isIncaricato = null;

	private String ruoloHelp = null;

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public RuoloDTO[] getRuoli() {
		return ruoli;
	}

	public void setRuoli(RuoloDTO[] ruoli) {
		this.ruoli = ruoli;
	}

	public BeneficiarioDTO[] getBeneficiari() {
		return beneficiari;
	}

	public void setBeneficiari(BeneficiarioDTO[] beneficiari) {
		this.beneficiari = beneficiari;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
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

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Boolean getIsIncaricato() {
		return isIncaricato;
	}

	public void setIsIncaricato(Boolean isIncaricato) {
		this.isIncaricato = isIncaricato;
	}

	public String getRuoloHelp() {
		return ruoloHelp;
	}

	public void setRuoloHelp(String ruoloHelp) {
		this.ruoloHelp = ruoloHelp;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "UserInfoDTO [idUtente=" + idUtente + ", ruoli=" + Arrays.toString(ruoli) + ", beneficiari="	+ Arrays.toString(beneficiari));
		sb.append(", idSoggetto=" + idSoggetto + ", cognome=" + cognome + ", nome="	+ nome + ", codiceFiscale=" + codiceFiscale );
		sb.append(", isIncaricato=" + isIncaricato + ", ruoloHelp="	+ ruoloHelp + "]");
		return sb.toString();
	}
	
}
