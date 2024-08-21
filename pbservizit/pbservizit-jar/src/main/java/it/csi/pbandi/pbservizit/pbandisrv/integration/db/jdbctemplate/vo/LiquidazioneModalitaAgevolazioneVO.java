/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;

public class LiquidazioneModalitaAgevolazioneVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private BigDecimal progrLiquidazione;
	private BigDecimal idModalitaAgevolazione;
	private BigDecimal idCausaleErogazione;
    private String descBreve_causale;
    private String descCausale;
    private String descBreveModalitaAgevolaz;
    private String descModalitaAgevolazione;
    private BigDecimal importoQuietanzato;
    
    
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getProgrLiquidazione() {
		return progrLiquidazione;
	}
	public void setProgrLiquidazione(BigDecimal progrLiquidazione) {
		this.progrLiquidazione = progrLiquidazione;
	}
	public BigDecimal getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(BigDecimal idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	public String getDescBreve_causale() {
		return descBreve_causale;
	}
	public void setDescBreve_causale(String descBreve_causale) {
		this.descBreve_causale = descBreve_causale;
	}
	public String getDescCausale() {
		return descCausale;
	}
	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
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
	public BigDecimal getImportoQuietanzato() {
		return importoQuietanzato;
	}
	public void setImportoQuietanzato(BigDecimal importoQuietanzato) {
		this.importoQuietanzato = importoQuietanzato;
	}

}
