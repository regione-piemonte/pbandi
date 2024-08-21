/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.services.bilancio.vo;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ImpegnoVO extends ImpegnoKeyVO {
	
	private BigDecimal idImpegno;						//
	private String annoImpegno; 						//
	private String annoEsercizio;
	private String annoPerente;							//
	private String annoProvvedimento;					//
	private String codiceProvvedimento;
	private String cig;									//
	private String codiceBeneficiario;
	private String cup;									//
	private Date dataAggiornamento; 					//
	private Date dataInserimento;   					//
	private Date dataProvvedimento;
	private String descCapitolo;						//
	private String descBreveDirezioneProvvedimento;		//
	private String descDirezioneProvvedimento;			//
	private BigDecimal importoDisponibilitaLiquidare;	//
	private BigDecimal importoAttualeImpegno; 			//
	private BigDecimal importoInizialeImpegno; 			//
	private String numeroCapitoloOrigine;				//
	private String numeroProvvedimento;					//
	private String numeroArticoloCapitolo;
	private String numeroCapitolo;						//
	private String numeroPerente;						//
	private String provenienzaCapitolo;
	private String ragioneSocialeBeneficiario;
	private String descBreveStatoImpegno; 				//
	private String descBreveTipoFondo;					//
	private String tipologiaBeneficiario;
	private BigDecimal totaleLiquidatoImpegno; 			//
	private BigDecimal importoTotaleQuietanzato; 		//
	private String tipologiaTrasferimento;
	private String voceEconomicaTrasferimento;
	private String utente;
	private String descTipologiaBeneficiario;
	private String descImpegno;							//
	private BigDecimal idStatoImpegno;					//
	private String descStatoImpegno;					//
	private BigDecimal idProvvedimento;					//
	private BigDecimal idTipoProvvedimento;				//
	private String descBreveTipoProvvedimento;			//
	private String descTipoProvvedimento;				//
	private BigDecimal idDirezioneProvvedimento;		//
	private BigDecimal idTipoEnteProvvedimento;			//
	private String descTipoEnteProvvedimento;			//
	private String descBreveTipoEnteProvvedimento;		//
	private BigDecimal idCapitolo;						//
	private BigDecimal idEnteCompetenzaCapitolo;		//
	private String descEnteCapitolo;					//
	private String descBreveEnteCapitolo;				//
	private BigDecimal idTipoFondo;						//
	private String descTipoFondo;						//
	private BigDecimal numeroTotaleMandati;				//
	private Boolean flagNoProvvedimento;				//
	private String descBreveDirezioneDelegata;			//
	
	private String flagPersonaFisica;
	private String partitaIva;
	private String codiceFiscale;
	
	
	
	public BigDecimal getIdImpegno() {
		return idImpegno;
	}
	public void setIdImpegno(BigDecimal idImpegno) {
		this.idImpegno = idImpegno;
	}
	public String getAnnoImpegno() {
		return annoImpegno;
	}
	public void setAnnoImpegno(String annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	public String getAnnoPerente() {
		return annoPerente;
	}
	public void setAnnoPerente(String annoPerente) {
		this.annoPerente = annoPerente;
	}
	public String getAnnoProvvedimento() {
		return annoProvvedimento;
	}
	public void setAnnoProvvedimento(String annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}
	public String getCodiceProvvedimento() {
		return codiceProvvedimento;
	}
	public void setCodiceProvvedimento(String codiceProvvedimento) {
		this.codiceProvvedimento = codiceProvvedimento;
	}
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public String getCodiceBeneficiario() {
		return codiceBeneficiario;
	}
	public void setCodiceBeneficiario(String codiceBeneficiario) {
		this.codiceBeneficiario = codiceBeneficiario;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataProvvedimento() {
		return dataProvvedimento;
	}
	public void setDataProvvedimento(Date dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}
	public String getDescCapitolo() {
		return descCapitolo;
	}
	public void setDescCapitolo(String descCapitolo) {
		this.descCapitolo = descCapitolo;
	}
	public String getDescBreveDirezioneProvvedimento() {
		return descBreveDirezioneProvvedimento;
	}
	public void setDescBreveDirezioneProvvedimento(
			String descBreveDirezioneProvvedimento) {
		this.descBreveDirezioneProvvedimento = descBreveDirezioneProvvedimento;
	}
	public String getDescDirezioneProvvedimento() {
		return descDirezioneProvvedimento;
	}
	public void setDescDirezioneProvvedimento(String descDirezioneProvvedimento) {
		this.descDirezioneProvvedimento = descDirezioneProvvedimento;
	}
	public BigDecimal getImportoDisponibilitaLiquidare() {
		return importoDisponibilitaLiquidare;
	}
	public void setImportoDisponibilitaLiquidare(
			BigDecimal importoDisponibilitaLiquidare) {
		this.importoDisponibilitaLiquidare = importoDisponibilitaLiquidare;
	}
	public BigDecimal getImportoAttualeImpegno() {
		return importoAttualeImpegno;
	}
	public void setImportoAttualeImpegno(BigDecimal importoAttualeImpegno) {
		this.importoAttualeImpegno = importoAttualeImpegno;
	}
	public BigDecimal getImportoInizialeImpegno() {
		return importoInizialeImpegno;
	}
	public void setImportoInizialeImpegno(BigDecimal importoInizialeImpegno) {
		this.importoInizialeImpegno = importoInizialeImpegno;
	}
	public String getNumeroCapitoloOrigine() {
		return numeroCapitoloOrigine;
	}
	public void setNumeroCapitoloOrigine(String numeroCapitoloOrigine) {
		this.numeroCapitoloOrigine = numeroCapitoloOrigine;
	}
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	public String getNumeroArticoloCapitolo() {
		return numeroArticoloCapitolo;
	}
	public void setNumeroArticoloCapitolo(String numeroArticoloCapitolo) {
		this.numeroArticoloCapitolo = numeroArticoloCapitolo;
	}
	public String getNumeroCapitolo() {
		return numeroCapitolo;
	}
	public void setNumeroCapitolo(String numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}
	public String getNumeroPerente() {
		return numeroPerente;
	}
	public void setNumeroPerente(String numeroPerente) {
		this.numeroPerente = numeroPerente;
	}
	public String getProvenienzaCapitolo() {
		return provenienzaCapitolo;
	}
	public void setProvenienzaCapitolo(String provenienzaCapitolo) {
		this.provenienzaCapitolo = provenienzaCapitolo;
	}
	public String getRagioneSocialeBeneficiario() {
		return ragioneSocialeBeneficiario;
	}
	public void setRagioneSocialeBeneficiario(String ragioneSocialeBeneficiario) {
		this.ragioneSocialeBeneficiario = ragioneSocialeBeneficiario;
	}
	public String getDescBreveStatoImpegno() {
		return descBreveStatoImpegno;
	}
	public void setDescBreveStatoImpegno(String descBreveStatoImpegno) {
		this.descBreveStatoImpegno = descBreveStatoImpegno;
	}
	public String getDescBreveTipoFondo() {
		return descBreveTipoFondo;
	}
	public void setDescBreveTipoFondo(String descBreveTipoFondo) {
		this.descBreveTipoFondo = descBreveTipoFondo;
	}
	public String getTipologiaBeneficiario() {
		return tipologiaBeneficiario;
	}
	public void setTipologiaBeneficiario(String tipologiaBeneficiario) {
		this.tipologiaBeneficiario = tipologiaBeneficiario;
	}
	public BigDecimal getTotaleLiquidatoImpegno() {
		return totaleLiquidatoImpegno;
	}
	public void setTotaleLiquidatoImpegno(BigDecimal totaleLiquidatoImpegno) {
		this.totaleLiquidatoImpegno = totaleLiquidatoImpegno;
	}
	public BigDecimal getImportoTotaleQuietanzato() {
		return importoTotaleQuietanzato;
	}
	public void setImportoTotaleQuietanzato(BigDecimal importoTotaleQuietanzato) {
		this.importoTotaleQuietanzato = importoTotaleQuietanzato;
	}
	public String getTipologiaTrasferimento() {
		return tipologiaTrasferimento;
	}
	public void setTipologiaTrasferimento(String tipologiaTrasferimento) {
		this.tipologiaTrasferimento = tipologiaTrasferimento;
	}
	public String getVoceEconomicaTrasferimento() {
		return voceEconomicaTrasferimento;
	}
	public void setVoceEconomicaTrasferimento(String voceEconomicaTrasferimento) {
		this.voceEconomicaTrasferimento = voceEconomicaTrasferimento;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public String getDescTipologiaBeneficiario() {
		return descTipologiaBeneficiario;
	}
	public void setDescTipologiaBeneficiario(String descTipologiaBeneficiario) {
		this.descTipologiaBeneficiario = descTipologiaBeneficiario;
	}
	public String getDescImpegno() {
		return descImpegno;
	}
	public void setDescImpegno(String descImpegno) {
		this.descImpegno = descImpegno;
	}
	public BigDecimal getIdStatoImpegno() {
		return idStatoImpegno;
	}
	public void setIdStatoImpegno(BigDecimal idStatoImpegno) {
		this.idStatoImpegno = idStatoImpegno;
	}
	public String getDescStatoImpegno() {
		return descStatoImpegno;
	}
	public void setDescStatoImpegno(String descStatoImpegno) {
		this.descStatoImpegno = descStatoImpegno;
	}
	public BigDecimal getIdProvvedimento() {
		return idProvvedimento;
	}
	public void setIdProvvedimento(BigDecimal idProvvedimento) {
		this.idProvvedimento = idProvvedimento;
	}
	public BigDecimal getIdTipoProvvedimento() {
		return idTipoProvvedimento;
	}
	public void setIdTipoProvvedimento(BigDecimal idTipoProvvedimento) {
		this.idTipoProvvedimento = idTipoProvvedimento;
	}
	public String getDescBreveTipoProvvedimento() {
		return descBreveTipoProvvedimento;
	}
	public void setDescBreveTipoProvvedimento(String descBreveTipoProvvedimento) {
		this.descBreveTipoProvvedimento = descBreveTipoProvvedimento;
	}
	public String getDescTipoProvvedimento() {
		return descTipoProvvedimento;
	}
	public void setDescTipoProvvedimento(String descTipoProvvedimento) {
		this.descTipoProvvedimento = descTipoProvvedimento;
	}
	public BigDecimal getIdDirezioneProvvedimento() {
		return idDirezioneProvvedimento;
	}
	public void setIdDirezioneProvvedimento(BigDecimal idDirezioneProvvedimento) {
		this.idDirezioneProvvedimento = idDirezioneProvvedimento;
	}
	public BigDecimal getIdTipoEnteProvvedimento() {
		return idTipoEnteProvvedimento;
	}
	public void setIdTipoEnteProvvedimento(BigDecimal idTipoEnteProvvedimento) {
		this.idTipoEnteProvvedimento = idTipoEnteProvvedimento;
	}
	public String getDescTipoEnteProvvedimento() {
		return descTipoEnteProvvedimento;
	}
	public void setDescTipoEnteProvvedimento(String descTipoEnteProvvedimento) {
		this.descTipoEnteProvvedimento = descTipoEnteProvvedimento;
	}
	public String getDescBreveTipoEnteProvvedimento() {
		return descBreveTipoEnteProvvedimento;
	}
	public void setDescBreveTipoEnteProvvedimento(
			String descBreveTipoEnteProvvedimento) {
		this.descBreveTipoEnteProvvedimento = descBreveTipoEnteProvvedimento;
	}
	public BigDecimal getIdCapitolo() {
		return idCapitolo;
	}
	public void setIdCapitolo(BigDecimal idCapitolo) {
		this.idCapitolo = idCapitolo;
	}
	public BigDecimal getIdEnteCompetenzaCapitolo() {
		return idEnteCompetenzaCapitolo;
	}
	public void setIdEnteCompetenzaCapitolo(BigDecimal idEnteCompetenzaCapitolo) {
		this.idEnteCompetenzaCapitolo = idEnteCompetenzaCapitolo;
	}
	public String getDescEnteCapitolo() {
		return descEnteCapitolo;
	}
	public void setDescEnteCapitolo(String descEnteCapitolo) {
		this.descEnteCapitolo = descEnteCapitolo;
	}
	public String getDescBreveEnteCapitolo() {
		return descBreveEnteCapitolo;
	}
	public void setDescBreveEnteCapitolo(String descBreveEnteCapitolo) {
		this.descBreveEnteCapitolo = descBreveEnteCapitolo;
	}
	public BigDecimal getIdTipoFondo() {
		return idTipoFondo;
	}
	public void setIdTipoFondo(BigDecimal idTipoFondo) {
		this.idTipoFondo = idTipoFondo;
	}
	public String getDescTipoFondo() {
		return descTipoFondo;
	}
	public void setDescTipoFondo(String descTipoFondo) {
		this.descTipoFondo = descTipoFondo;
	}
	public BigDecimal getNumeroTotaleMandati() {
		return numeroTotaleMandati;
	}
	public void setNumeroTotaleMandati(BigDecimal numeroTotaleMandati) {
		this.numeroTotaleMandati = numeroTotaleMandati;
	}
	public Boolean getFlagNoProvvedimento() {
		return flagNoProvvedimento;
	}
	public void setFlagNoProvvedimento(Boolean flagNoProvvedimento) {
		this.flagNoProvvedimento = flagNoProvvedimento;
	}
	public String getDescBreveDirezioneDelegata() {
		return descBreveDirezioneDelegata;
	}
	public void setDescBreveDirezioneDelegata(String descBreveDirezioneDelegata) {
		this.descBreveDirezioneDelegata = descBreveDirezioneDelegata;
	}
	public void setFlagPersonaFisica(String flagPersonaFisica) {
		this.flagPersonaFisica = flagPersonaFisica;
	}
	public String getFlagPersonaFisica() {
		return flagPersonaFisica;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String sep = "\t\n";
		String s = "ImpegnoVO :"+sep;
		if (idImpegno != null)
			s = s + "idImpegno = " + idImpegno + sep;
		if (annoImpegno != null)
			s = s + "annoImpegno = " + annoImpegno + sep;	
		if (annoEsercizio != null)
			s = s + "annoEsercizio = " + annoEsercizio + sep;
		if (annoPerente != null)
			s = s + "annoPerente = " + annoPerente + sep;					
		if (annoProvvedimento != null)
			s = s + "annoProvvedimento = " + annoProvvedimento + sep;			
		if (codiceProvvedimento != null)
			s = s + "codiceProvvedimento = " + codiceProvvedimento + sep;
		if (cig != null)
			s = s + "cig = " + cig + sep;							
		if (codiceBeneficiario != null)
			s = s + "codiceBeneficiario = " + codiceBeneficiario + sep;
		if (cup != null)
			s = s + "cup = " + cup + sep;							
		if (dataAggiornamento != null)
			s = s + "dataAggiornamento = " + sdf.format(dataAggiornamento) + sep; 			
		if (dataInserimento != null)
			s = s + "dataInserimento = " + sdf.format(dataInserimento) + sep;   			
		if (dataProvvedimento != null)
			s = s + "dataProvvedimento = " + sdf.format(dataProvvedimento) + sep;
		if (descCapitolo != null)
			s = s + "descCapitolo = " + descCapitolo + sep;				
		if (descBreveDirezioneProvvedimento != null)
			s = s + "descBreveDirezioneProvvedimento = " + descBreveDirezioneProvvedimento + sep;
		if (descDirezioneProvvedimento != null)
			s = s + "descDirezioneProvvedimento = " + descDirezioneProvvedimento + sep;	
		if (importoDisponibilitaLiquidare != null)
			s = s + "importoDisponibilitaLiquidare = " + importoDisponibilitaLiquidare + sep;
		if (importoAttualeImpegno != null)
			s = s + "importoAttualeImpegno = " + importoAttualeImpegno + sep; 	
		if (importoInizialeImpegno != null)
			s = s + "importoInizialeImpegno = " + importoInizialeImpegno + sep; 	
		if (numeroCapitoloOrigine != null)
			s = s + "numeroCapitoloOrigine = " + numeroCapitoloOrigine + sep;		
		if (numeroProvvedimento != null)
			s = s + "numeroProvvedimento = " + numeroProvvedimento + sep;			
		if (numeroArticoloCapitolo != null)
			s = s + "numeroArticoloCapitolo = " + numeroArticoloCapitolo + sep;
		if (numeroCapitolo != null)
			s = s + "numeroCapitolo = " + numeroCapitolo + sep;				
		if (numeroPerente != null)
			s = s + "numeroPerente = " + numeroPerente + sep;				
		if (provenienzaCapitolo != null)
			s = s + "provenienzaCapitolo = " + provenienzaCapitolo + sep;
		if (ragioneSocialeBeneficiario != null)
			s = s + "ragioneSocialeBeneficiario = " + ragioneSocialeBeneficiario + sep;
		if (descBreveStatoImpegno != null)
			s = s + "descBreveStatoImpegno = " + descBreveStatoImpegno + sep; 		
		if (descBreveTipoFondo != null)
			s = s + "descBreveTipoFondo = " + descBreveTipoFondo + sep;			
		if (tipologiaBeneficiario != null)
			s = s + "tipologiaBeneficiario = " + tipologiaBeneficiario + sep;
		if (totaleLiquidatoImpegno != null)
			s = s + "totaleLiquidatoImpegno = " + totaleLiquidatoImpegno + sep; 	
		if (importoTotaleQuietanzato != null)
			s = s + "importoTotaleQuietanzato = " + importoTotaleQuietanzato + sep; 
		if (tipologiaTrasferimento != null)
			s = s + "tipologiaTrasferimento = " + tipologiaTrasferimento + sep;
		if (voceEconomicaTrasferimento != null)
			s = s + "voceEconomicaTrasferimento = " + voceEconomicaTrasferimento + sep;
		if (utente != null)
			s = s + "utente = " + utente + sep;
		if (descTipologiaBeneficiario != null)
			s = s + "descTipologiaBeneficiario = " + descTipologiaBeneficiario + sep;
		if (descImpegno != null)
			s = s + "descImpegno = " + descImpegno + sep;					
		if (idStatoImpegno != null)
			s = s + "idStatoImpegno = " + idStatoImpegno + sep;			
		if (descStatoImpegno != null)
			s = s + "descStatoImpegno = " + descStatoImpegno + sep;			
		if (idProvvedimento != null)
			s = s + "idProvvedimento = " + idProvvedimento + sep;			
		if (idTipoProvvedimento != null)
			s = s + "idTipoProvvedimento = " + idTipoProvvedimento + sep;		
		if (descBreveTipoProvvedimento != null)
			s = s + "descBreveTipoProvvedimento = " + descBreveTipoProvvedimento + sep;	
		if (descTipoProvvedimento != null)
			s = s + "descTipoProvvedimento = " + descTipoProvvedimento + sep;		
		if (idDirezioneProvvedimento != null)
			s = s + "idDirezioneProvvedimento = " + idDirezioneProvvedimento + sep;
		if (idTipoEnteProvvedimento != null)
			s = s + "idTipoEnteProvvedimento = " + idTipoEnteProvvedimento + sep;	
		if (descTipoEnteProvvedimento != null)
			s = s + "descTipoEnteProvvedimento = " + descTipoEnteProvvedimento + sep;	
		if (descBreveTipoEnteProvvedimento != null)
			s = s + "descBreveTipoEnteProvvedimento = " + descBreveTipoEnteProvvedimento + sep;
		if (idCapitolo != null)
			s = s + "idCapitolo = " + idCapitolo + sep;				
		if (idEnteCompetenzaCapitolo != null)
			s = s + "idEnteCompetenzaCapitolo = " + idEnteCompetenzaCapitolo + sep;
		if (descEnteCapitolo != null)
			s = s + "descEnteCapitolo = " + descEnteCapitolo + sep;			
		if (descBreveEnteCapitolo != null)
			s = s + "descBreveEnteCapitolo = " + descBreveEnteCapitolo + sep;		
		if (idTipoFondo != null)
			s = s + "idTipoFondo = " + idTipoFondo + sep;				
		if (descTipoFondo != null)
			s = s + "descTipoFondo = " + descTipoFondo + sep;				
		if (numeroTotaleMandati != null)
			s = s + "numeroTotaleMandati = " + numeroTotaleMandati + sep;		
		if (flagNoProvvedimento != null)
			s = s + "flagNoProvvedimento = " + flagNoProvvedimento + sep;
		if (descBreveDirezioneDelegata != null)
			s = s + "descBreveDirezioneDelegata = " + descBreveDirezioneDelegata + sep;	
		if (flagPersonaFisica != null)
			s = s + "flagPersonaFisica = " + flagPersonaFisica + sep;
		if (partitaIva != null)
			s = s + "partitaIva = " + partitaIva + sep;
		if (codiceFiscale != null)
			s = s + "codiceFiscale = " + codiceFiscale + sep;	
		return s;
	}

}
