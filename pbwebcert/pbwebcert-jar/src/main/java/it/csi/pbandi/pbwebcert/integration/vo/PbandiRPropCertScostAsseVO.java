/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;

public class PbandiRPropCertScostAsseVO {
	private BigDecimal idPropostaCertificaz;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal idTipoSoggFinanziat;
  	
  	private BigDecimal valAssScostamento;
  	
  	private BigDecimal percScostamento;
  	
  	private String flagComp;
  	
	public PbandiRPropCertScostAsseVO() {}
  	
	public PbandiRPropCertScostAsseVO (BigDecimal idPropostaCertificaz, BigDecimal idLineaDiIntervento, BigDecimal idTipoSoggFinanziat) {
	
		this. idPropostaCertificaz =  idPropostaCertificaz;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
	}
  	
	public PbandiRPropCertScostAsseVO (BigDecimal idPropostaCertificaz, BigDecimal idLineaDiIntervento, BigDecimal idTipoSoggFinanziat, BigDecimal valAssScostamento, BigDecimal percScostamento, String flagComp) {
	
		this. idPropostaCertificaz =  idPropostaCertificaz;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idTipoSoggFinanziat =  idTipoSoggFinanziat;
		this. valAssScostamento =  valAssScostamento;
		this. percScostamento =  percScostamento;
		this. flagComp =  flagComp;
	}
  	
  	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public BigDecimal getIdTipoSoggFinanziat() {
		return idTipoSoggFinanziat;
	}
	
	public void setIdTipoSoggFinanziat(BigDecimal idTipoSoggFinanziat) {
		this.idTipoSoggFinanziat = idTipoSoggFinanziat;
	}
	
	public BigDecimal getValAssScostamento() {
		return valAssScostamento;
	}
	
	public void setValAssScostamento(BigDecimal valAssScostamento) {
		this.valAssScostamento = valAssScostamento;
	}
	
	public BigDecimal getPercScostamento() {
		return percScostamento;
	}
	
	public void setPercScostamento(BigDecimal percScostamento) {
		this.percScostamento = percScostamento;
	}
	
	public String getFlagComp() {
		return flagComp;
	}
	
	public void setFlagComp(String flagComp) {
		this.flagComp = flagComp;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && valAssScostamento != null && percScostamento != null && flagComp != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPropostaCertificaz != null && idLineaDiIntervento != null && idTipoSoggFinanziat != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
}
