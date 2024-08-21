/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.irregolarita;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.BusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.IrregolaritaManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ChecklistRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoScaricaDocumentoIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.IrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.RettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.irregolarita.IrregolaritaException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CecklistRettificaForfettariaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RettificaForfettariaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita.EsitoControlliProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita.IrregolaritaMaxVersioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita.IrregolaritaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita.IrregolaritaProvvisoriaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEsitoControlliVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIrregolaritaProvvVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIrregolaritaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRettForfettVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.irregolarita.IrregolaritaSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.doqui.index.ecmengine.dto.Node;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;



public class IrregolaritaBusinessImpl extends BusinessImpl implements IrregolaritaSrv {

	@Autowired
	private GenericDAO genericDAO;
	
 
	private IndexDAO indexDAO;
	
	private DocumentoManager documentoManager;
	
	private DecodificheManager decodificheManager;
	
	@Autowired
	private IrregolaritaManager irregolaritaManager;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setIndexDAO(IndexDAO indexDAO) {
		this.indexDAO = indexDAO;
	}

	public IndexDAO getIndexDAO() {
		return indexDAO;
	}

	public void setDocumentoManager(DocumentoManager documentoManager) {
		this.documentoManager = documentoManager;
	}

	public DocumentoManager getDocumentoManager() {
		return documentoManager;
	}

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}
	
	public void setIrregolaritaManager(IrregolaritaManager irregolaritaManager) {
		this.irregolaritaManager = irregolaritaManager;
	}

	public IrregolaritaManager getIrregolaritaManager() {
		return irregolaritaManager;
	}

	public static final String ERRORE_IRREGOLARITA_PROVV_LEGATA_A_REG = "L'irregolarità provvisoria è legata ad un esito regolare. Non è possibile procedere con l'eliminazione.";

	/**
	 * Trova le irregolarit&agrave; provvisorie e definitive secondo i criteri di ricerca specificati nel filtro.
	 * 
	 * @param idUtente Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param filtro Criteri per la ricerca delle irregolarit&agrave;.
	 * @return Elenco delle irregolarit&agrave; trovate.
	 * @throws CSIException  Eccezione di sistema. 
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException Eccezione applicativa lanciata programmaticamente.
	 */
	public IrregolaritaDTO[] findIrregolarita(Long idUtente, String identitaDigitale, IrregolaritaDTO filtro) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		logger.begin();
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
			
			return getIrregolaritaManager().findIrregolarita(idUtente, identitaDigitale, filtro);
		} finally {
			logger.end();
		}
	}
	
	public IrregolaritaDTO[] findEsitiRegolari(Long idUtente, String identitaDigitale, IrregolaritaDTO filtro) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
			
			return getIrregolaritaManager().findEsitiRegolari(idUtente, identitaDigitale, filtro);
		} finally {			 
		}
	}


	/**
	 * Trova i dati dell'irregolarit&agrave; provvisoria specificata.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idIrregolarita  Identificativo dell'irregolarit&agrave; da trovare.
	 * @return Dati dell'irregolarit&agrave; selezionata.
	 * @throws CSIException  Eccezione di sistema. 
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public IrregolaritaDTO findDettaglioIrregolaritaProvvisoria(Long idUtente, String identitaDigitale, Long idIrregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idIrregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);

		IrregolaritaProvvisoriaProgettoVO irregolaritaProvv = new IrregolaritaProvvisoriaProgettoVO();
		irregolaritaProvv.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		irregolaritaProvv = genericDAO.findSingleWhere(irregolaritaProvv);	

		IrregolaritaDTO result = new IrregolaritaDTO();
		//setto questo campo poich� altrimenti nella tabella del front-end non viene reperito l'idRiga
		result.setIdIrregolarita(idIrregolarita);

		result.setIdIrregolaritaProvv(idIrregolarita);
		result.setDtComunicazioneProvv(beanUtil.transform(irregolaritaProvv.getDtComunicazione(), Date.class));
		result.setDtFineProvvisoriaProvv(irregolaritaProvv.getDtFineProvvisoria());
		result.setIdMotivoRevocaProvv(irregolaritaProvv.getIdMotivoRevoca().longValue());
		result.setDescMotivoRevocaProvv(irregolaritaProvv.getDescMotivoRevoca());
		result.setImportoIrregolaritaProvv(NumberUtil.toDouble(irregolaritaProvv.getImportoIrregolarita()));
		result.setImportoAgevolazioneIrregProvv(NumberUtil.toDouble(irregolaritaProvv.getImportoAgevolazioneIrreg()));
		result.setImportoIrregolareCertificatoProvv(NumberUtil.toDouble(irregolaritaProvv.getImportoIrregolareCertificato()));
		result.setDtFineValiditaProvv(beanUtil.transform(irregolaritaProvv.getDtFineValidita(), Date.class));
		result.setIdProgettoProvv(irregolaritaProvv.getIdProgetto().longValue());
		result.setCodiceVisualizzato(irregolaritaProvv.getCodiceVisualizzato());
		result.setDenominazioneBeneficiario(irregolaritaProvv.getDenominazioneBeneficiario());
		result.setTipoControlliProvv(irregolaritaProvv.getTipoControlli());
		result.setDataInizioControlliProvv(beanUtil.transform(irregolaritaProvv.getDataInizioControlli(), Date.class));
		result.setDataFineControlliProvv(beanUtil.transform(irregolaritaProvv.getDataFineControlli(), Date.class));
		result.setFlagIrregolaritaAnnullataProvv(irregolaritaProvv.getIrregolaritaAnnullata());
		
		if (irregolaritaProvv.getIdPeriodo() != null)
			result.setIdPeriodoProvv(irregolaritaProvv.getIdPeriodo().longValue());
		if (irregolaritaProvv.getIdCategAnagrafica() != null)
			result.setIdCategAnagraficaProvv(irregolaritaProvv.getIdCategAnagrafica().longValue());
		result.setDescPeriodoVisualizzataProvv(irregolaritaProvv.getDescPeriodoVisualizzata());
		result.setDescCategAnagraficaProvv(irregolaritaProvv.getDescCategAnagrafica());
		result.setNoteProvv(irregolaritaProvv.getNote());
		
		logger.debug("dataCampione="+irregolaritaProvv.getDataCampione());
		result.setDataCampione(beanUtil.transform(irregolaritaProvv.getDataCampione(), Date.class));

		return result;
	}
	
	/**
	 * Trova i dati dell'irregolarit&agrave; definitiva specificata.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idIrregolarita  Identificativo dell'irregolarit&agrave; da trovare.
	 * @return Dati dell'irregolarit&agrave; selezionata.
	 * @throws CSIException  Eccezione di sistema. 
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public IrregolaritaDTO findDettaglioIrregolarita(Long idUtente, String identitaDigitale, Long idIrregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idIrregolarita" };
			ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);

			IrregolaritaProgettoVO filtroVO = new IrregolaritaProgettoVO();
			filtroVO.setIdIrregolarita(new BigDecimal(idIrregolarita));

			filtroVO = genericDAO.findSingleWhere(filtroVO);


			IrregolaritaDTO result = beanUtil.transform(filtroVO, IrregolaritaDTO.class);

			/*
			 * Ricerco la scheda OLAF associata all' irregolarita'
			 */
			//Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA;
			Long idProgetto = NumberUtil.toLong(filtroVO.getIdProgetto());
			Long idTarget = NumberUtil.toLong(filtroVO.getIdIrregolarita());
			
			/* 03/12/2018: getDocumentoIndexSuDb() usa una select che va su PBANDI_R_DOCU_INDEX_TIPO_STATO,
			 * che per� nel caso di irregolarit� non viene popolata e quindi la select non trova nulla.
			 * Stessa cosa sotto per i 'dati aggiuntivi'.
			 */
			//PbandiTDocumentoIndexVO schedaOlafVO = documentoManager.getDocumentoIndexSuDb(idTarget, idProgetto, Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA, "PBANDI_T_IRREGOLARITA");
			PbandiTDocumentoIndexVO schedaOlafVO = new PbandiTDocumentoIndexVO();
			schedaOlafVO.setIdProgetto(new BigDecimal(idProgetto));
			schedaOlafVO.setIdEntita(new BigDecimal(247));
			schedaOlafVO.setIdTarget(new BigDecimal(idTarget));
			schedaOlafVO.setIdTipoDocumentoIndex(new BigDecimal(11));
			schedaOlafVO = genericDAO.findSingleOrNoneWhere(schedaOlafVO);
			
			if (schedaOlafVO != null) {
				DocumentoIrregolaritaDTO documento = beanUtil.transform(schedaOlafVO, DocumentoIrregolaritaDTO.class);
				result.setSchedaOLAF(documento);
			}
			
			/*
			 * Ricerco il file con i dati aggiuntivi
			 */
			//PbandiTDocumentoIndexVO datiAggiuntiviVO = documentoManager.getDocumentoIndexSuDb(idTarget, idProgetto, Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA, "PBANDI_T_IRREGOLARITA");
			PbandiTDocumentoIndexVO datiAggiuntiviVO = new PbandiTDocumentoIndexVO();
			datiAggiuntiviVO.setIdProgetto(new BigDecimal(idProgetto));
			datiAggiuntiviVO.setIdEntita(new BigDecimal(247));
			datiAggiuntiviVO.setIdTarget(new BigDecimal(idTarget));
			datiAggiuntiviVO.setIdTipoDocumentoIndex(new BigDecimal(12));
			datiAggiuntiviVO = genericDAO.findSingleOrNoneWhere(datiAggiuntiviVO);
			
			if (datiAggiuntiviVO != null) {
				DocumentoIrregolaritaDTO documento = beanUtil.transform(datiAggiuntiviVO, DocumentoIrregolaritaDTO.class);
				result.setDatiAggiuntivi(documento);
			}
			
			result.setDescDisimpegnoAssociato(irregolaritaManager.findDescDisimpegno(idIrregolarita));

			return result;

		} finally {
	 
		}

	}

	
	/**
	 * Crea una nuova irregolarit&agrave; provvisoria.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idIrregolarita  Identificativo dell'irregolarit&agrave; da trovare.
	 * @return Esito dell'operazione. In caso di successo contiene l'identificativo dell'irregolarit&agrave; creata;
	 *         in caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO creaIrregolaritaProvvisoria(Long idUtente, String identitaDigitale, IrregolaritaDTO irregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, irregolarita);

		PbandiTIrregolaritaProvvVO irregolaritaProvvVO = new PbandiTIrregolaritaProvvVO();
		irregolaritaProvvVO.setDtComunicazione(DateUtil.utilToSqlDate(irregolarita.getDtComunicazioneProvv()));
		irregolaritaProvvVO.setIdProgetto(new BigDecimal(irregolarita.getIdProgettoProvv()));
		irregolaritaProvvVO.setImportoIrregolarita(new BigDecimal(irregolarita.getImportoIrregolaritaProvv()));
		if (irregolarita.getImportoAgevolazioneIrregProvv() != null) irregolaritaProvvVO.setImportoAgevolazioneIrreg(new BigDecimal(irregolarita.getImportoAgevolazioneIrregProvv()));
		if (irregolarita.getImportoIrregolareCertificatoProvv() != null) irregolaritaProvvVO.setImportoIrregolareCertificato(new BigDecimal(irregolarita.getImportoIrregolareCertificatoProvv()));
		if (irregolarita.getIdMotivoRevocaProvv() != null) irregolaritaProvvVO.setIdMotivoRevoca(new BigDecimal(irregolarita.getIdMotivoRevocaProvv()));
		irregolaritaProvvVO.setTipoControlli(irregolarita.getTipoControlliProvv());
		irregolaritaProvvVO.setDataInizioControlli(DateUtil.utilToSqlDate(irregolarita.getDataInizioControlliProvv()));
		if (irregolarita.getDataFineControlliProvv() != null) irregolaritaProvvVO.setDataFineControlli(DateUtil.utilToSqlDate(irregolarita.getDataFineControlliProvv()));
		if (irregolarita.getFlagIrregolaritaAnnullataProvv() != null) irregolaritaProvvVO.setIrregolaritaAnnullata(irregolarita.getFlagIrregolaritaAnnullataProvv());
		irregolaritaProvvVO.setIdCategAnagrafica(NumberUtil.toBigDecimal(irregolarita.getIdCategAnagraficaProvv()));
		irregolaritaProvvVO.setIdPeriodo(NumberUtil.toBigDecimal(irregolarita.getIdPeriodoProvv()));
		irregolaritaProvvVO.setNote(irregolarita.getNoteProvv());
		
		logger.debug("dataCampione="+irregolarita.getDataCampione());
		irregolaritaProvvVO.setDataCampione(DateUtil.utilToSqlDate(irregolarita.getDataCampione()));
		
		try {
			irregolaritaProvvVO = genericDAO.insert(irregolaritaProvvVO);
		} catch (Exception e) {
			logger.error("Errore in inserimento su PBANDI_T_IRREGOLARITA_PROVV", e);
			throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
		}		
		
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		esito.setIdIrregolarita(NumberUtil.toLong(irregolaritaProvvVO.getIdIrregolaritaProvv()));
		return esito;
	}
	
	
	/**
	 * Crea una nuova irregolarit&agrave; definitiva.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idIrregolarita  Identificativo dell'irregolarit&agrave; da trovare.
	 * @return Esito dell'operazione. In caso di successo contiene l'identificativo dell'irregolarit&agrave; creata;
	 *         in caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO creaIrregolarita(Long idUtente, String identitaDigitale, IrregolaritaDTO irregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();

		// 1 - validazioni necessarie
		String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, irregolarita);

		// 2 - creo l'irregolarita'
		esito = getIrregolaritaManager().creaIrregolarita(idUtente, identitaDigitale, irregolarita);
		
		return esito;
	}

	
	/**
	 * Modifica un'irregolarit&agrave; provvisoria esistente.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param irregolarita  Dati dell'irregolarit&agrave; da modificare.
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO modificaIrregolaritaProvvisoria(Long idUtente, String identitaDigitale, IrregolaritaDTO irregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, irregolarita);

		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		esito = getIrregolaritaManager().modificaIrregolaritaProvvisoria(idUtente, identitaDigitale, irregolarita);
		
		return esito;
	}
	
	
	/**
	 * Modifica un'irregolarit&agrave; definitiva esistente.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param irregolarita  Dati dell'irregolarit&agrave; da modificare.
	 * @param modificaDatiAggiuntivi true se l'allegato dati aggiuntivi &egrave; da modificare, false se &egrave; da inserire.
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO modificaIrregolarita(Long idUtente, String identitaDigitale, IrregolaritaDTO irregolarita, boolean modificaDatiAggiuntivi) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();

		// 1 - validazioni necessarie
		String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, irregolarita);

		// 2 - controlli di business (da CdU)
		if (Constants.FLAG_TRUE.equals(irregolarita.getFlagBlocco())) {
			esito.setEsito(Boolean.FALSE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(ERRORE_IRREGOLARITA_BLOCCATA_MOD);
			esito.setMsgs(new MessaggioDTO[]{msg});
			logger.end();
			return esito;			
		}

		if (!StringUtil.isEmpty(irregolarita.getNumeroIms()) && irregolarita.getDtIms() != null) {
			// irregolarita' inviata a IMS: e' l'ultima versione?
			if (!isLastVersion(idUtente, identitaDigitale, irregolarita)) {
				// non e' l'ultima versione: non posso modificare
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ERRORE_IRREGOLARITA_VERSIONE_MOD);
				esito.setMsgs(new MessaggioDTO[]{msg});
				return esito;
			} else {
				// e' l'ultima versione: eseguo AGGIONAMENTO ('clone')
				esito = getIrregolaritaManager().aggiornaIrregolarita(idUtente, identitaDigitale, irregolarita);
			}
		} else {
			// irregolarita' non ancora inviata a IMS: eseguo MODIFICA
			esito = getIrregolaritaManager().modificaIrregolarita(idUtente, identitaDigitale, irregolarita, modificaDatiAggiuntivi);
		}
		
		return esito;
	}

	
	/**
	 * Blocca/sblocca una irregolarit&agrave;, invertendo il valore del campo <code>flagBlocco</code>.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idIrregolarita  Identificativo dell'irregolarit&agrave; da modificare.
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO bloccaSbloccaIrregolarita(Long idUtente,
			String identitaDigitale, Long idIrregolarita) throws CSIException,
			SystemException, UnrecoverableException, IrregolaritaException {
		
		String[] nameParameter = { "idUtente", "identitaDigitale", "idIrregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);

		IrregolaritaProgettoVO filtroVO = new IrregolaritaProgettoVO();
		filtroVO.setIdIrregolarita(new BigDecimal(idIrregolarita));
		filtroVO = genericDAO.findSingleWhere(filtroVO);
		
		PbandiTIrregolaritaVO updVO = new PbandiTIrregolaritaVO();
		updVO.setIdIrregolarita(filtroVO.getIdIrregolarita());
		updVO.setIdUtenteAgg(new BigDecimal(idUtente));
		if(Constants.FLAG_FALSE.equals(filtroVO.getFlagBlocco())) {
			// se l'irregolarita' e' sbloccata, la blocco
			updVO.setFlagBlocco(Constants.FLAG_TRUE);
		} else if (Constants.FLAG_TRUE.equals(filtroVO.getFlagBlocco())) {
			// se l'irregolarita' e' bloccata, la sblocco
			updVO.setFlagBlocco(Constants.FLAG_FALSE);
		}
		
		try {
			genericDAO.update(updVO);
		} catch (Exception e) {
			logger.error("Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA", e);
			throw new IrregolaritaException(ERRORE_SERVER, e);
		}		
		
		// esito finale
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		return esito;
	}


	/**
	 * Modifica una nuova irregolarit&agrave; inserendo le informazioni relative a IMS.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param irregolarita  Dati dell'irregolarit&agrave; da modificare.
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO registraInvioIrregolarita(Long idUtente,
			String identitaDigitale, IrregolaritaDTO irregolarita)
			throws CSIException, SystemException, UnrecoverableException,
			IrregolaritaException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, irregolarita);
	
		PbandiTIrregolaritaVO updVO = new PbandiTIrregolaritaVO();
		updVO.setIdIrregolarita(new BigDecimal(irregolarita.getIdIrregolarita()));
		updVO.setIdUtenteAgg(new BigDecimal(idUtente));
		updVO.setNumeroIms(irregolarita.getNumeroIms());
		updVO.setDtIms(DateUtil.utilToSqlDate(irregolarita.getDtIms()));
		updVO.setFlagBlocco(Constants.FLAG_FALSE); // [DM] STDMDD-
		try {
			genericDAO.update(updVO);
		} catch (Exception e) {
			logger.error("Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA", e);
			throw new IrregolaritaException(ERRORE_SERVER, e);
		}		
		
		// esito finale
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		return esito;
	}

	/**
	 * Elimina logicamente una nuova irregolarit&agrave; provvisoria (se non � legata a una definitiva) impostando una data di fine validit&agrave;.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idIrregolarita  Identificativo dell'irregolarit&agrave; da cancellare (logicamente).
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO eliminaIrregolaritaProvvisoria(Long idUtente, String identitaDigitale, Long idIrregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idIrregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);
	
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		
		// una irregolarit� provvisoria legata ad una definitiva (in corso di validit�) non pu� essere cancellata 
		PbandiTIrregolaritaVO filtroIrrDef = new PbandiTIrregolaritaVO();
		filtroIrrDef.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		try {
			filtroIrrDef = genericDAO.findSingleOrNoneWhere(filtroIrrDef);
			if (filtroIrrDef != null && filtroIrrDef.getDtFineValidita() == null) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ERRORE_IRREGOLARITA_PROVV_LEGATA_A_DEF); //E005
				esito.setMsgs(new MessaggioDTO[]{msg});
				return esito;
			}
		} catch(Exception ex) {
			logger.error("Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA_PROVV", ex);
			throw new IrregolaritaException(ERRORE_SERVER, ex);
		}
		
		// una irregolarit� provvisoria legata ad un esito definitivo non pu� essere cancellata 
		PbandiTIrregolaritaProvvVO filtroIrrProvv = new PbandiTIrregolaritaProvvVO();
		filtroIrrProvv.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		try {
			filtroIrrProvv = genericDAO.findSingleOrNoneWhere(filtroIrrProvv);
			if (filtroIrrProvv != null && filtroIrrProvv.getIdEsitoControllo() != null) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ERRORE_IRREGOLARITA_PROVV_LEGATA_A_REG);
				esito.setMsgs(new MessaggioDTO[]{msg});
				return esito;
			}
		} catch(Exception ex) {
			logger.error("Errore nel carcare la provvisoria: ", ex);
			throw new IrregolaritaException(ERRORE_SERVER, ex);
		}
		
		// cancellazione logica: setto la data di fine validita'
		PbandiTIrregolaritaProvvVO updVO = new PbandiTIrregolaritaProvvVO();
		updVO.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		updVO.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
				
		try {
			genericDAO.update(updVO);
		} catch (Exception e) {
			logger.error("Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA_PROVV", e);
			throw new IrregolaritaException(ERRORE_SERVER, e);
		}		
		
		// esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		return esito;
	}  
	
	/**
	 * Elimina logicamente una nuova irregolarit&agrave; impostando una data di fine validit&agrave;.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idIrregolarita  Identificativo dell'irregolarit&agrave; da cancellare (logicamente).
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di errore.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO eliminaIrregolarita(Long idUtente, String identitaDigitale, Long idIrregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idIrregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);
	
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		
		// controlli di business (da CdU)
		IrregolaritaProgettoVO filtroVO = new IrregolaritaProgettoVO();
		filtroVO.setIdIrregolarita(new BigDecimal(idIrregolarita));
		filtroVO = genericDAO.findSingleWhere(filtroVO);
		
		// una irregolarita' bloccata NON pu� essere cancellata
		if (Constants.FLAG_TRUE.equals(filtroVO.getFlagBlocco())) {
			esito.setEsito(Boolean.FALSE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(ERRORE_IRREGOLARITA_BLOCCATA_CANC);
			esito.setMsgs(new MessaggioDTO[]{msg});
			return esito;			
		}
		
		// una irregolarita' inviata NON pu� essere cancellata
		if (!StringUtil.isEmpty(filtroVO.getNumeroIms()) && filtroVO.getDtIms() != null) {
			esito.setEsito(Boolean.FALSE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(ERRORE_IRREGOLARITA_INVIATA_CANC);
			esito.setMsgs(new MessaggioDTO[]{msg});
			return esito;			
		}		


		// cancellazione logica: setto la data di fine validita'
		PbandiTIrregolaritaVO updVO = new PbandiTIrregolaritaVO();
		updVO.setIdIrregolarita(new BigDecimal(idIrregolarita));
		updVO.setIdUtenteAgg(new BigDecimal(idUtente));
		updVO.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
		
		try {
			genericDAO.update(updVO);
		} catch (Exception e) {
			logger.error("Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA", e);
			throw new IrregolaritaException(ERRORE_SERVER, e);
		}
		
		
		/* 0) recupera la chiave della irreg provv per la cancellazione logica di quest'ultima */
		PbandiTIrregolaritaVO filtroIrrDef = new PbandiTIrregolaritaVO();
		filtroIrrDef.setIdIrregolarita(new BigDecimal(idIrregolarita));
		filtroIrrDef = genericDAO.findSingleWhere(filtroIrrDef);
		
		try {
			if (filtroIrrDef.getIdIrregolaritaProvv() != null) eliminaIrregolaritaProvvisoria(idUtente, identitaDigitale, NumberUtil.toLong(filtroIrrDef.getIdIrregolaritaProvv()));
		} catch (Exception e) {
			logger.error("Errore in richiamo eliminaIrregolaritaProvvisoria() da eliminaIrregolarita()", e);
			throw new IrregolaritaException(ERRORE_SERVER, e);
		}	
		
		
		/* 1) find documentoindexvo olaf*/
		Long idProgetto = NumberUtil.toLong(updVO.getIdProgetto());
		Long idTarget = NumberUtil.toLong(updVO.getIdIrregolarita());
		PbandiTDocumentoIndexVO schedaOlafVO = documentoManager.getDocumentoIndexSuDb(idTarget, idProgetto, Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA, "PBANDI_T_IRREGOLARITA");
		
		
		/* 3) elimina su index*/
		if(schedaOlafVO!=null){
			String uuidNodoSchedaOlaf = schedaOlafVO.getUuidNodo();
			try {
				genericDAO.delete(schedaOlafVO) ;
				 indexDAO.cancellaNodo(new Node(uuidNodoSchedaOlaf));
				
			} catch (Exception e) {
				logger.error("Errore nella cancellazione della scheda olaf con uid nodo "+uuidNodoSchedaOlaf+" su index ", e);
			}
		}
		
		/* 4) find documentoindexvo olaf*/
		/*
		 * Ricerco il file con i dati aggiuntivi
		 */
		PbandiTDocumentoIndexVO datiAggiuntiviVO = documentoManager.getDocumentoIndexSuDb(idTarget, idProgetto, Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA, "PBANDI_T_IRREGOLARITA");
		
		/* 5) elimina su index	*/
		if(datiAggiuntiviVO!=null){
			String uuidNodoDatiAggiuntivi = datiAggiuntiviVO.getUuidNodo();
			try {
				genericDAO.delete(datiAggiuntiviVO) ;
				 indexDAO.cancellaNodo(new Node(uuidNodoDatiAggiuntivi));
				
			} catch (Exception e) {
				logger.error("Errore nella cancellazione dei dati aggiuntivi con uid nodo "+uuidNodoDatiAggiuntivi+" su index ", e);
			}
			
		}
		
		
		// esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		return esito;
	}


	/**
	 * Scarica da INDEX un documento relativo ad una irregolarit&agrave;.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param idDocumentoIndex  Identificativo di INDEX del documento richiesto.
	 * @return Esito dell'operazione. In caso successo contiene il nome e i bytes del documento richiesto.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	public EsitoScaricaDocumentoIrregolaritaDTO scaricaDocumentoIrregolarita(
			Long idUtente, String identitaDigitale, Long idDocumentoIndex)
			throws CSIException, SystemException, UnrecoverableException,
			IrregolaritaException {

		String[] nameParameter = { "idUtente", "identitaDigitale", "idDocumentoIndex" };

		EsitoScaricaDocumentoIrregolaritaDTO result = new EsitoScaricaDocumentoIrregolaritaDTO();
		result.setEsito(Boolean.FALSE);

		try {
			ValidatorInput.verifyNullValue(nameParameter, idUtente, identitaDigitale, idDocumentoIndex);

			// scarico il documento da INDEX
			result = getIrregolaritaManager().scaricaDocumentoIrregolarita(idUtente, identitaDigitale, idDocumentoIndex);
			result.setEsito(Boolean.TRUE);

			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			IrregolaritaException ie = new IrregolaritaException("Documento non trovato su servizio INDEX");
			throw ie;
		} 
	}



	//////////////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS


	/**
	 * Verifica se l'irregolarit&agrave; che si sta cercando di modificare &egrave; 
	 * l'ultima versione oppure no.
	 * 
	 * @param idUtente  Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale  Identit&agrave; digitale (di IRIDE) dell'utente che esegue l'operazione.
	 * @param irregolarita  Dati dell'irregolarit&agrave; da modificare.
	 * @return true se la versione dell'irregolarit&agrave; da modificare coincide con la massima versione delle irregolarit&agrave;
	 *         collegate alla stessa irregolarit&agrave; padre, false altrimenti.
	 * @throws CSIException  Eccezione di sistema.
	 * @throws SystemException  Eccezione di sistema.
	 * @throws UnrecoverableException  Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata programmaticamente.
	 */
	private boolean isLastVersion(Long idUtente, String identitaDigitale, IrregolaritaDTO irregolarita) throws IrregolaritaException, SystemException, UnrecoverableException, CSIException {
		Long idIrregolaritaPadre = irregolarita.getIdIrregolaritaCollegata() != null ? irregolarita.getIdIrregolaritaCollegata() : irregolarita.getIdIrregolarita();
		IrregolaritaMaxVersioneVO vo = new IrregolaritaMaxVersioneVO();
		vo.setIdIrregolaritaPadre(new BigDecimal(idIrregolaritaPadre));
		vo = genericDAO.findSingleWhere(vo);
		if (irregolarita.getNumeroVersione().equals(NumberUtil.toLong(vo.getMaxVersione()))) {
			return true;
		}
		return false;
	}
	
	public EsitoSalvaIrregolaritaDTO creaEsitoRegolare(Long idUtente, String identitaDigitale, IrregolaritaDTO irregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, irregolarita);
		esito = getIrregolaritaManager().creaEsitoRegolare(idUtente, identitaDigitale, irregolarita);		
		return esito;
	}
	
	public EsitoSalvaIrregolaritaDTO eliminaEsitoRegolare(Long idUtente, String identitaDigitale, Long idEsitoregolare) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idEsitoregolare" };
		ValidatorInput.verifyNullValue(nameParameter, idEsitoregolare);
	
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		
		// Cerco l'esito.
		PbandiTEsitoControlliVO vo = new PbandiTEsitoControlliVO();
		vo.setIdEsitoControllo(new BigDecimal(idEsitoregolare));
		vo = genericDAO.findSingleWhere(vo);

		// cancellazione fisica.
		try {
			// Verifico se l'esito regolare deriva da un esito irregolare provvisorio.
			PbandiTIrregolaritaProvvVO irreg = new PbandiTIrregolaritaProvvVO();
			irreg.setIdEsitoControllo(vo.getIdEsitoControllo());
			irreg = genericDAO.findSingleOrNoneWhere(irreg);
			if (irreg != null && irreg.getIdIrregolaritaProvv() != null) {
				// Tolgo il riferimento all'id dell'esito regolare per evitare un urrore di integrit� alla delete sotto. 
				// e cancello logicamente l'esito provvisorio.
				irreg.setIdEsitoControllo(null);
				irreg.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
				genericDAO.updateNullables(irreg);
			}
			
			genericDAO.delete(vo);
		} catch (Exception e) {
			logger.error("Errore delete su PBANDI_T_ESITO_CONTROLLI con idEsitoControllo = "+idEsitoregolare, e);
			throw new IrregolaritaException(ERRORE_SERVER, e);
		}
		
		// esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		return esito;
	}

	public EsitoSalvaIrregolaritaDTO modificaEsitoRegolare(Long idUtente, String identitaDigitale, IrregolaritaDTO irregolarita) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, irregolarita);

		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		esito = getIrregolaritaManager().modificaEsitoRegolare(idUtente, identitaDigitale, irregolarita);
		
		return esito;
	}
	
	public IrregolaritaDTO findDettaglioEsitoRegolare(Long idUtente, String identitaDigitale, Long idEsitoRegolare) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idEsitoRegolare" };
			ValidatorInput.verifyNullValue(nameParameter, idEsitoRegolare);

			EsitoControlliProgettoVO filtroVO = new EsitoControlliProgettoVO();
			filtroVO.setIdEsitoControllo(new BigDecimal(idEsitoRegolare));

			filtroVO = genericDAO.findSingleWhere(filtroVO);

			IrregolaritaDTO result = beanUtil.transform(filtroVO, IrregolaritaDTO.class);
			result.setIdIrregolarita(result.getIdEsitoControllo());
			
			return result;
		} finally {	 
		}
	}
	
	public RettificaForfettariaDTO[] findRettificheForfettarie(Long idUtente, String identitaDigitale, IrregolaritaDTO filtro) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
		ValidatorInput.verifyNullValue(nameParameter, filtro);
		ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
		
		RettificaForfettariaVO vo = new RettificaForfettariaVO();			
		if (filtro.getIdSoggettoBeneficiario() != null)
			vo.setIdSoggettoBeneficiario(new BigDecimal(filtro.getIdSoggettoBeneficiario()));
		if (filtro.getIdProgetto() != null)
			vo.setIdProgetto(new BigDecimal(filtro.getIdProgetto()));
		vo.setDataInserimento(DateUtil.utilToSqlDate(filtro.getDtComunicazione()));
		
		List<RettificaForfettariaVO> elenco = genericDAO.findListWhere(vo);
		
		return beanUtil.transform(elenco, RettificaForfettariaDTO.class);
	}
	
	public ChecklistRettificaForfettariaDTO[] findChecklistRettificheForfettarie(Long idUtente, String identitaDigitale, Long idProgetto) throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idProgetto);
		
		CecklistRettificaForfettariaVO vo = new CecklistRettificaForfettariaVO();
		vo.setIdProgetto(new BigDecimal(idProgetto));			
		List<CecklistRettificaForfettariaVO> elenco = genericDAO.findListWhere(vo);
		
		return beanUtil.transform(elenco, ChecklistRettificaForfettariaDTO.class);
	}
	
	public EsitoSalvaRettificaForfettariaDTO salvaRettificaForfettaria(Long idUtente, String identitaDigitale, RettificaForfettariaDTO rettificaForfettaria) 
	throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "rettificaForfettaria" };
		ValidatorInput.verifyNullValue(nameParameter, rettificaForfettaria);
		
		PbandiTRettForfettVO vo = new PbandiTRettForfettVO();		
		vo.setIdAppalto(NumberUtil.toBigDecimal(rettificaForfettaria.getIdAppalto()));
		vo.setIdAppaltoChecklist(NumberUtil.toBigDecimal(rettificaForfettaria.getIdAppaltoChecklist()));
		vo.setIdCategAnagrafica(NumberUtil.toBigDecimal(rettificaForfettaria.getIdCategAnagrafica()));
		vo.setIdPropostaCertificaz(NumberUtil.toBigDecimal(rettificaForfettaria.getIdPropostaCertificaz()));
		vo.setPercRett(rettificaForfettaria.getPercRett());
		// java.sql.date  ->  java.util.Date
		vo.setDataInserimento(DateUtil.utilToSqlDate(rettificaForfettaria.getDataInserimento()));
		
		try {
			vo = genericDAO.insert(vo);
		} catch (Exception e) {
			logger.error("salvaRettificaForfettaria(): ERRORE: "+e);
			throw new IrregolaritaException(ERRORE_SERVER, e);
		}
		
		EsitoSalvaRettificaForfettariaDTO esito = new EsitoSalvaRettificaForfettariaDTO();
		esito.setEsito(true);
		esito.setIdRettificaForfettaria(vo.getIdRettificaForfett().longValue());
		
		return esito;
	}
	
	public EsitoSalvaRettificaForfettariaDTO eliminaRettificaForfettaria(Long idUtente, String identitaDigitale, Long idRettificaForfett) 
	throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idRettificaForfett" };
		ValidatorInput.verifyNullValue(nameParameter, idRettificaForfett);
		
		PbandiTRettForfettVO vo = new PbandiTRettForfettVO();
		vo.setIdRettificaForfett(NumberUtil.toBigDecimal(idRettificaForfett));
		
		try {
			genericDAO.delete(vo);
		} catch (Exception e) {
			logger.error("eliminaRettificaForfettaria(): ERRORE: "+e);
			throw new IrregolaritaException(ERRORE_SERVER, e);
		}
		
		EsitoSalvaRettificaForfettariaDTO esito = new EsitoSalvaRettificaForfettariaDTO();
		esito.setEsito(true);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		
		return esito;
	}
	
	public ProgettoDTO findProgetto(Long idUtente, String identitaDigitale, Long idProgetto) 
	throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idProgetto);
		
		PbandiTProgettoVO vo = new PbandiTProgettoVO();
		vo.setIdProgetto(new BigDecimal(idProgetto));
		vo = genericDAO.findSingleOrNoneWhere(vo);

		ProgettoDTO dto = new ProgettoDTO();		
		if (vo != null) {
			if (vo.getIdProgetto() != null)
				dto.setIdProgetto(vo.getIdProgetto().longValue());
			if (vo.getIdTipoOperazione() != null)
			dto.setIdTipoOperazione(vo.getIdTipoOperazione().longValue());
		}
		
		return dto;
	}
}
