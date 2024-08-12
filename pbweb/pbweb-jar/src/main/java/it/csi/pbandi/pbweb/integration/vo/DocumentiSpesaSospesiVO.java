package it.csi.pbandi.pbweb.integration.vo;


import java.util.Date;

public class DocumentiSpesaSospesiVO { 
		
	private Long idDocuSpesa;
	private String importoRendicon;
	private String idUtenteIns;
	private String idUtenteAgg;
	private String noteValidazione;
	private String task;
	private String idStatoDocumentoSpesa;
	private String idStatoDocumentoSpesaValid;
	private String tipoInvio;
	private String idAppalto;
	
	
	
	
	public DocumentiSpesaSospesiVO() {
	}
	
	public DocumentiSpesaSospesiVO(Long idDocuSpesa, String importoRendicon, String idUtenteIns, String idUtenteAgg,
			String noteValidazione, String task, String idStatoDocumentoSpesa, String idStatoDocumentoSpesaValid,
			String tipoInvio, String idAppalto) {
		this.idDocuSpesa = idDocuSpesa;
		this.importoRendicon = importoRendicon;
		this.idUtenteIns = idUtenteIns;
		this.idUtenteAgg = idUtenteAgg;
		this.noteValidazione = noteValidazione;
		this.task = task;
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
		this.idStatoDocumentoSpesaValid = idStatoDocumentoSpesaValid;
		this.tipoInvio = tipoInvio;
		this.idAppalto = idAppalto;
	}
	public Long getIdDocuSpesa() {
		return idDocuSpesa;
	}
	public void setIdDocuSpesa(Long idDocuSpesa) {
		this.idDocuSpesa = idDocuSpesa;
	}
	public String getImportoRendicon() {
		return importoRendicon;
	}
	public void setImportoRendicon(String importoRendicon) {
		this.importoRendicon = importoRendicon;
	}
	public String getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(String idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public String getIdUtenteAgg() {
		return idUtenteAgg;
	}
	public void setIdUtenteAgg(String idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	public String getNoteValidazione() {
		return noteValidazione;
	}
	public void setNoteValidazione(String noteValidazione) {
		this.noteValidazione = noteValidazione;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getIdStatoDocumentoSpesa() {
		return idStatoDocumentoSpesa;
	}
	public void setIdStatoDocumentoSpesa(String idStatoDocumentoSpesa) {
		this.idStatoDocumentoSpesa = idStatoDocumentoSpesa;
	}
	public String getIdStatoDocumentoSpesaValid() {
		return idStatoDocumentoSpesaValid;
	}
	public void setIdStatoDocumentoSpesaValid(String idStatoDocumentoSpesaValid) {
		this.idStatoDocumentoSpesaValid = idStatoDocumentoSpesaValid;
	}
	public String getTipoInvio() {
		return tipoInvio;
	}
	public void setTipoInvio(String tipoInvio) {
		this.tipoInvio = tipoInvio;
	}
	public String getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(String idAppalto) {
		this.idAppalto = idAppalto;
	}
	
	
		
	
	
	
	
}	