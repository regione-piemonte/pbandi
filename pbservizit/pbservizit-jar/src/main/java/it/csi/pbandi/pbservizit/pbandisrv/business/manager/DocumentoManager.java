/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.manager;

import it.csi.pbandi.pbservizit.dto.archivioFile.FileDTO;
//import it.csi.pbandi.pbservizit.integration.dao.impl.GestioneFileDAOImpl;
import it.csi.pbandi.pbservizit.integration.dao.impl.DecodificheDAOImpl;
import it.csi.pbandi.pbservizit.pbandisrv.dto.archivio.UserSpaceDTO;
//import it.csi.pbandi.pbweb.dto.archivioFile.EsitoInsertFiles;
import it.csi.pbandi.pbservizit.pbandisrv.dto.checklist.EsitoSalvaModuloCheckListDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoCheckListInLocoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.AllegatoRelazioneTecnicaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListAffidamentoDocumentaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListAffidamentoInLocoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListDocumentaleDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CheckListInLocoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ComunicazioneFineProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DocumentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ReportCampionamentoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.DocumentNotCreatedException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.DocumentNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO.Pair;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.exception.RecordNotFoundException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.BandoLineaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DichiarazioneDiSpesaConTipoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoDiSpesaDichiarazioneTotalePagamentiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoIndexMaxVersioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.GenericVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiCTipoDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoTipoDocIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDocuIndexTipoStatoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRTpDocIndBanLiIntVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTAppaltoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTCampionamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTChecklistVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTComunicazFineProgVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDichiarazioneSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoDiSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexLockVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDocumentoIndexVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFileEntitaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFolderVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTPagamentoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.services.index.IndexDAO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.Constants;
import it.csi.pbandi.pbservizit.pbandiutil.common.BeanUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.DateUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.LoggerUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.ObjectUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.StringUtil;
import it.csi.pbandi.pbservizit.pbandiutil.common.TimerUtil;
//import it.csi.pbandi.pbweb.dto.archivioFile.UserSpaceDTO;
import it.doqui.index.ecmengine.dto.Node;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * questa classe si pone come obbiettivo di essere un frontend comune per tutta
 * la gestione della interazione db+index per la gestione della persistenza dei
 * documenti
 * 
 * @author 71677
 * 
 */
public class DocumentoManager {
	public interface DocumentFinderClause {

		PbandiTDocumentoIndexVO find();

	}

	private static final int MAX_FORCE_LOCK_TRIES = 10;

	@Autowired
	private LoggerUtil logger;
	
	@Autowired
	private BeanUtil beanUtil;

	private IndexDAO indexDAO;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private DecodificheManager decodificheManager;
	
	@Autowired
	protected DecodificheDAOImpl decodificheDAOImpl;
	
	@Autowired
	private TimerManager timerManager;
	
	@Autowired
	private TimerUtil timerUtil;
	
//	@Autowired
//	private GestioneFileDAOImpl gestioneFileDAOImpl;
	
	private DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager;
	
	public DichiarazioneDiSpesaManager getDichiarazioneDiSpesaManager() {
		return dichiarazioneDiSpesaManager;
	}

	public void setDichiarazioneDiSpesaManager(
			DichiarazioneDiSpesaManager dichiarazioneDiSpesaManager) {
		this.dichiarazioneDiSpesaManager = dichiarazioneDiSpesaManager;
	}

	public static int getMaxForceLockTries() {
		return MAX_FORCE_LOCK_TRIES;
	}

	public static Map<Class<? extends Object>, Class<? extends GenericVO>> getMapDtoDocumentoVoEntita() {
		return MAP_DTO_DOCUMENTO_VO_ENTITA;
	}

	public static Map<Class<? extends Object>, String> getMapDtoTipoDocumentoIndex() {
		return MAP_DTO_TIPO_DOCUMENTO_INDEX;
	}

	public static Map<Class<? extends Object>, String> getMapDtoNomeFile() {
		return MAP_DTO_NOME_FILE;
	}

	public static Map<Class<?>, GeneratoreNomeFile<?>> getMapDtoGeneratoreNomeFile() {
		return MAP_DTO_GENERATORE_NOME_FILE;
	}

	public void setIndexDAO(IndexDAO indexDAO) {
		this.indexDAO = indexDAO;
	}

	public IndexDAO getIndexDAO() {
		return indexDAO;
	}

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

	public TimerManager getTimerManager() {
		return timerManager;
	}

	public void setTimerManager(TimerManager timerManager) {
		this.timerManager = timerManager;
	}

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public void setDecodificheManager(DecodificheManager decodificheManager) {
		this.decodificheManager = decodificheManager;
	}

	public DecodificheManager getDecodificheManager() {
		return decodificheManager;
	}

	public DocumentoDTO caricaDocumento(final BigDecimal idDocumentoIndex)
			throws DocumentNotFoundException {
		return doCaricaDocumento(new DocumentFinderClause() {
			public PbandiTDocumentoIndexVO find() {
				PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = new PbandiTDocumentoIndexVO();
				pbandiTDocumentoIndexVO.setIdDocumentoIndex(idDocumentoIndex);
				return genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
			}
		});
	}

	public DocumentoDTO caricaDocumento(final BigDecimal idTarget,
			final BigDecimal idProgetto,
			final Class<? extends GenericVO> voClass,
			final String codTipoDocIndex) throws DocumentNotFoundException {
		logger.info("\n\n\n\ncaricaDocumento ##########");
		if (idTarget == null) {
			throw new DocumentNotFoundException(
					"Impossibile caricare un documento senza idTarget");
		}

		return doCaricaDocumento(new DocumentFinderClause() {
			public PbandiTDocumentoIndexVO find() {
				return findDocumentoIndexVOUltimaVersione(getIdEntita(voClass),
						idProgetto, idTarget,
						decodificheManager.decodeDescBreve(
								PbandiCTipoDocumentoIndexVO.class,
								codTipoDocIndex));
			}
		});
	}

	private DocumentoDTO doCaricaDocumento(DocumentFinderClause c)
			throws DocumentNotFoundException {
		DocumentoDTO doc = new DocumentoDTO();
		try {
			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = c.find();

			doc.setNomeFile(pbandiTDocumentoIndexVO.getNomeFile());
			doc.setBytesDocumento(indexDAO.recuperaContenuto(pbandiTDocumentoIndexVO.getUuidNodo()));
			doc.setMimeType(indexDAO.recuperaMetadata(pbandiTDocumentoIndexVO.getUuidNodo()).getMimeType());
		} catch (RecordNotFoundException e) {
			String msg = "Documento non trovato su DB: " + e.getMessage();
			logger.error(msg, e);
			throw new DocumentNotFoundException(msg);
		} catch (Exception e) {
			String msg = "Documento non trovato su servizio INDEX: "
					+ e.getMessage();
			logger.error(msg, e);
			throw new DocumentNotFoundException(msg);
		}
		return doc;
	}

	
	
