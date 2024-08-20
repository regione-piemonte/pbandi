/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.sql.Date;

public class BeneficiarioDomandaVO {
	
	//DATI RAPPRESENTANTE LEGALE
	private String cognome;
	private String nome;
	private String tipoSoggetto;
	private String codiceFiscale;
	private Date dataNascita;
	private String comuneNascita;
	private String provinciaNascita;
	private String regioneNascita;
	private String nazioneNascita;
	private String indirizzoResidenza;
	private String comuneResidenza;
	private Long cap;
	private String provinciaResidenza;
	private String regioneResidenza;
	private String nazioneResidenza;
	
	
	//DATI SEDE AMMINISTRATIVA
	private String indirizzoSedeAmm;
	private String pIvaAmm;
	private String comuneSedeAmm;
	private String provinciaSedeAmm;
	private Long capSedeAmm;
	private String regioneSedeAmm;
	private String nazioneSedeAmm;
	private String codiceAtecoAmm;
	private String descAtecoAmm;
	
	//RECAPITI
	private Long telefono;
	private Long fax;
	private String email;
	
	
	//CONTO CORRENTE
	private Long numeroConto;
	private String iban;
	
	
	//BANCA APPOGGIO
	private String banca;
	//private Long abi;
	//private Long cab;
	
	
	//DELEGATI
	private String cognomeDeleg;
	private String nomeDeleg;
	private String ruoloSoggettoDeleg;
	private String tipoSoggettoDeleg;
	private String cfDeleg;
	private Date nascitaDeleg;
	private String comuneNascitaDeleg;
	private String provinciaNascitaDeleg;
	private String nazioneNascitaDeleg;
	private String indirizzoResidDeleg;
	private String comuneResidDeleg;
	private Long capResidDeleg;
	private String provinciaResidenzaDeleg;
	private String regioneResidenzaDeleg;
	private String nazioneResidenzaDeleg;
	
	
	//CONSULENTI
	private String denominazioneCons;
	private String ruoloSoggCons;
	private Long tipoSoggettoCons;
	private String cfCons;
	private String indirizzoSedeLeg;
	private String pIvaCons;
	private String comuneSedeLeg;
	private String provinciaSedeLeg;
	private Long capSedeLeg;
	private String nazioneSedeLeg;
	private String codiceAtecoLeg;
	private String descAtecoLeg;
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
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public String getProvinciaNascita() {
		return provinciaNascita;
	}
	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}
	public String getRegioneNascita() {
		return regioneNascita;
	}
	public void setRegioneNascita(String regioneNascita) {
		this.regioneNascita = regioneNascita;
	}
	public String getNazioneNascita() {
		return nazioneNascita;
	}
	public void setNazioneNascita(String nazioneNascita) {
		this.nazioneNascita = nazioneNascita;
	}
	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}
	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}
	public String getComuneResidenza() {
		return comuneResidenza;
	}
	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public Long getCap() {
		return cap;
	}
	public void setCap(Long cap) {
		this.cap = cap;
	}
	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}
	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}
	public String getRegioneResidenza() {
		return regioneResidenza;
	}
	public void setRegioneResidenza(String regioneResidenza) {
		this.regioneResidenza = regioneResidenza;
	}
	public String getNazioneResidenza() {
		return nazioneResidenza;
	}
	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}
	public String getIndirizzoSedeAmm() {
		return indirizzoSedeAmm;
	}
	public void setIndirizzoSedeAmm(String indirizzoSedeAmm) {
		this.indirizzoSedeAmm = indirizzoSedeAmm;
	}
	public String getpIvaAmm() {
		return pIvaAmm;
	}
	public void setpIvaAmm(String pIvaAmm) {
		this.pIvaAmm = pIvaAmm;
	}
	public String getComuneSedeAmm() {
		return comuneSedeAmm;
	}
	public void setComuneSedeAmm(String comuneSedeAmm) {
		this.comuneSedeAmm = comuneSedeAmm;
	}
	public String getProvinciaSedeAmm() {
		return provinciaSedeAmm;
	}
	public void setProvinciaSedeAmm(String provinciaSedeAmm) {
		this.provinciaSedeAmm = provinciaSedeAmm;
	}
	public Long getCapSedeAmm() {
		return capSedeAmm;
	}
	public void setCapSedeAmm(Long capSedeAmm) {
		this.capSedeAmm = capSedeAmm;
	}
	public String getRegioneSedeAmm() {
		return regioneSedeAmm;
	}
	public void setRegioneSedeAmm(String regioneSedeAmm) {
		this.regioneSedeAmm = regioneSedeAmm;
	}
	public String getNazioneSedeAmm() {
		return nazioneSedeAmm;
	}
	public void setNazioneSedeAmm(String nazioneSedeAmm) {
		this.nazioneSedeAmm = nazioneSedeAmm;
	}
	public String getCodiceAtecoAmm() {
		return codiceAtecoAmm;
	}
	public void setCodiceAtecoAmm(String codiceAtecoAmm) {
		this.codiceAtecoAmm = codiceAtecoAmm;
	}
	public String getDescAtecoAmm() {
		return descAtecoAmm;
	}
	public void setDescAtecoAmm(String descAtecoAmm) {
		this.descAtecoAmm = descAtecoAmm;
	}
	public Long getTelefono() {
		return telefono;
	}
	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}
	public Long getFax() {
		return fax;
	}
	public void setFax(Long fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getNumeroConto() {
		return numeroConto;
	}
	public void setNumeroConto(Long numeroConto) {
		this.numeroConto = numeroConto;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBanca() {
		return banca;
	}
	public void setBanca(String banca) {
		this.banca = banca;
	}
//	public Long getAbi() {
//		return abi;
//	}
//	public void setAbi(Long abi) {
//		this.abi = abi;
//	}
//	public Long getCab() {
//		return cab;
//	}
//	public void setCab(Long cab) {
//		this.cab = cab;
//	}
	public String getCognomeDeleg() {
		return cognomeDeleg;
	}
	public void setCognomeDeleg(String cognomeDeleg) {
		this.cognomeDeleg = cognomeDeleg;
	}
	public String getNomeDeleg() {
		return nomeDeleg;
	}
	public void setNomeDeleg(String nomeDeleg) {
		this.nomeDeleg = nomeDeleg;
	}
	public String getRuoloSoggettoDeleg() {
		return ruoloSoggettoDeleg;
	}
	public void setRuoloSoggettoDeleg(String ruoloSoggettoDeleg) {
		this.ruoloSoggettoDeleg = ruoloSoggettoDeleg;
	}
	public String getTipoSoggettoDeleg() {
		return tipoSoggettoDeleg;
	}
	public void setTipoSoggettoDeleg(String tipoSoggettoDeleg) {
		this.tipoSoggettoDeleg = tipoSoggettoDeleg;
	}
	public String getCfDeleg() {
		return cfDeleg;
	}
	public void setCfDeleg(String cfDeleg) {
		this.cfDeleg = cfDeleg;
	}
	public Date getNascitaDeleg() {
		return nascitaDeleg;
	}
	public void setNascitaDeleg(Date nascitaDeleg) {
		this.nascitaDeleg = nascitaDeleg;
	}
	public String getComuneNascitaDeleg() {
		return comuneNascitaDeleg;
	}
	public void setComuneNascitaDeleg(String comuneNascitaDeleg) {
		this.comuneNascitaDeleg = comuneNascitaDeleg;
	}
	public String getProvinciaNascitaDeleg() {
		return provinciaNascitaDeleg;
	}
	public void setProvinciaNascitaDeleg(String provinciaNascitaDeleg) {
		this.provinciaNascitaDeleg = provinciaNascitaDeleg;
	}
	public String getNazioneNascitaDeleg() {
		return nazioneNascitaDeleg;
	}
	public void setNazioneNascitaDeleg(String nazioneNascitaDeleg) {
		this.nazioneNascitaDeleg = nazioneNascitaDeleg;
	}
	public String getIndirizzoResidDeleg() {
		return indirizzoResidDeleg;
	}
	public void setIndirizzoResidDeleg(String indirizzoResidDeleg) {
		this.indirizzoResidDeleg = indirizzoResidDeleg;
	}
	public String getComuneResidDeleg() {
		return comuneResidDeleg;
	}
	public void setComuneResidDeleg(String comuneResidDeleg) {
		this.comuneResidDeleg = comuneResidDeleg;
	}
	public Long getCapResidDeleg() {
		return capResidDeleg;
	}
	public void setCapResidDeleg(Long capResidDeleg) {
		this.capResidDeleg = capResidDeleg;
	}
	public String getProvinciaResidenzaDeleg() {
		return provinciaResidenzaDeleg;
	}
	public void setProvinciaResidenzaDeleg(String provinciaResidenzaDeleg) {
		this.provinciaResidenzaDeleg = provinciaResidenzaDeleg;
	}
	public String getRegioneResidenzaDeleg() {
		return regioneResidenzaDeleg;
	}
	public void setRegioneResidenzaDeleg(String regioneResidenzaDeleg) {
		this.regioneResidenzaDeleg = regioneResidenzaDeleg;
	}
	public String getNazioneResidenzaDeleg() {
		return nazioneResidenzaDeleg;
	}
	public void setNazioneResidenzaDeleg(String nazioneResidenzaDeleg) {
		this.nazioneResidenzaDeleg = nazioneResidenzaDeleg;
	}
	public String getDenominazioneCons() {
		return denominazioneCons;
	}
	public void setDenominazioneCons(String denominazioneCons) {
		this.denominazioneCons = denominazioneCons;
	}
	public String getRuoloSoggCons() {
		return ruoloSoggCons;
	}
	public void setRuoloSoggCons(String ruoloSoggCons) {
		this.ruoloSoggCons = ruoloSoggCons;
	}
	public Long getTipoSoggettoCons() {
		return tipoSoggettoCons;
	}
	public void setTipoSoggettoCons(Long tipoSoggettoCons) {
		this.tipoSoggettoCons = tipoSoggettoCons;
	}
	public String getCfCons() {
		return cfCons;
	}
	public void setCfCons(String cfCons) {
		this.cfCons = cfCons;
	}
	public String getIndirizzoSedeLeg() {
		return indirizzoSedeLeg;
	}
	public void setIndirizzoSedeLeg(String indirizzoSedeLeg) {
		this.indirizzoSedeLeg = indirizzoSedeLeg;
	}
	public String getpIvaCons() {
		return pIvaCons;
	}
	public void setpIvaCons(String pIvaCons) {
		this.pIvaCons = pIvaCons;
	}
	public String getComuneSedeLeg() {
		return comuneSedeLeg;
	}
	public void setComuneSedeLeg(String comuneSedeLeg) {
		this.comuneSedeLeg = comuneSedeLeg;
	}
	public String getProvinciaSedeLeg() {
		return provinciaSedeLeg;
	}
	public void setProvinciaSedeLeg(String provinciaSedeLeg) {
		this.provinciaSedeLeg = provinciaSedeLeg;
	}
	public Long getCapSedeLeg() {
		return capSedeLeg;
	}
	public void setCapSedeLeg(Long capSedeLeg) {
		this.capSedeLeg = capSedeLeg;
	}
	public String getNazioneSedeLeg() {
		return nazioneSedeLeg;
	}
	public void setNazioneSedeLeg(String nazioneSedeLeg) {
		this.nazioneSedeLeg = nazioneSedeLeg;
	}
	public String getCodiceAtecoLeg() {
		return codiceAtecoLeg;
	}
	public void setCodiceAtecoLeg(String codiceAtecoLeg) {
		this.codiceAtecoLeg = codiceAtecoLeg;
	}
	public String getDescAtecoLeg() {
		return descAtecoLeg;
	}
	public void setDescAtecoLeg(String descAtecoLeg) {
		this.descAtecoLeg = descAtecoLeg;
	}
	@Override
	public String toString() {
		return "BeneficiarioDomandaVO [cognome=" + cognome + ", nome=" + nome + ", tipoSoggetto=" + tipoSoggetto
				+ ", codiceFiscale=" + codiceFiscale + ", dataNascita=" + dataNascita + ", comuneNascita="
				+ comuneNascita + ", provinciaNascita=" + provinciaNascita + ", regioneNascita=" + regioneNascita
				+ ", nazioneNascita=" + nazioneNascita + ", indirizzoResidenza=" + indirizzoResidenza
				+ ", comuneResidenza=" + comuneResidenza + ", cap=" + cap + ", provinciaResidenza=" + provinciaResidenza
				+ ", regioneResidenza=" + regioneResidenza + ", nazioneResidenza=" + nazioneResidenza
				+ ", indirizzoSedeAmm=" + indirizzoSedeAmm + ", pIvaAmm=" + pIvaAmm + ", comuneSedeAmm=" + comuneSedeAmm
				+ ", provinciaSedeAmm=" + provinciaSedeAmm + ", capSedeAmm=" + capSedeAmm + ", regioneSedeAmm="
				+ regioneSedeAmm + ", nazioneSedeAmm=" + nazioneSedeAmm + ", codiceAtecoAmm=" + codiceAtecoAmm
				+ ", descAtecoAmm=" + descAtecoAmm + ", telefono=" + telefono + ", fax=" + fax + ", email=" + email
				+ ", numeroConto=" + numeroConto + ", iban=" + iban + ", banca=" + banca + ", cognomeDeleg="
				+ cognomeDeleg + ", nomeDeleg=" + nomeDeleg + ", ruoloSoggettoDeleg=" + ruoloSoggettoDeleg
				+ ", tipoSoggettoDeleg=" + tipoSoggettoDeleg + ", cfDeleg=" + cfDeleg + ", nascitaDeleg=" + nascitaDeleg
				+ ", comuneNascitaDeleg=" + comuneNascitaDeleg + ", provinciaNascitaDeleg=" + provinciaNascitaDeleg
				+ ", nazioneNascitaDeleg=" + nazioneNascitaDeleg + ", indirizzoResidDeleg=" + indirizzoResidDeleg
				+ ", comuneResidDeleg=" + comuneResidDeleg + ", capResidDeleg=" + capResidDeleg
				+ ", provinciaResidenzaDeleg=" + provinciaResidenzaDeleg + ", regioneResidenzaDeleg="
				+ regioneResidenzaDeleg + ", nazioneResidenzaDeleg=" + nazioneResidenzaDeleg + ", denominazioneCons="
				+ denominazioneCons + ", ruoloSoggCons=" + ruoloSoggCons + ", tipoSoggettoCons=" + tipoSoggettoCons
				+ ", cfCons=" + cfCons + ", indirizzoSedeLeg=" + indirizzoSedeLeg + ", pIvaCons=" + pIvaCons
				+ ", comuneSedeLeg=" + comuneSedeLeg + ", provinciaSedeLeg=" + provinciaSedeLeg + ", capSedeLeg="
				+ capSedeLeg + ", nazioneSedeLeg=" + nazioneSedeLeg + ", codiceAtecoLeg=" + codiceAtecoLeg
				+ ", descAtecoLeg=" + descAtecoLeg + "]";
	}
	
	
	

}
