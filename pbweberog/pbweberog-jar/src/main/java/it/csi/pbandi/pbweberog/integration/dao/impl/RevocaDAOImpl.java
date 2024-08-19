/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RevocaRecuperoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.DecodificaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.CausaleErogazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.EsitoSalvaRevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.MessaggioDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.QuotaParteVoceDiSpesaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.revoca.RevocaModalitaAgevolazioneDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.manager.ContoEconomicoNonTrovatoException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ErogazioneCausaleModalitaAgevolazioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ModalitaDiAgevolazioneContoEconomicoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.RevocaIrregolaritaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.recupero.RecuperoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.ModalitaAgevolazioneTotaleRevocheVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.QuotaParteVoceSpesaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.revoca.RevocaProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.AndCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDCausaleDisimpegnoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDettPropCertRevocaVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDsDettRevocaIrregVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRRevocaIrregVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTDettRevocaIrregVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRevocaVO;
import it.csi.pbandi.pbservizit.pbandisrv.interfacecsi.gestionedatididominio.GestioneDatiDiDominioSrv;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweberog.business.MailUtil;
import it.csi.pbandi.pbweberog.integration.dao.RevocaDAO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;
import it.csi.pbandi.pbweberog.util.NumberUtil;
import it.csi.pbandi.pbweberog.util.StringUtil;

