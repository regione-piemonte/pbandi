package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class VociDiSpesaPadriFigliContoEconomicoVO extends GenericVO {
	
	private BigDecimal idContoEconomico;
	private BigDecimal idVoceDiSpesa;
	private BigDecimal idVoceDiSpesaPadre;
	private String descVoceDiSpesa;
	private String descVoceDiSpesaPadre;
	private BigDecimal importoAmmessoFinanziamento;
	private BigDecimal idRigoContoEconomico;
	private BigDecimal idTipologiaVoceDiSpesa;
	
	public BigDecimal getIdContoEconomico() {
		return idContoEconomico;
	}
	public void setIdContoEconomico(BigDecimal idContoEconomico) {
		this.idContoEconomico = idContoEconomico;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	public String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}
	public void setDescVoceDiSpesaPadre(String descVoceDiSpesaPadre) {
		this.descVoceDiSpesaPadre = descVoceDiSpesaPadre;
	}
	public BigDecimal getImportoAmmessoFinanziamento() {
		return importoAmmessoFinanziamento;
	}
	public void setImportoAmmessoFinanziamento(
			BigDecimal importoAmmessoFinanziamento) {
		this.importoAmmessoFinanziamento = importoAmmessoFinanziamento;
	}
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}


	public BigDecimal getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(BigDecimal idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}

	@Override
	public String toString() {
		return "VociDiSpesaPadriFigliContoEconomicoVO{" +
				"idContoEconomico=" + idContoEconomico +
				", idVoceDiSpesa=" + idVoceDiSpesa +
				", idVoceDiSpesaPadre=" + idVoceDiSpesaPadre +
				", descVoceDiSpesa='" + descVoceDiSpesa + '\'' +
				", descVoceDiSpesaPadre='" + descVoceDiSpesaPadre + '\'' +
				", importoAmmessoFinanziamento=" + importoAmmessoFinanziamento +
				", idRigoContoEconomico=" + idRigoContoEconomico +
				", idTipologiaVoceDiSpesa=" + idTipologiaVoceDiSpesa +
				'}';
	}
}
