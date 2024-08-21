/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.business.manager.DocumentiFSManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoScaricaDocumentoIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.IrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.irregolarita.IrregolaritaException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita.EsitoControlliProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita.IrregolaritaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.irregolarita.IrregolaritaProvvisoriaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDDispComunitariaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDMetodoIndividuazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDMotivoRevocaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDNaturaSanzioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDQualificazioneIrregVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoAmministrativoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoFinanziarioVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDTipoIrregolaritaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRRevocaIrregVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTEsitoControlliVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIrregolaritaProvvVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTIrregolaritaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRevocaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.ErrorConstants;
import it.csi.pbandi.pbservizit.pbandiutil.common.messages.MessaggiConstants;
import it.doqui.index.ecmengine.dto.Node;

/**
 * Questa classe contiene i metodi utilizzati da pi&ugrave; funzionalit&agrave;
 * relative alla gestine delle Irregolarit&agrave;.
 * 
 */
public class IrregolaritaManager {
	@Autowired
	private LoggerUtil logger;
	@Autowired
	private BeanUtil beanUtil;
	@Autowired
	private GenericDAO genericDAO;
	@Autowired
	private DocumentiFSManager documentiFSManager;

	@Deprecated
	private IndexDAO indexDAO;

	@Autowired
	private DecodificheManager decodificheManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setLogger(LoggerUtil logger) {
		this.logger = logger;
	}

	public LoggerUtil getLogger() {
		return logger;
	}

	public void setBeanUtil(BeanUtil beanUtil) {
		this.beanUtil = beanUtil;
	}

	public BeanUtil getBeanUtil() {
		return beanUtil;
	}

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

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public DocumentiFSManager getDocumentiFSManager() {
		return documentiFSManager;
	}

	public void setDocumentiFSManager(DocumentiFSManager documentiFSManager) {
		this.documentiFSManager = documentiFSManager;
	}

	/**
	 * Trova le irregolarit&agrave; provvisorie e definitive secondo i criteri di
	 * ricerca specificati nel filtro.
	 * 
	 * @param idUtente         Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale Identit&agrave; digitale (di IRIDE) dell'utente che
	 *                         esegue l'operazione.
	 * @param filtro           Criteri per la ricerca delle irregolarit&agrave;.
	 * @return Elenco delle irregolarit&agrave; trovate.
	 */
	public IrregolaritaDTO[] findIrregolarita(Long idUtente, String identitaDigitale, IrregolaritaDTO filtro) {
		logger.begin();
		try {
			IrregolaritaProgettoVO filtroVO = beanUtil.transform(filtro, IrregolaritaProgettoVO.class);
			logger.debug("FILTRO=" + filtroVO);

			List<IrregolaritaProgettoVO> irregolaritaDefList = genericDAO.findListWhere(filtroVO);

			if (irregolaritaDefList != null) {
				logger.debug(" trovata irregolaritaDefList size = " + irregolaritaDefList.size());
			} else {
				logger.debug(" trovata irregolaritaDefList NULLA");
			}
			if (filtroVO.getDescBreveDispComunitaria() == null && filtroVO.getDescQualificazioneIrreg() == null
					&& filtroVO.getDescBreveTipoIrregolarita() == null && filtroVO.getDescBreveMetodoInd() == null
					&& filtroVO.getDescStatoAmministrativo() == null && filtroVO.getDescBreveStatoFinanziario() == null
					&& filtroVO.getDescBreveNaturaSanzione() == null) {

				logger.debug("filtroVO ha attributi null...");
				IrregolaritaProvvisoriaProgettoVO filtroIrregProvv = new IrregolaritaProvvisoriaProgettoVO();
				filtroIrregProvv = beanUtil.transform(filtroVO, IrregolaritaProvvisoriaProgettoVO.class);
				filtroIrregProvv.setDescendentOrder("dtComunicazione");
				List<IrregolaritaProvvisoriaProgettoVO> irregolaritaProvv = genericDAO.findListWhere(filtroIrregProvv);

				if (irregolaritaProvv != null) {
					logger.debug(" trovata irregolaritaProvv size = " + irregolaritaProvv.size());
				} else {
					logger.debug(" trovata irregolaritaProvv NULLA");
				}

				ArrayList<IrregolaritaDTO> resultList = new ArrayList<IrregolaritaDTO>();
				for (int i = 0; i < irregolaritaProvv.size(); i++) {
					IrregolaritaDTO irregProvv = new IrregolaritaDTO();
					// solo per utente non IDG
					irregProvv.setCodiceVisualizzato(irregolaritaProvv.get(i).getCodiceVisualizzato());
					irregProvv.setDenominazioneBeneficiario(irregolaritaProvv.get(i).getDenominazioneBeneficiario());
					///////////
					// setto questo campo poich� altrimenti nella tabella del front-end non viene
					/////////// reperito l'idRiga
					irregProvv.setIdIrregolarita(irregolaritaProvv.get(i).getIdIrregolaritaProvv().longValue());
					///////////
					irregProvv.setIdIrregolaritaProvv(irregolaritaProvv.get(i).getIdIrregolaritaProvv().longValue());
					irregProvv.setDtComunicazioneProvv(irregolaritaProvv.get(i).getDtComunicazione());
					irregProvv.setDtFineProvvisoriaProvv(irregolaritaProvv.get(i).getDtFineProvvisoria());
					irregProvv.setIdProgettoProvv(irregolaritaProvv.get(i).getIdProgetto().longValue());
					if (irregolaritaProvv.get(i).getIdMotivoRevoca() != null) {
						irregProvv.setIdMotivoRevocaProvv(irregolaritaProvv.get(i).getIdMotivoRevoca().longValue());
					}
					irregProvv.setDescMotivoRevocaProvv(irregolaritaProvv.get(i).getDescMotivoRevoca());
					irregProvv.setImportoIrregolaritaProvv(
							NumberUtil.toDouble(irregolaritaProvv.get(i).getImportoIrregolarita()));
					irregProvv.setImportoAgevolazioneIrregProvv(
							NumberUtil.toDouble(irregolaritaProvv.get(i).getImportoAgevolazioneIrreg()));
					irregProvv.setImportoIrregolareCertificatoProvv(
							NumberUtil.toDouble(irregolaritaProvv.get(i).getImportoIrregolareCertificato()));
					irregProvv.setDtFineValiditaProvv(irregolaritaProvv.get(i).getDtFineValidita());
					irregProvv.setDataInizioControlliProvv(irregolaritaProvv.get(i).getDataInizioControlli());
					irregProvv.setDataFineControlliProvv(irregolaritaProvv.get(i).getDataFineControlli());
					irregProvv.setTipoControlliProvv(irregolaritaProvv.get(i).getTipoControlli());
					if (irregolaritaProvv.get(i).getIdPeriodo() != null) {
						irregProvv.setIdPeriodoProvv(irregolaritaProvv.get(i).getIdPeriodo().longValue());
					}
					irregProvv.setDescPeriodoVisualizzataProvv(irregolaritaProvv.get(i).getDescPeriodoVisualizzata());
					if (irregolaritaProvv.get(i).getIdCategAnagrafica() != null) {
						irregProvv
								.setIdCategAnagraficaProvv(irregolaritaProvv.get(i).getIdCategAnagrafica().longValue());
					}
					irregProvv.setDescCategAnagraficaProvv(irregolaritaProvv.get(i).getDescCategAnagrafica());

					logger.debug("DataCampione=" + irregolaritaProvv.get(i).getDataCampione());
					irregProvv.setDataCampione(irregolaritaProvv.get(i).getDataCampione());

					resultList.add(irregProvv);
				}

				LinkedList<IrregolaritaDTO> res = new LinkedList<IrregolaritaDTO>(
						Arrays.asList(beanUtil.transform(irregolaritaDefList, IrregolaritaDTO.class)));
				for (int i = 0; i < res.size(); i++) {
					for (int j = 0; j < resultList.size(); j++) {
						if (res.get(i).getIdIrregolaritaProvv() != null
								&& NumberUtil.compare(res.get(i).getIdIrregolaritaProvv(),
										resultList.get(j).getIdIrregolaritaProvv()) == 0
								&& !res.contains(resultList.get(j))) {
							try {
								res.add(i, resultList.get(j));
							} catch (IndexOutOfBoundsException ex) {
								res.addFirst(resultList.get(j));
							}
							break;
						}
					}
				}
				for (int j = 0; j < resultList.size(); j++) {
					if (!res.contains(resultList.get(j))) {
						res.addFirst(resultList.get(j));
					}
				}

				return beanUtil.transform(res, IrregolaritaDTO.class);
			} else {
				logger.debug(" filtroVO ha attributi valorizzati.");
				return beanUtil.transform(irregolaritaDefList, IrregolaritaDTO.class);
			}
		} catch (Exception e) {
			logger.error("Errore mapping campi irregolarit� nel metodo findIrregolarita()", e);
			e.printStackTrace();
		}
		logger.end();
		return null;
	}

