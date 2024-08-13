/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import it.csi.pbandi.pbweb.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweb.pbandiutil.common.StringUtil;

import java.math.BigDecimal;
import java.sql.Date;

public class SedeProgettoVO extends GenericVO {

	private BigDecimal idProgetto;
	private BigDecimal idSoggettoBeneficiario;
	private BigDecimal idSede;
	private BigDecimal progrSoggettoProgettoSede;
	private BigDecimal idTipoSede;
	private String descBreveTipoSede;
	private String descTipoSede;
	private String partitaIva;
	private String denominazione;
	private Date dtInizioValidita;
	private BigDecimal idAttivitaAteco;
	private BigDecimal idDimensioneTerritor;
	private BigDecimal idNazione;
	private String descNazione;
	private BigDecimal idRegione;
	private String descRegione;
	private BigDecimal idProvincia;
	private String descProvincia;
	private String siglaProvincia;
	private BigDecimal idComune;
	private String descComune;
	private BigDecimal idIndirizzo;
	private String descIndirizzo;
	private String civico;
	private String cap;
	private BigDecimal idRecapiti;
	private String email;
	private String fax;
	private String telefono;
	private String codIstatComune;
	private String flagSedeAmministrativa;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public BigDecimal getIdSede() {
		return idSede;
	}
	public void setIdSede(BigDecimal idSede) {
		this.idSede = idSede;
	}
	public BigDecimal getIdTipoSede() {
		return idTipoSede;
	}
	public void setIdTipoSede(BigDecimal idTipoSede) {
		this.idTipoSede = idTipoSede;
	}
	public String getDescBreveTipoSede() {
		return descBreveTipoSede;
	}
	public void setDescBreveTipoSede(String descBreveTipoSede) {
		this.descBreveTipoSede = descBreveTipoSede;
	}
	public String getDescTipoSede() {
		return descTipoSede;
	}
	public void setDescTipoSede(String descTipoSede) {
		this.descTipoSede = descTipoSede;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public BigDecimal getIdNazione() {
		return idNazione;
	}
	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	public String getDescNazione() {
		return descNazione;
	}
	public void setDescNazione(String descNazione) {
		this.descNazione = descNazione;
	}
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	public String getDescRegione() {
		return descRegione;
	}
	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getDescProvincia() {
		return descProvincia;
	}
	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}
	public BigDecimal getIdComune() {
		return idComune;
	}
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public BigDecimal getIdIndirizzo() {
		return idIndirizzo;
	}
	public void setIdIndirizzo(BigDecimal idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}
	public BigDecimal getIdRecapiti() {
		return idRecapiti;
	}
	public void setIdRecapiti(BigDecimal idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}
	public String getDescIndirizzo() {
		return descIndirizzo;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	public void setIdDimensioneTerritor(BigDecimal idDimensioneTerritor) {
		this.idDimensioneTerritor = idDimensioneTerritor;
	}
	public BigDecimal getIdDimensioneTerritor() {
		return idDimensioneTerritor;
	}
	public void setProgrSoggettoProgettoSede(BigDecimal progrSoggettoProgettoSede) {
		this.progrSoggettoProgettoSede = progrSoggettoProgettoSede;
	}
	public BigDecimal getProgrSoggettoProgettoSede() {
		return progrSoggettoProgettoSede;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCap() {
		return cap;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getCivico() {
		return civico;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public String getCodIstatComune() {
		return codIstatComune;
	}
	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public String toDescBreveSede(){
		
		String descrizioneSede = "";
		String descIndirizzo = getDescIndirizzo();
		if(StringUtil.isEmpty(descIndirizzo)) descIndirizzo="";			 			 

		String cap = getCap();
		if(StringUtil.isEmpty(cap)) cap="";	
		else cap+=" ";

		if (!StringUtil.isEmpty(descIndirizzo) && !StringUtil.isEmpty(cap)) {
			descrizioneSede = descIndirizzo;
			if (!StringUtil.isEmpty(getCivico())) {
				descIndirizzo = descIndirizzo.trim();
				String ultimoChar = String.valueOf(descIndirizzo.charAt(descIndirizzo.length()-1));
				if (!NumberUtil.isNumber(ultimoChar)) 
					descrizioneSede = descrizioneSede + " " + getCivico(); 
			}
			descrizioneSede = descrizioneSede + " - " + cap;
		}			 

		String descComune = getDescComune();
		if(StringUtil.isEmpty(descComune)) descComune="";	

		String siglaProvincia = getSiglaProvincia();
		if(StringUtil.isEmpty(siglaProvincia)) 
			siglaProvincia="";	
		if(!StringUtil.isEmpty(descComune) )
			descrizioneSede+=descComune;
		if(!StringUtil.isEmpty(siglaProvincia) )	 
			descrizioneSede+="(" +siglaProvincia+")";

		return descrizioneSede;
	}
	public String getFlagSedeAmministrativa() {
		return flagSedeAmministrativa;
	}
	public void setFlagSedeAmministrativa(String flagSedeAmministrativa) {
		this.flagSedeAmministrativa = flagSedeAmministrativa;
	}

}
