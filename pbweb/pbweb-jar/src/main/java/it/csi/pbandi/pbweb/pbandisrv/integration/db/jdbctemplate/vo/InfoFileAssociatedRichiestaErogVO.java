package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.util.Date;


public class InfoFileAssociatedRichiestaErogVO extends InfoFileVO {
	
	private Date dtRichiestaErogazione;
	private BigDecimal idRichiestaErogazione;
	private BigDecimal importoErogazioneRichiesto;
	private String numeroRichiestaErogazione;
	
	public String getNumeroRichiestaErogazione() {
		return numeroRichiestaErogazione;
	}
	public void setNumeroRichiestaErogazione(String numeroRichiestaErogazione) {
		this.numeroRichiestaErogazione = numeroRichiestaErogazione;
	}
	public BigDecimal getIdRichiestaErogazione() {
		return idRichiestaErogazione;
	}
	public void setIdRichiestaErogazione(BigDecimal idRichiestaErogazione) {
		this.idRichiestaErogazione = idRichiestaErogazione;
	}
	public Date getDtRichiestaErogazione() {
		return dtRichiestaErogazione;
	}
	public void setDtRichiestaErogazione(Date dtRichiestaErogazione) {
		this.dtRichiestaErogazione = dtRichiestaErogazione;
	}
	public BigDecimal getImportoErogazioneRichiesto() {
		return importoErogazioneRichiesto;
	}
	public void setImportoErogazioneRichiesto(BigDecimal importoErogazioneRichiesto) {
		this.importoErogazioneRichiesto = importoErogazioneRichiesto;
	}

 

}