	public IrregolaritaDTO[] findEsitiRegolari(Long idUtente, String identitaDigitale, IrregolaritaDTO filtro) {
		logger.begin();

		logger.debug("filtro=" + filtro.toString());

		IrregolaritaDTO[] output = null;
		try {
			EsitoControlliProgettoVO filtroVO = new EsitoControlliProgettoVO();
			if (filtro.getIdSoggettoBeneficiario() != null)
				filtroVO.setIdSoggettoBeneficiario(new BigDecimal(filtro.getIdSoggettoBeneficiario()));
			if (filtro.getIdProgetto() != null)
				filtroVO.setIdProgetto(new BigDecimal(filtro.getIdProgetto()));

			filtroVO.setDtComunicazione(DateUtil.utilToSqlDate(filtro.getDtComunicazione()));

			logger.debug("filtroVO=" + filtroVO.toString());

			List<EsitoControlliProgettoVO> esiti = genericDAO.findListWhere(filtroVO);

			output = beanUtil.transform(esiti, IrregolaritaDTO.class);

		} catch (Exception e) {
			logger.error("Errore in findEsitiRegolari(): ", e);
		}
		logger.end();
		return output;
	}

	/**
	 * Crea una nuova irregolarit&agrave;.
	 * 
	 * @param idUtente         Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale Identit&agrave; digitale (di IRIDE) dell'utente che
	 *                         esegue l'operazione.
	 * @param idIrregolarita   Identificativo dell'irregolarit&agrave; da trovare.
	 * @return Esito dell'operazione. In caso di successo contiene l'identificativo
	 *         dell'irregolarit&agrave; creata; in caso di fallimento contiene i
	 *         messaggi di errore.
	 * @throws CSIException           Eccezione di sistema.
	 * @throws SystemException        Eccezione di sistema.
	 * @throws UnrecoverableException Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata
	 *                                programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO creaIrregolarita(Long idUtente, String identitaDigitale,
			IrregolaritaDTO irregolarita)
			throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {

		// ogni nuova irregolarita' creata nasce con versione 0
		irregolarita.setNumeroVersione(new Long(0));

		// creo l'irregolarita'
		return inserisciIrregolarita(idUtente, identitaDigitale, irregolarita);
	}

	/**
	 * Modifica un'irregolarit&agrave; provvisoria esistente.
	 * 
	 * @param idUtente         Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale Identit&agrave; digitale (di IRIDE) dell'utente che
	 *                         esegue l'operazione.
	 * @param irregolarita     Dati dell'irregolarit&agrave; da modificare.
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di
	 *         errore.
	 * @throws CSIException           Eccezione di sistema.
	 * @throws SystemException        Eccezione di sistema.
	 * @throws UnrecoverableException Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata
	 *                                programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO modificaIrregolaritaProvvisoria(Long idUtente, String identitaDigitale,
			IrregolaritaDTO irregolarita)
			throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {

		// modifica genericDAO del PbandiTIrregolaritaProvvVO
		try {
			PbandiTIrregolaritaProvvVO irregolaritaProvvVO = new PbandiTIrregolaritaProvvVO();
			irregolaritaProvvVO.setIdIrregolaritaProvv(new BigDecimal(irregolarita.getIdIrregolaritaProvv()));
			irregolaritaProvvVO = genericDAO.findSingleWhere(irregolaritaProvvVO);

			// conversione DTO -> VO
			if (irregolaritaProvvVO.getIdMotivoRevoca() != null)
				irregolaritaProvvVO.setIdMotivoRevoca(new BigDecimal(irregolarita.getIdMotivoRevocaProvv()));
			irregolaritaProvvVO.setImportoIrregolarita(new BigDecimal(irregolarita.getImportoIrregolaritaProvv()));
			if (irregolarita.getImportoAgevolazioneIrregProvv() != null)
				irregolaritaProvvVO
						.setImportoAgevolazioneIrreg(new BigDecimal(irregolarita.getImportoAgevolazioneIrregProvv()));
			else
				irregolaritaProvvVO.setImportoAgevolazioneIrreg(null);
			if (irregolarita.getImportoIrregolareCertificatoProvv() != null)
				irregolaritaProvvVO.setImportoIrregolareCertificato(
						new BigDecimal(irregolarita.getImportoIrregolareCertificatoProvv()));
			else
				irregolaritaProvvVO.setImportoIrregolareCertificato(null);
			if (irregolarita.getDtFineProvvisoriaProvv() != null)
				irregolaritaProvvVO
						.setDtFineProvvisoria(DateUtil.utilToSqlDate(irregolarita.getDtFineProvvisoriaProvv()));
			irregolaritaProvvVO.setTipoControlli(irregolarita.getTipoControlliProvv());
			irregolaritaProvvVO
					.setDataInizioControlli(DateUtil.utilToSqlDate(irregolarita.getDataInizioControlliProvv()));
			if (irregolarita.getDataFineControlliProvv() != null)
				irregolaritaProvvVO
						.setDataFineControlli(DateUtil.utilToSqlDate(irregolarita.getDataFineControlliProvv()));
			else
				irregolaritaProvvVO.setDataFineControlli(null);
			if (irregolarita.getFlagIrregolaritaAnnullataProvv() != null)
				irregolaritaProvvVO.setIrregolaritaAnnullata(irregolarita.getFlagIrregolaritaAnnullataProvv());
			else
				irregolaritaProvvVO.setIrregolaritaAnnullata(null);

			if (irregolarita.getIdPeriodoProvv() != null)
				irregolaritaProvvVO.setIdPeriodo(new BigDecimal(irregolarita.getIdPeriodoProvv()));
			if (irregolarita.getIdCategAnagraficaProvv() != null)
				irregolaritaProvvVO.setIdCategAnagrafica(new BigDecimal(irregolarita.getIdCategAnagraficaProvv()));
			irregolaritaProvvVO.setNote(irregolarita.getNoteProvv());

			logger.info("DataCampione : " + irregolarita.getDataCampione());

			if (irregolarita.getDataCampione() != null)
				irregolaritaProvvVO.setDataCampione(DateUtil.utilToSqlDate(irregolarita.getDataCampione()));
			else
				irregolaritaProvvVO.setDataCampione(null);

			logger.info(
					"IdPeriodoProvv : " + irregolaritaProvvVO.getIdPeriodo() + "; " + irregolarita.getIdPeriodoProvv());
			logger.info("IdCategAnagraficaProvv : " + irregolaritaProvvVO.getIdCategAnagrafica() + "; "
					+ irregolarita.getIdCategAnagraficaProvv());
			logger.info("noteProvv : " + irregolaritaProvvVO.getNote() + "; " + irregolarita.getNoteProvv());

			genericDAO.updateNullables(irregolaritaProvvVO);
		} catch (Exception e) {
			logger.error("Errore in modifica su PBANDI_T_IRREGOLARITA", e);
			throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
		}

		// esito finale
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[] { msg });
		esito.setIdIrregolarita(irregolarita.getIdIrregolarita());
		return esito;
	}

	/**
	 * Modifica un'irregolarit&agrave; definitivia esistente.
	 * 
	 * @param idUtente               Identificativo dell'utente che esegue
	 *                               l'operazione.
	 * @param identitaDigitale       Identit&agrave; digitale (di IRIDE) dell'utente
	 *                               che esegue l'operazione.
	 * @param irregolarita           Dati dell'irregolarit&agrave; da modificare.
	 * @param modificaDatiAggiuntivi true se l'allegato dati aggiuntivi &egrave; da
	 *                               modificare, false se &egrave; da inserire.
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di
	 *         errore.
	 * @throws CSIException           Eccezione di sistema.
	 * @throws SystemException        Eccezione di sistema.
	 * @throws UnrecoverableException Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata
	 *                                programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO modificaIrregolarita(Long idUtente, String identitaDigitale,
			IrregolaritaDTO irregolarita, boolean modificaDatiAggiuntivi)
			throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();

		// 3 - conversione DTO -> VO
		PbandiTIrregolaritaVO irregolaritaVO = beanUtil.transform(irregolarita, PbandiTIrregolaritaVO.class);
		irregolaritaVO.setIdUtenteAgg(new BigDecimal(idUtente));
		irregolaritaVO.setIdDispComunitaria(irregolarita.getDescBreveDispComunitaria() == null ? null
				: decodificheManager.decodeDescBreve(PbandiDDispComunitariaVO.class,
						irregolarita.getDescBreveDispComunitaria()));
		irregolaritaVO.setIdMetodoIndividuazione(irregolarita.getDescBreveMetodoInd() == null ? null
				: decodificheManager.decodeDescBreve(PbandiDMetodoIndividuazioneVO.class,
						irregolarita.getDescBreveMetodoInd()));
		irregolaritaVO.setIdNaturaSanzione(irregolarita.getDescBreveNaturaSanzione() == null ? null
				: decodificheManager.decodeDescBreve(PbandiDNaturaSanzioneVO.class,
						irregolarita.getDescBreveNaturaSanzione()));
		irregolaritaVO.setIdQualificazioneIrreg(irregolarita.getDescBreveQualificIrreg() == null ? null
				: decodificheManager.decodeDescBreve(PbandiDQualificazioneIrregVO.class,
						irregolarita.getDescBreveQualificIrreg()));
		irregolaritaVO.setIdStatoAmministrativo(irregolarita.getDescBreveStatoAmministrativ() == null ? null
				: decodificheManager.decodeDescBreve(PbandiDStatoAmministrativoVO.class,
						irregolarita.getDescBreveStatoAmministrativ()));
		irregolaritaVO.setIdStatoFinanziario(irregolarita.getDescBreveStatoFinanziario() == null ? null
				: decodificheManager.decodeDescBreve(PbandiDStatoFinanziarioVO.class,
						irregolarita.getDescBreveStatoFinanziario()));
		irregolaritaVO.setIdTipoIrregolarita(irregolarita.getDescBreveTipoIrregolarita() == null ? null
				: decodificheManager.decodeDescBreve(PbandiDTipoIrregolaritaVO.class,
						irregolarita.getDescBreveTipoIrregolarita()));
		irregolaritaVO.setSoggettoResponsabile(irregolaritaVO.getSoggettoResponsabile() != null
				? irregolaritaVO.getSoggettoResponsabile().toUpperCase()
				: null);

		// 4 - inserimento/modifica documenti su index

		if (irregolarita.getSchedaOLAF() != null && irregolarita.getSchedaOLAF().getBytesDocumento() != null) {
			try {
				PbandiTDocumentoIndexVO schedaOlafAggVO = new PbandiTDocumentoIndexVO();
				schedaOlafAggVO.setIdDocumentoIndex(new BigDecimal(irregolarita.getSchedaOLAF().getIdDocumentoIndex()));
				schedaOlafAggVO = genericDAO.findSingleWhere(schedaOlafAggVO);

//				indexDAO.aggiornaContentIrregolarita(irregolarita, 
//						Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA, 
//						schedaOlafAggVO.getUuidNodo(),
//						idUtente);

//				InputStream inputStream = new ByteArrayInputStream(irregolarita.getSchedaOLAF().getBytesDocumento());
//				it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = documentiFSManager.trovaTDocumentoIndex(irregolarita.getSchedaOLAF().getIdDocumentoIndex());
				String newName = irregolarita.getSchedaOLAF().getNomeFile();
				newName = newName.replaceFirst("\\.",
						"_" + irregolarita.getSchedaOLAF().getIdDocumentoIndex().longValue() + ".");
				documentiFSManager.aggiornaDocumento(newName, schedaOlafAggVO.getNomeDocumento(),
						Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA,
						irregolarita.getSchedaOLAF().getBytesDocumento());

			} catch (Exception e) {
				logger.error("Errore creazione scheda OLAF su INDEX ", e);
				throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
			}
		}

		Node datiAggiuntiviNode = null;
		boolean esitoSalvaFile = false;
		if (irregolarita.getDatiAggiuntivi() != null && irregolarita.getDatiAggiuntivi().getBytesDocumento() != null) {
			if (modificaDatiAggiuntivi && irregolarita.getDatiAggiuntivi().getIdDocumentoIndex() != null) {
				try {
					PbandiTDocumentoIndexVO datiAggiuntiviAggVO = new PbandiTDocumentoIndexVO();
					datiAggiuntiviAggVO.setIdDocumentoIndex(
							new BigDecimal(irregolarita.getDatiAggiuntivi().getIdDocumentoIndex()));
					datiAggiuntiviAggVO = genericDAO.findSingleWhere(datiAggiuntiviAggVO);
//					indexDAO.aggiornaContentIrregolarita(irregolarita, 
//						Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA,
//						datiAggiuntiviAggVO.getUuidNodo(),
//						idUtente);
					String newName = irregolarita.getDatiAggiuntivi().getNomeFile();
					newName = newName.replaceFirst("\\.",
							"_" + irregolarita.getDatiAggiuntivi().getIdDocumentoIndex().longValue() + ".");
					documentiFSManager.aggiornaDocumento(newName, // Nuovo nome
																	// del file
							datiAggiuntiviAggVO.getNomeDocumento(), // vecchio nome del file
							Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA,
							irregolarita.getDatiAggiuntivi().getBytesDocumento());

				} catch (Exception e) {
					logger.error("Errore aggiornamento dati aggiuntivi su INDEX ", e);
					throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
				}
			} else if (modificaDatiAggiuntivi && irregolarita.getDatiAggiuntivi().getIdDocumentoIndex() == null) {
				try {
					// chiamare solo il salvaFile
//					datiAggiuntiviNode = indexDAO.creaContentIrregolarita(irregolarita, Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA, idUtente);
					it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = new it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO();
					pbandiTDocumentoIndexVO.setNomeFile(irregolarita.getDatiAggiuntivi().getNomeFile());
					// pbandiTDocumentoIndexVO.setIdDocumentoIndex(irregolarita.getDatiAggiuntivi().getIdDocumentoIndex());
					pbandiTDocumentoIndexVO.setRepository(Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
					pbandiTDocumentoIndexVO.setIdTarget(irregolaritaVO.getIdIrregolarita());
					pbandiTDocumentoIndexVO.setIdEntita(getIdEntita());
					BigDecimal idTipoDocDatiAgg = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
							Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
					pbandiTDocumentoIndexVO.setIdTipoDocumentoIndex(idTipoDocDatiAgg);
					pbandiTDocumentoIndexVO.setIdProgetto(irregolaritaVO.getIdProgetto());
					pbandiTDocumentoIndexVO.setUuidNodo("UUID");
					pbandiTDocumentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
					pbandiTDocumentoIndexVO.setDtInserimentoIndex(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
					esitoSalvaFile = documentiFSManager.salvaFile(irregolarita.getDatiAggiuntivi().getBytesDocumento(),
							pbandiTDocumentoIndexVO);
				} catch (Exception e) {
					logger.error("Errore creazione allegato dati aggiuntivi su INDEX ", e);
					throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
				}
			}

		}

		// 5 - modifica genericDAO del PbandiTIrregolaritaVO
		try {
			irregolaritaVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(irregolaritaVO);
		} catch (Exception e) {
			logger.error("Errore in modifica su PBANDI_T_IRREGOLARITA", e);

			// cancello dati aggiuntivi da INDEX
//			if (datiAggiuntiviNode != null && datiAggiuntiviNode.getUid() != null) {
			if (esitoSalvaFile) {
				logger.info(
						"ROLLBACK APPLICATIVO: cancello dati aggiuntivi appena creato su INDEX perch� c'e' stato un errore successivo");
				try {
					// Chiaaare CancellaSuFS deve cancellare solo su FS non su DB. Creare nuovi
					// metodi su DocumentiFSManager perchè il cancella di adesso cancella anche su
					// DB e in questo caso
					// non deve farlo
//					indexDAO.cancellaNodo(datiAggiuntiviNode);
					documentiFSManager.cancellaSuFileSystem(irregolarita.getDatiAggiuntivi().getNomeFile(),
							Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
				} catch (Exception e1) {
					logger.info("ATTENZIONE: ERRORE NEL CANCELLARE Dati Aggiuntivi appena creati su index "
							+ e1.getMessage());
					IrregolaritaException re = new IrregolaritaException(
							"Scheda OLAF non cancellata su servizio INDEX");
					re.initCause(e);
					throw re;
				}
			}

			throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
		}

		// 6 - inserimento/modifica documenti su PBandiTDocumentoIndex (2 documenti da
		// inserire, scheda OLAF obbligatoria, dati aggiuntivi facoltativo)
		BigDecimal idEntita = getIdEntita();

		// 6a - modifica scheda OLAF (obbligatoria)
		if (irregolarita.getSchedaOLAF() != null && irregolarita.getSchedaOLAF().getBytesDocumento() != null) {
			BigDecimal idTipoDocOlaf = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
					Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);
			PbandiTDocumentoIndexVO schedaOlafVO = new PbandiTDocumentoIndexVO();
			schedaOlafVO.setIdDocumentoIndex(new BigDecimal(irregolarita.getSchedaOLAF().getIdDocumentoIndex()));
			schedaOlafVO.setDtInserimentoIndex(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
			schedaOlafVO.setNomeFile(irregolarita.getSchedaOLAF().getNomeFile());
			String newName = schedaOlafVO.getNomeFile();
			schedaOlafVO.setNomeDocumento(
					newName.replaceFirst("\\.", "_" + schedaOlafVO.getIdDocumentoIndex().longValue() + "."));
			schedaOlafVO.setIdTarget(irregolaritaVO.getIdIrregolarita());
			schedaOlafVO.setIdEntita(idEntita);
			schedaOlafVO.setIdTipoDocumentoIndex(idTipoDocOlaf);
			schedaOlafVO.setIdUtenteAgg(new BigDecimal(idUtente));
			schedaOlafVO.setIdProgetto(irregolaritaVO.getIdProgetto());
			schedaOlafVO.setDtAggiornamentoIndex(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
			try {
				genericDAO.update(schedaOlafVO);
			} catch (Exception e) {
				logger.error("Errore in inserimento su PBANDI_T_DOCUMENTO_INDEX per scheda OLAF irregolarita'", e);

				// cancello dati aggiuntivi da INDEX
				if (esitoSalvaFile) {
					logger.info(
							"ROLLBACK APPLICATIVO: cancello dati aggiuntivi appena creato su INDEX perch� c'e' stato un errore successivo");
					try {
						// Cancella File solo su FS scheda OLAF
						// chiamare il "trovaTDocumentoIndex" -> con l'oggetto ottenuto creare un nuovo
						// metodo come cancellaFile ma senza cancella su db
//						indexDAO.cancellaNodo(datiAggiuntiviNode);
						documentiFSManager.cancellaSuFileSystem(irregolarita.getSchedaOLAF().getNomeFile(),
								Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);
					} catch (Exception e1) {
						logger.info("ATTENZIONE: ERRORE NEL CANCELLARE Dati Aggiuntivi appena creati su index "
								+ e1.getMessage());
						IrregolaritaException re = new IrregolaritaException(
								"Scheda OLAF non cancellata su servizio INDEX");
						re.initCause(e);
						throw re;
					}
				}

				throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
			}
		}

		// 6a - modifica/inserimanto dati aggiuntivi (facoltativa)
		if (irregolarita.getDatiAggiuntivi() != null && irregolarita.getDatiAggiuntivi().getBytesDocumento() != null) {
			BigDecimal idTipoDocDatiAgg = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
					Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
			PbandiTDocumentoIndexVO datiAggiuntiviVO = new PbandiTDocumentoIndexVO();
			datiAggiuntiviVO.setIdTarget(irregolaritaVO.getIdIrregolarita());
			datiAggiuntiviVO.setIdEntita(idEntita);
			datiAggiuntiviVO.setIdTipoDocumentoIndex(idTipoDocDatiAgg);
			datiAggiuntiviVO.setNomeFile(irregolarita.getDatiAggiuntivi().getNomeFile());
			datiAggiuntiviVO.setIdProgetto(irregolaritaVO.getIdProgetto());

			if (modificaDatiAggiuntivi && irregolarita.getDatiAggiuntivi().getIdDocumentoIndex() != null) {
				datiAggiuntiviVO
						.setIdDocumentoIndex(new BigDecimal(irregolarita.getDatiAggiuntivi().getIdDocumentoIndex()));
				String newName = datiAggiuntiviVO.getNomeFile();
				datiAggiuntiviVO.setNomeDocumento(
						newName.replaceFirst("\\.", "_" + datiAggiuntiviVO.getIdDocumentoIndex().longValue() + "."));
				datiAggiuntiviVO.setIdUtenteAgg(new BigDecimal(idUtente));
				datiAggiuntiviVO.setDtAggiornamentoIndex(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
				try {
					genericDAO.update(datiAggiuntiviVO);
				} catch (Exception e) {
					logger.error("Errore in modifica su PBANDI_T_DOCUMENTO_INDEX per dati aggiuntivi irregolarita'", e);
					throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
				}
			}
		}

		// 7 - esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[] { msg });
		esito.setIdIrregolarita(irregolarita.getIdIrregolarita());
		return esito;
	}

	/**
	 * Aggiorna un'irregolarit&agrave;. L'aggiornamento consiste nella creazione di
	 * una nuova irregolarit&agrave che ha per dati
	 * 
	 * @param idUtente               Identificativo dell'utente che esegue
	 *                               l'operazione.
	 * @param identitaDigitale       Identit&agrave; digitale (di IRIDE) dell'utente
	 *                               che esegue l'operazione.
	 * @param irregolarita           Dati dell'irregolarit&agrave; da modificare.
	 * @param modificaDatiAggiuntivi true se l'allegato dati aggiuntivi &egrave; da
	 *                               modificare, false se &egrave; da inserire.
	 * @return Esito dell'operazione. In caso di fallimento contiene i messaggi di
	 *         errore.
	 * @throws CSIException           Eccezione di sistema.
	 * @throws SystemException        Eccezione di sistema.
	 * @throws UnrecoverableException Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata
	 *                                programmaticamente.
	 */
	public EsitoSalvaIrregolaritaDTO aggiornaIrregolarita(Long idUtente, String identitaDigitale,
			IrregolaritaDTO irregolarita)
			throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {

		// 'clono' l'irregolarita'
		IrregolaritaDTO irregolaritaNew = getBeanUtil().clone(irregolarita);
		irregolaritaNew.setIdIrregolarita(null); // forse non serve, ma per sicurezza...
		Long idIrregolaritaPadre = irregolarita.getIdIrregolaritaCollegata() != null
				? irregolarita.getIdIrregolaritaCollegata()
				: irregolarita.getIdIrregolarita();
		irregolaritaNew.setIdIrregolaritaCollegata(idIrregolaritaPadre);
		irregolaritaNew.setNumeroVersione(irregolarita.getNumeroVersione() + 1); // incremento di 1 la versione
		irregolaritaNew.setDtIms(null);
		irregolaritaNew.setNumeroIms(null);
		irregolaritaNew.setDtComunicazione(DateUtil.getDataOdiernaSenzaOra());
		irregolaritaNew.setSchedaOLAF(irregolarita.getSchedaOLAF());
		irregolaritaNew.setDatiAggiuntivi(irregolarita.getDatiAggiuntivi());

		// Recupero i documenti (bytes) da Index se non sono stati uploadati nuovi files
		// (ovvero se non mi vengono trasmessi dei bytes)

		// scheda OLAF (obbligatoria)
		if (irregolaritaNew.getSchedaOLAF() != null && irregolaritaNew.getSchedaOLAF().getBytesDocumento() == null) {
			Long idDocumentoIndexSO = irregolaritaNew.getSchedaOLAF().getIdDocumentoIndex();
			try {
				EsitoScaricaDocumentoIrregolaritaDTO schedaOlaf = scaricaDocumentoIrregolarita(idUtente,
						identitaDigitale, idDocumentoIndexSO);
				irregolaritaNew.getSchedaOLAF().setBytesDocumento(schedaOlaf.getBytesDocumento());
				irregolaritaNew.getSchedaOLAF().setNomeFile(schedaOlaf.getNomeFile());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				IrregolaritaException ie = new IrregolaritaException("Scheda OLAF non trovata su servizio INDEX");
				throw ie;
			}
		}

		// dati aggiuntivi (facoltativa)
		if (irregolaritaNew.getDatiAggiuntivi() != null
				&& irregolaritaNew.getDatiAggiuntivi().getBytesDocumento() == null) {
			Long idDocumentoIndexDA = irregolaritaNew.getDatiAggiuntivi().getIdDocumentoIndex();
			try {
				EsitoScaricaDocumentoIrregolaritaDTO datiAggiuntivi = scaricaDocumentoIrregolarita(idUtente,
						identitaDigitale, idDocumentoIndexDA);
				irregolaritaNew.getDatiAggiuntivi().setBytesDocumento(datiAggiuntivi.getBytesDocumento());
				irregolaritaNew.getDatiAggiuntivi().setNomeFile(datiAggiuntivi.getNomeFile());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				IrregolaritaException ie = new IrregolaritaException(
						"Allegato dati aggiuntivi non trovato su servizio INDEX");
				throw ie;
			}
		}

		// creo l'irregolarita'
		return inserisciIrregolarita(idUtente, identitaDigitale, irregolaritaNew);
	}

