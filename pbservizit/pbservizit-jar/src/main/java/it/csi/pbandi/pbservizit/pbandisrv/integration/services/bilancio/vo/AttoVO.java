/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;

import java.util.Date;

public class AttoVO {



	///////////////// fields su altre tabelle correlate e di comodo per calcoli e altri dati
	private Double importoAtto;
	private Integer stato;
	private Date dataAnnullAtto;
	private String annoEsercizioAtto;
	
    //////////////////inizio fields corrispondenti su tabella ATTI_LIQUID
	private String annoprov;  //ANNOPROV (4) 
	private String nprov;  //NPROV (5) 
	private String direzione;  //DIREZIONE (4)
	private String settore;  //SETTORE (2)
	private String altroDaSpec;  //ALTRO_DA_SPEC (100)
	private String annoTitolario;  //ANNO_TITOLARIO (4)
	private String causalePagam;  //CAUSALE_PAGAM (150)
	private String chiaveUtente;  //CHIAVE_UTENTE (20)
	private String codTitolario;  //COD_TITOLARIO (15)
	private Date dataagg;  //DATAAGG
	private Date dataComplet;  //DATA_COMPLET
	private Date datareg;  //DATAREG
	private Date dataRicezione;  //DATA_RICEZIONE
	private Date dataRichModif;  //DATA_RICH_MODIF
	private Date dataRifiuto;  //DATA_RIFIUTO
	private Date datascad;  //DATASCAD
	private Date datavaluta;  //DATAVALUTA
	private String descAttivita;  //DESC_ATTIVITA (70)
	private String direttDirigResp;  //DIRETT_DIRIG_RESP (50)
	private String distinta;  //DISTINTA (3)
	private String divisaUsata;  //DIVISA_USATA (1)
	private String fatture;  //FATTURE (120)
	private String flAltro;  //FL_ALTRO (1)
	private String flDichiaraz;  //FL_DICHIARAZ (1)
	private String flDocGiustif;  //FL_DOC_GIUSTIF (1)
	private String flEstrCopiaProv;  //FL_ESTR_COPIA_PROV (1)
	private String flFatture;  //FL_FATTURE (1)
	private String funzLiq;  //FUNZ_LIQ (50)
	private String motivoRichModif;  //MOTIVO_RICH_MODIF (150)
	private Integer ncarta;  //NCARTA (6)
	private Integer nelenco;  //NELENCO (3)
	private String notaRifiuto;  //NOTA_RIFIUTO (70)
	private String note;  //NOTE (70)
	private String numPratica;  //NUM_PRATICA (20)
	private String numTel;  //NUM_TEL (10)
	private String tipoPagamAutom;  //TIPO_PAGAM_AUTOM (3)
	private String utente;  //UTENTE (8)
    //////////////////fine fields corrispondenti su tabella ATTI_LIQUID
	
