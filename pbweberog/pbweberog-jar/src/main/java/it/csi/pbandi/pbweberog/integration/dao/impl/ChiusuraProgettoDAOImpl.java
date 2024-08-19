/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ContoEconomicoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.DecodificheManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.ProgettoManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Notification;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.fineprogetto.EsitoOperazioneChiudiProgetto;
import it.csi.pbandi.pbservizit.pbandisrv.dto.manager.ContoEconomicoDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.exception.neoflux.NeoFluxException;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.neoflux.MetaDataVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiDStatoProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTRinunciaVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbweberog.integration.dao.ChiusuraProgettoDAO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.DateUtil;
import it.csi.pbandi.pbweberog.util.NumberUtil;

@Component
public class ChiusuraProgettoDAOImpl extends JdbcDaoSupport implements ChiusuraProgettoDAO {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	ProgettoManager progettoManager;
	
	@Autowired
	NeofluxBusinessImpl neofluxBusiness;
	
	@Autowired
	DecodificheManager decodificheManager;
	
	@Autowired
	ContoEconomicoManager contoEconomicoManager;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public ChiusuraProgettoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
	}

	@Override
	@Transactional
	public Boolean isRinunciaPresente(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::isRinunciaPresente]";
		LOG.info(prf + " BEGIN");
		try {
			String nameParameter[] = { "idProgetto" };
			ValidatorInput.verifyBeansNotEmpty(nameParameter, idProgetto);

			PbandiTRinunciaVO query = new PbandiTRinunciaVO();
			query.setIdProgetto(new BigDecimal(idProgetto));
			java.lang.Boolean response = genericDAO.findListWhere(query).size() > 0;
			LOG.info(prf + " END");
			return response;

		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public Long[] getListaIdModalitaAgevolazioni(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::getListaIdModalitaAgevolazioni]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,idIride, idProgetto);			
			Long[] arr = null;			
			List<?> listaIdModAgev = progettoManager.getListaIdModalitaAgevolazioni(idProgetto);			
			if(listaIdModAgev!=null){
				arr = new Long[listaIdModAgev.size()];
				LOG.info(prf + " trovata listaIdModAgev di dimensione = "+listaIdModAgev.size());
				int i = 0;
				for (Object litem : listaIdModAgev) {
					LOG.info(" arr["+i+"]="+litem);
					arr[i] = (Long)litem;
					i++;
				}
			} else {
				LOG.info(" listaIdModAgev NULL");
			}
			LOG.info(prf + " END");
			return arr;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public Boolean isProgettoAssociatoRegola(Long idUtente, String idIride, Long idProgetto, String codRegola) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::isProgettoAssociatoRegola]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto", "codRegola" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, codRegola);		
			Boolean ret = false;
			ret = progettoManager.isProgettoAssociatoRegola(idProgetto, codRegola);
			logger.debug("ret="+ret);
			LOG.info(prf + " END");
			return ret;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public String getCausaleErogazionePerContoEconomicoLiquidazione(Long idUtente, String idIride, Long idProgetto,
			Long idModalitaAgevolazione) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::getCausaleErogazionePerContoEconomicoLiquidazione]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto", "idModalitaAgevolazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, idModalitaAgevolazione);			
			String ret = progettoManager.getCausaleErogazionePerContoEconomicoLiquidazione(idProgetto, idModalitaAgevolazione);		
			LOG.info(prf + " END");
			return ret;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public String getCausaleErogazionePerContoEconomicoErogazione(Long idUtente, String idIride, Long idProgetto,
			Long idModalitaAgevolazione) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::getCausaleErogazionePerContoEconomicoErogazione]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto", "idModalitaAgevolazione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, idModalitaAgevolazione);		
			String ret = progettoManager.getCausaleErogazionePerContoEconomicoErogazione(idProgetto, idModalitaAgevolazione);		
			LOG.info(prf + " END");
			return ret;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public Long getIdProgettoAContributo(Long idUtente, String idIride, Long idProgettoAContributo) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::getIdProgettoAContributo]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgettoAContributo);			
			Long idProgetto = progettoManager.getIdProgettoAContributo(idProgettoAContributo);	
			LOG.info(prf + " END");
			return idProgetto;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public boolean checkErogazioneSaldoProgettoContributo(Long idUtente, String idIride, Long idProgetto) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::checkErogazioneSaldoProgettoContributo]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);			
			Boolean ret = progettoManager.checkErogazioneSaldoProgettoContributo(idProgetto);
			LOG.info(prf + " END");
			return ret;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public EsitoOperazioneChiudiProgetto chiudiProgetto(Long idUtente, String idIride, Long idProgetto, String noteChiusura) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::chiudiProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);
			EsitoOperazioneChiudiProgetto esito = new EsitoOperazioneChiudiProgetto();
			esito.setEsito(doChiudiProgetto(idProgetto, idUtente, noteChiusura, Constants.STATO_PROGETTO_CHIUSO));
			LOG.warn("\n\n\n############################ NEOFLUX CHIUSURA_PROGETTO ##############################\n");		
			try {
				neofluxBusiness.endAttivita(idUtente, idIride, idProgetto, Task.CHIUSURA_PROGETTO);
			} catch (NeoFluxException e) {
				LOG.warn(prf + " errore gestito NeoFluxException: message="+e.getMessage());
				throw new Exception(e.getMessage());
			} 
			LOG.warn("############################ NEOFLUX CHIUSURA_PROGETTO ##############################\n\n\n\n");			
			List<MetaDataVO>metaDatas=new ArrayList<MetaDataVO>();
			String descrBreveTemplateNotifica=Notification.NOTIFICA_CHIUSURA_DEL_PROGETTO;
			MetaDataVO metadata1=new MetaDataVO(); 
			metadata1.setNome(Notification.DATA_CHIUSURA_PROGETTO);
			metadata1.setValore( DateUtil.getDate(new Date()));
			metaDatas.add(metadata1);

			LOG.info("calling genericDAO.callProcedure().putNotificationMetadata for CHIUSURA_PROGETTO....");
			genericDAO.callProcedure().putNotificationMetadata(metaDatas);
			
			LOG.info(prf + " calling genericDAO.callProcedure().sendNotificationMessage for CHIUSURA_PROGETTO....");
			genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),descrBreveTemplateNotifica,Notification.BENEFICIARIO,idUtente);
					
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	private Boolean doChiudiProgetto(Long idProgetto, Long idUtente, String noteChiusura, String descBreveStatoProgetto) {
		String prf = "[ChiusuraProgettoDAOImpl::doChiudiProgetto]";
		LOG.info(prf + " START");
		PbandiTProgettoVO progettoVO = genericDAO.findSingleWhere(new PbandiTProgettoVO(new BigDecimal(idProgetto)));
		progettoVO.setNoteChiusuraProgetto(noteChiusura);
		progettoVO.setDtChiusuraProgetto(DateUtil.getSysdate());
		progettoVO.setIdStatoProgetto(decodificheManager.decodeDescBreve(PbandiDStatoProgettoVO.class, descBreveStatoProgetto));
		try {
			progettoVO.setIdUtenteAgg(new BigDecimal(idUtente));
			genericDAO.update(progettoVO);			
			/* Controllare se per ID progetto corrente esiste su PBANDI_T_PROGETTO un record tale 
			che PBANDI_T_PROGETTO.ID_PROGETTO_FINANZ è valorizzato con ID progetto corrente. */
			PbandiTProgettoVO cond = new PbandiTProgettoVO();
			cond.setIdProgettoFinanz(new BigDecimal(idProgetto));
			PbandiTProgettoVO progettoVOfinanz = genericDAO.findSingleOrNoneWhere(cond);
			
			if(progettoVOfinanz!=null){
				/* In caso positivo, aggiornare lo Stato e le altre informazioni del record così reperito con 
				gli stessi dati con cui e' stato aggiornato il record relativo al progetto corrente. */
				LOG.debug(" TROVATO progettoVOfinanz.idProgetto="+progettoVOfinanz.getIdProgetto());			
				progettoVOfinanz.setNoteChiusuraProgetto(noteChiusura);
				progettoVOfinanz.setDtChiusuraProgetto(DateUtil.getSysdate());
				progettoVOfinanz.setIdStatoProgetto(decodificheManager.decodeDescBreve(PbandiDStatoProgettoVO.class, descBreveStatoProgetto));
				progettoVOfinanz.setIdUtenteAgg(new BigDecimal(idUtente));				
				genericDAO.update(progettoVOfinanz);			
			}else{
				LOG.debug(" progettoVOfinanz NULL");
			}
			
			/* VN: Cambio lo stato del conto economico master  solo se il suo stato e' IPG */
			ContoEconomicoDTO ce = contoEconomicoManager.findContoEconomicoMaster(NumberUtil.toBigDecimal(idProgetto));
			LOG.debug("ce.getDescBreveStatoContoEconom="+ce.getDescBreveStatoContoEconom());
			
			if (Constants.STATO_CONTO_ECONOMICO_IN_PROPOSTA_PER_GESTIONE_OPERATIVA.equalsIgnoreCase(ce.getDescBreveStatoContoEconom())) {
				contoEconomicoManager.aggiornaStatoContoEconomico(NumberUtil.toLong(ce.getIdContoEconomico()), Constants.STATO_CONTO_ECONOMICO_APPROVATO, NumberUtil.toBigDecimal(idUtente));
			}	
			LOG.info(prf + " END");
			return Boolean.TRUE;
		} catch (Exception e) {
			LOG.error("Errore durante la update del progetto " + idProgetto + " con stato " + descBreveStatoProgetto, e);
			LOG.info(prf + " END");
			return Boolean.FALSE;
		}
	}

	@Override
	public EsitoOperazioneChiudiProgetto chiudiProgettoDiUfficio(Long idUtente, String idIride, Long idProgetto, String noteChiusura) throws Exception {
		String prf = "[ChiusuraProgettoDAOImpl::chiudiProgettoDiUfficio]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);
		EsitoOperazioneChiudiProgetto esito = new EsitoOperazioneChiudiProgetto();
		esito.setEsito(doChiudiProgetto(idProgetto, idUtente,noteChiusura, Constants.STATO_PROGETTO_CHIUSO_DI_UFFICIO));
		try {
			LOG.warn("\n\n\n############################ NEOFLUX CHIUS_UFF_PROG ##############################\n");
			neofluxBusiness.endAttivita(idUtente, idIride, idProgetto,Task.CHIUS_UFF_PROG);
			LOG.warn("############################ NEOFLUX CHIUS_UFF_PROG ##############################\n\n\n\n");
			
			List<MetaDataVO>metaDatas=new ArrayList<MetaDataVO>();
			String descrBreveTemplateNotifica=Notification.NOTIFICA_CHIUSURA_DUFFICIO_DEL_PROGETTO;
			MetaDataVO metadata1=new MetaDataVO(); 
			metadata1.setNome(Notification.DATA_CHIUSURA_PROGETTO);
			metadata1.setValore( DateUtil.getDate(new Date()));
			metaDatas.add(metadata1);		
			LOG.info("calling genericDAO.callProcedure().putNotificationMetadata for CHIUS_UFF_PROG....");
			genericDAO.callProcedure().putNotificationMetadata(metaDatas);
			
			LOG.info("calling genericDAO.callProcedure().sendNotificationMessage for CHIUS_UFF_PROG....");
			genericDAO.callProcedure().sendNotificationMessage(BigDecimal.valueOf(idProgetto),descrBreveTemplateNotifica,Notification.BENEFICIARIO,idUtente);
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error("Errore ", e);
			throw e;
		}  
	}
	
}
