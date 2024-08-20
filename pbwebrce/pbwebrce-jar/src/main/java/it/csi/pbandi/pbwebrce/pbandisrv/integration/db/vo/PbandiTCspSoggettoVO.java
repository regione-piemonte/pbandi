/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.pbandi.pbwebrce.pbandisrv.integration.db.vo;

import java.math.*;
import java.sql.Date;
import java.util.List;

import it.csi.pbandi.pbwebrce.pbandisrv.integration.util.DataFilter;



public class PbandiTCspSoggettoVO extends GenericVO {

  	
  	private BigDecimal idUtenteIns;
  	
  	private String nome;
  	
  	private String sesso;
  	
  	private String codiceFiscale;
  	
  	private BigDecimal idComuneEsteroResidenza;
  	
  	private BigDecimal idAteneo;
  	
  	private BigDecimal idAttivitaAteco;
  	
  	private BigDecimal idEnteCompetenza;
  	
  	private String capSedeLegale;
  	
  	private BigDecimal idComuneEsteroSedeLegale;
  	
  	private String iban;
  	
  	private BigDecimal idTipoSoggetto;
  	
  	private BigDecimal idTipoBeneficiario;
  	
  	private Date dtNascita;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private BigDecimal idComuneItalianoNascita;
  	
  	private Date dtElaborazione;
  	
  	private String indirizzoSedeLegale;
  	
  	private BigDecimal idCspProgetto;
  	
  	private String emailSedeLegale;
  	
  	private Date dtCostituzioneAzienda;
  	
  	private String emailPf;
  	
  	private String cognome;
  	
  	private BigDecimal idCspSoggetto;
  	
  	private String denominazione;
  	
  	private BigDecimal idComuneItalianoSedeLegale;
  	
  	private String faxPf;
  	
  	private String indirizzoPf;
  	
  	private BigDecimal idTipoSoggettoCorrelato;
  	
  	private String faxSedeLegale;
  	
  	private Date dtInizioValidita;
  	
  	private BigDecimal idComuneItalianoResidenza;
  	
  	private String capPf;
  	
  	private BigDecimal idFormaGiuridica;
  	
  	private String telefonoSedeLegale;
  	
  	private String partitaIvaSedeLegale;
  	
  	private BigDecimal idDimensioneImpresa;
  	
  	private BigDecimal idTipoAnagrafica;
  	
  	private BigDecimal idDipartimento;
  	
  	private BigDecimal idComuneEsteroNascita;
  	
  	private String telefonoPf;
  	
