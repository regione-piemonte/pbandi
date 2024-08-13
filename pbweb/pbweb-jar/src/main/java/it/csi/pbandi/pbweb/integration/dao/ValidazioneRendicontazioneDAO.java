/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweb.integration.dao;

import java.io.File;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.dto.EsitoOperazioni;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.*;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.pbandi.pbservizit.pbandisrv.exception.certificazione.CertificazioneException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCCostantiVO;
import it.csi.pbandi.pbweb.dto.EsitoDTO;
import it.csi.pbandi.pbweb.dto.archivioFile.EsitoLeggiFile;
import it.csi.pbandi.pbweb.dto.custom.EsitoValidazioneRendicontazionePiuGreen;
import it.csi.pbandi.pbweb.dto.dichiarazioneDiSpesa.MensilitaDichiarazioneSpesaDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.DocumentoDiSpesaDematDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.EsitoVerificaOperazioneMassivaDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.InitIntegrazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.InizializzaPopupChiudiValidazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.InizializzaValidazioneDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.RigaRicercaDocumentiDiSpesaDTO;
import it.csi.pbandi.pbweb.dto.validazioneRendicontazione.initDropDownCL_DTO;
import it.csi.pbandi.pbweb.integration.dao.request.CercaDocumentiDiSpesaValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.OperazioneMassivaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ProseguiChiudiValidazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.RichiediIntegrazioneRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaMensilitaRequest;
import it.csi.pbandi.pbweb.integration.dao.request.ValidaParzialmenteDocumentoRequest;
import it.csi.pbandi.pbweb.integration.dao.request.VerificaOperazioneMassivaRequest;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.ChecklistHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.EsitoSalvaModuloCheckListHtmlDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.checklisthtml.FileDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.IstanzaAttivitaDTO;
import it.csi.pbandi.pbweb.pbandisrv.dto.validazionerendicontazione.ValidazioneRendicontazioneDTO;

public interface ValidazioneRendicontazioneDAO {
	/* String getSvilConstant() throws Exception; */
	InizializzaValidazioneDTO inizializzaValidazione(Long idDichiarazioneDiSpesa, Long idProgetto, Long idBandoLinea,
			Long idSoggetto, String codiceRuolo, Long idUtente, String idIride)
			throws InvalidParameterException, Exception;