	//////////////////fields corrispondenti su tabella ATTI_LIQUID relativi alla ritenuta atto 
	//mappati su oggetto RitenutaAtto
	private RitenutaAttoVO ritenuta;
	
	
	//inizio Accessor con fields non corrispondenti di tabella ATTI_LIQUID:
	public String getAnnoAtto() {
		return getAnnoprov();
	}
	public void setAnnoAtto(String annoAtto) {
		this.setAnnoprov(annoAtto);
	}
	public String getNroAtto() {
		return getNprov();
	}
	public void setNroAtto(String nroAtto) {
		this.setNprov(nroAtto);
	}
	public String getDirAtto() {
		return getDirezione();
	}
	public void setDirAtto(String dirAtto) {
		this.setDirezione(dirAtto);
	}
	public String getSettoreAtto() {
		return getSettore();
	}
	public void setSettoreAtto(String settoreAtto) {
		this.setSettore(settoreAtto);
	}
	public String getDescri() {
		return getCausalePagam();
	}
	public void setDescri(String descri) {
		this.setCausalePagam(descri);
	}
	public String getAllAltro() {
		return getAltroDaSpec();
	}
	public void setAllAltro(String allAltro) {
		this.setAltroDaSpec(allAltro);
	}
	public Date getDataEmisAtto() {
		return getDatareg();
	}
	public void setDataEmisAtto(Date dataEmisAtto) {
		this.setDatareg(dataEmisAtto);
	}
	public Date getDataAggAtto() {
		return getDataagg();
	}
	public void setDataAggAtto(Date dataAggAtto) {
		this.setDataagg(dataAggAtto);
	}
	public Date getDataScadenza() {
		return getDatascad();
	}
	public void setDataScadenza(Date dataScadenza) {
		this.setDatascad(dataScadenza);
	}
	public Date getDataComplAtto() {
		return getDataComplet();
	}
	public void setDataComplAtto(Date dataComplAtto) {
		this.setDataComplet(dataComplAtto);
	}	
	public Date getDataRicAtto() {
		return getDataRicezione();
	}
	public void setDataRicAtto(Date dataRicAtto) {
		this.setDataRicezione(dataRicAtto);
	}
	public Date getDataRichMod() {
		return getDataRichModif();
	}
	public void setDataRichMod(Date dataRichMod) {
		this.setDataRichModif(dataRichMod);
	}
	public String getTestoRichMod() {
		return getMotivoRichModif();
	}
	public void setTestoRichMod(String testoRichMod) {
		this.setMotivoRichModif(testoRichMod);
	}
	public String getNroTelLiq() {
		return getNumTel();
	}
	public void setNroTelLiq(String nroTelLiq) {
		this.setNumTel(nroTelLiq);
	}
	public String getNomeLiq() {
		return getFunzLiq();
	}
	public void setNomeLiq(String nomeLiq) {
		this.setFunzLiq(nomeLiq);
	}
	public String getNomeDir() {
		return getDirettDirigResp();
	}
	public void setNomeDir(String nomeDir) {
		this.setDirettDirigResp(nomeDir);
	}
	public Integer getNroElencoAtto() {
		return getNelenco();
	}
	public void setNroElencoAtto(Integer nroElencoAtto) {
		this.setNelenco(nroElencoAtto);
	}	
	//fine Accessor con fields non corrispondenti di tabella ATTI_LIQUID:
	
	
	//Accessor fields di comodo
	public Double getImportoAtto() {
		return importoAtto;
	}

	public void setImportoAtto(Double importoAtto) {
		this.importoAtto = importoAtto;
	}

	public Integer getStato() {
		return stato;
	}

	public void setStato(Integer stato) {
		this.stato = stato;
	}
	public Date getDataAnnullAtto() {
		return dataAnnullAtto;
	}
	public void setDataAnnullAtto(Date dataAnnullAtto) {
		this.dataAnnullAtto = dataAnnullAtto;
	}
	public String getAnnoEsercizioAtto() {
		return annoEsercizioAtto;
	}
	public void setAnnoEsercizioAtto(String annoEsercizioAtto) {
		this.annoEsercizioAtto = annoEsercizioAtto;
	}
	//Accessor fields corrispondenti tabella ATTI_LIQUID
	private String getAnnoprov() {
		return annoprov;
	}

	private void setAnnoprov(String annoprov) {
		this.annoprov = annoprov;
	}

	private String getNprov() {
		return nprov;
	}

	private void setNprov(String nprov) {
		this.nprov = nprov;
	}

	private String getDirezione() {
		return direzione;
	}

	private void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	private String getSettore() {
		return settore;
	}

	private void setSettore(String settore) {
		this.settore = settore;
	}

	public String getAltroDaSpec() {
		return altroDaSpec;
	}

	private void setAltroDaSpec(String altroDaSpec) {
		this.altroDaSpec = altroDaSpec;
	}

	public String getAnnoTitolario() {
		return annoTitolario;
	}

	public void setAnnoTitolario(String annoTitolario) {
		this.annoTitolario = annoTitolario;
	}

	public String getCausalePagam() {
		return causalePagam;
	}

	private void setCausalePagam(String causalePagam) {
		this.causalePagam = causalePagam;
	}

	public String getChiaveUtente() {
		return chiaveUtente;
	}

	public void setChiaveUtente(String chiaveUtente) {
		this.chiaveUtente = chiaveUtente;
	}

	public String getCodTitolario() {
		return codTitolario;
	}

	public void setCodTitolario(String codTitolario) {
		this.codTitolario = codTitolario;
	}

	private Date getDataagg() {
		return dataagg;
	}

	private void setDataagg(Date dataagg) {
		this.dataagg = dataagg;
	}

	private Date getDataComplet() {
		return dataComplet;
	}

	private void setDataComplet(Date dataComplet) {
		this.dataComplet = dataComplet;
	}

	private Date getDatareg() {
		return datareg;
	}

	private void setDatareg(Date datareg) {
		this.datareg = datareg;
	}

	private Date getDataRicezione() {
		return dataRicezione;
	}

	private void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	private Date getDataRichModif() {
		return dataRichModif;
	}

	private void setDataRichModif(Date dataRichModif) {
		this.dataRichModif = dataRichModif;
	}

