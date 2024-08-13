/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTAttoLiquidazioneVO;

import java.math.BigDecimal;
import java.sql.Date;

public class DettaglioAttoLiquidazioneVO extends PbandiTAttoLiquidazioneVO {
	private BigDecimal idProgetto;
	private String codiceVisualizzatoProgetto;
	private BigDecimal idBeneficiarioBilancio;
	private String denominazioneBeneficiarioBil;
	private String estremiAtto;
	private String numeroImpegno;
	private String annoEsercizioImpegno;
	private String annoImpegno;
	private String descStatoAtto;
	private String descBreveStatoAtto;
	private String impegno;
	private String descCausale;
	private String descTipoSoggRitenuta;
	private BigDecimal idSoggetto;
	private Date dtEmissioneAtto;
	

	public Date getDtEmissioneAtto() {
		return dtEmissioneAtto;
	}

	public void setDtEmissioneAtto(Date dtEmissioneAtto) {
		this.dtEmissioneAtto = dtEmissioneAtto;
	}

	public DettaglioAttoLiquidazioneVO() {
		super();
	}
	
	public DettaglioAttoLiquidazioneVO(BigDecimal idAttoLiquidazione) {
		super(idAttoLiquidazione);
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}
	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}
	public BigDecimal getIdBeneficiarioBilancio() {
		return idBeneficiarioBilancio;
	}
	public void setIdBeneficiarioBilancio(BigDecimal idBeneficiarioBilancio) {
		this.idBeneficiarioBilancio = idBeneficiarioBilancio;
	}
	public String getDenominazioneBeneficiarioBil() {
		return denominazioneBeneficiarioBil;
	}
	public void setDenominazioneBeneficiarioBil(String denominazioneBeneficiarioBil) {
		this.denominazioneBeneficiarioBil = denominazioneBeneficiarioBil;
	}
	public String getEstremiAtto() {
		return estremiAtto;
	}
	public void setEstremiAtto(String estremiAtto) {
		this.estremiAtto = estremiAtto;
	}
	public String getNumeroImpegno() {
		return numeroImpegno;
	}
	public void setNumeroImpegno(String numeroImpegno) {
		this.numeroImpegno = numeroImpegno;
	}
	public String getAnnoEsercizioImpegno() {
		return annoEsercizioImpegno;
	}
	public void setAnnoEsercizioImpegno(String annoEsercizioImpegno) {
		this.annoEsercizioImpegno = annoEsercizioImpegno;
	}
	public String getAnnoImpegno() {
		return annoImpegno;
	}
	public void setAnnoImpegno(String annoImpegno) {
		this.annoImpegno = annoImpegno;
	}
	public String getDescStatoAtto() {
		return descStatoAtto;
	}
	public void setDescStatoAtto(String descStatoAtto) {
		this.descStatoAtto = descStatoAtto;
	}
	public String getDescBreveStatoAtto() {
		return descBreveStatoAtto;
	}

	public void setDescBreveStatoAtto(String descBreveStatoAtto) {
		this.descBreveStatoAtto = descBreveStatoAtto;
	}

	public String getImpegno() {
		return impegno;
	}
	public void setImpegno(String impegno) {
		this.impegno = impegno;
	}

	public String getDescCausale() {
		return descCausale;
	}

	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}

	public String getDescTipoSoggRitenuta() {
		return descTipoSoggRitenuta;
	}

	public void setDescTipoSoggRitenuta(String descTipoSoggRitenuta) {
		this.descTipoSoggRitenuta = descTipoSoggRitenuta;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
}
