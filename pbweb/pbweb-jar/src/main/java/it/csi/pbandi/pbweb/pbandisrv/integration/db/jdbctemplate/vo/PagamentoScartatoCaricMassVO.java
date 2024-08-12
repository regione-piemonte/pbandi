package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTCaricaMassDocSpesaVO;


public class PagamentoScartatoCaricMassVO extends GenericVO {
	
	
	private BigDecimal idCaricaMassDocSpesa;
	private BigDecimal idScartiCaricaMasPag;
    private String descDocumento;
    private String descPagamento;
    private String codiceErrore;
    private String erroreWarning;
	public BigDecimal getIdCaricaMassDocSpesa() {
		return idCaricaMassDocSpesa;
	}
	public void setIdCaricaMassDocSpesa(BigDecimal idCaricaMassDocSpesa) {
		this.idCaricaMassDocSpesa = idCaricaMassDocSpesa;
	}
	public String getCodiceErrore() {
		return codiceErrore;
	}
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	public String getDescDocumento() {
		return descDocumento;
	}
	public void setDescDocumento(String descDocumento) {
		this.descDocumento = descDocumento;
	}
	public String getDescPagamento() {
		return descPagamento;
	}
	public void setDescPagamento(String descPagamento) {
		this.descPagamento = descPagamento;
	}
	public String getErroreWarning() {
		return erroreWarning;
	}
	public void setErroreWarning(String erroreWarning) {
		this.erroreWarning = erroreWarning;
	}
	private Date dtValidazione;
	public Date getDtValidazione() {
		return dtValidazione;
	}
	public void setDtValidazione(Date dtValidazione) {
		this.dtValidazione = dtValidazione;
	}
	public BigDecimal getIdScartiCaricaMasPag() {
		return idScartiCaricaMasPag;
	}
	public void setIdScartiCaricaMasPag(BigDecimal idScartiCaricaMasPag) {
		this.idScartiCaricaMasPag = idScartiCaricaMasPag;
	}


}
