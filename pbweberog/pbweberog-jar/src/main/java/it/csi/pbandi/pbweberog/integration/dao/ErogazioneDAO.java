/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import java.util.List;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.csi.wrapper.UserException;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.FileDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.EstremiBancariDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.*;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.*;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.erogazione.*;
import it.csi.pbandi.pbweberog.exception.ErogazioneException;

public interface ErogazioneDAO {

	String findCodiceProgetto(Long idProgetto);

	EsitoErogazioneDTO findErogazione(Long idUtente, String idIride, Long idProgetto, Long idBandoLinea,
			Long idFormaGiuridica, Long idDimensioneImpresa) throws Exception;

	CodiceDescrizioneCausaleDTO[] findCausaliErogazione(Long idUtente, String idIride, Long idProgetto,
			Long idBandoLinea, Long idFormaGiuridica, Long idDimensioneImpresa) throws Exception;

	CodiceDescrizioneDTO[] findModalitaAgevolazionePerContoEconomico(Long idUtente, String idIride, Long idProgetto)
			throws Exception;

	EsitoErogazioneDTO inserisciErogazione(Long idUtente, String idIride, Long idProgetto, ErogazioneDTO erogazioneDTO,
			String codUtenteFlux) throws UserException, UnrecoverableException;

	EsitoOperazioni eliminaErogazione(Long idUtente, String idIride, Long idErogazione)
			throws FormalParameterException, ErogazioneException;

	EsitoOperazioni modificaErogazione(Long idUtente, String idIride, Long idProgetto, ErogazioneDTO erogazioneDTO)
			throws FormalParameterException, ErogazioneException, UnrecoverableException;

	EsitoRichiestaErogazioneDTO findDatiRiepilogoRichiestaErogazione(Long idUtente, String idIride, Long idProgetto,
			String codCausaleErogazione, Long idDimensioneImpresa, Long idFormaGiuridica, Long idBandoLinea,
			Long idSoggetto) throws Exception;

	RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente, String idIride, Long idProgetto,
			Long idSoggettoRappresentante);

	EsitoReportRichiestaErogazioneDTO creaReportRichiestaErogazione(Long idUtente, String idIride, Long idProgetto,
			RichiestaErogazioneDTO richiestaErogazione, RappresentanteLegaleDTO rappresentanteLegale, Long idDelegato,
			Long idSoggetto, List<PbandiTAffidServtecArVO> affidamentiServiziLavori) throws Exception;

	EsitoErogazioneDTO findDettaglioErogazione(Long idUtente, String idIride, Long idErogazione, Long idFormaGiuridica,
			Long idDimensioneImpresa, Long idBandoLinea, Long idProgetto) throws FormalParameterException;

	EsitoAssociaFilesDTO associaAllegatiARichiestaErogazione(AssociaFilesRequest associaFilesRequest, Long idUtente,
			String idIride) throws Exception;

	FileDTO[] getFilesAssociatedRichiestaErogazioneByIdProgetto(Long idProgetto) throws Exception;

	FileDTO[] getFilesAssociatedRichiestaErogazioneByIdErogazione(Long idErogazione) throws Exception;
	EsitoOperazioni disassociaAllegatiARichiestaErogazione(Long idDocumentoIndex, Long idErogazione, Long idProgetto,
			Long idUtente, String idIride) throws Exception;

	InizializzaErogazioneDTO inizializzaErogazione(Long idProgetto) throws Exception;

	Object getModalitaAgevolazioneDaVisualizzare(Long idModalitaAgev);

	PbandiTRichiestaErogazioneVO findRichiestaErogazione(Long idErogazione) throws RuntimeException;

	PbandiDCausaleErogazioneVO findCausaleErogazione(Long idCausaleErogazione) throws RuntimeException;

	List<PbandiTDocumentoIndexVO> findAllegatiRichiestaErogazione(Long idProgetto, Long idErogazione) throws RuntimeException;

	List<PbandiTAffidServtecArVO> findAffidamentiRichiestaErogazione(Long idErogazione);

	List<FideiussioneTipoTrattamentoDTO> findFideiussioniRichiestaErogazione(Long idProgetto);
	List<PbandiTFideiussioneVO> findFideiussioniPerProgetto(Long idProgetto);

	EsitoOperazioni verificaErogazione(Long idErogazione, Long idUtente, boolean verificato);

	EstremiBancariDTO findEstremiBancariVerificaErogazione(Long idProgetto);
}
