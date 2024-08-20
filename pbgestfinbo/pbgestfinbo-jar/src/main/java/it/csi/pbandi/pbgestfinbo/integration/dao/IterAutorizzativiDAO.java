/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import it.csi.pbandi.pbgestfinbo.dto.RicercaControlliDTO;
import it.csi.pbandi.pbgestfinbo.exception.DaoException;
import it.csi.pbandi.pbgestfinbo.integration.vo.iterAutorizzativi.*;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.BandoLineaInterventVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.DenominazioneSuggestVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.suggestion.ProgettoSuggestVO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface IterAutorizzativiDAO {

	//GET ITER
	List <IterAutorizzativiMiniVO> getIterAutorizzativi( RicercaControlliDTO searchVO,HttpServletRequest req);

	//GET DETTAGLIO ITER
	List<DettaglioIterAutorizzativiVO> getDettaglioIter(BigDecimal idWorkflow);

	//GET ALLEGATI ITER
	List<DocumentoIterVO> getAllegati(BigDecimal idWorkflow);
	EsitoLeggiFile reportDettaglioDocumentoDiSpesa (Long idDichiarazioneDiSpesa, Long idUtente, String idIride) throws Exception;

	//GESTIONE ITER
	void respingiIter(Long idWorkFlow, String motivazione, HttpServletRequest req) throws Exception;
	void autorizzaIter(Long idWorkFlow, HttpServletRequest req) throws Exception;

	boolean checkPermessiIter(Long idWorkFlow, HttpServletRequest req);

	//AVVIA ITER
	String avviaIterAutorizzativo(Long idTipoIter, Long idEntita, Long idTarget, Long idProgetto, Long idUtente);

	//SUGGEST
	List<ProgettoSuggestVO> suggestProgetto(String titoloProgetto, Long idBando, Long idSoggetto, HttpServletRequest req)throws DaoException;
	List<BandoLineaInterventVO> suggestBando(String nomeBando, Long idSoggetto) throws DaoException;
	List<DenominazioneSuggestVO> suggestBeneficiario(String denominazione, HttpServletRequest req) throws DaoException;
	List <StatoIterVO> getStatoIter(HttpServletRequest req);
	Object suggestIter(SuggestIterVO sugVO);
	Object getTendinaBando();
	Object getTendinaProgetto();
	Object getTendinaBeneficiario();
}
