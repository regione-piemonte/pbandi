package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ErogazioneReportVO extends GenericVO {
	
	private BigDecimal idModalitaAgevolazione;
	private BigDecimal idProgetto;
    private String descModalitaAgevolazione;
    private BigDecimal importoErogazione;
    private BigDecimal idErogazione;
     
     
    
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
	
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	/**
	 * @return the importoErogazione
	 */
	public BigDecimal getImportoErogazione() {
		return importoErogazione;
	}
	/**
	 * @param importoErogazione the importoErogazione to set
	 */
	public void setImportoErogazione(BigDecimal importoErogazione) {
		this.importoErogazione = importoErogazione;
	}
	/**
	 * @return the idErogazione
	 */
	public BigDecimal getIdErogazione() {
		return idErogazione;
	}
	/**
	 * @param idErogazione the idErogazione to set
	 */
	public void setIdErogazione(BigDecimal idErogazione) {
		this.idErogazione = idErogazione;
	}
	

}
