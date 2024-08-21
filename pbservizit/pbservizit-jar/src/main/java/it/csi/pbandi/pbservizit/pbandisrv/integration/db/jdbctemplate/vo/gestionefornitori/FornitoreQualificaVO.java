/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.gestionefornitori;

import java.util.Date;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class FornitoreQualificaVO extends GenericVO{

	static final long serialVersionUID = 1;
	
	private Double costoOrario = null;
	private java.lang.String descBreveQualifica = null;
	private Date dtFineValidita = null;
	private Date dtInizioValidita = null;
	private Long idFornitore = null;
	private Long idQualifica = null;
	private Double monteOre = null;
	private Long progrFornitoreQualifica = null;
	public Double getCostoOrario() {
		return costoOrario;
	}
	public void setCostoOrario(Double costoOrario) {
		this.costoOrario = costoOrario;
	}
	/*public Double getCostoRisorsa() {
		return costoRisorsa;
	}
	public void setCostoRisorsa(Double costoRisorsa) {
		this.costoRisorsa = costoRisorsa;
	}*/
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	public Long getIdFornitore() {
		return idFornitore;
	}
	public void setIdFornitore(Long idFornitore) {
		this.idFornitore = idFornitore;
	}
	public Long getIdQualifica() {
		return idQualifica;
	}
	public void setIdQualifica(Long idQualifica) {
		this.idQualifica = idQualifica;
	}
/*	public Double getMonteOre() {
		return monteOre;
	}
	public void setMonteOre(Double monteOre) {
		this.monteOre = monteOre;
	}*/
	public Long getProgrFornitoreQualifica() {
		return progrFornitoreQualifica;
	}
	public void setProgrFornitoreQualifica(Long progrFornitoreQualifica) {
		this.progrFornitoreQualifica = progrFornitoreQualifica;
	}
	public java.lang.String getDescBreveQualifica() {
		return descBreveQualifica;
	}
	public void setDescBreveQualifica(java.lang.String descBreveQualifica) {
		this.descBreveQualifica = descBreveQualifica;
	}
	

}
