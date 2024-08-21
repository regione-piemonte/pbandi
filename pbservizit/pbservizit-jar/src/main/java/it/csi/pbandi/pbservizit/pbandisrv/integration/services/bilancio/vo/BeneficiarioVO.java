/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

import java.util.Date;

public class BeneficiarioVO  {

	///////////////////// fields su altre tabelle correlate a beneficiari	
	//Descrizione della modalit di pagamento, campo su db Tabaccare.descri legato a Beneficiari.codaccre
	private String descrCodAccre;		
	//Descrizione codice ABI, campo su db Tabbanche.descri legato a Beneficiari.codbanca
	private String descrAbi;
	//Descrizione del codice CAB, campo su db Tabagenzie.descri lagato a Beneficiari.codagen 
	private String descrCab;
	
	////////////////////////////////fields corrispondenti su tabella BENEFICIARIO
	private String bic;  //BIC (11)
	private String bloccoPag;  //BLOCCO_PAG (1)
	private String cap;  //CAP (5)
	private String cin;  //CIN (1)
	private String codaccre;  //CODACCRE (2)
	private String codagen;  //CODAGEN (5)
	private String codbanca;  //CODBANCA (5)
	//private Integer codben;  //CODBEN (6)
	private Integer codbenCedente;  //CODBEN_CEDENTE (6)
	private Integer codbenCeduto;  //CODBEN_CEDUTO (6)
	private String codcc;  //CODCC (13)
	private String codfiscQuiet;  //CODFISC_QUIET (16)
	private String codpag;  //CODPAG (2)
	private String codupos;  //CODUPOS (3)
	private String comune;  //COMUNE (50)
	private Date dataAgg;  //DATA_AGG
	private String email;  //EMAIL (120)
	private String flCommErog;  //FL_COMM_EROG (1)
	private String iban;  //IBAN (34)
	private String intestcc;  //INTESTCC (80)
	private String piazza;  //PIAZZA (50)
	//private Integer progben;  //PROGBEN (3)
	private Integer progbenCeduto;  //PROGBEN_CEDUTO (3)
	private String prov;  //PROV (2)
	private String quietanz;  //QUIETANZ (80)
	private String ragsocAgg;  //RAGSOC_AGG (150)
	private String utente;  //UTENTE (8)
	private String via;  //VIA (50)
	
	private Integer codBen;  //NUMBER(6)
	private Integer progBen; //NUMBER(3)

	//inizio Accessor con fields non corrispondenti:
	
	public String getAbi(){
		return getCodbanca();
	}
	
	public void setAbi(String abi){
		setCodbanca(abi);
	}

	public String getCab(){
		return getCodagen();
	}
	
	public void setCab(String cab){
		setCodagen(cab);
	}

	public String getNroCC(){
		return getCodcc();
	}
	
	public void setNroCC(String nroCC){
		setCodcc(nroCC);
	}

	public String getMailModPag(){
		return getEmail();
	}
	
	public void setMailModPag(String email){
		setEmail(email);
	}
	public String getQuiet(){
		return getQuietanz();
	}
	
	public void setQuiet(String quiet){
		setQuietanz(quiet);
	}

	public String getFlagTutteErog() {
		return getFlCommErog();
	}

	public void setFlagTutteErog(String flagTutteErog) {
		setFlCommErog(flagTutteErog);
	}
	
	public String getRagSocSede() {
		return getRagsocAgg();
	}

	public void setRagSocSede(String ragSocSede) {
		setRagsocAgg(ragSocSede);
	}

	public String getViaSede() {
		return getVia();
	}

	public void setViaSede(String viaSede) {
		setVia(viaSede);
	}

	public String getCapSede() {
		return getCap();
	}

	public void setCapSede(String capSede) {
		setCap(capSede);
	}

	public String getComuneSede() {
		return getComune();
	}

	public void setComuneSede(String comuneSede) {
		setComune(comuneSede);
	}

	public String getProvSede() {
		return getProv();
	}

	public void setProvSede(String provSede) {
		setProv(provSede);
	}

	//fine Accessor con fields non corrispondenti
	
