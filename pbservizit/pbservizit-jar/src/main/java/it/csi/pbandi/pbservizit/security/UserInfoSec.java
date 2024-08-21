/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.security;

import java.util.ArrayList;

import it.csi.pbandi.pbservizit.dto.profilazione.Ruolo;

public class UserInfoSec implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String idIride = null;
	private String codFisc = null;
	private String cognome = null;
	private String nome = null;
	private String ente = null;
	private String ruolo = null;
	private Long idUtente = null;
	private String tipoBeneficiario = null;
	private String codiceRuolo = null;
	private BeneficiarioSec beneficiarioSelezionato = null;
	private Long idSoggetto = null;
	private Long idSoggettoIncaricante = null;
	private Boolean beneficiarioSelezionatoAutomaticamente = null;
	private String codRuolo = null;
	private Boolean isIncaricato = null;
	private String ruoloHelp = null;
	private ArrayList<Ruolo> ruoli = new ArrayList<Ruolo>();
	private ArrayList<BeneficiarioSec> beneficiari = new ArrayList<BeneficiarioSec>();
	private Long beneficiariCount = null;

	public String getIdIride() {
		return idIride;
	}

	public void setIdIride(String idIride) {
		this.idIride = idIride;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
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

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	public BeneficiarioSec getBeneficiarioSelezionato() {
		return beneficiarioSelezionato;
	}

	public void setBeneficiarioSelezionato(BeneficiarioSec beneficiarioSelezionato) {
		this.beneficiarioSelezionato = beneficiarioSelezionato;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public Long getIdSoggettoIncaricante() {
		return idSoggettoIncaricante;
	}

	public void setIdSoggettoIncaricante(Long idSoggettoIncaricante) {
		this.idSoggettoIncaricante = idSoggettoIncaricante;
	}

	public Boolean getBeneficiarioSelezionatoAutomaticamente() {
		return beneficiarioSelezionatoAutomaticamente;
	}

	public void setBeneficiarioSelezionatoAutomaticamente(Boolean beneficiarioSelezionatoAutomaticamente) {
		this.beneficiarioSelezionatoAutomaticamente = beneficiarioSelezionatoAutomaticamente;
	}

	public String getCodRuolo() {
		return codRuolo;
	}

	public void setCodRuolo(String codRuolo) {
		this.codRuolo = codRuolo;
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

	public ArrayList<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(ArrayList<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public ArrayList<BeneficiarioSec> getBeneficiari() {
		return beneficiari;
	}

	public void setBeneficiari(ArrayList<BeneficiarioSec> beneficiari) {
		this.beneficiari = beneficiari;
	}

	public Long getBeneficiariCount() {
		return beneficiariCount;
	}

	public void setBeneficiariCount(Long beneficiariCount) {
		this.beneficiariCount = beneficiariCount;
	}

	public UserInfoSec() {
		super();

	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("UserInfoSec [idIride=" + idIride + ", codFisc=" + codFisc + ", cognome=" + cognome);
		sb.append(", nome=" + nome + ", ente=" + ente + ", ruolo=" + ruolo + ", idUtente=" + idUtente);

		sb.append(", tipoBeneficiario=" + tipoBeneficiario + ", codiceRuolo=" + codiceRuolo);
		sb.append(", beneficiarioSelezionato=" + beneficiarioSelezionato);
		sb.append(", idSoggetto=" + idSoggetto + ", idSoggettoIncaricante=" + idSoggettoIncaricante);
		sb.append(", beneficiarioSelezionatoAutomaticamente=" + beneficiarioSelezionatoAutomaticamente);
		sb.append(", codRuolo=" + codRuolo + ", isIncaricato=" + isIncaricato + ", ruoloHelp=" + ruoloHelp);

		if (ruoli != null && !ruoli.isEmpty()) {
			sb.append(", ruoli.size=" + ruoli.size());
		} else {
			sb.append(", ruoli null");
		}

		if (beneficiari != null && !beneficiari.isEmpty()) {
			sb.append(", beneficiari.size=" + beneficiari.size());
		} else {
			sb.append(", beneficiari null");
		}
		sb.append("]");
		return sb.toString();
	}
}
