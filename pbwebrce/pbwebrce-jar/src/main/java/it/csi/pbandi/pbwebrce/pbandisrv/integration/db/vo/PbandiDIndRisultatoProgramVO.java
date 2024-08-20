/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiDIndRisultatoProgramVO extends GenericVO {

  	
  	private BigDecimal idIndRisultatoProgram;
  	
  	private Date dtFineValidita;
  	
  	private String descIndRisultatoProgramma;
  	
  	private BigDecimal idLineaDiIntervento;
  	
  	private String codIgrueT20;
  	
  	private Date dtInizioValidita;
  	
	public PbandiDIndRisultatoProgramVO() {}
  	
	public PbandiDIndRisultatoProgramVO (BigDecimal idIndRisultatoProgram) {
	
		this. idIndRisultatoProgram =  idIndRisultatoProgram;
	}
  	
	public PbandiDIndRisultatoProgramVO (BigDecimal idIndRisultatoProgram, Date dtFineValidita, String descIndRisultatoProgramma, BigDecimal idLineaDiIntervento, String codIgrueT20, Date dtInizioValidita) {
	
		this. idIndRisultatoProgram =  idIndRisultatoProgram;
		this. dtFineValidita =  dtFineValidita;
		this. descIndRisultatoProgramma =  descIndRisultatoProgramma;
		this. idLineaDiIntervento =  idLineaDiIntervento;
		this. codIgrueT20 =  codIgrueT20;
		this. dtInizioValidita =  dtInizioValidita;
	}
  	
  	
	public BigDecimal getIdIndRisultatoProgram() {
		return idIndRisultatoProgram;
	}
	
	public void setIdIndRisultatoProgram(BigDecimal idIndRisultatoProgram) {
		this.idIndRisultatoProgram = idIndRisultatoProgram;
	}
	
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	
	public String getDescIndRisultatoProgramma() {
		return descIndRisultatoProgramma;
	}
	
	public void setDescIndRisultatoProgramma(String descIndRisultatoProgramma) {
		this.descIndRisultatoProgramma = descIndRisultatoProgramma;
	}
	
	public BigDecimal getIdLineaDiIntervento() {
		return idLineaDiIntervento;
	}
	
	public void setIdLineaDiIntervento(BigDecimal idLineaDiIntervento) {
		this.idLineaDiIntervento = idLineaDiIntervento;
	}
	
	public String getCodIgrueT20() {
		return codIgrueT20;
	}
	
	public void setCodIgrueT20(String codIgrueT20) {
		this.codIgrueT20 = codIgrueT20;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && descIndRisultatoProgramma != null && idLineaDiIntervento != null && codIgrueT20 != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idIndRisultatoProgram != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idIndRisultatoProgram);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idIndRisultatoProgram: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtFineValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtFineValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descIndRisultatoProgramma);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descIndRisultatoProgramma: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idLineaDiIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idLineaDiIntervento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codIgrueT20);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codIgrueT20: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idIndRisultatoProgram");
		
	    return pk;
	}
	
	
}
