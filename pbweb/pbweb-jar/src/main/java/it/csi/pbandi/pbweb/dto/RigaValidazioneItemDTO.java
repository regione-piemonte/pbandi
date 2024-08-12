package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class RigaValidazioneItemDTO implements java.io.Serializable {

	private Boolean rigaPagamento = false;
	private java.lang.String modalitaPagamento = null;
	private java.lang.String dataPagamento = null;
	private java.lang.Double importoTotalePagamento = null;
	private java.lang.String descrizioneVoceDiSpesa = null;
	private java.lang.Double importoAssociatoVoceDiSpesa = null;
	private java.lang.String importoValidato = null;
	private java.lang.Double importoValidatoVoceDiSpesa = null;
	private java.lang.Long idPagamento = null;
	private java.lang.String statoPagamento = null;
	private java.lang.Long idQuotaParte = null;
	private java.lang.Long idDocumentoDiSpesa = null;
	private Boolean rigaModificabile = false;
	private Boolean dataPagamentoVisible = false;
	private Boolean dataValutaVisible = false;
	private java.lang.String dataValuta = null;
	private java.lang.Long idDichiarazioneDiSpesa = null;
	private java.lang.Long idRigoContoEconomico = null;
	private java.lang.Long progrPagQuotParteDocSp = null;
	private java.lang.Double importoTotaleRettifica = null;
	private java.lang.Double importoValidatoPrecedenteVoceDiSpesa = null;
	private java.lang.String riferimentoRettifica = null;
	private java.lang.String linkModifica = null;
	private java.lang.String linkNote = null;
	private java.lang.String linkRettifiche = null;
	private java.lang.String note = null;
	private java.lang.Double oreLavorate = null;
	private java.lang.Double importoResiduoAmmesso = null;
	private java.lang.Double importoAmmesso = null;

	public Boolean getRigaPagamento() {
		return rigaPagamento;
	}

	public void setRigaPagamento(Boolean rigaPagamento) {
		this.rigaPagamento = rigaPagamento;
	}

	public java.lang.String getModalitaPagamento() {
		return modalitaPagamento;
	}

	public void setModalitaPagamento(java.lang.String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}

	public java.lang.String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(java.lang.String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public java.lang.Double getImportoTotalePagamento() {
		return importoTotalePagamento;
	}

	public void setImportoTotalePagamento(java.lang.Double importoTotalePagamento) {
		this.importoTotalePagamento = importoTotalePagamento;
	}

	public java.lang.String getDescrizioneVoceDiSpesa() {
		return descrizioneVoceDiSpesa;
	}

	public void setDescrizioneVoceDiSpesa(java.lang.String descrizioneVoceDiSpesa) {
		this.descrizioneVoceDiSpesa = descrizioneVoceDiSpesa;
	}

	public java.lang.Double getImportoAssociatoVoceDiSpesa() {
		return importoAssociatoVoceDiSpesa;
	}

	public void setImportoAssociatoVoceDiSpesa(java.lang.Double importoAssociatoVoceDiSpesa) {
		this.importoAssociatoVoceDiSpesa = importoAssociatoVoceDiSpesa;
	}

	public java.lang.String getImportoValidato() {
		return importoValidato;
	}

	public void setImportoValidato(java.lang.String importoValidato) {
		this.importoValidato = importoValidato;
	}

	public java.lang.Double getImportoValidatoVoceDiSpesa() {
		return importoValidatoVoceDiSpesa;
	}

	public void setImportoValidatoVoceDiSpesa(java.lang.Double importoValidatoVoceDiSpesa) {
		this.importoValidatoVoceDiSpesa = importoValidatoVoceDiSpesa;
	}

	public java.lang.Long getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(java.lang.Long idPagamento) {
		this.idPagamento = idPagamento;
	}

	public java.lang.String getStatoPagamento() {
		return statoPagamento;
	}

	public void setStatoPagamento(java.lang.String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}

	public java.lang.Long getIdQuotaParte() {
		return idQuotaParte;
	}

	public void setIdQuotaParte(java.lang.Long idQuotaParte) {
		this.idQuotaParte = idQuotaParte;
	}

	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(java.lang.Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public Boolean getRigaModificabile() {
		return rigaModificabile;
	}

	public void setRigaModificabile(Boolean rigaModificabile) {
		this.rigaModificabile = rigaModificabile;
	}

	public Boolean getDataPagamentoVisible() {
		return dataPagamentoVisible;
	}

	public void setDataPagamentoVisible(Boolean dataPagamentoVisible) {
		this.dataPagamentoVisible = dataPagamentoVisible;
	}

	public Boolean getDataValutaVisible() {
		return dataValutaVisible;
	}

	public void setDataValutaVisible(Boolean dataValutaVisible) {
		this.dataValutaVisible = dataValutaVisible;
	}

	public java.lang.String getDataValuta() {
		return dataValuta;
	}

	public void setDataValuta(java.lang.String dataValuta) {
		this.dataValuta = dataValuta;
	}

	public java.lang.Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}

	public void setIdDichiarazioneDiSpesa(java.lang.Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}

	public java.lang.Long getIdRigoContoEconomico() {
		return idRigoContoEconomico;
	}

	public void setIdRigoContoEconomico(java.lang.Long idRigoContoEconomico) {
		this.idRigoContoEconomico = idRigoContoEconomico;
	}

	public java.lang.Long getProgrPagQuotParteDocSp() {
		return progrPagQuotParteDocSp;
	}

	public void setProgrPagQuotParteDocSp(java.lang.Long progrPagQuotParteDocSp) {
		this.progrPagQuotParteDocSp = progrPagQuotParteDocSp;
	}

	public java.lang.Double getImportoTotaleRettifica() {
		return importoTotaleRettifica;
	}

	public void setImportoTotaleRettifica(java.lang.Double importoTotaleRettifica) {
		this.importoTotaleRettifica = importoTotaleRettifica;
	}

	public java.lang.Double getImportoValidatoPrecedenteVoceDiSpesa() {
		return importoValidatoPrecedenteVoceDiSpesa;
	}

	public void setImportoValidatoPrecedenteVoceDiSpesa(java.lang.Double importoValidatoPrecedenteVoceDiSpesa) {
		this.importoValidatoPrecedenteVoceDiSpesa = importoValidatoPrecedenteVoceDiSpesa;
	}

	public java.lang.String getRiferimentoRettifica() {
		return riferimentoRettifica;
	}

	public void setRiferimentoRettifica(java.lang.String riferimentoRettifica) {
		this.riferimentoRettifica = riferimentoRettifica;
	}

	public java.lang.String getLinkModifica() {
		return linkModifica;
	}

	public void setLinkModifica(java.lang.String linkModifica) {
		this.linkModifica = linkModifica;
	}

	public java.lang.String getLinkNote() {
		return linkNote;
	}

	public void setLinkNote(java.lang.String linkNote) {
		this.linkNote = linkNote;
	}

	public java.lang.String getLinkRettifiche() {
		return linkRettifiche;
	}

	public void setLinkRettifiche(java.lang.String linkRettifiche) {
		this.linkRettifiche = linkRettifiche;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.Double getOreLavorate() {
		return oreLavorate;
	}

	public void setOreLavorate(java.lang.Double oreLavorate) {
		this.oreLavorate = oreLavorate;
	}

	public java.lang.Double getImportoResiduoAmmesso() {
		return importoResiduoAmmesso;
	}

	public void setImportoResiduoAmmesso(java.lang.Double importoResiduoAmmesso) {
		this.importoResiduoAmmesso = importoResiduoAmmesso;
	}

	public java.lang.Double getImportoAmmesso() {
		return importoAmmesso;
	}

	public void setImportoAmmesso(java.lang.Double importoAmmesso) {
		this.importoAmmesso = importoAmmesso;
	}

	public RigaValidazioneItemDTO() {}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nRigaValidazioneItemDTO: ");
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
