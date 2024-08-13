/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

import it.csi.pbandi.pbweb.pbandisrv.integration.db.vo.PbandiTCaricaMassDocSpesaVO;


public class DocScartatoCaricMassVO extends GenericVO {
	
	
	private String codiceErrore;
	private String documentoDiSpesa;
	private Date dtValidazione;
	private String erroreWarning;
	private BigDecimal idCaricaMassDocSpesa;
	private BigDecimal idScartiCaricaMassDs;
    private String rigaErrore;
	public BigDecimal getIdCaricaMassDocSpesa() {
		return idCaricaMassDocSpesa;
	}
	public void setIdCaricaMassDocSpesa(BigDecimal idCaricaMassDocSpesa) {
		this.idCaricaMassDocSpesa = idCaricaMassDocSpesa;
	}
	public BigDecimal getIdScartiCaricaMassDs() {
		return idScartiCaricaMassDs;
	}
	public void setIdScartiCaricaMassDs(BigDecimal idScartiCaricaMassDs) {
		this.idScartiCaricaMassDs = idScartiCaricaMassDs;
	}
	public String getRigaErrore() {
		return rigaErrore;
	}
	public void setRigaErrore(String rigaErrore) {
		this.rigaErrore = rigaErrore;
	}
	public String getCodiceErrore() {
		return codiceErrore;
	}
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	public Date getDtValidazione() {
		return dtValidazione;
	}
	public void setDtValidazione(Date dtValidazione) {
		this.dtValidazione = dtValidazione;
	}
	public String getErroreWarning() {
		return erroreWarning;
	}
	public void setErroreWarning(String erroreWarning) {
		this.erroreWarning = erroreWarning;
	}
	public String getDocumentoDiSpesa() {
		return documentoDiSpesa;
	}
	public void setDocumentoDiSpesa(String documentoDiSpesa) {
		this.documentoDiSpesa = documentoDiSpesa;
	}


}
