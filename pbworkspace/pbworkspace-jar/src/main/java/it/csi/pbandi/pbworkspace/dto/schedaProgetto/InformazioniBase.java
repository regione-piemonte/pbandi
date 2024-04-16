/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.dto.schedaProgetto;

import java.beans.IntrospectionException;
import java.util.Date;
import java.util.Set;

import it.csi.pbandi.pbworkspace.util.BeanUtil;

public class InformazioniBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private java.lang.String titoloProgetto = null;
	private java.lang.String numeroDomanda = null;
	private java.lang.String cup = null;
	private java.lang.String dataDecorrenza = null;
	private java.lang.String dataGenerazione = null;
	private java.lang.String dataConcessione = null;
	private java.lang.String dataComitato = null;
	private java.lang.String idSettoreAttivita = null;
	private java.lang.String idAttivitaAteco = null;
	private java.lang.String idPrioritaQsn = null;
	private java.lang.String idObiettivoGeneraleQsn = null;
	private java.lang.String idObiettivoSpecificoQsn = null;
	private java.lang.String idStrumentoAttuativo = null;
	private java.lang.String idSettoreCpt = null;
	private java.lang.String idTemaPrioritario = null;
	private java.lang.String idIndicatoreRisultatoProgramma = null;
	private java.lang.String idIndicatoreQsn = null;
	private java.lang.String idTipoAiuto = null;
	private java.lang.String idTipoStrumentoProgrammazione = null;
	private java.lang.String idDimensioneTerritoriale = null;
	private java.lang.String idProgettoComplesso = null;
	private java.lang.String idSettoreCipe = null;
	private java.lang.String idSottoSettoreCipe = null;
	private java.lang.String idCategoriaCipe = null;
	private java.lang.String idNaturaCipe = null;
	private java.lang.String idTipologiaCipe = null;
	private java.lang.String flagCardine = null;
	private java.lang.String flagGeneratoreEntrate = null;
	private java.lang.String flagLeggeObiettivo = null;
	private java.lang.String flagAltroFondo = null;
	private java.lang.String flagStatoProgettoProgramma = null;
	private java.lang.String flagDettaglioCup = null;
	private java.lang.String idTipoOperazione = null;
	private java.lang.String flagAggiungiCup = null;
	private java.lang.String flagBeneficiarioCup = null;
	private java.lang.String annoConcessioneOld = null;
	private java.lang.String dataPresentazioneDomanda = null;
	private java.lang.String flagProgettoDaInviareAlMonitoraggio = null;
	private java.lang.String flagRichiestaAutomaticaDelCup = null;
	private java.lang.String idObiettivoTematico = null;
	private java.lang.String idClassificazioneRA = null;
	private java.lang.String idGrandeProgetto = null;
	private java.lang.String flagPPP = null;
	private java.lang.String flagStrategico = null;
	private Date dtFirmaAccordo = null;
	private Date dtCompletamentoValutazione = null;

	public InformazioniBase() {
		super();
	}
	
	public java.lang.String getTitoloProgetto() {
		return titoloProgetto;
	}

	public void setTitoloProgetto(java.lang.String titoloProgetto) {
		this.titoloProgetto = titoloProgetto;
	}

	public java.lang.String getNumeroDomanda() {
		return numeroDomanda;
	}

	public void setNumeroDomanda(java.lang.String numeroDomanda) {
		this.numeroDomanda = numeroDomanda;
	}

	public java.lang.String getCup() {
		return cup;
	}

	public void setCup(java.lang.String cup) {
		this.cup = cup;
	}

	public java.lang.String getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(java.lang.String dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	public java.lang.String getDataGenerazione() {
		return dataGenerazione;
	}

	public void setDataGenerazione(java.lang.String dataGenerazione) {
		this.dataGenerazione = dataGenerazione;
	}

	public java.lang.String getDataConcessione() {
		return dataConcessione;
	}

	public void setDataConcessione(java.lang.String dataConcessione) {
		this.dataConcessione = dataConcessione;
	}

	public java.lang.String getDataComitato() {
		return dataComitato;
	}

	public void setDataComitato(java.lang.String dataComitato) {
		this.dataComitato = dataComitato;
	}

	public java.lang.String getIdSettoreAttivita() {
		return idSettoreAttivita;
	}

	public void setIdSettoreAttivita(java.lang.String idSettoreAttivita) {
		this.idSettoreAttivita = idSettoreAttivita;
	}

	public java.lang.String getIdAttivitaAteco() {
		return idAttivitaAteco;
	}

	public void setIdAttivitaAteco(java.lang.String idAttivitaAteco) {
		this.idAttivitaAteco = idAttivitaAteco;
	}

	public java.lang.String getIdPrioritaQsn() {
		return idPrioritaQsn;
	}

	public void setIdPrioritaQsn(java.lang.String idPrioritaQsn) {
		this.idPrioritaQsn = idPrioritaQsn;
	}

	public java.lang.String getIdObiettivoGeneraleQsn() {
		return idObiettivoGeneraleQsn;
	}

	public void setIdObiettivoGeneraleQsn(java.lang.String idObiettivoGeneraleQsn) {
		this.idObiettivoGeneraleQsn = idObiettivoGeneraleQsn;
	}

	public java.lang.String getIdObiettivoSpecificoQsn() {
		return idObiettivoSpecificoQsn;
	}

	public void setIdObiettivoSpecificoQsn(java.lang.String idObiettivoSpecificoQsn) {
		this.idObiettivoSpecificoQsn = idObiettivoSpecificoQsn;
	}

	public java.lang.String getIdStrumentoAttuativo() {
		return idStrumentoAttuativo;
	}

	public void setIdStrumentoAttuativo(java.lang.String idStrumentoAttuativo) {
		this.idStrumentoAttuativo = idStrumentoAttuativo;
	}

	public java.lang.String getIdSettoreCpt() {
		return idSettoreCpt;
	}

	public void setIdSettoreCpt(java.lang.String idSettoreCpt) {
		this.idSettoreCpt = idSettoreCpt;
	}

	public java.lang.String getIdTemaPrioritario() {
		return idTemaPrioritario;
	}

	public void setIdTemaPrioritario(java.lang.String idTemaPrioritario) {
		this.idTemaPrioritario = idTemaPrioritario;
	}

	public java.lang.String getIdIndicatoreRisultatoProgramma() {
		return idIndicatoreRisultatoProgramma;
	}

	public void setIdIndicatoreRisultatoProgramma(java.lang.String idIndicatoreRisultatoProgramma) {
		this.idIndicatoreRisultatoProgramma = idIndicatoreRisultatoProgramma;
	}

	public java.lang.String getIdIndicatoreQsn() {
		return idIndicatoreQsn;
	}

	public void setIdIndicatoreQsn(java.lang.String idIndicatoreQsn) {
		this.idIndicatoreQsn = idIndicatoreQsn;
	}

	public java.lang.String getIdTipoAiuto() {
		return idTipoAiuto;
	}

	public void setIdTipoAiuto(java.lang.String idTipoAiuto) {
		this.idTipoAiuto = idTipoAiuto;
	}

	public java.lang.String getIdTipoStrumentoProgrammazione() {
		return idTipoStrumentoProgrammazione;
	}

	public void setIdTipoStrumentoProgrammazione(java.lang.String idTipoStrumentoProgrammazione) {
		this.idTipoStrumentoProgrammazione = idTipoStrumentoProgrammazione;
	}

	public java.lang.String getIdDimensioneTerritoriale() {
		return idDimensioneTerritoriale;
	}

	public void setIdDimensioneTerritoriale(java.lang.String idDimensioneTerritoriale) {
		this.idDimensioneTerritoriale = idDimensioneTerritoriale;
	}

	public java.lang.String getIdProgettoComplesso() {
		return idProgettoComplesso;
	}

	public void setIdProgettoComplesso(java.lang.String idProgettoComplesso) {
		this.idProgettoComplesso = idProgettoComplesso;
	}

	public java.lang.String getIdSettoreCipe() {
		return idSettoreCipe;
	}

	public void setIdSettoreCipe(java.lang.String idSettoreCipe) {
		this.idSettoreCipe = idSettoreCipe;
	}

	public java.lang.String getIdSottoSettoreCipe() {
		return idSottoSettoreCipe;
	}

	public void setIdSottoSettoreCipe(java.lang.String idSottoSettoreCipe) {
		this.idSottoSettoreCipe = idSottoSettoreCipe;
	}

	public java.lang.String getIdCategoriaCipe() {
		return idCategoriaCipe;
	}

	public void setIdCategoriaCipe(java.lang.String idCategoriaCipe) {
		this.idCategoriaCipe = idCategoriaCipe;
	}

	public java.lang.String getIdNaturaCipe() {
		return idNaturaCipe;
	}

	public void setIdNaturaCipe(java.lang.String idNaturaCipe) {
		this.idNaturaCipe = idNaturaCipe;
	}

	public java.lang.String getIdTipologiaCipe() {
		return idTipologiaCipe;
	}

	public void setIdTipologiaCipe(java.lang.String idTipologiaCipe) {
		this.idTipologiaCipe = idTipologiaCipe;
	}

	public java.lang.String getFlagCardine() {
		return flagCardine;
	}

	public void setFlagCardine(java.lang.String flagCardine) {
		this.flagCardine = flagCardine;
	}

	public java.lang.String getFlagGeneratoreEntrate() {
		return flagGeneratoreEntrate;
	}

	public void setFlagGeneratoreEntrate(java.lang.String flagGeneratoreEntrate) {
		this.flagGeneratoreEntrate = flagGeneratoreEntrate;
	}

	public java.lang.String getFlagLeggeObiettivo() {
		return flagLeggeObiettivo;
	}

	public void setFlagLeggeObiettivo(java.lang.String flagLeggeObiettivo) {
		this.flagLeggeObiettivo = flagLeggeObiettivo;
	}

	public java.lang.String getFlagAltroFondo() {
		return flagAltroFondo;
	}

	public void setFlagAltroFondo(java.lang.String flagAltroFondo) {
		this.flagAltroFondo = flagAltroFondo;
	}

	public java.lang.String getFlagStatoProgettoProgramma() {
		return flagStatoProgettoProgramma;
	}

	public void setFlagStatoProgettoProgramma(java.lang.String flagStatoProgettoProgramma) {
		this.flagStatoProgettoProgramma = flagStatoProgettoProgramma;
	}

	public java.lang.String getFlagDettaglioCup() {
		return flagDettaglioCup;
	}

	public void setFlagDettaglioCup(java.lang.String flagDettaglioCup) {
		this.flagDettaglioCup = flagDettaglioCup;
	}

	public java.lang.String getIdTipoOperazione() {
		return idTipoOperazione;
	}

	public void setIdTipoOperazione(java.lang.String idTipoOperazione) {
		this.idTipoOperazione = idTipoOperazione;
	}

	public java.lang.String getFlagAggiungiCup() {
		return flagAggiungiCup;
	}

	public void setFlagAggiungiCup(java.lang.String flagAggiungiCup) {
		this.flagAggiungiCup = flagAggiungiCup;
	}

	public java.lang.String getFlagBeneficiarioCup() {
		return flagBeneficiarioCup;
	}

	public void setFlagBeneficiarioCup(java.lang.String flagBeneficiarioCup) {
		this.flagBeneficiarioCup = flagBeneficiarioCup;
	}

	public java.lang.String getAnnoConcessioneOld() {
		return annoConcessioneOld;
	}

	public void setAnnoConcessioneOld(java.lang.String annoConcessioneOld) {
		this.annoConcessioneOld = annoConcessioneOld;
	}

	public java.lang.String getDataPresentazioneDomanda() {
		return dataPresentazioneDomanda;
	}

	public void setDataPresentazioneDomanda(java.lang.String dataPresentazioneDomanda) {
		this.dataPresentazioneDomanda = dataPresentazioneDomanda;
	}

	public java.lang.String getFlagProgettoDaInviareAlMonitoraggio() {
		return flagProgettoDaInviareAlMonitoraggio;
	}

	public void setFlagProgettoDaInviareAlMonitoraggio(java.lang.String flagProgettoDaInviareAlMonitoraggio) {
		this.flagProgettoDaInviareAlMonitoraggio = flagProgettoDaInviareAlMonitoraggio;
	}

	public java.lang.String getFlagRichiestaAutomaticaDelCup() {
		return flagRichiestaAutomaticaDelCup;
	}

	public void setFlagRichiestaAutomaticaDelCup(java.lang.String flagRichiestaAutomaticaDelCup) {
		this.flagRichiestaAutomaticaDelCup = flagRichiestaAutomaticaDelCup;
	}

	public java.lang.String getIdObiettivoTematico() {
		return idObiettivoTematico;
	}

	public void setIdObiettivoTematico(java.lang.String idObiettivoTematico) {
		this.idObiettivoTematico = idObiettivoTematico;
	}

	public java.lang.String getIdClassificazioneRA() {
		return idClassificazioneRA;
	}

	public void setIdClassificazioneRA(java.lang.String idClassificazioneRA) {
		this.idClassificazioneRA = idClassificazioneRA;
	}

	public java.lang.String getIdGrandeProgetto() {
		return idGrandeProgetto;
	}

	public void setIdGrandeProgetto(java.lang.String idGrandeProgetto) {
		this.idGrandeProgetto = idGrandeProgetto;
	}

	public java.lang.String getFlagPPP() {
		return flagPPP;
	}

	public void setFlagPPP(java.lang.String flagPPP) {
		this.flagPPP = flagPPP;
	}

	public java.lang.String getFlagStrategico() {
		return flagStrategico;
	}

	public void setFlagStrategico(java.lang.String flagStrategico) {
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n"+this.getClass().getName()+": ");
		try {
			Set<String> properties = BeanUtil.getProperties(this.getClass());
			for (String propName : properties) {
				sb.append("\n"+propName+" = "+BeanUtil.getPropertyValueByName(this, propName));
			}
		} catch (IntrospectionException e) {
		}
		return sb.toString();
	}
	
}
