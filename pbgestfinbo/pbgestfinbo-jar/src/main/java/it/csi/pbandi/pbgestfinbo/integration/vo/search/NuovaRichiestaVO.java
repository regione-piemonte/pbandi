/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.math.BigDecimal;
import java.util.Date;

public class NuovaRichiestaVO {
	
	private BigDecimal idTipoRichiesta;
	private BigDecimal idUtenteIns;
	private String numeroDomanda;
	private String flagUrgenza;
	private String codiceFiscale;
	private BigDecimal codiceBando;
	private String codiceProgetto;
	private BigDecimal idRichiesta; 
	private int idStatoRichiesta; 
	private BigDecimal idSoggeto; 
	
	
	
	public BigDecimal getIdRichiesta() {
		return idRichiesta;
	}
	public void setIdRichiesta(BigDecimal idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	public int getIdStatoRichiesta() {
		return idStatoRichiesta;
	}
	public void setIdStatoRichiesta(int idStatoRichiesta) {
		this.idStatoRichiesta = idStatoRichiesta;
	}
	public BigDecimal getIdSoggeto() {
		return idSoggeto;
	}
	public void setIdSoggeto(BigDecimal idSoggeto) {
		this.idSoggeto = idSoggeto;
	}
	public BigDecimal getIdTipoRichiesta() {
		return idTipoRichiesta;
	}
	public void setIdTipoRichiesta(BigDecimal idTipoRichiesta) {
		this.idTipoRichiesta = idTipoRichiesta;
	}
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	public String getFlagUrgenza() {
		return flagUrgenza;
	}
	public void setFlagUrgenza(String flagUrgenza) {
		this.flagUrgenza = flagUrgenza;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public BigDecimal getCodiceBando() {
		return codiceBando;
	}
	public void setCodiceBando(BigDecimal codiceBando) {
		this.codiceBando = codiceBando;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	
	@Override
	public String toString() {
		return "NuovaRichiestaVO [idTipoRichiesta=" + idTipoRichiesta + ", idUtenteIns=" + idUtenteIns
				+ ", numeroDomanda=" + numeroDomanda + ", flagUrgenza=" + flagUrgenza + ", codiceFiscale="
				+ codiceFiscale + ", codiceBando=" + codiceBando + ", codiceProgetto=" + codiceProgetto + "]";
	}
	
	
}
