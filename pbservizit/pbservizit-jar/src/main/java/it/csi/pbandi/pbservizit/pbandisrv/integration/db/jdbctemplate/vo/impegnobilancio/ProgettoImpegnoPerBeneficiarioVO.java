/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.impegnobilancio;

import java.math.BigDecimal;

import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.GenericVO;

public class ProgettoImpegnoPerBeneficiarioVO extends GenericVO {
	
    private BigDecimal idProgetto;
    private BigDecimal idImpegno;
   
    /* private String codiceFiscaleSoggetto;

	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	} */

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getIdImpegno() {
		return idImpegno;
	}

	public void setIdImpegno(BigDecimal idImpegno) {
		this.idImpegno = idImpegno;
	}

}
