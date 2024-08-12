
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTEnteGiuridicoVO extends GenericVO {

  	
  	private BigDecimal idClassificazioneEnte;
  	
  	private String denominazioneEnteGiuridico;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idFormaGiuridica;
  	
  	private BigDecimal idAttivitaUic;
  	
  	private BigDecimal capitaleSociale;
  	
  	private BigDecimal idDimensioneImpresa;
  	
  	private Date dtCostituzione;
  	
  	private Date dtFineValidita;
  	
  	private BigDecimal idEnteGiuridico;
  	
  	private BigDecimal idUtenteIns;
  	
  	private Date dtUltimoEsercizioChiuso;
  	
  	private BigDecimal idSoggetto;
  	
  	private BigDecimal flagPubblicoPrivato;
  	
  	private String codUniIpa;
  	
	public PbandiTEnteGiuridicoVO() {}
  	
	public PbandiTEnteGiuridicoVO (BigDecimal idEnteGiuridico) {
	
		this. idEnteGiuridico =  idEnteGiuridico;
	}
  	
	public PbandiTEnteGiuridicoVO (BigDecimal idClassificazioneEnte, String denominazioneEnteGiuridico, BigDecimal idUtenteAgg, Date dtInizioValidita, BigDecimal idFormaGiuridica, BigDecimal idAttivitaUic, BigDecimal capitaleSociale, BigDecimal idDimensioneImpresa, Date dtCostituzione, Date dtFineValidita, BigDecimal idEnteGiuridico, BigDecimal idUtenteIns, Date dtUltimoEsercizioChiuso, BigDecimal idSoggetto, BigDecimal flagPubblicoPrivato, String codUniIpa) {
	
		this. idClassificazioneEnte =  idClassificazioneEnte;
		this. denominazioneEnteGiuridico =  denominazioneEnteGiuridico;
		this. idUtenteAgg =  idUtenteAgg;
		this. dtInizioValidita =  dtInizioValidita;
		this. idFormaGiuridica =  idFormaGiuridica;
		this. idAttivitaUic =  idAttivitaUic;
		this. capitaleSociale =  capitaleSociale;
		this. idDimensioneImpresa =  idDimensioneImpresa;
		this. dtCostituzione =  dtCostituzione;
		this. dtFineValidita =  dtFineValidita;
		this. idEnteGiuridico =  idEnteGiuridico;
		this. idUtenteIns =  idUtenteIns;
		this. dtUltimoEsercizioChiuso =  dtUltimoEsercizioChiuso;
		this. idSoggetto =  idSoggetto;
		this. flagPubblicoPrivato = flagPubblicoPrivato;
		this. codUniIpa = codUniIpa;
	}
  	
  	
	public BigDecimal getIdClassificazioneEnte() {
		return idClassificazioneEnte;
	}
	
	public void setIdClassificazioneEnte(BigDecimal idClassificazioneEnte) {
		this.idClassificazioneEnte = idClassificazioneEnte;
	}
	
	public String getDenominazioneEnteGiuridico() {
		return denominazioneEnteGiuridico;
	}
	
	public void setDenominazioneEnteGiuridico(String denominazioneEnteGiuridico) {
		this.denominazioneEnteGiuridico = denominazioneEnteGiuridico;
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
	
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	
	public BigDecimal getIdAttivitaUic() {
		return idAttivitaUic;
	}
	
	public void setIdAttivitaUic(BigDecimal idAttivitaUic) {
		this.idAttivitaUic = idAttivitaUic;
	}
	
	public BigDecimal getCapitaleSociale() {
		return capitaleSociale;
	}
	
	public void setCapitaleSociale(BigDecimal capitaleSociale) {
		this.capitaleSociale = capitaleSociale;
	}
	
	public BigDecimal getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}
	
	public void setIdDimensioneImpresa(BigDecimal idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}
	
	public Date getDtCostituzione() {
		return dtCostituzione;
	}
	
	public void setDtCostituzione(Date dtCostituzione) {
		this.dtCostituzione = dtCostituzione;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public BigDecimal getIdEnteGiuridico() {
		return idEnteGiuridico;
	}
	
	public void setIdEnteGiuridico(BigDecimal idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public Date getDtUltimoEsercizioChiuso() {
		return dtUltimoEsercizioChiuso;
	}
	
	public void setDtUltimoEsercizioChiuso(Date dtUltimoEsercizioChiuso) {
		this.dtUltimoEsercizioChiuso = dtUltimoEsercizioChiuso;
	}
	
	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}
	
	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	
	public BigDecimal getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}

	public void setFlagPubblicoPrivato(BigDecimal flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}

	public String getCodUniIpa() {
		return codUniIpa;
	}

	public void setCodUniIpa(String codUniIpa) {
		this.codUniIpa = codUniIpa;
	}

	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && denominazioneEnteGiuridico != null && dtInizioValidita != null && idUtenteIns != null && idSoggetto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idEnteGiuridico != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idClassificazioneEnte);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idClassificazioneEnte: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( denominazioneEnteGiuridico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" denominazioneEnteGiuridico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaUic);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaUic: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( capitaleSociale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" capitaleSociale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneImpresa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneImpresa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtCostituzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtCostituzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteGiuridico);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteGiuridico: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtUltimoEsercizioChiuso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtUltimoEsercizioChiuso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagPubblicoPrivato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagPubblicoPrivato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codUniIpa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codUniIpa: " + temp + "\t\n");
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idEnteGiuridico");
		
	    return pk;
	}
	
	
	
}