	/**
	 * Scarica da INDEX un documento relativo ad una irregolarit&agrave;.
	 * 
	 * @param idUtente         Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale Identit&agrave; digitale (di IRIDE) dell'utente che
	 *                         esegue l'operazione.
	 * @param idDocumentoIndex Identificativo di INDEX del documento richiesto.
	 * @return Esito dell'operazione. In caso successo contiene il nome e i bytes
	 *         del documento richiesto.
	 * @throws Exception
	 */
	public EsitoScaricaDocumentoIrregolaritaDTO scaricaDocumentoIrregolarita(Long idUtente, String identitaDigitale,
			Long idDocumentoIndex) throws Exception {

		logger.begin();
		EsitoScaricaDocumentoIrregolaritaDTO result = new EsitoScaricaDocumentoIrregolaritaDTO();

		// cerco il documento su DB
		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();
		documentoIndexVO.setIdDocumentoIndex(new BigDecimal(idDocumentoIndex));
		documentoIndexVO = genericDAO.findSingleWhere(documentoIndexVO);

		// cerco il contenuto su index
		if (documentoIndexVO != null && documentoIndexVO.getUuidNodo() != null) {
			result.setBytesDocumento(indexDAO.recuperaContenuto(documentoIndexVO.getUuidNodo()));
			result.setNomeFile(documentoIndexVO.getNomeFile());
		}

		logger.end();
		return result;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS

	/**
	 * Effettua le operazioni una nuova irregolarit&agrave;.
	 * 
	 * @param idUtente         Identificativo dell'utente che esegue l'operazione.
	 * @param identitaDigitale Identit&agrave; digitale (di IRIDE) dell'utente che
	 *                         esegue l'operazione.
	 * @param idIrregolarita   Identificativo dell'irregolarit&agrave; da trovare.
	 * @return Esito dell'operazione. In caso di successo contiene l'identificativo
	 *         dell'irregolarit&agrave; creata; in caso di fallimento contiene i
	 *         messaggi di errore.
	 * @throws CSIException           Eccezione di sistema.
	 * @throws SystemException        Eccezione di sistema.
	 * @throws UnrecoverableException Eccezione di sistema.
	 * @throws IrregolaritaException  Eccezione applicativa lanciata
	 *                                programmaticamente.
	 */
	private EsitoSalvaIrregolaritaDTO inserisciIrregolarita(Long idUtente, String identitaDigitale,
			IrregolaritaDTO irregolarita)
			throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {

		logger.begin();

		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();

		// 1 - conversione DTO -> VO
		PbandiTIrregolaritaVO irregolaritaVO = beanUtil.transform(irregolarita, PbandiTIrregolaritaVO.class);
		irregolaritaVO.setIdUtenteIns(new BigDecimal(idUtente));
		irregolaritaVO.setIdDispComunitaria(decodificheManager.decodeDescBreve(PbandiDDispComunitariaVO.class,
				irregolarita.getDescBreveDispComunitaria()));
		irregolaritaVO.setIdMetodoIndividuazione(decodificheManager.decodeDescBreve(PbandiDMetodoIndividuazioneVO.class,
				irregolarita.getDescBreveMetodoInd()));
		irregolaritaVO.setIdNaturaSanzione(decodificheManager.decodeDescBreve(PbandiDNaturaSanzioneVO.class,
				irregolarita.getDescBreveNaturaSanzione()));
		irregolaritaVO.setIdQualificazioneIrreg(decodificheManager.decodeDescBreve(PbandiDQualificazioneIrregVO.class,
				irregolarita.getDescBreveQualificIrreg()));
		irregolaritaVO.setIdStatoAmministrativo(decodificheManager.decodeDescBreve(PbandiDStatoAmministrativoVO.class,
				irregolarita.getDescBreveStatoAmministrativ()));
		irregolaritaVO.setIdStatoFinanziario(decodificheManager.decodeDescBreve(PbandiDStatoFinanziarioVO.class,
				irregolarita.getDescBreveStatoFinanziario()));
		irregolaritaVO.setIdTipoIrregolarita(decodificheManager.decodeDescBreve(PbandiDTipoIrregolaritaVO.class,
				irregolarita.getDescBreveTipoIrregolarita()));
		irregolaritaVO.setFlagBlocco(Constants.FLAG_FALSE);
		irregolaritaVO.setFlagCasoChiuso(Constants.FLAG_FALSE);
		irregolaritaVO.setSoggettoResponsabile(irregolaritaVO.getSoggettoResponsabile() != null
				? irregolaritaVO.getSoggettoResponsabile().toUpperCase()
				: null);
		irregolaritaVO.setDtComunicazione(DateUtil.utilToSqlDate(irregolarita.getDtComunicazione()));
		irregolaritaVO.setIdCategAnagrafica(NumberUtil.toBigDecimal(irregolarita.getIdCategAnagrafica()));
		irregolaritaVO.setIdPeriodo(NumberUtil.toBigDecimal(irregolarita.getIdPeriodo()));
		irregolaritaVO.setNote(irregolarita.getNote());

		logger.info("DataCampione=" + irregolarita.getDataCampione());
		irregolaritaVO.setDataCampione(DateUtil.utilToSqlDate(irregolarita.getDataCampione()));

		// 2 - inserimento genericDAO del PbandiTIrregolaritaVO
		try {
			irregolaritaVO = genericDAO.insert(irregolaritaVO);
		} catch (Exception e) {
			logger.error("Errore in inserimento su PBANDI_T_IRREGOLARITA", e);
			throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
		}

		logger.info(
				"inserisciEsitoInregolareDefinitivo(): IdIrregolarita = " + irregolaritaVO.getIdIrregolaritaProvv());
		if (irregolarita.getIdIrregolaritaProvv() != null) {
			PbandiTIrregolaritaProvvVO prov = new PbandiTIrregolaritaProvvVO();
			prov.setIdIrregolaritaProvv(irregolaritaVO.getIdIrregolaritaProvv());
			prov.setDtFineProvvisoria(DateUtil.getSysdate());
			try {
				genericDAO.update(prov);
			} catch (Exception e) {
				logger.error(
						"inserisciEsitoInregolare Definitivo(): ERRORE nell'aggiornamento di PbandiTIrregolaritaProvv. "
								+ e);
				throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
			}
		}

		// inserisco l'idIrregolarita ottenuto dall'inserimento nel DTO (serve a INDEX)
		irregolarita.setIdIrregolarita(irregolaritaVO.getIdIrregolarita().longValue());
		logger.info("inserisciIrregolarita(): id inserito = " + irregolaritaVO.getIdIrregolarita());

		// 3 - inserimento documenti su index
		Node schedaOlafNode = null;
		boolean esitoSalvaFileOLAF = false;
		boolean esitoSalvaFileSchedaAggiuntiva = false;
		BigDecimal idEntita = getIdEntita();
		try {
//			schedaOlafNode = indexDAO.creaContentIrregolarita(irregolarita, Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA, idUtente);

			it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = new it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO();
			pbandiTDocumentoIndexVO.setNomeFile(irregolarita.getSchedaOLAF().getNomeFile());

			pbandiTDocumentoIndexVO.setIdTarget(irregolaritaVO.getIdIrregolarita());
			BigDecimal idTipoDocOlaf = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
					Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);
			pbandiTDocumentoIndexVO.setIdTipoDocumentoIndex(idTipoDocOlaf);
			pbandiTDocumentoIndexVO.setIdEntita(idEntita);
			pbandiTDocumentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			pbandiTDocumentoIndexVO.setIdProgetto(irregolaritaVO.getIdProgetto());

			pbandiTDocumentoIndexVO.setIdDocumentoIndex(irregolarita.getSchedaOLAF().getIdDocumentoIndex() != null
					? new BigDecimal(irregolarita.getSchedaOLAF().getIdDocumentoIndex())
					: null);
			pbandiTDocumentoIndexVO.setRepository(Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);

//			esitoSalvaFileOLAF = documentiFSManager.salvaSuFileSystem(new ByteArrayInputStream(irregolarita.getSchedaOLAF().getBytesDocumento()), 
//					                                                  pbandiTDocumentoIndexVO.getNomeFile(), pbandiTDocumentoIndexVO.getRepository());
			esitoSalvaFileOLAF = documentiFSManager.salvaFile(irregolarita.getSchedaOLAF().getBytesDocumento(),
					pbandiTDocumentoIndexVO);

		} catch (Exception e) {
			logger.error("Errore creazione scheda OLAF su INDEX ", e);
			throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
		}
		logger.info("inserisciIrregolarita(): inserimento del file : " + irregolarita.getSchedaOLAF().getNomeFile()
				+ " nella cartella: " + Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA + " eseguito");

		Node datiAggiuntiviNode = null;
		if (irregolarita.getDatiAggiuntivi() != null) {
			try {
//				datiAggiuntiviNode = indexDAO.creaContentIrregolarita(irregolarita, Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA, idUtente);

				it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = new it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO();
				pbandiTDocumentoIndexVO.setNomeFile(irregolarita.getDatiAggiuntivi().getNomeFile());

				pbandiTDocumentoIndexVO.setIdTarget(irregolaritaVO.getIdIrregolarita());
				BigDecimal idTipoDocDatiAgg = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
						Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
				pbandiTDocumentoIndexVO.setIdTipoDocumentoIndex(idTipoDocDatiAgg);
				pbandiTDocumentoIndexVO.setIdEntita(idEntita);
				pbandiTDocumentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
				pbandiTDocumentoIndexVO.setIdProgetto(irregolaritaVO.getIdProgetto());

				pbandiTDocumentoIndexVO
						.setIdDocumentoIndex(irregolarita.getDatiAggiuntivi().getIdDocumentoIndex() != null
								? new BigDecimal(irregolarita.getDatiAggiuntivi().getIdDocumentoIndex())
								: null);
				pbandiTDocumentoIndexVO.setRepository(Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);

//				esitoSalvaFileSchedaAggiuntiva = documentiFSManager.salvaSuFileSystem(new ByteArrayInputStream(irregolarita.getDatiAggiuntivi().getBytesDocumento()), 
//                        															  pbandiTDocumentoIndexVO.getNomeFile(), pbandiTDocumentoIndexVO.getRepository());
				esitoSalvaFileSchedaAggiuntiva = documentiFSManager
						.salvaFile(irregolarita.getDatiAggiuntivi().getBytesDocumento(), pbandiTDocumentoIndexVO);

			} catch (Exception e) {
				logger.error("Errore creazione allegato dati aggiuntivi su INDEX ", e);
				// cancello scheda OLAF se fallisce l'inserimento dei dati aggiuntivi
//				if (schedaOlafNode != null && schedaOlafNode.getUid() != null) {
				if (esitoSalvaFileOLAF) {
					logger.info(
							"ROLLBACK APPLICATIVO: cancello Scheda OLAF appena creato su INDEX perch� c'e' stato un errore successivo");
					try {
//						indexDAO.cancellaNodo(schedaOlafNode);
						documentiFSManager.cancellaSuFileSystem(irregolarita.getSchedaOLAF().getNomeFile(),
								Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);
					} catch (Exception e1) {
						logger.info("ATTENZIONE: ERRORE NEL CANCELLARE Scheda OLAF appena creato su index "
								+ e1.getMessage());
						IrregolaritaException re = new IrregolaritaException(
								"Scheda OLAF non cancellata su servizio INDEX");
						re.initCause(e);
						throw re;
					}
				}

				throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
			}

			logger.info("inserisciIrregolarita(): inserimento del file : "
					+ irregolarita.getDatiAggiuntivi().getNomeFile() + " " + "nella cartella: "
					+ Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA + " eseguito");
		}

		// 4 - inserimento documenti su PBandiTDocumentoIndex (2 documenti da inserire,
		// scheda OLAF obbligatoria, dati aggiuntivi facoltativo)
//		BigDecimal idEntita = getIdEntita();

		// 4a - inserimento scheda OLAF (obbligatoria)
//		BigDecimal idTipoDocOlaf = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);
//		PbandiTDocumentoIndexVO schedaOlafVO = new PbandiTDocumentoIndexVO();
//		schedaOlafVO.setDtInserimentoIndex(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
//		schedaOlafVO.setNomeFile(irregolarita.getSchedaOLAF().getNomeFile());
//		schedaOlafVO.setIdTarget(irregolaritaVO.getIdIrregolarita());
//		schedaOlafVO.setIdEntita(idEntita);
//		schedaOlafVO.setIdTipoDocumentoIndex(idTipoDocOlaf);
//		schedaOlafVO.setUuidNodo("UUID");
//		schedaOlafVO.setIdUtenteIns(new BigDecimal(idUtente));
//		schedaOlafVO.setDtInserimentoIndex(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
//		schedaOlafVO.setRepository(Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);
//		schedaOlafVO.setIdProgetto(irregolaritaVO.getIdProgetto());
//		try {
//			schedaOlafVO = genericDAO.insert(schedaOlafVO);
//		} catch (Exception e) {
//			logger.error("Errore in inserimento su PBANDI_T_DOCUMENTO_INDEX per scheda OLAF irregolarita'", e);
//			
//			// cancello scheda OLAF da INDEX
////			if (schedaOlafNode != null && schedaOlafNode.getUid() != null) {
//			if (esitoSalvaFileOLAF) {
//				
//				logger.info("ROLLBACK APPLICATIVO: cancello Scheda OLAF appena creato su INDEX perch� c'e' stato un errore successivo");
//				try {
////					indexDAO.cancellaNodo(schedaOlafNode);
//					documentiFSManager.cancellaSuFileSystem(irregolarita.getSchedaOLAF().getNomeFile(), Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);
//				} catch (Exception e1) {
//					logger.info("ATTENZIONE: ERRORE NEL CANCELLARE Scheda OLAF appena creato su index " + e1.getMessage());
//					IrregolaritaException re = new IrregolaritaException("Scheda OLAF non cancellata su servizio INDEX");
//					re.initCause(e);
//					throw re;
//				}
//			}			
//			
//			// cancello dati aggiuntivi da INDEX
////			if (datiAggiuntiviNode != null && datiAggiuntiviNode.getUid() != null) {
//			if (esitoSalvaFileSchedaAggiuntiva) {
//				
//				logger.info("ROLLBACK APPLICATIVO: cancello dati aggiuntivi appena creato su INDEX perch� c'e' stato un errore successivo");
//				try {
////					indexDAO.cancellaNodo(datiAggiuntiviNode);
//					documentiFSManager.cancellaSuFileSystem(irregolarita.getDatiAggiuntivi().getNomeFile(), Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
//				} catch (Exception e1) {
//					logger.info("ATTENZIONE: ERRORE NEL CANCELLARE Dati Aggiuntivi appena creati su index " + e1.getMessage());
//					IrregolaritaException re = new IrregolaritaException("Scheda OLAF non cancellata su servizio INDEX");
//					re.initCause(e);
//					throw re;
//				}
//			}
//			
//			throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
//		}
//		logger.info("inserisciIrregolarita(): inserimento scheda olaf eseguito");

		// 6a - inserimento dati aggiuntivi (facoltativa)
//		if (irregolarita.getDatiAggiuntivi() != null) {
//			BigDecimal idTipoDocDatiAgg = decodificheManager.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class, Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
//			PbandiTDocumentoIndexVO datiAggiuntiviVO = new PbandiTDocumentoIndexVO();
//			datiAggiuntiviVO.setNomeFile(irregolarita.getDatiAggiuntivi().getNomeFile());
//			datiAggiuntiviVO.setIdTarget(irregolaritaVO.getIdIrregolarita());
//			datiAggiuntiviVO.setIdEntita(idEntita);
//			datiAggiuntiviVO.setIdTipoDocumentoIndex(idTipoDocDatiAgg);
//			datiAggiuntiviVO.setUuidNodo("UUID");
//			datiAggiuntiviVO.setIdUtenteIns(new BigDecimal(idUtente));
//			datiAggiuntiviVO.setDtInserimentoIndex(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
//			datiAggiuntiviVO.setRepository(Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
//			datiAggiuntiviVO.setIdProgetto(irregolaritaVO.getIdProgetto());
//			try {
////				datiAggiuntiviVO = genericDAO.insert(datiAggiuntiviVO);
//			} catch (Exception e) {
//				logger.error("Errore in inserimento su PBANDI_T_DOCUMENTO_INDEX per dati aggiuntivi irregolarita'", e);
//				
//				// cancello scheda OLAF da INDEX
////				if (schedaOlafNode != null && schedaOlafNode.getUid() != null) {
//				if (esitoSalvaFileOLAF) {
//					
//					logger.info("ROLLBACK APPLICATIVO: cancello Scheda OLAF appena creato su INDEX perch� c'e' stato un errore successivo");
//					try {
////						indexDAO.cancellaNodo(schedaOlafNode);
//						documentiFSManager.cancellaSuFileSystem(irregolarita.getSchedaOLAF().getNomeFile(), Constants.TIPO_DOCUMENTO_INDEX_SCHEDA_OLAF_IRREGOLARITA);
//					} catch (Exception e1) {
//						logger.info("ATTENZIONE: ERRORE NEL CANCELLARE Scheda OLAF appena creato su index " + e1.getMessage());
//						IrregolaritaException re = new IrregolaritaException("Scheda OLAF non cancellata su servizio INDEX");
//						re.initCause(e);
//						throw re;
//					}
//				}			
//				
//				// cancello dati aggiuntivi da INDEX
////				if (datiAggiuntiviNode != null && datiAggiuntiviNode.getUid() != null) {
//				if (esitoSalvaFileSchedaAggiuntiva) {
//					logger.info("ROLLBACK APPLICATIVO: cancello dati aggiuntivi appena creato su INDEX perch� c'e' stato un errore successivo");
//					try {
////						indexDAO.cancellaNodo(datiAggiuntiviNode);
//						documentiFSManager.cancellaSuFileSystem(irregolarita.getDatiAggiuntivi().getNomeFile(), Constants.TIPO_DOCUMENTO_INDEX_DATI_AGGIUNTIVI_IRREGOLARITA);
//					} catch (Exception e1) {
//						logger.info("ATTENZIONE: ERRORE NEL CANCELLARE Dati Aggiuntivi appena creati su index " + e1.getMessage());
//						IrregolaritaException re = new IrregolaritaException("Scheda OLAF non cancellata su servizio INDEX");
//						re.initCause(e);
//						throw re;
//					}
//				}
//				
//				throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
//			}
//		}
//		logger.info("inserisciIrregolarita(): inserimento dati aggiuntivi eseguito");

		// 7 - esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[] { msg });
		esito.setIdIrregolarita(NumberUtil.toLong(irregolaritaVO.getIdIrregolarita()));
		logger.end();
		return esito;

	}

