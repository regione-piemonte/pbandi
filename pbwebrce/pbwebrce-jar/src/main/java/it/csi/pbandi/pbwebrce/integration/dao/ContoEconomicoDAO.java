/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbwebrce.dto.DocumentoAllegato;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.AltriCostiDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoInviaPropostaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoSalvaPropostaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoSalvaRimodulazioneConfermataDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoSalvaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.EsitoValidaRimodulazioneConfermataDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaConcludiPropostaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaConcludiRichiestaContoEconomicoDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaConcludiRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaPropostaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaRimodulazioneDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaRimodulazioneIstruttoriaDTO;
import it.csi.pbandi.pbwebrce.dto.contoeconomico.InizializzaUploadFileFirmatoDTO;
import it.csi.pbandi.pbwebrce.integration.request.InviaPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaPropostaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRichiestaContoEconomicoRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRimodulazioneConfermataRequest;
import it.csi.pbandi.pbwebrce.integration.request.SalvaRimodulazioneRequest;
import it.csi.pbandi.pbwebrce.integration.request.ValidaRimodulazioneConfermataRequest;

public interface ContoEconomicoDAO {

	InizializzaPropostaRimodulazioneDTO inizializzaPropostaRimodulazione(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaPropostaRimodulazioneDTO inizializzaPropostaRimodulazioneInDomanda(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean contoEconomicoLocked(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaPropostaRimodulazioneDTO salvaPropostaRimodulazione(SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;	
	EsitoSalvaPropostaRimodulazioneDTO salvaPropostaRimodulazioneConfermata(SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaConcludiPropostaRimodulazioneDTO inizializzaConcludiPropostaRimodulazione (Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoAssociaFilesDTO associaAllegatiAPropostaRimodulazione(AssociaFilesRequest associaFilesRequest, Long idUtente) throws InvalidParameterException, Exception;
	EsitoOperazioni disassociaAllegatoPropostaRimodulazione(Long idDocumentoIndex, Long idProgetto, Long idUtente) throws InvalidParameterException, Exception;
	ArrayList<DocumentoAllegato> allegatiPropostaRimodulazione(Long idProgetto, Long idUtente) throws InvalidParameterException, Exception;
	EsitoInviaPropostaRimodulazioneDTO inviaPropostaRimodulazione(InviaPropostaRimodulazioneRequest inviaPropostaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaUploadFileFirmatoDTO inizializzaUploadFileFirmato(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean salvaFileFirmato(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean verificaFirmaDigitale(Long idDocumentoIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaRimodulazioneDTO inizializzaRimodulazione(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaRimodulazioneDTO salvaRimodulazione(SalvaRimodulazioneRequest salvaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaConcludiRimodulazioneDTO inizializzaConcludiRimodulazione (Long idProgetto, Long idUtente, Long long1, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaRimodulazioneConfermataDTO salvaRimodulazioneConfermata(SalvaRimodulazioneConfermataRequest salvaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoValidaRimodulazioneConfermataDTO validaRimodulazioneConfermata (ValidaRimodulazioneConfermataRequest req, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoLeggiFile scaricaRimodulazione (Long idProgetto, Long idSoggettoBeneficiario, Long idContoEconomico, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaRimodulazioneIstruttoriaDTO inizializzaRimodulazioneIstruttoria(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaRimodulazioneDTO validaRimodulazioneIstruttoria(SalvaRimodulazioneRequest salvaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaRimodulazioneDTO salvaRimodulazioneIstruttoria(SalvaRimodulazioneRequest salvaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaConcludiRimodulazioneDTO inizializzaConcludiRimodulazioneIstruttoria (Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaRimodulazioneConfermataDTO salvaRimodulazioneIstruttoriaConfermata(SalvaRimodulazioneConfermataRequest salvaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaPropostaRimodulazioneDTO validaPropostaRimodulazioneInDomanda(SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaPropostaRimodulazioneDTO salvaPropostaRimodulazioneInDomanda(SalvaPropostaRimodulazioneRequest salvaPropostaRimodulazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaConcludiRichiestaContoEconomicoDTO inizializzaConcludiRichiestaContoEconomico (Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoSalvaPropostaRimodulazioneDTO salvaRichiestaContoEconomico (SalvaRichiestaContoEconomicoRequest salvaRichiestaContoEconomicoRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	List<AltriCostiDTO> getAltriCosti(Long idBandoLinea, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
}
