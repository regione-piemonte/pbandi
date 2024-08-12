package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiRBandoLineaInterventVO;

public class BandoLineaVO extends PbandiRBandoLineaInterventVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4630285426496038955L;
	private String descBreveLinea;
	private String descAreaScientifica;
	private String codAreaScientifica;
	private String descBandoLinea;
	private String descBando;
	private String uuidProcesso;
	private String titoloBando;
	private BigDecimal idBandoLinea;
	private String determinaApprovazione;
	
	// CDU-14-V08 inizio
	private BigDecimal idLineaDiIntervento;
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}

	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	// CDU-14-V08 fine

	public void setDescBreveLinea(String descBreveLinea) {
		this.descBreveLinea = descBreveLinea;
	}

	public String getDescBreveLinea() {
		return descBreveLinea;
	}

	public void setDescAreaScientifica(String descAreaScientifica) {
		this.descAreaScientifica = descAreaScientifica;
	}

	public String getDescAreaScientifica() {
		return descAreaScientifica;
	}

	public void setCodAreaScientifica(String codAreaScientifica) {
		this.codAreaScientifica = codAreaScientifica;
	}

	public String getCodAreaScientifica() {
		return codAreaScientifica;
	}

	public void setDescBandoLinea(String descBandoLinea) {
		this.descBandoLinea = descBandoLinea;
	}

	public String getDescBandoLinea() {
		return descBandoLinea;
	}

	public void setDescBando(String descBando) {
		this.descBando = descBando;
	}

	public String getDescBando() {
		return descBando;
	}

	public void setUuidProcesso(String uuidProcesso) {
		this.uuidProcesso = uuidProcesso;
	}

	public String getUuidProcesso() {
		return uuidProcesso;
	}

	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}

	public String getTitoloBando() {
		return titoloBando;
	}

	public void setIdBandoLinea(BigDecimal idBandoLinea) {
		this.idBandoLinea = idBandoLinea;
	}

	public BigDecimal getIdBandoLinea() {
		return idBandoLinea;
	}
	
	public String getDeterminaApprovazione() {
		return determinaApprovazione;
	}

	public void setDeterminaApprovazione(String determinaApprovazione) {
		this.determinaApprovazione = determinaApprovazione;
	}

}