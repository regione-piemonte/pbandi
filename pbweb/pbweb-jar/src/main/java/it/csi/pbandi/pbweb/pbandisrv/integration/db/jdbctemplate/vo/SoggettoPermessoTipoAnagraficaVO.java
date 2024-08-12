package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;


public class SoggettoPermessoTipoAnagraficaVO extends PermessoTipoAnagraficaVO {

  	private BigDecimal idSoggetto;
  	
	
  	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

}