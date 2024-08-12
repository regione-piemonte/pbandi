package it.csi.pbandi.pbweb.dto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class FatturaRiferimentoDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	
	private java.lang.Long idDocumentoDiSpesa = null;
	private java.lang.String descrizione = null;
	private java.lang.Double importoRendicontabile = null;
	private java.lang.Double importoTotaleDocumentoIvato = null;
	private java.lang.Double importoTotaleQuietanzato = null;
	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}
	public void setIdDocumentoDiSpesa(java.lang.Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}
	public java.lang.String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(java.lang.String descrizione) {
		this.descrizione = descrizione;
	}
	public java.lang.Double getImportoRendicontabile() {
		return importoRendicontabile;
	}
	public void setImportoRendicontabile(java.lang.Double importoRendicontabile) {
		this.importoRendicontabile = importoRendicontabile;
	}
	public java.lang.Double getImportoTotaleDocumentoIvato() {
		return importoTotaleDocumentoIvato;
	}
	public void setImportoTotaleDocumentoIvato(java.lang.Double importoTotaleDocumentoIvato) {
		this.importoTotaleDocumentoIvato = importoTotaleDocumentoIvato;
	}
	public java.lang.Double getImportoTotaleQuietanzato() {
		return importoTotaleQuietanzato;
	}
	public void setImportoTotaleQuietanzato(java.lang.Double importoTotaleQuietanzato) {
		this.importoTotaleQuietanzato = importoTotaleQuietanzato;
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
