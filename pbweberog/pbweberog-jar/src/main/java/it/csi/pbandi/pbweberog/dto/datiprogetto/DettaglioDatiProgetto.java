/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.dto.datiprogetto;

import java.util.Date;

public class DettaglioDatiProgetto implements java.io.Serializable {

	private Long idSettoreAttivita;
	private String descSettore;
	private Long idAttivitaAteco;
	private String descAteco;
	private Long idTipoOperazione;
	private String descTipoOperazione;
	private Long idPrioritaQsn;
	private String descPrioritaQsn;
	private Long idObiettivoGenQsn;
	private String descObiettivoGeneraleQsn;
	private Long idObiettivoSpecifQsn;
	private String descObiettivoSpecificoQsn;
	private Long idStrumentoAttuativo;
	private String descStrumentoAttuativo;
	private Long idSettoreCpt;
	private String descSettoreCpt;
	private Long idTemaPrioritario;
	private String descTemaPrioritario;
	private Long idIndRisultatoProgram;
	private String descIndRisultatoProgramma;
	private Long idIndicatoreQsn;
	private String descIndicatoreQsn;
	private Long idTipoAiuto;
	private String descTipoAiuto;
	private Long idTipoStrumentoProgr;
	private String descTipoStrumento;
	private Long idDimensioneTerritor;
	private String descDimensioneTerritoriale;
	private Long idProgettoComplesso;
	private String descProgettoComplesso;
	private Long idSettoreCipe;
	private String descSettoreCipe;
	private Long idSottoSettoreCipe;
	private String descSottoSettoreCipe;
	private Long idCategoriaCipe;
	private Long idNaturaCipe;
	private String descNaturaCipe;
	private Long idTipologiaCipe;
	private String descTipologiaCipe;
	private Boolean flagCardine;
	private Boolean flagGeneratoreEntrate;
	private Boolean flagLegObiettivo;
	private Boolean flagAltroFondo;
	private Boolean statoProgettoProgramma;
	private String dtComitato;
	private String dtConcessione;
	private Boolean flagRichiestaCup;
	private Boolean flagInvioMonit;
	private Double importoEntrate;
	private Long idObiettivoTem;
	private String descObiettivoTem;
	private Long idClassificazioneRa;
	private String descClassificazioneRa;
	private Long idGrandeProgetto;
	private String descGrandeProgetto;
	private Long idTipoProceduraOrig;
	private String descTipoProceduraOrig;
	private Boolean flagPPP;
	private Boolean flagStrategico;
	private Date dtFirmaAccordo;
	private Date dtCompletamentoValutazione;
	
	private static final long serialVersionUID = 1L;
	
	private String descCategoriaCipe;
	
	
	

	public Long getIdSettoreAttivita() {
		return idSettoreAttivita;
	}

	public void setIdSettoreAttivita(Long idSettoreAttivita) {
		this.idSettoreAttivita = idSettoreAttivita;
	}

	public String getDescSettore() {
		return descSettore;
	}

	public void setDescSettore(String descSettore) {
		this.descSettore = descSettore;
	}

	public Long getIdAttivitaAteco() {
		return idAttivitaAteco;
	}

	public void setIdAttivitaAteco(Long idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}

	public String getDescAteco() {
		return descAteco;
	}

	public void setDescAteco(String descAteco) {
		this.descAteco = descAteco;
	}

	public Long getIdTipoOperazione() {
		return idTipoOperazione;
	}

	public void setIdTipoOperazione(Long idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}

	public String getDescTipoOperazione() {
		return descTipoOperazione;
	}

	public void setDescTipoOperazione(String descTipoOperazione) {
		this.descTipoOperazione = descTipoOperazione;
	}

	public Long getIdPrioritaQsn() {
		return idPrioritaQsn;
	}

	public void setIdPrioritaQsn(Long idPrioritaQsn) {
		this.idPrioritaQsn = idPrioritaQsn;
	}

