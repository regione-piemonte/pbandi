package it.csi.pbandi.pbweb.dto.documentiDiProgetto;

import java.beans.IntrospectionException;
import java.util.Set;

import it.csi.pbandi.pbweb.util.BeanUtil;

public class DocumentoIndexDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L; 

	private java.lang.String allegati = null;
    private java.lang.String beneficiario = null;
	private java.lang.String codiceProgettoVisualizzato = null;
	private java.lang.String codStatoDoc = null;
	private java.lang.String codTipoDoc = null;
	private java.lang.String descErrore = null;
	private java.lang.String descTipoDocumento = null;
	private java.lang.String dtCreazione = null;
	private java.lang.String dtSign = null;
	private java.lang.String dtTimestamp = null;
	private java.lang.String documento = null;
	private java.lang.Boolean flagFirmaCartacea = null;
	private java.lang.Boolean flagTrasmDest = null;
	private java.lang.String idDocumentoIndex = null;
	private java.lang.String idProgetto = null;
	private java.lang.String idBandoLinea = null;
	private java.lang.Boolean isBR50 = null;
	private java.lang.String note = null;
	private java.lang.String protocollo = null;
	private java.lang.Boolean signed = null;
	private java.lang.Boolean signable = null;
	private java.lang.Boolean signValid = null;
	private java.lang.Boolean timeStamped = null;
	private java.lang.String tipoInvioDs = null;
	private java.lang.String idCategAnagraficaMitt = null;
	private java.lang.String descCategAnagraficaMitt = null;	
	private java.lang.String idEntita = null;
	private java.lang.String idTarget = null;
	private java.lang.String iconDownloadDS = null;
	private Long dimMaxFileFirmato = null;
	private Boolean flagFirmaAutografa = null;
	
	//private java.lang.String linkEliminaUpload = null;
	//private java.lang.String icons = null;

	public DocumentoIndexDTO() {
		super();
	}

	public java.lang.Boolean getFlagTrasmDest() {
		return flagTrasmDest;
	}

	public void setFlagTrasmDest(java.lang.Boolean flagTrasmDest) {
		this.flagTrasmDest = flagTrasmDest;
	}

	public java.lang.String getAllegati() {
		return allegati;
	}

	public void setAllegati(java.lang.String allegati) {
		this.allegati = allegati;
	}

	public java.lang.String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(java.lang.String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public java.lang.String getCodiceProgettoVisualizzato() {
		return codiceProgettoVisualizzato;
	}

	public void setCodiceProgettoVisualizzato(java.lang.String codiceProgettoVisualizzato) {
		this.codiceProgettoVisualizzato = codiceProgettoVisualizzato;
	}

	public java.lang.String getCodStatoDoc() {
		return codStatoDoc;
	}

	public void setCodStatoDoc(java.lang.String codStatoDoc) {
		this.codStatoDoc = codStatoDoc;
	}

	public java.lang.String getCodTipoDoc() {
		return codTipoDoc;
	}

	public void setCodTipoDoc(java.lang.String codTipoDoc) {
		this.codTipoDoc = codTipoDoc;
	}

	public java.lang.String getDescErrore() {
		return descErrore;
	}

	public void setDescErrore(java.lang.String descErrore) {
		this.descErrore = descErrore;
	}

	public java.lang.String getDescTipoDocumento() {
		return descTipoDocumento;
	}

	public void setDescTipoDocumento(java.lang.String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}

	public java.lang.String getDtCreazione() {
		return dtCreazione;
	}

	public void setDtCreazione(java.lang.String dtCreazione) {
		this.dtCreazione = dtCreazione;
	}

	public java.lang.String getDtSign() {
		return dtSign;
	}

	public void setDtSign(java.lang.String dtSign) {
		this.dtSign = dtSign;
	}

	public java.lang.String getDtTimestamp() {
		return dtTimestamp;
	}

	public void setDtTimestamp(java.lang.String dtTimestamp) {
		this.dtTimestamp = dtTimestamp;
	}

	public java.lang.String getDocumento() {
		return documento;
	}

	public void setDocumento(java.lang.String documento) {
		this.documento = documento;
	}

	public java.lang.Boolean getFlagFirmaCartacea() {
		return flagFirmaCartacea;
	}

	public void setFlagFirmaCartacea(java.lang.Boolean flagFirmaCartacea) {
		this.flagFirmaCartacea = flagFirmaCartacea;
	}

	public java.lang.String getIdDocumentoIndex() {
		return idDocumentoIndex;
	}

	public void setIdDocumentoIndex(java.lang.String idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}

	public java.lang.String getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(java.lang.String idProgetto) {
		this.idProgetto = idProgetto;
	}	

	public java.lang.String getIdBandoLinea() { 
		return idBandoLinea;
	}

	public void setIdBandoLinea(java.lang.String idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	
	public java.lang.Boolean getIsBR50() {
		return isBR50;
	}

	public void setIsBR50(java.lang.Boolean isBR50) {
		this.isBR50 = isBR50;
	}

	public java.lang.String getNote() {
		return note;
	}

	public void setNote(java.lang.String note) {
		this.note = note;
	}

	public java.lang.String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(java.lang.String protocollo) {
		this.protocollo = protocollo;
	}

	public java.lang.Boolean getSigned() {
		return signed;
	}

	public void setSigned(java.lang.Boolean signed) {
		this.signed = signed;
	}

	public java.lang.Boolean getSignable() {
		return signable;
	}

	public void setSignable(java.lang.Boolean signable) {
		this.signable = signable;
	}

	public java.lang.Boolean getSignValid() {
		return signValid;
	}

	public void setSignValid(java.lang.Boolean signValid) {
		this.signValid = signValid;
	}

	public java.lang.Boolean getTimeStamped() {
		return timeStamped;
	}

	public void setTimeStamped(java.lang.Boolean timeStamped) {
		this.timeStamped = timeStamped;
	}

	public java.lang.String getTipoInvioDs() {
		return tipoInvioDs;
	}

	public void setTipoInvioDs(java.lang.String tipoInvioDs) {
		this.tipoInvioDs = tipoInvioDs;
	}

	public java.lang.String getIdCategAnagraficaMitt() {
		return idCategAnagraficaMitt;
	}

	public void setIdCategAnagraficaMitt(java.lang.String idCategAnagraficaMitt) {
		this.idCategAnagraficaMitt = idCategAnagraficaMitt;
	}

	public java.lang.String getDescCategAnagraficaMitt() {
		return descCategAnagraficaMitt;
	}

	public void setDescCategAnagraficaMitt(java.lang.String descCategAnagraficaMitt) {
		this.descCategAnagraficaMitt = descCategAnagraficaMitt;
	}

	public java.lang.String getIdEntita() {
		return idEntita;
	}

	public void setIdEntita(java.lang.String idEntita) {
		this.idEntita = idEntita;
	}

	public java.lang.String getIdTarget() {
		return idTarget;
	}

	public void setIdTarget(java.lang.String idTarget) {
		this.idTarget = idTarget;
	}

	public java.lang.String getIconDownloadDS() {
		return iconDownloadDS;
	}

	public void setIconDownloadDS(java.lang.String iconDownloadDS) {
		this.iconDownloadDS = iconDownloadDS;
	}
	
	
	

	public Long getDimMaxFileFirmato() {
		return dimMaxFileFirmato;
	}

	public void setDimMaxFileFirmato(Long dimMaxFileFirmato) {
		this.dimMaxFileFirmato = dimMaxFileFirmato;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public Boolean getFlagFirmaAutografa() {
		return flagFirmaAutografa;
	}

	public void setFlagFirmaAutografa(Boolean flagFirmaAutografa) {
		this.flagFirmaAutografa = flagFirmaAutografa;
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
