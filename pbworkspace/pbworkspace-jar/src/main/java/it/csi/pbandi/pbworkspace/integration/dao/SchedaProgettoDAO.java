/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbworkspace.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import it.csi.pbandi.pbworkspace.dto.CodiceDescrizione;
import it.csi.pbandi.pbworkspace.dto.EsitoDTO;
import it.csi.pbandi.pbworkspace.dto.InizializzaComboDTO;
import it.csi.pbandi.pbworkspace.dto.InizializzaSchedaProgettoDTO;
import it.csi.pbandi.pbworkspace.dto.schedaProgetto.SchedaProgetto;
import it.csi.pbandi.pbworkspace.integration.request.SalvaSchedaProgettoRequest;

public interface SchedaProgettoDAO {

	InizializzaSchedaProgettoDTO inizializzaSchedaProgetto(Long progrBandoLineaIntervento, Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaComboDTO inizializzaCombo(Long progrBandoLineaIntervento, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<CodiceDescrizione> popolaComboAttivitaAteco(Long idUtente, String idIride, Long idSettoreAttivita) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboObiettivoGeneraleQsn(Long idUtente, String idIride, Long idListaPrioritaQsn) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboObiettivoSpecificoQsn(Long idUtente, String idIride, Long idObiettivoGenerale) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboClassificazioneRA(Long idUtente, String idIride, Long idObiettivoTematico) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboSottoSettoreCipe(Long idUtente, String idIride, Long idSettoreCipe) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboCategoriaCipe(Long idUtente, String idIride, Long idSottoSettoreCipe) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboTipologiaCipe(Long idUtente, String idIride, Long idNaturaCipe) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboRegione(Long idUtente, String idIride) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboRegioneNascita(Long idUtente, String idIride) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboProvincia(Long idUtente, String idIride, Long idRegione) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboProvinciaNascita(Long idUtente, String idIride, Long idRegione) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboComuneEstero(Long idUtente, String idIride, Long idNazione) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboComuneEsteroNascita(Long idUtente, String idIride, Long idNazione) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboComuneItaliano(Long idUtente, String idIride, Long idProvincia) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboComuneItalianoNascita(Long idUtente, String idIride, Long idProvincia) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboDenominazioneSettori(Long idUtente, String idIride, Long idEnte) throws Exception;
	it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.BeneficiarioCspDTO[] ricercaBeneficiarioCsp(String codiceFiscale, Long idUtente, String idIride) throws Exception;
	it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.RapprLegaleCspDTO[] ricercaRapprLegaleCsp(String codiceFiscale, Long idUtente, String idIride) throws Exception;
	ArrayList<CodiceDescrizione> popolaComboDenominazioneEnteDipUni(Long idUtente, String idIride, Long idAteneo) throws Exception;
	EsitoDTO salvaSchedaProgetto(SalvaSchedaProgettoRequest salvaSchedaProgettoRequest, Long idUtente, String idIride) throws Exception;
	Boolean verificaNumeroDomandaUnico(String numDomanda, Long idProgetto, Long idBandoLinea) throws Exception;
	Boolean verificaCupUnico(String cup, Long idProgetto) throws Exception;
	it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO caricaInfoDirezioneRegionale(Long idEnteCompetenza, Long idUtente, String idIride) throws Exception;
	it.csi.pbandi.pbworkspace.pbandisrv.dto.gestioneavvioprogetto.EsitoSoggettoDTO caricaInfoPubblicaAmministrazione(Long idEnteCompetenza, Long idUtente, String idIride) throws Exception;
}
