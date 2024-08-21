/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.util.Date;

public class FornitoreCreaAttoVO extends GenericVO{
	
	private Long idAttoLiquidazione;
	private String codiceFiscale;
	private String partIva;
	private Integer codBen;
	private String ragSoc;
	private String indirizzo;
	private String cap;
	private String comune;
	private String prov;
	private String mail;
	private String sesso;
	private Date dtNascita;
	private String comuneNascita;
	private String provNascita;
	private Integer idEnteGiuridico;
    private Integer idPersonaFisica;
	
	
	public Long getIdAttoLiquidazione() {
		return idAttoLiquidazione;
	}
	public void setIdAttoLiquidazione(Long idAttoLiquidazione) {
		this.idAttoLiquidazione = idAttoLiquidazione;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getPartIva() {
		return partIva;
	}
	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}
	public Integer getCodBen() {
		return codBen;
	}
	public void setCodBen(Integer codBen) {
		this.codBen = codBen;
	}
	public String getRagSoc() {
		return ragSoc;
	}
	public void setRagSoc(String ragSoc) {
		this.ragSoc = ragSoc;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Date getDtNascita() {
		return dtNascita;
	}
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}
	public String getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public String getProvNascita() {
		return provNascita;
	}
	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}
	public Integer getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	public void setIdEnteGiuridico(Integer idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	public Integer getIdPersonaFisica() {
		return idPersonaFisica;
	}
	public void setIdPersonaFisica(Integer idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	

}
