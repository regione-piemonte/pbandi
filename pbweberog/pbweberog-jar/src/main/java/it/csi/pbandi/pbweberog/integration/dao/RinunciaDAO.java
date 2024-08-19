/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao;

import java.security.InvalidParameterException;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.dto.richiestaerogazione.RappresentanteLegaleDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.DichiarazioneDiRinunciaDTO;
import it.csi.pbandi.pbweberog.dto.erogazione.EsitoScaricaDichiarazioneDiRinuncia;
import it.csi.pbandi.pbweberog.exception.ErogazioneException;

public interface RinunciaDAO {

	RappresentanteLegaleDTO[] findRappresentantiLegali(Long idUtente, String idIride, Long idProgetto, Long idSoggettoRappresentante) throws UnrecoverableException;

	DichiarazioneDiRinunciaDTO inviaDichiarazioneDiRinuncia(Long idUtente, String idIride,
			DichiarazioneDiRinunciaDTO dichiarazioneDTO) throws ErogazioneException;

	EsitoScaricaDichiarazioneDiRinuncia scaricaDichiarazioneDiRinuncia(Long idUtente, String idIride,
			Long idDocumentoIndex) throws ErogazioneException;
	
	Boolean salvaFileFirmato(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	
	Boolean salvaFileFirmaAutografa(MultipartFormDataInput multipartFormData, Long idUtente, String idIride) throws InvalidParameterException, Exception;

	Boolean verificaFirmaDigitale(Long idDocumentoIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	
	Boolean salvaInvioCartaceo(Boolean invioCartaceo, Long idDocumentoIndex, Long idUtente, String idIride) throws InvalidParameterException, Exception;

	Boolean getIsBeneficiarioPrivatoCittadino(Long idProgetto, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	Boolean getIsRegolaApplicabileForProgetto(Long idProgetto, String codiceRegola, Long idUtente, String idIride) throws SystemException, UnrecoverableException, CSIException;
	
}
