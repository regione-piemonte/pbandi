package it.csi.pbandi.pbweb.integration.dao.request;

public class SalvaCheckListValidazioneDocumentaleHtmlRequest {

	private Long idProgetto;
	private Long idDichiarazioneDiSpesa;
	private Long idDocumentoIndex;
	private String stato;
	private String checklistHtml;
	private Long idBandoLinea;
	private String codiceProgetto;
	private boolean dsIntegrativaConsentita;
	
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public Long getIdDichiarazioneDiSpesa() {
		return idDichiarazioneDiSpesa;
	}
	public void setIdDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa) {
		this.idDichiarazioneDiSpesa = idDichiarazioneDiSpesa;
	}
	public Long getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(Long idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getChecklistHtml() {
		return checklistHtml;
	}
	public void setChecklistHtml(String checklistHtml) {
		this.checklistHtml = checklistHtml;
	}
	public Long getIdBandoLinea() {
		return idBandoLinea;
	}
	public void setIdBandoLinea(Long idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public boolean isDsIntegrativaConsentita() {
		return dsIntegrativaConsentita;
	}
	public void setDsIntegrativaConsentita(boolean dsIntegrativaConsentita) {
		this.dsIntegrativaConsentita = dsIntegrativaConsentita;
	}
	
	@Override
	public String toString() {
		String s = "SalvaCheckListValidazioneDocumentaleHtmlRequest [idProgetto=" + idProgetto + 
				", idDichiarazioneDiSpesa="	+ idDichiarazioneDiSpesa + 
				", idDocumentoIndex=" + idDocumentoIndex + ", stato=" + stato +
				", idBandoLinea=" + idBandoLinea + ", codiceProgetto=" + codiceProgetto +
				". dsIntegrativaConsentita="+dsIntegrativaConsentita ;
				
		if(checklistHtml==null) {
			s = s + ", checklistHtml=" + checklistHtml + "]";
		}else {
			s = s + ", checklistHtml.length=" + checklistHtml.length() + "]";
		}
		return s;
	}
	
	
	
}
