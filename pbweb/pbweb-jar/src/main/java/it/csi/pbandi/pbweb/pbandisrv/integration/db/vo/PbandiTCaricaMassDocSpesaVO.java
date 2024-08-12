
package it.csi.pbandi.pbweb.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import it.csi.pbandi.pbweb.pbandisrv.integration.util.DataFilter;



public class PbandiTCaricaMassDocSpesaVO extends GenericVO {

  	
  	private String codiceErrore;
  	
  	private Date dtValidazione;
  	
  	private String rigaErrore;
  	
  	private Date dtInserimento;
  	
  	private String nomeFile;
  	
  	private BigDecimal idUtente;
  	
  	private String flagCaricato;
  	
  	private Date dtElaborazione;
  	
  	private BigDecimal idCaricaMassDocSpesa;
  	
  	private BigDecimal idProgetto;
  	
  	private String fileXml;
  	
  	private String erroreOracle;
  	
  	private String flagValidato;
  	
  	private BigDecimal idSoggettoBeneficiario;
  	
	public PbandiTCaricaMassDocSpesaVO() {}
  	
	public PbandiTCaricaMassDocSpesaVO (BigDecimal idCaricaMassDocSpesa) {
	
		this. idCaricaMassDocSpesa =  idCaricaMassDocSpesa;
	}
  	
	public PbandiTCaricaMassDocSpesaVO (String codiceErrore, Date dtValidazione, String rigaErrore, Date dtInserimento, String nomeFile, BigDecimal idUtente, String flagCaricato, Date dtElaborazione, BigDecimal idCaricaMassDocSpesa, BigDecimal idProgetto, String fileXml, String erroreOracle, String flagValidato, BigDecimal idSoggettoBeneficiario) {
	
		this. codiceErrore =  codiceErrore;
		this. dtValidazione =  dtValidazione;
		this. rigaErrore =  rigaErrore;
		this. dtInserimento =  dtInserimento;
		this. nomeFile =  nomeFile;
		this. idUtente =  idUtente;
		this. flagCaricato =  flagCaricato;
		this. dtElaborazione =  dtElaborazione;
		this. idCaricaMassDocSpesa =  idCaricaMassDocSpesa;
		this. idProgetto =  idProgetto;
		this. fileXml =  fileXml;
		this. erroreOracle =  erroreOracle;
		this. flagValidato =  flagValidato;
		this. idSoggettoBeneficiario =  idSoggettoBeneficiario;
	}
  	
  	
	public String getCodiceErrore() {
		return codiceErrore;
	}
	
	public void setCodiceErrore(String codiceErrore) {
		this.codiceErrore = codiceErrore;
	}
	
	public Date getDtValidazione() {
		return dtValidazione;
	}
	
	public void setDtValidazione(Date dtValidazione) {
		this.dtValidazione = dtValidazione;
	}
	
	public String getRigaErrore() {
		return rigaErrore;
	}
	
	public void setRigaErrore(String rigaErrore) {
		this.rigaErrore = rigaErrore;
	}
	
	public Date getDtInserimento() {
		return dtInserimento;
	}
	
	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}
	
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	public BigDecimal getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(BigDecimal idUtente) {
		this.idUtente = idUtente;
	}
	
	public String getFlagCaricato() {
		return flagCaricato;
	}
	
	public void setFlagCaricato(String flagCaricato) {
		this.flagCaricato = flagCaricato;
	}
	
	public Date getDtElaborazione() {
		return dtElaborazione;
	}
	
	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}
	
	public BigDecimal getIdCaricaMassDocSpesa() {
		return idCaricaMassDocSpesa;
	}
	
	public void setIdCaricaMassDocSpesa(BigDecimal idCaricaMassDocSpesa) {
		this.idCaricaMassDocSpesa = idCaricaMassDocSpesa;
	}
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	public String getFileXml() {
		return fileXml;
	}
	
	public void setFileXml(String fileXml) {
		this.fileXml = fileXml;
	}
	
	public String getErroreOracle() {
		return erroreOracle;
	}
	
	public void setErroreOracle(String erroreOracle) {
		this.erroreOracle = erroreOracle;
	}
	
	public String getFlagValidato() {
		return flagValidato;
	}
	
	public void setFlagValidato(String flagValidato) {
		this.flagValidato = flagValidato;
	}
	
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && dtInserimento != null && nomeFile != null && idUtente != null && flagCaricato != null && fileXml != null && flagValidato != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCaricaMassDocSpesa != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceErrore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtValidazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtValidazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( rigaErrore);
	    if (!DataFilter.isEmpty(temp)) sb.append(" rigaErrore: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInserimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInserimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nomeFile);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nomeFile: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtente);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtente: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagCaricato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagCaricato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCaricaMassDocSpesa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCaricaMassDocSpesa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( fileXml);
	    if (!DataFilter.isEmpty(temp)) sb.append(" fileXml: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( erroreOracle);
	    if (!DataFilter.isEmpty(temp)) sb.append(" erroreOracle: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( flagValidato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" flagValidato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idSoggettoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idSoggettoBeneficiario: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	public java.util.List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
			pk.add("idCaricaMassDocSpesa");
		
	    return pk;
	}
	
	
}
