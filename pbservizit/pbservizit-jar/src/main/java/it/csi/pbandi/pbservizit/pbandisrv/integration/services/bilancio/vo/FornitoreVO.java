/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

import java.util.Date;

public class FornitoreVO {

	//field non corrispondeti su tabella FORNITORI
	private Boolean isPersonaFisica;
	
	////////////////////////////////fields corrispondenti su tabella FORNITORI
	private Double aliquo;  //ALIQUO (5,2)
	private String bloccoPag;  //BLOCCO_PAG (1)
	private String cap;  //CAP (5)
	private String causaleBlocco;  //CAUSALE_BLOCCO (150)
	private String cfe;  //CFE (1)
	private String classben;  //CLASSBEN (3)
	private String cmns;  //CMNS (50)
	private Integer codben;  //CODBEN (6)
	private String codComune;  //COD_COMUNE (6)
	private String codfisc;  //CODFISC (16)
	private String codnatgiu;  //CODNATGIU (2)
	private String comune;  //COMUNE (50)
	private Date dataAgg;  //DATA_AGG
	private String datlavCap;  //DATLAV_CAP (5)
	private String datlavComune;  //DATLAV_COMUNE (50)
	private String datlavProv;  //DATLAV_PROV (2)
	private String datlavRagsoc;  //DATLAV_RAGSOC (50)
	private String datlavVia;  //DATLAV_VIA (50)
	private String divisaUsata;  //DIVISA_USATA (1)
	private Date dtns;  //DTNS
	private String email;  //EMAIL (120)
	private String fax;  //FAX (9)
	private String flAvviso;  //FL_AVVISO (1)
	private String flFd;  //FL_FD (1)
	private String generico;  //GENERICO (1)
	private String note;  //NOTE (150)
	private String oldCodben;  //OLD_CODBEN (6)
	private String oldUtente;  //OLD_UTENTE (16)
	private String partiva;  //PARTIVA (11)
	private String pref;  //PREF (4)
	private String prns;  //PRNS (2)
	private String prov;  //PROV (2)
	private String ragsoc;  //RAGSOC (150)
	private String ssso;  //SSSO (1)
	private String tel1;  //TEL1 (9)
	private String tel2;  //TEL2 (9)
	private String utente;  //UTENTE (8)
	private String via;  //VIA (50)
	
	
	//inizio Accessor con fields non corrispondenti:
	
	public String getSesso() {
		return getSsso();
	}
	public void setSesso(String sesso) {
		setSsso(sesso);
	}
	
	public Date getDataNascita() {
		return getDtns();
	}
	public void setDataNascita(Date dataNascita) {
		setDtns(dataNascita);
	}
	
	public String getComuneNascita() {
		return getCmns();
	}
	public void setComuneNascita(String comuneNascita) {
		setCmns(comuneNascita);
	}
	
	public String getProvNascita() {
		return getPrns();
	}
	public void setProvNascita(String provNascita) {
		setPrns(provNascita);
	}
	
	//fine Accessor con fields non corrispondenti:
	
	public Double getAliquo() {
		return aliquo;
	}
	public void setAliquo(Double aliquo) {
		this.aliquo = aliquo;
	}
	public String getBloccoPag() {
		return bloccoPag;
	}
	public void setBloccoPag(String bloccoPag) {
		this.bloccoPag = bloccoPag;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCausaleBlocco() {
		return causaleBlocco;
	}
	public void setCausaleBlocco(String causaleBlocco) {
		this.causaleBlocco = causaleBlocco;
	}
	public String getCfe() {
		return cfe;
	}
	public void setCfe(String cfe) {
		this.cfe = cfe;
	}
	public String getClassben() {
		return classben;
	}
	public void setClassben(String classben) {
		this.classben = classben;
	}
	private String getCmns() {
		return cmns;
	}
	private void setCmns(String cmns) {
		this.cmns = cmns;
	}
	public Integer getCodben() {
		return codben;
	}
	public void setCodben(Integer codben) {
		this.codben = codben;
	}
	public String getCodComune() {
		return codComune;
	}
	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}
	public String getCodfisc() {
		return codfisc;
	}
	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}
	public String getCodnatgiu() {
		return codnatgiu;
	}
	public void setCodnatgiu(String codnatgiu) {
		this.codnatgiu = codnatgiu;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public Date getDataAgg() {
		return dataAgg;
	}
	public void setDataAgg(Date dataAgg) {
		this.dataAgg = dataAgg;
	}
	public String getDatlavCap() {
		return datlavCap;
	}
	public void setDatlavCap(String datlavCap) {
		this.datlavCap = datlavCap;
	}
	public String getDatlavComune() {
		return datlavComune;
	}
	public void setDatlavComune(String datlavComune) {
		this.datlavComune = datlavComune;
	}
	public String getDatlavProv() {
		return datlavProv;
	}
	public void setDatlavProv(String datlavProv) {
		this.datlavProv = datlavProv;
	}
	public String getDatlavRagsoc() {
		return datlavRagsoc;
	}
	public void setDatlavRagsoc(String datlavRagsoc) {
		this.datlavRagsoc = datlavRagsoc;
	}
	public String getDatlavVia() {
		return datlavVia;
	}
	public void setDatlavVia(String datlavVia) {
		this.datlavVia = datlavVia;
	}
	public String getDivisaUsata() {
		return divisaUsata;
	}
	public void setDivisaUsata(String divisaUsata) {
		this.divisaUsata = divisaUsata;
	}
	private Date getDtns() {
		return dtns;
	}
	private void setDtns(Date dtns) {
		this.dtns = dtns;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getFlAvviso() {
		return flAvviso;
	}
	public void setFlAvviso(String flAvviso) {
		this.flAvviso = flAvviso;
	}
	public String getFlFd() {
		return flFd;
	}
	public void setFlFd(String flFd) {
		this.flFd = flFd;
	}
	public String getGenerico() {
		return generico;
	}
	public void setGenerico(String generico) {
		this.generico = generico;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getOldCodben() {
		return oldCodben;
	}
	public void setOldCodben(String oldCodben) {
		this.oldCodben = oldCodben;
	}
	public String getOldUtente() {
		return oldUtente;
	}
	public void setOldUtente(String oldUtente) {
		this.oldUtente = oldUtente;
	}
	public String getPartiva() {
		return partiva;
	}
	public void setPartiva(String partiva) {
		this.partiva = partiva;
	}
	public String getPref() {
		return pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	private String getPrns() {
		return prns;
	}
	private void setPrns(String prns) {
		this.prns = prns;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getRagsoc() {
		return ragsoc;
	}
	public void setRagsoc(String ragsoc) {
		this.ragsoc = ragsoc;
	}
	private String getSsso() {
		return ssso;
	}
	private void setSsso(String ssso) {
		this.ssso = ssso;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public void setPersonaFisica(boolean isPersonaFisica) {
		this.isPersonaFisica = isPersonaFisica;
	}
	public Boolean isPersonaFisica() {
		return isPersonaFisica;
	}	

}
