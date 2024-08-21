/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestioneincarichi;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class RappresentanteLegaleGestioneIncarichiVO extends GenericVO {
	        
	private BigDecimal idSoggettoBeneficiario;
	private BigDecimal idSoggettoDelegante;
	private BigDecimal idTipoSoggettoCorrelato;
	private String descBreveTipoSoggCorrelato;
	private String descTipoSoggettoCorrelato;
	private String codiceFiscaleBeneficiario;
	
	
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	
	public BigDecimal getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	public void setIdTipoSoggettoCorrelato(BigDecimal idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
	}
	public String getDescBreveTipoSoggCorrelato() {
		return descBreveTipoSoggCorrelato;
	}
	public void setDescBreveTipoSoggCorrelato(String descBreveTipoSoggCorrelato) {
		this.descBreveTipoSoggCorrelato = descBreveTipoSoggCorrelato;
	}
	public String getDescTipoSoggettoCorrelato() {
		return descTipoSoggettoCorrelato;
	}
	public void setDescTipoSoggettoCorrelato(String descTipoSoggettoCorrelato) {
		this.descTipoSoggettoCorrelato = descTipoSoggettoCorrelato;
	}
	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}
	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}
	public void setIdSoggettoDelegante(BigDecimal idSoggettoDelegante) {
		this.idSoggettoDelegante = idSoggettoDelegante;
	}
	public BigDecimal getIdSoggettoDelegante() {
		return idSoggettoDelegante;
	}

}
