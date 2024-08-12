package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.rettifica;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class RettificaVoceDiSpesaPagamentoVO extends GenericVO {
	
	private BigDecimal idRettificaDiSpesa;
	private BigDecimal progrPagQuotParteDocSp;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idPagamento;
	private BigDecimal idQuotaParteDocSpesa;
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idProgetto;
	private Date dtValuta;
	private Date dtPagamento;
	private String descModalitaPagamento;
	private BigDecimal importoPagamento;
	private String descVoceDiSpesa;
	private Date dtRettifica;
	private BigDecimal importoRettifica;
	private String riferimento;
	private BigDecimal idUtenteIns;
	private String codiceFiscaleSoggetto;
	
	
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	
	
	public BigDecimal getIdRettificaDiSpesa() {
		return idRettificaDiSpesa;
	}
	public void setIdRettificaDiSpesa(BigDecimal idRettificaDiSpesa) {
		this.idRettificaDiSpesa = idRettificaDiSpesa;
	}
	public BigDecimal getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}
	public void setProgrPagQuotParteDocSp(BigDecimal progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(BigDecimal idPagamento) {
		this.idPagamento = idPagamento;
	}
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	public Date getDtRettifica() {
		return dtRettifica;
	}
	public void setDtRettifica(Date dtRettifica) {
		this.dtRettifica = dtRettifica;
	}
	public BigDecimal getImportoRettifica() {
		return importoRettifica;
	}
	public void setImportoRettifica(BigDecimal importoRettifica) {
		this.importoRettifica = importoRettifica;
	}
	public String getRiferimento() {
		return riferimento;
	}
	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public Date getDtValuta() {
		return dtValuta;
	}
	public void setDtValuta(Date dtValuta) {
		this.dtValuta = dtValuta;
	}
	public Date getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public String getDescModalitaPagamento() {
		return descModalitaPagamento;
	}
	public void setDescModalitaPagamento(String descModalitaPagamento) {
		this.descModalitaPagamento = descModalitaPagamento;
	}
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

}
