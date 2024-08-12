package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.erogazione;

import java.math.BigDecimal;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ModalitaAgevolazioneErogazioneVO extends GenericVO {
	
	private BigDecimal idModalitaAgevolazione;
	private BigDecimal idProgetto;
	private String descBreveModalitaAgevolaz;
    private String descModalitaAgevolazione;
    private BigDecimal importoTotaleRecupero;
    private BigDecimal importoTotaleErogazioni;
    
    
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
	public String getDescBreveModalitaAgevolaz() {
		return descBreveModalitaAgevolaz;
	}
	public void setDescBreveModalitaAgevolaz(String descBreveModalitaAgevolaz) {
		this.descBreveModalitaAgevolaz = descBreveModalitaAgevolaz;
	}
	public String getDescModalitaAgevolazione() {
		return descModalitaAgevolazione;
	}
	public void setDescModalitaAgevolazione(String descModalitaAgevolazione) {
		this.descModalitaAgevolazione = descModalitaAgevolazione;
	}
	public BigDecimal getImportoTotaleRecupero() {
		return importoTotaleRecupero;
	}
	public void setImportoTotaleRecupero(BigDecimal importoTotaleRecupero) {
		this.importoTotaleRecupero = importoTotaleRecupero;
	}
	public void setImportoTotaleErogazioni(BigDecimal importoTotaleErogazioni) {
		this.importoTotaleErogazioni = importoTotaleErogazioni;
	}
	public BigDecimal getImportoTotaleErogazioni() {
		return importoTotaleErogazioni;
	}

}