	public PbandiTDocumentoIndexVO getDocumentoIndexSuDb(Long idTarget,
			Long idProgetto, String tipoDocIndex, String nomeEntita) {
		logger.begin();
		BigDecimal idEntitaBigDecimal = getIdEntita(nomeEntita);
		BigDecimal idProgettoBigDecimal = beanUtil.transform(idProgetto,BigDecimal.class);
		BigDecimal idTargetBigDecimal = beanUtil.transform(idTarget,BigDecimal.class);
		BigDecimal idTipoDocumentoIndexBigDecimal = decodificheManager
				.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,
						tipoDocIndex);

		PbandiTDocumentoIndexVO documentoIndexVO;
		try {
			documentoIndexVO = findDocumentoIndexVOUltimaVersione(
					idEntitaBigDecimal, idProgettoBigDecimal,
					idTargetBigDecimal, idTipoDocumentoIndexBigDecimal);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		if(documentoIndexVO!=null)
			logger.info("documentoIndexVO trovato: "+documentoIndexVO.getIdDocumentoIndex());
		logger.end();
		return documentoIndexVO;
	}

	private PbandiTDocumentoIndexVO findDocumentoIndexVOUltimaVersione(
			BigDecimal idEntitaBigDecimal, BigDecimal idProgettoBigDecimal,
			BigDecimal idTargetBigDecimal,
			BigDecimal idTipoDocumentoIndexBigDecimal) {
		DocumentoIndexMaxVersioneVO documentoIndexVO = new DocumentoIndexMaxVersioneVO();
		documentoIndexVO.setIdEntita(idEntitaBigDecimal);
		documentoIndexVO.setIdProgetto(idProgettoBigDecimal);
		documentoIndexVO.setIdTarget(idTargetBigDecimal);
		documentoIndexVO
				.setIdTipoDocumentoIndex(idTipoDocumentoIndexBigDecimal);

		return beanUtil.transform(genericDAO.findSingleWhere(documentoIndexVO),
				PbandiTDocumentoIndexVO.class);
	}

