
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiWAttiLiquidazioneDlVO extends GenericVO {

  	
  	private BigDecimal idAttiLiquidazioneDl;
  	
  	private String annoimp;
  	
  	private String cupliq;
  	
  	private BigDecimal nliqprec;
  	
  	private BigDecimal nrocapitolo;
  	
  	private String direzioneprovimp;
  	
  	private String trasfvoce;
  	
  	private String tipoprovimp;
  	
  	private String cigliq;
  	
  	private String direzione;
  	
  	private Date dataagg;
  	
  	private String tiporecord;
  	
  	private BigDecimal annoeser;
  	
  	private BigDecimal nroarticolo;
  	
  	private BigDecimal nroimp;
  	
  	private BigDecimal subimpegno;
  	
  	private BigDecimal nliq;
  	
  	private String tipofondo;
  	
  	private BigDecimal idAttiLiquidazioneDt;
  	
  	private String trasftipo;
  	
  	private String annobilrif;
  	
  	private String annoprovimp;
  	
  	private String flagOnline;
  	
  	private BigDecimal importo;
  	
  	private String annoprovv;
  	
  	private String nroprov;
  	
  	private String nroprovimp;
  	
  	private String stato;
  	
	public PbandiWAttiLiquidazioneDlVO() {}
  	
	public PbandiWAttiLiquidazioneDlVO (BigDecimal idAttiLiquidazioneDl) {
	
		this. idAttiLiquidazioneDl =  idAttiLiquidazioneDl;
	}
  	
	public PbandiWAttiLiquidazioneDlVO (BigDecimal idAttiLiquidazioneDl, String annoimp, String cupliq, BigDecimal nliqprec, BigDecimal nrocapitolo, String direzioneprovimp, String trasfvoce, String tipoprovimp, String cigliq, String direzione, Date dataagg, String tiporecord, BigDecimal annoeser, BigDecimal nroarticolo, BigDecimal nroimp, BigDecimal subimpegno, BigDecimal nliq, String tipofondo, BigDecimal idAttiLiquidazioneDt, String trasftipo, String annobilrif, String annoprovimp, String flagOnline, BigDecimal importo, String annoprovv, String nroprov, String nroprovimp, String stato) {
	
		this. idAttiLiquidazioneDl =  idAttiLiquidazioneDl;
		this. annoimp =  annoimp;
		this. cupliq =  cupliq;
		this. nliqprec =  nliqprec;
		this. nrocapitolo =  nrocapitolo;
		this. direzioneprovimp =  direzioneprovimp;
		this. trasfvoce =  trasfvoce;
		this. tipoprovimp =  tipoprovimp;
		this. cigliq =  cigliq;
		this. direzione =  direzione;
		this. dataagg =  dataagg;
		this. tiporecord =  tiporecord;
		this. annoeser =  annoeser;
		this. nroarticolo =  nroarticolo;
		this. nroimp =  nroimp;
		this. subimpegno =  subimpegno;
		this. nliq =  nliq;
		this. tipofondo =  tipofondo;
		this. idAttiLiquidazioneDt =  idAttiLiquidazioneDt;
		this. trasftipo =  trasftipo;
		this. annobilrif =  annobilrif;
		this. annoprovimp =  annoprovimp;
		this. flagOnline =  flagOnline;
		this. importo =  importo;
		this. annoprovv =  annoprovv;
		this. nroprov =  nroprov;
		this. nroprovimp =  nroprovimp;
		this. stato = stato;
	}
  	
  	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public BigDecimal getIdAttiLiquidazioneDl() {
		return idAttiLiquidazioneDl;
	}
	
	public void setIdAttiLiquidazioneDl(BigDecimal idAttiLiquidazioneDl) {
		this.idAttiLiquidazioneDl = idAttiLiquidazioneDl;
	}
	
	public String getAnnoimp() {
		return annoimp;
	}
	
	public void setAnnoimp(String annoimp) {
		this.annoimp = annoimp;
	}
	
	public String getCupliq() {
		return cupliq;
	}
	
	public void setCupliq(String cupliq) {
		this.cupliq = cupliq;
	}
	
	public BigDecimal getNliqprec() {
		return nliqprec;
	}
	
	public void setNliqprec(BigDecimal nliqprec) {
		this.nliqprec = nliqprec;
	}
	
	public BigDecimal getNrocapitolo() {
		return nrocapitolo;
	}
	
	public void setNrocapitolo(BigDecimal nrocapitolo) {
		this.nrocapitolo = nrocapitolo;
	}
	
	public String getDirezioneprovimp() {
		return direzioneprovimp;
	}
	
	public void setDirezioneprovimp(String direzioneprovimp) {
		this.direzioneprovimp = direzioneprovimp;
	}
	
	public String getTrasfvoce() {
		return trasfvoce;
	}
	
	public void setTrasfvoce(String trasfvoce) {
		this.trasfvoce = trasfvoce;
	}
	
	public String getTipoprovimp() {
		return tipoprovimp;
	}
	
	public void setTipoprovimp(String tipoprovimp) {
		this.tipoprovimp = tipoprovimp;
	}
	
	public String getCigliq() {
		return cigliq;
	}
	
	public void setCigliq(String cigliq) {
		this.cigliq = cigliq;
	}
	
	public String getDirezione() {
		return direzione;
	}
	
	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}
	
	public Date getDataagg() {
		return dataagg;
	}
	
	public void setDataagg(Date dataagg) {
		this.dataagg = dataagg;
	}
	
	public String getTiporecord() {
		return tiporecord;
	}
	
	public void setTiporecord(String tiporecord) {
		this.tiporecord = tiporecord;
	}
	
	public BigDecimal getAnnoeser() {
		return annoeser;
	}
	
	public void setAnnoeser(BigDecimal annoeser) {
		this.annoeser = annoeser;
	}
	
	public BigDecimal getNroarticolo() {
		return nroarticolo;
	}
	
	public void setNroarticolo(BigDecimal nroarticolo) {
		this.nroarticolo = nroarticolo;
	}
	
	public BigDecimal getNroimp() {
		return nroimp;
	}
	
	public void setNroimp(BigDecimal nroimp) {
		this.nroimp = nroimp;
	}
	
	public BigDecimal getSubimpegno() {
		return subimpegno;
	}
	
	public void setSubimpegno(BigDecimal subimpegno) {
		this.subimpegno = subimpegno;
	}
	
	public BigDecimal getNliq() {
		return nliq;
	}
	
	public void setNliq(BigDecimal nliq) {
		this.nliq = nliq;
	}
	
	public String getTipofondo() {
		return tipofondo;
	}
	
	public void setTipofondo(String tipofondo) {
		this.tipofondo = tipofondo;
	}
	
	public BigDecimal getIdAttiLiquidazioneDt() {
		return idAttiLiquidazioneDt;
	}
	
	public void setIdAttiLiquidazioneDt(BigDecimal idAttiLiquidazioneDt) {
		this.idAttiLiquidazioneDt = idAttiLiquidazioneDt;
	}
	
	public String getTrasftipo() {
		return trasftipo;
	}
	
	public void setTrasftipo(String trasftipo) {
		this.trasftipo = trasftipo;
	}
	
	public String getAnnobilrif() {
		return annobilrif;
	}
	
	public void setAnnobilrif(String annobilrif) {
		this.annobilrif = annobilrif;
	}
	
	public String getAnnoprovimp() {
		return annoprovimp;
	}
	
	public void setAnnoprovimp(String annoprovimp) {
		this.annoprovimp = annoprovimp;
	}
	
	public String getFlagOnline() {
		return flagOnline;
	}
	
	public void setFlagOnline(String flagOnline) {
		this.flagOnline = flagOnline;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}
	
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	public String getAnnoprovv() {
		return annoprovv;
	}
	
	public void setAnnoprovv(String annoprovv) {
		this.annoprovv = annoprovv;
	}
	
	public String getNroprov() {
		return nroprov;
	}
	
	public void setNroprov(String nroprov) {
		this.nroprov = nroprov;
	}
	
	public String getNroprovimp() {
		return nroprovimp;
	}
	
	public void setNroprovimp(String nroprovimp) {
		this.nroprovimp = nroprovimp;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idAttiLiquidazioneDt != null && flagOnline != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idAttiLiquidazioneDl != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttiLiquidazioneDl);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttiLiquidazioneDl: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoeser);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoeser: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoprovv);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoprovv: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nroprov);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroprov: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nrocapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nrocapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nroarticolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroarticolo: " + temp + "\t\n");
	
	    temp = DataFilter.removeNull( importo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importo: " + importo + "\t\n");
	    
	    temp = DataFilter.removeNull( annoimp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoimp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nroimp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroimp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cupliq);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cupliq: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nliqprec);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nliqprec: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( direzioneprovimp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" direzioneprovimp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( trasfvoce);
	    if (!DataFilter.isEmpty(temp)) sb.append(" trasfvoce: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoprovimp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoprovimp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cigliq);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cigliq: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( direzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" direzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataagg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataagg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tiporecord);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tiporecord: " + temp + "\t\n");	    
	    
	    temp = DataFilter.removeNull( subimpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" subimpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nliq);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nliq: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipofondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipofondo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttiLiquidazioneDt);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttiLiquidazioneDt: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( trasftipo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" trasftipo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annobilrif);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annobilrif: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoprovimp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoprovimp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagOnline);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagOnline: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nroprovimp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroprovimp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( stato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" stato: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idAttiLiquidazioneDl");
		
	    return pk;
	}
	
	
}
