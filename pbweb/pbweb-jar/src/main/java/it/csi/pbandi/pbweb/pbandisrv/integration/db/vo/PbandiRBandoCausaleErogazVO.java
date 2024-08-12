
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiRBandoCausaleErogazVO extends GenericVO {

  	
  	private BigDecimal idBando;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal percSogliaSpesaQuietanzata;
  	
  	private BigDecimal idFormaGiuridica;
  	
  	private BigDecimal idCausaleErogazione;
  	
  	private BigDecimal progrBandoCausaleErogaz;
  	
  	private BigDecimal percErogazione;
  	
  	private BigDecimal idDimensioneImpresa;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal percLimite;
  	
	public PbandiRBandoCausaleErogazVO() {}
  	
	public PbandiRBandoCausaleErogazVO (BigDecimal progrBandoCausaleErogaz) {
	
		this. progrBandoCausaleErogaz =  progrBandoCausaleErogaz;
	}
  	
	public PbandiRBandoCausaleErogazVO (BigDecimal idBando, BigDecimal idUtenteAgg, BigDecimal percSogliaSpesaQuietanzata, BigDecimal idFormaGiuridica, BigDecimal idCausaleErogazione, BigDecimal progrBandoCausaleErogaz, BigDecimal percErogazione, BigDecimal idDimensioneImpresa, BigDecimal idUtenteIns, BigDecimal percLimite) {
	
		this. idBando =  idBando;
		this. idUtenteAgg =  idUtenteAgg;
		this. percSogliaSpesaQuietanzata =  percSogliaSpesaQuietanzata;
		this. idFormaGiuridica =  idFormaGiuridica;
		this. idCausaleErogazione =  idCausaleErogazione;
		this. progrBandoCausaleErogaz =  progrBandoCausaleErogaz;
		this. percErogazione =  percErogazione;
		this. idDimensioneImpresa =  idDimensioneImpresa;
		this. idUtenteIns =  idUtenteIns;
		this. percLimite =  percLimite;
	}
  	
  	
	public BigDecimal getIdBando() {
		return idBando;
	}
	
	public void setIdBando(BigDecimal idBando) {
		this.idBando = idBando;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getPercSogliaSpesaQuietanzata() {
		return percSogliaSpesaQuietanzata;
	}
	
	public void setPercSogliaSpesaQuietanzata(BigDecimal percSogliaSpesaQuietanzata) {
		this.percSogliaSpesaQuietanzata = percSogliaSpesaQuietanzata;
	}
	
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	
	public BigDecimal getProgrBandoCausaleErogaz() {
		return progrBandoCausaleErogaz;
	}
	
	public void setProgrBandoCausaleErogaz(BigDecimal progrBandoCausaleErogaz) {
		this.progrBandoCausaleErogaz = progrBandoCausaleErogaz;
	}
	
	public BigDecimal getPercErogazione() {
		return percErogazione;
	}
	
	public void setPercErogazione(BigDecimal percErogazione) {
		this.percErogazione = percErogazione;
	}
	
	public BigDecimal getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}
	
	public void setIdDimensioneImpresa(BigDecimal idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getPercLimite() {
		return percLimite;
	}
	
	public void setPercLimite(BigDecimal percLimite) {
		this.percLimite = percLimite;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idBando != null && idCausaleErogazione != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (progrBandoCausaleErogaz != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idBando: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percSogliaSpesaQuietanzata);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percSogliaSpesaQuietanzata: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCausaleErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( progrBandoCausaleErogaz);
	    if (!DataFilter.isEmpty(temp)) sb.append(" progrBandoCausaleErogaz: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneImpresa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneImpresa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( percLimite);
	    if (!DataFilter.isEmpty(temp)) sb.append(" percLimite: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("progrBandoCausaleErogaz");
		
	    return pk;
	}
	
	
}
