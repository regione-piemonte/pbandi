package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class VoceDiSpesaDocumentoVO extends GenericVO {
	
	private BigDecimal idDocumentoDiSpesa;
	private BigDecimal idProgetto;
    private Date dtEmissioneDocumento;
    private BigDecimal idTipoDocumentoSpesa;
    private String descBreveTipoDocSpesa;
    private String descTipoDocumentoSpesa;
    private BigDecimal idRigoContoEconomico;
    private BigDecimal idVoceDiSpesa;
    private BigDecimal idVoceDiSpesaPadre;
    private BigDecimal idQuotaParteDocSpesa;
    private String codTipoVoceDiSpesa;
    private String descVoceDiSpesa;
    private BigDecimal importoVoceDiSpesa;
    private BigDecimal idDocRiferimento;
    private BigDecimal importoAmmessoFinanziamento;
    private BigDecimal oreLavorate;
    private String descVoceDiSpesaPadre;
    private String descVoceDiSpesaCompleta;
    private BigDecimal idTipologiaVoceDiSpesa;
    
	public BigDecimal getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(BigDecimal idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	
	
	public Date getDtEmissioneDocumento() {
		return dtEmissioneDocumento;
	}
	public void setDtEmissioneDocumento(Date dtEmissioneDocumento) {
		this.dtEmissioneDocumento = dtEmissioneDocumento;
	}
	
	public String getCodTipoVoceDiSpesa() {
		return codTipoVoceDiSpesa;
	}
	public void setCodTipoVoceDiSpesa(String codTipoVoceDiSpesa) {
		this.codTipoVoceDiSpesa = codTipoVoceDiSpesa;
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
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	public BigDecimal getIdVoceDiSpesa() {
		return idVoceDiSpesa;
	}
	public void setIdVoceDiSpesa(BigDecimal idVoceDiSpesa) {
		this.idVoceDiSpesa = idVoceDiSpesa;
	}
	public BigDecimal getIdQuotaParteDocSpesa() {
		return idQuotaParteDocSpesa;
	}
	public void setIdQuotaParteDocSpesa(BigDecimal idQuotaParteDocSpesa) {
		this.idQuotaParteDocSpesa = idQuotaParteDocSpesa;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public void setIdDocRiferimento(BigDecimal idDocRiferimento) {
		this.idDocRiferimento = idDocRiferimento;
	}
	public BigDecimal getIdDocRiferimento() {
		return idDocRiferimento;
	}
	public BigDecimal getImportoAmmessoFinanziamento() {
		return importoAmmessoFinanziamento;
	}
	public void setImportoAmmessoFinanziamento(
			BigDecimal importoAmmessoFinanziamento) {
		this.importoAmmessoFinanziamento = importoAmmessoFinanziamento;
	}
	public BigDecimal getOreLavorate() {
		return oreLavorate;
	}
	public void setOreLavorate(BigDecimal oreLavorate) {
		this.oreLavorate = oreLavorate;
	}
	public String getDescVoceDiSpesaPadre() {
		return descVoceDiSpesaPadre;
	}
	public void setDescVoceDiSpesaPadre(String descVoceDiSpesaPadre) {
		this.descVoceDiSpesaPadre = descVoceDiSpesaPadre;
	}
	public String getDescVoceDiSpesaCompleta() {
		return descVoceDiSpesaCompleta;
	}
	public void setDescVoceDiSpesaCompleta(String descVoceDiSpesaCompleta) {
		this.descVoceDiSpesaCompleta = descVoceDiSpesaCompleta;
	}
	public BigDecimal getIdVoceDiSpesaPadre() {
		return idVoceDiSpesaPadre;
	}
	public void setIdVoceDiSpesaPadre(BigDecimal idVoceDiSpesaPadre) {
		this.idVoceDiSpesaPadre = idVoceDiSpesaPadre;
	}
	public BigDecimal getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}
	public void setIdRigoContoEconomico(BigDecimal idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}
	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}
	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}
	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}


	public BigDecimal getIdTipologiaVoceDiSpesa() {
		return idTipologiaVoceDiSpesa;
	}

	public void setIdTipologiaVoceDiSpesa(BigDecimal idTipologiaVoceDiSpesa) {
		this.idTipologiaVoceDiSpesa = idTipologiaVoceDiSpesa;
	}
}