	/**
	 * 
	 * @return
	 */
	private BigDecimal getIdEntita() {
		PbandiCEntitaVO entitaVO = new PbandiCEntitaVO();
		entitaVO.setNomeEntita("PBANDI_T_IRREGOLARITA");
		entitaVO = genericDAO.findSingleWhere(entitaVO);
		return entitaVO.getIdEntita();
	}

	public String findDescDisimpegno(Long idIrregolarita) {
		// Leggo i dati dell'eventuale disimpegno\revoca a cui � associata
		// l'irregolarit�.
		PbandiRRevocaIrregVO revIrrVO = new PbandiRRevocaIrregVO();
		revIrrVO.setIdIrregolarita(new BigDecimal(idIrregolarita));
		List<PbandiRRevocaIrregVO> lista = genericDAO.findListWhere(revIrrVO);
		if (lista.size() == 0) {
			return "";
		} else {
			// Legge il disimpegno.
			PbandiTRevocaVO revoca = new PbandiTRevocaVO();
			revoca.setIdRevoca(lista.get(0).getIdRevoca());
			revoca = genericDAO.findSingleWhere(revoca);
			// Legge il motivo del disimpegno.
			PbandiDMotivoRevocaVO motivo = new PbandiDMotivoRevocaVO();
			motivo.setIdMotivoRevoca(revoca.getIdMotivoRevoca());
			motivo = genericDAO.findSingleWhere(motivo);
			String importo = "";
			if (revoca.getImporto() != null) {
				importo = NumberUtil.getStringValue(revoca.getImporto());
			}
			String s = "disimpegno associato: " + motivo.getDescMotivoRevoca() + " - euro " + importo;
			return s;
		}
	}

