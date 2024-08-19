/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DocumentoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.IrregolaritaManager;
import it.csi.pbandi.pbservizit.pbandisrv.dto.PropostaCertificazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ChecklistRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.DocumentoIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaIrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.EsitoSalvaRettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.IrregolaritaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.ProgettoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.irregolarita.RettificaForfettariaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.CodiceDescrizioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.irregolarita.IrregolaritaException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CampionamentoProgettiEsistentiVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.CecklistRettificaForfettariaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.PropostaCertificazApertaByIdLineaInterventoVO;
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
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.NumberUtil;
import it.csi.pbandi.pbweberog.dto.EsitoGenerazioneReportDTO;
import it.csi.pbandi.pbweberog.dto.MotivoIrregolarita;
import it.csi.pbandi.pbweberog.integration.dao.RegistroControlliDAO;
import it.csi.pbandi.pbweberog.integration.dao.impl.rowmapper.DocumentoIndexRowMapper;
import it.csi.pbandi.pbweberog.integration.vo.DocumentoIndexVO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.StringUtil;
import it.doqui.index.ecmengine.dto.Node;

@Component
public class RegistroControlliDAOImpl extends JdbcDaoSupport implements RegistroControlliDAO{


	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	protected FileApiServiceImpl fileApiServiceImpl;
	
	@Autowired
	private GenericDAO genericDAO;
	
	
	@Autowired
	IrregolaritaManager irregolaritaManager;
	
	@Autowired
	DocumentoManager documentoManager;
	
//	@Autowired
//	IndexDAO indexDAO;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public RegistroControlliDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public IrregolaritaDTO[] findIrregolarita(Long idUtente, String idIride, IrregolaritaDTO filtro) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findIrregolarita]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
			
			LOG.info(prf + " END");
			//return irregolaritaManager.findIrregolarita(idUtente, idIride, filtro);
			
