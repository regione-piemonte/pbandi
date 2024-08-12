package it.csi.pbandi.pbweb.dto.documentoDiSpesa;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class DatiFatturaElettronica implements java.io.Serializable {

	// Dati generali documento
	private java.lang.String numeroDocumento = null;
	private java.util.Date dataDocumentoDiSpesa = null;
	private java.lang.String descrizioneDocumentoDiSpesa = null;
	private java.lang.String flagElettronico = null;				// S o N
	private java.lang.String flagElettXml = null;					// S o N : marca che è stato allegata una fattura elettronica.

	// Importi
	private java.lang.Double imponibile = null;
	private java.lang.Double importoIva = null;
	private java.lang.Double importoTotaleDocumentoIvato = null;
	
	// Fornitore
	private java.lang.String denominazioneFornitore = null;
	private java.lang.String codiceFiscaleFornitore = null;
	private java.lang.String partitaIvaFornitore = null;
	private java.lang.String flagPubblicoPrivatoFornitore = null;  	// S o N
	private java.lang.Long idTipoFornitore = null;
	private java.lang.String codTipologiaFornitore = null;			// EG o PF
	
	// Appalto
	private java.lang.Long idAppalto = null;
	private java.lang.String descrizioneAppalto = null;
	public java.lang.String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(java.lang.String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public java.util.Date getDataDocumentoDiSpesa() {
		return dataDocumentoDiSpesa;
	}
	public void setDataDocumentoDiSpesa(java.util.Date dataDocumentoDiSpesa) {
		this.dataDocumentoDiSpesa = dataDocumentoDiSpesa;
	}
	public java.lang.String getDescrizioneDocumentoDiSpesa() {
		return descrizioneDocumentoDiSpesa;
	}
	public void setDescrizioneDocumentoDiSpesa(java.lang.String descrizioneDocumentoDiSpesa) {
		this.descrizioneDocumentoDiSpesa = descrizioneDocumentoDiSpesa;
	}
	public java.lang.String getFlagElettronico() {
		return flagElettronico;
	}
	public void setFlagElettronico(java.lang.String flagElettronico) {
		this.flagElettronico = flagElettronico;
	}
	public java.lang.String getFlagElettXml() {
		return flagElettXml;
	}
	public void setFlagElettXml(java.lang.String flagElettXml) {
		this.flagElettXml = flagElettXml;
	}
	public java.lang.Double getImponibile() {
		return imponibile;
	}
	public void setImponibile(java.lang.Double imponibile) {
		this.imponibile = imponibile;
	}
	public java.lang.Double getImportoIva() {
		return importoIva;
	}
	public void setImportoIva(java.lang.Double importoIva) {
		this.importoIva = importoIva;
	}
	public java.lang.Double getImportoTotaleDocumentoIvato() {
		return importoTotaleDocumentoIvato;
	}
	public void setImportoTotaleDocumentoIvato(java.lang.Double importoTotaleDocumentoIvato) {
		this.importoTotaleDocumentoIvato = importoTotaleDocumentoIvato;
	}
	public java.lang.String getDenominazioneFornitore() {
		return denominazioneFornitore;
	}
	public void setDenominazioneFornitore(java.lang.String denominazioneFornitore) {
		this.denominazioneFornitore = denominazioneFornitore;
	}
	public java.lang.String getCodiceFiscaleFornitore() {
		return codiceFiscaleFornitore;
	}
	public void setCodiceFiscaleFornitore(java.lang.String codiceFiscaleFornitore) {
		this.codiceFiscaleFornitore = codiceFiscaleFornitore;
	}
	public java.lang.String getPartitaIvaFornitore() {
		return partitaIvaFornitore;
	}
	public void setPartitaIvaFornitore(java.lang.String partitaIvaFornitore) {
		this.partitaIvaFornitore = partitaIvaFornitore;
	}
	public java.lang.String getFlagPubblicoPrivatoFornitore() {
		return flagPubblicoPrivatoFornitore;
	}
	public void setFlagPubblicoPrivatoFornitore(java.lang.String flagPubblicoPrivatoFornitore) {
		this.flagPubblicoPrivatoFornitore = flagPubblicoPrivatoFornitore;
	}
	public java.lang.Long getIdTipoFornitore() {
		return idTipoFornitore;
	}
	public void setIdTipoFornitore(java.lang.Long idTipoFornitore) {
		this.idTipoFornitore = idTipoFornitore;
	}
	public java.lang.String getCodTipologiaFornitore() {
		return codTipologiaFornitore;
	}
	public void setCodTipologiaFornitore(java.lang.String codTipologiaFornitore) {
		this.codTipologiaFornitore = codTipologiaFornitore;
	}
	public java.lang.Long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(java.lang.Long idAppalto) {
		this.idAppalto = idAppalto;
	}
	public java.lang.String getDescrizioneAppalto() {
		return descrizioneAppalto;
	}
	public void setDescrizioneAppalto(java.lang.String descrizioneAppalto) {
		this.descrizioneAppalto = descrizioneAppalto;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nDatiFatturaElettronica: ");
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
