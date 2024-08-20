/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.dto.profilazione;

import java.math.BigDecimal;
import java.sql.Date;

public class StoricoPropostaVarazioneStatoCreditoDTO {
	
	private Long idVariazStatoCredito; 
	private Long idSoggProgStatoCred;
	private Long progrSoggProgetto; 
	private String nome;
	private String cognome;
	private String titoloBando; 
	private String codiceFiscale; 
	private String descStatoCredFin;
	private Date dataProposta; 
	private String nag; 
	private Long idSoggetto;
	private Long idProgetto;
	private String descNuovoStatoCred;
	private String descStatoProposta;
	private Long idStatoPropVariazCred;
	private String partitaIva;
	private String descStatAzienda; 
	private Long giorniSconf;
	private BigDecimal percSconf;
	private BigDecimal impSconfCapitale;
	private BigDecimal impSconfInteressi; 
	private BigDecimal impSconfAgev;
	private String descModalitaAgevolaz;
	private String denominazione; 
	private int idStatoCreditoProposto; 
	private int idStatoCreditoAttuale; 
	private boolean isConfirmable; 
	private String statoCreditoProposto;
	private String statoCreditoAttuale;
	private String Ndg; 
	private String codiceVisualizzatoProgetto; 
	private int  idModalitaAgevolazioneRif; 
	private int idModalitaAgevolazione;
	private String codiceFondoFinpis;
	private String flagAccettatoRifiutato; 
	
	
	
	
	public String getFlagAccettatoRifiutato() {
		return flagAccettatoRifiutato;
	}
	public void setFlagAccettatoRifiutato(String flagAccettatoRifiutato) {
		this.flagAccettatoRifiutato = flagAccettatoRifiutato;
	}
	public int getIdModalitaAgevolazioneRif() {
		return idModalitaAgevolazioneRif;
	}
	public void setIdModalitaAgevolazioneRif(int idModalitaAgevolazioneRif) {
		this.idModalitaAgevolazioneRif = idModalitaAgevolazioneRif;
	}
	public int getIdModalitaAgevolazione() {
		return idModalitaAgevolazione;
	}
	public void setIdModalitaAgevolazione(int idModalitaAgevolazione) {
		this.idModalitaAgevolazione = idModalitaAgevolazione;
	}
	public String getNdg() {
		return Ndg;
	}
	public void setNdg(String ndg) {
		Ndg = ndg;
	}
	public String getCodiceVisualizzatoProgetto() {
		return codiceVisualizzatoProgetto;
	}
	public void setCodiceVisualizzatoProgetto(String codiceVisualizzatoProgetto) {
		this.codiceVisualizzatoProgetto = codiceVisualizzatoProgetto;
	}
	public int getIdStatoCreditoProposto() {
		return idStatoCreditoProposto;
	}
	public void setIdStatoCreditoProposto(int idStatoCreditoProposto) {
		this.idStatoCreditoProposto = idStatoCreditoProposto;
	}
	public int getIdStatoCreditoAttuale() {
		return idStatoCreditoAttuale;
	}
	public void setIdStatoCreditoAttuale(int idStatoCreditoAttuale) {
		this.idStatoCreditoAttuale = idStatoCreditoAttuale;
	}
	public boolean isConfirmable() {
		return isConfirmable;
	}
	public void setConfirmable(boolean isConfirmable) {
		this.isConfirmable = isConfirmable;
	}
	public String getStatoCreditoProposto() {
		return statoCreditoProposto;
	}
	public void setStatoCreditoProposto(String statoCreditoProposto) {
		this.statoCreditoProposto = statoCreditoProposto;
	}
	public String getStatoCreditoAttuale() {
		return statoCreditoAttuale;
	}
	public void setStatoCreditoAttuale(String statoCreditoAttuale) {
		this.statoCreditoAttuale = statoCreditoAttuale;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDescModalitaAgevolaz() {
		return descModalitaAgevolaz;
	}
	public void setDescModalitaAgevolaz(String descModalitaAgevolaz) {
		this.descModalitaAgevolaz = descModalitaAgevolaz;
	}
	public Long getIdVariazStatoCredito() {
		return idVariazStatoCredito;
	}
	public void setIdVariazStatoCredito(Long idVariazStatoCredito) {
		this.idVariazStatoCredito = idVariazStatoCredito;
	}
	public Long getIdSoggProgStatoCred() {
		return idSoggProgStatoCred;
	}
	public void setIdSoggProgStatoCred(Long idSoggProgStatoCred) {
		this.idSoggProgStatoCred = idSoggProgStatoCred;
	}
	public Long getProgrSoggProgetto() {
		return progrSoggProgetto;
	}
	public void setProgrSoggProgetto(Long progrSoggProgetto) {
		this.progrSoggProgetto = progrSoggProgetto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getTitoloBando() {
		return titoloBando;
	}
	public void setTitoloBando(String titoloBando) {
		this.titoloBando = titoloBando;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDescStatoCredFin() {
		return descStatoCredFin;
	}
	public void setDescStatoCredFin(String descStatoCredFin) {
		this.descStatoCredFin = descStatoCredFin;
	}
	public Date getDataProposta() {
		return dataProposta;
	}
	public void setDataProposta(Date dataProposta) {
		this.dataProposta = dataProposta;
	}
	public String getNag() {
		return nag;
	}
	public void setNag(String nag) {
		this.nag = nag;
	}
	public Long getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public Long getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}
	public String getDescNuovoStatoCred() {
		return descNuovoStatoCred;
	}
	public void setDescNuovoStatoCred(String descNuovoStatoCred) {
		this.descNuovoStatoCred = descNuovoStatoCred;
	}
	public String getDescStatoProposta() {
		return descStatoProposta;
	}
	public void setDescStatoProposta(String descStatoProposta) {
		this.descStatoProposta = descStatoProposta;
	}
	public Long getIdStatoPropVariazCred() {
		return idStatoPropVariazCred;
	}
	public void setIdStatoPropVariazCred(Long idStatoPropVariazCred) {
		this.idStatoPropVariazCred = idStatoPropVariazCred;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getDescStatAzienda() {
		return descStatAzienda;
	}
	public void setDescStatAzienda(String descStatAzienda) {
		this.descStatAzienda = descStatAzienda;
	}
	public Long getGiorniSconf() {
		return giorniSconf;
	}
	public void setGiorniSconf(Long giorniSconf) {
		this.giorniSconf = giorniSconf;
	}
	public BigDecimal getPercSconf() {
		return percSconf;
	}
	public void setPercSconf(BigDecimal percSconf) {
		this.percSconf = percSconf;
	}
	public BigDecimal getImpSconfCapitale() {
		return impSconfCapitale;
	}
	public void setImpSconfCapitale(BigDecimal impSconfCapitale) {
		this.impSconfCapitale = impSconfCapitale;
	}
	public BigDecimal getImpSconfInteressi() {
		return impSconfInteressi;
	}
	public void setImpSconfInteressi(BigDecimal impSconfInteressi) {
		this.impSconfInteressi = impSconfInteressi;
	}
	public BigDecimal getImpSconfAgev() {
		return impSconfAgev;
	}
	public void setImpSconfAgev(BigDecimal impSconfAgev) {
		this.impSconfAgev = impSconfAgev;
	}
	public String getCodiceFondoFinpis() {
		return codiceFondoFinpis;
	}
	public void setCodiceFondoFinpis(String codiceFondoFinpis) {
		this.codiceFondoFinpis = codiceFondoFinpis;
	}

	
	
}
