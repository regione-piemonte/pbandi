
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTRichiestaErogazioneVO extends GenericVO {

  	
  	private BigDecimal numeroRichiestaErogazione;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idCausaleErogazione;
  	
  	private Date dtRaggiungimentoP90;
  	
  	private Date dtRichiestaErogazione;
  	
  	private BigDecimal importoErogazioneRichiesto;
  	
  	private Date dtRaggiungimentoP30;
  	
  	private String residenzaDirettoreLavori;
  	
  	private String direttoreLavori;
  	
  	private BigDecimal idProgetto;
  	
  	private Date dtRaggiungimentoP100;
  	
  	private Date dtStipulazioneContratti;
  	
  	private Date dtInizioLavori;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idRichiestaErogazione;
  	
	public PbandiTRichiestaErogazioneVO() {}
  	
	public PbandiTRichiestaErogazioneVO (BigDecimal idRichiestaErogazione) {
	
		this. idRichiestaErogazione =  idRichiestaErogazione;
	}
  	
	public PbandiTRichiestaErogazioneVO (BigDecimal numeroRichiestaErogazione, BigDecimal idUtenteAgg, BigDecimal idCausaleErogazione, Date dtRaggiungimentoP90, Date dtRichiestaErogazione, BigDecimal importoErogazioneRichiesto, Date dtRaggiungimentoP30, String residenzaDirettoreLavori, String direttoreLavori, BigDecimal idProgetto, Date dtRaggiungimentoP100, Date dtStipulazioneContratti, Date dtInizioLavori, BigDecimal idUtenteIns, BigDecimal idRichiestaErogazione) {
	
		this. numeroRichiestaErogazione =  numeroRichiestaErogazione;
		this. idUtenteAgg =  idUtenteAgg;
		this. idCausaleErogazione =  idCausaleErogazione;
		this. dtRaggiungimentoP90 =  dtRaggiungimentoP90;
		this. dtRichiestaErogazione =  dtRichiestaErogazione;
		this. importoErogazioneRichiesto =  importoErogazioneRichiesto;
		this. dtRaggiungimentoP30 =  dtRaggiungimentoP30;
		this. residenzaDirettoreLavori =  residenzaDirettoreLavori;
		this. direttoreLavori =  direttoreLavori;
		this. idProgetto =  idProgetto;
		this. dtRaggiungimentoP100 =  dtRaggiungimentoP100;
		this. dtStipulazioneContratti =  dtStipulazioneContratti;
		this. dtInizioLavori =  dtInizioLavori;
		this. idUtenteIns =  idUtenteIns;
		this. idRichiestaErogazione =  idRichiestaErogazione;
	}
  	
  	
	public BigDecimal getNumeroRichiestaErogazione() {
		return numeroRichiestaErogazione;
	}
	
	public void setNumeroRichiestaErogazione(BigDecimal numeroRichiestaErogazione) {
		this.numeroRichiestaErogazione = numeroRichiestaErogazione;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdCausaleErogazione() {
		return idCausaleErogazione;
	}
	
	public void setIdCausaleErogazione(BigDecimal idCausaleErogazione) {
		this.idCausaleErogazione = idCausaleErogazione;
	}
	
	public Date getDtRaggiungimentoP90() {
		return dtRaggiungimentoP90;
	}
	
	public void setDtRaggiungimentoP90(Date dtRaggiungimentoP90) {
		this.dtRaggiungimentoP90 = dtRaggiungimentoP90;
	}
	
	public Date getDtRichiestaErogazione() {
		return dtRichiestaErogazione;
	}
	
	public void setDtRichiestaErogazione(Date dtRichiestaErogazione) {
		this.dtRichiestaErogazione = dtRichiestaErogazione;
	}
	
	public BigDecimal getImportoErogazioneRichiesto() {
		return importoErogazioneRichiesto;
	}
	
	public void setImportoErogazioneRichiesto(BigDecimal importoErogazioneRichiesto) {
		this.importoErogazioneRichiesto = importoErogazioneRichiesto;
	}
	
	public Date getDtRaggiungimentoP30() {
		return dtRaggiungimentoP30;
	}
	
	public void setDtRaggiungimentoP30(Date dtRaggiungimentoP30) {
		this.dtRaggiungimentoP30 = dtRaggiungimentoP30;
	}
	
	public String getResidenzaDirettoreLavori() {
		return residenzaDirettoreLavori;
	}
	
	public void setResidenzaDirettoreLavori(String residenzaDirettoreLavori) {
		this.residenzaDirettoreLavori = residenzaDirettoreLavori;
	}
	
	public String getDirettoreLavori() {
		return direttoreLavori;
	}
	
	public void setDirettoreLavori(String direttoreLavori) {
		this.direttoreLavori = direttoreLavori;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public Date getDtRaggiungimentoP100() {
		return dtRaggiungimentoP100;
	}
	
	public void setDtRaggiungimentoP100(Date dtRaggiungimentoP100) {
		this.dtRaggiungimentoP100 = dtRaggiungimentoP100;
	}
	
	public Date getDtStipulazioneContratti() {
		return dtStipulazioneContratti;
	}
	
	public void setDtStipulazioneContratti(Date dtStipulazioneContratti) {
		this.dtStipulazioneContratti = dtStipulazioneContratti;
	}
	
	public Date getDtInizioLavori() {
		return dtInizioLavori;
	}
	
	public void setDtInizioLavori(Date dtInizioLavori) {
		this.dtInizioLavori = dtInizioLavori;
	}
	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public BigDecimal getIdRichiestaErogazione() {
		return idRichiestaErogazione;
	}
	
	public void setIdRichiestaErogazione(BigDecimal idRichiestaErogazione) {
		this.idRichiestaErogazione = idRichiestaErogazione;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && numeroRichiestaErogazione != null && idCausaleErogazione != null && dtRichiestaErogazione != null && importoErogazioneRichiesto != null && idProgetto != null && idUtenteIns != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idRichiestaErogazione != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( numeroRichiestaErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" numeroRichiestaErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCausaleErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCausaleErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRaggiungimentoP90);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRaggiungimentoP90: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRichiestaErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRichiestaErogazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoErogazioneRichiesto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoErogazioneRichiesto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRaggiungimentoP30);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRaggiungimentoP30: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( residenzaDirettoreLavori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" residenzaDirettoreLavori: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( direttoreLavori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" direttoreLavori: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtRaggiungimentoP100);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtRaggiungimentoP100: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtStipulazioneContratti);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtStipulazioneContratti: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioLavori);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioLavori: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idRichiestaErogazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idRichiestaErogazione: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idRichiestaErogazione");
		
	    return pk;
	}
	
	
}
