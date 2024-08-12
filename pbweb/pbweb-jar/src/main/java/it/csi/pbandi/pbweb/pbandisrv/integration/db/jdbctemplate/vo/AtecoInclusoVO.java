package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class AtecoInclusoVO extends GenericVO {
	
	private BigDecimal idBando;
	private BigDecimal idAteco;
	private String codAteco;	
	private String codAtecoNorm;
	private String descAteco;
	private String codSezione;
	private BigDecimal codLivello;
	
	public BigDecimal getIdBando() {
		return idBando;
	}
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	public BigDecimal getIdAteco() {
		return idAteco;
	}
	public void setIdAteco(BigDecimal idAteco) {
		this.idAteco = idAteco;
	}
	public String getCodAteco() {
		return codAteco;
	}
	public void setCodAteco(String codAteco) {
		this.codAteco = codAteco;
	}
	public String getCodAtecoNorm() {
		return codAtecoNorm;
	}
	public void setCodAtecoNorm(String codAtecoNorm) {
		this.codAtecoNorm = codAtecoNorm;
	}
	public String getDescAteco() {
		return descAteco;
	}
	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}
	public String getCodSezione() {
		return codSezione;
	}
	public void setCodSezione(String codSezione) {
		this.codSezione = codSezione;
	}
	public BigDecimal getCodLivello() {
		return codLivello;
	}
	public void setCodLivello(BigDecimal codLivello) {
		this.codLivello = codLivello;
	}

}
