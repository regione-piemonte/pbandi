/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SuggestionDAO {
	List<ProgettoSuggestVO> suggestProgetto(String codiceProgetto, HttpServletRequest req) throws DaoException;

	List<ProgettoSuggestVO> suggestProgetto(String codiceProgetto, Long idBando, HttpServletRequest req) throws DaoException;

	List<DenominazioneSuggestVO> suggestDenominazione(String denominazione, HttpServletRequest req) throws DaoException;

	List<BandoSuggestVO> suggestionBando(String titoloBando, HttpServletRequest req) throws DaoException;

	List<AgevolazioneSuggestVO> suggestionAgevolazione(String descrizioneAgevolazione, HttpServletRequest req) throws DaoException;

	List<IdDistintaVO> suggestionDistinta(String idDistinta, Boolean respinta, HttpServletRequest req) throws DaoException;

	List<IdSoggettoVO> suggestionIdSoggetto(String idSoggetto, HttpServletRequest req) throws DaoException;

	List<SuggestIdDescVO> suggestCodiceFondoFinpis(String codiceFondoFinpis, HttpServletRequest req) throws DaoException;

	List<CodiceFiscaleVO> suggestionCodiceFiscale(String codiceFiscale, Long idSoggetto, HttpServletRequest req) throws DaoException;

	List<PartitaIvaVO> suggestionPartitaIva(String partitaIva, Long idSoggetto, HttpServletRequest req) throws DaoException;

	List<DenominazioneVO> suggestionDenominazione(String denominazione, Long idSoggetto, HttpServletRequest req) throws DaoException;

	List<IdDomandaVO> suggestionIdDomanda(String numeroDomanda, Long idSoggetto, HttpServletRequest req) throws DaoException;

	List<IdProgettoVO> suggestionIdProgetto(String codProgetto, Long idSoggetto, HttpServletRequest req) throws DaoException;
	
	List<NomeVO> getNome(String nome, Long idSoggetto, HttpServletRequest req)  throws DaoException;
	
	List<CognomeVO> getCognome(String cognome, Long idSoggetto, HttpServletRequest req)  throws DaoException;

	AutofillVO getAutofill(Long idSoggetto, String tipoSoggetto, Long idDomanda, Long idProgetto, HttpServletRequest req)  throws DaoException;

	
}
