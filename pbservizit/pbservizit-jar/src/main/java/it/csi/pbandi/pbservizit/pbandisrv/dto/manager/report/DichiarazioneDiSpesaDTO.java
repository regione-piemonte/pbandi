/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.manager.report;

import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.BeneficiarioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoContabileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.RappresentanteLegaleDTO;

import java.util.List;



public class DichiarazioneDiSpesaDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;
	private EnteAppartenenzaDTO ente = null;
	private ProgettoDTO progetto = null;
	private java.util.Date dataDal = null;
	private java.util.Date dataAl = null;
	private BeneficiarioDTO beneficiario = null;
	private RappresentanteLegaleDTO rappresentanteLegale = null;
	private java.lang.Long idDichiarazione = null;
	private java.lang.String descTipoDichiarazione = null;
	private java.lang.String indirizzo = null;
	private String descBando;
	private String noteFineProgetto;
	private Long idBandoLinea ;
	private String numero= null;
	private String data= null;
	private String descModalitaAgevolazione= null;
	private java.util.Date dataFineRendicontazione = null;
	private List<VoceDiCostoDTO> vociDiCosto = null;
	private List<DocumentoContabileDTO> documentiContabili = null;
	private Boolean isBr02;
	private Boolean isBr05;
	private Boolean isBR16;
	private List<FileAllegatoDTO> fileAllegati = null;
	
	public EnteAppartenenzaDTO getEnte() {
		return ente;
	}
	public void setEnte(EnteAppartenenzaDTO ente) {
		this.ente = ente;
	}
	public ProgettoDTO getProgetto() {
		return progetto;
	}
	public void setProgetto(ProgettoDTO progetto) {
		this.progetto = progetto;
	}
	public java.util.Date getDataDal() {
		return dataDal;
	}
	public void setDataDal(java.util.Date dataDal) {
		this.dataDal = dataDal;
	}
	public java.util.Date getDataAl() {
		return dataAl;
	}
	public void setDataAl(java.util.Date dataAl) {
		this.dataAl = dataAl;
	}
	public BeneficiarioDTO getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(BeneficiarioDTO beneficiario) {
		this.beneficiario = beneficiario;
	}
	public RappresentanteLegaleDTO getRappresentanteLegale() {
		return rappresentanteLegale;
	}
	public void setRappresentanteLegale(RappresentanteLegaleDTO rappresentanteLegale) {
		this.rappresentanteLegale = rappresentanteLegale;
	}
	public java.lang.Long getIdDichiarazione() {
		return idDichiarazione;
	}
	public void setIdDichiarazione(java.lang.Long idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}
	public java.lang.String getDescTipoDichiarazione() {
		return descTipoDichiarazione;
	}
	public void setDescTipoDichiarazione(java.lang.String descTipoDichiarazione) {
		this.descTipoDichiarazione = descTipoDichiarazione;
	}
	public java.lang.String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(java.lang.String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getDescBando() {
		return descBando;
	}
	public void setDescBando(String descBando) {
		this.descBando = descBando;
	}
	public String getNoteFineProgetto() {
		return noteFineProgetto;
	}
	public void setNoteFineProgetto(String noteFineProgetto) {
		this.noteFineProgetto = noteFineProgetto;
	}
	public Long getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public void setDataFineRendicontazione(java.util.Date dataFineRendicontazione) {
		this.dataFineRendicontazione = dataFineRendicontazione;
	}
	public java.util.Date getDataFineRendicontazione() {
		return dataFineRendicontazione;
	}
	public void setVociDiCosto(List<VoceDiCostoDTO> vociDiCosto) {
		this.vociDiCosto = vociDiCosto;
	}
	public List<VoceDiCostoDTO> getVociDiCosto() {
		return vociDiCosto;
	}
	public void setIsBr02(Boolean isBr02) {
		this.isBr02 = isBr02;
	}
	public Boolean getIsBr02() {
		return isBr02;
	}
	public void setIsBr05(Boolean isBr05) {
		this.isBr05 = isBr05;
	}
	public Boolean getIsBr05() {
		return isBr05;
	}
	public void setIsBR16(Boolean isBR16) {
		this.isBR16 = isBR16;
	}
	public Boolean getIsBR16() {
		return isBR16;
	}
	public void setDocumentiContabili(List<DocumentoContabileDTO> documentiContabili) {
		this.documentiContabili = documentiContabili;
	}
	public List<DocumentoContabileDTO> getDocumentiContabili() {
		return documentiContabili;
	}
	public List<FileAllegatoDTO> getFileAllegati() {
		return fileAllegati;
	}
	public void setFileAllegati(List<FileAllegatoDTO> fileAllegati) {
		this.fileAllegati = fileAllegati;
	}
	
	
}