			try {			
				IrregolaritaProgettoVO filtroVO  = beanUtil.transform(filtro, IrregolaritaProgettoVO.class);
				LOG.debug("FILTRO="+filtroVO);
				
				List<IrregolaritaProgettoVO> irregolaritaDefList = genericDAO.findListWhere(filtroVO);
				
				if(irregolaritaDefList!=null){
					LOG.debug(" trovata irregolaritaDefList size = "+irregolaritaDefList.size());
				}else{
					LOG.debug(" trovata irregolaritaDefList NULLA");
				}
				if (filtroVO.getDescBreveDispComunitaria() == null && 
						filtroVO.getDescQualificazioneIrreg() == null && 
						filtroVO.getDescBreveTipoIrregolarita() == null && 
						filtroVO.getDescBreveMetodoInd() == null && 
						filtroVO.getDescStatoAmministrativo() == null && 
						filtroVO.getDescBreveStatoFinanziario()  == null && 
						filtroVO.getDescBreveNaturaSanzione() == null) {
					
					LOG.debug("filtroVO ha attributi null...");
					IrregolaritaProvvisoriaProgettoVO filtroIrregProvv = new IrregolaritaProvvisoriaProgettoVO();
					filtroIrregProvv = beanUtil.transform(filtroVO, IrregolaritaProvvisoriaProgettoVO.class);
					filtroIrregProvv.setDescendentOrder("dtComunicazione");
					List<IrregolaritaProvvisoriaProgettoVO> irregolaritaProvv = genericDAO.findListWhere(filtroIrregProvv);				
					
					if(irregolaritaProvv!=null){
						logger.debug(" trovata irregolaritaProvv size = "+irregolaritaProvv.size());
					}else{
						LOG.debug(" trovata irregolaritaProvv NULLA");
					}
					
					ArrayList<IrregolaritaDTO> resultList = new ArrayList<IrregolaritaDTO>();
					for(int i=0; i<irregolaritaProvv.size(); i++) {
						IrregolaritaDTO irregProvv = new IrregolaritaDTO();
						//solo per utente non IDG
						irregProvv.setCodiceVisualizzato(irregolaritaProvv.get(i).getCodiceVisualizzato());
						irregProvv.setDenominazioneBeneficiario(irregolaritaProvv.get(i).getDenominazioneBeneficiario());				
						///////////
						//setto questo campo poich� altrimenti nella tabella del front-end non viene reperito l'idRiga
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
						irregProvv.setImportoIrregolaritaProvv(NumberUtil.toDouble(irregolaritaProvv.get(i).getImportoIrregolarita()));
						irregProvv.setImportoAgevolazioneIrregProvv(NumberUtil.toDouble(irregolaritaProvv.get(i).getImportoAgevolazioneIrreg()));
						irregProvv.setImportoIrregolareCertificatoProvv(NumberUtil.toDouble(irregolaritaProvv.get(i).getImportoIrregolareCertificato()));
						irregProvv.setDtFineValiditaProvv(irregolaritaProvv.get(i).getDtFineValidita());
						irregProvv.setDataInizioControlliProvv(irregolaritaProvv.get(i).getDataInizioControlli());
						irregProvv.setDataFineControlliProvv(irregolaritaProvv.get(i).getDataFineControlli());
						irregProvv.setTipoControlliProvv(irregolaritaProvv.get(i).getTipoControlli());
						if (irregolaritaProvv.get(i).getIdPeriodo() != null) {
							irregProvv.setIdPeriodoProvv(irregolaritaProvv.get(i).getIdPeriodo().longValue());
						}
						irregProvv.setDescPeriodoVisualizzataProvv(irregolaritaProvv.get(i).getDescPeriodoVisualizzata());
						if (irregolaritaProvv.get(i).getIdCategAnagrafica() != null) {
							irregProvv.setIdCategAnagraficaProvv(irregolaritaProvv.get(i).getIdCategAnagrafica().longValue());
						}
						irregProvv.setDescCategAnagraficaProvv(irregolaritaProvv.get(i).getDescCategAnagrafica());				
						
						LOG.debug("DataCampione="+irregolaritaProvv.get(i).getDataCampione());
						irregProvv.setDataCampione(irregolaritaProvv.get(i).getDataCampione());
						
						resultList.add(irregProvv);
					}
					
					LinkedList<IrregolaritaDTO> res = new LinkedList<IrregolaritaDTO>(Arrays.asList(beanUtil.transform(irregolaritaDefList, IrregolaritaDTO.class)));
					for(int i=0; i<res.size(); i++){
						for(int j=0; j<resultList.size(); j++){
							if (res.get(i).getIdIrregolaritaProvv() != null && 
									NumberUtil.compare(res.get(i).getIdIrregolaritaProvv(), resultList.get(j).getIdIrregolaritaProvv()) == 0 && 
									!res.contains(resultList.get(j))) {
								try {
									res.add(i, resultList.get(j));
								} catch (IndexOutOfBoundsException ex) {
									res.addFirst(resultList.get(j));
								}
								break;
							}
						}
					}
					for(int j=0; j<resultList.size(); j++){
						if (!res.contains(resultList.get(j))) {
							res.addFirst(resultList.get(j));
						}
					}
					
					return beanUtil.transform(res, IrregolaritaDTO.class);
				} else {
					LOG.debug(" filtroVO ha attributi valorizzati.");
					return beanUtil.transform(irregolaritaDefList, IrregolaritaDTO.class);
				}
			} catch (Exception e) {
				LOG.error("Errore mapping campi irregolarit� nel metodo findIrregolarita()", e);
				e.printStackTrace();
			}
			return null;
			
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public IrregolaritaDTO[] findEsitiRegolari(Long idUtente, String idIride, IrregolaritaDTO filtro) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findEsitiRegolari]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);			
			LOG.info(prf + " END");
			//return irregolaritaManager.findIrregolarita(idUtente, idIride, filtro);
			IrregolaritaDTO[] output = null;
			EsitoControlliProgettoVO filtroVO  = new EsitoControlliProgettoVO();
			if (filtro.getIdSoggettoBeneficiario() != null)
				filtroVO.setIdSoggettoBeneficiario(new BigDecimal(filtro.getIdSoggettoBeneficiario()));
			if (filtro.getIdProgetto() != null)
				filtroVO.setIdProgetto(new BigDecimal(filtro.getIdProgetto()));			
			filtroVO.setDtComunicazione(DateUtil.utilToSqlDate(filtro.getDtComunicazione()));			
			LOG.debug("filtroVO="+filtroVO.toString());		
			List<EsitoControlliProgettoVO> esiti = genericDAO.findListWhere(filtroVO);			
			output = beanUtil.transform(esiti, IrregolaritaDTO.class);
			return output;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public RettificaForfettariaDTO[] findRettificheForfettarie(Long idUtente, String idIride,
			IrregolaritaDTO filtro) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findRettificheForfettarie]";
		LOG.info(prf + " BEGIN");
		try {
			RettificaForfettariaVO vo = new RettificaForfettariaVO();			
			if (filtro.getIdSoggettoBeneficiario() != null)
				vo.setIdSoggettoBeneficiario(new BigDecimal(filtro.getIdSoggettoBeneficiario()));
			if (filtro.getIdProgetto() != null)
				vo.setIdProgetto(new BigDecimal(filtro.getIdProgetto()));
			vo.setDataInserimento(DateUtil.utilToSqlDate(filtro.getDtComunicazione()));
			
			List<RettificaForfettariaVO> elenco = genericDAO.findListWhere(vo);
			LOG.info(prf + " END");
			return beanUtil.transform(elenco, RettificaForfettariaDTO.class);
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}
	
	@Override
	@Transactional
	public IrregolaritaDTO findDettaglioIrregolarita(Long idUtente, String idIride, Long idIrregolarita) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findDettaglioIrregolarita]";
		LOG.info(prf + " BEGIN");
		try {			
			String[] nameParameter = { "idUtente", "identitaDigitale", "idIrregolarita" };
			ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);
			IrregolaritaProgettoVO filtroVO = new IrregolaritaProgettoVO();
			filtroVO.setIdIrregolarita(new BigDecimal(idIrregolarita));
			filtroVO = genericDAO.findSingleWhere(filtroVO);
			IrregolaritaDTO result = beanUtil.transform(filtroVO, IrregolaritaDTO.class);
			/* * Ricerco la scheda OLAF associata all' irregolarita'*/
			Long idProgetto = NumberUtil.toLong(filtroVO.getIdProgetto());
			Long idTarget = NumberUtil.toLong(filtroVO.getIdIrregolarita());
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
			LOG.info(prf + " END");
			return result;
			
		} catch (Exception e) {
			throw e;
		}

	}
	

	@Override
	public IrregolaritaDTO findDettaglioEsitoRegolare(Long idUtente, String idIride, Long idEsitoRegolare) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findDettaglioEsitoRegolare]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "idEsitoRegolare" };
			ValidatorInput.verifyNullValue(nameParameter, idEsitoRegolare);

			EsitoControlliProgettoVO filtroVO = new EsitoControlliProgettoVO();
			filtroVO.setIdEsitoControllo(new BigDecimal(idEsitoRegolare));

			filtroVO = genericDAO.findSingleWhere(filtroVO);

			IrregolaritaDTO result = beanUtil.transform(filtroVO, IrregolaritaDTO.class);
			result.setIdIrregolarita(result.getIdEsitoControllo());
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public CodiceDescrizioneDTO[] findDataCampione(Long idUtente, String idIride, Long idProgetto, String tipoControlli, Long idPeriodo,
			Long idCategAnagrafica) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findDataCampione]";
		LOG.info(prf + " BEGIN");
		CodiceDescrizioneDTO[] codArr = null;		
		try {			
			CampionamentoProgettiEsistentiVO filtroProgettiEsistenti = new CampionamentoProgettiEsistentiVO();
			filtroProgettiEsistenti.setIdProgetto(beanUtil.transform(idProgetto, BigDecimal.class));
			filtroProgettiEsistenti.setTipoControlli(tipoControlli);
			filtroProgettiEsistenti.setIdPeriodo(beanUtil.transform(idPeriodo, BigDecimal.class));
			filtroProgettiEsistenti.setIdCategAnagrafica(beanUtil.transform(idCategAnagrafica, BigDecimal.class));
			filtroProgettiEsistenti.setIdPropostaCertificaz(null);			
			ArrayList<CampionamentoProgettiEsistentiVO> listaCampProgetti = (ArrayList<CampionamentoProgettiEsistentiVO>) genericDAO.findListWhere(filtroProgettiEsistenti);		
			if(listaCampProgetti!=null && !listaCampProgetti.isEmpty()){	
				codArr = new CodiceDescrizioneDTO[listaCampProgetti.size()];				
				int i = 0;
				for (CampionamentoProgettiEsistentiVO item : listaCampProgetti) {
					CodiceDescrizioneDTO cod = new CodiceDescrizioneDTO();
					String id = "";
					String descr = "";
					if(item.getDataCampionamento() != null){
//						id = item.getIdCampione().toString(); sarebbe meglio
						id = DateUtil.getDate(item.getDataCampionamento());
						//tmp = item.getDataCampionamento().toString();
						descr = DateUtil.getDate(item.getDataCampionamento());
					}
					LOG.debug("["+i+"] idCampione="+item.getIdCampione()+" , data campionamento=["+descr+"]");
					cod.setCodice(id);  // TODO PK : qui sarebbe meglio mettere un numero 
					cod.setDescrizione(descr);
					
					codArr[i] = cod;
					i++;
					LOG.info(prf + " END");
				}
			}else {
				
				LOG.debug("listaCampProgetti NULLO");
			}
			
		} catch (Exception e) {
			LOG.error(" Errore durante l'esecuzione del servizio getComboDataCampione:", e);
			throw new Exception("[getComboDataCampione]:: Errore durante l'esecuzione del servizio getComboDataCampione:", e);
		}
		LOG.info(prf + " END");
		return codArr;
	}
	

	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO modificaIrregolarita(Long idUtente, String idIride, IrregolaritaDTO irregolarita,
			boolean updateDatiAggiuntivi) throws Exception {
		String prf = "[RegistroControlliDAOImpl::modificaIrregolarita]";
		LOG.info(prf + " BEGIN");
		try {			
	
			EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();

			// 1 - validazioni necessarie
			String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
			ValidatorInput.verifyNullValue(nameParameter, irregolarita);

			// 2 - controlli di business (da CdU)
			if (Constants.FLAG_TRUE.equals(irregolarita.getFlagBlocco())) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_IRREGOLARITA_BLOCCATA_MOD);
				esito.setMsgs(new MessaggioDTO[]{msg});
				return esito;			
			}

			if (!StringUtil.isEmpty(irregolarita.getNumeroIms()) && irregolarita.getDtIms() != null) {
				// irregolarita' inviata a IMS: e' l'ultima versione?
				if (!isLastVersion(idUtente, idIride, irregolarita)) {
					// non e' l'ultima versione: non posso modificare
					esito.setEsito(Boolean.FALSE);
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.ERRORE_IRREGOLARITA_VERSIONE_MOD);
					esito.setMsgs(new MessaggioDTO[]{msg});
					return esito;
				} else {
					// e' l'ultima versione: eseguo AGGIONAMENTO ('clone')
					esito = irregolaritaManager.aggiornaIrregolarita(idUtente, idIride, irregolarita);
				}
			} else {
				// irregolarita' non ancora inviata a IMS: eseguo MODIFICA
				esito = irregolaritaManager.modificaIrregolarita(idUtente, idIride, irregolarita, updateDatiAggiuntivi);
			}
			LOG.info(prf + " END");

			return esito;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	private boolean isLastVersion(Long idUtente, String idIride, IrregolaritaDTO irregolarita) {
		String prf = "[RegistroControlliDAOImpl::isLastVersion]";
		LOG.info(prf + " START");
		Long idIrregolaritaPadre = irregolarita.getIdIrregolaritaCollegata() != null ? irregolarita.getIdIrregolaritaCollegata() : irregolarita.getIdIrregolarita();
		IrregolaritaMaxVersioneVO vo = new IrregolaritaMaxVersioneVO();
		vo.setIdIrregolaritaPadre(new BigDecimal(idIrregolaritaPadre));
		vo = genericDAO.findSingleWhere(vo);
		if (irregolarita.getNumeroVersione().equals(NumberUtil.toLong(vo.getMaxVersione()))) {
			return true;
		}
		LOG.info(prf + " END");
		return false;
	}

	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO modificaEsitoRegolare(Long idUtente, String idIride, IrregolaritaDTO irregolarita) throws Exception {
		String prf = "[RegistroControlliDAOImpl::modificaEsitoRegolare]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "irregolarita" };
			ValidatorInput.verifyNullValue(nameParameter, irregolarita);
			EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();		
			esito = irregolaritaManager.modificaEsitoRegolare(idUtente, idIride, irregolarita);		
			LOG.info(prf + " END");
			return esito;			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO modificaIrregolaritaProvvisoria(Long idUtente, String idIride,
			IrregolaritaDTO irregolarita) throws Exception {
		String prf = "[RegistroControlliDAOImpl::modificaIrregolaritaProvvisoria]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "irregolarita" };
			ValidatorInput.verifyNullValue(nameParameter, irregolarita);

			EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
			esito = irregolaritaManager.modificaIrregolaritaProvvisoria(idUtente, idIride, irregolarita);
			LOG.info(prf + " END");
			return esito;
			
		} catch (Exception e) {
			throw e;
		}
	}

	
	

	@Override
	@Transactional
	public EsitoGenerazioneReportDTO getContenutoDocumentoById(Long idUtente, String idIride, Long idDocumentoIndex) throws Exception {
		String prf = "[RegistroControlliDAOImpl::getContenutoDocumentoById]";
		LOG.info(prf + " BEGIN");
		try {
			EsitoGenerazioneReportDTO esito = new EsitoGenerazioneReportDTO();
			//ottieni il nomeDocumento da db
			StringBuilder sql = new StringBuilder();
			sql.append("select d.*, t.desc_breve_tipo_doc_index, t.desc_tipo_doc_index "
					+ "from PBANDI_T_DOCUMENTO_INDEX d, PBANDI_C_TIPO_DOCUMENTO_INDEX t "
					+ "where d.id_tipo_documento_index = t.id_tipo_documento_index and ID_DOCUMENTO_INDEX =");
			sql.append(idDocumentoIndex);
			DocumentoIndexVO documento = getJdbcTemplate().queryForObject(sql.toString(), new DocumentoIndexRowMapper());	
			LOG.info(prf + " Nome file da scaricare: "+ documento.getNomeDocumento() + " "+ documento.getNomeFile());
			//scarica file da file storage
			//java.io.File file =   fileApiServiceImpl.downloadFile(documento.getNomeDocumento(), documento.getDescBreveTipoDocIndex());
			
			if(fileApiServiceImpl == null) {
				fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
			}
			
			java.io.File file =   fileApiServiceImpl.downloadFile(documento.getNomeFile(), documento.getDescBreveTipoDocIndex());
			if(file == null)
				throw new Exception("FILE NOT FOUND");
			irregolaritaManager.scaricaDocumentoIrregolarita(idUtente, idIride, idDocumentoIndex);
			byte[] fileBytes = FileUtils.readFileToByteArray(file);
			esito.setEsito(true);
			esito.setIdDocumentoIndex(idDocumentoIndex);
			esito.setNomeDocumento(documento.getNomeDocumento());
			esito.setReport(fileBytes);	
			return esito;
		} catch (Exception e) {
			throw e;
		}
	}

	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////// SERVIZI PER INSERIMENTO /////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO creaEsitoRegolare(Long idUtente, String idIride, IrregolaritaDTO irregolarita) throws Exception {
		String prf = "[RegistroControlliDAOImpl::creaEsitoRegolare]";
		LOG.info(prf + " BEGIN");
		try {
			EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
			String[] nameParameter = { "idUtente", "idIride", "irregolarita" };
			ValidatorInput.verifyNullValue(nameParameter, irregolarita);
			esito = irregolaritaManager.creaEsitoRegolare(idUtente, idIride, irregolarita);	
			LOG.info(prf + " END");
			return esito;				
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO creaIrregolarita(Long idUtente, String idIride, IrregolaritaDTO irregolarita) throws Exception {
		String prf = "[RegistroControlliDAOImpl::creaIrregolarita]";
		LOG.info(prf + " BEGIN");
		try {
			EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
			// 1 - validazioni necessarie
			String[] nameParameter = { "idUtente", "idIride", "irregolarita" };
			ValidatorInput.verifyNullValue(nameParameter, irregolarita);
			// 2 - creo l'irregolarita'
			esito = irregolaritaManager.creaIrregolarita(idUtente, idIride, irregolarita);
			LOG.info(prf + " END");
			return esito;				
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO creaIrregolaritaProvvisoria(Long idUtente, String idIride, IrregolaritaDTO irregolarita) throws Exception {
		String prf = "[RegistroControlliDAOImpl::creaIrregolaritaProvvisoria]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "irregolarita" };
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
				throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
			}		
			
			EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
			esito.setEsito(Boolean.TRUE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
			esito.setMsgs(new MessaggioDTO[]{msg});
			esito.setIdIrregolarita(NumberUtil.toLong(irregolaritaProvvVO.getIdIrregolaritaProvv()));
			LOG.info(prf + " END");
			return esito;				
		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO cancellaIrregolaritaRegolare(Long idUtente, String idIride, Long idEsitoregolare) throws IrregolaritaException, FormalParameterException {
		String prf = "[RegistroControlliDAOImpl::cancellaIrregolaritaRegolare]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride", "idEsitoregolare" };
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
				// Tolgo il riferimento all'id dell'esito regolare per evitare un urrore di integrità alla delete sotto. 
				// e cancello logicamente l'esito provvisorio.
				irreg.setIdEsitoControllo(null);
				irreg.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
				genericDAO.updateNullables(irreg);
			}
			
			genericDAO.delete(vo);
		} catch (Exception e) {
			logger.error("Errore delete su PBANDI_T_ESITO_CONTROLLI con idEsitoControllo = "+idEsitoregolare, e);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
		}
		
		// esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO cancellaIrregolarita(Long idUtente, String idIride, Long idIrregolarita) throws IrregolaritaException, FormalParameterException {
		String prf = "[RegistroControlliDAOImpl::cancellaIrregolarita]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "idIrregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);	
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		
		// controlli di business (da CdU)
		IrregolaritaProgettoVO filtroVO = new IrregolaritaProgettoVO();
		filtroVO.setIdIrregolarita(new BigDecimal(idIrregolarita));
		filtroVO = genericDAO.findSingleWhere(filtroVO);		
		// una irregolarita' bloccata NON può essere cancellata
		if (Constants.FLAG_TRUE.equals(filtroVO.getFlagBlocco())) {
			esito.setEsito(Boolean.FALSE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(ErrorMessages.ERRORE_IRREGOLARITA_BLOCCATA_CANC);
			esito.setMsgs(new MessaggioDTO[]{msg});
			return esito;			
		}		
		// una irregolarita' inviata NON può essere cancellata
		if (!StringUtil.isEmpty(filtroVO.getNumeroIms()) && filtroVO.getDtIms() != null) {
			esito.setEsito(Boolean.FALSE);
			MessaggioDTO msg = new MessaggioDTO();
			msg.setMsgKey(ErrorMessages.ERRORE_IRREGOLARITA_INVIATA_CANC);
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
			LOG.error(prf + " Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA", e);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
		}		
		/* 0) recupera la chiave della irreg provv per la cancellazione logica di quest'ultima */
		PbandiTIrregolaritaVO filtroIrrDef = new PbandiTIrregolaritaVO();
		filtroIrrDef.setIdIrregolarita(new BigDecimal(idIrregolarita));
		filtroIrrDef = genericDAO.findSingleWhere(filtroIrrDef);
		
		try {
			if (filtroIrrDef.getIdIrregolaritaProvv() != null) 
				eliminaIrregolaritaProvvisoria(idUtente, idIride, NumberUtil.toLong(filtroIrrDef.getIdIrregolaritaProvv()));
		} catch (Exception e) {
			LOG.error(prf + " Errore in richiamo eliminaIrregolaritaProvvisoria() da eliminaIrregolarita()", e);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
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
				 //indexDAO.cancellaNodo(new Node(uuidNodoSchedaOlaf));
				
			} catch (Exception e) {
				LOG.error(prf + " Errore nella cancellazione della scheda olaf con uid nodo "+uuidNodoSchedaOlaf+" su index ", e);
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
				 //indexDAO.cancellaNodo(new Node(uuidNodoDatiAggiuntivi));
				
			} catch (Exception e) {
				LOG.error(prf + " Errore nella cancellazione dei dati aggiuntivi con uid nodo "+uuidNodoDatiAggiuntivi+" su index ", e);
			}
			
		}
		
		
		// esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		LOG.info(prf + " END");
		return esito;
	}

	private EsitoSalvaIrregolaritaDTO eliminaIrregolaritaProvvisoria(Long idUtente, String idIride, Long idIrregolarita) throws IrregolaritaException, FormalParameterException {
		String prf = "[RegistroControlliDAOImpl::eliminaIrregolaritaProvvisoria]";
		LOG.info(prf + " START");
		String[] nameParameter = { "idUtente", "identitaDigitale", "idIrregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);	
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();		
		// una irregolarità provvisoria legata ad una definitiva (in corso di validità) non può essere cancellata 
		PbandiTIrregolaritaVO filtroIrrDef = new PbandiTIrregolaritaVO();
		filtroIrrDef.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		try {
			filtroIrrDef = genericDAO.findSingleOrNoneWhere(filtroIrrDef);
			if (filtroIrrDef != null && filtroIrrDef.getDtFineValidita() == null) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_IRREGOLARITA_PROVV_LEGATA_A_DEF); //E005
				esito.setMsgs(new MessaggioDTO[]{msg});
				return esito;
			}
		} catch(Exception ex) {
			logger.error("Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA_PROVV", ex);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, ex);
		}
		
		// una irregolarità provvisoria legata ad un esito definitivo non può essere cancellata 
		PbandiTIrregolaritaProvvVO filtroIrrProvv = new PbandiTIrregolaritaProvvVO();
		filtroIrrProvv.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		try {
			filtroIrrProvv = genericDAO.findSingleOrNoneWhere(filtroIrrProvv);
			if (filtroIrrProvv != null && filtroIrrProvv.getIdEsitoControllo() != null) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_IRREGOLARITA_PROVV_LEGATA_A_REG);
				esito.setMsgs(new MessaggioDTO[]{msg});
				return esito;
			}
		} catch(Exception ex) {
			logger.error("Errore nel carcare la provvisoria: ", ex);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, ex);
		}
		
		// cancellazione logica: setto la data di fine validita'
		PbandiTIrregolaritaProvvVO updVO = new PbandiTIrregolaritaProvvVO();
		updVO.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		updVO.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
				
		try {
			genericDAO.update(updVO);
		} catch (Exception e) {
			logger.error("Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA_PROVV", e);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
		}		
		
		// esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		LOG.info(prf + " END");
		return esito;
	}

	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO cancellaIrregolaritaProvvisoria(Long idUtente, String idIride, Long idIrregolarita) throws IrregolaritaException, FormalParameterException {
		String prf = "[RegistroControlliDAOImpl::eliminaIrregolaritaProvvisoria]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride", "idIrregolarita" };
		ValidatorInput.verifyNullValue(nameParameter, idIrregolarita);	
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();		
		//una irregolarità provvisoria legata ad una definitiva (in corso di validità) non può essere cancellata 
		PbandiTIrregolaritaVO filtroIrrDef = new PbandiTIrregolaritaVO();
		filtroIrrDef.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		try {
			filtroIrrDef = genericDAO.findSingleOrNoneWhere(filtroIrrDef);
			if (filtroIrrDef != null && filtroIrrDef.getDtFineValidita() == null) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_IRREGOLARITA_PROVV_LEGATA_A_DEF); //E005
				esito.setMsgs(new MessaggioDTO[]{msg});
				return esito;
			}
		} catch(Exception ex) {
			LOG.error(prf + " Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA_PROVV", ex);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, ex);
		}		
		// una irregolarità provvisoria legata ad un esito definitivo non può essere cancellata 
		PbandiTIrregolaritaProvvVO filtroIrrProvv = new PbandiTIrregolaritaProvvVO();
		filtroIrrProvv.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		try {
			filtroIrrProvv = genericDAO.findSingleOrNoneWhere(filtroIrrProvv);
			if (filtroIrrProvv != null && filtroIrrProvv.getIdEsitoControllo() != null) {
				esito.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERRORE_IRREGOLARITA_PROVV_LEGATA_A_REG);
				esito.setMsgs(new MessaggioDTO[]{msg});
				return esito;
			}
		} catch(Exception ex) {
			LOG.error(prf + " Errore nel carcare la provvisoria: ", ex);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, ex);
		}		
		// cancellazione logica: setto la data di fine validita'
		PbandiTIrregolaritaProvvVO updVO = new PbandiTIrregolaritaProvvVO();
		updVO.setIdIrregolaritaProvv(new BigDecimal(idIrregolarita));
		updVO.setDtFineValidita(DateUtil.utilToSqlDate(DateUtil.getDataOdierna()));
				
		try {
			genericDAO.update(updVO);
		} catch (Exception e) {
			LOG.error(prf + " Errore in blocco/sblocco su PBANDI_T_IRREGOLARITA_PROVV", e);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
		}				
		// esito finale
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		LOG.info(prf + " END");
		return esito;
	}
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////// RETTIFICHE FORFEITTARIE SU AFFIDAMENTI ////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional
	public PropostaCertificazioneDTO[] findProposteCertificazioneAperteByIdLineaIntervento(Long idUtente,
			String idIride, Long idLineaDiIntervento) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findProposteCertificazioneAperteByIdLineaIntervento]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idLineaDiIntervento" };
			ValidatorInput.verifyNullValue(nameParameter, idLineaDiIntervento);
			PropostaCertificazApertaByIdLineaInterventoVO filtro = new PropostaCertificazApertaByIdLineaInterventoVO();
			filtro.setDescBreveStatoPropostaCert(Constants.STATO_PROPOSTA_CERTIFICAZIONE_APERTA);
			filtro.setIdLineaDiIntervento(idLineaDiIntervento);
			LOG.info(prf + " END");
			return beanUtil.transform(genericDAO.findListWhere(filtro), PropostaCertificazioneDTO.class);			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public ChecklistRettificaForfettariaDTO[] findChecklistRettificheForfettarie(Long idUtente, String idIride,
			Long idProgetto) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findChecklistRettificheForfettarie]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);			
			CecklistRettificaForfettariaVO vo = new CecklistRettificaForfettariaVO();
			vo.setIdProgetto(new BigDecimal(idProgetto));			
			List<CecklistRettificaForfettariaVO> elenco = genericDAO.findListWhere(vo);
			LOG.info(prf + " END");
			return beanUtil.transform(elenco, ChecklistRettificaForfettariaDTO.class);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaRettificaForfettariaDTO salvaRettificaForfettaria(Long idUtente, String idIride,
			RettificaForfettariaDTO rettifica) throws Exception {
		String prf = "[RegistroControlliDAOImpl::salvaRettificaForfettaria]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "rettifica" };
			ValidatorInput.verifyNullValue(nameParameter, rettifica);		
			PbandiTRettForfettVO vo = new PbandiTRettForfettVO();		
			vo.setIdAppalto(NumberUtil.toBigDecimal(rettifica.getIdAppalto()));
			vo.setIdAppaltoChecklist(NumberUtil.toBigDecimal(rettifica.getIdAppaltoChecklist()));
			vo.setIdCategAnagrafica(NumberUtil.toBigDecimal(rettifica.getIdCategAnagrafica()));
			vo.setIdPropostaCertificaz(NumberUtil.toBigDecimal(rettifica.getIdPropostaCertificaz()));
			vo.setPercRett(rettifica.getPercRett());
			// java.sql.date  ->  java.util.Date
			vo.setDataInserimento(DateUtil.utilToSqlDate(rettifica.getDataInserimento()));
			
			try {
				vo = genericDAO.insert(vo);
			} catch (Exception e) {
				LOG.error("salvaRettificaForfettaria(): ERRORE: "+e);
				throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
			}			
			EsitoSalvaRettificaForfettariaDTO esito = new EsitoSalvaRettificaForfettariaDTO();
			esito.setEsito(true);
			esito.setIdRettificaForfettaria(vo.getIdRettificaForfett().longValue());
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public ProgettoDTO findProgetto(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[RegistroControlliDAOImpl::findProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
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
			LOG.info(prf + " END");
			return dto;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaRettificaForfettariaDTO eliminaRettificaForfettaria(Long idUtente, String idIride,
			Long idRettificaForfett) throws FormalParameterException, IrregolaritaException {
		String prf = "[RegistroControlliDAOImpl::eliminaRettificaForfettaria]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "idRettificaForfett" };
		ValidatorInput.verifyNullValue(nameParameter, idRettificaForfett);		
		PbandiTRettForfettVO vo = new PbandiTRettForfettVO();
		vo.setIdRettificaForfett(NumberUtil.toBigDecimal(idRettificaForfett));		
		try {
			genericDAO.delete(vo);
		} catch (Exception e) {
			logger.error("eliminaRettificaForfettaria(): ERRORE: "+e);
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
		}		
		EsitoSalvaRettificaForfettariaDTO esito = new EsitoSalvaRettificaForfettariaDTO();
		esito.setEsito(true);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		LOG.info(prf + " END");
		return esito;	
	}

	@Override
	@Transactional
	public EsitoSalvaIrregolaritaDTO registraInvioIrregolarita(Long idUtente, String idIride, IrregolaritaDTO irregolarita) throws FormalParameterException, IrregolaritaException {
		String prf = "[RegistroControlliDAOImpl::registraInvioIrregolarita]";
		LOG.info(prf + " BEGIN");
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
			throw new IrregolaritaException(ErrorMessages.ERRORE_SERVER, e);
		}		
		
		// esito finale
		EsitoSalvaIrregolaritaDTO esito = new EsitoSalvaIrregolaritaDTO();
		esito.setEsito(Boolean.TRUE);
		MessaggioDTO msg = new MessaggioDTO();
		msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
		esito.setMsgs(new MessaggioDTO[]{msg});
		LOG.info(prf + " END");
		return esito;
		
	}

	


	

}
