/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.dto.fineprogetto;

import java.util.Date;

public class FineComunicazioneReportDTO implements java.io.Serializable {
	static final long serialVersionUID = 1;

	private EnteAppartenenzaDTO ente;
	private ProgettoDTO progetto;
	private Date dataDal;
	private Date dataAl;
	private BeneficiarioDTO beneficiario;
	private RappresentanteLegaleDTO rappresentanteLegale;
	private Long idDichiarazione;
	private String descTipoDichiarazione;
	private String indirizzo;
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
	public Date getDataDal() {
		return dataDal;
	}
	public void setDataDal(Date dataDal) {
		this.dataDal = dataDal;
	}
	public Date getDataAl() {
		return dataAl;
	}
	public void setDataAl(Date dataAl) {
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
	public Long getIdDichiarazione() {
		return idDichiarazione;
	}
	public void setIdDichiarazione(Long idDichiarazione) {
		this.idDichiarazione = idDichiarazione;
	}
	public String getDescTipoDichiarazione() {
		return descTipoDichiarazione;
	}
	public void setDescTipoDichiarazione(String descTipoDichiarazione) {
		this.descTipoDichiarazione = descTipoDichiarazione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
}
