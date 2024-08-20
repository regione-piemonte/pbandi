/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.search.FiltroRevocaVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.SuggestIdDescVO;

import java.util.List;

public interface SuggestRevocheDAO {

    Long newNumeroRevoca();

    List<SuggestIdDescVO> suggestNumeroRevoche(FiltroRevocaVO filtroRevocaVO) throws DaoException;

    List<SuggestIdDescVO> suggestBeneficiario(String nomeBeneficiario, Long progrBandoLineaIntervent) throws DaoException;

    List<SuggestIdDescVO> suggestBandoLineaIntervent(String nomeBandoLinea, Long idSoggetto) throws DaoException;

    List<SuggestIdDescVO> suggestProgetto(String codiceVisualizzatoProgetto, Long progrBandoLineaIntervent, Long idSoggetto) throws DaoException;

    List<SuggestIdDescVO> suggestCausaRevoche(FiltroRevocaVO filtroRevocaVO);

    List<SuggestIdDescVO> suggestStatoRevoche(FiltroRevocaVO filtroRevocaVO);

    List<SuggestIdDescVO> suggestAttivitaRevoche(FiltroRevocaVO filtroRevocaVO);

    List<SuggestIdDescVO> suggestAllCausaRevoca();

    List<SuggestIdDescVO> suggestAllAutoritaControllante();

    List<SuggestIdDescVO> getModalitaRecupero();

    List<SuggestIdDescVO> getMotivoRevoca();

}