	public String getDescPrioritaQsn() {
		return descPrioritaQsn;
	}

	public void setDescPrioritaQsn(String descPrioritaQsn) {
		this.descPrioritaQsn = descPrioritaQsn;
	}

	public Long getIdObiettivoGenQsn() {
		return idObiettivoGenQsn;
	}

	public void setIdObiettivoGenQsn(Long idObiettivoGenQsn) {
		this.idObiettivoGenQsn = idObiettivoGenQsn;
	}

	public String getDescObiettivoGeneraleQsn() {
		return descObiettivoGeneraleQsn;
	}

	public void setDescObiettivoGeneraleQsn(String descObiettivoGeneraleQsn) {
		this.descObiettivoGeneraleQsn = descObiettivoGeneraleQsn;
	}

	public Long getIdObiettivoSpecifQsn() {
		return idObiettivoSpecifQsn;
	}

	public void setIdObiettivoSpecifQsn(Long idObiettivoSpecifQsn) {
		this.idObiettivoSpecifQsn = idObiettivoSpecifQsn;
	}

	public String getDescObiettivoSpecificoQsn() {
		return descObiettivoSpecificoQsn;
	}

	public void setDescObiettivoSpecificoQsn(String descObiettivoSpecificoQsn) {
		this.descObiettivoSpecificoQsn = descObiettivoSpecificoQsn;
	}

