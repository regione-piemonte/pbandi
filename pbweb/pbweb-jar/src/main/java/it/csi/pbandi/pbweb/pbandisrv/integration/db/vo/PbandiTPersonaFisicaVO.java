
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTPersonaFisicaVO extends GenericVO {

  	
  	private String nome;
  	
  	private BigDecimal idTitoloStudio;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idComuneItalianoNascita;
  	
  	private String cognome;
  	
  	private String sesso;
  	
  	private Date dtNascita;
  	
  	private BigDecimal idPersonaFisica;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idComuneEsteroNascita;
  	
  	private BigDecimal idCittadinanza;
  	
  	private BigDecimal idOccupazione;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idNazioneNascita;
  	
  	private BigDecimal idSoggetto;
  	
	public PbandiTPersonaFisicaVO() {}
  	
	public PbandiTPersonaFisicaVO (BigDecimal idPersonaFisica) {
	
		this. idPersonaFisica =  idPersonaFisica;
	}
  	
	public PbandiTPersonaFisicaVO (String nome, BigDecimal idTitoloStudio, BigDecimal idUtenteAgg, Date dtInizioValidita, BigDecimal idComuneItalianoNascita, String cognome, String sesso, Date dtNascita, BigDecimal idPersonaFisica, Date dtFineValidita, BigDecimal idComuneEsteroNascita, BigDecimal idCittadinanza, BigDecimal idOccupazione, BigDecimal idUtenteIns, BigDecimal idNazioneNascita, BigDecimal idSoggetto) {
	
		this. nome =  nome;
		this. idTitoloStudio =  idTitoloStudio;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. idComuneItalianoNascita =  idComuneItalianoNascita;
		this. cognome =  cognome;
		this. sesso =  sesso;
		this. dtNascita =  dtNascita;
		this. idPersonaFisica =  idPersonaFisica;
		this. dtFineValidita =  dtFineValidita;
		this. idComuneEsteroNascita =  idComuneEsteroNascita;
		this. idCittadinanza =  idCittadinanza;
		this. idOccupazione =  idOccupazione;
		this. idUtenteIns =  idUtenteIns;
		this. idNazioneNascita =  idNazioneNascita;
		this. idSoggetto =  idSoggetto;
	}
  	
  	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public BigDecimal getIdTitoloStudio() {
		return idTitoloStudio;
	}
	
	public void setIdTitoloStudio(BigDecimal idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdComuneItalianoNascita() {
		return idComuneItalianoNascita;
	}
	
	public void setIdComuneItalianoNascita(BigDecimal idComuneItalianoNascita) {
		this.idComuneItalianoNascita = idComuneItalianoNascita;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getSesso() {
		return sesso;
	}
	
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public Date getDtNascita() {
		return dtNascita;
	}
	
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}
	
	public BigDecimal getIdPersonaFisica() {
		return idPersonaFisica;
	}
	
	public void setIdPersonaFisica(BigDecimal idPersonaFisica) {
		this.idPersonaFisica = idPersonaFisica;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdComuneEsteroNascita() {
		return idComuneEsteroNascita;
	}
	
	public void setIdComuneEsteroNascita(BigDecimal idComuneEsteroNascita) {
		this.idComuneEsteroNascita = idComuneEsteroNascita;
	}
	
	public BigDecimal getIdCittadinanza() {
		return idCittadinanza;
	}
	
	public void setIdCittadinanza(BigDecimal idCittadinanza) {
		this.idCittadinanza = idCittadinanza;
	}
	
	public BigDecimal getIdOccupazione() {
		return idOccupazione;
	}
	
	public void setIdOccupazione(BigDecimal idOccupazione) {
		this.idOccupazione = idOccupazione;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdNazioneNascita() {
		return idNazioneNascita;
	}
	
	public void setIdNazioneNascita(BigDecimal idNazioneNascita) {
		this.idNazioneNascita = idNazioneNascita;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInizioValidita != null && cognome != null && idUtenteIns != null && idSoggetto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idPersonaFisica != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( nome);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nome: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTitoloStudio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTitoloStudio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneItalianoNascita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneItalianoNascita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cognome);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cognome: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtNascita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtNascita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idPersonaFisica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idPersonaFisica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneEsteroNascita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneEsteroNascita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCittadinanza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCittadinanza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idOccupazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idOccupazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idNazioneNascita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idNazioneNascita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idPersonaFisica");
		
	    return pk;
	}
	
	
}
