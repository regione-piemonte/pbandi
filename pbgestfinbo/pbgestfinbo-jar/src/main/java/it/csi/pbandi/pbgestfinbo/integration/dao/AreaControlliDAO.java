/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbgestfinbo.integration.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbgestfinbo.dto.AttivitaDTO;
import it.csi.pbandi.pbgestfinbo.dto.DocumentoIndexVO;
import it.csi.pbandi.pbgestfinbo.dto.RicercaControlliDTO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.ControlloLocoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.RichiestaIntegrazioneControlloLocoVO;
import it.csi.pbandi.pbgestfinbo.integration.vo.affidamenti.RichiestaProrogaVO;

public interface AreaControlliDAO {

	List<AttivitaDTO>  getListaSugg(RicercaControlliDTO suggestDTO, int id);

	List<AttivitaDTO> getStatoControllo();

	List<ControlloLocoVO> getElencoControlli(RicercaControlliDTO controlloDTO, HttpServletRequest req);

	List<AttivitaDTO> getAutoritaControllante(HttpServletRequest req);

	ControlloLocoVO getControllo(BigDecimal idControllo, BigDecimal idProgetto, HttpServletRequest req);

	BigDecimal gestioneControllo(ControlloLocoVO controllo, HttpServletRequest req);

	RichiestaIntegrazioneControlloLocoVO checkRichiestaIntegrazione(BigDecimal idControllo, HttpServletRequest req);

	//List<AttivitaDTO> getElencoAllegati(BigDecimal idControllo);

	Boolean updateControlloFinp(ControlloLocoVO controlloVO, BigDecimal idUtente, int idAttivitaControllo);

	Boolean salvaAllegato(MultipartFormDataInput multipartFormData);

	BigDecimal avviaIterIntegrazione(RichiestaIntegrazioneControlloLocoVO richiesta, BigDecimal idUtente);

	Boolean chiamaIterAuto002( MultipartFormDataInput multipartFormData);

	Boolean chiudiRichiestaIntegr(BigDecimal idRichiestaIntegr, BigDecimal idUtente);

	RichiestaProrogaVO getRichProroga(BigDecimal idRichiestaIntegr);

	Boolean gestisciProroga(RichiestaProrogaVO proroga, BigDecimal idUtente, int id);

	Boolean salvaDataNotifica(RichiestaIntegrazioneControlloLocoVO richiesta, HttpServletRequest req);

	BigDecimal salvaAltroControllo(ControlloLocoVO controllo, BigDecimal idUtente, int idAttivitaControllo);

	List<AttivitaDTO>  getListaBando(BigDecimal idSoggetto);

	List<AttivitaDTO>  getListaProgetto(BigDecimal idSoggetto, BigDecimal progBandoLinea);

	List<ControlloLocoVO> getAltriControlli(RicercaControlliDTO searchDTO);

	ControlloLocoVO getAltroControllo(BigDecimal idControllo, BigDecimal idProgetto);

	Boolean updateAltroControllo(ControlloLocoVO controllo, BigDecimal idUtente);

	RichiestaIntegrazioneControlloLocoVO checkRichiestaIntegrAltroControllo(BigDecimal idControllo);

	List<DocumentoIndexVO> getElencoFile(BigDecimal idControllo, BigDecimal idEntita);

	List<RichiestaProrogaVO> getElencoRichProroga(BigDecimal idRichiestaIntegr);

	List<DocumentoIndexVO>  getElencoFileBeneficario(BigDecimal idTarget, BigDecimal idEntita);

	Boolean avviaIterEsitoPositivoControllo(Long idProgetto, Long idTarget, Boolean isAltriControlli, HttpServletRequest req);

	int getPreviousStateControllo(BigDecimal idControllo, int i);

	Object salvaAllegati(MultipartFormDataInput multipartFormData, HttpServletRequest req);

	//RichiestaProrogaVO getRichProrogaAltroControllo(BigDecimal idRichiestaIntegr);

}
