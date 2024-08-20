/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

import java.math.BigDecimal;
import java.util.ArrayList;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;

public class NuovoCampionamentoVO {
	
	private int idTipoCampionamento;  
	private String descCampionamento; 
	private ArrayList<AttivitaDTO> bandi  ;
	private DichiarazioneSpesaCampionamentoVO dichiarazioneSpesa;
	private BigDecimal idUtenteIns;
	private BigDecimal idCampionamento; 
	private long percEstrazione; 
	private Long numProgettiSel;
	private Long importoValidato; 
	private Long importEstrattoValidato; 
	private Long numProgettiTotali; 
	private Long numProgettiEstratti; 
	
	

	public int getIdTipoCampionamento() {
		return idTipoCampionamento;
	}
	public void setIdTipoCampionamento(int idTipoCampionamento) {
		this.idTipoCampionamento = idTipoCampionamento;
	}
	public String getDescCampionamento() {
		return descCampionamento;
	}
	public void setDescCampionamento(String descCampionamento) {
		this.descCampionamento = descCampionamento;
	}
	public ArrayList<AttivitaDTO> getBandi() {
		return bandi;
	}
	public void setBandi(ArrayList<AttivitaDTO> bandi) {
		this.bandi = bandi;
	}
	public DichiarazioneSpesaCampionamentoVO getDichiarazioneSpesa() {
		return dichiarazioneSpesa;
	}
	public void setDichiarazioneSpesa(DichiarazioneSpesaCampionamentoVO dichiarazioneSpesa) {
		this.dichiarazioneSpesa = dichiarazioneSpesa;
	}
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public long getPercEstrazione() {
		return percEstrazione;
	}
	public void setPercEstrazione(long percEstrazione) {
		this.percEstrazione = percEstrazione;
	}
	public BigDecimal getIdCampionamento() {
		return idCampionamento;
	}
	public void setIdCampionamento(BigDecimal idCampionamento) {
		this.idCampionamento = idCampionamento;
	}
	public Long getNumProgettiSel() {
		return numProgettiSel;
	}
	public void setNumProgettiSel(Long numProgettiSel) {
		this.numProgettiSel = numProgettiSel;
	}
	public Long getImportoValidato() {
		return importoValidato;
	}
	public void setImportoValidato(Long importoValidato) {
		this.importoValidato = importoValidato;
	}
	public Long getImportEstrattoValidato() {
		return importEstrattoValidato;
	}
	public void setImportEstrattoValidato(Long importEstrattoValidato) {
		this.importEstrattoValidato = importEstrattoValidato;
	}
	public Long getNumProgettiTotali() {
		return numProgettiTotali;
	}
	public void setNumProgettiTotali(Long numProgettiTotali) {
		this.numProgettiTotali = numProgettiTotali;
	}
	public Long getNumProgettiEstratti() {
		return numProgettiEstratti;
	}
	public void setNumProgettiEstratti(Long numProgettiEstratti) {
		this.numProgettiEstratti = numProgettiEstratti;
	}
	@Override
	public String toString() {
		return "NuovoCampionamentoVO [idTipoCampionamento=" + idTipoCampionamento + ", descCampionamento="
				+ descCampionamento + ", bandi=" + bandi + ", dichiarazioneSpesa=" + dichiarazioneSpesa
				+ ", idUtenteIns=" + idUtenteIns + ", idCampionamento=" + idCampionamento + ", percEstrazione="
				+ percEstrazione + ", numProgettiSel=" + numProgettiSel + ", importoValidato=" + importoValidato
				+ ", importEstrattoValidato=" + importEstrattoValidato + ", numProgettiTotali=" + numProgettiTotali
				+ ", numProgettiEstratti=" + numProgettiEstratti + "]";
	}
	

}
