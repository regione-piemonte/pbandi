package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.gestionepagamenti;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

import java.math.BigDecimal;
import java.sql.Date;

public class PagamentoQuotePartiVO extends GenericVO{

	
	private String causalePagamento;
	private String descBreveModalitaPagamento;
	private String descrizioneModalitaPagamento;
	private String destinatarioPagamento;
	private Date dtEmissioneDocumentoDiSpesa;
	private Date dtPagamento;
	private Date dtValuta;
	private String estremiPagamento;
	private String flagPagamento;
	private BigDecimal idCausalePagamento;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idPagamento;
	private BigDecimal idProgetto;
	private BigDecimal idModalitaPagamento;
	private BigDecimal idSoggetto;
	private String isUsedDichiarazioni;
	private BigDecimal idUtenteAgg;
	private BigDecimal idUtenteIns;
	private BigDecimal importoPagamento;
	private BigDecimal importoQuietanzato;
	private Double importoRendicontabilePagato;
	private BigDecimal importoResiduoUtilizzabile;
	private String isQuietanzato;
	private String numeroDocumentoDiSpesa;
	private String rifPagamento;
  	public BigDecimal getIdCausalePagamento() {
		return idCausalePagamento;
	}

	public String getRifPagamento() {
		return rifPagamento;
	}

	public void setIdCausalePagamento(BigDecimal idCausalePagamento) {
		this.idCausalePagamento = idCausalePagamento;
	}

	public void setRifPagamento(String rifPagamento) {
		this.rifPagamento = rifPagamento;
	}

	private BigDecimal totaleImportiQuietanzati;
  	private BigDecimal totaleImportiPagamenti;

  	
	public Date getDtEmissioneDocumentoDiSpesa() {
		return dtEmissioneDocumentoDiSpesa;
	}

	public void setDtEmissioneDocumentoDiSpesa(Date dtEmissioneDocumentoDiSpesa) {
		this.dtEmissioneDocumentoDiSpesa = dtEmissioneDocumentoDiSpesa;
	}

	public String getNumeroDocumentoDiSpesa() {
		return numeroDocumentoDiSpesa;
	}

	public void setNumeroDocumentoDiSpesa(String numeroDocumentoDiSpesa) {
		this.numeroDocumentoDiSpesa = numeroDocumentoDiSpesa;
	}

	public Date getDtValuta() {
		return dtValuta;
	}

	public void setDtValuta(Date dtValuta) {
		this.dtValuta = dtValuta;
	}

	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public String getDescrizioneModalitaPagamento() {
		return descrizioneModalitaPagamento;
	}

	public void setDescrizioneModalitaPagamento(String descrizioneModalitaPagamento) {
		this.descrizioneModalitaPagamento = descrizioneModalitaPagamento;
	}

	public Double getImportoRendicontabilePagato() {
		return importoRendicontabilePagato;
	}

	public void setImportoRendicontabilePagato(Double importoRendicontabilePagato) {
		this.importoRendicontabilePagato = importoRendicontabilePagato;
	}

	public String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}

	public void setDescBreveModalitaPagamento(String descBreveModalitaPagamento) {
		this.descBreveModalitaPagamento = descBreveModalitaPagamento;
	}

	public BigDecimal getIdModalitaPagamento() {
		return idModalitaPagamento;
	}

	public void setIdModalitaPagamento(BigDecimal idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	public String getDestinatarioPagamento() {
		return destinatarioPagamento;
	}

	public void setDestinatarioPagamento(String destinatarioPagamento) {
		this.destinatarioPagamento = destinatarioPagamento;
	}

	public String getCausalePagamento() {
		return causalePagamento;
	}

	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}

	public String getEstremiPagamento() {
		return estremiPagamento;
	}

	public void setEstremiPagamento(String estremiPagamento) {
		this.estremiPagamento = estremiPagamento;
	}

	public BigDecimal getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setTotaleImportiQuietanzati(BigDecimal totaleImportiQuietanzati) {
		this.totaleImportiQuietanzati = totaleImportiQuietanzati;
	}

	public BigDecimal getTotaleImportiQuietanzati() {
		return totaleImportiQuietanzati;
	}

	public void setTotaleImportiPagamenti(BigDecimal totaleImportiPagamenti) {
		this.totaleImportiPagamenti = totaleImportiPagamenti;
	}

	public BigDecimal getTotaleImportiPagamenti() {
		return totaleImportiPagamenti;
	}

	public String getIsQuietanzato() {
		return isQuietanzato;
	}

	public void setIsQuietanzato(String isQuietanzato) {
		this.isQuietanzato = isQuietanzato;
	}

	public BigDecimal getImportoResiduoUtilizzabile() {
		return importoResiduoUtilizzabile;
	}

	public void setImportoResiduoUtilizzabile(BigDecimal importoResiduoUtilizzabile) {
		this.importoResiduoUtilizzabile = importoResiduoUtilizzabile;
	}

	public String getIsUsedDichiarazioni() {
		return isUsedDichiarazioni;
	}

	public void setIsUsedDichiarazioni(String isUsedDichiarazioni) {
		this.isUsedDichiarazioni = isUsedDichiarazioni;
	}

	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}

	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}

	public String getFlagPagamento() {
		return flagPagamento;
	}

	public void setFlagPagamento(String flagPagamento) {
		this.flagPagamento = flagPagamento;
	}
}
