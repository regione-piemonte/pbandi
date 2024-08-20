/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti;

import java.math.BigDecimal;
import java.sql.Date;

public class ProgettoEstrattoCampVO {
	
	private BigDecimal idSoggetto; 
	private String descTipoSede; 
	private String sedeIntervento ; 
	private String codiceFiscaleSoggetto; 
	private String denominazione; 
	private String comuneSedeLegale; 
	private String provSedeLegale; 
	private String comuneSedeIntervento; 
	private String provSedeIntervento;
	private BigDecimal idProgetto; 
	private String descBando; 
	private BigDecimal idBando; 
	private String descFormaGiuridica; 
	private Date dataUltimoControllo;
	private int idFormaGiuridica;
	private BigDecimal idCampionamento; 
	private String motivazione; 
	private Long importoValidato; 
	private String codVisualizzato; 
	
	
	
	
	
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	public BigDecimal getIdCampionamento() {
		return idCampionamento;
	}
	public void setIdCampionamento(BigDecimal idCampionamento) {
		this.idCampionamento = idCampionamento;
	}
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getDescTipoSede() {
		return descTipoSede;
	}
	public void setDescTipoSede(String descTipoSede) {
		this.descTipoSede = descTipoSede;
	}
	public String getSedeIntervento() {
		return sedeIntervento;
	}
	public void setSedeIntervento(String sedeIntervento) {
		this.sedeIntervento = sedeIntervento;
	}
	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}
	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getComuneSedeLegale() {
		return comuneSedeLegale;
	}
	public void setComuneSedeLegale(String comuneSedeLegale) {
		this.comuneSedeLegale = comuneSedeLegale;
	}
	public String getProvSedeLegale() {
		return provSedeLegale;
	}
	public void setProvSedeLegale(String provSedeLegale) {
		this.provSedeLegale = provSedeLegale;
	}
	public String getComuneSedeIntervento() {
		return comuneSedeIntervento;
	}
	public void setComuneSedeIntervento(String comuneSedeIntervento) {
		this.comuneSedeIntervento = comuneSedeIntervento;
	}
	public String getProvSedeIntervento() {
		return provSedeIntervento;
	}
	public void setProvSedeIntervento(String provSedeIntervento) {
		this.provSedeIntervento = provSedeIntervento;
	}
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getDescBando() {
		return descBando;
	}
	public void setDescBando(String descBando) {
		this.descBando = descBando;
	}
	public BigDecimal getIdBando() {
		return idBando;
	}
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	public String getDescFormaGiuridica() {
		return descFormaGiuridica;
	}
	public void setDescFormaGiuridica(String descFormaGiuridica) {
		this.descFormaGiuridica = descFormaGiuridica;
	}
	public Date getDataUltimoControllo() {
		return dataUltimoControllo;
	}
	public void setDataUltimoControllo(Date dataUltimoControllo) {
		this.dataUltimoControllo = dataUltimoControllo;
	}
	public int getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	public void setIdFormaGiuridica(int idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	public Long getImportoValidato() {
		return importoValidato;
	}
	public void setImportoValidato(Long importoValidato) {
		this.importoValidato = importoValidato;
	}
	public String getCodVisualizzato() {
		return codVisualizzato;
	}
	public void setCodVisualizzato(String codVisualizzato) {
		this.codVisualizzato = codVisualizzato;
	}
	@Override
	public String toString() {
		return "ProgettoEstrattoCampVO [idSoggetto=" + idSoggetto + ", descTipoSede=" + descTipoSede
				+ ", sedeIntervento=" + sedeIntervento + ", codiceFiscaleSoggetto=" + codiceFiscaleSoggetto
				+ ", denominazione=" + denominazione + ", comuneSedeLegale=" + comuneSedeLegale + ", provSedeLegale="
				+ provSedeLegale + ", comuneSedeIntervento=" + comuneSedeIntervento + ", provSedeIntervento="
				+ provSedeIntervento + ", idProgetto=" + idProgetto + ", descBando=" + descBando + ", idBando="
				+ idBando + ", descFormaGiuridica=" + descFormaGiuridica + ", dataUltimoControllo="
				+ dataUltimoControllo + ", idFormaGiuridica=" + idFormaGiuridica + ", idCampionamento="
				+ idCampionamento + ", motivazione=" + motivazione + ", importoValidato=" + importoValidato + "]";
	}
	
		
	
}