	InitIntegrazioneDTO initIntegrazione(Long idDichiarazioneSpesa) throws Exception;
	InizializzaPopupChiudiValidazioneDTO inizializzaPopupChiudiValidazione(Long idDichiarazioneDiSpesa, Long idProgetto, Long idBandoLinea, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	ArrayList<RigaRicercaDocumentiDiSpesaDTO> cercaDocumentiDiSpesaValidazione(CercaDocumentiDiSpesaValidazioneRequest cercaDocumentiDiSpesaValidazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	DocumentoDiSpesaDematDTO dettaglioDocumentoDiSpesaPerValidazione (Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto, Long idBandoLinea, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	DocumentoDiSpesaDematOldDTO dettaglioDocumentoDiSpesaPerValidazioneOld (Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto, Long idBandoLinea, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	void setDataNotifica (Long idIntegrazioneSpesa, Date dataNotifica) throws Exception;
	void sospendiDocumento (Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto, String noteValidazione, Long idUtente, String idIride, Boolean fromAttivitaValidazione) throws InvalidParameterException, Exception;
	void respingiDocumento (Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto, String noteValidazione, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	void invalidaDocumento (Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto, String noteValidazione, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	void validaDocumento (Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto, String noteValidazione, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO validaParzialmenteDocumento (ValidaParzialmenteDocumentoRequest validaParzialmenteDocumentoRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoLeggiFile reportDettaglioDocumentoDiSpesa (Long idDichiarazioneDiSpesa, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoVerificaOperazioneMassivaDTO verificaOperazioneMassiva (VerificaOperazioneMassivaRequest verificaOperazioneMassivaRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO operazioneMassiva (OperazioneMassivaRequest operazioneMassivaRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO richiediIntegrazione (RichiediIntegrazioneRequest richiediIntegrazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO newRichiediIntegrazione (MultipartFormDataInput form) throws Exception;
	EsitoDTO chiudiRichiestaIntegrazione (Long idIntegrazione, HttpServletRequest req);
	EsitoDTO chiudiValidazione (Long idDichiarazioneDiSpesa, Long idDocumentoIndex, Long idBandoLinea, Boolean invioExtraProcedura, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	EsitoDTO proseguiChiudiValidazione (ProseguiChiudiValidazioneRequest proseguiChiudiValidazioneRequest, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Boolean salvaProtocollo (Long idDocumentoIndex, String protocollo, Long idUtente, String idIride) throws InvalidParameterException, Exception;
	Object getModuloCheckListValidazioneHtml(Long idUtente, String idIride, Long idProgetto, Long idDichiarazioneDiSpesa, String soggettoControllore);
	EsitoSalvaModuloCheckListHtmlDTO saveCheckListDocumentaleHtml(Long idUtente, String idIride, Long idProgetto, String stato, ChecklistHtmlDTO checkListHtmlDTO, Long idDichiarazioneDiSpesa)throws InvalidParameterException, Exception;
	
	EsitoValidazioneRendicontazionePiuGreen chiudiValidazioneChecklistHtml(Long idUtente, String idIride, IstanzaAttivitaDTO istanza, ValidazioneRendicontazioneDTO validazioneDTO) throws Exception;
	
	EsitoValidazioneRendicontazionePiuGreen chiudiValidazioneChecklistHtml(Long idUtente, String idIride, IstanzaAttivitaDTO istanza, ValidazioneRendicontazioneDTO validazioneDTO, Long idChecklist, FileDTO[] verbali) throws Exception;
		
	ArrayList<MensilitaDichiarazioneSpesaDTO> recuperaMensilitaDichiarazioneSpesa(Long idDichiarazioneSpesa,
			Long idUtente, String idIride) throws InvalidParameterException, Exception;
	
	EsitoDTO validaMensilita(
			List<ValidaMensilitaRequest> validaMensilitaRequest, Long idUtente,
			String idIride) throws InvalidParameterException, Exception;

	PbandiCCostantiVO getCostantiAllegati (Long idUtente, String idIride)throws Exception;
	
	String getNomeFile(Long idDocumentoIndex);

	initDropDownCL_DTO initDropDownCL(Long idProgetto) throws Exception;
	
	Integer updateCriteri(BigDecimal idAttribValidSpesa, BigDecimal idEsitoValidSpesa, BigDecimal idDichiarazioneSpesa);

	String salvaLetteraAccompagnatoria(File filePart, String nomeFile, Boolean visibilitaFile, BigDecimal idProgetto, int entita, Long idUtente, BigDecimal idTarget);
	
	String salvaChecklistInterna(File filePart, String nomeFile, Boolean visibilitaFile, BigDecimal idProgetto, Long idUtente, BigDecimal idTarget, Boolean integrazione) throws Exception;
	String salvaReportValidazione(File filePart, String nomeFile, Boolean visibilitaFile, BigDecimal idProgetto, Long idUtente, BigDecimal idTarget, Boolean integrazione) throws Exception;

	String salvaAllegatoIntegrazione(MultipartFormDataInput multipartFormData, Long idWhatever) throws Exception;

    String verificaInserimentoPropostaRevoca(Date dataPropostaRevoca, Long idProgetto);
    
    String creaPropostaRevoca(BigDecimal idProgetto, BigDecimal idDichiarazioneSpesa, Long idUtente, File filePart, String nomeFile, Boolean visibilitaFile, int entita);

	String verificaPropostaErogazione(BigDecimal idProgetto);
    String creaPropostaErogazione(List<Long> listIdModalitaAgevolazione, BigDecimal importoDaErogare, BigDecimal importoIres, BigDecimal idProgetto, Long idUtente, Long idDichiarazioneSpesa, Double premialita, BigDecimal idCausaleErogContributo, BigDecimal idCausaleErogFinanz, HttpServletRequest req) throws Exception;

	String creaPropostaRevocaEdErogazione(BigDecimal importoDaErogare, BigDecimal importoIres, BigDecimal idProgetto, Long idDichiarazioneSpesa, Long idUtente, Double premialita, BigDecimal idCausaleErogContributo, BigDecimal idCausaleErogFinanz);

	Long getIdChecklist(Long idDichiarazioneSpesa, Long idDocumentoIndex);

	Double getSumImportoErogProgettiPercettori(Long idUtente, String idIride, Long progrBandoLineaIntervento,
			Long idDichiarazioneDiSpesa) throws Exception;

	EsitoOperazioni chiudiValidazioneUfficio(Long idDichiarazioneSpesa, HttpServletRequest req) throws Exception;

	String inviaNotificaChiudiValidRagioneriaDelegata(Long idDichiarazioneDiSpesa, Long idProgetto, Long idUtente, String identitaDigitale)
			throws CSIException, SystemException, UnrecoverableException, CertificazioneException, Exception;

	List<String> leggiIndirizzoMailRuoloEnteProgetto(Long idProgetto, String descrBreveRuoloEnte) throws Exception;

	void annullaSospendiDocumentoSpesaValid(Long idDichiarazioneDiSpesa, Long idDocumentoDiSpesa, Long idProgetto, String noteValidazione, Long idUtente, String idIride, Boolean fromAttivitaValidazione) throws InvalidParameterException, Exception;

}
