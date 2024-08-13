/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTCaricaMassDocSpesaVO;


public class BeneficiarioCaricMassVO extends GenericVO {
	
	private BigDecimal idSoggetto;
	private BigDecimal idSoggettoBeneficiario;
	private String denominazioneEnteGiuridico;
	private String 	descBreveTipoAnagrafica;
	private String 	codiceFiscale;
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getDenominazioneEnteGiuridico() {
		return denominazioneEnteGiuridico;
	}
	public void setDenominazioneEnteGiuridico(String denominazioneEnteGiuridico) {
		this.denominazioneEnteGiuridico = denominazioneEnteGiuridico;
	}
	public String getDescBreveTipoAnagrafica() {
		return descBreveTipoAnagrafica;
	}
	public void setDescBreveTipoAnagrafica(String descBreveTipoAnagrafica) {
		this.descBreveTipoAnagrafica = descBreveTipoAnagrafica;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

}
