/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;


public class PbandiRPropostaCertifLineaVO {
	private BigDecimal idPropostaCertificaz;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idUtenteIns;
  	
	public PbandiRPropostaCertifLineaVO() {}
  	
	public PbandiRPropostaCertifLineaVO (BigDecimal idPropostaCertificaz, BigDecimal idLineaDiIntervento) {
	
		this. idPropostaCertificaz =  idPropostaCertificaz;
		this. idLineaDiIntervento =  idLineaDiIntervento;
	}
  	
	public PbandiRPropostaCertifLineaVO (BigDecimal idPropostaCertificaz, BigDecimal idLineaDiIntervento, BigDecimal idUtenteAgg, BigDecimal idUtenteIns) {
	
		this. idPropostaCertificaz =  idPropostaCertificaz;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. idUtenteAgg =  idUtenteAgg;
		this. idUtenteIns =  idUtenteIns;
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
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPropostaCertificaz != null && idLineaDiIntervento != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}

}
