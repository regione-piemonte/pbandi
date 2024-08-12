package it.csi.pbandi.pbweb.integration.vo;

import java.math.BigDecimal;

public class TipoAnagraficaVO extends PbandiDTipoAnagraficaVO {

	private String descRuoloHelp;
  	private BigDecimal idSoggetto;



	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getDescRuoloHelp() {
		return descRuoloHelp;
	}

	public void setDescRuoloHelp(String descRuoloHelp) {
		this.descRuoloHelp = descRuoloHelp;
	}
}