	public Date getDataRifiuto() {
		return dataRifiuto;
	}

	public void setDataRifiuto(Date dataRifiuto) {
		this.dataRifiuto = dataRifiuto;
	}

	private Date getDatascad() {
		return datascad;
	}

	private void setDatascad(Date datascad) {
		this.datascad = datascad;
	}

	public Date getDatavaluta() {
		return datavaluta;
	}

	public void setDatavaluta(Date datavaluta) {
		this.datavaluta = datavaluta;
	}

	public String getDescAttivita() {
		return descAttivita;
	}

	public void setDescAttivita(String descAttivita) {
		this.descAttivita = descAttivita;
	}

	public String getDirettDirigResp() {
		return direttDirigResp;
	}

	private void setDirettDirigResp(String direttDirigResp) {
		this.direttDirigResp = direttDirigResp;
	}

	public String getDistinta() {
		return distinta;
	}

	public void setDistinta(String distinta) {
		this.distinta = distinta;
	}

	public String getDivisaUsata() {
		return divisaUsata;
	}

	public void setDivisaUsata(String divisaUsata) {
		this.divisaUsata = divisaUsata;
	}

	public String getFatture() {
		return fatture;
	}

	public void setFatture(String fatture) {
		this.fatture = fatture;
	}

	public String getFlAltro() {
		return flAltro;
	}

	public void setFlAltro(String flAltro) {
		this.flAltro = flAltro;
	}

	public String getFlDichiaraz() {
		return flDichiaraz;
	}

	public void setFlDichiaraz(String flDichiaraz) {
		this.flDichiaraz = flDichiaraz;
	}

	public String getFlDocGiustif() {
		return flDocGiustif;
	}

	public void setFlDocGiustif(String flDocGiustif) {
		this.flDocGiustif = flDocGiustif;
	}

	public String getFlEstrCopiaProv() {
		return flEstrCopiaProv;
	}

	public void setFlEstrCopiaProv(String flEstrCopiaProv) {
		this.flEstrCopiaProv = flEstrCopiaProv;
	}

	public String getFlFatture() {
		return flFatture;
	}

	public void setFlFatture(String flFatture) {
		this.flFatture = flFatture;
	}

	public String getFunzLiq() {
		return funzLiq;
	}

	private void setFunzLiq(String funzLiq) {
		this.funzLiq = funzLiq;
	}

	private String getMotivoRichModif() {
		return motivoRichModif;
	}

	private void setMotivoRichModif(String motivoRichModif) {
		this.motivoRichModif = motivoRichModif;
	}

	public Integer getNcarta() {
		return ncarta;
	}

	public void setNcarta(Integer ncarta) {
		this.ncarta = ncarta;
	}

	private Integer getNelenco() {
		return nelenco;
	}

	private void setNelenco(Integer nelenco) {
		this.nelenco = nelenco;
	}

	public String getNotaRifiuto() {
		return notaRifiuto;
	}

	public void setNotaRifiuto(String notaRifiuto) {
		this.notaRifiuto = notaRifiuto;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumPratica() {
		return numPratica;
	}

	public void setNumPratica(String numPratica) {
		this.numPratica = numPratica;
	}

	public String getNumTel() {
		return numTel;
	}

	private void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public String getTipoPagamAutom() {
		return tipoPagamAutom;
	}

	public void setTipoPagamAutom(String tipoPagamAutom) {
		this.tipoPagamAutom = tipoPagamAutom;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public RitenutaAttoVO getRitenuta() {
		return ritenuta;
	}
	public void setRitenuta(RitenutaAttoVO ritenuta) {
		this.ritenuta = ritenuta;
	}
	
	// CDU-110-V03 inizio
	
	private String strutturaAmmContabile;
	private String descCausale;
	private String numeroDocSpesa;
	private String descizioneDocSpesa;

	public String getDescizioneDocSpesa() {
		return descizioneDocSpesa;
	}
	public void setDescizioneDocSpesa(String descizioneDocSpesa) {
		this.descizioneDocSpesa = descizioneDocSpesa;
	}
	public String getNumeroDocSpesa() {
		return numeroDocSpesa;
	}
	public void setNumeroDocSpesa(String numeroDocSpesa) {
		this.numeroDocSpesa = numeroDocSpesa;
	}
	public String getStrutturaAmmContabile() {
		return strutturaAmmContabile;
	}
	public void setStrutturaAmmContabile(String strutturaAmmContabile) {
		this.strutturaAmmContabile = strutturaAmmContabile;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	
	// CDU-110-V03 fine

}