	public void aggiornaInfoNodoIndexSuDb(java.lang.Long idUtente,
			PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO,
			String codStatoTipoDocIndex) throws Exception {


			pbandiTDocumentoIndexVO.setIdUtenteAgg(new BigDecimal(idUtente));
			pbandiTDocumentoIndexVO.setDtAggiornamentoIndex(DateUtil.getSysdate());
			genericDAO.update(pbandiTDocumentoIndexVO);
			if (codStatoTipoDocIndex != null) {
				PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
				pbandiRDocuIndexTipoStatoVO
						.setIdDocumentoIndex(pbandiTDocumentoIndexVO
								.getIdDocumentoIndex());
				pbandiRDocuIndexTipoStatoVO
						.setIdTipoDocumentoIndex(pbandiTDocumentoIndexVO
								.getIdTipoDocumentoIndex());

				BigDecimal idStatoTipoDocumentoIndex = decodificheManager
						.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,
								codStatoTipoDocIndex);
				PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVOEsistente[] = genericDAO
						.findWhere(pbandiRDocuIndexTipoStatoVO);
				if (!ObjectUtil.isEmpty(pbandiRDocuIndexTipoStatoVOEsistente)) {
					pbandiRDocuIndexTipoStatoVO
							.setIdStatoTipoDocIndex(idStatoTipoDocumentoIndex);
					genericDAO.update(pbandiRDocuIndexTipoStatoVOEsistente[0],
							pbandiRDocuIndexTipoStatoVO);
				} else {
					pbandiRDocuIndexTipoStatoVO
							.setIdStatoTipoDocIndex(idStatoTipoDocumentoIndex);
					genericDAO.insert(pbandiRDocuIndexTipoStatoVO);
				}

			}
	}


	public PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDb(Long idUtente,Node nodoCreato,
			String nomeFile, Long idTarget,Long idRappLegale,Long idDelegato, Long idProgetto,
			String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex,String shaHex) {
		return salvaInfoNodoIndexSuDb(idUtente,nodoCreato, nomeFile, idTarget,  idRappLegale,  idDelegato,
				idProgetto, tipoDocIndex, entita, codStatoTipoDocIndex, null,shaHex);
	}

	private PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDb(Long idUtente,Node nodoCreato,
			String nomeFile, Long idTarget,Long idRappLegale,Long idDelegato, Long idProgetto,
			String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex, BigDecimal versione,String shaHex) {
		return salvaInfoNodoIndexSuDb(nodoCreato, nomeFile,
				beanUtil.transform(idTarget, BigDecimal.class),idRappLegale,idDelegato,
				beanUtil.transform(idProgetto, BigDecimal.class), tipoDocIndex,
				entita, codStatoTipoDocIndex, versione,idUtente,shaHex);
	}

	public PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDb(Node nodoCreato,
			String nomeFile, BigDecimal idTarget, Long idRappLegale, Long idDelegato, BigDecimal idProgetto,
			String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex, BigDecimal versione,Long idUtente,String shaHex) {
		
		logger.begin();
		logger.info(" nodoCreato: " + nodoCreato);
		logger.info(" nomeFile: " + nomeFile);
		logger.info(" idTarget: " + idTarget);
		logger.info(" idRappLegale: " + idRappLegale);
		logger.info(" idDelegato: " + idDelegato);
		logger.info(" idProgetto: " + idProgetto);
		logger.info(" tipoDocIndex: " + tipoDocIndex);
		logger.info(" entita: " + entita);
		logger.info(" codStatoTipoDocIndex: " + codStatoTipoDocIndex);
		logger.info(" versione: " + versione);
		logger.info(" shaHex: " + shaHex);
		
		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();

		try {
			BigDecimal idTipoDocumentoIndex = decodificheManager
					.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,	tipoDocIndex);
			
			logger.info(" idTipoDocumentoIndex: " + idTipoDocumentoIndex);
			
			documentoIndexVO.setDtInserimentoIndex(DateUtil.getSysdate());
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdProgetto(idProgetto);
			documentoIndexVO.setIdEntita(getIdEntita(entita));
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setVersione(versione);
			documentoIndexVO.setRepository(Constants.APP_COMPANY_HOME_CM_BANDI);
			documentoIndexVO.setUuidNodo(nodoCreato.getUid());  // PK per lo storage schianto "UUID"
			if(idRappLegale!=null)
				documentoIndexVO.setIdSoggRapprLegale(BigDecimal.valueOf(idRappLegale));
			if(idDelegato!=null)
				documentoIndexVO.setIdSoggDelegato(BigDecimal.valueOf(idDelegato));
			documentoIndexVO.setMessageDigest(shaHex);
			DecodificaDTO statoGenerato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_GENERATO);
			documentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoGenerato.getId()));
			
			if(tipoDocIndex.startsWith("CL"))
				documentoIndexVO.setIdModello(getIdModello(idProgetto,idTipoDocumentoIndex)); // only for checklist
			
			documentoIndexVO.setFlagFirmaCartacea(this.getFlagFirmaCartacea(idTipoDocumentoIndex, idTarget, idProgetto));
			
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			documentoIndexVO = genericDAO.insert(documentoIndexVO);

			logger.info("idDocIndex inserito sul db " 	+ documentoIndexVO.getIdDocumentoIndex());
			//solo per checklist !
			// Jira PBANDI-2856: tolgo il controllo su "D" messo da Chiesa col commit del 11/12/19
			// poiche' non inserire in PbandiRDocuIndexTipoStato impedisce alla ricerca di trovare
			// le checklist in loco bozza. Rimetto quindi il controllo che esisteva prima.
			//if("D".equals(codStatoTipoDocIndex)){
			if(codStatoTipoDocIndex!=null){
				   logger.info("codStatoTipoDocIndex: "+codStatoTipoDocIndex);
					BigDecimal idStatoTipoDocIndex = decodificheManager
							.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,	codStatoTipoDocIndex);
					
					logger.info("idStatoTipoDocIndex: "+idStatoTipoDocIndex);
					
					if (idStatoTipoDocIndex != null) {
						PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
						pbandiRDocuIndexTipoStatoVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
						pbandiRDocuIndexTipoStatoVO.setIdStatoTipoDocIndex(idStatoTipoDocIndex);
						pbandiRDocuIndexTipoStatoVO.setIdTipoDocumentoIndex(beanUtil.transform(idTipoDocumentoIndex, BigDecimal.class));
						genericDAO.insert(pbandiRDocuIndexTipoStatoVO);
					}
			}else{
				logger.info(" codStatoTipoDocIndex null , non e' una checklist");
			}

		} catch (Exception e) {
			String message = "Impossibile inserire il documento:" + e.getMessage();
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}  
		logger.end();
		return documentoIndexVO;
	}
	
	private String getFlagFirmaCartacea(BigDecimal idTipoDocumentoIndex, BigDecimal idTarget, BigDecimal idProgetto) {
		logger.info("getFlagFirmaCartacea(): idTipoDocumentoIndex = "+idTipoDocumentoIndex+"; idTarget = "+idTarget);
		if (idTipoDocumentoIndex != null && idTipoDocumentoIndex.intValue() == 1) {
			// Si tratta di Ducumento Di Spesa.
			PbandiTDichiarazioneSpesaVO vo = new PbandiTDichiarazioneSpesaVO();
			vo.setIdDichiarazioneSpesa(idTarget);
			vo = genericDAO.findSingleOrNoneWhere(vo);
			if (vo != null) {
				String tipoInvioDs = vo.getTipoInvioDs();
				logger.info("getFlagFirmaCartacea(): tipoInvioDs = "+tipoInvioDs);
				if (Constants.TIPO_INVIO_DS_CARTACEO.equalsIgnoreCase(tipoInvioDs)) {
					return "S";
				}
			}
		}
		if (idTipoDocumentoIndex != null && idTipoDocumentoIndex.intValue() == 17) {
			// Si tratta di Comunicazione di fine Progetto.
			DichiarazioneDiSpesaConTipoVO vo = dichiarazioneDiSpesaManager.getDichiarazioneFinaleConComunicazione(idProgetto.longValue());
			if (vo == null) {
				logger.info("DichiarazioneFinale non trovata per idProgetto = "+idProgetto);
				return null;
			}
			String tipoInvioDs = vo.getTipoInvioDs();
			logger.info("getFlagFirmaCartacea(): tipoInvioDs = "+tipoInvioDs);
			if (Constants.TIPO_INVIO_DS_CARTACEO.equalsIgnoreCase(tipoInvioDs)) {
				return "S";
			}
		}
		return null;
	}

	public BigDecimal getIdModello(BigDecimal idProgetto,
			BigDecimal idTipoDocumentoIndex) {
		PbandiRTpDocIndBanLiIntVO tpDocIndBanLiIntVO = new PbandiRTpDocIndBanLiIntVO();
		tpDocIndBanLiIntVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);

		BandoLineaProgettoVO bandoLineaProgettoVO = new BandoLineaProgettoVO();
		bandoLineaProgettoVO.setIdProgetto(idProgetto);

		List<Pair<GenericVO, PbandiRTpDocIndBanLiIntVO, BandoLineaProgettoVO>> list = genericDAO
				.join(Condition.filterBy(tpDocIndBanLiIntVO),
						Condition.filterBy(bandoLineaProgettoVO))
				.by("progrBandoLineaIntervento").select();

		BigDecimal idModello = list.size() == 1 ? list.get(0).getFirst()
				.getIdModello() : null;

		return idModello;
	}

	public String getDescBreveStatoDocumento(BigDecimal idDocumentoIndex) {
		PbandiRDocuIndexTipoStatoVO tipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
		tipoStatoVO.setIdDocumentoIndex(idDocumentoIndex);
		List<PbandiRDocuIndexTipoStatoVO> tipoStatoVOs = genericDAO
				.findListWhere(new FilterCondition<PbandiRDocuIndexTipoStatoVO>(
						tipoStatoVO));
		if (tipoStatoVOs.size() > 0) {
			PbandiDStatoTipoDocIndexVO statoTipoDocIndexVO = new PbandiDStatoTipoDocIndexVO();
			statoTipoDocIndexVO.setIdStatoTipoDocIndex(tipoStatoVOs.get(0)
					.getIdStatoTipoDocIndex());
			statoTipoDocIndexVO = genericDAO
					.findSingleWhere(statoTipoDocIndexVO);
			return statoTipoDocIndexVO.getDescBreveStatoTipDocIndex();
		} else {
			return "";
		}
	}

	private BigDecimal getIdEntita(String nomeEntita) {
		PbandiCEntitaVO entitaVO = new PbandiCEntitaVO();
		entitaVO.setNomeEntita(nomeEntita);

		BigDecimal idEntita = null;
		try {
			idEntita = getGenericDAO().findSingleWhere(entitaVO).getIdEntita();
			logger.debug(nomeEntita + " -> " + idEntita);
		} catch (RecordNotFoundException e) {
			// FIXME insomma, la runtime non � bella...
			throw new RuntimeException("Non trovato id per codice "
					+ nomeEntita);
		}

		return idEntita;
	}

	public BigDecimal getIdEntita(Class<? extends GenericVO> voClass) {
		return getIdEntita(GenericVO.getTableNameForValueObject(voClass));
	}

	public boolean isLockDocumentoScaduto(Long idUtente, Long idProgetto,
			Long idTarget, Class<? extends GenericVO> voClass,
			String codTipoDocIndex) {
		boolean result = getLockDocumentoScaduto(idUtente, idProgetto,
				idTarget, voClass, codTipoDocIndex) != null;
		return result;
	}

	public boolean isLockDocumentoScaduto(Long idProgetto, Long idTarget,
			Class<? extends GenericVO> voClass, String codTipoDocIndex) {
		return isLockDocumentoScaduto(null, idProgetto, idTarget, voClass,
				codTipoDocIndex);
	}

	private PbandiTDocumentoIndexLockVO getLockDocumentoScaduto(
			Long idProgetto, Long idTarget, Class<? extends GenericVO> voClass,
			String codTipoDocIndex) {
		return getLockDocumentoScaduto(null, idProgetto, idTarget, voClass,
				codTipoDocIndex);
	}

	private PbandiTDocumentoIndexLockVO getLockDocumentoScaduto(Long idUtente,
			Long idProgetto, Long idTarget, Class<? extends GenericVO> voClass,
			String codTipoDocIndex) {
		logger.begin();
		PbandiTDocumentoIndexLockVO pbandiTDocumentoIndexLockVO = creaIstanzaTDocumentoIndexLockVO(
				beanUtil.transform(idProgetto, BigDecimal.class),
				beanUtil.transform(idTarget, BigDecimal.class), voClass,
				codTipoDocIndex);
		pbandiTDocumentoIndexLockVO.setIdUtente(beanUtil.transform(idUtente,
				BigDecimal.class));

		List<PbandiTDocumentoIndexLockVO> pbandiTDocumentoIndexLockVOs = genericDAO
				.findListWhere(Condition.filterBy(pbandiTDocumentoIndexLockVO));

		PbandiTDocumentoIndexLockVO result = new PbandiTDocumentoIndexLockVO();
		if (pbandiTDocumentoIndexLockVOs.size() > 0) {
			result = pbandiTDocumentoIndexLockVOs.get(0);
			logger.dump(result);

			Timestamp timestampLock = DateUtil.utilToSqlTimeStamp(result
					.getDtLockDocumento());

			if (getTimerUtil().isTimeoutNotOccurred(
					getDurataLockInMillisecondi(), timestampLock.getTime())) {
				logger.debug("Il lock NON e' scaduto.");
				result = null;
			}
		}
		logger.end();
		return result;
	}

	private PbandiTDocumentoIndexLockVO creaIstanzaTDocumentoIndexLockVO(
			BigDecimal idProgetto, BigDecimal idTarget,
			Class<? extends GenericVO> voClass, String codTipoDocIndex) {
		logger.begin();
		PbandiTDocumentoIndexLockVO pbandiTDocumentoIndexLockVO = new PbandiTDocumentoIndexLockVO();
		pbandiTDocumentoIndexLockVO.setIdTarget(idTarget);
		pbandiTDocumentoIndexLockVO.setIdTipoDocumentoIndex(decodificheManager
				.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,	codTipoDocIndex));
		pbandiTDocumentoIndexLockVO.setIdProgetto(idProgetto);
		pbandiTDocumentoIndexLockVO.setIdEntita(getIdEntita(voClass));
		logger.end();
		return pbandiTDocumentoIndexLockVO;
	}

	public boolean lockDocument(Long idUtente, Long idProgetto, Long idTarget,
			String codTipoDocIndex, Class<? extends GenericVO> voClass)
			throws Exception {
		boolean success = false;

		if (!insertLock(idUtente, idProgetto, idTarget, codTipoDocIndex,
				voClass)) {
			PbandiTDocumentoIndexLockVO documentoIndexLockVO = creaIstanzaTDocumentoIndexLockVO(
					beanUtil.transform(idProgetto, BigDecimal.class),
					beanUtil.transform(idTarget, BigDecimal.class), voClass,
					codTipoDocIndex);
			documentoIndexLockVO.setIdUtente(beanUtil.transform(idUtente,
					BigDecimal.class));

			if (!updateLock(idUtente, documentoIndexLockVO)) {
				documentoIndexLockVO = getLockDocumentoScaduto(idProgetto,
						idTarget, voClass, codTipoDocIndex);
				if (documentoIndexLockVO != null
						&& new BigDecimal(idProgetto)
								.equals(documentoIndexLockVO.getIdProgetto())) {
					success = updateLock(idUtente, documentoIndexLockVO);
				} else {
					logger.debug("Altro lock presente.");
				}
			} else {
				success = true;
			}
		} else {
			success = true;
		}

		if (success) {
			logger.debug("Documento loccato con successo.");
		} else {
			logger.debug("Impossbile ottenere il lock.");
		}

		return success;
	}

	private boolean updateLock(Long idUtente,
			PbandiTDocumentoIndexLockVO documentoIndexLockVO) {
		PbandiTDocumentoIndexLockVO newValue = new PbandiTDocumentoIndexLockVO();
		newValue.setIdUtente(beanUtil.transform(idUtente, BigDecimal.class));
		newValue.setDtLockDocumento(DateUtil.getSysdate());

		logger.dump(documentoIndexLockVO);

		boolean success = false;
		try {
			// FIXME perch� funzioni bene ci vorrebbe anche la data, ma c'� un
			// cattivissimo bug nel generic dao per cui l'uguaglianza tra date
			// con ora non � gestita
			documentoIndexLockVO.setDtLockDocumento(null);

			if (genericDAO.update(documentoIndexLockVO, newValue)) {
				success = true;
			}
		} catch (Exception e) {
			logger.warn("Anomalia nell'aggiornamento del lock: "
					+ e.getMessage());
		}
		return success;
	}

	public void forceLockDocument(Long idUtente, Long idProgetto,
			Long idTarget, Class<? extends GenericVO> voClass,
			String codTipoDocIndex) {
		logger.begin();

		PbandiTDocumentoIndexLockVO documentoIndexLockVO = creaIstanzaTDocumentoIndexLockVO(
				beanUtil.transform(idProgetto, BigDecimal.class),
				beanUtil.transform(idTarget, BigDecimal.class), voClass,
				codTipoDocIndex);

		int tryNum = 1;
		while (!updateLock(idUtente, documentoIndexLockVO)
				&& !insertLock(idUtente, idProgetto, idTarget, codTipoDocIndex,
						voClass) && tryNum < MAX_FORCE_LOCK_TRIES) {
			logger.warn("Impossibile forzare l'acquisizione del lock, tentativo: "
					+ tryNum);
			tryNum++;
		}

		logger.end();
	}

	public boolean eliminaLock(BigDecimal idUtente, BigDecimal idProgetto,
			BigDecimal idTarget, Class<? extends GenericVO> voClass,
			String codTipoDocIndex) throws Exception {
		PbandiTDocumentoIndexLockVO documentoIndexLockVO = creaIstanzaTDocumentoIndexLockVO(
				idProgetto, idTarget, voClass, codTipoDocIndex);

		documentoIndexLockVO.setIdUtente(idUtente);
		return genericDAO.delete(documentoIndexLockVO);
	}

	private boolean insertLock(Long idUtente, Long idProgetto, Long idTarget,
			String codTipoDocIndex, Class<? extends GenericVO> voClass) {
		boolean lockPresente = false;
		PbandiTDocumentoIndexLockVO documentoIndexLockVO = creaIstanzaTDocumentoIndexLockVO(
				beanUtil.transform(idProgetto, BigDecimal.class),
				beanUtil.transform(idTarget, BigDecimal.class), voClass,
				codTipoDocIndex);

		documentoIndexLockVO.setDtLockDocumento(new java.sql.Date(DateUtil
				.utilToSqlTimeStamp(getTimerManager().start()).getTime()));
		documentoIndexLockVO.setIdUtente(new BigDecimal(idUtente));

		try {
			genericDAO.insert(documentoIndexLockVO);
			logger.debug("Lock inserito.");
			lockPresente = true;
		} catch (Exception e) {
			logger.debug("Impossibile inserire lock (suppongo esista gi�).");
		}

		logger.dump(documentoIndexLockVO);
		return lockPresente;
	}

	public void eliminaDocumento(BigDecimal idUtente,
			BigDecimal idDocumentoIndex) throws Exception {
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO = new PbandiTDocumentoIndexVO();
		pbandiTDocumentoIndexVO.setIdDocumentoIndex(idDocumentoIndex);
		pbandiTDocumentoIndexVO = genericDAO
				.findSingleWhere(pbandiTDocumentoIndexVO);

		PbandiTDocumentoIndexLockVO lockVO = beanUtil.transform(
				pbandiTDocumentoIndexVO, PbandiTDocumentoIndexLockVO.class);
		lockVO.setIdUtente(idUtente);

		genericDAO.deleteWhere(Condition.filterBy(lockVO));
		long maxTimeLock=1800000;
		boolean lockNonPresente=false;
		lockVO.setIdUtente(null);
		List<PbandiTDocumentoIndexLockVO> locks = genericDAO.findListWhere(lockVO);
		if(ObjectUtil.isEmpty(locks)) {
			lockNonPresente=true;
		}else{
			for (PbandiTDocumentoIndexLockVO pbandiTDocumentoIndexLockVO : locks) {
				java.sql.Date dtLockDocumento = pbandiTDocumentoIndexLockVO.getDtLockDocumento();
				long timeLock= dtLockDocumento.getTime();
				long actualTime=System.currentTimeMillis();
				logger.info("\n\n\n\n\n\n\n\n actualTime-timeLock= "+(actualTime-timeLock));
			
				if((actualTime-timeLock)<maxTimeLock){
					lockNonPresente=true;
				}
			}
		}
	

		if (lockNonPresente) {
			PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
			pbandiRDocuIndexTipoStatoVO.setIdDocumentoIndex(idDocumentoIndex);
			pbandiRDocuIndexTipoStatoVO
					.setIdTipoDocumentoIndex(pbandiTDocumentoIndexVO
							.getIdTipoDocumentoIndex());
			genericDAO
					.deleteWhere(new FilterCondition<PbandiRDocuIndexTipoStatoVO>(
							pbandiRDocuIndexTipoStatoVO));

			genericDAO.delete(pbandiTDocumentoIndexVO);

			//PK : commentato per index
//			indexDAO.cancellaNodo(new Node(pbandiTDocumentoIndexVO	.getUuidNodo()));
		} else {
			throw new Exception(
					"Impossibile eliminare il documento, lock presente.");
		}
	}

 

	
	public void disassociateArchivioFileAndNullNumProtocollo(BigDecimal idUtente,
			BigDecimal idDocumentoIndex,BigDecimal idFolder) throws Exception {
		PbandiTDocumentoIndexVO pbandiTDocumentoIndexVO=new PbandiTDocumentoIndexVO(idDocumentoIndex);
		pbandiTDocumentoIndexVO = genericDAO.findSingleWhere(pbandiTDocumentoIndexVO);
		pbandiTDocumentoIndexVO.setIdUtenteAgg( idUtente);
		pbandiTDocumentoIndexVO.setIdTarget(idFolder);			 
		pbandiTDocumentoIndexVO.setNumProtocollo(null);
		pbandiTDocumentoIndexVO.setIdEntita(getIdEntita(PbandiTFolderVO.class));
		logger.info("trying to disassociate  idDocumentoIndex "+idDocumentoIndex +" with idTarget "+idFolder);
		genericDAO.updateNullables(pbandiTDocumentoIndexVO);
		
	}

	public void aggiornaDocumento(Object dto, Long idUtente,
			PbandiTDocumentoIndexVO documentoIndexVO,
			String codStatoTipoDocIndex) throws Exception {
		logger.begin();
		logger.shallowDump(dto, "info");
		getIndexDAO().aggiornaContent(dto, idUtente);

		aggiornaInfoNodoIndexSuDb(idUtente, documentoIndexVO,
				codStatoTipoDocIndex);
	}

	public static final Map<Class<? extends Object>, Class<? extends GenericVO>> MAP_DTO_DOCUMENTO_VO_ENTITA = Collections
			.unmodifiableMap(new HashMap<Class<? extends Object>, Class<? extends GenericVO>>() {
				{
					this.put(AllegatoCheckListInLocoDTO.class,
							PbandiTChecklistVO.class);
					this.put(CheckListInLocoDTO.class, PbandiTChecklistVO.class);
					this.put(CheckListDocumentaleDTO.class,PbandiTDichiarazioneSpesaVO.class);

					this.put(AllegatoRelazioneTecnicaDTO.class,
							PbandiTDichiarazioneSpesaVO.class);
					this.put(ComunicazioneFineProgettoDTO.class,
							PbandiTComunicazFineProgVO.class);
					
					this.put(CheckListAffidamentoDocumentaleDTO.class, PbandiTAppaltoVO.class);
					
					this.put(CheckListAffidamentoInLocoDTO.class, PbandiTAppaltoVO.class);
					
					this.put(ReportCampionamentoDTO.class, PbandiTCampionamentoVO.class);
					
				}
			});

	public static final Map<Class<? extends Object>, String> MAP_DTO_TIPO_DOCUMENTO_INDEX = Collections
			.unmodifiableMap(new HashMap<Class<? extends Object>, String>() {
				{
					this.put(
							CheckListInLocoDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_IN_LOCO);
					this.put(
							AllegatoCheckListInLocoDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_VERBALE_CONTROLLO_VALIDAZIONE);
					this.put(
							CheckListDocumentaleDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_DOCUMENTALE);
					this.put(
							AllegatoRelazioneTecnicaDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_RELAZIONE_TECNICA);
					this.put(
							ComunicazioneFineProgettoDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_COMUNICAZIONE_FINE_PROGETTO);
					this.put(
							CheckListAffidamentoDocumentaleDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_AFFIDAMENTO_VALIDAZIONE);					
					this.put(
							CheckListAffidamentoInLocoDTO.class,
							GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_CHECKLIST_AFFIDAMENTO_IN_LOCO);
					
					this.put(ReportCampionamentoDTO.class, GestioneDatiDiDominioSrv.COD_TIPO_DOCUMENTO_INDEX_FILE_CAMPIONAMENTO_CERT);
				}
			});

	
	
	public static final Map<Class<? extends Object>, String> MAP_DTO_NOME_FILE = Collections
			.unmodifiableMap(new HashMap<Class<? extends Object>, String>() {
				{
					this.put(CheckListInLocoDTO.class, "checkList.pdf");
					this.put(AllegatoCheckListInLocoDTO.class,
							"verbaleChecklist.pdf");
				}
			});

	private static final Map<Class<?>, GeneratoreNomeFile<?>> MAP_DTO_GENERATORE_NOME_FILE = new HashMap<Class<?>, GeneratoreNomeFile<?>>();

	static {
		MAP_DTO_GENERATORE_NOME_FILE.put(CheckListDocumentaleDTO.class,
				new GeneratoreNomeFile<CheckListDocumentaleDTO>() {

					public String generaNomeFile(CheckListDocumentaleDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return "checkList_" + dto.getIdDichiarazioneDiSpesa()
								+ "_V" + dto.getVersione() + "_" + time
								+ ".pdf";
					}
				});
		MAP_DTO_GENERATORE_NOME_FILE.put(ComunicazioneFineProgettoDTO.class,
				new GeneratoreNomeFile<ComunicazioneFineProgettoDTO>() {

					public String generaNomeFile(ComunicazioneFineProgettoDTO dto) {
						String time = DateUtil.getTime(dto.getDtComunicazione(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return "ComunicazioneDiFineProgetto_" + dto.getIdComunicazFineProg()
								+ "_" + time
								+ ".pdf";
					}
				});
		/*   Jira Pbandi-2859
		MAP_DTO_GENERATORE_NOME_FILE.put(CheckListAffidamentoDocumentaleDTO.class,
				new GeneratoreNomeFile<CheckListAffidamentoDocumentaleDTO>() {

					public String generaNomeFile(CheckListAffidamentoDocumentaleDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return "checkListAffidamento_" + dto.getIdAppalto()
								+ "_V" + dto.getVersione() + "_" + time
								+ ".pdf";
					}
				});
		*/		
		MAP_DTO_GENERATORE_NOME_FILE.put(CheckListAffidamentoInLocoDTO.class,
				new GeneratoreNomeFile<CheckListAffidamentoInLocoDTO>() {

					public String generaNomeFile(CheckListAffidamentoInLocoDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return "checkList_" + dto.getIdAffidamento()
								+ "_" + time
								+ ".pdf";
					}
				});
		
		MAP_DTO_GENERATORE_NOME_FILE.put(ReportCampionamentoDTO.class,
				new GeneratoreNomeFile<ReportCampionamentoDTO>() {

					public String generaNomeFile(ReportCampionamentoDTO dto) {
						String time = DateUtil.getTime(new java.util.Date(),
								Constants.DATEHOUR_FORMAT_PER_NOME_FILE);

						return dto.getTemplateName()
								+ "_" + time
								+ ".xls";
					}
				});
	}

	private static Class<? extends GenericVO> getEntitaPerDocumento(
			Class<? extends Object> dtoClass) {
		return MAP_DTO_DOCUMENTO_VO_ENTITA.get(dtoClass);
	}

	private static String getTipoDocIndex(Class<? extends Object> dtoClass) {
		return MAP_DTO_TIPO_DOCUMENTO_INDEX.get(dtoClass);
	}

	@SuppressWarnings("static-access")
	public PbandiTDocumentoIndexVO creaDocumento(Long idUtente, Object dto,
			String codStatoTipoDocIndex, String shaHex, Long idRappLegale, Long idDelegato) throws DocumentNotCreatedException {
		logger.begin();
		try {
			String nomeFile = getNomeFile(dto);			
			beanUtil.setPropertyValueByName(dto, "nomeFile", nomeFile);
			logger.info(" nomeFile = "+nomeFile);
			logger.info(" codStatoTipoDocIndex = "+codStatoTipoDocIndex);
			
//			FileDTO f = new FileDTO();
			
			
			if("7".equals(codStatoTipoDocIndex)){
				EsitoSalvaModuloCheckListDTO DTO = (EsitoSalvaModuloCheckListDTO) dto;
				logger.info("DTO.getIdChecklist : " + DTO.getIdChecklist());
				logger.info("DTO.getIdDocumentoIndex: " + DTO.getIdDocumentoIndex());
				logger.info("DTO.getByteModulo() length: " + (DTO.getByteModulo() != null ? DTO.getByteModulo().length : "niente"));
				logger.info("DTO.getEsito: " + DTO.getEsito());
				nomeFile += codStatoTipoDocIndex;

//				f.setBytes(DTO.getByteModulo());
//				f.setIdDocumentoIndex(DTO.getIdDocumentoIndex()); //TODO PK ??
				
//			} else {
//				f.setBytes(getBytes(dto));
//				f.setIdDocumentoIndex(0L); //TODO PK ??
			}
			
//			f.setNomeFile(nomeFile);
			
//			Long idFolder = 0L; //TODO PK : ha senso calcolare lo spazio per le checklist??
//			Long idSoggettoBeneficiario = 0; //TODO PK : ha senso calcolare lo spazio per le checklist??
//
//			it.csi.pbandi.pbservizit.integration.vo.PbandiTDocumentoIndexVO doc = gestioneFileDAOImpl.salvaFileCL(idUtente, f);
//
//			return new PbandiTDocumentoIndexVO(
//					doc.getIdProgetto(), 
//					doc.getIdModello(),
//					doc.getNumProtocollo(),
//					doc.getIdSoggDelegato(),
//					doc.getIdDocumentoIndex(),
//					doc.getIdSoggRapprLegale(),
//					doc.getDtVerificaFirma(),
//					doc.getIdStatoDocumento(),
//					doc.getVersione(),
//					doc.getDtAggiornamentoIndex(),
//					doc.getIdUtenteIns(),
//					doc.getIdTipoDocumentoIndex(),
//					doc.getMessageDigest(),
//					doc.getIdTarget(),
//					doc.getRepository(),
//					doc.getFlagFirmaCartacea(),
//					doc.getNomeFile(),
//					doc.getUuidNodo(),
//					doc.getNoteDocumentoIndex(),
//					doc.getDtMarcaTemporale(),
//					doc.getIdUtenteIns(),
//					doc.getDtInserimentoIndex(),
//					doc.getIdEntita(),
//					doc.getIdCategAnagraficaMitt()
//					);
			
			
			Node node = getIndexDAO().creaContent(dto, idUtente);
			Class<? extends GenericVO> classeEntita = getEntitaPerDocumento(dto.getClass());
			logger.info("classeEntita= "+classeEntita);
//			
//			//PK : aggiornamento PBANDI_T_DOCUMENTO_INDEX e PBANDI_R_DOCU_INDEX_TIPO_STATO
			return salvaInfoNodoIndexSuDb(node, 
										  nomeFile,
										  beanUtil.getPropertyValueByName(dto,getNomeProprietaChiave(classeEntita),	BigDecimal.class), 
										  idRappLegale, 
										  idDelegato, 
										  beanUtil.getPropertyValueByName( dto, "idProgetto", BigDecimal.class),
										  getTipoDocIndex(dto.getClass()), 
										  classeEntita, 
										  codStatoTipoDocIndex, 
										  beanUtil.getPropertyValueByName(dto,"versione", BigDecimal.class),
										  idUtente,
										  shaHex);
		} catch (Exception e) {
			throw new DocumentNotCreatedException(e.getMessage());
		}finally {
			logger.end();
		}

	}
 
	private String getNomeProprietaChiave(Class<? extends GenericVO> classeEntita)
			throws InstantiationException, IllegalAccessException {
		return (String) classeEntita.newInstance().getPK().get(0);
	}

	private interface GeneratoreNomeFile<T> {

		String generaNomeFile(T dto);
	};

	public <T> String getNomeFile(T dto) {
		logger.dump(dto);

		String nomeFileFromDTO = beanUtil.getPropertyValueByName(dto,
				"nomeFile", String.class);

		String nomeFile = "nomeFileGenerico.bin";
		if (!StringUtil.isEmpty(nomeFileFromDTO)) {
			logger.debug("nome file preso dal dto");
			nomeFile = nomeFileFromDTO;
		} else {
			@SuppressWarnings("unchecked")
			GeneratoreNomeFile<T> generatoreNomeFile = (GeneratoreNomeFile<T>) MAP_DTO_GENERATORE_NOME_FILE
					.get(dto.getClass());
			if (generatoreNomeFile != null) {
				nomeFile = generatoreNomeFile.generaNomeFile(dto);
			} else {
				String baseName = MAP_DTO_NOME_FILE.get(dto.getClass());
				if (baseName == null) {
					baseName = nomeFile;
				}

				nomeFile = baseName
						.replace(
								".",
								("_"
										+ beanUtil.getPropertyValueByName(dto,
												"idProgetto", String.class)
										+ "_" + DateUtil
										.getTime(
												new java.util.Date(),
												Constants.DATEHOUR_FORMAT_PER_NOME_FILE))
										+ ".");
			}
		}

		logger.info("nome file: " + nomeFile);
		return nomeFile;
	}

	public String getStatoDocumento(Object dto) throws Exception {
		Class<? extends GenericVO> classeEntita = getEntitaPerDocumento(dto
				.getClass());
		return getDescBreveStatoDocumento(findDocumentoIndexVOUltimaVersione(
				getIdEntita(classeEntita),
				beanUtil.getPropertyValueByName(dto, "idProgetto",
						BigDecimal.class),
				beanUtil.getPropertyValueByName(dto,
						getNomeProprietaChiave(classeEntita), BigDecimal.class),
				decodificheManager.decodeDescBreve(
						PbandiCTipoDocumentoIndexVO.class,
						getTipoDocIndex(dto.getClass()))).getIdDocumentoIndex());
	}

	public Long getDurataLockInMillisecondi() {
		Long timeInMilliSeconds = beanUtil.transform(beanUtil.transform(
				getTimerManager()
						.getTimeout("serverConfiguration.documentLock"),
				String.class), Long.class);
		return timeInMilliSeconds;
	}

	public void setTimerUtil(TimerUtil timerUtil) {
		this.timerUtil = timerUtil;
	}

	public TimerUtil getTimerUtil() {
		return timerUtil;
	}
	
	public Long getIdTipoDocIndex(String descBreveTipoDoc){
		DecodificaDTO tipoDocumentoIndex = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.TIPO_DOCUMENTO_INDEX, descBreveTipoDoc);
		if (tipoDocumentoIndex == null)
			return null;
		else
			return tipoDocumentoIndex.getId();
	}

	public List<DocumentoDiSpesaDichiarazioneTotalePagamentiVO> findDocumentiDichiarazione(
			BigDecimal idDichiarazione) {

		DocumentoDiSpesaDichiarazioneTotalePagamentiVO vo = new DocumentoDiSpesaDichiarazioneTotalePagamentiVO();
		vo.setIdDichiarazioneSpesa(idDichiarazione);

		return genericDAO.findListWhere(vo);
	}

	public void disassociateArchivioFileAssociatedDocDiSpesa(long idUtente,long idDocumentoDiSpesa,long idProgetto) throws Exception {
		logger.info("tryng to disassociate file to idDocumentoDiSpesa "+idDocumentoDiSpesa+" idProgetto:"+idProgetto);
		PbandiTFileEntitaVO pbandiRFileEntitaVO=new PbandiTFileEntitaVO();
		pbandiRFileEntitaVO.setIdEntita(getIdEntita(PbandiTDocumentoDiSpesaVO.class));
		pbandiRFileEntitaVO.setIdTarget(BigDecimal.valueOf(idDocumentoDiSpesa));
		pbandiRFileEntitaVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		Boolean delete = genericDAO.deleteWhere(Condition.filterBy( pbandiRFileEntitaVO));
		logger.info("record delete from PbandiRFileEntita: "+delete);
	}

	 
	
	public void disassociateArchivioFileAssociatedPagamento(Long idUtente,Long idPagamento ) throws Exception {
	 
		logger.info("tryng to disassociate file to idPagamento "+idPagamento);
		PbandiTFileEntitaVO pbandiRFileEntitaVO=new PbandiTFileEntitaVO();
		pbandiRFileEntitaVO.setIdEntita(getIdEntita(PbandiTPagamentoVO.class));
		pbandiRFileEntitaVO.setIdTarget(BigDecimal.valueOf(idPagamento));
	//	pbandiRFileEntitaVO.setIdProgetto(BigDecimal.valueOf(idProgetto));
		Boolean delete = genericDAO.deleteWhere(Condition.filterBy(pbandiRFileEntitaVO));
		logger.info("record delete from PbandiRFileEntita: "+delete);
	}
	
 
	public DocumentoDTO getDocumento(String uidNodo)
			throws DocumentNotFoundException {
		DocumentoDTO doc = new DocumentoDTO();
		try {
			doc.setBytesDocumento(indexDAO.recuperaContenuto(uidNodo));
			doc.setMimeType(indexDAO.recuperaMetadata(uidNodo).getMimeType());
		} catch (RecordNotFoundException e) {
			String msg = "Documento non trovato su DB: " + e.getMessage();
			logger.error(msg, e);
			throw new DocumentNotFoundException(msg);
		} catch (Exception e) {
			String msg = "Documento non trovato su servizio INDEX: "
					+ e.getMessage();
			logger.error(msg, e);
			throw new DocumentNotFoundException(msg);
		}
		return doc;
	}

	// **************************************************************************************************
	// INIZIO Metodi duplicati per poter usare salvaInfoNodoIndexSuDb senza l'insert su PbandiTDocumentoIndex.
	// **************************************************************************************************
	
	public PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDbSenzaInsert(Long idUtente,Node nodoCreato,
			String nomeFile, Long idTarget,Long idRappLegale,Long idDelegato, Long idProgetto,
			String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex,String shaHex) {
		return salvaInfoNodoIndexSuDbSenzaInsert(idUtente,nodoCreato, nomeFile, idTarget,  idRappLegale,  idDelegato,
				idProgetto, tipoDocIndex, entita, codStatoTipoDocIndex, null,shaHex);
	}

	private PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDbSenzaInsert(Long idUtente,Node nodoCreato,
			String nomeFile, Long idTarget,Long idRappLegale,Long idDelegato, Long idProgetto,
			String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex, BigDecimal versione,String shaHex) {
		return salvaInfoNodoIndexSuDbSenzaInsert(nodoCreato, nomeFile,
				beanUtil.transform(idTarget, BigDecimal.class),idRappLegale,idDelegato,
				beanUtil.transform(idProgetto, BigDecimal.class), tipoDocIndex,
				entita, codStatoTipoDocIndex, versione,idUtente,shaHex);
	}

	public PbandiTDocumentoIndexVO salvaInfoNodoIndexSuDbSenzaInsert(Node nodoCreato,
			String nomeFile, BigDecimal idTarget, Long idRappLegale, Long idDelegato, BigDecimal idProgetto,
			String tipoDocIndex, Class<? extends GenericVO> entita,
			String codStatoTipoDocIndex, BigDecimal versione,Long idUtente,String shaHex) {
		
		logger.begin();
		logger.info(" nodoCreato: " + nodoCreato);
		logger.info(" nomeFile: " + nomeFile);
		logger.info(" idTarget: " + idTarget);
		logger.info(" idRappLegale: " + idRappLegale);
		logger.info(" idDelegato: " + idDelegato);
		logger.info(" idProgetto: " + idProgetto);
		logger.info(" tipoDocIndex: " + tipoDocIndex);
		logger.info(" entita: " + entita);
		logger.info(" codStatoTipoDocIndex: " + codStatoTipoDocIndex);
		logger.info(" versione: " + versione);
		logger.info(" shaHex: " + shaHex);
		
		PbandiTDocumentoIndexVO documentoIndexVO = new PbandiTDocumentoIndexVO();

		try {
			BigDecimal idTipoDocumentoIndex = decodificheManager
					.decodeDescBreve(PbandiCTipoDocumentoIndexVO.class,	tipoDocIndex);
			
			logger.info(" idTipoDocumentoIndex: " + idTipoDocumentoIndex);
			
			documentoIndexVO.setDtInserimentoIndex(DateUtil.getSysdate());
			documentoIndexVO.setNomeFile(nomeFile);
			documentoIndexVO.setIdProgetto(idProgetto);
			documentoIndexVO.setIdEntita(getIdEntita(entita));
			documentoIndexVO.setIdTarget(idTarget);
			documentoIndexVO.setIdTipoDocumentoIndex(idTipoDocumentoIndex);
			documentoIndexVO.setVersione(versione);
			documentoIndexVO.setRepository(Constants.APP_COMPANY_HOME_CM_BANDI);
			documentoIndexVO.setUuidNodo(nodoCreato.getUid());  // PK per lo storage schianto "UUID"
			if(idRappLegale!=null)
				documentoIndexVO.setIdSoggRapprLegale(BigDecimal.valueOf(idRappLegale));
			if(idDelegato!=null)
				documentoIndexVO.setIdSoggDelegato(BigDecimal.valueOf(idDelegato));
			documentoIndexVO.setMessageDigest(shaHex);
			DecodificaDTO statoGenerato = decodificheManager.findDecodifica(
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX,
					GestioneDatiDiDominioSrv.STATO_DOCUMENTO_INDEX_GENERATO);
			documentoIndexVO.setIdStatoDocumento(BigDecimal.valueOf(statoGenerato.getId()));
			
			if(tipoDocIndex.startsWith("CL"))
				documentoIndexVO.setIdModello(getIdModello(idProgetto,idTipoDocumentoIndex)); // only for checklist
			
			documentoIndexVO.setFlagFirmaCartacea(this.getFlagFirmaCartacea(idTipoDocumentoIndex, idTarget, idProgetto));
			
			documentoIndexVO.setIdUtenteIns(new BigDecimal(idUtente));
			
			/////documentoIndexVO = genericDAO.insert(documentoIndexVO);

			logger.info("idDocIndex inserito sul db " 	+ documentoIndexVO.getIdDocumentoIndex());
			//solo per checklist !
			// Jira PBANDI-2856: tolgo il controllo su "D" messo da Chiesa col commit del 11/12/19
			// poiche' non inserire in PbandiRDocuIndexTipoStato impedisce alla ricerca di trovare
			// le checklist in loco bozza. Rimetto quindi il controllo che esisteva prima.
			//if("D".equals(codStatoTipoDocIndex)){
			if(codStatoTipoDocIndex!=null){
				   logger.info("codStatoTipoDocIndex: "+codStatoTipoDocIndex);
					BigDecimal idStatoTipoDocIndex = decodificheManager
							.decodeDescBreve(PbandiDStatoTipoDocIndexVO.class,	codStatoTipoDocIndex);
					
					logger.info("idStatoTipoDocIndex: "+idStatoTipoDocIndex);
					
					if (idStatoTipoDocIndex != null) {
						PbandiRDocuIndexTipoStatoVO pbandiRDocuIndexTipoStatoVO = new PbandiRDocuIndexTipoStatoVO();
						pbandiRDocuIndexTipoStatoVO.setIdDocumentoIndex(documentoIndexVO.getIdDocumentoIndex());
						pbandiRDocuIndexTipoStatoVO.setIdStatoTipoDocIndex(idStatoTipoDocIndex);
						pbandiRDocuIndexTipoStatoVO.setIdTipoDocumentoIndex(beanUtil.transform(idTipoDocumentoIndex, BigDecimal.class));
						genericDAO.insert(pbandiRDocuIndexTipoStatoVO);
					}
			}else{
				logger.info(" codStatoTipoDocIndex null , non e' una checklist");
			}

		} catch (Exception e) {
			String message = "Impossibile inserire il documento:" + e.getMessage();
			logger.error(message, e);
			throw new RuntimeException(message, e);
		}  
		logger.end();
		return documentoIndexVO;
	}
	
	// **************************************************************************************************
	// FINE Metodi duplicati per poter usare salvaInfoNodoIndexSuDb senza l'insert su PbandiTDocumentoIndex.
	// **************************************************************************************************	

}
