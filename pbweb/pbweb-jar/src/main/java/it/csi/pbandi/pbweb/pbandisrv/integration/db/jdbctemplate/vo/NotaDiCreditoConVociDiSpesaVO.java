package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class NotaDiCreditoConVociDiSpesaVO extends GenericVO {
	private BigDecimal idProgetto;
	private BigDecimal idDocRiferimento;
	private BigDecimal idNotaDiCredito;
	private String numeroDocumento;
	private Date dtEmissioneDocumento;
	private String descStatoDocumentoSpesa;
	private BigDecimal importoTotaleDocumento;
	private String descVoceDiSpesa;
	private BigDecimal importoVoceDiSpesa;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdDocRiferimento() {
		return idDocRiferimento;
	}
	public void setIdDocRiferimento(BigDecimal idDocRiferimento) {
		this.idDocRiferimento = idDocRiferimento;
	}
	public BigDecimal getIdNotaDiCredito() {
		return idNotaDiCredito;
	}
	public void setIdNotaDiCredito(BigDecimal idNotaDiCredito) {
		this.idNotaDiCredito = idNotaDiCredito;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}
	public void setDtEmissioneDocumento(Date dtEmissioneDocumento) {
		this.dtEmissioneDocumento = dtEmissioneDocumento;
	}
	public String getDescStatoDocumentoSpesa() {
		return descStatoDocumentoSpesa;
	}
	public void setDescStatoDocumentoSpesa(String descStatoDocumentoSpesa) {
		this.descStatoDocumentoSpesa = descStatoDocumentoSpesa;
	}
	public BigDecimal getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}
	public void setImportoTotaleDocumento(BigDecimal importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}
	public String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}
	public void setDescVoceDiSpesa(String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}
	public BigDecimal getImportoVoceDiSpesa() {
		return importoVoceDiSpesa;
	}
	public void setImportoVoceDiSpesa(BigDecimal importoVoceDiSpesa) {
		this.importoVoceDiSpesa = importoVoceDiSpesa;
	}

	
	

}
