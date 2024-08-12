package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class AllegatoVO extends GenericVO {
	
	private String contenutoMicroSezione;
	private BigDecimal idMacroSezioneModulo;
	private BigDecimal idTipoDocumentoIndex;
	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal numOrdinamentoMicroSezione;
	private BigDecimal idMicroSezioneModulo;
	
	public String getContenutoMicroSezione() {
		return contenutoMicroSezione;
	}
	public void setContenutoMicroSezione(String contenutoMicroSezione) {
		this.contenutoMicroSezione = contenutoMicroSezione;
	}
	public BigDecimal getIdMacroSezioneModulo() {
		return idMacroSezioneModulo;
	}
	public void setIdMacroSezioneModulo(BigDecimal idMacroSezioneModulo) {
		this.idMacroSezioneModulo = idMacroSezioneModulo;
	}
	public BigDecimal getIdTipoDocumentoIndex() {
		return idTipoDocumentoIndex;
	}
	public void setIdTipoDocumentoIndex(BigDecimal idTipoDocumentoIndex) {
		this.idTipoDocumentoIndex = idTipoDocumentoIndex;
	}
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	public void setNumOrdinamentoMicroSezione(BigDecimal numOrdinamentoMicroSezione) {
		this.numOrdinamentoMicroSezione = numOrdinamentoMicroSezione;
	}
	public BigDecimal getNumOrdinamentoMicroSezione() {
		return numOrdinamentoMicroSezione;
	}
	public BigDecimal getIdMicroSezioneModulo() {
		return idMicroSezioneModulo;
	}
	public void setIdMicroSezioneModulo(BigDecimal idMicroSezioneModulo) {
		this.idMicroSezioneModulo = idMicroSezioneModulo;
	}
}