	/////////////////// inizio fields su altre tabelle correlate a beneficiari

	public String getDescrCodAccre() {
		return descrCodAccre;
	}

	public void setDescrCodAccre(String descrCodAccre) {
		this.descrCodAccre = descrCodAccre;
	}

	public String getDescrAbi() {
		return descrAbi;
	}

	public void setDescrAbi(String descrAbi) {
		this.descrAbi = descrAbi;
	}

	public String getDescrCab() {
		return descrCab;
	}

	public void setDescrCab(String descrCab) {
		this.descrCab = descrCab;
	}
	
	///////////////// fine fields su altre tabelle correlate a beneficiari	
	

	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
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
	private void setCap(String cap) {
		this.cap = cap;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getCodaccre() {
		return codaccre;
	}
	public void setCodaccre(String codaccre) {
		this.codaccre = codaccre;
	}
	private String getCodagen() {
		return codagen;
	}
	private void setCodagen(String codagen) {
		this.codagen = codagen;
	}
	private String getCodbanca() {
		return codbanca;
	}
	private void setCodbanca(String codbanca) {
		this.codbanca = codbanca;
	}

	public Integer getCodbenCedente() {
		return codbenCedente;
	}
	public void setCodbenCedente(Integer codbenCedente) {
		this.codbenCedente = codbenCedente;
	}
	public Integer getCodbenCeduto() {
		return codbenCeduto;
	}
	public void setCodbenCeduto(Integer codbenCeduto) {
		this.codbenCeduto = codbenCeduto;
	}
	private String getCodcc() {
		return codcc;
	}
	private void setCodcc(String codcc) {
		this.codcc = codcc;
	}
	public String getCodfiscQuiet() {
		return codfiscQuiet;
	}
	public void setCodfiscQuiet(String codfiscQuiet) {
		this.codfiscQuiet = codfiscQuiet;
	}
	public String getCodpag() {
		return codpag;
	}
	public void setCodpag(String codpag) {
		this.codpag = codpag;
	}
	public String getCodupos() {
		return codupos;
	}
	public void setCodupos(String codupos) {
		this.codupos = codupos;
	}
	private String getComune() {
		return comune;
	}
	private void setComune(String comune) {
		this.comune = comune;
	}
	public Date getDataAgg() {
		return dataAgg;
	}
	public void setDataAgg(Date dataAgg) {
		this.dataAgg = dataAgg;
	}
	private String getEmail() {
		return email;
	}
	private void setEmail(String email) {
		this.email = email;
	}
	private String getFlCommErog() {
		return flCommErog;
	}
	private void setFlCommErog(String flCommErog) {
		this.flCommErog = flCommErog;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getIntestcc() {
		return intestcc;
	}
	public void setIntestcc(String intestcc) {
		this.intestcc = intestcc;
	}
	public String getPiazza() {
		return piazza;
	}
	public void setPiazza(String piazza) {
		this.piazza = piazza;
	}
	/*public Integer getProgben() {
		return progben;
	}
	public void setProgben(Integer progben) {
		this.progben = progben;
	}*/
	public Integer getProgbenCeduto() {
		return progbenCeduto;
	}
	public void setProgbenCeduto(Integer progbenCeduto) {
		this.progbenCeduto = progbenCeduto;
	}
	private String getProv() {
		return prov;
	}
	private void setProv(String prov) {
		this.prov = prov;
	}
	private String getQuietanz() {
		return quietanz;
	}
	private void setQuietanz(String quietanz) {
		this.quietanz = quietanz;
	}
	private String getRagsocAgg() {
		return ragsocAgg;
	}
	private void setRagsocAgg(String ragsocAgg) {
		this.ragsocAgg = ragsocAgg;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	private String getVia() {
		return via;
	}
	private void setVia(String via) {
		this.via = via;
	}

	public Integer getCodBen() {
		return codBen;
	}
	public void setCodBen(Integer codBen) {
		this.codBen = codBen;
	}
	public Integer getProgBen() {
		return progBen;
	}
	public void setProgBen(Integer progBen) {
		this.progBen = progBen;
	}

}
