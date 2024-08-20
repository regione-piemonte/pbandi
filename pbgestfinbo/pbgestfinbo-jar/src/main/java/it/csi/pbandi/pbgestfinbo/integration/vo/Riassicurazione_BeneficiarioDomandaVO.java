/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo;

import java.math.BigDecimal;
import java.util.List;

public class Riassicurazione_BeneficiarioDomandaVO {
	
	private Long idSoggetto;
	private Long idProgetto;

	private String denominazione;
	private Long idDomanda;
	private String numeroDomanda;
	private Long idStatoDomanda;
	private String descBreveStatoDomanda;
	private String descStatoDomanda;
	private String titoloBando;
	private BigDecimal importoRichiesto;
	private BigDecimal importoAmmesso;
	
	//private List<Riassicurazione_ProgettiAssociatiVO> progettiAssociati;
	private Long idUltimaEscussione;
	private List<Riassicurazione_SoggettiCorrelatiVO> soggettiCorrelati;

	
	

	public Riassicurazione_BeneficiarioDomandaVO(Long idSoggetto, Long idProgetto, String denominazione, Long idDomanda,
			String numeroDomanda, Long idStatoDomanda, String descBreveStatoDomanda, String descStatoDomanda,
			String titoloBando, BigDecimal importoRichiesto, BigDecimal importoAmmesso, Long ultimoIdEscussione,
			List<Riassicurazione_SoggettiCorrelatiVO> soggettiCorrelati) {
		this.idSoggetto = idSoggetto;
		this.idProgetto = idProgetto;
		this.denominazione = denominazione;
		this.idDomanda = idDomanda;
		this.numeroDomanda = numeroDomanda;
		this.idStatoDomanda = idStatoDomanda;
		this.descBreveStatoDomanda = descBreveStatoDomanda;
		this.descStatoDomanda = descStatoDomanda;
		this.titoloBando = titoloBando;
		this.importoRichiesto = importoRichiesto;
		this.importoAmmesso = importoAmmesso;
		this.idUltimaEscussione = ultimoIdEscussione;
		this.soggettiCorrelati = soggettiCorrelati;
	}

	public Riassicurazione_BeneficiarioDomandaVO() {
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Long getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}

	public String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public Long getIdStatoDomanda() {
		return idStatoDomanda;
	}

	public void setIdStatoDomanda(Long idStatoDomanda) {
		this.idStatoDomanda = idStatoDomanda;
	}

	public String getDescBreveStatoDomanda() {
		return descBreveStatoDomanda;
	}

	public void setDescBreveStatoDomanda(String descBreveStatoDomanda) {
		this.descBreveStatoDomanda = descBreveStatoDomanda;
	}

	public String getDescStatoDomanda() {
		return descStatoDomanda;
	}

	public void setDescStatoDomanda(String descStatoDomanda) {
		this.descStatoDomanda = descStatoDomanda;
	}

	public String getTitoloBando() {
		return titoloBando;
	}

	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}

	public BigDecimal getImportoRichiesto() {
		return importoRichiesto;
	}

	public void setImportoRichiesto(BigDecimal importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}

	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}

	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public List<Riassicurazione_SoggettiCorrelatiVO> getSoggettiCorrelati() {
		return soggettiCorrelati;
	}

	public void setSoggettiCorrelati(List<Riassicurazione_SoggettiCorrelatiVO> soggettiCorrelati) {
		this.soggettiCorrelati = soggettiCorrelati;
	}


	public Long getIdUltimaEscussione() {
		return idUltimaEscussione;
	}

	public void setIdUltimaEscussione(Long idUltimaEscussione) {
		this.idUltimaEscussione = idUltimaEscussione;
	}

	@Override
	public String toString() {
		return "Riassicurazione_BeneficiarioDomandaVO [idSoggetto=" + idSoggetto + ", idProgetto=" + idProgetto
				+ ", denominazione=" + denominazione + ", idDomanda=" + idDomanda + ", numeroDomanda=" + numeroDomanda
				+ ", idStatoDomanda=" + idStatoDomanda + ", descBreveStatoDomanda=" + descBreveStatoDomanda
				+ ", descStatoDomanda=" + descStatoDomanda + ", titoloBando=" + titoloBando + ", importoRichiesto="
				+ importoRichiesto + ", importoAmmesso=" + importoAmmesso + ", ultimoIdEscussione=" + idUltimaEscussione
				+ ", soggettiCorrelati=" + soggettiCorrelati + "]";
	}

	
	
	
}
