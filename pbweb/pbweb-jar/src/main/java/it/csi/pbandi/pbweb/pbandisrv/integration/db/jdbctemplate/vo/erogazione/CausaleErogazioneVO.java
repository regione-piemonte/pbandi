package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class CausaleErogazioneVO extends GenericVO {
	
	 private BigDecimal idCausaleErogazione;
	 private BigDecimal idModalitaAgevolazione;
	 private BigDecimal idProgetto;
	 private BigDecimal idErogazione;
	 private String descBreveCausale;
	 private String descCausale;
     private BigDecimal importoErogazione;
     private Date dtContabile;
     private String codRiferimentoErogazione;
     
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getDescBreveCausale() {
		return descBreveCausale;
	}
	public void setDescBreveCausale(String descBreveCausale) {
		this.descBreveCausale = descBreveCausale;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
	}
	public void setImportoErogazione(BigDecimal importoErogazione) {
		this.importoErogazione = importoErogazione;
	}
	public BigDecimal getImportoErogazione() {
		return importoErogazione;
	}
	public void setDtContabile(Date dtContabile) {
		this.dtContabile = dtContabile;
	}
	public Date getDtContabile() {
		return dtContabile;
	}
	public void setCodRiferimentoErogazione(String codRiferimentoErogazione) {
		this.codRiferimentoErogazione = codRiferimentoErogazione;
	}
	public String getCodRiferimentoErogazione() {
		return codRiferimentoErogazione;
	}
	public void setIdErogazione(BigDecimal idErogazione) {
		this.idErogazione = idErogazione;
	}
	public BigDecimal getIdErogazione() {
		return idErogazione;
	}
	
     
     

}
