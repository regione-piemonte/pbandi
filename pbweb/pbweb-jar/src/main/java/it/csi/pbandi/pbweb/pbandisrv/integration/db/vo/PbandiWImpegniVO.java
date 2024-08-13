/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiWImpegniVO extends GenericVO {

  	
  	private String annoimp;
  	
  	private String tipoprov;
  	
  	private String trasftipo;
  	
  	private BigDecimal nroarticolo;
  	
  	private BigDecimal importoattuale;
  	
  	private BigDecimal nrocapitolo;
  	
  	private BigDecimal importoiniziale;
  	
  	private BigDecimal conteggiomandati;
  	
  	private String descrcapitolo;
  	
  	private String annoprovv;
  	
  	private String provcap;
  	
  	private String tipoforn;
  	
  	private String flagnoprov;
  	
  	private BigDecimal idImpegni;
  	
  	private String descimpegno;
  	
  	private String direzione;
  	
  	private BigDecimal displiq;
  	
  	private String annoperente;
  	
  	private String direzioneprovenienzacapitolo;
  	
  	private String nroprov;
  	
  	private String ragsoc;
  	
  	private String trasfvoce;
  	
  	private Date datains;
  	
  	private String codFiscale;
  	
  	private String cup;
  	
  	private String cig;
  	
  	private String tipofondo;
  	
  	private Date dataprov;
  	
  	private String direzionedel;
  	
  	private BigDecimal totaleliq;
  	
  	private String flagOnline;
  	
  	private BigDecimal nroperente;
  	
  	private BigDecimal nrocapitoloorig;
  	
  	private BigDecimal nroimp;
  	
  	private BigDecimal totalepagato;
  	
  	private BigDecimal codben;
  	
  	private String flagPf;
  	
  	private String pIva;
  	
  	private String desctipoforn;
  	
  	private Date dataagg;
  	
  	private BigDecimal annoesercizio;
  	
  	private String stato;
  	
	public PbandiWImpegniVO() {}
  	
	public PbandiWImpegniVO (BigDecimal idImpegni) {
	
		this. idImpegni =  idImpegni;
	}
  	
	public PbandiWImpegniVO (String annoimp, String tipoprov, String trasftipo, BigDecimal nroarticolo, BigDecimal importoattuale, BigDecimal nrocapitolo, BigDecimal importoiniziale, BigDecimal conteggiomandati, String descrcapitolo, String annoprovv, String provcap, String tipoforn, String flagnoprov, BigDecimal idImpegni, String descimpegno, String direzione, BigDecimal displiq, String annoperente, String direzioneprovenienzacapitolo, String nroprov, String ragsoc, String trasfvoce, Date datains, String codFiscale, String cup, String cig, String tipofondo, Date dataprov, String direzionedel, BigDecimal totaleliq, String flagOnline, BigDecimal nroperente, BigDecimal nrocapitoloorig, BigDecimal nroimp, BigDecimal totalepagato, BigDecimal codben, String flagPf, String pIva, String desctipoforn, Date dataagg, BigDecimal annoesercizio, String stato) {
	
		this. annoimp =  annoimp;
		this. tipoprov =  tipoprov;
		this. trasftipo =  trasftipo;
		this. nroarticolo =  nroarticolo;
		this. importoattuale =  importoattuale;
		this. nrocapitolo =  nrocapitolo;
		this. importoiniziale =  importoiniziale;
		this. conteggiomandati =  conteggiomandati;
		this. descrcapitolo =  descrcapitolo;
		this. annoprovv =  annoprovv;
		this. provcap =  provcap;
		this. tipoforn =  tipoforn;
		this. flagnoprov =  flagnoprov;
		this. idImpegni =  idImpegni;
		this. descimpegno =  descimpegno;
		this. direzione =  direzione;
		this. displiq =  displiq;
		this. annoperente =  annoperente;
		this. direzioneprovenienzacapitolo =  direzioneprovenienzacapitolo;
		this. nroprov =  nroprov;
		this. ragsoc =  ragsoc;
		this. trasfvoce =  trasfvoce;
		this. datains =  datains;
		this. codFiscale =  codFiscale;
		this. cup =  cup;
		this. cig =  cig;
		this. tipofondo =  tipofondo;
		this. dataprov =  dataprov;
		this. direzionedel =  direzionedel;
		this. totaleliq =  totaleliq;
		this. flagOnline =  flagOnline;
		this. nroperente =  nroperente;
		this. nrocapitoloorig =  nrocapitoloorig;
		this. nroimp =  nroimp;
		this. totalepagato =  totalepagato;
		this. codben =  codben;
		this. flagPf =  flagPf;
		this. pIva =  pIva;
		this. desctipoforn =  desctipoforn;
		this. dataagg =  dataagg;
		this. annoesercizio =  annoesercizio;
		this. stato =  stato;
	}
  	
  	
	public String getAnnoimp() {
		return annoimp;
	}
	
	public void setAnnoimp(String annoimp) {
		this.annoimp = annoimp;
	}
	
	public String getTipoprov() {
		return tipoprov;
	}
	
	public void setTipoprov(String tipoprov) {
		this.tipoprov = tipoprov;
	}
	
	public String getTrasftipo() {
		return trasftipo;
	}
	
	public void setTrasftipo(String trasftipo) {
		this.trasftipo = trasftipo;
	}
	
	public BigDecimal getNroarticolo() {
		return nroarticolo;
	}
	
	public void setNroarticolo(BigDecimal nroarticolo) {
		this.nroarticolo = nroarticolo;
	}
	
	public BigDecimal getImportoattuale() {
		return importoattuale;
	}
	
	public void setImportoattuale(BigDecimal importoattuale) {
		this.importoattuale = importoattuale;
	}
	
	public BigDecimal getNrocapitolo() {
		return nrocapitolo;
	}
	
	public void setNrocapitolo(BigDecimal nrocapitolo) {
		this.nrocapitolo = nrocapitolo;
	}
	
	public BigDecimal getImportoiniziale() {
		return importoiniziale;
	}
	
	public void setImportoiniziale(BigDecimal importoiniziale) {
		this.importoiniziale = importoiniziale;
	}
	
	public BigDecimal getConteggiomandati() {
		return conteggiomandati;
	}
	
	public void setConteggiomandati(BigDecimal conteggiomandati) {
		this.conteggiomandati = conteggiomandati;
	}
	
	public String getDescrcapitolo() {
		return descrcapitolo;
	}
	
	public void setDescrcapitolo(String descrcapitolo) {
		this.descrcapitolo = descrcapitolo;
	}
	
	public String getAnnoprovv() {
		return annoprovv;
	}
	
	public void setAnnoprovv(String annoprovv) {
		this.annoprovv = annoprovv;
	}
	
	public String getProvcap() {
		return provcap;
	}
	
	public void setProvcap(String provcap) {
		this.provcap = provcap;
	}
	
	public String getTipoforn() {
		return tipoforn;
	}
	
	public void setTipoforn(String tipoforn) {
		this.tipoforn = tipoforn;
	}
	
	public String getFlagnoprov() {
		return flagnoprov;
	}
	
	public void setFlagnoprov(String flagnoprov) {
		this.flagnoprov = flagnoprov;
	}
	
	public BigDecimal getIdImpegni() {
		return idImpegni;
	}
	
	public void setIdImpegni(BigDecimal idImpegni) {
		this.idImpegni = idImpegni;
	}
	
	public String getDescimpegno() {
		return descimpegno;
	}
	
	public void setDescimpegno(String descimpegno) {
		this.descimpegno = descimpegno;
	}
	
	public String getDirezione() {
		return direzione;
	}
	
	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}
	
	public BigDecimal getDispliq() {
		return displiq;
	}
	
	public void setDispliq(BigDecimal displiq) {
		this.displiq = displiq;
	}
	
	public String getAnnoperente() {
		return annoperente;
	}
	
	public void setAnnoperente(String annoperente) {
		this.annoperente = annoperente;
	}
	
	public String getDirezioneprovenienzacapitolo() {
		return direzioneprovenienzacapitolo;
	}
	
	public void setDirezioneprovenienzacapitolo(String direzioneprovenienzacapitolo) {
		this.direzioneprovenienzacapitolo = direzioneprovenienzacapitolo;
	}
	
	public String getNroprov() {
		return nroprov;
	}
	
	public void setNroprov(String nroprov) {
		this.nroprov = nroprov;
	}
	
	public String getRagsoc() {
		return ragsoc;
	}
	
	public void setRagsoc(String ragsoc) {
		this.ragsoc = ragsoc;
	}
	
	public String getTrasfvoce() {
		return trasfvoce;
	}
	
	public void setTrasfvoce(String trasfvoce) {
		this.trasfvoce = trasfvoce;
	}
	
	public Date getDatains() {
		return datains;
	}
	
	public void setDatains(Date datains) {
		this.datains = datains;
	}
	
	public String getCodFiscale() {
		return codFiscale;
	}
	
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	
	public String getCup() {
		return cup;
	}
	
	public void setCup(String cup) {
		this.cup = cup;
	}
	
	public String getCig() {
		return cig;
	}
	
	public void setCig(String cig) {
		this.cig = cig;
	}
	
	public String getTipofondo() {
		return tipofondo;
	}
	
	public void setTipofondo(String tipofondo) {
		this.tipofondo = tipofondo;
	}
	
	public Date getDataprov() {
		return dataprov;
	}
	
	public void setDataprov(Date dataprov) {
		this.dataprov = dataprov;
	}
	
	public String getDirezionedel() {
		return direzionedel;
	}
	
	public void setDirezionedel(String direzionedel) {
		this.direzionedel = direzionedel;
	}
	
	public BigDecimal getTotaleliq() {
		return totaleliq;
	}
	
	public void setTotaleliq(BigDecimal totaleliq) {
		this.totaleliq = totaleliq;
	}
	
	public String getFlagOnline() {
		return flagOnline;
	}
	
	public void setFlagOnline(String flagOnline) {
		this.flagOnline = flagOnline;
	}
	
	public BigDecimal getNroperente() {
		return nroperente;
	}
	
	public void setNroperente(BigDecimal nroperente) {
		this.nroperente = nroperente;
	}
	
	public BigDecimal getNrocapitoloorig() {
		return nrocapitoloorig;
	}
	
	public void setNrocapitoloorig(BigDecimal nrocapitoloorig) {
		this.nrocapitoloorig = nrocapitoloorig;
	}
	
	public BigDecimal getNroimp() {
		return nroimp;
	}
	
	public void setNroimp(BigDecimal nroimp) {
		this.nroimp = nroimp;
	}
	
	public BigDecimal getTotalepagato() {
		return totalepagato;
	}
	
	public void setTotalepagato(BigDecimal totalepagato) {
		this.totalepagato = totalepagato;
	}
	
	public BigDecimal getCodben() {
		return codben;
	}
	
	public void setCodben(BigDecimal codben) {
		this.codben = codben;
	}
	
	public String getFlagPf() {
		return flagPf;
	}
	
	public void setFlagPf(String flagPf) {
		this.flagPf = flagPf;
	}
	
	public String getPIva() {
		return pIva;
	}
	
	public void setPIva(String pIva) {
		this.pIva = pIva;
	}
	
	public String getDesctipoforn() {
		return desctipoforn;
	}
	
	public void setDesctipoforn(String desctipoforn) {
		this.desctipoforn = desctipoforn;
	}
	
	public Date getDataagg() {
		return dataagg;
	}
	
	public void setDataagg(Date dataagg) {
		this.dataagg = dataagg;
	}
	
	public BigDecimal getAnnoesercizio() {
		return annoesercizio;
	}
	
	public void setAnnoesercizio(BigDecimal annoesercizio) {
		this.annoesercizio = annoesercizio;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && flagOnline != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idImpegni != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( annoimp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoimp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoprov);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoprov: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( trasftipo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" trasftipo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nroarticolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroarticolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoattuale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoattuale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nrocapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nrocapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoiniziale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoiniziale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( conteggiomandati);
	    if (!DataFilter.isEmpty(temp)) sb.append(" conteggiomandati: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descrcapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descrcapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoprovv);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoprovv: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( provcap);
	    if (!DataFilter.isEmpty(temp)) sb.append(" provcap: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipoforn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipoforn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagnoprov);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagnoprov: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idImpegni);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idImpegni: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( descimpegno);
	    if (!DataFilter.isEmpty(temp)) sb.append(" descimpegno: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( direzione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" direzione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( displiq);
	    if (!DataFilter.isEmpty(temp)) sb.append(" displiq: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoperente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoperente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( direzioneprovenienzacapitolo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" direzioneprovenienzacapitolo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nroprov);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroprov: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( ragsoc);
	    if (!DataFilter.isEmpty(temp)) sb.append(" ragsoc: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( trasfvoce);
	    if (!DataFilter.isEmpty(temp)) sb.append(" trasfvoce: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( datains);
	    if (!DataFilter.isEmpty(temp)) sb.append(" datains: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codFiscale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codFiscale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cup);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cup: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cig: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( tipofondo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" tipofondo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataprov);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataprov: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( direzionedel);
	    if (!DataFilter.isEmpty(temp)) sb.append(" direzionedel: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( totaleliq);
	    if (!DataFilter.isEmpty(temp)) sb.append(" totaleliq: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagOnline);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagOnline: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nroperente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroperente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nrocapitoloorig);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nrocapitoloorig: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nroimp);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nroimp: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( totalepagato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" totalepagato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codben);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codben: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagPf);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagPf: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( pIva);
	    if (!DataFilter.isEmpty(temp)) sb.append(" pIva: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( desctipoforn);
	    if (!DataFilter.isEmpty(temp)) sb.append(" desctipoforn: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dataagg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dataagg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( annoesercizio);
	    if (!DataFilter.isEmpty(temp)) sb.append(" annoesercizio: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( stato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" stato: " + temp + "\t\n");
	    
	    return sb.toString();
	}

	@Override
	public List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("idImpegni");
	
    return pk;
	}
	
	
	
}
