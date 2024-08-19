/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbweberog.integration.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.ErogazioneNonModificabileVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.Condition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.GreaterThanOrEqualCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTFideiussioneVO;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.pbandiutil.common.RegoleConstants;
import it.csi.pbandi.pbweberog.dto.EsitoOperazioni;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FideiussioneDTO;
import it.csi.pbandi.pbweberog.dto.fideiussioni.FideiussioneEsitoGenericoDTO;
import it.csi.pbandi.pbweberog.integration.dao.FideiussioneDAO;
import it.csi.pbandi.pbweberog.util.BeanUtil;
import it.csi.pbandi.pbweberog.util.Constants;
import it.csi.pbandi.pbweberog.util.ErrorMessages;
import it.csi.pbandi.pbweberog.util.MessageConstants;

@Component
public class FideiussioneDAOImpl extends JdbcDaoSupport implements FideiussioneDAO {
	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);
	@Autowired
	protected BeanUtil beanUtil;
	
	@Autowired
	private GenericDAO genericDAO;
	
	@Autowired
	private RegolaManager regolaManager;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}
	
	@Autowired
	public FideiussioneDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.genericDAO = new GenericDAO(dataSource);
	}
	
	@Override
	@Transactional
	public EsitoOperazioni isProgettoGestibile(Long idUtente, String idIride, Long idProgetto) throws UnrecoverableException {
		String prf = "[FideiussioneDAOImpl::isProgettoGestibile]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

			FideiussioneEsitoGenericoDTO esito = new FideiussioneEsitoGenericoDTO();
			esito.setEsito(regolaManager.isRegolaApplicabileForProgetto(idProgetto, RegoleConstants.BR14_GESTIONE_FIDEIUSSIONE_DISPONIBILE));

			EsitoOperazioni res = new EsitoOperazioni();
			res.setEsito(esito.getEsito());
			LOG.info(prf + " END");
			return res;
		} catch (Exception e) {
			throw new UnrecoverableException("Impossibile effettuare la ricerca, il progetto non è gestibile.", e);
		}  
	}
	
	@Override
	@Transactional
	public FideiussioneDTO[] findFideiussioni(Long idUtente, String idIride, Long idProgetto,
			FideiussioneDTO fideiussioneFiltro) throws UnrecoverableException {
		String prf = "[FideiussioneDAOImpl::findFideiussioni]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride",
					"idProgetto" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					idIride, fideiussioneFiltro);

			PbandiTFideiussioneVO query = beanUtil.transform(fideiussioneFiltro, PbandiTFideiussioneVO.class);
			query.setDtDecorrenza(null);
			query.setDtScadenza(null);
			PbandiTFideiussioneVO dateQuery = beanUtil.transform(fideiussioneFiltro, PbandiTFideiussioneVO.class,
					new HashMap<String,String>(){{
						put("dtDecorrenza","dtDecorrenza");
						put("dtScadenza","dtScadenza");
					}});
			List<PbandiTFideiussioneVO> tipi = getGenericDAO().where(Condition.filterBy(query).and(new GreaterThanOrEqualCondition<PbandiTFideiussioneVO>(dateQuery))).select();
			FideiussioneDTO[] result = new FideiussioneDTO[tipi.size()];

			beanUtil.valueCopy(tipi.toArray(), result);
			LOG.info(prf + " END");
			return result;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare la ricerca.", e);
		}  
	}

	@Override
	@Transactional
	public FideiussioneEsitoGenericoDTO eliminaFideiussione(Long idUtente, String idIride, Long idFideiussione) throws UnrecoverableException {
		String prf = "[FideiussioneDAOImpl::eliminaFideiussione]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride",
					"idFideiussione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					idIride, idFideiussione);

			FideiussioneEsitoGenericoDTO esito = new FideiussioneEsitoGenericoDTO();

			if (isFideiussioneModificabile(idFideiussione)) {
				PbandiTFideiussioneVO vo = new PbandiTFideiussioneVO();
				vo.setIdFideiussione(new BigDecimal(idFideiussione));
				esito.setEsito(genericDAO.delete(vo));
				if (esito.getEsito()) {
					esito
							.setMessage(Constants.CANCELLAZIONE_AVVENUTA_CON_SUCCESSO);
				} else {
					esito.setMessage(Constants.ERRORE_SERVER);
				}
			} else {
				esito.setEsito(false);
				esito.setMessage(ErrorMessages.FIDEIUSSIONE_NON_ELIMINABILE);
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			throw new UnrecoverableException(
					"Impossibile effettuare l'eliminazione.", e);
		}  
	}

	private boolean isFideiussioneModificabile(Long idFideiussione) {
		ErogazioneNonModificabileVO check = new ErogazioneNonModificabileVO();
		check.setIdFideiussione(new BigDecimal(idFideiussione));
		return genericDAO.findListWhere(check).isEmpty();
	}

	@Override
	@Transactional
	public FideiussioneEsitoGenericoDTO aggiornaInserisciFideiussione(Long idUtente, String idIride,
			FideiussioneDTO fideiussione) throws UnrecoverableException {
		String prf = "[FideiussioneDAOImpl::aggiornaInserisciFideiussione]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride",
					"fideiussione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente,
					idIride, fideiussione);

			FideiussioneEsitoGenericoDTO esito = new FideiussioneEsitoGenericoDTO();

			if (fideiussione.getIdFideiussione() == null
					|| fideiussione.getIdFideiussione() <= 0) {
				// inserimento
				PbandiTFideiussioneVO vo = beanUtil.transform(fideiussione,
						PbandiTFideiussioneVO.class);
				vo.setIdUtenteIns(new BigDecimal(idUtente));
				vo = genericDAO.insert(vo);
				esito.setEsito(true);
				esito.setMessage(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				esito.setParams(new String[] {vo.getIdFideiussione().toString()});
			} else {
				// modifica
				PbandiTFideiussioneVO vo = new PbandiTFideiussioneVO();
				vo.setIdFideiussione(new BigDecimal(fideiussione
						.getIdFideiussione()));
				PbandiTFideiussioneVO oldVO = genericDAO.findSingleWhere(vo);
				vo = beanUtil.transform(fideiussione,
						PbandiTFideiussioneVO.class);

				if (vo.getImportoFideiussione().equals(
						oldVO.getImportoFideiussione())
						|| isFideiussioneModificabile(fideiussione
								.getIdFideiussione())) {
					// posso modificare
					vo.setIdUtenteIns(oldVO.getIdUtenteIns());
					vo.setIdUtenteAgg(new BigDecimal(idUtente));
					genericDAO.updateNullables(vo);
					esito.setEsito(true);
					esito.setMessage(MessageConstants.SALVATAGGIO_AVVENUTO_CON_SUCCESSO);
				} else {
					esito.setEsito(false);
					esito.setMessage(ErrorMessages.ERRORE_SERVER);
				}
			}
			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			logger.error("Errore "+e.getMessage(), e);
			throw new UnrecoverableException(
					"Impossibile aggiornare o inserire fideiussione ", e);
		} 
	}

	@Override
	@Transactional
	public FideiussioneDTO dettaglioFideiussione(Long idUtente, String idIride, Long idFideiussione) throws UnrecoverableException {
		String prf = "[FideiussioneDAOImpl::dettaglioFideiussione]";
		LOG.info(prf + " BEGIN");
		try {
			String[] nameParameter = { "idUtente", "idIride", "idFideiussione" };
			ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idFideiussione);
			PbandiTFideiussioneVO query = new PbandiTFideiussioneVO();
			query.setIdFideiussione(new BigDecimal(idFideiussione));
			PbandiTFideiussioneVO dettaglio = getGenericDAO().findSingleWhere( query);
			LOG.info(prf + " END");
			return beanUtil.transform(dettaglio, FideiussioneDTO.class);
		} catch (Exception e) {
			throw new UnrecoverableException("Impossibile effettuare la ricerca.", e);
		}  
	}

}
