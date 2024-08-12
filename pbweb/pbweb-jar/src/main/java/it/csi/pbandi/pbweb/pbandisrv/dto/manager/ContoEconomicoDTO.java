package it.csi.pbandi.pbweb.pbandisrv.dto.manager;

import java.math.BigDecimal;
import java.util.Date;

public class ContoEconomicoDTO extends RigoContoEconomicoDTO {
	private String descBreveStatoContoEconom;
	private Date dataPresentazioneDomanda;
	private String flagRibassoAsta;
	/*
	 * VN: ribasso d' asta
	 */
	private BigDecimal importoRibassoAsta;
	private BigDecimal percRibassoAsta;
	private BigDecimal importoImpegnoVincolante;
	
	// Cultura
	private BigDecimal percSpGenFunz;
	
	public BigDecimal getPercSpGenFunz() {
		return percSpGenFunz;
	}

	public void setPercSpGenFunz(BigDecimal percSpGenFunz) {
		this.percSpGenFunz = percSpGenFunz;
	}

	public void setDescBreveStatoContoEconom(String descBreveStatoContoEconom) {
		this.descBreveStatoContoEconom = descBreveStatoContoEconom;
	}

	public String getDescBreveStatoContoEconom() {
		return descBreveStatoContoEconom;
	}

	public void setDataPresentazioneDomanda(Date dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}

	public Date getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	public void setFlagRibassoAsta(String flagRibassoAsta) {
		this.flagRibassoAsta = flagRibassoAsta;
	}

	public String getFlagRibassoAsta() {
		return flagRibassoAsta;
	}

	public void setImportoRibassoAsta(BigDecimal importoRibassoAsta) {
		this.importoRibassoAsta = importoRibassoAsta;
	}

	public BigDecimal getImportoRibassoAsta() {
		return importoRibassoAsta;
	}

	public void setPercRibassoAsta(BigDecimal percRibassoAsta) {
		this.percRibassoAsta = percRibassoAsta;
	}

	public BigDecimal getPercRibassoAsta() {
		return percRibassoAsta;
	}

	public void setImportoImpegnoVincolante(BigDecimal importoImpegnoVincolante) {
		this.importoImpegnoVincolante = importoImpegnoVincolante;
	}

	public BigDecimal getImportoImpegnoVincolante() {
		return importoImpegnoVincolante;
	}

	@Override
	public String toString() {
		return "ContoEconomicoDTO [descBreveStatoContoEconom=" + descBreveStatoContoEconom
				+ ", dataPresentazioneDomanda=" + dataPresentazioneDomanda + ", flagRibassoAsta=" + flagRibassoAsta
				+ ", importoRibassoAsta=" + importoRibassoAsta + ", percRibassoAsta=" + percRibassoAsta
				+ ", importoImpegnoVincolante=" + importoImpegnoVincolante + ", percSpGenFunz=" + percSpGenFunz + "]";
	}

}
