/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class FormAssociaDocSpesa implements Serializable {

	private Long idDocumentoDiSpesa;
	private String task;
	private Double importoRendicontabile;
	private Double massimoRendicontabile;
	private Boolean isDocumentoTotalmenteRendicontato;
	private String descBreveTipoDocumento;
	
	public Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public Double getImportoRendicontabile() {
		return importoRendicontabile;
	}
	public void setImportoRendicontabile(Double importoRendicontabile) {
		this.importoRendicontabile = importoRendicontabile;
	}
	public Double getMassimoRendicontabile() {
		return massimoRendicontabile;
	}
	public void setMassimoRendicontabile(Double massimoRendicontabile) {
		this.massimoRendicontabile = massimoRendicontabile;
	}
	public Boolean getIsDocumentoTotalmenteRendicontato() {
		return isDocumentoTotalmenteRendicontato;
	}
	public void setIsDocumentoTotalmenteRendicontato(Boolean isDocumentoTotalmenteRendicontato) {
		this.isDocumentoTotalmenteRendicontato = isDocumentoTotalmenteRendicontato;
	}
	public String getDescBreveTipoDocumento() {
		return descBreveTipoDocumento;
	}
	public void setDescBreveTipoDocumento(String descBreveTipoDocumento) {
		this.descBreveTipoDocumento = descBreveTipoDocumento;
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
