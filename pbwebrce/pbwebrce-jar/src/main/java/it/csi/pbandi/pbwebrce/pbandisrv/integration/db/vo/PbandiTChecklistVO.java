/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTChecklistVO extends GenericVO {

  	
  	private Date dtControllo;
  	
  	private Date dtAggiornamento;
  	
  	private Date dtInserimento;
  	
  	private String soggettoControllore;
  	
  	private String referenteBeneficiario;
  	
  	private BigDecimal idDichiarazioneSpesa;
  	
  	private BigDecimal idDocumentoIndex;
  	
  	private BigDecimal versione;
  	
  	private BigDecimal idChecklist;
  	
  	private String flagIrregolarita;
  	
  	private String flagModol;
  	
  	//IMPORTO_IRREGOLARITA 
  	
  	private BigDecimal importoIrregolarita;
  	
	public PbandiTChecklistVO() {}
  	
	public PbandiTChecklistVO (BigDecimal idChecklist) {
	
		this. idChecklist =  idChecklist;
	}
  	
	public PbandiTChecklistVO (Date dtControllo, Date dtAggiornamento, Date dtInserimento, String soggettoControllore,String referenteBeneficiario, BigDecimal idDichiarazioneSpesa, BigDecimal idDocumentoIndex, BigDecimal versione, BigDecimal idChecklist, String flagIrregolarita) {
	
		this. dtControllo =  dtControllo;
		this. dtAggiornamento =  dtAggiornamento;
		this. dtInserimento =  dtInserimento;
		this. soggettoControllore =  soggettoControllore;
		this.setReferenteBeneficiario(referenteBeneficiario);
		this. idDichiarazioneSpesa =  idDichiarazioneSpesa;
		this. idDocumentoIndex =  idDocumentoIndex;
		this. versione =  versione;
		this. idChecklist =  idChecklist;
		this. flagIrregolarita =  flagIrregolarita;
	}
  	
  	
	public Date getDtControllo() {
		return dtControllo;
	}
	
	public void setDtControllo(Date dtControllo) {
		this.dtControllo = dtControllo;
	}
	
	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}
	
	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public String getSoggettoControllore() {
		return soggettoControllore;
	}
	
	public void setSoggettoControllore(String soggettoControllore) {
		this.soggettoControllore = soggettoControllore;
	}
	
	public BigDecimal getIdDichiarazioneSpesa() {
		return idDichiarazioneSpesa;
	}
	
	public void setIdDichiarazioneSpesa(BigDecimal idDichiarazioneSpesa) {
		this.idDichiarazioneSpesa = idDichiarazioneSpesa;
	}
	
	public BigDecimal getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	
	public void setIdDocumentoIndex(BigDecimal idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	
	public BigDecimal getVersione() {
		return versione;
	}
	
	public void setVersione(BigDecimal versione) {
		this.versione = versione;
	}
	
	public BigDecimal getIdChecklist() {
		return idChecklist;
	}
	
	public void setIdChecklist(BigDecimal idChecklist) {
		this.idChecklist = idChecklist;
	}
	
	public String getFlagIrregolarita() {
		return flagIrregolarita;
	}
	
	public void setFlagIrregolarita(String flagIrregolarita) {
		this.flagIrregolarita = flagIrregolarita;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtControllo != null && dtInserimento != null && flagIrregolarita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idChecklist != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( dtControllo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtControllo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtAggiornamento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtAggiornamento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( soggettoControllore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" soggettoControllore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( getReferenteBeneficiario());
	    if (!DataFilter.isEmpty(temp)) sb.append(" referenteBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDichiarazioneSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDichiarazioneSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDocumentoIndex);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDocumentoIndex: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( versione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" versione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idChecklist);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idChecklist: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagIrregolarita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( importoIrregolarita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" importoIrregolarita: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List<String> getPK() {
		java.util.List<String> pk=new java.util.ArrayList<String>();
		
			pk.add("idChecklist");
		
	    return pk;
	}

	public String getReferenteBeneficiario() {
		return referenteBeneficiario;
	}

	public void setReferenteBeneficiario(String referenteBeneficiario) {
		this.referenteBeneficiario = referenteBeneficiario;
	}

	public BigDecimal getImportoIrregolarita() {
		return importoIrregolarita;
	}

	public void setImportoIrregolarita(BigDecimal importoIrregolarita) {
		this.importoIrregolarita = importoIrregolarita;
	}

	public String getFlagModol() {
		return flagModol;
	}

	public void setFlagModol(String flagModol) {
		this.flagModol = flagModol;
	}
	
	public Boolean isModol(){
		return flagModol != null && flagModol.equalsIgnoreCase("S");
	}
}
