/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import it.csi.pbandi.pbweb.integration.dao.request.AnteprimaDichiarazioneDiSpesaCulturaRequest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

//import it.csi.pbandi.pbweb.dto.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
//import it.csi.pbandi.pbweb.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbweb.dto.DocumentoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.RigaTabRichiesteIntegrazioniDs;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.AllegatiDichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.EsitoOperazioneAnteprimaDichiarazioneSpesa;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.EsitoOperazioneInviaDichiarazioneDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.EsitoOperazioneVerificaDichiarazioneSpesa;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.InizializzaIntegrazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.InizializzaInvioDichiarazioneDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.TipoAllegatoDTO;
import it.csi.pbandi.pbweb.dto.profilazione.DecodificaDTO;
import it.csi.pbandi.pbweb.integration.dao.request.AnteprimaDichiarazioneDiSpesaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaDichiarazioneDiSpesaRequest;

public interface DichiarazioneDiSpesaDAO {
	
	Boolean getIsBeneficiarioPrivatoCittadino(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean isBottoneConsuntivoVisibile(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<DecodificaDTO> rappresentantiLegali(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoOperazioneVerificaDichiarazioneSpesa verificaDichiarazioneDiSpesa(VerificaDichiarazioneDiSpesaRequest verificaDichiarazioneDiSpesaRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<DecodificaDTO> delegatiCombo(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	String iban(Long idProgetto, Long idSoggettoBeneficiario, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<TipoAllegatoDTO> tipoAllegati(Long idBandoLinea, String codiceTipoDichiarazioneDiSpesa, Long idDichiarazione, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO salvaTipoAllegati(ArrayList<TipoAllegatoDTO> listaTipoAllegati, String codiceTipoDichiarazioneDiSpesa, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<DocumentoAllegatoDTO> allegatiDichiarazioneDiSpesaPerIdProgetto(String codiceTipoDichiarazioneDiSpesa, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	AllegatiDichiarazioneDiSpesaDTO allegatiDichiarazioneDiSpesa(Long idDichiarazioneDiSpesa, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	AllegatiDichiarazioneDiSpesaDTO allegatiDichiarazioneDiSpesaIntegrazioni(Long idDichiarazioneDiSpesa, Long idProgetto, Long idUtente, String idIride) throws Exception;
	EsitoDTO disassociaAllegatoDichiarazioneDiSpesa(Long idDocumentoIndex, Long idDichiarazioneDiSpesa, Long idProgetto, String tipoDichiarazione, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO disassociaAllegatoIntegrazioneDiSpesa(Long idDocumentoIndex, Long idIntegrazioneDiSpesa, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoAssociaFilesDTO associaAllegatiADichiarazioneDiSpesa(AssociaFilesRequest associaFilesRequest, Long idUtente) throws InvalidParameterException, Exception;
	EsitoAssociaFilesDTO associaAllegatiADichiarazioneDiSpesaFinale(AssociaFilesRequest associaFilesRequest, Long idUtente) throws InvalidParameterException, Exception;
	EsitoAssociaFilesDTO associaAllegatiAIntegrazioneDiSpesa(AssociaFilesRequest associaFilesRequest, Long idUtente) throws InvalidParameterException, Exception;
	InizializzaInvioDichiarazioneDiSpesaDTO inizializzaInvioDichiarazioneDiSpesa (Long idProgetto, Long idBandoLinea, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	InizializzaIntegrazioneDiSpesaDTO inizializzaIntegrazioneDiSpesa (Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoOperazioneAnteprimaDichiarazioneSpesa anteprimaDichiarazioneDiSpesa(AnteprimaDichiarazioneDiSpesaRequest request, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoOperazioneAnteprimaDichiarazioneSpesa anteprimaDichiarazioneDiSpesaCultura(AnteprimaDichiarazioneDiSpesaCulturaRequest request, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoOperazioneInviaDichiarazioneDTO inviaDichiarazioneDiSpesa(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean salvaFileFirmato(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean salvaFileFirmaAutografa(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean verificaFirmaDigitale(Long idDocumentoIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<RigaTabRichiesteIntegrazioniDs> integrazioniSpesaByIdProgetto(Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO marcaComeDichiarazioneDiIntegrazione(Long idDocumentoIndex, Long idIntegrazioneDiSpesa, Long idProgetto, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO inviaIntegrazioneDiSpesaAIstruttore(Long idIntegrazioneDiSpesa, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean isBandoCultura(Long idBandoLinea, Long idUtente, String idIride) throws Exception;
	
	//Boolean testAnteprimaDichiarazioneDiSpesa(Long idBandoLinea, Long idProgetto, Long idSoggetto, Long idSoggettoBeneficiario, Long idRappresentanteLegale, String codiceTipoDichiarazioneDiSpesa, Long idUtente, String idIride) throws InvalidParameterException, Exception;
} 
