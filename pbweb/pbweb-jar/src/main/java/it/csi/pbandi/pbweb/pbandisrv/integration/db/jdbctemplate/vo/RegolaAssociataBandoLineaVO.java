package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRRegolaBandoLineaVO;



public class RegolaAssociataBandoLineaVO extends PbandiRRegolaBandoLineaVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5466939830371266418L;
	private String descRegolaComposta;
	private String tipoAssociazione;
	private String descBreveRegola;
	private String nomeBandolinea;
	
	public void setDescRegolaComposta(String descRegolaComposta) {
		this.descRegolaComposta = descRegolaComposta;
	}
	public String getDescRegolaComposta() {
		return descRegolaComposta;
	}
	public void setTipoAssociazione(String tipoAssociazione) {
		this.tipoAssociazione = tipoAssociazione;
	}
	public String getTipoAssociazione() {
		return tipoAssociazione;
	}
	public void setDescBreveRegola(String descBreveRegola) {
		this.descBreveRegola = descBreveRegola;
	}
	public String getDescBreveRegola() {
		return descBreveRegola;
	}
	public void setNomeBandolinea(String nomeBandolinea) {
		this.nomeBandolinea = nomeBandolinea;
	}
	public String getNomeBandolinea() {
		return nomeBandolinea;
	}
}
