/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.List;

public class Riassicurazione_ProgettiAssociatiVO {
	
	private Long idProgetto;
	private Long idSoggetto;
	private Long idBando;
	private Long progrSoggettoProgetto;
	private Long idEscussione;

	private String codiceVisualizzato;
	private Long idModalitaAgevolazioneOrig;
	private String descBreveModalitaAgevolazioneOrig;
	private String descModalitaAgevolazioneOrig;
	private Long idModalitaAgevolazioneRif;
	private String descBreveModalitaAgevolazioneRif;
	private String descModalitaAgevolazioneRif;
	private BigDecimal totaleBanca;
	private BigDecimal totaleAmmesso;
	
	private List<Riassicurazione_SoggettiCorrelatiVO> soggettiCorrelati;
	
	

	public Riassicurazione_ProgettiAssociatiVO(Long idProgetto, Long idSoggetto, Long idBando,
			Long progrSoggettoProgetto, Long idEscussione, String codiceVisualizzato, Long idModalitaAgevolazioneOrig,
			String descBreveModalitaAgevolazioneOrig, String descModalitaAgevolazioneOrig,
			Long idModalitaAgevolazioneRif, String descBreveModalitaAgevolazioneRif, String descModalitaAgevolazioneRif,
			BigDecimal totaleBanca, BigDecimal totaleAmmesso,
			List<Riassicurazione_SoggettiCorrelatiVO> soggettiCorrelati) {
		this.idProgetto = idProgetto;
		this.idSoggetto = idSoggetto;
		this.idBando = idBando;
		this.progrSoggettoProgetto = progrSoggettoProgetto;
		this.idEscussione = idEscussione;
		this.codiceVisualizzato = codiceVisualizzato;
		this.idModalitaAgevolazioneOrig = idModalitaAgevolazioneOrig;
		this.descBreveModalitaAgevolazioneOrig = descBreveModalitaAgevolazioneOrig;
		this.descModalitaAgevolazioneOrig = descModalitaAgevolazioneOrig;
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
		this.descBreveModalitaAgevolazioneRif = descBreveModalitaAgevolazioneRif;
		this.descModalitaAgevolazioneRif = descModalitaAgevolazioneRif;
		this.totaleBanca = totaleBanca;
		this.totaleAmmesso = totaleAmmesso;
		this.soggettiCorrelati = soggettiCorrelati;
	}



	public Riassicurazione_ProgettiAssociatiVO() {
	}



	public Long getIdProgetto() {
		return idProgetto;
	}



	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}



	public Long getIdSoggetto() {
		return idSoggetto;
	}



	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}



	public Long getIdBando() {
		return idBando;
	}



	public void setIdBando(Long idBando) {
		this.idBando = idBando;
	}



	public Long getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}



	public void setProgrSoggettoProgetto(Long progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}



	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}



	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}



	public Long getIdModalitaAgevolazioneOrig() {
		return idModalitaAgevolazioneOrig;
	}



	public void setIdModalitaAgevolazioneOrig(Long idModalitaAgevolazioneOrig) {
		this.idModalitaAgevolazioneOrig = idModalitaAgevolazioneOrig;
	}



	public String getDescBreveModalitaAgevolazioneOrig() {
		return descBreveModalitaAgevolazioneOrig;
	}



	public void setDescBreveModalitaAgevolazioneOrig(String descBreveModalitaAgevolazioneOrig) {
		this.descBreveModalitaAgevolazioneOrig = descBreveModalitaAgevolazioneOrig;
	}



	public String getDescModalitaAgevolazioneOrig() {
		return descModalitaAgevolazioneOrig;
	}



	public void setDescModalitaAgevolazioneOrig(String descModalitaAgevolazioneOrig) {
		this.descModalitaAgevolazioneOrig = descModalitaAgevolazioneOrig;
	}



	public Long getIdModalitaAgevolazioneRif() {
		return idModalitaAgevolazioneRif;
	}



	public void setIdModalitaAgevolazioneRif(Long idModalitaAgevolazioneRif) {
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
	}



	public String getDescBreveModalitaAgevolazioneRif() {
		return descBreveModalitaAgevolazioneRif;
	}



	public void setDescBreveModalitaAgevolazioneRif(String descBreveModalitaAgevolazioneRif) {
		this.descBreveModalitaAgevolazioneRif = descBreveModalitaAgevolazioneRif;
	}



	public String getDescModalitaAgevolazioneRif() {
		return descModalitaAgevolazioneRif;
	}



	public void setDescModalitaAgevolazioneRif(String descModalitaAgevolazioneRif) {
		this.descModalitaAgevolazioneRif = descModalitaAgevolazioneRif;
	}



	public BigDecimal getTotaleBanca() {
		return totaleBanca;
	}



	public void setTotaleBanca(BigDecimal totaleBanca) {
		this.totaleBanca = totaleBanca;
	}



	public BigDecimal getTotaleAmmesso() {
		return totaleAmmesso;
	}



	public void setTotaleAmmesso(BigDecimal totaleAmmesso) {
		this.totaleAmmesso = totaleAmmesso;
	}



	public List<Riassicurazione_SoggettiCorrelatiVO> getSoggettiCorrelati() {
		return soggettiCorrelati;
	}



	public void setSoggettiCorrelati(List<Riassicurazione_SoggettiCorrelatiVO> soggettiCorrelati) {
		this.soggettiCorrelati = soggettiCorrelati;
	}



	public Long getIdEscussione() {
		return idEscussione;
	}



	public void setIdEscussione(Long idEscussione) {
		this.idEscussione = idEscussione;
	}



	@Override
	public String toString() {
		return "Riassicurazione_ProgettiAssociatiVO [idProgetto=" + idProgetto + ", idSoggetto=" + idSoggetto
				+ ", idBando=" + idBando + ", progrSoggettoProgetto=" + progrSoggettoProgetto + ", idEscussione="
				+ idEscussione + ", codiceVisualizzato=" + codiceVisualizzato + ", idModalitaAgevolazioneOrig="
				+ idModalitaAgevolazioneOrig + ", descBreveModalitaAgevolazioneOrig="
				+ descBreveModalitaAgevolazioneOrig + ", descModalitaAgevolazioneOrig=" + descModalitaAgevolazioneOrig
				+ ", idModalitaAgevolazioneRif=" + idModalitaAgevolazioneRif + ", descBreveModalitaAgevolazioneRif="
				+ descBreveModalitaAgevolazioneRif + ", descModalitaAgevolazioneRif=" + descModalitaAgevolazioneRif
				+ ", totaleBanca=" + totaleBanca + ", totaleAmmesso=" + totaleAmmesso + ", soggettiCorrelati="
				+ soggettiCorrelati + "]";
	}





	
	
}