    // PBANDI-2408	inizio  	
  	private String civico;
  	
	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}
    // PBANDI-2408	fine
  	
	public PbandiTCspSoggettoVO() {}
  	
	public PbandiTCspSoggettoVO (BigDecimal idCspSoggetto) {
	
		this. idCspSoggetto =  idCspSoggetto;
	}
  	
	public PbandiTCspSoggettoVO (BigDecimal idUtenteIns, String nome, String sesso, String codiceFiscale, BigDecimal idComuneEsteroResidenza, BigDecimal idAteneo, BigDecimal idAttivitaAteco, BigDecimal idEnteCompetenza, String capSedeLegale, BigDecimal idComuneEsteroSedeLegale, String iban, BigDecimal idTipoSoggetto, BigDecimal idTipoBeneficiario, Date dtNascita, BigDecimal idUtenteAgg, BigDecimal idComuneItalianoNascita, Date dtElaborazione, String indirizzoSedeLegale, BigDecimal idCspProgetto, String emailSedeLegale, Date dtCostituzioneAzienda, String emailPf, String cognome, BigDecimal idCspSoggetto, String denominazione, BigDecimal idComuneItalianoSedeLegale, String faxPf, String indirizzoPf, BigDecimal idTipoSoggettoCorrelato, String faxSedeLegale, Date dtInizioValidita, BigDecimal idComuneItalianoResidenza, String capPf, BigDecimal idFormaGiuridica, String telefonoSedeLegale, String partitaIvaSedeLegale, BigDecimal idDimensioneImpresa, BigDecimal idTipoAnagrafica, BigDecimal idDipartimento, BigDecimal idComuneEsteroNascita, String telefonoPf) {
	
		this. idUtenteIns =  idUtenteIns;
		this. nome =  nome;
		this. sesso =  sesso;
		this. codiceFiscale =  codiceFiscale;
		this. idComuneEsteroResidenza =  idComuneEsteroResidenza;
		this. idAteneo =  idAteneo;
		this. idAttivitaAteco =  idAttivitaAteco;
		this. idEnteCompetenza =  idEnteCompetenza;
		this. capSedeLegale =  capSedeLegale;
		this. idComuneEsteroSedeLegale =  idComuneEsteroSedeLegale;
		this. iban =  iban;
		this. idTipoSoggetto =  idTipoSoggetto;
		this. idTipoBeneficiario =  idTipoBeneficiario;
		this. dtNascita =  dtNascita;
		this. idUtenteAgg =  idUtenteAgg;
		this. idComuneItalianoNascita =  idComuneItalianoNascita;
		this. dtElaborazione =  dtElaborazione;
		this. indirizzoSedeLegale =  indirizzoSedeLegale;
		this. idCspProgetto =  idCspProgetto;
		this. emailSedeLegale =  emailSedeLegale;
		this. dtCostituzioneAzienda =  dtCostituzioneAzienda;
		this. emailPf =  emailPf;
		this. cognome =  cognome;
		this. idCspSoggetto =  idCspSoggetto;
		this. denominazione =  denominazione;
		this. idComuneItalianoSedeLegale =  idComuneItalianoSedeLegale;
		this. faxPf =  faxPf;
		this. indirizzoPf =  indirizzoPf;
		this. idTipoSoggettoCorrelato =  idTipoSoggettoCorrelato;
		this. faxSedeLegale =  faxSedeLegale;
		this. dtInizioValidita =  dtInizioValidita;
		this. idComuneItalianoResidenza =  idComuneItalianoResidenza;
		this. capPf =  capPf;
		this. idFormaGiuridica =  idFormaGiuridica;
		this. telefonoSedeLegale =  telefonoSedeLegale;
		this. partitaIvaSedeLegale =  partitaIvaSedeLegale;
		this. idDimensioneImpresa =  idDimensioneImpresa;
		this. idTipoAnagrafica =  idTipoAnagrafica;
		this. idDipartimento =  idDipartimento;
		this. idComuneEsteroNascita =  idComuneEsteroNascita;
		this. telefonoPf =  telefonoPf;
	}
  	
  	
	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}
	
	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSesso() {
		return sesso;
	}
	
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public BigDecimal getIdComuneEsteroResidenza() {
		return idComuneEsteroResidenza;
	}
	
	public void setIdComuneEsteroResidenza(BigDecimal idComuneEsteroResidenza) {
		this.idComuneEsteroResidenza = idComuneEsteroResidenza;
	}
	
	public BigDecimal getIdAteneo() {
		return idAteneo;
	}
	
	public void setIdAteneo(BigDecimal idAteneo) {
		this.idAteneo = idAteneo;
	}
	
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	
	public BigDecimal getIdEnteCompetenza() {
		return idEnteCompetenza;
	}
	
	public void setIdEnteCompetenza(BigDecimal idEnteCompetenza) {
		this.idEnteCompetenza = idEnteCompetenza;
	}
	
	public String getCapSedeLegale() {
		return capSedeLegale;
	}
	
	public void setCapSedeLegale(String capSedeLegale) {
		this.capSedeLegale = capSedeLegale;
	}
	
	public BigDecimal getIdComuneEsteroSedeLegale() {
		return idComuneEsteroSedeLegale;
	}
	
	public void setIdComuneEsteroSedeLegale(BigDecimal idComuneEsteroSedeLegale) {
		this.idComuneEsteroSedeLegale = idComuneEsteroSedeLegale;
	}
	
	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public BigDecimal getIdTipoSoggetto() {
		return idTipoSoggetto;
	}
	
	public void setIdTipoSoggetto(BigDecimal idTipoSoggetto) {
		this.idTipoSoggetto = idTipoSoggetto;
	}
	
	public BigDecimal getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}
	
	public void setIdTipoBeneficiario(BigDecimal idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}
	
	public Date getDtNascita() {
		return dtNascita;
	}
	
	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}
	
	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}
	
	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}
	
	public BigDecimal getIdComuneItalianoNascita() {
		return idComuneItalianoNascita;
	}
	
	public void setIdComuneItalianoNascita(BigDecimal idComuneItalianoNascita) {
		this.idComuneItalianoNascita = idComuneItalianoNascita;
	}
	
	public Date getDtElaborazione() {
		return dtElaborazione;
	}
	
	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}
	
	public String getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}
	
	public void setIndirizzoSedeLegale(String indirizzoSedeLegale) {
		this.indirizzoSedeLegale = indirizzoSedeLegale;
	}
	
	public BigDecimal getIdCspProgetto() {
		return idCspProgetto;
	}
	
	public void setIdCspProgetto(BigDecimal idCspProgetto) {
		this.idCspProgetto = idCspProgetto;
	}
	
	public String getEmailSedeLegale() {
		return emailSedeLegale;
	}
	
	public void setEmailSedeLegale(String emailSedeLegale) {
		this.emailSedeLegale = emailSedeLegale;
	}
	
	public Date getDtCostituzioneAzienda() {
		return dtCostituzioneAzienda;
	}
	
	public void setDtCostituzioneAzienda(Date dtCostituzioneAzienda) {
		this.dtCostituzioneAzienda = dtCostituzioneAzienda;
	}
	
	public String getEmailPf() {
		return emailPf;
	}
	
	public void setEmailPf(String emailPf) {
		this.emailPf = emailPf;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public BigDecimal getIdCspSoggetto() {
		return idCspSoggetto;
	}
	
	public void setIdCspSoggetto(BigDecimal idCspSoggetto) {
		this.idCspSoggetto = idCspSoggetto;
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public BigDecimal getIdComuneItalianoSedeLegale() {
		return idComuneItalianoSedeLegale;
	}
	
	public void setIdComuneItalianoSedeLegale(BigDecimal idComuneItalianoSedeLegale) {
		this.idComuneItalianoSedeLegale = idComuneItalianoSedeLegale;
	}
	
	public String getFaxPf() {
		return faxPf;
	}
	
	public void setFaxPf(String faxPf) {
		this.faxPf = faxPf;
	}
	
	public String getIndirizzoPf() {
		return indirizzoPf;
	}
	
	public void setIndirizzoPf(String indirizzoPf) {
		this.indirizzoPf = indirizzoPf;
	}
	
	public BigDecimal getIdTipoSoggettoCorrelato() {
		return idTipoSoggettoCorrelato;
	}
	
	public void setIdTipoSoggettoCorrelato(BigDecimal idTipoSoggettoCorrelato) {
		this.idTipoSoggettoCorrelato = idTipoSoggettoCorrelato;
	}
	
	public String getFaxSedeLegale() {
		return faxSedeLegale;
	}
	
	public void setFaxSedeLegale(String faxSedeLegale) {
		this.faxSedeLegale = faxSedeLegale;
	}
	
	public Date getDtInizioValidita() {
		return dtInizioValidita;
	}
	
	public void setDtInizioValidita(Date dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	public BigDecimal getIdComuneItalianoResidenza() {
		return idComuneItalianoResidenza;
	}
	
	public void setIdComuneItalianoResidenza(BigDecimal idComuneItalianoResidenza) {
		this.idComuneItalianoResidenza = idComuneItalianoResidenza;
	}
	
	public String getCapPf() {
		return capPf;
	}
	
	public void setCapPf(String capPf) {
		this.capPf = capPf;
	}
	
	public BigDecimal getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	
	public void setIdFormaGiuridica(BigDecimal idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	
	public String getTelefonoSedeLegale() {
		return telefonoSedeLegale;
	}
	
	public void setTelefonoSedeLegale(String telefonoSedeLegale) {
		this.telefonoSedeLegale = telefonoSedeLegale;
	}
	
	public String getPartitaIvaSedeLegale() {
		return partitaIvaSedeLegale;
	}
	
	public void setPartitaIvaSedeLegale(String partitaIvaSedeLegale) {
		this.partitaIvaSedeLegale = partitaIvaSedeLegale;
	}
	
	public BigDecimal getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}
	
	public void setIdDimensioneImpresa(BigDecimal idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}
	
	public BigDecimal getIdTipoAnagrafica() {
		return idTipoAnagrafica;
	}
	
	public void setIdTipoAnagrafica(BigDecimal idTipoAnagrafica) {
		this.idTipoAnagrafica = idTipoAnagrafica;
	}
	
	public BigDecimal getIdDipartimento() {
		return idDipartimento;
	}
	
	public void setIdDipartimento(BigDecimal idDipartimento) {
		this.idDipartimento = idDipartimento;
	}
	
	public BigDecimal getIdComuneEsteroNascita() {
		return idComuneEsteroNascita;
	}
	
	public void setIdComuneEsteroNascita(BigDecimal idComuneEsteroNascita) {
		this.idComuneEsteroNascita = idComuneEsteroNascita;
	}
	
	public String getTelefonoPf() {
		return telefonoPf;
	}
	
	public void setTelefonoPf(String telefonoPf) {
		this.telefonoPf = telefonoPf;
	}
	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && idUtenteIns != null && idTipoSoggetto != null && idCspProgetto != null && dtInizioValidita != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idCspSoggetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public String toString() {
		
	    String temp = "";
	    StringBuffer sb = new StringBuffer();
	    sb.append("\t\n" + this.getClass().getName() + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteIns);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteIns: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( nome);
	    if (!DataFilter.isEmpty(temp)) sb.append(" nome: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( sesso);
	    if (!DataFilter.isEmpty(temp)) sb.append(" sesso: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( codiceFiscale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" codiceFiscale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneEsteroResidenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneEsteroResidenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAteneo);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAteneo: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idAttivitaAteco);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idAttivitaAteco: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idEnteCompetenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idEnteCompetenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( capSedeLegale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" capSedeLegale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneEsteroSedeLegale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneEsteroSedeLegale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( iban);
	    if (!DataFilter.isEmpty(temp)) sb.append(" iban: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoBeneficiario);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoBeneficiario: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtNascita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtNascita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idUtenteAgg);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idUtenteAgg: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneItalianoNascita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneItalianoNascita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtElaborazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtElaborazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( indirizzoSedeLegale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" indirizzoSedeLegale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspProgetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspProgetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( emailSedeLegale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" emailSedeLegale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtCostituzioneAzienda);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtCostituzioneAzienda: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( emailPf);
	    if (!DataFilter.isEmpty(temp)) sb.append(" emailPf: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( cognome);
	    if (!DataFilter.isEmpty(temp)) sb.append(" cognome: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idCspSoggetto);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idCspSoggetto: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( denominazione);
	    if (!DataFilter.isEmpty(temp)) sb.append(" denominazione: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneItalianoSedeLegale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneItalianoSedeLegale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( faxPf);
	    if (!DataFilter.isEmpty(temp)) sb.append(" faxPf: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( indirizzoPf);
	    if (!DataFilter.isEmpty(temp)) sb.append(" indirizzoPf: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoSoggettoCorrelato);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoSoggettoCorrelato: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( faxSedeLegale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" faxSedeLegale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( dtInizioValidita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" dtInizioValidita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneItalianoResidenza);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneItalianoResidenza: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( capPf);
	    if (!DataFilter.isEmpty(temp)) sb.append(" capPf: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idFormaGiuridica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idFormaGiuridica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( telefonoSedeLegale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" telefonoSedeLegale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( partitaIvaSedeLegale);
	    if (!DataFilter.isEmpty(temp)) sb.append(" partitaIvaSedeLegale: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDimensioneImpresa);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDimensioneImpresa: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idTipoAnagrafica);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idTipoAnagrafica: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idDipartimento);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idDipartimento: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( idComuneEsteroNascita);
	    if (!DataFilter.isEmpty(temp)) sb.append(" idComuneEsteroNascita: " + temp + "\t\n");
	    
	    temp = DataFilter.removeNull( telefonoPf);
	    if (!DataFilter.isEmpty(temp)) sb.append(" telefonoPf: " + temp + "\t\n");
	    
	    return sb.toString();
	}
	
	@Override
	public List getPK() {
		java.util.List pk=new java.util.ArrayList();
		
		pk.add("idCspSoggetto");
		
		return pk;
	}
	
}
