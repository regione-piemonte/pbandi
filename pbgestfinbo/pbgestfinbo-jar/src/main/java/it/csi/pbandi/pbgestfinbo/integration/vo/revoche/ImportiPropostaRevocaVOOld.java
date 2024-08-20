/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportiPropostaRevocaVOOld implements Serializable {
	
	private Long idGestioneRevoca;
	private Long idProgetto;
	private Long idBando;
	private Long idDomanda;	
	
	public String descModalitaAgevolazione;  
	public Integer importoAmmesso;   
	
	public Integer importoConcessoContributo;  
	public Integer importoConcessoFinanziamento;  
	public Integer importoConcessoGaranzia;  
	public Integer importoRevocatoContributo;  
	public Integer importoRevocatoFinanziamento;  
	public Integer importoRevocatoGaranzia;
	
	public Integer importoConcessoAlNettoRevocatoContributo;	
	public Integer importoConcessoAlNettoRevocatoFinanziamento;
	public Integer importoConcessoAlNettoRevocatoGaranzia;
	
	public Integer importoErogatoContributo;  
	public Integer importoErogatoFinanziamento;  
	public Integer importoErogatoGaranzia;  
	public Integer importoRecuperatoContributo;  
	public Integer importoRecuperatoFinanziamento;  
	public Integer importoRecuperatoGaranzia;
	
	public Integer importoErogatoAlNettoRecuperatoContributo;	
	public Integer importoErogatoAlNettoRecuperatoFinanziamento;
	public Integer importoErogatoAlNettoRecuperatoGaranzia;
	
	
		
	
	public ImportiPropostaRevocaVOOld() {
		super();
	}
	
	
	
	//GETTER SETTER ID
	
	public Long getIdGestioneRevoca() {
		return idGestioneRevoca;
	}
	public void setIdGestioneRevoca(Long idGestioneRevoca) {
		this.idGestioneRevoca = idGestioneRevoca;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdBando() {
		return idBando;
	}
	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}
	public Long getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}
	
	
	//GETTER SETTER DESCR AGEV E IPORTO AMMESSO
	
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public Integer getImportoAmmesso() {
		return importoAmmesso;
	}
	public void setImportoAmmesso(Integer importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}
	
	
	//GETTER SETTER IMPORTI CONCESSI E REVOCATI
	
	public Integer getImportoConcessoContributo() {
		return importoConcessoContributo;
	}
	public void setImportoConcessoContributo(Integer importoConcessoContributo) {
		this.importoConcessoContributo = importoConcessoContributo;
	}
	public Integer getImportoConcessoFinanziamento() {
		return importoConcessoFinanziamento;
	}
	public void setImportoConcessoFinanziamento(Integer importoConcessoFinanziamento) {
		this.importoConcessoFinanziamento = importoConcessoFinanziamento;
	}
	public Integer getImportoConcessoGaranzia() {
		return importoConcessoGaranzia;
	}
	public void setImportoConcessoGaranzia(Integer importoConcessoGaranzia) {
		this.importoConcessoGaranzia = importoConcessoGaranzia;
	}
	public Integer getImportoRevocatoContributo() {
		return importoRevocatoContributo;
	}
	public void setImportoRevocatoContributo(Integer importoRevocatoContributo) {
		this.importoRevocatoContributo = importoRevocatoContributo;
	}
	public Integer getImportoRevocatoFinanziamento() {
		return importoRevocatoFinanziamento;
	}
	public void setImportoRevocatoFinanziamento(Integer importoRevocatoFinanziamento) {
		this.importoRevocatoFinanziamento = importoRevocatoFinanziamento;
	}
	public Integer getImportoRevocatoGaranzia() {
		return importoRevocatoGaranzia;
	}
	public void setImportoRevocatoGaranzia(Integer importoRevocatoGaranzia) {
		this.importoRevocatoGaranzia = importoRevocatoGaranzia;
	}
	
	
	
	//GETTER SETTER IMPORTI CONCESSI AL NETTO DEI REVOCATI
	
	public Integer getImportoConcessoAlNettoRevocatoContributo() {		
		if(importoConcessoContributo != null && importoRevocatoContributo != null && importoConcessoContributo - importoRevocatoContributo >= 0)
			importoConcessoAlNettoRevocatoContributo = importoConcessoContributo - importoRevocatoContributo;
		return importoConcessoAlNettoRevocatoContributo;
	}
	public void setImportoConcessoAlNettoRevocatoContributo(Integer importoConcessoAlNettoRevocatoContributo) {
		this.importoConcessoAlNettoRevocatoContributo = importoConcessoAlNettoRevocatoContributo;
	}
	public Integer getImportoConcessoAlNettoRevocatoFinanziamento() {		
		if(importoConcessoFinanziamento != null && importoRevocatoFinanziamento != null && importoConcessoFinanziamento - importoRevocatoFinanziamento >= 0)
			importoConcessoAlNettoRevocatoFinanziamento = importoConcessoFinanziamento - importoRevocatoFinanziamento;
		return importoConcessoAlNettoRevocatoFinanziamento;
	}
	public void setImportoConcessoAlNettoRevocatoFinanziamento(Integer importoConcessoAlNettoRevocatoFinanziamento) {
		this.importoConcessoAlNettoRevocatoFinanziamento = importoConcessoAlNettoRevocatoFinanziamento;
	}
	public Integer getImportoConcessoAlNettoRevocatoGaranzia() {		
		if(importoConcessoGaranzia != null && importoRevocatoGaranzia != null && importoConcessoGaranzia - importoRevocatoGaranzia >= 0)
			importoConcessoAlNettoRevocatoGaranzia = importoConcessoGaranzia - importoRevocatoGaranzia;
		return importoConcessoAlNettoRevocatoGaranzia;
	}
	public void setImportoConcessoAlNettoRevocatoGaranzia(Integer importoConcessoAlNettoRevocatoGaranzia) {
		this.importoConcessoAlNettoRevocatoGaranzia = importoConcessoAlNettoRevocatoGaranzia;
	} 	
	
	
	//GETTER SETTER EROGATO E RECUPERATO
	
	public Integer getImportoErogatoContributo() {
		return importoErogatoContributo;
	}
	public void setImportoErogatoContributo(Integer importoErogatoContributo) {
		this.importoErogatoContributo = importoErogatoContributo;
	}
	public Integer getImportoErogatoFinanziamento() {
		return importoErogatoFinanziamento;
	}
	public void setImportoErogatoFinanziamento(Integer importoErogatoFinanziamento) {
		this.importoErogatoFinanziamento = importoErogatoFinanziamento;
	}
	public Integer getImportoErogatoGaranzia() {
		return importoErogatoGaranzia;
	}
	public void setImportoErogatoGaranzia(Integer importoErogatoGaranzia) {
		this.importoErogatoGaranzia = importoErogatoGaranzia;
	}
	public Integer getImportoRecuperatoContributo() {
		return importoRecuperatoContributo;
	}
	public void setImportoRecuperatoContributo(Integer importoRecuperatoContributo) {
		this.importoRecuperatoContributo = importoRecuperatoContributo;
	}
	public Integer getImportoRecuperatoFinanziamento() {
		return importoRecuperatoFinanziamento;
	}
	public void setImportoRecuperatoFinanziamento(Integer importoRecuperatoFinanziamento) {
		this.importoRecuperatoFinanziamento = importoRecuperatoFinanziamento;
	}
	public Integer getImportoRecuperatoGaranzia() {
		return importoRecuperatoGaranzia;
	}
	public void setImportoRecuperatoGaranzia(Integer importoRecuperatoGaranzia) {
		this.importoRecuperatoGaranzia = importoRecuperatoGaranzia;
	}	
	
	
	//GETTER SETTER IMPORTI EROGATO AL NETTO DEI RECUPERATI
	
	public Integer getImportoErogatoAlNettoRecuperatoContributo() {
		if(importoErogatoContributo != null && importoRecuperatoContributo != null && importoErogatoContributo - importoRecuperatoContributo >= 0)
			importoConcessoAlNettoRevocatoContributo = importoErogatoContributo - importoRecuperatoContributo;
		return importoErogatoAlNettoRecuperatoContributo;
	}
	public void setImportoErogatoAlNettoRecuperatoContributo(Integer importoErogatoAlNettoRecuperatoContributo) {
		this.importoErogatoAlNettoRecuperatoContributo = importoErogatoAlNettoRecuperatoContributo;
	}
	public Integer getImportoErogatoAlNettoRecuperatoFinanziamento() {
		if(importoErogatoFinanziamento != null && importoRecuperatoFinanziamento != null && importoErogatoFinanziamento - importoRecuperatoFinanziamento >= 0)
			importoConcessoAlNettoRevocatoFinanziamento = importoErogatoFinanziamento - importoRecuperatoFinanziamento;
		return importoErogatoAlNettoRecuperatoFinanziamento;
	}
	public void setImportoErogatoAlNettoRecuperatoFinanziamento(Integer importoErogatoAlNettoRecuperatoFinanziamento) {
		this.importoErogatoAlNettoRecuperatoFinanziamento = importoErogatoAlNettoRecuperatoFinanziamento;
	}
	public Integer getImportoErogatoAlNettoRecuperatoGaranzia() {
		return importoErogatoAlNettoRecuperatoGaranzia;
	}
	public void setImportoErogatoAlNettoRecuperatoGaranzia(Integer importoErogatoAlNettoRecuperatoGaranzia) {
		if(importoErogatoGaranzia != null && importoRecuperatoGaranzia != null && importoErogatoGaranzia - importoRecuperatoGaranzia >= 0)
			importoConcessoAlNettoRevocatoGaranzia = importoErogatoGaranzia - importoRecuperatoGaranzia;
		this.importoErogatoAlNettoRecuperatoGaranzia = importoErogatoAlNettoRecuperatoGaranzia;
	}
	
	
	
	
	
	
