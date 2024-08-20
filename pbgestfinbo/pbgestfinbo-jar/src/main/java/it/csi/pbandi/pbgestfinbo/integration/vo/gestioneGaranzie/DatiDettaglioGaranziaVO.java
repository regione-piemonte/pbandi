/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.gestioneGaranzie;

import java.math.BigDecimal;
import java.util.Date;

public class DatiDettaglioGaranziaVO {
	
	private Long idProgetto; 
	private Integer idModalitaAgevolazione;
	private Integer idModalitaAgevolazioneRif;
	private String descBreveModalitaAgevolazione;
	private String descModalitaAgevolazione;
	private BigDecimal importoAmmesso;
	private BigDecimal totaleFondo;
	private BigDecimal totaleBanca;
	private Date dataConcessione;
	private Date dataErogazione;
	private BigDecimal importoDebitoResiduo; // Da amministrativo contabile
	private BigDecimal importoEscusso;
	private Long idEscussione;
	private Long idTipoEscussione;
	private Long idStatoEscussione;
	private String statoCredito;
	private String revocaBancaria;
	private String azioniRecuperoBanca;
	private String saldoStralcio;
	
	
	public DatiDettaglioGaranziaVO(Long idProgetto, Integer idModalitaAgevolazione, Integer idModalitaAgevolazioneRif,
			String descBreveModalitaAgevolazione, String descModalitaAgevolazione, BigDecimal importoAmmesso,
			BigDecimal totaleFondo, BigDecimal totaleBanca, Date dataConcessione, Date dataErogazione,
			BigDecimal importoDebitoResiduo, BigDecimal importoEscusso, Long idEscussione, Long idTipoEscussione,
			Long idStatoEscussione, String statoCredito, String revocaBancaria, String azioniRecuperoBanca,
			String saldoStralcio) {
		this.idProgetto = idProgetto;
		this.idModalitaAgevolazione = idModalitaAgevolazione;
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
		this.descBreveModalitaAgevolazione = descBreveModalitaAgevolazione;
		this.descModalitaAgevolazione = descModalitaAgevolazione;
		this.importoAmmesso = importoAmmesso;
		this.totaleFondo = totaleFondo;
		this.totaleBanca = totaleBanca;
		this.dataConcessione = dataConcessione;
		this.dataErogazione = dataErogazione;
		this.importoDebitoResiduo = importoDebitoResiduo;
		this.importoEscusso = importoEscusso;
		this.idEscussione = idEscussione;
		this.idTipoEscussione = idTipoEscussione;
		this.idStatoEscussione = idStatoEscussione;
		this.statoCredito = statoCredito;
		this.revocaBancaria = revocaBancaria;
		this.azioniRecuperoBanca = azioniRecuperoBanca;
		this.saldoStralcio = saldoStralcio;
	}


	public DatiDettaglioGaranziaVO() {
	}


	public Long getIdProgetto() {
		return idProgetto;
	}


	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}


	public Integer getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}


	public void setIdModalitaAgevolazione(Integer idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}


	public Integer getIdModalitaAgevolazioneRif() {
		return idModalitaAgevolazioneRif;
	}


	public void setIdModalitaAgevolazioneRif(Integer idModalitaAgevolazioneRif) {
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
	}


	public String getDescBreveModalitaAgevolazione() {
		return descBreveModalitaAgevolazione;
	}


	public void setDescBreveModalitaAgevolazione(String descBreveModalitaAgevolazione) {
		this.descBreveModalitaAgevolazione = descBreveModalitaAgevolazione;
	}


	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}


	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}


	public BigDecimal getImportoAmmesso() {
		return importoAmmesso;
	}


	public void setImportoAmmesso(BigDecimal importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}


	public BigDecimal getTotaleFondo() {
		return totaleFondo;
	}


	public void setTotaleFondo(BigDecimal totaleFondo) {
		this.totaleFondo = totaleFondo;
	}


	public BigDecimal getTotaleBanca() {
		return totaleBanca;
	}


	public void setTotaleBanca(BigDecimal totaleBanca) {
		this.totaleBanca = totaleBanca;
	}


	public Date getDataConcessione() {
		return dataConcessione;
	}


	public void setDataConcessione(Date dataConcessione) {
		this.dataConcessione = dataConcessione;
	}


	public Date getDataErogazione() {
		return dataErogazione;
	}


	public void setDataErogazione(Date dataErogazione) {
		this.dataErogazione = dataErogazione;
	}


	public BigDecimal getImportoDebitoResiduo() {
		return importoDebitoResiduo;
	}


	public void setImportoDebitoResiduo(BigDecimal importoDebitoResiduo) {
		this.importoDebitoResiduo = importoDebitoResiduo;
	}


	public BigDecimal getImportoEscusso() {
		return importoEscusso;
	}


	public void setImportoEscusso(BigDecimal importoEscusso) {
		this.importoEscusso = importoEscusso;
	}


	public Long getIdEscussione() {
		return idEscussione;
	}


	public void setIdEscussione(Long idEscussione) {
		this.idEscussione = idEscussione;
	}


	public Long getIdTipoEscussione() {
		return idTipoEscussione;
	}


	public void setIdTipoEscussione(Long idTipoEscussione) {
		this.idTipoEscussione = idTipoEscussione;
	}


	public Long getIdStatoEscussione() {
		return idStatoEscussione;
	}


	public void setIdStatoEscussione(Long idStatoEscussione) {
		this.idStatoEscussione = idStatoEscussione;
	}


	public String getStatoCredito() {
		return statoCredito;
	}


	public void setStatoCredito(String statoCredito) {
		this.statoCredito = statoCredito;
	}


	public String getRevocaBancaria() {
		return revocaBancaria;
	}


	public void setRevocaBancaria(String revocaBancaria) {
		this.revocaBancaria = revocaBancaria;
	}


	public String getAzioniRecuperoBanca() {
		return azioniRecuperoBanca;
	}


	public void setAzioniRecuperoBanca(String azioniRecuperoBanca) {
		this.azioniRecuperoBanca = azioniRecuperoBanca;
	}


	public String getSaldoStralcio() {
		return saldoStralcio;
	}


	public void setSaldoStralcio(String saldoStralcio) {
		this.saldoStralcio = saldoStralcio;
	}


	@Override
	public String toString() {
		return "DatiDettaglioGaranziaVO [idProgetto=" + idProgetto + ", idModalitaAgevolazione="
				+ idModalitaAgevolazione + ", idModalitaAgevolazioneRif=" + idModalitaAgevolazioneRif
				+ ", descBreveModalitaAgevolazione=" + descBreveModalitaAgevolazione + ", descModalitaAgevolazione="
				+ descModalitaAgevolazione + ", importoAmmesso=" + importoAmmesso + ", totaleFondo=" + totaleFondo
				+ ", totaleBanca=" + totaleBanca + ", dataConcessione=" + dataConcessione + ", dataErogazione="
				+ dataErogazione + ", importoDebitoResiduo=" + importoDebitoResiduo + ", importoEscusso="
				+ importoEscusso + ", idEscussione=" + idEscussione + ", idTipoEscussione=" + idTipoEscussione
				+ ", idStatoEscussione=" + idStatoEscussione + ", statoCredito=" + statoCredito + ", revocaBancaria="
				+ revocaBancaria + ", azioniRecuperoBanca=" + azioniRecuperoBanca + ", saldoStralcio=" + saldoStralcio
				+ "]";
	}
	
	
	
	
	
}