	public EsitoSalvaIrregolaritaDTO creaEsitoRegolare(Long idUtente, String identitaDigitale,
			IrregolaritaDTO irregolarita)
			throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {
		// ogni nuova irregolarita' creata nasce con versione 0
		irregolarita.setNumeroVersione(new Long(0));
		// creo l'irregolarita'
		return inserisciEsitoRegolare(idUtente, identitaDigitale, irregolarita);
	}

	private EsitoSalvaIrregolaritaDTO inserisciEsitoRegolare(Long idUtente, String identitaDigitale,
			IrregolaritaDTO irregolarita)
			throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {

		logger.begin();
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();

		// 1 - conversione DTO -> VO sqldate utildate
		PbandiTEsitoControlliVO vo = beanUtil.transform(irregolarita, PbandiTEsitoControlliVO.class);
		vo.setDataFineControlli(DateUtil.utilToSqlDate(irregolarita.getDataFineControlli()));
		vo.setDataInizioControlli(DateUtil.utilToSqlDate(irregolarita.getDataInizioControlli()));
		vo.setDtComunicazione(DateUtil.utilToSqlDate(irregolarita.getDtComunicazione()));
		vo.setEsitoControllo("REGOLARE");
		vo.setIdCategAnagrafica(NumberUtil.toBigDecimal(irregolarita.getIdCategAnagrafica()));
		vo.setIdPeriodo(NumberUtil.toBigDecimal(irregolarita.getIdPeriodo()));
		vo.setIdProgetto(NumberUtil.toBigDecimal(irregolarita.getIdProgetto()));
		vo.setNote(irregolarita.getNote());
		vo.setTipoControlli(irregolarita.getTipoControlli());
		vo.setDataCampione(DateUtil.utilToSqlDate(irregolarita.getDataCampione()));

		logger.debug("irregolarita.getDataCampione()=" + irregolarita.getDataCampione());
		logger.debug("PbandiTEsitoControlliVO =" + vo);

		// 2 - inserimento genericDAO del PbandiTIrregolaritaVO
		try {
			vo = genericDAO.insert(vo);
		} catch (Exception e) {
			logger.error("Errore in inserimento su PBANDI_T_ESITO_CONTROLLI", e);
			throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
		}

		// 3 - se si tratta di un esito regolare trasformato da una irregolarit�
		// provvisoria,
		// si salva l'id dell'esito regolare nella irregolarit� provvisoria.
		logger.info("inserisciEsitoRegolare(): IdIrregolaritaCollegata = " + irregolarita.getIdIrregolaritaCollegata());
		if (irregolarita.getIdIrregolaritaCollegata() != null) {
			PbandiTIrregolaritaProvvVO prov = new PbandiTIrregolaritaProvvVO();
			prov.setIdIrregolaritaProvv(new BigDecimal(irregolarita.getIdIrregolaritaCollegata()));
			prov.setIdEsitoControllo(vo.getIdEsitoControllo());
			prov.setDtFineProvvisoria(DateUtil.getSysdate());
			try {
				genericDAO.update(prov);
			} catch (Exception e) {
				logger.error("inserisciEsitoRegolare(): ERRORE nell'aggiornamento di PbandiTIrregolaritaProvv. " + e);
				throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
			}
		}

		// 4 - esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[] { msg });
		esito.setIdIrregolarita(NumberUtil.toLong(vo.getIdEsitoControllo()));

		logger.end();
		return esito;
	}

