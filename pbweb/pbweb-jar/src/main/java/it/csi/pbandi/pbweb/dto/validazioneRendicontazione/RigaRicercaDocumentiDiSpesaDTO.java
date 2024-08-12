package it.csi.pbandi.pbweb.dto.validazioneRendicontazione;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class RigaRicercaDocumentiDiSpesaDTO implements java.io.Serializable {
	
	private java.lang.Long idDocumentoDiSpesa = null;
	
	private java.lang.Long idTipoDocumentoDiSpesa = null;
	private java.lang.String descBreveTipoDocumentoDiSpesa = null;
	private java.lang.String descrizioneTipologiaDocumento = null;
	
	private java.lang.Long idStatoDocumentoSpesa = null;
	private java.lang.String descrizioneStatoDocumentoSpesa = null;		// colonna 'stato validazione'
	private java.lang.String task = null;
	
	private java.lang.String tipoInvio = null;							// D = digitale; C = cartaceo		
	private java.lang.String numeroDocumento = null;
	private java.lang.String denominazioneFornitore = null;
	
	
	private java.lang.Double importoTotaleDocumento = null;
	private java.lang.Double importoTotaleValidato = null;
	
	private java.util.Date dataDocumento = null;
	

	public java.lang.Long getIdDocumentoDiSpesa() {
		return idDocumentoDiSpesa;
	}

	public void setIdDocumentoDiSpesa(java.lang.Long idDocumentoDiSpesa) {
		this.idDocumentoDiSpesa = idDocumentoDiSpesa;
	}

	public java.lang.Long getIdTipoDocumentoDiSpesa() {
		return idTipoDocumentoDiSpesa;
	}

	public void setIdTipoDocumentoDiSpesa(java.lang.Long idTipoDocumentoDiSpesa) {
		this.idTipoDocumentoDiSpesa = idTipoDocumentoDiSpesa;
	}

	public java.lang.String getDescBreveTipoDocumentoDiSpesa() {
		return descBreveTipoDocumentoDiSpesa;
	}

	public void setDescBreveTipoDocumentoDiSpesa(java.lang.String descBreveTipoDocumentoDiSpesa) {
		this.descBreveTipoDocumentoDiSpesa = descBreveTipoDocumentoDiSpesa;
	}

	public java.lang.String getDescrizioneTipologiaDocumento() {
		return descrizioneTipologiaDocumento;
	}

	public void setDescrizioneTipologiaDocumento(java.lang.String descrizioneTipologiaDocumento) {
		this.descrizioneTipologiaDocumento = descrizioneTipologiaDocumento;
	}

	public java.lang.Long getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}

	public void setIdStatoDocumentoSpesa(java.lang.Long idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}

	public java.lang.String getDescrizioneStatoDocumentoSpesa() {
		return descrizioneStatoDocumentoSpesa;
	}

	public void setDescrizioneStatoDocumentoSpesa(java.lang.String descrizioneStatoDocumentoSpesa) {
		this.descrizioneStatoDocumentoSpesa = descrizioneStatoDocumentoSpesa;
	}

	public java.lang.String getTask() {
		return task;
	}

	public void setTask(java.lang.String task) {
		this.task = task;
	}

	public java.lang.String getTipoInvio() {
		return tipoInvio;
	}




	public void setTipoInvio(java.lang.String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}

	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(java.lang.String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}

	public void setDenominazioneFornitore(java.lang.String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}

	public java.lang.Double getImportoTotaleDocumento() {
		return importoTotaleDocumento;
	}

	public void setImportoTotaleDocumento(java.lang.Double importoTotaleDocumento) {
		this.importoTotaleDocumento = importoTotaleDocumento;
	}

	public java.lang.Double getImportoTotaleValidato() {
		return importoTotaleValidato;
	}

	public void setImportoTotaleValidato(java.lang.Double importoTotaleValidato) {
		this.importoTotaleValidato = importoTotaleValidato;
	}

	public java.util.Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(java.util.Date dataDocumento) {
		this.dataDocumento = dataDocumento;
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
