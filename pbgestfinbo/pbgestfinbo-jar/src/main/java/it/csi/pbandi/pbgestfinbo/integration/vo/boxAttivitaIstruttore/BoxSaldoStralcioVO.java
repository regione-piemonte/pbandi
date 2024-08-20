/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.boxAttivitaIstruttore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BoxSaldoStralcioVO {
	
	private Long idSaldoStralcio;
	private Long idProgetto;
	private Integer idModalitaAgevolazione;
	
	private Integer idAttivitaSaldoStralcio;
	private String descBreveAttivitaSaldoStralcio;
	private String descAttivitaSaldoStralcio;
	private Date dataProposta;
	private Date dataAccettazione;
	private BigDecimal importoDebitore;
	private BigDecimal importoConfidi;
	private Integer idAttivitaEsito;
	private String descBreveAttivitaEsito;
	private String descAttivitaEsito;
	private Date dataEsito;
	private Date dataPagamentoDebitore;
	private Date dataPagamentoConfidi;
	private Integer idAttivitaRecupero;
	private String descBreveAttivitaRecupero;
	private String descAttivitaRecupero;
	private BigDecimal importoRecuperato;
	private BigDecimal importoPerdita;
	private String note;
	private Boolean flagAgevolazione;
	private BigDecimal importoDisimpegno;

	private Date dataInizioValidita;
	private Long idUtenteIns;
	private String nomeUtenteIns;
	private Date dataAggiornamento;
	private Long idUtenteAgg;
	private String nomeUtenteAgg;
	
	
	public BoxSaldoStralcioVO(Long idSaldoStralcio, Long idProgetto, Integer idModalitaAgevolazione,
			Integer idAttivitaSaldoStralcio, String descBreveAttivitaSaldoStralcio, String descAttivitaSaldoStralcio,
			Date dataProposta, Date dataAccettazione, BigDecimal importoDebitore, BigDecimal importoConfindi,
			Integer idAttivitaEsito, String descBreveAttivitaEsito, String descAttivitaEsito, Date dataEsito,
			Date dataPagamentoDebitore, Date dataPagamentoConfidi, Integer idAttivitaRecupero,
			String descBreveAttivitaRecupero, String descAttivitaRecupero, BigDecimal importoRecuperato,
			BigDecimal importoPerdita, String note, Boolean flagAgevolazione, BigDecimal importoDisimpegno,
			Date dataInizioValidita, Long idUtenteIns, String nomeUtenteIns, Date dataAggiornamento, Long idUtenteAgg,
			String nomeUtenteAgg) {
		this.idSaldoStralcio = idSaldoStralcio;
		this.idProgetto = idProgetto;
		this.idModalitaAgevolazione = idModalitaAgevolazione;
		this.idAttivitaSaldoStralcio = idAttivitaSaldoStralcio;
		this.descBreveAttivitaSaldoStralcio = descBreveAttivitaSaldoStralcio;
		this.descAttivitaSaldoStralcio = descAttivitaSaldoStralcio;
		this.dataProposta = dataProposta;
		this.dataAccettazione = dataAccettazione;
		this.importoDebitore = importoDebitore;
		this.importoConfidi = importoConfindi;
		this.idAttivitaEsito = idAttivitaEsito;
		this.descBreveAttivitaEsito = descBreveAttivitaEsito;
		this.descAttivitaEsito = descAttivitaEsito;
		this.dataEsito = dataEsito;
		this.dataPagamentoDebitore = dataPagamentoDebitore;
		this.dataPagamentoConfidi = dataPagamentoConfidi;
		this.idAttivitaRecupero = idAttivitaRecupero;
		this.descBreveAttivitaRecupero = descBreveAttivitaRecupero;
		this.descAttivitaRecupero = descAttivitaRecupero;
		this.importoRecuperato = importoRecuperato;
		this.importoPerdita = importoPerdita;
		this.note = note;
		this.flagAgevolazione = flagAgevolazione;
		this.importoDisimpegno = importoDisimpegno;
		this.dataInizioValidita = dataInizioValidita;
		this.idUtenteIns = idUtenteIns;
		this.nomeUtenteIns = nomeUtenteIns;
		this.dataAggiornamento = dataAggiornamento;
		this.idUtenteAgg = idUtenteAgg;
		this.nomeUtenteAgg = nomeUtenteAgg;
	}


	public BoxSaldoStralcioVO() {
	}


	public Long getIdSaldoStralcio() {
		return idSaldoStralcio;
	}


	public void setIdSaldoStralcio(Long idSaldoStralcio) {
		this.idSaldoStralcio = idSaldoStralcio;
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


	public Integer getIdAttivitaSaldoStralcio() {
		return idAttivitaSaldoStralcio;
	}


	public void setIdAttivitaSaldoStralcio(Integer idAttivitaSaldoStralcio) {
		this.idAttivitaSaldoStralcio = idAttivitaSaldoStralcio;
	}


	public String getDescBreveAttivitaSaldoStralcio() {
		return descBreveAttivitaSaldoStralcio;
	}


	public void setDescBreveAttivitaSaldoStralcio(String descBreveAttivitaSaldoStralcio) {
		this.descBreveAttivitaSaldoStralcio = descBreveAttivitaSaldoStralcio;
	}


	public String getDescAttivitaSaldoStralcio() {
		return descAttivitaSaldoStralcio;
	}


	public void setDescAttivitaSaldoStralcio(String descAttivitaSaldoStralcio) {
		this.descAttivitaSaldoStralcio = descAttivitaSaldoStralcio;
	}


	public Date getDataProposta() {
		return dataProposta;
	}


	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}


	public Date getDataAccettazione() {
		return dataAccettazione;
	}


	public void setDataAccettazione(Date dataAccettazione) {
		this.dataAccettazione = dataAccettazione;
	}


	public BigDecimal getImportoDebitore() {
		return importoDebitore;
	}


	public void setImportoDebitore(BigDecimal importoDebitore) {
		this.importoDebitore = importoDebitore;
	}


	public BigDecimal getImportoConfidi() {
		return importoConfidi;
	}


	public void setImportoConfidi(BigDecimal importoConfindi) {
		this.importoConfidi = importoConfindi;
	}


	public Integer getIdAttivitaEsito() {
		return idAttivitaEsito;
	}


	public void setIdAttivitaEsito(Integer idAttivitaEsito) {
		this.idAttivitaEsito = idAttivitaEsito;
	}


	public String getDescBreveAttivitaEsito() {
		return descBreveAttivitaEsito;
	}


	public void setDescBreveAttivitaEsito(String descBreveAttivitaEsito) {
		this.descBreveAttivitaEsito = descBreveAttivitaEsito;
	}


	public String getDescAttivitaEsito() {
		return descAttivitaEsito;
	}


	public void setDescAttivitaEsito(String descAttivitaEsito) {
		this.descAttivitaEsito = descAttivitaEsito;
	}


	public Date getDataEsito() {
		return dataEsito;
	}


	public void setDataEsito(Date dataEsito) {
		this.dataEsito = dataEsito;
	}


	public Date getDataPagamentoDebitore() {
		return dataPagamentoDebitore;
	}


	public void setDataPagamentoDebitore(Date dataPagamentoDebitore) {
		this.dataPagamentoDebitore = dataPagamentoDebitore;
	}


	public Date getDataPagamentoConfidi() {
		return dataPagamentoConfidi;
	}


	public void setDataPagamentoConfidi(Date dataPagamentoConfidi) {
		this.dataPagamentoConfidi = dataPagamentoConfidi;
	}


	public Integer getIdAttivitaRecupero() {
		return idAttivitaRecupero;
	}


	public void setIdAttivitaRecupero(Integer idAttivitaRecupero) {
		this.idAttivitaRecupero = idAttivitaRecupero;
	}


	public String getDescBreveAttivitaRecupero() {
		return descBreveAttivitaRecupero;
	}


	public void setDescBreveAttivitaRecupero(String descBreveAttivitaRecupero) {
		this.descBreveAttivitaRecupero = descBreveAttivitaRecupero;
	}


	public String getDescAttivitaRecupero() {
		return descAttivitaRecupero;
	}


	public void setDescAttivitaRecupero(String descAttivitaRecupero) {
		this.descAttivitaRecupero = descAttivitaRecupero;
	}


	public BigDecimal getImportoRecuperato() {
		return importoRecuperato;
	}


	public void setImportoRecuperato(BigDecimal importoRecuperato) {
		this.importoRecuperato = importoRecuperato;
	}


	public BigDecimal getImportoPerdita() {
		return importoPerdita;
	}


	public void setImportoPerdita(BigDecimal importoPerdita) {
		this.importoPerdita = importoPerdita;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Boolean getFlagAgevolazione() {
		return flagAgevolazione;
	}


	public void setFlagAgevolazione(Boolean flagAgevolazione) {
		this.flagAgevolazione = flagAgevolazione;
	}


	public BigDecimal getImportoDisimpegno() {
		return importoDisimpegno;
	}


	public void setImportoDisimpegno(BigDecimal importoDisimpegno) {
		this.importoDisimpegno = importoDisimpegno;
	}


	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}


	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}


	public Long getIdUtenteIns() {
		return idUtenteIns;
	}


	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}


	public String getNomeUtenteIns() {
		return nomeUtenteIns;
	}


	public void setNomeUtenteIns(String nomeUtenteIns) {
		this.nomeUtenteIns = nomeUtenteIns;
	}


	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}


	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}


	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}


	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}


	public String getNomeUtenteAgg() {
		return nomeUtenteAgg;
	}


	public void setNomeUtenteAgg(String nomeUtenteAgg) {
		this.nomeUtenteAgg = nomeUtenteAgg;
	}


	@Override
	public String toString() {
		return "BoxSaldoStralcioVO [idSaldoStralcio=" + idSaldoStralcio + ", idProgetto=" + idProgetto
				+ ", idModalitaAgevolazione=" + idModalitaAgevolazione + ", idAttivitaSaldoStralcio="
				+ idAttivitaSaldoStralcio + ", descBreveAttivitaSaldoStralcio=" + descBreveAttivitaSaldoStralcio
				+ ", descAttivitaSaldoStralcio=" + descAttivitaSaldoStralcio + ", dataProposta=" + dataProposta
				+ ", dataAccettazione=" + dataAccettazione + ", importoDebitore=" + importoDebitore
				+ ", importoConfindi=" + importoConfidi + ", idAttivitaEsito=" + idAttivitaEsito
				+ ", descBreveAttivitaEsito=" + descBreveAttivitaEsito + ", descAttivitaEsito=" + descAttivitaEsito
				+ ", dataEsito=" + dataEsito + ", dataPagamentoDebitore=" + dataPagamentoDebitore
				+ ", dataPagamentoConfidi=" + dataPagamentoConfidi + ", idAttivitaRecupero=" + idAttivitaRecupero
				+ ", descBreveAttivitaRecupero=" + descBreveAttivitaRecupero + ", descAttivitaRecupero="
				+ descAttivitaRecupero + ", importoRecuperato=" + importoRecuperato + ", importoPerdita="
				+ importoPerdita + ", note=" + note + ", flagAgevolazione=" + flagAgevolazione + ", importoDisimpegno="
				+ importoDisimpegno + ", dataInizioValidita=" + dataInizioValidita + ", idUtenteIns=" + idUtenteIns
				+ ", nomeUtenteIns=" + nomeUtenteIns + ", dataAggiornamento=" + dataAggiornamento + ", idUtenteAgg="
				+ idUtenteAgg + ", nomeUtenteAgg=" + nomeUtenteAgg + "]";
	}
	
	
	
	
	

}
