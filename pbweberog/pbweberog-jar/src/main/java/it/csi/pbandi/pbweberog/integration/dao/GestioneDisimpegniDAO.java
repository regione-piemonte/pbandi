/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import java.util.ArrayList;
import java.util.List;

import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.EsitoSalvaRevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.MancatoRecuperoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.QuotaParteVoceDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbweberog.dto.CodiceDescrizione;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.disimpegni.ModalitaAgevolazioneDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.RevocaDTO;
import it.csi.pbandi.pbweberog.dto.disimpegni.RigaModificaRevocaItem;
import it.csi.pbandi.pbweberog.dto.revoca.RigaRevocaItem;
import it.csi.pbandi.pbweberog.integration.request.RequestAssociaIrregolarita;

public interface GestioneDisimpegniDAO {

	EsitoOperazioni checkPropostaCertificazione(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException;

	it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] findRiepilogoRevoche(Long idUtente, String idIride, RevocaDTO filtro) throws FormalParameterException, Exception;

	ArrayList<CodiceDescrizione> findPeriodi(Long idUtente, String idIride) throws FormalParameterException;

	MancatoRecuperoDTO[] findModalitaRecupero(Long idUtente, String idIride) throws FormalParameterException;

	QuotaParteVoceDiSpesaDTO[] findQuotePartePerRevoca(Long idSoggetto, String idIride,
			QuotaParteVoceDiSpesaDTO filtro) throws Exception;

	RevocaModalitaAgevolazioneDTO[] findRevoche(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO filtro) throws Exception;

	EsitoSalvaRevocaDTO checkDisimpegni(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO[] transform) throws FormalParameterException;

	EsitoSalvaRevocaDTO salvaDisimpegni(Long idUtente, String idIride,   RevocaModalitaAgevolazioneDTO[] revocheDTO) throws FormalParameterException, Exception;

	EsitoSalvaRevocaDTO modificaDisimpegno(Long idUtente, String idIride,
			it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modalitaAgevolazione) throws FormalParameterException, Exception;

	EsitoSalvaRevocaDTO eliminaRevoca(Long idUtente, String idIride, Long idRevoca, Long idProgetto) throws Exception;

	Long[] findDsIrregolarita(Long idUtente, String idIride, Long idProgetto) throws FormalParameterException;

	EsitoSalvaRevocaDTO saveIrregolarita(Long idUtente, String idIride, RequestAssociaIrregolarita requestSalva) throws Exception;

	boolean revocaConIrregolarita(Long idUtente, String idIride, Long idRevoca) throws Exception;

	

}
