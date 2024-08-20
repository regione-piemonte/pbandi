/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.search;

import java.sql.Date;
import java.util.List;

public class AnagraficaBeneficiarioVO {
	

    //ELENCO DOMANDE E PROGETTI
	private String codiceFondo;
	private String numeroDomanda;
	private String statoDomanda;
	private String codiceProgetto;
	private String statoProgetto ;
	
	

    
	//ANAGRAFICA BENEFICIARIO - SEDE LEGALE - ISCRIZIONE
	private String ruolo; 
	private Long idIscrizione;
    private String numIscrizione;            
    private String flagIscrizione;
    private Long idTipoIscrizione;
    private Long idProvinciaIscrizione;
    private Long idIndirizzo;
    private String descIndirizzo;            
    private String cap;                         
    private String descComune;                 
    private Long idComune;                   
    private String descProvincia;              
    private Long idProvincia;                
    private String descRegione;                
    private Long idRegione;                  
    private String descNazione;                
    private Long idNazione;                  
    private String cfSoggetto;     
    private Long idTipoSogg;            
    private String codAteco;                   
    private String descAteco;                  
    private Long idSoggetto;                 
    private String ragSoc;
    private Date dtCostituzione;             
    private Long capSociale;            
    private Long idAttIuc;             
    private Long idFormaGiuridica;          
    private Long idClassEnte;     
    private Long idDimensioneImpresa;       
    private Long idEnteGiuridico;           
    private Date dtInizioVal;          
    private Date dtFineVal;            
    private Date dtUltimoEseChiuso;  
    private Long idUtenteIns;               
    private Long idUtenteAgg;               
    private Long flagPubblicoPrivato;       
    private String codUniIpa;                 
    private Long idStatoAtt;           
    private Date dtValEsito;        
    private String periodoScadEse;  
    private Date dtInizioAttEsito;    
    private Date dtIscrizione;    
    private String flagRatingLeg;        
    private String descFormaGiur;        
    private String pIva; 
    private Long idSede;
    private Long idAttAteco;
    private Date dtInizioValSede;
    private String descTipoSoggetto;
    private String descTipoAnagrafica;
    private String descStatoAttivita;
    private String descSezioneSpeciale;
    private String descProvinciaIscriz; 
    private String descStatoDomanda; 
    private String descPec; 
    private String quotaPartecipazione; 
    private Long idSezioneSpeciale; 
    private Long idTipoSoggCorr; 
    private String descTipoSoggCorr; 
    private boolean datiAnagrafici; 
    private boolean  sedeLegale; 
    private boolean  attivitaEconomica; 
    private boolean  datiIscrizione; 
    private boolean  sezioneAppartenenza; 
    private Long idEnteSezione; 
    private String progSoggDomanda; 
    private String progSoggProgetto; 
    private String idSoggettoEnteGiuridico;
    private String idDomanda;
    private boolean quota;
    private String ndg; 
    private Long idRecapiti; 
    private List<Integer> campiModificati; 
    
    
    public List<Integer> getCampiModificati() {
		return campiModificati;
	}
	public void setCampiModificati(List<Integer> campiModificati) {
		this.campiModificati = campiModificati;
	}
	public Date getDtIscrizione() {
		return dtIscrizione;
	}
	public void setDtIscrizione(Date dtIscrizione) {
		this.dtIscrizione = dtIscrizione;
	}
	public Long getIdRecapiti() {
		return idRecapiti;
	}
	public void setIdRecapiti(Long idRecapiti) {
		this.idRecapiti = idRecapiti;
	}
	public String getNdg() {
		return ndg;
	}
	public void setNdg(String ndg) {
		this.ndg = ndg;
	}
	public boolean isQuota() {
		return quota;
	}
	public void setQuota(boolean quota) {
		this.quota = quota;
	}
	public String getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}
	public String getIdSoggettoEnteGiuridico() {
		return idSoggettoEnteGiuridico;
	}
	public void setIdSoggettoEnteGiuridico(String idSoggettoEnteGiuridico) {
		this.idSoggettoEnteGiuridico = idSoggettoEnteGiuridico;
	}
	public String getProgSoggDomanda() {
		return progSoggDomanda;
	}
	public void setProgSoggDomanda(String progSoggDomanda) {
		this.progSoggDomanda = progSoggDomanda;
	}
	public String getProgSoggProgetto() {
		return progSoggProgetto;
	}
	public void setProgSoggProgetto(String progSoggProgetto) {
		this.progSoggProgetto = progSoggProgetto;
	}
	public Long getIdEnteSezione() {
		return idEnteSezione;
	}
    public void setIdEnteSezione(Long idEnteSezione) {
		this.idEnteSezione = idEnteSezione;
	}
    public boolean isDatiIscrizione() {
		return datiIscrizione;
	}
    public void setDatiIscrizione(boolean datiIscrizione) {
		this.datiIscrizione = datiIscrizione;
	}
    
 
	public boolean isSedeLegale() {
		return sedeLegale;
	}

	public void setSedeLegale(boolean sedeLegale) {
		this.sedeLegale = sedeLegale;
	}

	public boolean isAttivitaEconomica() {
		return attivitaEconomica;
	}

	public void setAttivitaEconomica(boolean attivitaEconomica) {
		this.attivitaEconomica = attivitaEconomica;
	}

	public boolean isSezioneAppartenenza() {
		return sezioneAppartenenza;
	}

	public void setSezioneAppartenenza(boolean sezioneAppartenenza) {
		this.sezioneAppartenenza = sezioneAppartenenza;
	}

	public void setDatiAnagrafici(boolean datiAnagrafici) {
		this.datiAnagrafici = datiAnagrafici;
	}
    
    public boolean isDatiAnagrafici() {
		return datiAnagrafici;
	}

	public Long getIdTipoSoggCorr() {
		return idTipoSoggCorr;
	}
	public void setIdTipoSoggCorr(Long idTipoSoggCorr) {
		this.idTipoSoggCorr = idTipoSoggCorr;
	}
	public String getDescTipoSoggCorr() {
		return descTipoSoggCorr;
	}
	public void setDescTipoSoggCorr(String descTipoSoggCorr) {
		this.descTipoSoggCorr = descTipoSoggCorr;
	}
	public Long getIdSezioneSpeciale() {
		return idSezioneSpeciale;
	}
	public void setIdSezioneSpeciale(Long idSezioneSpeciale) {
		this.idSezioneSpeciale = idSezioneSpeciale;
	}
	public String getQuotaPartecipazione() {
		return quotaPartecipazione;
	}
	public void setQuotaPartecipazione(String quotaPartecipazione) {
		this.quotaPartecipazione = quotaPartecipazione;
	}
	public String getDescPec() {
		return descPec;
	}
    public void setDescPec(String descPec) {
		this.descPec = descPec;
	}
    
    public String getDescStatoDomanda() {
		return descStatoDomanda;
	}
	public void setDescStatoDomanda(String descStatoDomanda) {
		this.descStatoDomanda = descStatoDomanda;
	}
	public void setDescProvinciaIscriz(String descProvinciaIscriz) {
		this.descProvinciaIscriz = descProvinciaIscriz;
	}
    public String getDescProvinciaIscriz() {
		return descProvinciaIscriz;
	}
    
    public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
    public String getRuolo() {
		return ruolo;
	}
    
    


	public AnagraficaBeneficiarioVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getCodiceFondo() {
		return codiceFondo;
	}


	public void setCodiceFondo(String codiceFondo) {
		this.codiceFondo = codiceFondo;
	}


	public String getNumeroDomanda() {
		return numeroDomanda;
	}


	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}


	public String getStatoDomanda() {
		return statoDomanda;
	}


	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
	}


	public String getCodiceProgetto() {
		return codiceProgetto;
	}


	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}


	public String getStatoProgetto() {
		return statoProgetto;
	}


	public void setStatoProgetto(String statoProgetto) {
		this.statoProgetto = statoProgetto;
	}


	public String getNumIscrizione() {
		return numIscrizione;
	}


	public void setNumIscrizione(String numIscrizione) {
		this.numIscrizione = numIscrizione;
	}

	public String getDescIndirizzo() {
		return descIndirizzo;
	}


	public void setDescIndirizzo(String descIndirizzo) {
		this.descIndirizzo = descIndirizzo;
	}


	public String getCap() {
		return cap;
	}


	public void setCap(String cap) {
		this.cap = cap;
	}


	public String getDescComune() {
		return descComune;
	}


	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}


	public Long getIdComune() {
		return idComune;
	}


	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}


	public String getDescProvincia() {
		return descProvincia;
	}


	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}


	public Long getIdProvincia() {
		return idProvincia;
	}


	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}


	public String getDescRegione() {
		return descRegione;
	}


	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}


	public Long getIdRegione() {
		return idRegione;
	}


	public void setIdRegione(Long idRegione) {
		this.idRegione = idRegione;
	}


	public String getDescNazione() {
		return descNazione;
	}


	public void setDescNazione(String descNazione) {
		this.descNazione = descNazione;
	}


	public Long getIdNazione() {
		return idNazione;
	}


	public void setIdNazione(Long idNazione) {
		this.idNazione = idNazione;
	}


	public String getCfSoggetto() {
		return cfSoggetto;
	}


	public void setCfSoggetto(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}


	public Long getIdTipoSogg() {
		return idTipoSogg;
	}


	public void setIdTipoSogg(Long idTipoSogg) {
		this.idTipoSogg = idTipoSogg;
	}


	public String getCodAteco() {
		return codAteco;
	}


	public void setCodAteco(String codAteco) {
		this.codAteco = codAteco;
	}


	public String getDescAteco() {
		return descAteco;
	}


	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}


	public Long getIdSoggetto() {
		return idSoggetto;
	}


	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}


	public String getRagSoc() {
		return ragSoc;
	}


	public void setRagSoc(String ragSoc) {
		this.ragSoc = ragSoc;
	}


	public Date getDtCostituzione() {
		return dtCostituzione;
	}


	public void setDtCostituzione(Date dtCostituzione) {
		this.dtCostituzione = dtCostituzione;
	}


	public Long getCapSociale() {
		return capSociale;
	}


	public void setCapSociale(Long capSociale) {
		this.capSociale = capSociale;
	}


	public Long getIdAttIuc() {
		return idAttIuc;
	}


	public void setIdAttIuc(Long idAttIuc) {
		this.idAttIuc = idAttIuc;
	}


	public Long getIdFormaGiuridica() {
		return idFormaGiuridica;
	}


	public void setIdFormaGiuridica(Long idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}


	public Long getIdClassEnte() {
		return idClassEnte;
	}


	public void setIdClassEnte(Long idClassEnte) {
		this.idClassEnte = idClassEnte;
	}


	public Long getIdDimensioneImpresa() {
		return idDimensioneImpresa;
	}


	public void setIdDimensioneImpresa(Long idDimensioneImpresa) {
		this.idDimensioneImpresa = idDimensioneImpresa;
	}


	public Long getIdEnteGiuridico() {
		return idEnteGiuridico;
	}


	public void setIdEnteGiuridico(Long idEnteGiuridico) {
		this.idEnteGiuridico = idEnteGiuridico;
	}


	public Date getDtInizioVal() {
		return dtInizioVal;
	}


	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}


	public Date getDtFineVal() {
		return dtFineVal;
	}


	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}


	public Date getDtUltimoEseChiuso() {
		return dtUltimoEseChiuso;
	}


	public void setDtUltimoEseChiuso(Date dtUltimoEseChiuso) {
		this.dtUltimoEseChiuso = dtUltimoEseChiuso;
	}


	public Long getIdUtenteIns() {
		return idUtenteIns;
	}


	public void setIdUtenteIns(Long idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}


	public Long getIdUtenteAgg() {
		return idUtenteAgg;
	}


	public void setIdUtenteAgg(Long idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}


	public Long getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}


	public void setFlagPubblicoPrivato(Long flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}


	public String getCodUniIpa() {
		return codUniIpa;
	}


	public void setCodUniIpa(String codUniIpa) {
		this.codUniIpa = codUniIpa;
	}


	public Long getIdStatoAtt() {
		return idStatoAtt;
	}


	public void setIdStatoAtt(Long idStatoAtt) {
		this.idStatoAtt = idStatoAtt;
	}


	public Date getDtValEsito() {
		return dtValEsito;
	}


	public void setDtValEsito(Date dtValEsito) {
		this.dtValEsito = dtValEsito;
	}


	public String getPeriodoScadEse() {
		return periodoScadEse;
	}


	public void setPeriodoScadEse(String periodoScadEse) {
		this.periodoScadEse = periodoScadEse;
	}


	public Date getDtInizioAttEsito() {
		return dtInizioAttEsito;
	}


	public void setDtInizioAttEsito(Date dtInizioAttEsito) {
		this.dtInizioAttEsito = dtInizioAttEsito;
	}


	public String getFlagRatingLeg() {
		return flagRatingLeg;
	}


	public void setFlagRatingLeg(String flagRatingLeg) {
		this.flagRatingLeg = flagRatingLeg;
	}


	public String getDescFormaGiur() {
		return descFormaGiur;
	}


	public void setDescFormaGiur(String descFormaGiur) {
		this.descFormaGiur = descFormaGiur;
	}



	public String getpIva() {
		return pIva;
	}


	public void setpIva(String pIva) {
		this.pIva = pIva;
	}


	public Long getIdIndirizzo() {
		return idIndirizzo;
	}


	public void setIdIndirizzo(Long idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}


	public Long getIdSede() {
		return idSede;
	}


	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}


	public Long getIdAttAteco() {
		return idAttAteco;
	}


	public void setIdAttAteco(Long idAttAteco) {
		this.idAttAteco = idAttAteco;
	}


	public Date getDtInizioValSede() {
		return dtInizioValSede;
	}


	public void setDtInizioValSede(Date dtInizioValSede) {
		this.dtInizioValSede = dtInizioValSede;
	}


	public Long getIdIscrizione() {
		return idIscrizione;
	}


	public void setIdIscrizione(Long idIscrizione) {
		this.idIscrizione = idIscrizione;
	}


	public String getFlagIscrizione() {
		return flagIscrizione;
	}


	public void setFlagIscrizione(String flagIscrizione) {
		this.flagIscrizione = flagIscrizione;
	}


	public Long getIdTipoIscrizione() {
		return idTipoIscrizione;
	}


	public void setIdTipoIscrizione(Long idTipoIscrizione) {
		this.idTipoIscrizione = idTipoIscrizione;
	}


	public Long getIdProvinciaIscrizione() {
		return idProvinciaIscrizione;
	}


	public void setIdProvinciaIscrizione(Long idProvinciaIscrizione) {
		this.idProvinciaIscrizione = idProvinciaIscrizione;
	}


	public String getDescTipoSoggetto() {
		return descTipoSoggetto;
	}


	public void setDescTipoSoggetto(String descTipoSoggetto) {
		this.descTipoSoggetto = descTipoSoggetto;
	}


	public String getDescTipoAnagrafica() {
		return descTipoAnagrafica;
	}


	public void setDescTipoAnagrafica(String descTipoAnagrafica) {
		this.descTipoAnagrafica = descTipoAnagrafica;
	}


	public String getDescStatoAttivita() {
		return descStatoAttivita;
	}


	public void setDescStatoAttivita(String descStatoAttivita) {
		this.descStatoAttivita = descStatoAttivita;
	}


	public String getDescSezioneSpeciale() {
		return descSezioneSpeciale;
	}


	public void setDescSezioneSpeciale(String descSezioneSpeciale) {
		this.descSezioneSpeciale = descSezioneSpeciale;
	}


	
    
}
