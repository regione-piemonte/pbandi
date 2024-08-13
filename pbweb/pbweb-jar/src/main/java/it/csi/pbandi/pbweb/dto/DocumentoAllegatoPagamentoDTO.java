/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto;

import it.csi.pbandi.pbweb.util.BeanUtil;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DocumentoAllegatoPagamentoDTO implements java.io.Serializable {

	private java.lang.Long idPagamento = null;
	private java.lang.String dtPagamento = null;
	private java.lang.String descrizioneModalitaPagamento = null;
	private java.lang.Double importoPagamento = null;

	private List<List<DocumentoAllegatoDTO>> documentoAllegatoIntegrazioni = new ArrayList<>();
	private List<DocumentoAllegatoDTO> documentoAllegatoGenerico = new ArrayList<>();


	private java.lang.Long id = null;
	private java.lang.String nomeFile = null;
	private java.lang.Long sizeFile = null;
	private java.lang.String idIntegrazioneSpesa = null;

	public String getIdIntegrazioneSpesa() {
		return idIntegrazioneSpesa;
	}

	public void setIdIntegrazioneSpesa(String idIntegrazioneSpesa) {
		this.idIntegrazioneSpesa = idIntegrazioneSpesa;
	}

	public List<List<DocumentoAllegatoDTO>> getDocumentoAllegatoIntegrazioni() {
		return documentoAllegatoIntegrazioni;
	}

	public void setDocumentoAllegatoIntegrazioni(List<List<DocumentoAllegatoDTO>> documentoAllegatoIntegrazioni) {
		this.documentoAllegatoIntegrazioni = documentoAllegatoIntegrazioni;
	}

	public List<DocumentoAllegatoDTO> getDocumentoAllegatoGenerico() {
		return documentoAllegatoGenerico;
	}

	public void setDocumentoAllegatoGenerico(List<DocumentoAllegatoDTO> documentoAllegatoGenerico) {
		this.documentoAllegatoGenerico = documentoAllegatoGenerico;
	}

	public java.lang.String getDescrizioneModalitaPagamento() {
		return descrizioneModalitaPagamento;
	}

	public void setDescrizioneModalitaPagamento(java.lang.String descrizioneModalitaPagamento) {
		this.descrizioneModalitaPagamento = descrizioneModalitaPagamento;
	}

	public java.lang.String getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(java.lang.String dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public java.lang.Double getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(java.lang.Double importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	public java.lang.String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(java.lang.String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(java.lang.Long idPagamento) {
		this.idPagamento = idPagamento;
	}

	public java.lang.Long getSizeFile() {
		return sizeFile;
	}

	public void setSizeFile(java.lang.Long sizeFile) {
		this.sizeFile = sizeFile;
	}

	public DocumentoAllegatoPagamentoDTO() {}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nDocumentoAllegatoPagamentoDTO: ");
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
