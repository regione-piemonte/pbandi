
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTCspProgSedeIntervVO extends GenericVO {

  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String partitaIva;
  	
  	private String indirizzo;
  	
  	private String email;
  	
  	private BigDecimal idCspProgetto;
  	
  	private String cap;
  	
  	private BigDecimal idProvincia;
  	
  	private Date dtElaborazione;
  	
  	private BigDecimal idComune;
  	
  	private BigDecimal idNazione;
  	
  	private String fax;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idRegione;
  	
  	private String telefono;
  	
  	private BigDecimal idCspProgSedeInterv;
  	
  	private BigDecimal idComuneEstero;
  	
    // PBANDI-2408	inizio  	
  	private String civico;
  	
	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}
    // PBANDI-2408	fine
	
	public PbandiTCspProgSedeIntervVO() {}
  	
	public PbandiTCspProgSedeIntervVO (BigDecimal idCspProgSedeInterv) {
	
		this. idCspProgSedeInterv =  idCspProgSedeInterv;
	}
  	
	public PbandiTCspProgSedeIntervVO (Date dtInizioValidita, BigDecimal idUtenteAgg, String partitaIva, String indirizzo, String email, BigDecimal idCspProgetto, String cap, BigDecimal idProvincia, Date dtElaborazione, BigDecimal idComune, BigDecimal idNazione, String fax, BigDecimal idUtenteIns, BigDecimal idRegione, String telefono, BigDecimal idCspProgSedeInterv, BigDecimal idComuneEstero) {
	
		this. dtInizioValidita =  dtInizioValidita;
		this. idUtenteAgg =  idUtenteAgg;
		this. partitaIva =  partitaIva;
		this. indirizzo =  indirizzo;
		this. email =  email;
		this. idCspProgetto =  idCspProgetto;
		this. cap =  cap;
		this. idProvincia =  idProvincia;
		this. dtElaborazione =  dtElaborazione;
		this. idComune =  idComune;
		this. idNazione =  idNazione;
		this. fax =  fax;
		this. idUtenteIns =  idUtenteIns;
		this. idRegione =  idRegione;
		this. telefono =  telefono;
		this. idCspProgSedeInterv =  idCspProgSedeInterv;
		this. idComuneEstero =  idComuneEstero;
	}
  	
  	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
	
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public BigDecimal getIdCspProgetto() {
		return idCspProgetto;
	}
	
	public void setIdCspProgetto(BigDecimal idCspProgetto) {
		this.idCspProgetto = idCspProgetto;
	}
	
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public BigDecimal getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(BigDecimal idProvincia) {
		this.idProvincia = idProvincia;
	}
	
	public Date getDtElaborazione() {
		return dtElaborazione;
	}
	
	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}
	
	public BigDecimal getIdComune() {
		return idComune;
	}
	
	public void setIdComune(BigDecimal idComune) {
		this.idComune = idComune;
	}
	
	public BigDecimal getIdNazione() {
		return idNazione;
	}
	
	public void setIdNazione(BigDecimal idNazione) {
		this.idNazione = idNazione;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdRegione() {
		return idRegione;
	}
	
	public void setIdRegione(BigDecimal idRegione) {
		this.idRegione = idRegione;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public BigDecimal getIdCspProgSedeInterv() {
		return idCspProgSedeInterv;
	}
	
	public void setIdCspProgSedeInterv(BigDecimal idCspProgSedeInterv) {
		this.idCspProgSedeInterv = idCspProgSedeInterv;
	}
	
	public BigDecimal getIdComuneEstero() {
		return idComuneEstero;
	}
	
	public void setIdComuneEstero(BigDecimal idComuneEstero) {
		this.idComuneEstero = idComuneEstero;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && idCspProgetto != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCspProgSedeInterv != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( partitaIva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" partitaIva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( indirizzo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" indirizzo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( email);
	    if (!DataFilter.isEmpty(temp)) sb.append(" email: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cap);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cap: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProvincia);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProvincia: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComune);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComune: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( fax);
	    if (!DataFilter.isEmpty(temp)) sb.append(" fax: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRegione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRegione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( telefono);
	    if (!DataFilter.isEmpty(temp)) sb.append(" telefono: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspProgSedeInterv);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspProgSedeInterv: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneEstero);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneEstero: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idCspProgSedeInterv");
		
	    return pk;
	}
	
	
}
