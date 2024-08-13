/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.util.BeanUtil;

public class DocumentoDiPagamentoDTO implements java.io.Serializable {

	private String causalePagamento = null;
	private String dtPagamento = null;
	private String descBreveModalitaPagamento = null;
	private String descrizioneModalitaPagamento = null;
	private String destinatarioPagamento = null;
	private java.util.ArrayList<DocumentoAllegatoDTO> documentiAllegati = new java.util.ArrayList<DocumentoAllegatoDTO>();
	private String estremiPagamento = null;
	private String flagPagamento = null;
	private long id = 0;
	private Long idCausalePagamento = null;
	private long idModalitaPagamento = 0;
	private double importoPagamento = 0;
	private double importoDaAssociare = 0;
	private double importoRendicontabilePagato = 0;
	private double importoResiduoUtilizzabile = 0;
	private Boolean importoResiduoUtilizzabileVuoto = null;
	private long idSoggetto = 0;
	private boolean linkAllegaFileVisible = false;
	private Boolean pagamentoModificabile = null;
	private Boolean pagamentoEliminabile = null;
	//private String linkModifica = null;
	//private String linkCancella = null;
	//private String linkDettaglio = null;
	private String rifPagamento = null;
	private String statoPagamento = null;
	public String getCausalePagamento() {
		return causalePagamento;
	}
	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}
	public String getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(String dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public String getDescBreveModalitaPagamento() {
		return descBreveModalitaPagamento;
	}
	public void setDescBreveModalitaPagamento(String descBreveModalitaPagamento) {
		this.descBreveModalitaPagamento = descBreveModalitaPagamento;
	}
	public String getDescrizioneModalitaPagamento() {
		return descrizioneModalitaPagamento;
	}
	public void setDescrizioneModalitaPagamento(String descrizioneModalitaPagamento) {
		this.descrizioneModalitaPagamento = descrizioneModalitaPagamento;
	}
	public String getDestinatarioPagamento() {
		return destinatarioPagamento;
	}
	public void setDestinatarioPagamento(String destinatarioPagamento) {
		this.destinatarioPagamento = destinatarioPagamento;
	}
	public java.util.ArrayList<DocumentoAllegatoDTO> getDocumentiAllegati() {
		return documentiAllegati;
	}
	public void setDocumentiAllegati(java.util.ArrayList<DocumentoAllegatoDTO> documentiAllegati) {
		this.documentiAllegati = documentiAllegati;
	}
	public String getEstremiPagamento() {
		return estremiPagamento;
	}
	public void setEstremiPagamento(String estremiPagamento) {
		this.estremiPagamento = estremiPagamento;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getIdCausalePagamento() {
		return idCausalePagamento;
	}
	public void setIdCausalePagamento(Long idCausalePagamento) {
		this.idCausalePagamento = idCausalePagamento;
	}
	public long getIdModalitaPagamento() {
		return idModalitaPagamento;
	}
	public void setIdModalitaPagamento(long idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}
	public double getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(double importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	public double getImportoDaAssociare() {
		return importoDaAssociare;
	}
	public void setImportoDaAssociare(double importoDaAssociare) {
		this.importoDaAssociare = importoDaAssociare;
	}
	public double getImportoRendicontabilePagato() {
		return importoRendicontabilePagato;
	}
	public void setImportoRendicontabilePagato(double importoRendicontabilePagato) {
		this.importoRendicontabilePagato = importoRendicontabilePagato;
	}
	public double getImportoResiduoUtilizzabile() {
		return importoResiduoUtilizzabile;
	}
	public void setImportoResiduoUtilizzabile(double importoResiduoUtilizzabile) {
		this.importoResiduoUtilizzabile = importoResiduoUtilizzabile;
	}
	public Boolean getImportoResiduoUtilizzabileVuoto() {
		return importoResiduoUtilizzabileVuoto;
	}
	public void setImportoResiduoUtilizzabileVuoto(Boolean importoResiduoUtilizzabileVuoto) {
		this.importoResiduoUtilizzabileVuoto = importoResiduoUtilizzabileVuoto;
	}
	public long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public boolean isLinkAllegaFileVisible() {
		return linkAllegaFileVisible;
	}
	public void setLinkAllegaFileVisible(boolean linkAllegaFileVisible) {
		this.linkAllegaFileVisible = linkAllegaFileVisible;
	}
	public Boolean getPagamentoModificabile() {
		return pagamentoModificabile;
	}
	public void setPagamentoModificabile(Boolean pagamentoModificabile) {
		this.pagamentoModificabile = pagamentoModificabile;
	}
	public Boolean getPagamentoEliminabile() {
		return pagamentoEliminabile;
	}
	public void setPagamentoEliminabile(Boolean pagamentoEliminabile) {
		this.pagamentoEliminabile = pagamentoEliminabile;
	}
	public String getRifPagamento() {
		return rifPagamento;
	}
	public void setRifPagamento(String rifPagamento) {
		this.rifPagamento = rifPagamento;
	}
	public String getStatoPagamento() {
		return statoPagamento;
	}
	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
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

	public String getFlagPagamento() {
		return flagPagamento;
	}

	public void setFlagPagamento(String flagPagamento) {
		this.flagPagamento = flagPagamento;
	}
}