public ImportiPropostaRevocaVOOld(Long idGestioneRevoca, Long idProgetto, Long idBando, Long idDomanda,
			String descModalitaAgevolazione, Integer importoAmmesso, Integer importoConcessoContributo,
			Integer importoConcessoFinanziamento, Integer importoConcessoGaranzia, Integer importoRevocatoContributo,
			Integer importoRevocatoFinanziamento, Integer importoRevocatoGaranzia,
			Integer importoConcessoAlNettoRevocatoContributo, Integer importoConcessoAlNettoRevocatoFinanziamento,
			Integer importoConcessoAlNettoRevocatoGaranzia, Integer importoErogatoContributo,
			Integer importoErogatoFinanziamento, Integer importoErogatoGaranzia, Integer importoRecuperatoContributo,
			Integer importoRecuperatoFinanziamento, Integer importoRecuperatoGaranzia,
			Integer importoErogatoAlNettoRecuperatoContributo, Integer importoErogatoAlNettoRecuperatoFinanziamento,
			Integer importoErogatoAlNettoRecuperatoGaranzia) {
		super();
		this.idGestioneRevoca = idGestioneRevoca;
		this.idProgetto = idProgetto;
		this.idBando = idBando;
		this.idDomanda = idDomanda;
		this.descModalitaAgevolazione = descModalitaAgevolazione;
		this.importoAmmesso = importoAmmesso;
		this.importoConcessoContributo = importoConcessoContributo;
		this.importoConcessoFinanziamento = importoConcessoFinanziamento;
		this.importoConcessoGaranzia = importoConcessoGaranzia;
		this.importoRevocatoContributo = importoRevocatoContributo;
		this.importoRevocatoFinanziamento = importoRevocatoFinanziamento;
		this.importoRevocatoGaranzia = importoRevocatoGaranzia;
		this.importoConcessoAlNettoRevocatoContributo = importoConcessoAlNettoRevocatoContributo;
		this.importoConcessoAlNettoRevocatoFinanziamento = importoConcessoAlNettoRevocatoFinanziamento;
		this.importoConcessoAlNettoRevocatoGaranzia = importoConcessoAlNettoRevocatoGaranzia;
		this.importoErogatoContributo = importoErogatoContributo;
		this.importoErogatoFinanziamento = importoErogatoFinanziamento;
		this.importoErogatoGaranzia = importoErogatoGaranzia;
		this.importoRecuperatoContributo = importoRecuperatoContributo;
		this.importoRecuperatoFinanziamento = importoRecuperatoFinanziamento;
		this.importoRecuperatoGaranzia = importoRecuperatoGaranzia;
		this.importoErogatoAlNettoRecuperatoContributo = importoErogatoAlNettoRecuperatoContributo;
		this.importoErogatoAlNettoRecuperatoFinanziamento = importoErogatoAlNettoRecuperatoFinanziamento;
		this.importoErogatoAlNettoRecuperatoGaranzia = importoErogatoAlNettoRecuperatoGaranzia;
	}


	public static ImportiPropostaRevocaVOOld createMokObject () {
		
		return new ImportiPropostaRevocaVOOld(
				1L,
				1L,
				1L,
				1L,
				"descModalitaAgevolazione",
				300,
				100,
				100,
				100,
				0,
				0,
				0,
				100,
				100,
				100,
				50,
				50,
				50,
				0,
				0,
				0,
				50,
				50,
				50
		);		
				
		
		
	}
	
	public static List<ImportiPropostaRevocaVOOld> createMokObjectList (){
		List<ImportiPropostaRevocaVOOld> importiProposteRevocaV0List = new ArrayList<ImportiPropostaRevocaVOOld>();
    	importiProposteRevocaV0List.add(ImportiPropostaRevocaVOOld.createMokObject());
    	importiProposteRevocaV0List.add(ImportiPropostaRevocaVOOld.createMokObject());
    	return importiProposteRevocaV0List;
	}
    
    
}