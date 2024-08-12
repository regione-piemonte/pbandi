package it.csi.pbandi.pbweb.integration.vo;

import java.math.BigDecimal;

public class TipoDocumentiSpesaVO {
	
	private BigDecimal progrBandoLineaIntervento;
	private BigDecimal idTipoDocumentoSpesa;
	private String descBreveTipoDocSpesa;
	private String descTipoDocumentoSpesa;
	
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getIdTipoDocumentoSpesa() {
		return idTipoDocumentoSpesa;
	}
	
	public void setIdTipoDocumentoSpesa(BigDecimal idTipoDocumentoSpesa) {
		this.idTipoDocumentoSpesa = idTipoDocumentoSpesa;
	}
	
	public String getDescBreveTipoDocSpesa() {
		return descBreveTipoDocSpesa;
	}
	
	public void setDescBreveTipoDocSpesa(String descBreveTipoDocSpesa) {
		this.descBreveTipoDocSpesa = descBreveTipoDocSpesa;
	}
	
	public String getDescTipoDocumentoSpesa() {
		return descTipoDocumentoSpesa;
	}
	
	public void setDescTipoDocumentoSpesa(String descTipoDocumentoSpesa) {
		this.descTipoDocumentoSpesa = descTipoDocumentoSpesa;
	}
}
