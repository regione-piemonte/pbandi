package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

public class SezioneContenutoVO extends GenericVO {
	
	private String contenutoMicroSezione;
	private Long idTipoDocumentoIndex;

	public Long getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}

	public void setIdTipoDocumentoIndex(Long idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}

	public String getContenutoMicroSezione() {
		return contenutoMicroSezione;
	}

	public void setContenutoMicroSezione(String contenutoMicroSezione) {
		this.contenutoMicroSezione = contenutoMicroSezione;
	}

}
