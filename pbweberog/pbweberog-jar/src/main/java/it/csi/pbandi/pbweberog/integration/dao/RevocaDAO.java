/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;

import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.EsitoSalvaRevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.QuotaParteVoceDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaModalitaAgevolazioneDTO;

public interface RevocaDAO {

	QuotaParteVoceDiSpesaDTO[] findQuotePartePerRevoca(Long idUtente, String idIride, QuotaParteVoceDiSpesaDTO filtro) throws Exception;

	RevocaModalitaAgevolazioneDTO[] findRevoche(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO filtro) throws Exception;

	EsitoSalvaRevocaDTO salvaRevoche(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO[] transform) throws Exception;

	Boolean checkPropostaCertificazione(Long idUtente, String idIride, Long idProgetto) throws Exception;

	it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] findRiepilogoRevoche(Long idUtente, String idIride, RevocaDTO filtro) throws Exception;

	EsitoSalvaRevocaDTO modificaRevoca(Long idUtente, String idIride,
			it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modalitaAgevolazione) throws Exception;

	EsitoSalvaRevocaDTO cancellaRevoca(Long idUtente, String idIride, Long idRevoca) throws Exception;

	EsitoSalvaRevocaDTO checkRevoche(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO[] transform) throws Exception;
	
	HashMap<String, String> getTitoloBandoENumeroDomandaValidazione(Long idProgetto, String regola) throws Exception;
	
	ResponseEntity<String> invioNotificaDomRes(String note, String dataRevoca, String dataDecorrenza, Long idProgetto,
			Long idMotivoRevoca, String beneficiario) throws Exception;


}