	public EsitoSalvaIrregolaritaDTO modificaEsitoRegolare(Long idUtente, String identitaDigitale,
			IrregolaritaDTO irregolarita)
			throws CSIException, SystemException, UnrecoverableException, IrregolaritaException {

		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();

		PbandiTEsitoControlliVO vo = beanUtil.transform(irregolarita, PbandiTEsitoControlliVO.class);
		vo.setIdEsitoControllo(NumberUtil.toBigDecimal(irregolarita.getIdEsitoControllo()));
		vo.setDataFineControlli(DateUtil.utilToSqlDate(irregolarita.getDataFineControlli()));
		vo.setDataInizioControlli(DateUtil.utilToSqlDate(irregolarita.getDataInizioControlli()));
		vo.setDtComunicazione(DateUtil.utilToSqlDate(irregolarita.getDtComunicazione()));
		vo.setEsitoControllo("REGOLARE");
		vo.setIdCategAnagrafica(NumberUtil.toBigDecimal(irregolarita.getIdCategAnagrafica()));
		vo.setIdPeriodo(NumberUtil.toBigDecimal(irregolarita.getIdPeriodo()));
		vo.setIdProgetto(NumberUtil.toBigDecimal(irregolarita.getIdProgetto()));
		vo.setNote(irregolarita.getNote());
		vo.setTipoControlli(irregolarita.getTipoControlli());

		try {
			genericDAO.updateNullables(vo);
		} catch (Exception e) {
			logger.error("Errore in modifica su PBANDI_T_ESITO_CONTROLLI con id_esito_controllo = "
					+ vo.getIdEsitoControllo(), e);
			throw new IrregolaritaException(ErrorConstants.ERRORE_SERVER, e);
		}

		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessaggiConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[] { msg });
		esito.setIdIrregolarita(NumberUtil.toLong(vo.getIdEsitoControllo()));
		return esito;
	}

}
