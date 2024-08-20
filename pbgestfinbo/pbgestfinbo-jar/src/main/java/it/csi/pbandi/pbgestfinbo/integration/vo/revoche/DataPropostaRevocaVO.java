/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.vo.revoche;


import java.io.Serializable;
import java.util.Date;

public class DataPropostaRevocaVO implements Serializable {
	
	private Long idGestioneRevoca;
	private Long idProgetto;
	private Long idSoggetto;
	private Long idDomanda;

	private String denominazioneBeneficiario;
	private Long idBeneficiario;
	private String codiceFiscaleSoggetto;
	private String nomeBandoLinea;
	private String titoloProgetto;
	private Long numeroRevoca;
	private String causaRevoca;
	private String descCausaleBlocco;
	private String descCategAnagrafica;
	private String descStatoRevoca;
	private Date dtStatoRevoca; 
	private Date dtGestione; 	
	private String codiceVisualizzato;
	private String descBreveStatoRevoca;

	private String nomeIstruttore;
	private String cognomeIstruttore;
	
	public DataPropostaRevocaVO() {
	}

	public DataPropostaRevocaVO(Long idGestioneRevoca, Long idProgetto, Long idSoggetto, Long idDomanda, String denominazioneBeneficiario, Long idBeneficiario, String codiceFiscaleSoggetto, String nomeBandoLinea, String titoloProgetto, Long numeroRevoca, String causaRevoca, String descCausaleBlocco, String descCategAnagrafica, String descStatoRevoca, Date dtStatoRevoca, Date dtGestione, String codiceVisualizzato, String descBreveStatoRevoca, String nomeIstruttore, String cognomeIstruttore) {
		this.idGestioneRevoca = idGestioneRevoca;
		this.idProgetto = idProgetto;
		this.idSoggetto = idSoggetto;
		this.idDomanda = idDomanda;
		this.denominazioneBeneficiario = denominazioneBeneficiario;
		this.idBeneficiario = idBeneficiario;
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
		this.nomeBandoLinea = nomeBandoLinea;
		this.titoloProgetto = titoloProgetto;
		this.numeroRevoca = numeroRevoca;
		this.causaRevoca = causaRevoca;
		this.descCausaleBlocco = descCausaleBlocco;
		this.descCategAnagrafica = descCategAnagrafica;
		this.descStatoRevoca = descStatoRevoca;
		this.dtStatoRevoca = dtStatoRevoca;
		this.dtGestione = dtGestione;
		this.codiceVisualizzato = codiceVisualizzato;
		this.descBreveStatoRevoca = descBreveStatoRevoca;
		this.nomeIstruttore = nomeIstruttore;
		this.cognomeIstruttore = cognomeIstruttore;
	}

	public static DataPropostaRevocaVO createMokObject () {
		
		return new DataPropostaRevocaVO(
				1L,
				1L,
				1L,
				1L,
				"Nome",
				1L,
				"codiceFicale",
				"nomeBandoLinea",
				"titoloProgetto",
				1L,
				"causaRevoca",
				"descCausaleBlocco",
				"descCategAnagrafica",
				"descStatoRevoca",
				new Date(),
				new Date(),
				"note",
				"descBreveStatoRevoca",
				"Nome",
				"Cognome"
		);
	}

	public Long getIdGestioneRevoca() {
		return idGestioneRevoca;
	}

	public void setIdGestioneRevoca(Long idGestioneRevoca) {
		this.idGestioneRevoca = idGestioneRevoca;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public Long getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(Long idDomanda) {
		this.idDomanda = idDomanda;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	public String getCodiceFiscaleSoggetto() {
		return codiceFiscaleSoggetto;
	}

	public void setCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
		this.codiceFiscaleSoggetto = codiceFiscaleSoggetto;
	}

	public String getNomeBandoLinea() {
		return nomeBandoLinea;
	}

	public void setNomeBandoLinea(String nomeBandoLinea) {
		this.nomeBandoLinea = nomeBandoLinea;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public Long getNumeroRevoca() {
		return numeroRevoca;
	}

	public void setNumeroRevoca(Long numeroRevoca) {
		this.numeroRevoca = numeroRevoca;
	}

	public String getCausaRevoca() {
		return causaRevoca;
	}

	public void setCausaRevoca(String causaRevoca) {
		this.causaRevoca = causaRevoca;
	}

	public String getDescCausaleBlocco() {
		return descCausaleBlocco;
	}

	public void setDescCausaleBlocco(String descCausaleBlocco) {
		this.descCausaleBlocco = descCausaleBlocco;
	}

	public String getDescCategAnagrafica() {
		return descCategAnagrafica;
	}

	public void setDescCategAnagrafica(String descCategAnagrafica) {
		this.descCategAnagrafica = descCategAnagrafica;
	}

	public String getDescStatoRevoca() {
		return descStatoRevoca;
	}

	public void setDescStatoRevoca(String descStatoRevoca) {
		this.descStatoRevoca = descStatoRevoca;
	}

	public Date getDtStatoRevoca() {
		return dtStatoRevoca;
	}

	public void setDtStatoRevoca(Date dtStatoRevoca) {
		this.dtStatoRevoca = dtStatoRevoca;
	}

	public Date getDtGestione() {
		return dtGestione;
	}

	public void setDtGestione(Date dtGestione) {
		this.dtGestione = dtGestione;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public String getDescBreveStatoRevoca() {
		return descBreveStatoRevoca;
	}

	public void setDescBreveStatoRevoca(String descBreveStatoRevoca) {
		this.descBreveStatoRevoca = descBreveStatoRevoca;
	}

	public String getNomeIstruttore() {
		return nomeIstruttore;
	}

	public void setNomeIstruttore(String nomeIstruttore) {
		this.nomeIstruttore = nomeIstruttore;
	}

	public String getCognomeIstruttore() {
		return cognomeIstruttore;
	}

	public void setCognomeIstruttore(String cognomeIstruttore) {
		this.cognomeIstruttore = cognomeIstruttore;
	}
}
