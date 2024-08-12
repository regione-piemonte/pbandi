package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;
import java.math.BigDecimal;
import java.sql.Date;

public class PbandiTSportelliBandiVO extends GenericVO {
	
	private BigDecimal idSportelloBando;
	private BigDecimal progrBandoLineaIntervento;
	private String numAtto;
	private Date dtAtto;
	private Date dtApertura;
	private Date dtChiusura;
	private BigDecimal numMaxDomande;
	
	public PbandiTSportelliBandiVO() {};
	
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idSportelloBando != null) {
   			isPkValid = true;
   		}
   		return isPkValid;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid()) {
   			isValid = true;
   		}
   		return isValid;
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk = new java.util.ArrayList<String>();
		pk.add("idSportelloBando");
	    return pk;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idSportelloBando);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipolBeneficiario: " + temp + "\t\n");
	    temp = DataFilter.removeNull( progrBandoLineaIntervento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipolBeneficiario: " + temp + "\t\n");
	    temp = DataFilter.removeNull( numAtto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipolBeneficiario: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	public BigDecimal getIdSportelloBando() {
		return idSportelloBando;
	}

	public void setIdSportelloBando(BigDecimal idSportelloBando) {
		this.idSportelloBando = idSportelloBando;
	}

	public BigDecimal getProgrBandoLineaIntervento() {
		return progrBandoLineaIntervento;
	}

	public void setProgrBandoLineaIntervento(BigDecimal progrBandoLineaIntervento) {
		this.progrBandoLineaIntervento = progrBandoLineaIntervento;
	}

	public String getNumAtto() {
		return numAtto;
	}

	public void setNumAtto(String numAtto) {
		this.numAtto = numAtto;
	}

	public Date getDtAtto() {
		return dtAtto;
	}

	public void setDtAtto(Date dtAtto) {
		this.dtAtto = dtAtto;
	}

	public Date getDtApertura() {
		return dtApertura;
	}

	public void setDtApertura(Date dtApertura) {
		this.dtApertura = dtApertura;
	}

	public Date getDtChiusura() {
		return dtChiusura;
	}

	public void setDtChiusura(Date dtChiusura) {
		this.dtChiusura = dtChiusura;
	}

	public BigDecimal getNumMaxDomande() {
		return numMaxDomande;
	}

	public void setNumMaxDomande(BigDecimal numMaxDomande) {
		this.numMaxDomande = numMaxDomande;
	}

}
