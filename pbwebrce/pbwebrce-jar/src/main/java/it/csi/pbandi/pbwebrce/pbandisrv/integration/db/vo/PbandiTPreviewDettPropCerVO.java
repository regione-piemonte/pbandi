/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTPreviewDettPropCerVO extends GenericVO {

  	
  	private BigDecimal importoCertificazioneNetto;
  	
  	private String denominazioneBeneficiario;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String nomeBandoLinea;
  	
  	private BigDecimal importoRevoche;
  	
  	private String codiceProgetto;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal idPreviewDettPropCer;
  	
  	private BigDecimal progrBandoLineaIntervento;
  	
  	private BigDecimal importoPagamenti;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSoggettoBeneficiario;
  	
  	private BigDecimal idPropostaCertificaz;
  	
  	private String flagAttivo;
  	
	public PbandiTPreviewDettPropCerVO() {}
  	
	public PbandiTPreviewDettPropCerVO (BigDecimal idPreviewDettPropCer) {
	
		this. idPreviewDettPropCer =  idPreviewDettPropCer;
	}
  	
	public PbandiTPreviewDettPropCerVO (BigDecimal importoCertificazioneNetto, String denominazioneBeneficiario, BigDecimal idUtenteAgg, String nomeBandoLinea, BigDecimal importoRevoche, String codiceProgetto, BigDecimal idProgetto, BigDecimal idPreviewDettPropCer, BigDecimal progrBandoLineaIntervento, BigDecimal importoPagamenti, BigDecimal idUtenteIns, BigDecimal idSoggettoBeneficiario, BigDecimal idPropostaCertificaz, String flagAttivo) {
	
		this. importoCertificazioneNetto =  importoCertificazioneNetto;
		this. denominazioneBeneficiario =  denominazioneBeneficiario;
		this. idUtenteAgg =  idUtenteAgg;
		this. nomeBandoLinea =  nomeBandoLinea;
		this. importoRevoche =  importoRevoche;
		this. codiceProgetto =  codiceProgetto;
		this. idProgetto =  idProgetto;
		this. idPreviewDettPropCer =  idPreviewDettPropCer;
		this. progrBandoLineaIntervento =  progrBandoLineaIntervento;
		this. importoPagamenti =  importoPagamenti;
		this. idUtenteIns =  idUtenteIns;
		this. idSoggettoBeneficiario =  idSoggettoBeneficiario;
		this. idPropostaCertificaz =  idPropostaCertificaz;
		this. flagAttivo =  flagAttivo;
	}
  	
  	
	public BigDecimal getImportoCertificazioneNetto() {
		return importoCertificazioneNetto;
	}
	
	public void setImportoCertificazioneNetto(BigDecimal importoCertificazioneNetto) {
		this.importoCertificazioneNetto = importoCertificazioneNetto;
	}
	
	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}
	
	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}
	
	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}
	
	public BigDecimal getImportoRevoche() {
		return importoRevoche;
	}
	
	public void setImportoRevoche(BigDecimal importoRevoche) {
		this.importoRevoche = importoRevoche;
	}
	
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public BigDecimal getIdPreviewDettPropCer() {
		return idPreviewDettPropCer;
	}
	
	public void setIdPreviewDettPropCer(BigDecimal idPreviewDettPropCer) {
		this.idPreviewDettPropCer = idPreviewDettPropCer;
	}
	
	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}
	
	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}
	
	public BigDecimal getImportoPagamenti() {
		return importoPagamenti;
	}
	
	public void setImportoPagamenti(BigDecimal importoPagamenti) {
		this.importoPagamenti = importoPagamenti;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	
	public BigDecimal getIdPropostaCertificaz() {
		return idPropostaCertificaz;
	}
	
	public void setIdPropostaCertificaz(BigDecimal idPropostaCertificaz) {
		this.idPropostaCertificaz = idPropostaCertificaz;
	}
	
	public String getFlagAttivo() {
		return flagAttivo;
	}
	
	public void setFlagAttivo(String flagAttivo) {
		this.flagAttivo = flagAttivo;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && denominazioneBeneficiario != null && nomeBandoLinea != null && codiceProgetto != null && idProgetto != null && progrBandoLineaIntervento != null && idUtenteIns != null && idSoggettoBeneficiario != null && idPropostaCertificaz != null && flagAttivo != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPreviewDettPropCer != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( importoCertificazioneNetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoCertificazioneNetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( denominazioneBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" denominazioneBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeBandoLinea);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeBandoLinea: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoRevoche);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoRevoche: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPreviewDettPropCer);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPreviewDettPropCer: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoLineaIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoPagamenti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoPagamenti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPropostaCertificaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPropostaCertificaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagAttivo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagAttivo: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idPreviewDettPropCer");
		
	    return pk;
	}
	
	
}
