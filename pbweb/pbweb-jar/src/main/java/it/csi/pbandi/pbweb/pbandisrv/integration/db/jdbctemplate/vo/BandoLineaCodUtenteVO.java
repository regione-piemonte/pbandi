package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.GenericVO;

public class BandoLineaCodUtenteVO extends GenericVO {

	private String codUtente;
	private BigDecimal idProcesso;
	private String nomeBandoLinea;
	private BigDecimal progrBandoLineaIntervento;

	@Override
	public List getPK() {
		return null;
	}

	@Override
	public boolean isPKValid() {
		return false;
	}

	public String getCodUtente() {
		return codUtente;
	}

	public void setCodUtente(String codUtente) {
		this.codUtente = codUtente;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(
			BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public BigDecimal getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}

}
