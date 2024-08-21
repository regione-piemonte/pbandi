/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class DatiProgettoVO extends GenericVO {
	
	private BigDecimal idProgetto;
	private BigDecimal idSoggettoBeneficiario;
	private BigDecimal progrSoggettoProgetto;
	private BigDecimal progrSoggettoProgettoSede;
	private String cup;
	private String numeroDomanda;
	private BigDecimal idDomanda;
	private String titoloProgetto;
	private String codiceVisualizzato;
	private String codiceProgetto;
	private Date dtComitato;
	private Date dtConcessione;
	private BigDecimal idSedeIntervento;
	private BigDecimal idSettoreAttivita;
	private String descSettore;
	private BigDecimal idAttivitaAteco;
	private String descAteco;
	private BigDecimal idTipoOperazione;
	private String descTipoOperazione;
	private BigDecimal idPrioritaQsn;
	private String descPrioritaQsn;
	private BigDecimal idObiettivoGenQsn;
	private String descObiettivoGeneraleQsn;
	private BigDecimal idObiettivoSpecifQsn;
	private String descObiettivoSpecificoQsn;
	private BigDecimal idStrumentoAttuativo;
	private String descStrumentoAttuativo;
	private BigDecimal idSettoreCpt;
	private String descSettoreCpt;
	private BigDecimal idTemaPrioritario;
	private String descTemaPrioritario;
	private BigDecimal idIndRisultatoProgram;
	private String descIndRisultatoProgramma;
	private BigDecimal idIndicatoreQsn;
	private String descIndicatoreQsn;
	private BigDecimal idTipoAiuto;
	private String descTipoAiuto;
	private BigDecimal idTipoStrumentoProgr;
	private String descTipoStrumento;
	private BigDecimal idDimensioneTerritor;
	private String descDimensioneTerritoriale;
	private BigDecimal idProgettoComplesso;
	private String descProgettoComplesso;
	private BigDecimal idSettoreCipe;
	private String descSettoreCipe;
	private BigDecimal idSottoSettoreCipe;
	private String descSottoSettoreCipe;
	private BigDecimal idCategoriaCipe;
	private String descCategoriaCipe;
	private BigDecimal idNaturaCipe;
	private String descNaturaCipe;
	private BigDecimal idTipologiaCipe;
	private String descTipologiaCipe;
	private String flagCardine;
	private String flagGeneratoreEntrate;
	private String flagLegObiettivo;
	private String flagAltroFondo;
	private String statoProgettoProgramma;
	private String codiceProgettoCipe;
	private String flagRichiestaCup;
	private String flagInvioMonit;
	private BigDecimal importoEntrate;
	private BigDecimal idObiettivoTem;
	private String descObiettivoTem;
	private BigDecimal idClassificazioneRa;
	private String descClassificazioneRa;
	private BigDecimal idGrandeProgetto;
	private String descGrandeProgetto;
	private String flagPPP;
	private String flagStrategico;
	private Date dtFirmaAccordo;
	private Date dtCompletamentoValutazione;
	
	// PBANDI-2734
	private BigDecimal idTipoProceduraOrig;
	private String descTipoProceduraOrig;
	
	public BigDecimal getIdProgetto() {
		return idProgetto;
	}
	public void setIdProgetto(BigDecimal idProgetto) {
		this.idProgetto = idProgetto;
	}
	public BigDecimal getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(BigDecimal idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getCup() {
		return cup;
	}
	public void setCup(String cup) {
		this.cup = cup;
	}
	public String getTitoloProgetto() {
		return titoloProgetto;
	}
	public void setTitoloProgetto(String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}
	public String getCodiceVisualizzato() {
		return codiceVisualizzato;
	}
	public void setCodiceVisualizzato(String codiceVisualizzato) {
		this.codiceVisualizzato = codiceVisualizzato;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public Date getDtComitato() {
		return dtComitato;
	}
	public void setDtComitato(Date dtComitato) {
		this.dtComitato = dtComitato;
	}
	public Date getDtConcessione() {
		return dtConcessione;
	}
	public void setDtConcessione(Date dtConcessione) {
		this.dtConcessione = dtConcessione;
	}
	public BigDecimal getIdSettoreAttivita() {
		return idSettoreAttivita;
	}
	public void setIdSettoreAttivita(BigDecimal idSettoreAttivita) {
		this.idSettoreAttivita = idSettoreAttivita;
	}
	public String getDescSettore() {
		return descSettore;
	}
	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}
	public BigDecimal getIdAttivitaAteco() {
		return idAttivitaAteco;
	}
	public void setIdAttivitaAteco(BigDecimal idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}
	public String getDescAteco() {
		return descAteco;
	}
	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}
	public BigDecimal getIdTipoOperazione() {
		return idTipoOperazione;
	}
	public void setIdTipoOperazione(BigDecimal idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}
	public String getDescTipoOperazione() {
		return descTipoOperazione;
	}
	public void setDescTipoOperazione(String descTipoOperazione) {
		this.descTipoOperazione = descTipoOperazione;
	}
	public BigDecimal getIdPrioritaQsn() {
		return idPrioritaQsn;
	}
	public void setIdPrioritaQsn(BigDecimal idPrioritaQsn) {
		this.idPrioritaQsn = idPrioritaQsn;
	}
	public String getDescPrioritaQsn() {
		return descPrioritaQsn;
	}
	public void setDescPrioritaQsn(String descPrioritaQsn) {
		this.descPrioritaQsn = descPrioritaQsn;
	}
	public BigDecimal getIdObiettivoGenQsn() {
		return idObiettivoGenQsn;
	}
	public void setIdObiettivoGenQsn(BigDecimal idObiettivoGenQsn) {
		this.idObiettivoGenQsn = idObiettivoGenQsn;
	}
	public String getDescObiettivoGeneraleQsn() {
		return descObiettivoGeneraleQsn;
	}
	public void setDescObiettivoGeneraleQsn(String descObiettivoGeneraleQsn) {
		this.descObiettivoGeneraleQsn = descObiettivoGeneraleQsn;
	}
	public BigDecimal getIdObiettivoSpecifQsn() {
		return idObiettivoSpecifQsn;
	}
	public void setIdObiettivoSpecifQsn(BigDecimal idObiettivoSpecifQsn) {
		this.idObiettivoSpecifQsn = idObiettivoSpecifQsn;
	}
	public String getDescObiettivoSpecificoQsn() {
		return descObiettivoSpecificoQsn;
	}
	public void setDescObiettivoSpecificoQsn(String descObiettivoSpecificoQsn) {
		this.descObiettivoSpecificoQsn = descObiettivoSpecificoQsn;
	}
	public BigDecimal getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}
	public void setIdStrumentoAttuativo(BigDecimal idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}
	public String getDescStrumentoAttuativo() {
		return descStrumentoAttuativo;
	}
	public void setDescStrumentoAttuativo(String descStrumentoAttuativo) {
		this.descStrumentoAttuativo = descStrumentoAttuativo;
	}
	public BigDecimal getIdSettoreCpt() {
		return idSettoreCpt;
	}
	public void setIdSettoreCpt(BigDecimal idSettoreCpt) {
		this.idSettoreCpt = idSettoreCpt;
	}
	public String getDescSettoreCpt() {
		return descSettoreCpt;
	}
	public void setDescSettoreCpt(String descSettoreCpt) {
		this.descSettoreCpt = descSettoreCpt;
	}
	public BigDecimal getIdTemaPrioritario() {
		return idTemaPrioritario;
	}
	public void setIdTemaPrioritario(BigDecimal idTemaPrioritario) {
		this.idTemaPrioritario = idTemaPrioritario;
	}
	public String getDescTemaPrioritario() {
		return descTemaPrioritario;
	}
	public void setDescTemaPrioritario(String descTemaPrioritario) {
		this.descTemaPrioritario = descTemaPrioritario;
	}
	public BigDecimal getIdIndRisultatoProgram() {
		return idIndRisultatoProgram;
	}
	public void setIdIndRisultatoProgram(BigDecimal idIndRisultatoProgram) {
		this.idIndRisultatoProgram = idIndRisultatoProgram;
	}
	public String getDescIndRisultatoProgramma() {
		return descIndRisultatoProgramma;
	}
	public void setDescIndRisultatoProgramma(String descIndRisultatoProgramma) {
		this.descIndRisultatoProgramma = descIndRisultatoProgramma;
	}
	public BigDecimal getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}
	public void setIdIndicatoreQsn(BigDecimal idIndicatoreQsn) {
		this.idIndicatoreQsn = idIndicatoreQsn;
	}
	public String getDescIndicatoreQsn() {
		return descIndicatoreQsn;
	}
	public void setDescIndicatoreQsn(String descIndicatoreQsn) {
		this.descIndicatoreQsn = descIndicatoreQsn;
	}
	public BigDecimal getIdTipoAiuto() {
		return idTipoAiuto;
	}
	public void setIdTipoAiuto(BigDecimal idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}
	public String getDescTipoAiuto() {
		return descTipoAiuto;
	}
	public void setDescTipoAiuto(String descTipoAiuto) {
		this.descTipoAiuto = descTipoAiuto;
	}
	public BigDecimal getIdTipoStrumentoProgr() {
		return idTipoStrumentoProgr;
	}
	public void setIdTipoStrumentoProgr(BigDecimal idTipoStrumentoProgr) {
		this.idTipoStrumentoProgr = idTipoStrumentoProgr;
	}
	public String getDescTipoStrumento() {
		return descTipoStrumento;
	}
	public void setDescTipoStrumento(String descTipoStrumento) {
		this.descTipoStrumento = descTipoStrumento;
	}
	public BigDecimal getIdDimensioneTerritor() {
		return idDimensioneTerritor;
	}
	public void setIdDimensioneTerritor(BigDecimal idDimensioneTerritor) {
		this.idDimensioneTerritor = idDimensioneTerritor;
	}
	public String getDescDimensioneTerritoriale() {
		return descDimensioneTerritoriale;
	}
	public void setDescDimensioneTerritoriale(String descDimensioneTerritoriale) {
		this.descDimensioneTerritoriale = descDimensioneTerritoriale;
	}
	public BigDecimal getIdProgettoComplesso() {
		return idProgettoComplesso;
	}
	public void setIdProgettoComplesso(BigDecimal idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}
	public String getDescProgettoComplesso() {
		return descProgettoComplesso;
	}
	public void setDescProgettoComplesso(String descProgettoComplesso) {
		this.descProgettoComplesso = descProgettoComplesso;
	}
	public BigDecimal getIdSettoreCipe() {
		return idSettoreCipe;
	}
	public void setIdSettoreCipe(BigDecimal idSettoreCipe) {
		this.idSettoreCipe = idSettoreCipe;
	}
	public String getDescSettoreCipe() {
		return descSettoreCipe;
	}
	public void setDescSettoreCipe(String descSettoreCipe) {
		this.descSettoreCipe = descSettoreCipe;
	}
	public BigDecimal getIdSottoSettoreCipe() {
		return idSottoSettoreCipe;
	}
	public void setIdSottoSettoreCipe(BigDecimal idSottoSettoreCipe) {
		this.idSottoSettoreCipe = idSottoSettoreCipe;
	}
	public String getDescSottoSettoreCipe() {
		return descSottoSettoreCipe;
	}
	public void setDescSottoSettoreCipe(String descSottoSettoreCipe) {
		this.descSottoSettoreCipe = descSottoSettoreCipe;
	}
	public BigDecimal getIdCategoriaCipe() {
		return idCategoriaCipe;
	}
	public void setIdCategoriaCipe(BigDecimal idCategoriaCipe) {
		this.idCategoriaCipe = idCategoriaCipe;
	}
	public String getDescCategoriaCipe() {
		return descCategoriaCipe;
	}
	public void setDescCategoriaCipe(String descCategoriaCipe) {
		this.descCategoriaCipe = descCategoriaCipe;
	}
	public BigDecimal getIdNaturaCipe() {
		return idNaturaCipe;
	}
	public void setIdNaturaCipe(BigDecimal idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}
	public String getDescNaturaCipe() {
		return descNaturaCipe;
	}
	public void setDescNaturaCipe(String descNaturaCipe) {
		this.descNaturaCipe = descNaturaCipe;
	}
	public BigDecimal getIdTipologiaCipe() {
		return idTipologiaCipe;
	}
	public void setIdTipologiaCipe(BigDecimal idTipologiaCipe) {
		this.idTipologiaCipe = idTipologiaCipe;
	}
	public String getDescTipologiaCipe() {
		return descTipologiaCipe;
	}
	public void setDescTipologiaCipe(String descTipologiaCipe) {
		this.descTipologiaCipe = descTipologiaCipe;
	}
	public String getFlagCardine() {
		return flagCardine;
	}
	public void setFlagCardine(String flagCardine) {
		this.flagCardine = flagCardine;
	}
	public String getFlagGeneratoreEntrate() {
		return flagGeneratoreEntrate;
	}
	public void setFlagGeneratoreEntrate(String flagGeneratoreEntrate) {
		this.flagGeneratoreEntrate = flagGeneratoreEntrate;
	}
	public String getFlagLegObiettivo() {
		return flagLegObiettivo;
	}
	public void setFlagLegObiettivo(String flagLegObiettivo) {
		this.flagLegObiettivo = flagLegObiettivo;
	}
	public String getFlagAltroFondo() {
		return flagAltroFondo;
	}
	public void setFlagAltroFondo(String flagAltroFondo) {
		this.flagAltroFondo = flagAltroFondo;
	}
	public String getStatoProgettoProgramma() {
		return statoProgettoProgramma;
	}
	public void setStatoProgettoProgramma(String statoProgettoProgramma) {
		this.statoProgettoProgramma = statoProgettoProgramma;
	}
	public void setNumeroDomanda(String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}
	public String getNumeroDomanda() {
		return numeroDomanda;
	}
	public void setIdSedeIntervento(BigDecimal idSedeIntervento) {
		this.idSedeIntervento = idSedeIntervento;
	}
	public BigDecimal getIdSedeIntervento() {
		return idSedeIntervento;
	}
	public void setProgrSoggettoProgetto(BigDecimal progrSoggettoProgetto) {
		this.progrSoggettoProgetto = progrSoggettoProgetto;
	}
	public BigDecimal getProgrSoggettoProgetto() {
		return progrSoggettoProgetto;
	}
	public void setCodiceProgettoCipe(String codiceProgettoCipe) {
		this.codiceProgettoCipe = codiceProgettoCipe;
	}
	public String getCodiceProgettoCipe() {
		return codiceProgettoCipe;
	}
	public void setIdDomanda(BigDecimal idDomanda) {
		this.idDomanda = idDomanda;
	}
	public BigDecimal getIdDomanda() {
		return idDomanda;
	}
	public void setProgrSoggettoProgettoSede(BigDecimal progrSoggettoProgettoSede) {
		this.progrSoggettoProgettoSede = progrSoggettoProgettoSede;
	}
	public BigDecimal getProgrSoggettoProgettoSede() {
		return progrSoggettoProgettoSede;
	}
	public String getFlagRichiestaCup() {
		return flagRichiestaCup;
	}
	public void setFlagRichiestaCup(String flagRichiestaCup) {
		this.flagRichiestaCup = flagRichiestaCup;
	}
	public String getFlagInvioMonit() {
		return flagInvioMonit;
	}
	public void setFlagInvioMonit(String flagInvioMonit) {
		this.flagInvioMonit = flagInvioMonit;
	}
	public BigDecimal getImportoEntrate() {
		return importoEntrate;
	}
	public void setImportoEntrate(BigDecimal importoEntrate) {
		this.importoEntrate = importoEntrate;
	}
	public BigDecimal getIdObiettivoTem() {
		return idObiettivoTem;
	}
	public void setIdObiettivoTem(BigDecimal idObiettivoTem) {
		this.idObiettivoTem = idObiettivoTem;
	}
	public String getDescObiettivoTem() {
		return descObiettivoTem;
	}
	public void setDescObiettivoTem(String descObiettivoTem) {
		this.descObiettivoTem = descObiettivoTem;
	}
	public BigDecimal getIdClassificazioneRa() {
		return idClassificazioneRa;
	}
	public void setIdClassificazioneRa(BigDecimal idClassificazioneRa) {
		this.idClassificazioneRa = idClassificazioneRa;
	}
	public String getDescClassificazioneRa() {
		return descClassificazioneRa;
	}
	public void setDescClassificazioneRa(String descClassificazioneRa) {
		this.descClassificazioneRa = descClassificazioneRa;
	}
	public BigDecimal getIdGrandeProgetto() {
		return idGrandeProgetto;
	}
	public void setIdGrandeProgetto(BigDecimal idGrandeProgetto) {
		this.idGrandeProgetto = idGrandeProgetto;
	}
	public String getDescGrandeProgetto() {
		return descGrandeProgetto;
	}
	public void setDescGrandeProgetto(String descGrandeProgetto) {
		this.descGrandeProgetto = descGrandeProgetto;
	}
	public BigDecimal getIdTipoProceduraOrig() {
		return idTipoProceduraOrig;
	}
	public void setIdTipoProceduraOrig(BigDecimal idTipoProceduraOrig) {
		this.idTipoProceduraOrig = idTipoProceduraOrig;
	}
	public String getDescTipoProceduraOrig() {
		return descTipoProceduraOrig;
	}
	public void setDescTipoProceduraOrig(String descTipoProceduraOrig) {
		this.descTipoProceduraOrig = descTipoProceduraOrig;
	}
	public String getFlagPPP() {
		return flagPPP;
	}
	public void setFlagPPP(String flagPPP) {
		this.flagPPP = flagPPP;
	}
	public String getFlagStrategico() {
		return flagStrategico;
	}
	public void setFlagStrategico(String flagStrategico) {
		this.flagStrategico = flagStrategico;
	}
	public Date getDtFirmaAccordo() {
		return dtFirmaAccordo;
	}
	public void setDtFirmaAccordo(Date dtFirmaAccordo) {
		this.dtFirmaAccordo = dtFirmaAccordo;
	}
	public Date getDtCompletamentoValutazione() {
		return dtCompletamentoValutazione;
	}
	public void setDtCompletamentoValutazione(Date dtCompletamentoValutazione) {
		this.dtCompletamentoValutazione = dtCompletamentoValutazione;
	}

	
}
