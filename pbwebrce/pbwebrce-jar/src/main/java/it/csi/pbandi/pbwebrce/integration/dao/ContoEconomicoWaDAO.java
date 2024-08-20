/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebrce.integration.dao;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoAssociaFilesDTO;
import it.csi.pbandi.pbservizit.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbservizit.integration.dao.request.AssociaFilesRequest;
import it.csi.pbandi.pbwebrce.dto.ComuneProvinciaSedeDTO;
import it.csi.pbandi.pbwebrce.dto.CupProgettoNumeroDomandaDTO;
import it.csi.pbandi.pbwebrce.dto.DatiQteDTO;
import it.csi.pbandi.pbwebrce.dto.DocumentoAllegato;
import it.csi.pbandi.pbwebrce.dto.DtFineEffEstremiAttoDTO;
import it.csi.pbandi.pbwebrce.dto.EsitoDTO;
import it.csi.pbandi.pbwebrce.dto.EsitoOperazioni;
import it.csi.pbandi.pbwebrce.dto.QteFaseDTO;
import it.csi.pbandi.pbwebrce.dto.QteHtmlDTO;
import it.csi.pbandi.pbwebrce.dto.TitoloBandoNomeBandoLineaDTO;
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

public interface ContoEconomicoWaDAO {

	QteHtmlDTO getModelloQte(Long progrBandoLineaIntervento, Long idSoggetto, String codiceRuolo, Long idUtente,
			String idIride, Long idBeneficiario) throws InvalidParameterException, Exception;

	QteHtmlDTO getQteProgetto(Long idProgetto, Long idSoggetto, String codiceRuolo, Long idUtente, String idIride,
			Long idBeneficiario) throws InvalidParameterException, Exception;

	EsitoDTO salvaQteHtmlProgetto(Long idProgetto, Long idQtesHtmlProgetto, String htmlQtesProgetto, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception;

	EsitoDTO salvaQteDatiProgetto(Long idProgetto, List<DatiQteDTO> datiQte, Long idSoggetto, String codiceRuolo,
			Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception;

	List<QteFaseDTO> getColonneModelloQte(Long progrBandoLineaIntervento, Long idSoggetto, String codiceRuolo,
			Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception;

	String getDescEnteCompetenza(Long progrBandoLineaIntervento, String descBreveRuoloEnteCompetenza, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception;

	String getDescSettoreEnte(Long progrBandoLineaIntervento, String descBreveRuoloEnteCompetenza, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception;

	TitoloBandoNomeBandoLineaDTO getTitoloBandoNomeBandoLinea(Long progrBandoLineaIntervento, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception;

	CupProgettoNumeroDomandaDTO getCupProgettoNumeroDomanda(Long idProgetto, Long idSoggetto, String codiceRuolo,
			Long idUtente, String idIride, Long idBeneficiario) throws InvalidParameterException, Exception;

	ComuneProvinciaSedeDTO getComuneProvinciaSede(Long idProgetto, String descBreveTipoSede, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception;

	DtFineEffEstremiAttoDTO getDtFineEffEstremiAtto(Long idProgetto, String codIgrueFaseMonit, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception;

	Boolean salvaCCCDefinitivo(MultipartFormDataInput multipartFormData, Long idUtente, String codiceRuolo,
			String idIride) throws InvalidParameterException, Exception;

	Long getIdDocumentoIndexCCC(Long idProgetto, Long idQtesHtmlProgetto, String descBreveTipoDocIndex, Long idSoggetto,
			String codiceRuolo, Long idUtente, String idIride, Long idBeneficiario)
			throws InvalidParameterException, Exception;
}