@Component
public class RevocaDAOImpl extends JdbcDaoSupport implements RevocaDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	ContoEconomicoManager contoEconomicoManager;
	
	@Autowired
	NeofluxBusinessImpl neofluxBusiness;
	
	@Autowired
	DecodificheManager decodificheManager;
	
	@Autowired
	RevocaRecuperoManager revocaRecuperoManager;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public RevocaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
	}
	
	private static ResourceBundle restServices = ResourceBundle.getBundle("conf.restServices");
	private static ResourceBundle mail = ResourceBundle.getBundle("conf.mail");

	@Override
	@Transactional
	public QuotaParteVoceDiSpesaDTO[] findQuotePartePerRevoca(Long idUtente, String idIride,
			QuotaParteVoceDiSpesaDTO filtro) throws Exception {
		String prf = "[RevocaDAOImpl::findQuotePartePerRevoca]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			ValidatorInput.verifyAtLeastOneNotNullValue(filtro);
			ValidatorInput.verifyNullValue(new String[] { "idProgetto" },
					filtro.getIdProgetto());
			BigDecimal idContoEconomico = null;
			try {
				idContoEconomico = contoEconomicoManager.getIdContoMaster(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
			} catch (ContoEconomicoNonTrovatoException e) {
				LOG.error("Conto economico non trovato", e);
				throw new Exception(Constants.ERRORE_SERVER);
			}

			DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO filtroVO = new DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO();
			filtroVO.setIdContoEconomico(idContoEconomico);
			filtroVO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
			FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO> filterCondition = new FilterCondition<DocumentoSpesaVoceSpesaPagamentoContoEconomicoVO>(
					filtroVO);
			List<QuotaParteVoceSpesaVO> quoteParte = genericDAO.findListWhereGroupBy(filterCondition, QuotaParteVoceSpesaVO.class);
			LOG.info(prf + " END");
			return beanUtil.transform(quoteParte, QuotaParteVoceDiSpesaDTO.class);
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public RevocaModalitaAgevolazioneDTO[] findRevoche(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO filtro) throws Exception {
		String prf = "[RevocaDAOImpl::findRevoche]";
		LOG.info(prf + " BEGIN");
		try {
			List<ModalitaDiAgevolazioneContoEconomicoVO> listModalitaVO = null;
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, filtro);
			try {
				listModalitaVO = contoEconomicoManager.caricaModalitaAgevolazione( NumberUtil.toBigDecimal(filtro.getIdProgetto()), Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			} catch (ContoEconomicoNonTrovatoException e) {
				LOG.error("Nessun conto economico trovato", e);
				throw new Exception(Constants.ERRORE_SERVER);
			}
			RevocaModalitaAgevolazioneDTO[] result = beanUtil.transform(listModalitaVO, RevocaModalitaAgevolazioneDTO.class);

			/** Carico gli idModalitaAgevolazione */
			if (listModalitaVO != null) {
				List<ErogazioneCausaleModalitaAgevolazioneVO> listCausaleModalitaAgevolazioni = new ArrayList<ErogazioneCausaleModalitaAgevolazioneVO>();
				List<ModalitaAgevolazioneTotaleRevocheVO> listModalitaAgevolazioni = new ArrayList<ModalitaAgevolazioneTotaleRevocheVO>();
				for (ModalitaDiAgevolazioneContoEconomicoVO vo : listModalitaVO) {
					ErogazioneCausaleModalitaAgevolazioneVO causaleModalitaVO = new ErogazioneCausaleModalitaAgevolazioneVO();
					causaleModalitaVO.setIdModalitaAgevolazione(vo.getIdModalitaAgevolazione());
					listCausaleModalitaAgevolazioni.add(causaleModalitaVO);

					ModalitaAgevolazioneTotaleRevocheVO modalitaVO = new ModalitaAgevolazioneTotaleRevocheVO();
					modalitaVO.setIdModalitaAgevolazione(vo
							.getIdModalitaAgevolazione());
					listModalitaAgevolazioni.add(modalitaVO);
				}

				ErogazioneCausaleModalitaAgevolazioneVO filtroCausaliVO = new ErogazioneCausaleModalitaAgevolazioneVO();
				filtroCausaliVO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
				filtroCausaliVO.setAscendentOrder("idModalitaAgevolazione");

				FilterCondition<ErogazioneCausaleModalitaAgevolazioneVO> inConditionCausali = new FilterCondition<ErogazioneCausaleModalitaAgevolazioneVO>(
						listCausaleModalitaAgevolazioni);
				FilterCondition<ErogazioneCausaleModalitaAgevolazioneVO> conditionCausali = new FilterCondition<ErogazioneCausaleModalitaAgevolazioneVO>(
						filtroCausaliVO);
				AndCondition<ErogazioneCausaleModalitaAgevolazioneVO> andConditionCausali = new AndCondition<ErogazioneCausaleModalitaAgevolazioneVO>(
						inConditionCausali, conditionCausali);

				List<ErogazioneCausaleModalitaAgevolazioneVO> listCausali = genericDAO.findListWhere(andConditionCausali);

				ModalitaAgevolazioneTotaleRevocheVO filtroModalitaVO = new ModalitaAgevolazioneTotaleRevocheVO();
				filtroModalitaVO.setIdProgetto(NumberUtil.toBigDecimal(filtro.getIdProgetto()));
				filtroModalitaVO.setAscendentOrder("idModalitaAgevolazione");

				FilterCondition<ModalitaAgevolazioneTotaleRevocheVO> inConditioModalita = new FilterCondition<ModalitaAgevolazioneTotaleRevocheVO>(
						listModalitaAgevolazioni);
				FilterCondition<ModalitaAgevolazioneTotaleRevocheVO> conditionModalita = new FilterCondition<ModalitaAgevolazioneTotaleRevocheVO>(
						filtroModalitaVO);
				AndCondition<ModalitaAgevolazioneTotaleRevocheVO> andConditionModalita = new AndCondition<ModalitaAgevolazioneTotaleRevocheVO>(
						inConditioModalita, conditionModalita);

				List<ModalitaAgevolazioneTotaleRevocheVO> listModalita = genericDAO.findListWhere(andConditionModalita);

				for (RevocaModalitaAgevolazioneDTO dto : result) {
					BigDecimal idModalitaAgev = NumberUtil.toBigDecimal(dto.getIdModalitaAgevolazione());
					List<CausaleErogazioneDTO> causaliModalita = new ArrayList<CausaleErogazioneDTO>();
					for (ErogazioneCausaleModalitaAgevolazioneVO causaleVO : listCausali) {
						if (NumberUtil.compare(causaleVO.getIdModalitaAgevolazione(), idModalitaAgev) == 0) {
							CausaleErogazioneDTO causale = beanUtil.transform( causaleVO, CausaleErogazioneDTO.class);
							causaliModalita.add(causale);
						}
					}
					dto.setCausaliErogazioni(causaliModalita.toArray(new CausaleErogazioneDTO[] {}));

					for (ModalitaAgevolazioneTotaleRevocheVO modalitaTot : listModalita) {
						if (NumberUtil.compare( modalitaTot.getIdModalitaAgevolazione(), idModalitaAgev) == 0) {
							dto.setTotaleImportoRevocato(NumberUtil.toDouble(modalitaTot.getTotaleImportoRevocato()));
							dto.setTotaleImportoRecuperato(NumberUtil.toDouble(modalitaTot.getTotaleImportoRecuperato()));
						}
					}
				}

			}
			LOG.info(prf + " END");
			return result;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoSalvaRevocaDTO salvaRevoche(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO[] revoche) throws Exception {
		String prf = "[RevocaDAOImpl::salvaRevoche]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "revoche" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, revoche);
			EsitoSalvaRevocaDTO esito = new EsitoSalvaRevocaDTO();
			esito.setEsito(Boolean.FALSE);
			Long idProgetto=null;
			List<MessaggioDTO> errors = new ArrayList<MessaggioDTO>();
			if (revoche != null) {
				for (RevocaModalitaAgevolazioneDTO dto : revoche) {
					if (NumberUtil.compare(dto.getImportoRevoca(), 0D) > 0) {
						idProgetto=dto.getIdProgetto();
						PbandiTRevocaVO vo = new PbandiTRevocaVO();
						vo.setIdModalitaAgevolazione(NumberUtil.toBigDecimal(dto.getIdModalitaAgevolazione()));
						vo.setImporto(BeanUtil.transformToBigDecimal(dto.getImportoRevoca()));
						vo.setIdProgetto(NumberUtil.toBigDecimal(dto.getIdProgetto()));
						vo.setEstremi(dto.getEstremi());
						vo.setDtRevoca(DateUtil.utilToSqlDate(dto.getDtRevoca()));
						vo.setDtDecorRevoca(dto.getDtDecorRevoca() != null ? DateUtil.utilToSqlDate(dto.getDtDecorRevoca()) : null);
						
						if(dto.getCodCausaleDisimpegno()!=null){
							BigDecimal idCausaleDisimpegno = decodificheManager.decodeDescBreve(PbandiDCausaleDisimpegnoVO.class, dto.getCodCausaleDisimpegno());
							vo.setIdCausaleDisimpegno(idCausaleDisimpegno );
						}else{
							vo.setIdCausaleDisimpegno(null );
						}
						
						if(dto.getIdMotivoRevoca()!=null)
							vo.setIdMotivoRevoca(NumberUtil.toBigDecimal(dto.getIdMotivoRevoca()));
						
						vo.setIdPeriodo(NumberUtil.toBigDecimal(dto.getIdPeriodo()));
					 
						vo.setNote(dto.getNote());
						vo.setIdUtenteIns(NumberUtil.toBigDecimal(idUtente));
						try {
							genericDAO.insert(vo);
						} catch (Exception e) {
							LOG.error("Errore insert PBANDI_T_REVOCA", e);
							throw new Exception(Constants.ERRORE_SERVER);
						}
					}
				}
			}
			if (errors.isEmpty()) {
				esito.setEsito(Boolean.TRUE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				esito.setMsgs(new MessaggioDTO[] { msg });
			} else {
				esito.setMsgs(beanUtil.transform(errors, MessaggioDTO.class));
			}
			
			
			LOG.warn("\n\n\n############################ NEOFLUX UNLOCK REVOCA ##############################\n");
			neofluxBusiness.unlockAttivita(idUtente, idIride, idProgetto, Task.REVOCA) ;
			LOG.warn("############################ NEOFLUX UNLOCK REVOCA ##############################\n\n\n\n");
			
			//	${data_revoca}	DATA_REVOCA
			List<MetaDataVO>metaDatas=new ArrayList<MetaDataVO>();
			String descrBreveTemplateNotifica=Notification.NOTIFICA_DI_REVOCA;
			MetaDataVO metadata1=new MetaDataVO(); 
			metadata1.setNome(Notification.DATA_REVOCA);
			metadata1.setValore( DateUtil.getDate(new Date()));
			metaDatas.add(metadata1);

			
			LOG.info("calling genericDAO.callProcedure().putNotificationMetadata....");
			genericDAO.callProcedure().putNotificationMetadata(metaDatas);
			
			LOG.info("calling genericDAO.callProcedure().sendNotificationMessage....");
			genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),descrBreveTemplateNotifica,Notification.BENEFICIARIO,idUtente);
			
			
			
			LOG.info(prf + " END");
			return esito;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Boolean checkPropostaCertificazione(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[RevocaDAOImpl::checkPropostaCertificazione]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idProgetto);
			LOG.info(prf + " END");
			return revocaRecuperoManager.checkPropostaCertificazioneProgetto(idProgetto);		
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO[] findRiepilogoRevoche(Long idUtente, String idIride, RevocaDTO filtro) throws Exception {
		String prf = "[RevocaDAOImpl::findRiepilogoRevoche]";
		LOG.info(prf + " BEGIN");
		try {
			Long idProgetto = filtro.getIdProgetto();
			Long idModalitaAgevolazione = filtro.getIdModalitaAgevolazione();
			Long idRevoca = filtro.getIdRevoca();

			LOG.info(prf + " findRiepilogoRevoche for idProgetto: "+idProgetto+"  ,idModalitaAgevolazione:"+idModalitaAgevolazione+"  ,idRevoca: "+idRevoca);
			
			/* Carico le modalita' di agevolazioni del conto economico MASTER  legato al progetto */
			List<ModalitaDiAgevolazioneContoEconomicoVO> listModalitaContoEconomicoVO = null;
			try {
				listModalitaContoEconomicoVO = contoEconomicoManager.caricaModalitaAgevolazione(NumberUtil.toBigDecimal(idProgetto), Constants.TIPOLOGIA_CONTO_ECONOMICO_MASTER);
			} catch (ContoEconomicoNonTrovatoException e) {
				LOG.error(prf + " Nessun conto economico trovato", e);
				throw new Exception(Constants.ERRORE_SERVER);
			}
			List<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO> result = new ArrayList<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO>();

			if (listModalitaContoEconomicoVO != null) {
				LOG.info(prf + " listModalitaContoEconomicoVO not null");
				for (ModalitaDiAgevolazioneContoEconomicoVO modContoEconomicoVO : listModalitaContoEconomicoVO) {
					it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO dto = beanUtil.transform(modContoEconomicoVO, it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO.class);
					dto.setIdProgetto(idProgetto);
					it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO dtoWithRevoche =null;
					if (idModalitaAgevolazione != null) {
						LOG.info(prf + " idModalitaAgev not null");
						/** Recupero i dati solo della modalita di agevolazione  che interessa  */
						if (NumberUtil.compare(modContoEconomicoVO.getIdModalitaAgevolazione(), NumberUtil.toBigDecimal(idModalitaAgevolazione)) == 0) {
							LOG.info(prf + " modContoEconomicoVO.getIdModalitaAgevolazione() = idModalitaAgevolazione");
							dtoWithRevoche = findRiepilogoModalitaRevoca(dto, idRevoca);
						}
					} else {
						LOG.info(prf + " idModalitaAgev null");
						/* Recupero i dati di tutte le modalita di agevolazioni */
						dtoWithRevoche = findRiepilogoModalitaRevoca(dto, idRevoca);
					}
					if (dtoWithRevoche != null) {
						result.add(dtoWithRevoche);
					} else{
						/* Ricerco i recuperi associati alla modalita' di agevolazione del  progetto */
						Long idMod=idModalitaAgevolazione;
						if ( idMod== null) {
							idMod=modContoEconomicoVO.getIdModalitaAgevolazione().longValue();
						} 
						/* Ricerco le causali */
						List<ErogazioneCausaleModalitaAgevolazioneVO> listCausaleModalitaAgevolazioniVO = revocaRecuperoManager.findCausaliModalitaAgevolazione(idProgetto, idMod);
						dto.setCausaliErogazioni(beanUtil.transform(listCausaleModalitaAgevolazioniVO, it.csi.pbandi.pbweberog.dto.disimpegni.CausaleErogazioneDTO.class));
						/* Ricerco i totali delle revoche e dei recuperi per la modalita' di  agevolazione */					
						result.add(dto);
					}
				}
			}
			result = this.integraIrregolarita(result);
			this.logIrregolarita(result);	
			LOG.info(prf + " END");
			return beanUtil.transform(result, it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO.class);	
		} catch(Exception e) {
			throw e;
		}
	}

	private void logIrregolarita(List<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO> lista) {
		String prf = "[RevocaDAOImpl::logIrregolarita]";
		LOG.info(prf + " START");
		String s = "";
		if (lista != null) {
			for (it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO m : lista) {
				if (m.getRevoche() != null) {
					for (it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO r : m.getRevoche()) {						
						s = s + "\nRevoca: idRevoca = "+r.getIdRevoca()+"; descModalitaAgevolaz = "+r.getDescModalitaAgevolazione()+"; causale = "+r.getCausaleDisimpegno()+"; importo = "+r.getImporto();
						if (r.getRevocaIrregolarita() != null) {
							for (it.csi.pbandi.pbweberog.dto.disimpegni.RevocaIrregolaritaDTO i : r.getRevocaIrregolarita()) {
								s = s + "\n     Irregolarita: idIrregolarita = "+i.getIdIrregolarita()+"; tipoIrregolarita = "+i.getTipoIrregolarita()+"; quota = "+i.getQuotaIrreg()+"; DtFineValidita = "+i.getDtFineValidita();
								if (i.getDettagliRevocaIrregolarita() != null) {
									for (it.csi.pbandi.pbweberog.dto.disimpegni.DettaglioRevocaIrregolaritaDTO d : i.getDettagliRevocaIrregolarita()) {
										s = s + "\n          Dettaglio: IdDettRevocaIrreg = "+d.getIdDettRevocaIrreg()+"; idClassRevocaIrreg = "+d.getIdClassRevocaIrreg()+"; importo = "+d.getImporto();
										for (it.csi.pbandi.pbweberog.dto.disimpegni.DsDettaglioRevocaIrregolaritaDTO ds : d.getDsDettagliRevocaIrregolarita()) {
											s = s + "\n               DS associate: IdDichiarazioneSpesa = "+ds.getIdDichiarazioneSpesa()+"; importoIrregolareDs = "+ds.getImportoIrregolareDs();
										}
									}
								} 
							}
						} 
					}
				} 
			}
		}
		LOG.info(prf + " END");
	}

	private List<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO> integraIrregolarita(List<it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO> lista) {
		String prf = "[RevocaDAOImpl::integraIrregolarita]";
		LOG.info(prf + " START");
		if (lista != null) {		
			// Scorre le modalita agevolazione per accedere alle revoche.
			for (it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO m : lista) {
				if (m.getRevoche() != null) {
					// Scorre le revoche per accedere alle irregolarità ('disimpegni' a video).
					for (it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO r : m.getRevoche()) {												
						if (r.getRevocaIrregolarita() != null) {
							// Per ogni irregolarità cerca gli eventuali dettagli.
							for (it.csi.pbandi.pbweberog.dto.disimpegni.RevocaIrregolaritaDTO i : r.getRevocaIrregolarita()) {
								// Cerca gli eventuali dettagli associati all'irregolarità.
								List<PbandiTDettRevocaIrregVO> dettagliVO = findDettagliRevocaIrregolarita(r.getIdRevoca(), i.getIdIrregolarita());
								// Assegna i dettagli all'irregolarità.
								i.setDettagliRevocaIrregolarita(beanUtil.transform(dettagliVO, it.csi.pbandi.pbweberog.dto.disimpegni.DettaglioRevocaIrregolaritaDTO.class));
								// Per ogni dettaglio cerca le eventuali DS associate.
								for (it.csi.pbandi.pbweberog.dto.disimpegni.DettaglioRevocaIrregolaritaDTO d : i.getDettagliRevocaIrregolarita()) {
									List<PbandiRDsDettRevocaIrregVO> dsVO = findDsDettaglioRevocaIrregolarita(d.getIdDettRevocaIrreg());
									// Assegna le ds al dettaglio.
									d.setDsDettagliRevocaIrregolarita(beanUtil.transform(dsVO, it.csi.pbandi.pbweberog.dto.disimpegni.DsDettaglioRevocaIrregolaritaDTO.class));
								}
							}
						} 
					}
				} 
			}
		}
		LOG.info(prf + " END");
		return lista;
	}

	private List<PbandiRDsDettRevocaIrregVO> findDsDettaglioRevocaIrregolarita(Long idDettRevovaIrreg) {
		String prf = "[RevocaDAOImpl::findDsDettaglioRevocaIrregolarita]";
		LOG.info(prf + " START");
		PbandiRDsDettRevocaIrregVO dsVO = new PbandiRDsDettRevocaIrregVO();
		dsVO.setIdDettRevocaIrreg(new BigDecimal(idDettRevovaIrreg));
		List<PbandiRDsDettRevocaIrregVO> listaDsVO = genericDAO.findListWhere(dsVO);
		LOG.info(prf + " END");
		return listaDsVO;
	}

	private List<PbandiTDettRevocaIrregVO> findDettagliRevocaIrregolarita(Long idRevoca, Long idIrregolarita) {
		String prf = "[RevocaDAOImpl::findDettagliRevocaIrregolarita]";
		LOG.info(prf + " START");
		PbandiTDettRevocaIrregVO dettVO = new PbandiTDettRevocaIrregVO();
		dettVO.setIdRevoca(new BigDecimal(idRevoca));
		dettVO.setIdIrregolarita(new BigDecimal(idIrregolarita));
		List<PbandiTDettRevocaIrregVO> dettagliVO = genericDAO.findListWhere(dettVO);
		LOG.info(prf + " END");
		return dettagliVO;
	}

	private it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO findRiepilogoModalitaRevoca(it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO dto, Long idRevoca) throws FormalParameterException {
		String prf = "[RevocaDAOImpl::findRiepilogoModalitaRevoca]";
		LOG.info(prf + " START");
		Long idProgetto = dto.getIdProgetto();
		Long idModalitaAgevolazione = dto.getIdModalitaAgevolazione();

		LOG.info(prf + " idRevoca " + idRevoca);
		List<RevocaProgettoVO> listRevocheModalitaVO = revocaRecuperoManager.findRevocheProgetto(idProgetto, idModalitaAgevolazione, idRevoca);
		RevocaIrregolaritaVO filterIrregolarita=new RevocaIrregolaritaVO();
		filterIrregolarita.setIdProgetto(BigDecimal.valueOf(idProgetto));
		
		LOG.info(prf + " cerco revocheIrregolaritaConDuplicati");
		List<RevocaIrregolaritaVO> revocheIrregolaritaConDuplicati = genericDAO.findListWhere(filterIrregolarita);

		if (!listRevocheModalitaVO.isEmpty()) {
			LOG.info("\n\ncerco irregolarità");
			List<it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO> revocheDTO=new ArrayList<it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO>();
			int i = 0;
			for (RevocaProgettoVO revocaProgettoVO : listRevocheModalitaVO) {
				LOG.info(prf + " i = " + i);
				if(revocaProgettoVO.getIdRevoca()!=null){
					Map<BigDecimal, RevocaIrregolaritaVO> mapIrregolaritaPerRevoca = getMapIrregolaritaPerRevoca(revocaProgettoVO.getIdRevoca(), revocheIrregolaritaConDuplicati);
					Map<BigDecimal, RevocaIrregolaritaVO> mapIrregolaritaPerRevocaNew=new HashMap<BigDecimal, RevocaIrregolaritaVO>(mapIrregolaritaPerRevoca);
					List<RevocaIrregolaritaVO> revocheIrregolaritaSenzaDuplicati =new ArrayList<RevocaIrregolaritaVO>();
					it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revocaDTO=beanUtil.transform(revocaProgettoVO,it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO.class);
					if(revocaProgettoVO.getIdCausaleDisimpegno()!=null){
						 DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.CAUSALE_DISIMPEGNO, revocaProgettoVO.getIdCausaleDisimpegno());
					//	logger.info("codCausaleDisimpegno: "+codCausaleDisimpegno);
						revocaDTO.setCodCausaleDisimpegno(decodifica.getDescrizioneBreve());
						revocaDTO.setCausaleDisimpegno(decodifica.getDescrizione());
					}
					if (revocaProgettoVO.getIdMancatoRecupero() != null) {
						DecodificaDTO decodifica = decodificheManager.findDecodifica(GestioneDatiDiDominioSrv.MANCATO_RECUPERO, revocaProgettoVO.getIdMancatoRecupero());
						revocaDTO.setDescMancatoRecupero(decodifica.getDescrizione());
					}
					if(!revocheIrregolaritaConDuplicati.isEmpty()){
 						 for (RevocaIrregolaritaVO voz : revocheIrregolaritaConDuplicati) {
							 if(!mapIrregolaritaPerRevocaNew.containsKey(voz.getIdIrregolarita())){
								 RevocaIrregolaritaVO clone = (RevocaIrregolaritaVO)voz.clone();
								 clone.setQuotaIrreg(BigDecimal.valueOf(0d));
								 mapIrregolaritaPerRevocaNew.put(voz.getIdIrregolarita(),clone);
							 }
						 }
						 List <RevocaIrregolaritaVO>list=new ArrayList<RevocaIrregolaritaVO>(mapIrregolaritaPerRevocaNew.values());
						 revocaDTO.setRevocaIrregolarita(beanUtil.transform(list,it.csi.pbandi.pbweberog.dto.disimpegni.RevocaIrregolaritaDTO.class));
					}
					revocheDTO.add(revocaDTO);
				}
				i++;
			}
			
			dto.setRevoche(revocheDTO.toArray(new it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO[0]));
			LOG.info(prf +" Ricerco i recuperi associati alla modalita' di agevolazione del progetto");
			List<RecuperoProgettoVO> listRecuperiModalitaVO = revocaRecuperoManager.findRecuperiProgetto(idProgetto, idModalitaAgevolazione, null);
			dto.setRecuperi(beanUtil.transform(listRecuperiModalitaVO, it.csi.pbandi.pbweberog.dto.disimpegni.RecuperoDTO.class));

			/*
			 * Ricerco le causali
			 */
			LOG.info(prf + " Ricerco le causali");
			List<ErogazioneCausaleModalitaAgevolazioneVO> listCausaleModalitaAgevolazioniVO = revocaRecuperoManager.findCausaliModalitaAgevolazione(idProgetto, idModalitaAgevolazione);
			dto.setCausaliErogazioni(beanUtil.transform(listCausaleModalitaAgevolazioniVO, it.csi.pbandi.pbweberog.dto.disimpegni.CausaleErogazioneDTO.class));

			/*
			 * Ricerco i totali delle revoche e dei recuperi per la modalita' di agevolazione
			 */
			LOG.info(prf + "Ricerco i totali delle revoche e dei recuperi per la modalita' di agevolazione");
			ModalitaAgevolazioneTotaleRevocheVO totaliVO = revocaRecuperoManager.findTotaleRevocheRecuperi(idProgetto, idModalitaAgevolazione);
			dto.setTotaleImportoRecuperato(NumberUtil.toDouble(totaliVO.getTotaleImportoRecuperato()));
			dto.setTotaleImportoRevocato(NumberUtil.toDouble(totaliVO.getTotaleImportoRevocato()));
			LOG.info(prf + " END");
			return dto;
		} else {
			LOG.info(prf + " END");
			return null;
		}
	}

	private Map<BigDecimal, RevocaIrregolaritaVO> getMapIrregolaritaPerRevoca(BigDecimal idRevoca,
			List<RevocaIrregolaritaVO> revocheIrregolaritaConDuplicati) {
		String prf = "[RevocaDAOImpl::getMapIrregolaritaPerRevoca]";
		LOG.info(prf + " START");
		Map <BigDecimal,RevocaIrregolaritaVO> map=new HashMap<BigDecimal,RevocaIrregolaritaVO>();
		if(!revocheIrregolaritaConDuplicati.isEmpty()){
			 for (RevocaIrregolaritaVO vo  : revocheIrregolaritaConDuplicati) {
				 if(vo.getIdRevoca()!=null && vo.getIdRevoca().longValue()==idRevoca.longValue())
					map.put(vo.getIdIrregolarita(), vo);
			 }
		}
		LOG.info(prf + " END");
		return map;
	}

	@Override
	@Transactional
	public EsitoSalvaRevocaDTO modificaRevoca(Long idUtente, String idIride,
			it.csi.pbandi.pbweberog.dto.revoca.ModalitaAgevolazioneDTO modalitaAgevolazione) throws Exception {
		String prf = "[RevocaDAOImpl::modificaRevoca]";
		LOG.info(prf + " BEGIN");
		try {
			EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
			result.setEsito(Boolean.TRUE);
			List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();

			String[] nameParameter = { "idUtente", "identitaDigitale", "modalitaAgevolazione" };
			ValidatorInput.verifyNullValue(nameParameter, modalitaAgevolazione);
			ValidatorInput.verifyAtLeastOneNotNullValue(modalitaAgevolazione);
			ValidatorInput.verifyNullValue(new String[] { "modalitaAgevolazione,revoche[0]" }, modalitaAgevolazione.getRevoche()[0]);
			ValidatorInput.verifyNullValue(new String[] { "modalitaAgevolazione,revoche[0].idRevoca" }, modalitaAgevolazione.getRevoche()[0].getIdRevoca());

			BigDecimal totaleRevoche = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getTotaleImportoRevocato());
			BigDecimal totaleRecupero = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getTotaleImportoRecuperato());
			BigDecimal totaleErogato = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getImportoErogazioni());
			BigDecimal importoAgevolato = BeanUtil.transformToBigDecimal(modalitaAgevolazione.getQuotaImportoAgevolato());

			it.csi.pbandi.pbweberog.dto.revoca.RevocaDTO revoca = modalitaAgevolazione.getRevoche()[0];
			BigDecimal importoRevocato = BeanUtil.transformToBigDecimal(revoca.getImporto());

			/*
			 * Al totale delle revoche devo sottrarre il vecchio importo
			 * relativo alla revoca in oggetto e sommare il nuovo importo
			 */
			PbandiTRevocaVO revocaOldVO = new PbandiTRevocaVO();
			revocaOldVO.setIdRevoca(NumberUtil.toBigDecimal(revoca.getIdRevoca()));
			revocaOldVO = genericDAO.findSingleWhere(revocaOldVO);

			totaleRevoche = NumberUtil.subtract(totaleRevoche,revocaOldVO.getImporto());
			totaleRevoche = NumberUtil.sum(totaleRevoche, importoRevocato);

			/*
			 * Verifico che il totale delle recuperi non sia superiore al totale
			 * dei revoche
			 */
			if (NumberUtil.compare(totaleRecupero, totaleRevoche) > 0) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERROR_REVOCA_RECUPERATO_MAGGIORE_REVOCATO);
				msgs.add(msg);
				result.setEsito(Boolean.FALSE);

			}

			/*
			 * Verifico che il totale delle revoche non sia superiore alla somma
			 * erogato.
			 */
			BigDecimal sommaErogato = totaleErogato;
			if (NumberUtil.compare(totaleErogato, importoAgevolato) > 0) {
				sommaErogato = NumberUtil.subtract(totaleErogato,
						importoAgevolato);
			}
			if (NumberUtil.compare(totaleRevoche, sommaErogato) > 0) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(it.csi.pbandi.pbweberog.util.ErrorMessages.ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_EROGAZIONI);
				msgs.add(msg);
				result.setEsito(Boolean.FALSE);
			}

			/*
			 * Verifico se la revoca e' stata certificata e, nel caso in cui non
			 * e' stata inserita la motivazione nelle note visualizzato un msg
			 */
			if (isRevocaCertificata(revoca.getIdRevoca())
					&& (revoca.getNote() == null || revoca.getNote().trim()
							.length() == 0)) {
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessageConstants.WARN_REVOCA_REVOCA_CERTIFICATA);
				msgs.add(msg);
				result.setEsito(Boolean.FALSE);
			}

			/*
			 * Se non vi sono errori allora posso aggiornare la revoca
			 */
			if (result.getEsito()) {
				revocaOldVO.setImporto(BeanUtil.transformToBigDecimal(revoca.getImporto()));
				revocaOldVO.setEstremi(revoca.getEstremi());
				revocaOldVO.setDtRevoca(DateUtil.utilToSqlDate(revoca.getDtRevoca()));
				revocaOldVO.setDtDecorRevoca(revoca.getDtDecorRevoca() != null ? DateUtil.utilToSqlDate(revoca.getDtDecorRevoca()) : null);
				revocaOldVO.setNote(revoca.getNote());
				revocaOldVO.setIdMotivoRevoca(NumberUtil.toBigDecimal(revoca.getIdMotivoRevoca()));
				String codCausaleDisimpegno = revoca.getCodCausaleDisimpegno();
				revocaOldVO.setIdPeriodo(NumberUtil.toBigDecimal(revoca.getIdPeriodo()));
				//M.R.////////////
				revocaOldVO.setFlagOrdineRecupero(revoca.getFlagOrdineRecupero());
				revocaOldVO.setIdMancatoRecupero(NumberUtil.toBigDecimal(revoca.getIdMancatoREcupero()));
				//////////////////
				
				if(codCausaleDisimpegno!=null){
					BigDecimal idCausaleDisimpegno = decodificheManager.decodeDescBreve(PbandiDCausaleDisimpegnoVO.class, codCausaleDisimpegno);
					revocaOldVO.setIdCausaleDisimpegno(idCausaleDisimpegno );
				}else{
					revocaOldVO.setIdCausaleDisimpegno(null );
				}
				
				try {
					revocaOldVO.setIdUtenteAgg(new BigDecimal(idUtente));
					logger.info("\n\n\n\n\n\n\n\ngenericDAO.update(revocaOldVO)");
					genericDAO.updateNullables(revocaOldVO);
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
					msgs.add(msg);

				} catch (Exception e) {
					LOG.error("Errore in fase di update record in PBANDI_T_REVOCA",e);
					throw new Exception(Constants.ERRORE_SERVER);
				}
			}

			result.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));
			LOG.info(prf + " END");
			return result;
			
		} catch(Exception e) {
			throw e;
		}
	}

	private boolean isRevocaCertificata(Long idRevoca) {
		String prf = "[RevocaDAOImpl::isRevocaCertificata]";
		LOG.info(prf + " START");
		PbandiRDettPropCertRevocaVO filtroVO = new PbandiRDettPropCertRevocaVO();
		filtroVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
		List<PbandiRDettPropCertRevocaVO> certificazioniRevoca = genericDAO
				.findListWhere(filtroVO);
		LOG.info(prf + " END");
		if (certificazioniRevoca != null && !certificazioniRevoca.isEmpty())
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	@Override
	@Transactional
	public EsitoSalvaRevocaDTO cancellaRevoca(Long idUtente, String idIride, Long idRevoca) throws Exception {
		String prf = "[RevocaDAOImpl::cancellaRevoca]";
		LOG.info(prf + " BEGIN");
		try {
			EsitoSalvaRevocaDTO result = new EsitoSalvaRevocaDTO();
			List<MessaggioDTO> msgs = new ArrayList<MessaggioDTO>();

			String[] nameParameter = { "idUtente", "identitaDigitale",
					"idRevoca" };
			ValidatorInput.verifyNullValue(nameParameter, idRevoca);

			if (!isRevocaCertificata(idRevoca)) {
				PbandiTRevocaVO revocaVO = new PbandiTRevocaVO();
				revocaVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
				revocaVO = genericDAO.findSingleWhere(revocaVO);

				/*
				 * Verifico che il totale revocato (al netto dell' importo della
				 * revoca in oggetto) non sia inferiore al totale recuperato
				 */
				ModalitaAgevolazioneTotaleRevocheVO filtroTotaliVO = new ModalitaAgevolazioneTotaleRevocheVO();
				filtroTotaliVO.setIdProgetto(revocaVO.getIdProgetto());
				List<ModalitaAgevolazioneTotaleRevocheVO> totali = genericDAO
						.findListWhere(filtroTotaliVO);
				BigDecimal totaleRecuperato = new BigDecimal(0);
				BigDecimal totaleRevocato = new BigDecimal(0);
				for (ModalitaAgevolazioneTotaleRevocheVO vo : totali) {
					totaleRecuperato = NumberUtil.sum(totaleRecuperato,
							vo.getTotaleImportoRecuperato());
					totaleRevocato = NumberUtil.sum(totaleRevocato,
							vo.getTotaleImportoRevocato());
				}

				totaleRevocato = NumberUtil.subtract(totaleRevocato,
						revocaVO.getImporto());

				if (NumberUtil.compare(totaleRevocato, totaleRecuperato) >= 0) {
					try {
						// Cancello eventuali record in PBANDI_T_DETT_REVOCA_IRREG e PBANDI_R_DS_DETT_REVOCA_IRREG.
						this.cancellaDettagliEDs(idRevoca);						
						
						revocaVO = new PbandiTRevocaVO();
						revocaVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
						PbandiRRevocaIrregVO pbandiRRevocaIrregVO= new PbandiRRevocaIrregVO();
						pbandiRRevocaIrregVO.setIdRevoca(NumberUtil.toBigDecimal(idRevoca));
						logger.info("cancello eventuali revocheIrregolarita *** ");
						genericDAO.deleteWhere(Condition.filterBy(pbandiRRevocaIrregVO)) ;
						logger.info("cancello la revoca ");
						genericDAO.delete(revocaVO);
						MessaggioDTO msg = new MessaggioDTO();
						msg.setMsgKey(MessageConstants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
						result.setEsito(Boolean.TRUE);
						msgs.add(msg);
					} catch (Exception e) {
						LOG.error("Errore in fase di cancellazione record in PBANDI_T_REVOCA",e);
							
						throw new Exception(Constants.ERRORE_SERVER);
					}
				} else {
					MessaggioDTO msg = new MessaggioDTO();
					msg.setMsgKey(ErrorMessages.ERROR_REVOCA_CANCELLAZIONE_TOTALE_REVOCATO_INFERIORE_TOTALE_RECUPERATO);
					msgs.add(msg);
					result.setEsito(Boolean.FALSE);
				}
			} else {
				result.setEsito(Boolean.FALSE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(ErrorMessages.ERROR_REVOCA_PRESENTE_CERTIFICAZIONE_REVOCA);
				msgs.add(msg);
			}
			result.setMsgs(beanUtil.transform(msgs, MessaggioDTO.class));
			LOG.info(prf + " END");
			return result;		
		} catch(Exception e) {
			throw e;
		}
	}

	private void cancellaDettagliEDs(Long idRevoca) throws Exception {
		PbandiTDettRevocaIrregVO filtro = new PbandiTDettRevocaIrregVO();
		filtro.setIdRevoca(new BigDecimal(idRevoca));
		ArrayList<PbandiTDettRevocaIrregVO> listaDettagli = (ArrayList<PbandiTDettRevocaIrregVO>) genericDAO.findListWhere(filtro);		
		// Per ogni dettaglio:
		//  - cancello le eventuali ds associate.
		//  - cancello il dettaglio.
		for (PbandiTDettRevocaIrregVO dettaglio : listaDettagli) {
			// Cancello le eventuali ds associate al dettaglio corrente.
			PbandiRDsDettRevocaIrregVO filtroDs = new PbandiRDsDettRevocaIrregVO();
			filtroDs.setIdDettRevocaIrreg(dettaglio.getIdDettRevocaIrreg());
			ArrayList<PbandiRDsDettRevocaIrregVO> listaDs = (ArrayList<PbandiRDsDettRevocaIrregVO>) genericDAO.findListWhere(filtroDs);
			for (PbandiRDsDettRevocaIrregVO ds : listaDs) {
				genericDAO.delete(ds);
			}
			// Cancello il dettaglio corrente.
			genericDAO.delete(dettaglio);
		}
		
		// Cancello le associazioni revoca\irregolarità.
		PbandiRRevocaIrregVO filtroRevocaIrreg = new PbandiRRevocaIrregVO();
		filtroRevocaIrreg.setIdRevoca(new BigDecimal(idRevoca));
		List<PbandiRRevocaIrregVO> associazioni = genericDAO.findListWhere(filtroRevocaIrreg);
		for (PbandiRRevocaIrregVO r : associazioni) {
			genericDAO.delete(r);
		}
	}

	@Override
	@Transactional
	public EsitoSalvaRevocaDTO checkRevoche(Long idUtente, String idIride, RevocaModalitaAgevolazioneDTO[] revoche) throws Exception {
		String prf = "[RevocaDAOImpl::checkRevoche]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "identitaDigitale", "filtro" };
			ValidatorInput.verifyNullValue(nameParameter, revoche);
			EsitoSalvaRevocaDTO esito = new EsitoSalvaRevocaDTO();
			esito.setEsito(Boolean.FALSE);
			List<MessaggioDTO> errors = new ArrayList<MessaggioDTO>();
			if (revoche != null) {
				for (RevocaModalitaAgevolazioneDTO dto : revoche) {
					boolean hasError = false;
					HashMap<String, String> info = getTitoloBandoENumeroDomandaValidazione(dto.getIdProgetto(), "BR69");
					String titoloBando = info != null && !info.isEmpty() ? info.get("titoloBando") : "";
					/*
					 * Controllo che l'importo della revoca sia maggiore o
					 * uguale a 0
					 */
					if (NumberUtil.compare(dto.getImportoRevoca(), 0D) < 0) {
						hasError = true;
						MessaggioDTO error = new MessaggioDTO();
						error.setId(dto.getIdModalitaAgevolazione());
						error.setMsgKey(ErrorMessages.ERROR_REVOCA_IMPORTO_REVOCA_NEGATIVO);
						errors.add(error);
					}
 
					/**
					 * Controllo da saltare per buono dom/res
					 */
					if(!Constants.BUONO_DOMICILIARITA.equals(titoloBando) && !Constants.BUONO_RESIDENZIALITA.equals(titoloBando)) {
						if (!hasError && (dto.getImportoErogazioni() == null || NumberUtil.compare(dto.getImportoErogazioni(), 0D) == 0)) {
							hasError = true;
							MessaggioDTO error = new MessaggioDTO();
							error.setId(dto.getIdModalitaAgevolazione());
							error.setMsgKey(ErrorMessages.ERROR_REVOCA_IMPORTO_GIA_EROGATO_NON_PRESENTE);
							errors.add(error);
						}
					}

					if (!hasError && dto.getImportoRevoca() != null) {

						/*
						 * Controllo che la somma dei recuperi non sia superiore
						 * al totale degli importi revocati
						 */

						Double totaleRevocato = NumberUtil.sum(NumberUtil.getDoubleValue(dto.getTotaleImportoRevocato()), dto.getImportoRevoca());

						if (NumberUtil.compare(dto.getTotaleImportoRecuperato(), totaleRevocato) > 0) {
							hasError = true;
							MessaggioDTO error = new MessaggioDTO();
							error.setId(dto.getIdModalitaAgevolazione());
							error.setMsgKey(ErrorMessages.ERROR_REVOCA_RECUPERATO_MAGGIORE_REVOCATO);
							errors.add(error);
						}

						/*
						 * Controllo che la somma tra il nuovo importo della
						 * revoca ed il totale gia' revocato sia minore o uguale
						 * al totale delle erogazioni per la modalita'
						 * Da saltare per buono dom/res
						 */
						if(!Constants.BUONO_DOMICILIARITA.equals(titoloBando) && !Constants.BUONO_RESIDENZIALITA.equals(titoloBando)) {
							if (!hasError && NumberUtil.compare(totaleRevocato, dto.getImportoErogazioni()) > 0) {
								hasError = true;
								MessaggioDTO error = new MessaggioDTO();
								error.setId(dto.getIdModalitaAgevolazione());
								error.setMsgKey(ErrorMessages.ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_EROGAZIONI);
								errors.add(error);
	
							}
						}

						/* Se l' erogato e' maggiore dell' agevolato controllo  che la differenza tra erogato,
						 *  agevolato e totale  gia' revocato non sia inferiore alla nuovo importo  revoca */
						if (!hasError && NumberUtil.compare(dto.getImportoErogazioni(), dto.getQuotaImportoAgevolato()) > 0) {
							BigDecimal diff1 = NumberUtil.subtract(NumberUtil.createScaledBigDecimal(dto.getImportoErogazioni()), NumberUtil.createScaledBigDecimal(dto.getQuotaImportoAgevolato()));
							BigDecimal diffTot = NumberUtil.subtract(diff1,	NumberUtil.createScaledBigDecimal(dto.getTotaleImportoRevocato()));
							if (NumberUtil.compare(diffTot, NumberUtil.createScaledBigDecimal(dto.getImportoRevoca())) < 0) {
								hasError = true;
								MessaggioDTO error = new MessaggioDTO();
								error.setId(dto.getIdModalitaAgevolazione());
								error.setMsgKey(ErrorMessages.ERROR_REVOCA_RIDURRE_IMPORTO_REVOCA);
								errors.add(error);
							}
						}
						
						/**
						 * BUONO DOM/RES - L'importo revocato non può superare l'agevolato
						 */
						if(Constants.BUONO_DOMICILIARITA.equals(titoloBando) || Constants.BUONO_RESIDENZIALITA.equals(titoloBando)) {
							if (!hasError && NumberUtil.compare(totaleRevocato, dto.getQuotaImportoAgevolato()) > 0) {
								hasError = true;
								MessaggioDTO error = new MessaggioDTO();
								error.setId(dto.getIdModalitaAgevolazione());
								error.setMsgKey(ErrorMessages.ERROR_REVOCA_RIDURRE_IMPORTO_REVOCA);
								errors.add(error);
	
							}
						}
						
					}
				}
			}
			if (errors.isEmpty()) {
				esito.setEsito(Boolean.TRUE);
				MessaggioDTO msg = new MessaggioDTO();
				msg.setMsgKey(MessageConstants.WARN_REVOCA_CONFERMA_SALVATAGGIO);
				esito.setMsgs(new MessaggioDTO[] { msg });
			} else {
				esito.setMsgs(beanUtil.transform(errors, MessaggioDTO.class));
			}
			LOG.info(prf + " END");
			return esito;
		} catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Estrae il titolo bando ed il numero domanda a partire dall'idProgetto per una
	 * certa regola
	 * 
	 * @param idProgetto
	 * @param regola
	 * @return
	 */
	@Override
	public HashMap<String, String> getTitoloBandoENumeroDomandaValidazione(Long idProgetto, String regola) {
		LOG.info("[RevocaDAOImpl::getTitoloBandoENumeroDomandaValidazione] BEGIN");
		LOG.info("idProgetto: " + idProgetto + ", regola = " + regola);
		HashMap<String, String> info = null;
		String query = "SELECT ptb.TITOLO_BANDO, \n" + 
				"       ptd.NUMERO_DOMANDA \n" + 
				"FROM PBANDI_T_PROGETTO ptp \n" + 
				"JOIN PBANDI_T_DOMANDA ptd ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n" + 
				"JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.PROGR_BANDO_LINEA_INTERVENTO = ptd.PROGR_BANDO_LINEA_INTERVENTO \n" + 
				"JOIN PBANDI_T_BANDO ptb ON ptb.ID_BANDO = prbli.ID_BANDO \n" + 
				"JOIN PBANDI_R_REGOLA_BANDO_LINEA prrbl ON prrbl.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO \n" + 
				"JOIN PBANDI_C_REGOLA pcr ON pcr.ID_REGOLA = prrbl.ID_REGOLA \n" + 
				"WHERE ptp.ID_PROGETTO = ? \n" + 
				"  AND pcr.DESC_BREVE_REGOLA = ?";
		LOG.info("Query: " + query);
	    
		info = getJdbcTemplate().query(query, rs -> {
			HashMap<String, String> map = null;
			while(rs.next()) {
				map = new HashMap<String, String>();
				map.put("titoloBando", rs.getString("TITOLO_BANDO"));
				map.put("numeroDomanda", rs.getString("NUMERO_DOMANDA"));
			}
			return map;
		}, idProgetto, regola);
		
		LOG.info("[RevocaDAOImpl::getTitoloBandoENumeroDomandaValidazione] END");

		return info;
	}
	
	/**
	 * Chiamata al servizio di Domiciliarità/Residenzialità
	 * 
	 * @param note
	 * @param dataRevoca
	 * @param dataDecorrenza
	 * @param idProgetto
	 * @param idMotivoRevoca
	 * @param beneficiario
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<String> invioNotificaDomRes(String note, String dataRevoca, String dataDecorrenza, Long idProgetto,
			Long idMotivoRevoca, String beneficiario) throws Exception {
		LOG.info("[RevocaDAOImpl::invioNotificaDomRes] BEGIN");
		HashMap<String, String> result = getTitoloBandoENumeroDomandaValidazione(idProgetto, "BR69");
		ResponseEntity<String> response = null;
		String numeroDomanda = "";
		String titoloBando = "";
		if (result != null && !result.isEmpty()) {
			try {
				HashMap<String, String> codFiscali = getCodiciFiscali(idProgetto);
				String cfBeneficiario = codFiscali.get("cfBeneficiario");
				String cfRichiedente = !StringUtil.isEmpty(codFiscali.get("cfRichiedente"))
						? codFiscali.get("cfRichiedente")
						: cfBeneficiario;
				numeroDomanda = result.get("numeroDomanda");
				titoloBando = result.get("titoloBando");
				LOG.info("numeroDomanda: " + numeroDomanda + ", titoloBando: " + titoloBando);
				String host = "";
				String user = "";
				String pass = "";
				if (titoloBando.equals(Constants.BUONO_DOMICILIARITA)) {
					host = restServices.getString(Constants.BUONO_DOMICILIARITA_ENDPOINT);
					user = restServices.getString(Constants.USER_DOMICILIARITA);
					pass = restServices.getString(Constants.PASS_DOMICILIARITA);
				} else if (titoloBando.equals(Constants.BUONO_RESIDENZIALITA)) {
					host = restServices.getString(Constants.BUONO_RESIDENZIALITA_ENDPOINT);
					user = restServices.getString(Constants.USER_RESIDENZIALITA);
					pass = restServices.getString(Constants.PASS_RESIDENZIALITA);
				}
				LOG.info("host: " + host);
				RestTemplate template = new RestTemplate();
				UUID uuid4 = UUID.randomUUID();
				logger.info("X-Request-Id = " + uuid4);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
				headers.add("Authorization", getBasicAuthenticationHeader(user, pass));
				headers.add("X-Request-Id", uuid4.toString());
				ObjectMapper requestMapper = new ObjectMapper();
				requestMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
				requestMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				Date dtDecorRevoca = new SimpleDateFormat("dd/MM/yyyy").parse(dataDecorrenza);
				String jsonRequest = "{\"esito_acquisizione\": {" + "\"cf_beneficiario\": "
						+ requestMapper.writeValueAsString(cfBeneficiario) + ",\"cf_richiedente\": "
						+ requestMapper.writeValueAsString(cfRichiedente) + ",\"data_decorrenza_revoca\": "
						+ requestMapper.writeValueAsString(dtDecorRevoca) + ",\"nota\": "
						+ requestMapper.writeValueAsString(note) + "}}";
				LOG.info("Request: " + jsonRequest);
				HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);
				response = template.exchange(host + numeroDomanda + "/revoca", HttpMethod.PUT, entity, String.class);
			} catch (HttpClientErrorException e) {
				LOG.error("Errore nella chiamata al servizio di Scelta Sociale: " + e);
				response = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			} catch(HttpServerErrorException e) {
				LOG.error("Errore nella chiamata al servizio di Scelta Sociale: " + e);
				response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (response != null && "OK".equals(response.getBody())) {
				inviaMail(numeroDomanda, idMotivoRevoca, beneficiario, note, dataRevoca, dataDecorrenza, titoloBando);
			}

		}
		LOG.info("response: " + response);
		return response;
	}
	
	/**
	 * Estrae i codici fiscali di beneficiario e richiedente
	 * 
	 * @param idProgetto
	 * @return
	 */
	private HashMap<String, String> getCodiciFiscali(Long idProgetto) {
		LOG.info("[RevocaDAOImpl::getCodiciFiscali] BEGIN");
		LOG.info("idProgetto: " + idProgetto);
		HashMap<String, String> info = null;
		String query = "SELECT PRSP.ID_TIPO_ANAGRAFICA, \n" + 
				"       PTS.CODICE_FISCALE_SOGGETTO \n" + 
				"FROM PBANDI_R_SOGGETTO_PROGETTO PRSP \n" + 
				"JOIN PBANDI_T_SOGGETTO PTS ON PTS.ID_SOGGETTO = PRSP.ID_SOGGETTO \n" + 
				"WHERE PRSP.ID_PROGETTO = ?";
		LOG.info("Query: " + query);
	    
		info = getJdbcTemplate().query(query, rs -> {
			HashMap<String, String> map = new HashMap<String, String>();
			while(rs.next()) {
				String tipoAnag = rs.getString("ID_TIPO_ANAGRAFICA");
				String cf = rs.getString("CODICE_FISCALE_SOGGETTO");
				if("1".equals(tipoAnag)) {
					map.put("cfBeneficiario", cf);
				} else {
					map.put("cfRichiedente", cf);
				}
			}
			return map;
		}, idProgetto);
		
		LOG.info("[RevocaDAOImpl::getCodiciFiscali] END");

		return info;
	}
	
	/**
	 * Genera la stringa per la basic auth
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public String getBasicAuthenticationHeader(String username, String password) {
		String valueToEncode = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
	}
	
	/**
	 * Invio mail al beneficiario
	 * 
	 * @param numeroDomanda
	 * @param idMotivoRevoca
	 * @param beneficiario
	 * @param note
	 * @param dataRevoca
	 * @param dataDecorrenza
	 * @param titoloBando
	 * @throws Exception
	 */
	private void inviaMail(String numeroDomanda, Long idMotivoRevoca, String beneficiario, String note,
			String dataRevoca, String dataDecorrenza, String titoloBando) throws Exception {
		LOG.info("[RevocaDAOImpl::inviaMail] BEGIN");

		try {
			/**
			 * 1. Recupero destinatari
			 */
			String queryDestinatari = "SELECT ptr.EMAIL \n" 
					+ "FROM PBANDI_T_DOMANDA ptd \n"
					+ "JOIN PBANDI_T_PROGETTO ptp ON ptd.ID_DOMANDA = ptp.ID_DOMANDA \n"
					+ "JOIN PBANDI_R_SOGGETTO_PROGETTO prsp ON prsp.ID_PROGETTO = ptp.ID_PROGETTO \n"
					+ "AND prsp.ID_TIPO_ANAGRAFICA = 1 \n"
					+ "JOIN PBANDI_T_RECAPITI ptr ON ptr.ID_RECAPITI = prsp.ID_RECAPITI_PERSONA_FISICA \n"
					+ "WHERE NUMERO_DOMANDA = ?";
			
			LOG.info("Query destinatari: " + queryDestinatari);
			String destinatari = getJdbcTemplate().query(queryDestinatari, rs -> {
				String email = "";
				while (rs.next()) {
					email = rs.getString("EMAIL");
				}
				return email;
			}, numeroDomanda);
			
			if(Constants.BUONO_RESIDENZIALITA.equals(titoloBando)) {
				String queryDestinatariRes = "SELECT EMAIL \n" + 
						"FROM PBANDI_T_FORNITORE_STRUTTURA PTFS \n" + 
						"WHERE NUMERO_DOMANDA = ?";
				LOG.info("Query destinatari res: " + queryDestinatariRes);
				destinatari = destinatari + getJdbcTemplate().query(queryDestinatariRes, rs -> {
					String email = "";
					while (rs.next()) {
						email = email + "," + rs.getString("EMAIL");
					}
					return email;
				}, numeroDomanda);
				LOG.info("Destinatari: " + destinatari);
			}
			LOG.info("Destinatari: " + destinatari);
	
			if(!StringUtil.isEmpty(destinatari)) {
				/**
				 * 2. Recupero template mail
				 */
				String attributo = getAttributo(idMotivoRevoca, titoloBando);
				String queryTemplate = "SELECT VALORE \n" 
						+ "FROM PBANDI_C_COSTANTI \n"
						+ "WHERE ATTRIBUTO = '" + attributo + "'";
				LOG.info("Query template: " + queryTemplate);
				String template = getJdbcTemplate().queryForObject(queryTemplate, String.class);
				String meseDecorrenzaRevoca = getMeseDecorrenzaRevoca(dataRevoca);
				String testo = template.replace("${beneficiario}", beneficiario)
						.replace("${noteRevoca}", note != null ? note : "")
						.replace("${dataDecorrenzaRevoca}", dataDecorrenza)
						.replace("${meseDecorrenzaRevoca}", meseDecorrenzaRevoca)
						.replace("${numeroDomanda}", numeroDomanda);
				LOG.info("Testo: " + testo);
		
				String oggetto = getOggettoMail(idMotivoRevoca, titoloBando);
		
				MailUtil.sendEmail("no-reply@csi.it", "", destinatari, oggetto, testo, mail.getString("mail.host"), null);
			}
		} catch(Exception e) {
			LOG.error("Errore invio mail");
		}
		
		LOG.info("[RevocaDAOImpl::inviaMail] END");
	}
	
	/**
	 * Ritorna l'attributo della PBANDI_C_COSTANTI da ricercare
	 * 
	 * @param idMotivoRevoca
	 * @return
	 */
	private String getAttributo(Long idMotivoRevoca, String titoloBando) {
		LOG.info("[RevocaDAOImpl::getAttributo] BEGIN");
		String attributo = "";
		String bando = Constants.BUONO_DOMICILIARITA.equals(titoloBando) ? "dom" : "res";
		switch (idMotivoRevoca.intValue()) {
		case 901:
			attributo = Constants.TEMPLATE_MAIL_REVOCA + bando + "_revoca_per_carenza_requisiti_contrattuali";
			break;
		case 902:
			attributo = Constants.TEMPLATE_MAIL_REVOCA + bando + "_revoca_per_decesso";
			break;
		case 903:
			attributo = Constants.TEMPLATE_MAIL_REVOCA  + bando + "_revoca_per_mancata_rendicontazione";
			break;
		case 904:
			attributo = Constants.TEMPLATE_MAIL_REVOCA  + bando + "_revoca_per_trasferimento_fuori_regione";
			break;
		case 905:
			attributo = Constants.TEMPLATE_MAIL_REVOCA  + bando + "_revoca_per_rinuncia_al_buono";
			break;
		default:
			attributo = "";
		}
		LOG.info("[RevocaDAOImpl::getAttributo] END");
		return attributo;
	}
	
	/**
	 * Restituisce l'oggetto della mail in base al titolo bando e motivo revoca
	 * 
	 * @param idMotivoRevoca
	 * @param titoloBando
	 * @return
	 */
	private String getOggettoMail(Long idMotivoRevoca, String titoloBando) {
		LOG.info("[RevocaDAOImpl::getOggettoMail] BEGIN");
		String oggetto = "";
		switch (idMotivoRevoca.intValue()) {
		case 901:
			oggetto = Constants.BUONO_DOMICILIARITA.equals(titoloBando)
					? "Preavviso di revoca delle agevolazioni concesse – “Buono Domiciliarità” – PR FSE PLUS 21/27 – “Rafforzamento del supporto alla persona non autosufficiente correlato all’acquisto di servizi di cura e di assistenza domiciliari” di cui alle D.G.R. n. 27 – 6320 del 22/12/2022 e n. 32 – 6475 del 30/01/2023."
					: "Preavviso di revoca delle agevolazioni concesse – “Buono Residenzialità” – PR FSE PLUS 21/27 – “Sostegno all’inserimento di persone non autosufficienti in strutture residenziali a carattere socio-sanitario o socio-assistenziale” di cui alle D.G.R. n. 51 – 6810 del 27/04/2023";
			break;
		case 902:
			oggetto = Constants.BUONO_DOMICILIARITA.equals(titoloBando)
					? "Comunicazione di revoca delle agevolazioni concesse a seguito di decesso del/la destinatario/a – “Buono Domiciliarità” – PR FSE PLUS 21/27 – “Rafforzamento del supporto alla persona non autosufficiente correlato all’acquisto di servizi di cura e di assistenza domiciliari” di cui alle D.G.R. n. 27 – 6320 del 22/12/2022 e n. 32 – 6475 del 30/01/2023."
					: "Comunicazione di revoca delle agevolazioni concesse a seguito di decesso del/la destinatario/a – “Buono Residenzialità” – PR FSE PLUS 21/27 – “Sostegno all’inserimento di persone non autosufficienti in strutture residenziali a carattere socio-sanitario o socio-assistenziale”” di cui alle D.G.R. n. 51 – 6810 del 27/04/2023";
			break;
		case 903:
			oggetto = Constants.BUONO_DOMICILIARITA.equals(titoloBando)
					? "Comunicazione di revoca delle agevolazioni concesse per mancata rendicontazione di mensilità pregresse – “Buono Domiciliarità” – PR FSE PLUS 21/27 – “Rafforzamento del supporto alla persona non autosufficiente correlato all’acquisto di servizi di cura e di assistenza domiciliari” di cui alle D.G.R. n. 27 – 6320 del 22/12/2022 e n. 32 – 6475 del 30/01/2023."
					: "Comunicazione di revoca delle agevolazioni concesse per superamento del limite massimo di assenza di rendicontazione - \"Buono Residenzialità\" - PR FSE PLUS 21/27 - \"Sostegno all'inserimento di persone non autosufficienti in strutture residenziali a carattere socio-sanitario o socio-assistenziale\"” di cui alle D.G.R. n. 51 – 6810 del 27/04/2023";
			break;
		case 904:
			oggetto = Constants.BUONO_DOMICILIARITA.equals(titoloBando)
					? "Comunicazione di revoca delle agevolazioni concesse a seguito di trasferimento del/la destinatario/a fuori Regione – “Buono Domiciliarità” – PR FSE PLUS 21/27 – “Rafforzamento del supporto alla persona non autosufficiente correlato all’acquisto di servizi di cura e di assistenza domiciliari” di cui alle D.G.R. n. 27 – 6320 del 22/12/2022 e n. 32 – 6475 del 30/01/2023."
					: "Comunicazione di revoca delle agevolazioni concesse a seguito di trasferimento del/la destinatario/a fuori Regione – “Buono Residenzialità” – PR FSE PLUS 21/27 – “Sostegno all’inserimento di persone non autosufficienti in strutture residenziali a carattere socio-sanitario o socio-assistenziale”” di cui alle D.G.R. n. 51 – 6810 del 27/04/2023";
			break;
		case 905:
			oggetto = Constants.BUONO_DOMICILIARITA.equals(titoloBando) 
					? "Presa d’atto “rinuncia” – “Buono Domiciliarità” – PR FSE PLUS 21/27 – “Rafforzamento del supporto alla persona non autosufficiente correlato all’acquisto di servizi di cura e di assistenza domiciliari” di cui alle D.G.R. n. 27 – 6320 del 22/12/2022 e n. 32 – 6475 del 30/01/2023" 
					: "Presa d'atto \"rinuncia\" – “Buono Residenzialità” – PR FSE PLUS 21/27 – “Sostegno all’inserimento di persone non autosufficienti in strutture residenziali a carattere socio-sanitario o socio-assistenziale”” di cui alle D.G.R. n. 51 – 6810 del 27/04/2023";
			break;
		default:
			oggetto = "";
		}
		LOG.info("[RevocaDAOImpl::getOggettoMail] END");
		return oggetto;
	}
	
	/**
	 * Mappatura mesi
	 * 
	 * @param dataRevoca
	 * @return
	 */
	private String getMeseDecorrenzaRevoca(String dataRevoca) {
		LOG.info("[RevocaDAOImpl::getMeseDecorrenzaRevoca] BEGIN");
		String data[] = dataRevoca.split("/");
		String mese = "";
		if("01".equals(data[1])) {
			mese = "Gennaio";	
		} else if("02".equals(data[1])) {
			mese = "Febbraio";
		} else if("03".equals(data[1])) {
			mese = "Marzo";
		} else if("04".equals(data[1])) {
			mese = "April";
		} else if("05".equals(data[1])) {
			mese = "Maggio";
		} else if("06".equals(data[1])) {
			mese = "Giugno";
		} else if("07".equals(data[1])) {
			mese = "Luglio";
		} else if("08".equals(data[1])) {
			mese = "Agosto";
		} else if("09".equals(data[1])) {
			mese = "Settembre";
		} else if("10".equals(data[1])) {
			mese = "Ottobre";
		} else if("11".equals(data[1])) {
			mese = "Novembre";
		} else if("12".equals(data[1])) {
			mese = "Dicembre";
		}
		LOG.info("[RevocaDAOImpl::getMeseDecorrenzaRevoca] END");
		return mese;
	}
	
}
