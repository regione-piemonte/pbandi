package it.csi.pbandi.pbweb.pbandisrv.dto.checklist;

public class EsitoOperazioneDTO implements java.io.Serializable {

	static final long serialVersionUID = 1;
	private boolean esito = true;
	private String descBreveStato = null;
	private String codiceMessaggio = null;

	private Long idDocumentoIndexCL = null;
	public boolean isEsito() {
		return esito;
	}
	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	public String getDescBreveStato() {
		return descBreveStato;
	}
	public void setDescBreveStato(String descBreveStato) {
		this.descBreveStato = descBreveStato;
	}
	public String getCodiceMessaggio() {
		return codiceMessaggio;
	}
	public void setCodiceMessaggio(String codiceMessaggio) {
		this.codiceMessaggio = codiceMessaggio;
	}

	public Long getIdDocumentoIndexCL() {
		return idDocumentoIndexCL;
	}

	public void setIdDocumentoIndexCL(Long idDocumentoIndexCL) {
		this.idDocumentoIndexCL = idDocumentoIndexCL;
	}
}

