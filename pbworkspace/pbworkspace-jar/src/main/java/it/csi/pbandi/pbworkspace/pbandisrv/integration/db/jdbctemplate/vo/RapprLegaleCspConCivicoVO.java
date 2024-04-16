/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.pbandisrv.integration.db.jdbctemplate.vo;

import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;

public class RapprLegaleCspConCivicoVO extends GenericVO {
	
	private String civico;
	private String codiceFiscaleSoggetto;
	private String cognome;
	private String nome;
	private String sesso;
	private Date dtNascita;
	private String descIndirizzo;
	private String cap;
	private String email;
	private String telefono;
	private String fax;
	private Long idNazioneRes;
	private String descNazioneRes;
	private Long idRegioneRes;
	private String descRegioneRes;
	private Long idProvinciaRes;
	private String descProvinciaRes;
	private Long idComuneRes;
	private String descComuneRes;
	private Long idComuneEsteroRes;
	private String descComuneEsteroRes;
	private Long idNazioneNascita;
	private String descNazioneNascita;
	private Long idRegioneNascita;
	private String descRegioneNascita;
	private Long idProvinciaNascita;
	private String descProvinciaNascita;
	private Long idComuneNascita;
	private String descComuneNascita;
	private Long idComuneEsteroNascita;
	private String descComuneEsteroNascita;
	
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public Long getIdComuneEsteroRes() {
		return idComuneEsteroRes;
	}
	public void setIdComuneEsteroRes(Long idComuneEsteroRes) {
		this.idComuneEsteroRes = idComuneEsteroRes;
	}
	public String getDescComuneEsteroRes() {
		return descComuneEsteroRes;
	}
	public void setDescComuneEsteroRes(String descComuneEsteroRes) {
		this.descComuneEsteroRes = descComuneEsteroRes;
	}
	public Long getIdComuneEsteroNascita() {
		return idComuneEsteroNascita;
	}
	public void setIdComuneEsteroNascita(Long idComuneEsteroNascita) {
		this.idComuneEsteroNascita = idComuneEsteroNascita;
	}
	public String getDescComuneEsteroNascita() {
		return descComuneEsteroNascita;
	}
	public void setDescComuneEsteroNascita(String descComuneEsteroNascita) {
		this.descComuneEsteroNascita = descComuneEsteroNascita;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Long getIdRegioneRes() {
		return idRegioneRes;
	}
	public void setIdRegioneRes(Long idRegioneRes) {
		this.idRegioneRes = idRegioneRes;
	}
	public String getDescRegioneRes() {
		return descRegioneRes;
	}
	public void setDescRegioneRes(String descRegioneRes) {
		this.descRegioneRes = descRegioneRes;
	}
	public Long getIdProvinciaRes() {
		return idProvinciaRes;
	}
	public void setIdProvinciaRes(Long idProvinciaRes) {
		this.idProvinciaRes = idProvinciaRes;
	}

	public Long getIdComuneRes() {
		return idComuneRes;
	}
	public void setIdComuneRes(Long idComuneRes) {
		this.idComuneRes = idComuneRes;
	}
	public String getDescComuneRes() {
		return descComuneRes;
	}
	public void setDescComuneRes(String descComuneRes) {
		this.descComuneRes = descComuneRes;
	}
	public Long getIdNazioneNascita() {
		return idNazioneNascita;
	}
	public void setIdNazioneNascita(Long idNazioneNascita) {
		this.idNazioneNascita = idNazioneNascita;
	}
	public String getDescNazioneNascita() {
		return descNazioneNascita;
	}
	public void setDescNazioneNascita(String descNazioneNascita) {
		this.descNazioneNascita = descNazioneNascita;
	}
	public Long getIdRegioneNascita() {
		return idRegioneNascita;
	}
	public void setIdRegioneNascita(Long idRegioneNascita) {
		this.idRegioneNascita = idRegioneNascita;
	}
	public String getDescRegioneNascita() {
		return descRegioneNascita;
	}
	public void setDescRegioneNascita(String descRegioneNascita) {
		this.descRegioneNascita = descRegioneNascita;
	}
	public Long getIdProvinciaNascita() {
		return idProvinciaNascita;
	}
	public void setIdProvinciaNascita(Long idProvinciaNascita) {
		this.idProvinciaNascita = idProvinciaNascita;
	}

	public String getDescProvinciaRes() {
		return descProvinciaRes;
	}
	public void setDescProvinciaRes(String descProvinciaRes) {
		this.descProvinciaRes = descProvinciaRes;
	}
	public String getDescProvinciaNascita() {
		return descProvinciaNascita;
	}
	public void setDescProvinciaNascita(String descProvinciaNascita) {
		this.descProvinciaNascita = descProvinciaNascita;
	}
	public Long getIdComuneNascita() {
		return idComuneNascita;
	}
	public void setIdComuneNascita(Long idComuneNascita) {
		this.idComuneNascita = idComuneNascita;
	}
	public String getDescComuneNascita() {
		return descComuneNascita;
	}
	public void setDescComuneNascita(String descComuneNascita) {
		this.descComuneNascita = descComuneNascita;
	}
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
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
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public Long getIdNazioneRes() {
		return idNazioneRes;
	}
	public void setIdNazioneRes(Long idNazioneRes) {
		this.idNazioneRes = idNazioneRes;
	}
	public String getDescNazioneRes() {
		return descNazioneRes;
	}
	public void setDescNazioneRes(String descNazioneRes) {
		this.descNazioneRes = descNazioneRes;
	}
	
	public String toString() {
		String s = this.getCodiceFiscaleSoggetto()+" - "+this.getCognome()+" - "+this.getNome()+" - "+this.getSesso()+" - "+this.getDtNascita()+" - "+this.getDescNazioneRes()+" - "+this.getEmail()+" - "+this.getTelefono()+" - "+this.getFax();
		return s;
	}
	
	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

}
