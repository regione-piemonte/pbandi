/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebcert.integration.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class PbandiTProgettoVO {
	private String idIstanzaProcesso;
  	
  	private BigDecimal idProgetto;
  	
  	private BigDecimal sogliaSpesaCalcErogazioni;
  	
  	private BigDecimal idObiettivoSpecifQsn;
  	
  	private BigDecimal idIndicatoreQsn;
  	
  	private BigDecimal idTipologiaCipe;
  	
  	private String codiceProgetto;
  	
  	private Date dtChiusuraProgetto;
  	
  	private BigDecimal idProgettoComplesso;
  	
  	private String titoloProgetto;
  	
  	private String acronimoProgetto;
  	
  	private BigDecimal idTipoStrumentoProgr;
  	
  	private BigDecimal idDomanda;
  	
  	private BigDecimal idIndRisultatoProgram;
  	
  	private String cup;
  	
  	private Date dtInserimento;
  	
  	private BigDecimal idUtenteIns;
  	
  	private BigDecimal idSettoreCpt;
  	
  	private BigDecimal idCategoriaCipe;
  	
  	private BigDecimal importoPremialita;
  	
  	private BigDecimal idTipoOperazione;
  	
  	private Date dtComitato;
  	
  	private String flagProgettoMaster;
  	
  	private BigDecimal percentualePremialita;
  	
  	private BigDecimal idTemaPrioritario;
  	
  	private BigDecimal idStrumentoAttuativo;
  	
  	private String codiceVisualizzato;
  	
  	private BigDecimal idUtenteAgg;
  	
  	private String noteChiusuraProgetto;
  	
  	private BigDecimal idStatoProgetto;
  	
  	private Date dtConcessione;
  	
  	private BigDecimal idProgettoPadre;
  	
  	private Date dtAggiornamento;
  	
	public String getIdIstanzaProcesso() {
		return idIstanzaProcesso;
	}

	public void setIdIstanzaProcesso(String idIstanzaProcesso) {
		this.idIstanzaProcesso = idIstanzaProcesso;
	}

	public BigDecimal getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}

	public BigDecimal getSogliaSpesaCalcErogazioni() {
		return sogliaSpesaCalcErogazioni;
	}

	public void setSogliaSpesaCalcErogazioni(BigDecimal sogliaSpesaCalcErogazioni) {
		this.sogliaSpesaCalcErogazioni = sogliaSpesaCalcErogazioni;
	}

	public BigDecimal getIdObiettivoSpecifQsn() {
		return idObiettivoSpecifQsn;
	}

	public void setIdObiettivoSpecifQsn(BigDecimal idObiettivoSpecifQsn) {
		this.idObiettivoSpecifQsn = idObiettivoSpecifQsn;
	}

	public BigDecimal getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}

	public void setIdIndicatoreQsn(BigDecimal idIndicatoreQsn) {
		this.idIndicatoreQsn = idIndicatoreQsn;
	}

	public BigDecimal getIdTipologiaCipe() {
		return idTipologiaCipe;
	}

	public void setIdTipologiaCipe(BigDecimal idTipologiaCipe) {
		this.idTipologiaCipe = idTipologiaCipe;
	}

	public String getCodiceProgetto() {
		return codiceProgetto;
	}

	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}

	public Date getDtChiusuraProgetto() {
		return dtChiusuraProgetto;
	}

	public void setDtChiusuraProgetto(Date dtChiusuraProgetto) {
		this.dtChiusuraProgetto = dtChiusuraProgetto;
	}

	public BigDecimal getIdProgettoComplesso() {
		return idProgettoComplesso;
	}

	public void setIdProgettoComplesso(BigDecimal idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}

	public String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public String getAcronimoProgetto() {
		return acronimoProgetto;
	}

	public void setAcronimoProgetto(String acronimoProgetto) {
		this.acronimoProgetto = acronimoProgetto;
	}

	public BigDecimal getIdTipoStrumentoProgr() {
		return idTipoStrumentoProgr;
	}

	public void setIdTipoStrumentoProgr(BigDecimal idTipoStrumentoProgr) {
		this.idTipoStrumentoProgr = idTipoStrumentoProgr;
	}

	public BigDecimal getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}

	public BigDecimal getIdIndRisultatoProgram() {
		return idIndRisultatoProgram;
	}

	public void setIdIndRisultatoProgram(BigDecimal idIndRisultatoProgram) {
		this.idIndRisultatoProgram = idIndRisultatoProgram;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public Date getDtInserimento() {
		return dtInserimento;
	}

	public void setDtInserimento(Date dtInserimento) {
		this.dtInserimento = dtInserimento;
	}

	public BigDecimal getIdUtenteIns() {
		return idUtenteIns;
	}

	public void setIdUtenteIns(BigDecimal idUtenteIns) {
		this.idUtenteIns = idUtenteIns;
	}

	public BigDecimal getIdSettoreCpt() {
		return idSettoreCpt;
	}

	public void setIdSettoreCpt(BigDecimal idSettoreCpt) {
		this.idSettoreCpt = idSettoreCpt;
	}

	public BigDecimal getIdCategoriaCipe() {
		return idCategoriaCipe;
	}

	public void setIdCategoriaCipe(BigDecimal idCategoriaCipe) {
		this.idCategoriaCipe = idCategoriaCipe;
	}

	public BigDecimal getImportoPremialita() {
		return importoPremialita;
	}

	public void setImportoPremialita(BigDecimal importoPremialita) {
		this.importoPremialita = importoPremialita;
	}

	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}

	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}

	public Date getDtComitato() {
		return dtComitato;
	}

	public void setDtComitato(Date dtComitato) {
		this.dtComitato = dtComitato;
	}

	public String getFlagProgettoMaster() {
		return flagProgettoMaster;
	}

	public void setFlagProgettoMaster(String flagProgettoMaster) {
		this.flagProgettoMaster = flagProgettoMaster;
	}

	public BigDecimal getPercentualePremialita() {
		return percentualePremialita;
	}

	public void setPercentualePremialita(BigDecimal percentualePremialita) {
		this.percentualePremialita = percentualePremialita;
	}

	public BigDecimal getIdTemaPrioritario() {
		return idTemaPrioritario;
	}

	public void setIdTemaPrioritario(BigDecimal idTemaPrioritario) {
		this.idTemaPrioritario = idTemaPrioritario;
	}

	public BigDecimal getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}

	public void setIdStrumentoAttuativo(BigDecimal idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}

	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}

	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}

	public BigDecimal getIdUtenteAgg() {
		return idUtenteAgg;
	}

	public void setIdUtenteAgg(BigDecimal idUtenteAgg) {
		this.idUtenteAgg = idUtenteAgg;
	}

	public String getNoteChiusuraProgetto() {
		return noteChiusuraProgetto;
	}

	public void setNoteChiusuraProgetto(String noteChiusuraProgetto) {
		this.noteChiusuraProgetto = noteChiusuraProgetto;
	}

	public BigDecimal getIdStatoProgetto() {
		return idStatoProgetto;
	}

	public void setIdStatoProgetto(BigDecimal idStatoProgetto) {
		this.idStatoProgetto = idStatoProgetto;
	}

	public Date getDtConcessione() {
		return dtConcessione;
	}

	public void setDtConcessione(Date dtConcessione) {
		this.dtConcessione = dtConcessione;
	}

	public BigDecimal getIdProgettoPadre() {
		return idProgettoPadre;
	}

	public void setIdProgettoPadre(BigDecimal idProgettoPadre) {
		this.idProgettoPadre = idProgettoPadre;
	}

	public Date getDtAggiornamento() {
		return dtAggiornamento;
	}

	public void setDtAggiornamento(Date dtAggiornamento) {
		this.dtAggiornamento = dtAggiornamento;
	}

	public PbandiTProgettoVO() {}
  	
	public PbandiTProgettoVO (BigDecimal idProgetto) {
	
		this. idProgetto =  idProgetto;
	}
  	
	public boolean isValid() {
		boolean isValid = false;
                if (isPKValid() && codiceProgetto != null && titoloProgetto != null && idTipoStrumentoProgr != null && idDomanda != null && dtInserimento != null && idUtenteIns != null && idTipoOperazione != null && flagProgettoMaster != null && idStrumentoAttuativo != null && codiceVisualizzato != null && idStatoProgetto != null) {
   			isValid = true;
   		}
   		return isValid;
	}
	public boolean isPKValid() {
		boolean isPkValid = false;
		if (idProgetto != null ) {
   			isPkValid = true;
   		}

   		return isPkValid;
	}
	
	public List<String> getPK() {
		java.util.List<String> pk= new java.util.ArrayList();
		
		pk.add("idProgetto");
		
		return pk;
	}
	
}