	public Long getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}

	public void setIdStrumentoAttuativo(Long idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}

	public String getDescStrumentoAttuativo() {
		return descStrumentoAttuativo;
	}

	public void setDescStrumentoAttuativo(String descStrumentoAttuativo) {
		this.descStrumentoAttuativo = descStrumentoAttuativo;
	}

	public Long getIdSettoreCpt() {
		return idSettoreCpt;
	}

	public void setIdSettoreCpt(Long idSettoreCpt) {
		this.idSettoreCpt = idSettoreCpt;
	}

	public String getDescSettoreCpt() {
		return descSettoreCpt;
	}

	public void setDescSettoreCpt(String descSettoreCpt) {
		this.descSettoreCpt = descSettoreCpt;
	}

	public Long getIdTemaPrioritario() {
		return idTemaPrioritario;
	}

	public void setIdTemaPrioritario(Long idTemaPrioritario) {
		this.idTemaPrioritario = idTemaPrioritario;
	}

	public String getDescTemaPrioritario() {
		return descTemaPrioritario;
	}

	public void setDescTemaPrioritario(String descTemaPrioritario) {
		this.descTemaPrioritario = descTemaPrioritario;
	}

	public Long getIdIndRisultatoProgram() {
		return idIndRisultatoProgram;
	}

	public void setIdIndRisultatoProgram(Long idIndRisultatoProgram) {
		this.idIndRisultatoProgram = idIndRisultatoProgram;
	}

	public String getDescIndRisultatoProgramma() {
		return descIndRisultatoProgramma;
	}

	public void setDescIndRisultatoProgramma(String descIndRisultatoProgramma) {
		this.descIndRisultatoProgramma = descIndRisultatoProgramma;
	}

	public Long getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}

	public void setIdIndicatoreQsn(Long idIndicatoreQsn) {
		this.idIndicatoreQsn = idIndicatoreQsn;
	}

	public String getDescIndicatoreQsn() {
		return descIndicatoreQsn;
	}

	public void setDescIndicatoreQsn(String descIndicatoreQsn) {
		this.descIndicatoreQsn = descIndicatoreQsn;
	}

	public Long getIdTipoAiuto() {
		return idTipoAiuto;
	}

	public void setIdTipoAiuto(Long idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}

	public String getDescTipoAiuto() {
		return descTipoAiuto;
	}

	public void setDescTipoAiuto(String descTipoAiuto) {
		this.descTipoAiuto = descTipoAiuto;
	}

	public Long getIdTipoStrumentoProgr() {
		return idTipoStrumentoProgr;
	}

	public void setIdTipoStrumentoProgr(Long idTipoStrumentoProgr) {
		this.idTipoStrumentoProgr = idTipoStrumentoProgr;
	}

	public String getDescTipoStrumento() {
		return descTipoStrumento;
	}

	public void setDescTipoStrumento(String descTipoStrumento) {
		this.descTipoStrumento = descTipoStrumento;
	}

	public Long getIdDimensioneTerritor() {
		return idDimensioneTerritor;
	}

	public void setIdDimensioneTerritor(Long idDimensioneTerritor) {
		this.idDimensioneTerritor = idDimensioneTerritor;
	}

	public String getDescDimensioneTerritoriale() {
		return descDimensioneTerritoriale;
	}

	public void setDescDimensioneTerritoriale(String descDimensioneTerritoriale) {
		this.descDimensioneTerritoriale = descDimensioneTerritoriale;
	}

	public Long getIdProgettoComplesso() {
		return idProgettoComplesso;
	}

	public void setIdProgettoComplesso(Long idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}

	public String getDescProgettoComplesso() {
		return descProgettoComplesso;
	}

	public void setDescProgettoComplesso(String descProgettoComplesso) {
		this.descProgettoComplesso = descProgettoComplesso;
	}

	public Long getIdSettoreCipe() {
		return idSettoreCipe;
	}

	public void setIdSettoreCipe(Long idSettoreCipe) {
		this.idSettoreCipe = idSettoreCipe;
	}

	public String getDescSettoreCipe() {
		return descSettoreCipe;
	}

	public void setDescSettoreCipe(String descSettoreCipe) {
		this.descSettoreCipe = descSettoreCipe;
	}

	public Long getIdSottoSettoreCipe() {
		return idSottoSettoreCipe;
	}

	public void setIdSottoSettoreCipe(Long idSottoSettoreCipe) {
		this.idSottoSettoreCipe = idSottoSettoreCipe;
	}

	public String getDescSottoSettoreCipe() {
		return descSottoSettoreCipe;
	}

	public void setDescSottoSettoreCipe(String descSottoSettoreCipe) {
		this.descSottoSettoreCipe = descSottoSettoreCipe;
	}

	public Long getIdCategoriaCipe() {
		return idCategoriaCipe;
	}

	public void setIdCategoriaCipe(Long idCategoriaCipe) {
		this.idCategoriaCipe = idCategoriaCipe;
	}

	public Long getIdNaturaCipe() {
		return idNaturaCipe;
	}

	public void setIdNaturaCipe(Long idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}

	public String getDescNaturaCipe() {
		return descNaturaCipe;
	}

	public void setDescNaturaCipe(String descNaturaCipe) {
		this.descNaturaCipe = descNaturaCipe;
	}

	public Long getIdTipologiaCipe() {
		return idTipologiaCipe;
	}

	public void setIdTipologiaCipe(Long idTipologiaCipe) {
		this.idTipologiaCipe = idTipologiaCipe;
	}

	public String getDescTipologiaCipe() {
		return descTipologiaCipe;
	}

	public void setDescTipologiaCipe(String descTipologiaCipe) {
		this.descTipologiaCipe = descTipologiaCipe;
	}

	public Boolean getFlagCardine() {
		return flagCardine;
	}

	public void setFlagCardine(Boolean flagCardine) {
		this.flagCardine = flagCardine;
	}

	public Boolean getFlagGeneratoreEntrate() {
		return flagGeneratoreEntrate;
	}

	public void setFlagGeneratoreEntrate(Boolean flagGeneratoreEntrate) {
		this.flagGeneratoreEntrate = flagGeneratoreEntrate;
	}

	public Boolean getFlagLegObiettivo() {
		return flagLegObiettivo;
	}

	public void setFlagLegObiettivo(Boolean flagLegObiettivo) {
		this.flagLegObiettivo = flagLegObiettivo;
	}

	public Boolean getFlagAltroFondo() {
		return flagAltroFondo;
	}

	public void setFlagAltroFondo(Boolean flagAltroFondo) {
		this.flagAltroFondo = flagAltroFondo;
	}

	public Boolean getStatoProgettoProgramma() {
		return statoProgettoProgramma;
	}

	public void setStatoProgettoProgramma(Boolean statoProgettoProgramma) {
		this.statoProgettoProgramma = statoProgettoProgramma;
	}

	public String getDtComitato() {
		return dtComitato;
	}

	public void setDtComitato(String dtComitato) {
		this.dtComitato = dtComitato;
	}

	public String getDtConcessione() {
		return dtConcessione;
	}

	public void setDtConcessione(String dtConcessione) {
		this.dtConcessione = dtConcessione;
	}

	public Boolean getFlagRichiestaCup() {
		return flagRichiestaCup;
	}

	public void setFlagRichiestaCup(Boolean flagRichiestaCup) {
		this.flagRichiestaCup = flagRichiestaCup;
	}

	public Boolean getFlagInvioMonit() {
		return flagInvioMonit;
	}

	public void setFlagInvioMonit(Boolean flagInvioMonit) {
		this.flagInvioMonit = flagInvioMonit;
	}

	public Double getImportoEntrate() {
		return importoEntrate;
	}

	public void setImportoEntrate(Double importoEntrate) {
		this.importoEntrate = importoEntrate;
	}

	public Long getIdObiettivoTem() {
		return idObiettivoTem;
	}

	public void setIdObiettivoTem(Long idObiettivoTem) {
		this.idObiettivoTem = idObiettivoTem;
	}

	public String getDescObiettivoTem() {
		return descObiettivoTem;
	}

	public void setDescObiettivoTem(String descObiettivoTem) {
		this.descObiettivoTem = descObiettivoTem;
	}

	public Long getIdClassificazioneRa() {
		return idClassificazioneRa;
	}

	public void setIdClassificazioneRa(Long idClassificazioneRa) {
		this.idClassificazioneRa = idClassificazioneRa;
	}

	public String getDescClassificazioneRa() {
		return descClassificazioneRa;
	}

	public void setDescClassificazioneRa(String descClassificazioneRa) {
		this.descClassificazioneRa = descClassificazioneRa;
	}

	public Long getIdGrandeProgetto() {
		return idGrandeProgetto;
	}

	public void setIdGrandeProgetto(Long idGrandeProgetto) {
		this.idGrandeProgetto = idGrandeProgetto;
	}

	public String getDescGrandeProgetto() {
		return descGrandeProgetto;
	}

	public void setDescGrandeProgetto(String descGrandeProgetto) {
		this.descGrandeProgetto = descGrandeProgetto;
	}

	public Long getIdTipoProceduraOrig() {
		return idTipoProceduraOrig;
	}

	public void setIdTipoProceduraOrig(Long idTipoProceduraOrig) {
		this.idTipoProceduraOrig = idTipoProceduraOrig;
	}

	public String getDescTipoProceduraOrig() {
		return descTipoProceduraOrig;
	}

	public void setDescTipoProceduraOrig(String descTipoProceduraOrig) {
		this.descTipoProceduraOrig = descTipoProceduraOrig;
	}

	public DettaglioDatiProgetto() {
		super();

	}

	public String toString() {
		return super.toString();
	}

	public String getDescCategoriaCipe() {
		return descCategoriaCipe;
	}

	public void setDescCategoriaCipe(String descCategoriaCipe) {
		this.descCategoriaCipe = descCategoriaCipe;
	}

	public Boolean getFlagPPP() {
		return flagPPP;
	}

	public void setFlagPPP(Boolean flagPPP) {
		this.flagPPP = flagPPP;
	}

	public Boolean getFlagStrategico() {
		return flagStrategico;
	}

	public void setFlagStrategico(Boolean flagStrategico) {
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
