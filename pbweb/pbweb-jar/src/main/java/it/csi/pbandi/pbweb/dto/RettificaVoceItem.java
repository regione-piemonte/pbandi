package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class RettificaVoceItem implements java.io.Serializable {

	private java.lang.Long progrPagQuotParteDocSp = null;
	private java.lang.String descVoceDiSpesa = null;
	private java.lang.String dtRettifica = null;
	private java.lang.Double importoRettifica = null;
	private java.lang.String riferimento = null;
	private java.lang.String codiceFiscaleSoggetto = null;
	private java.lang.Boolean rigaVoce = null;
	private java.lang.String dtPagamento = null;
	private java.lang.String dtValuta = null;
	private java.lang.Double importoPagamento = null;
	private java.lang.String modalitaPagamento = null;
	private java.lang.Boolean dtPagamentoVisible = null;
	private java.lang.Boolean dtValutaVisible = null;

	public RettificaVoceItem() {}

	public java.lang.Long getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}

	public void setProgrPagQuotParteDocSp(java.lang.Long progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}

	public java.lang.String getDescVoceDiSpesa() {
		return descVoceDiSpesa;
	}

	public void setDescVoceDiSpesa(java.lang.String descVoceDiSpesa) {
		this.descVoceDiSpesa = descVoceDiSpesa;
	}

	public java.lang.String getDtRettifica() {
		return dtRettifica;
	}

	public void setDtRettifica(java.lang.String dtRettifica) {
		this.dtRettifica = dtRettifica;
	}

	public java.lang.Double getImportoRettifica() {
		return importoRettifica;
	}

	public void setImportoRettifica(java.lang.Double importoRettifica) {
		this.importoRettifica = importoRettifica;
	}

	public java.lang.String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(java.lang.String riferimento) {
		this.riferimento = riferimento;
	}

	public java.lang.String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(java.lang.String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public java.lang.Boolean getRigaVoce() {
		return rigaVoce;
	}

	public void setRigaVoce(java.lang.Boolean rigaVoce) {
		this.rigaVoce = rigaVoce;
	}

	public java.lang.String getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(java.lang.String dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public java.lang.String getDtValuta() {
		return dtValuta;
	}

	public void setDtValuta(java.lang.String dtValuta) {
		this.dtValuta = dtValuta;
	}

	public java.lang.Double getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(java.lang.Double importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	public java.lang.String getModalitaPagamento() {
		return modalitaPagamento;
	}

	public void setModalitaPagamento(java.lang.String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}

	public java.lang.Boolean getDtPagamentoVisible() {
		return dtPagamentoVisible;
	}

	public void setDtPagamentoVisible(java.lang.Boolean dtPagamentoVisible) {
		this.dtPagamentoVisible = dtPagamentoVisible;
	}

	public java.lang.Boolean getDtValutaVisible() {
		return dtValutaVisible;
	}

	public void setDtValutaVisible(java.lang.Boolean dtValutaVisible) {
		this.dtValutaVisible = dtValutaVisible;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
