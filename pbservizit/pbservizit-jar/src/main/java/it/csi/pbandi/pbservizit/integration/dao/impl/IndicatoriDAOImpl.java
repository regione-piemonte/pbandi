/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.pbandi.filestorage.business.api.impl.FileApiServiceImpl;
import it.csi.pbandi.filestorage.exceptions.IncorrectUploadPathException;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.IndicatoriManager;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.NeofluxBusinessImpl;
import it.csi.pbandi.pbservizit.pbandisrv.business.neoflux.Task;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.IndicatoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.dto.gestioneindicatori.TipoIndicatoreDTO;
import it.csi.pbandi.pbservizit.pbandisrv.exception.FormalParameterException;
import it.csi.pbandi.pbservizit.pbandisrv.util.validation.ValidatorInput;
import it.csi.pbandi.pbservizit.security.UserInfoSec;
import it.csi.pbandi.pbservizit.dto.indicatori.EsitoSaveIndicatori;
import it.csi.pbandi.pbservizit.dto.indicatori.IndicatoreItem;
import it.csi.pbandi.pbservizit.dto.indicatori.InfoIndicatore;
import it.csi.pbandi.pbservizit.integration.dao.IndicatoriDAO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.InfoIndicatoriRowMapper;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.dao.GenericDAO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.jdbctemplate.vo.IndicatoreSifVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.util.FilterCondition;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiRDomandaIndicatoriVO;
import it.csi.pbandi.pbservizit.pbandisrv.integration.db.vo.PbandiTProgettoVO;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.DateUtil;
import it.csi.pbandi.pbservizit.util.FileSqlUtil;
import it.csi.pbandi.pbservizit.util.NumberUtil;

@Component
public class IndicatoriDAOImpl extends JdbcDaoSupport implements IndicatoriDAO {
	private static final int MAX_SCALE_INDICATORI = 5;

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	@Autowired
	private GenericDAO genericDAO;

	@Autowired
	private NeofluxBusinessImpl neofluxBusinessImpl;

	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	protected FileApiServiceImpl fileApiServiceImpl;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Autowired
	private IndicatoriManager indicatoriManager;

	@Autowired
	public IndicatoriDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		try {
			fileApiServiceImpl = new FileApiServiceImpl(Constants.ROOT_FILE_SYSTEM);
		} catch (IncorrectUploadPathException e) {
			e.printStackTrace();
		}
		this.genericDAO = new GenericDAO(dataSource);
	}

	@Override
	@Transactional
	public String findCodiceProgetto(Long idProgetto) {
		String prf = "[IndicatoriDAOImpl::findCodiceProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = "SELECT CODICE_VISUALIZZATO FROM PBANDI_T_PROGETTO WHERE ID_PROGETTO = ?";
			Object[] param = new Object[] { idProgetto };
			String codiceProgetto = getJdbcTemplate().queryForObject(sql, param, String.class);
			LOG.info(prf + " END");
			return codiceProgetto;
		} catch (Exception e) {
			LOG.info(prf + " END");
			throw e;
		}
	}

	@Override
	@Transactional
	public TipoIndicatoreDTO[] findIndicatoriGestione(Long idUtente, String idIride, Long idProgetto,
			boolean isMonitoraggio) throws FormalParameterException, UnrecoverableException {
		String prf = "[IndicatoriDAOImpl::findIndicatoriGestione]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

		TipoIndicatoreDTO[] tipiIndicatore = null;
		try {

			tipiIndicatore = indicatoriManager.findIndicatoriRimappatiGestione(idProgetto, isMonitoraggio);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		LOG.info(prf + " END");
		return tipiIndicatore;
	}

	@Override
	@Transactional
	public ArrayList<IndicatoreItem> findIndicatoriGestioneSif(Long idUtente, String idIride, Long idProgetto,
			boolean isMonitoraggio) throws FormalParameterException, UnrecoverableException {
		String prf = "[IndicatoriDAOImpl::findIndicatoriGestioneSif]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

		ArrayList<IndicatoreItem> indicatori = new ArrayList<IndicatoreItem>();
		try {

			List<IndicatoreSifVO> result = indicatoriManager.findIndicatoriSif(isMonitoraggio, idProgetto);

			if (result != null && result.size() > 0) {
				Long idBando = getIdBandoByIdProgetto(idProgetto);
				for (IndicatoreSifVO vo : result) {

					boolean trovato = false;
					for (IndicatoreItem i : indicatori) {
						if (i.getIdTipoIndicatore().equals(new Long(vo.getId_tipo_indicatore().longValue()))) {
							trovato = true;
						}
					}
					if (indicatori.size() == 0 || !trovato) {
						IndicatoreItem tipo = new IndicatoreItem();
						tipo.setIdTipoIndicatore(new Long(vo.getId_tipo_indicatore().longValue()));
						tipo.setDescTipoIndicatore(vo.getDesc_tipo_indicatore());
						tipo.setIsValoreAggiornamentoEditable(false);
						tipo.setIsValoreFinaleEditable(false);
						tipo.setIsValoreInizialeEditable(false);
						tipo.setCodIgrue(vo.getCod_igrue());
						tipo.setIsTipoIndicatore(true);
						indicatori.add(tipo);
					}

					IndicatoreItem item = new IndicatoreItem();
					item.setIdIndicatore(new Long(vo.getId_indicatori().longValue()));
					item.setIdTipoIndicatore(new Long(vo.getId_tipo_indicatore().longValue()));
					item.setDescIndicatore(vo.getDesc_indicatore());
					item.setDescTipoIndicatore(vo.getDesc_tipo_indicatore());
					item.setDescUnitaMisura(vo.getDesc_unita_misura());
					if (vo.getValoreIniziale() != null) {
						item.setValoreIniziale(NumberUtil.getStringValueItalianFormat(vo.getValoreIniziale(),
								Constants.MAX_SCALE_INDICATORI_MONIT));
					}
					if (vo.getValoreFinale() != null) {
						item.setValoreFinale(NumberUtil.getStringValueItalianFormat(vo.getValoreFinale(),
								Constants.MAX_SCALE_INDICATORI_MONIT));
					}
					if (vo.getValoreAggiornamento() != null) {
						item.setValoreAggiornamento(NumberUtil.getStringValueItalianFormat(vo.getValoreAggiornamento(),
								Constants.MAX_SCALE_INDICATORI_MONIT));
					}
					item.setIsValoreAggiornamentoEditable(false);
					item.setIsValoreFinaleEditable(false);
					item.setIsValoreInizialeEditable(false);
					item.setCodIgrue(vo.getCod_igrue());
					item.setIsTipoIndicatore(false);
					indicatori.add(item);
					
					InfoIndicatore info = getInfoIndicatore(idBando, item.getIdIndicatore());

					item.setInfoIniziale(info.getInfoIniziale());
					item.setInfoFinale(info.getInfoFinale());
				}
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		LOG.info(prf + " END");
		return indicatori;
	}

	@Override
	@Transactional
	public TipoIndicatoreDTO[] findIndicatoriAvvio(Long idUtente, String idIride, Long idProgetto,
			boolean isMonitoraggio) throws FormalParameterException, UnrecoverableException {
		String prf = "[IndicatoriDAOImpl::findIndicatoriAvvio]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);

		TipoIndicatoreDTO[] tipiIndicatore = null;
		try {

			tipiIndicatore = indicatoriManager.findIndicatoriRimappatiAvvio(idProgetto, isMonitoraggio);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		LOG.info(prf + " END");
		return tipiIndicatore;
	}

	@Override
	@Transactional
	public Boolean esisteCFP(UserInfoSec userInfo, Long idProgetto)
			throws FormalParameterException, UnrecoverableException {
		String prf = "[IndicatoriDAOImpl::esisteCFP]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, userInfo.getIdUtente(), userInfo.getIdIride(), idProgetto);

		Boolean esito = false;
		try {
			esito = indicatoriManager.esisteCFP(idProgetto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		logger.info("esisteCFP(): esito per id progetto " + idProgetto + " = " + esito);
		return esito;
	}

	@Override
	@Transactional
	public Boolean esisteDsFinale(UserInfoSec userInfo, Long idProgetto)
			throws FormalParameterException, UnrecoverableException {
		String prf = "[IndicatoriDAOImpl::esisteDsFinale]";
		LOG.info(prf + " BEGIN");
		String[] nameParameter = { "idUtente", "identitaDigitale", "idProgetto" };
		ValidatorInput.verifyNullValue(nameParameter, userInfo.getIdUtente(), userInfo.getIdIride(), idProgetto);

		Boolean esito = false;
		try {
			esito = indicatoriManager.esisteDsFinale(idProgetto);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new UnrecoverableException(e.getMessage(), e);
		}
		LOG.info(prf + ": esito per id progetto " + idProgetto + " = " + esito);
		LOG.info(prf + " END");
		return esito;
	}

//	
//	@Override
//	@Transactional
//	public Boolean eliminaIndicatoriDomanda(Long idUtente, String idIride, Long idProgetto) throws Exception {
//		String prf = "[IndicatoriDAOImpl::eliminaIndicatoriDomanda]";
//		LOG.info(prf + " BEGIN");
//		String[] nameParameter = { "idUtente", "idIride", "idProgetto" };
//		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto);
//		
//		try {
//			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(new BigDecimal(idProgetto));
//			pbandiTProgettoVO = getGenericDAO().findSingleWhere(pbandiTProgettoVO);
//			BigDecimal idDomanda = pbandiTProgettoVO.getIdDomanda();
//
//			PbandiRDomandaIndicatoriVO pbandiRDomandaIndicatoriVO = new PbandiRDomandaIndicatoriVO();
//			pbandiRDomandaIndicatoriVO.setIdDomanda(idDomanda);
//			
//			genericDAO.deleteWhere(new FilterCondition<PbandiRDomandaIndicatoriVO>(pbandiRDomandaIndicatoriVO));
//			
//			LOG.info(prf + " END");
//			return true;
//		} catch (Exception e) {
//			LOG.error(e.getMessage(), e);
//			throw e;
////			throw new UnrecoverableException(e.getMessage(), e);
//		}  
//			
//	}

	@Override
	@Transactional
	public EsitoSaveIndicatori saveIndicatoriGestione(Long idUtente, String idIride, Long idProgetto,
			TipoIndicatoreDTO[] tipoIndicatori) throws Exception {
		String prf = "[IndicatoriDAOImpl::saveIndicatoriGestione]";
		LOG.info(prf + " BEGIN");
		EsitoSaveIndicatori esitoSaveIndicatori = new EsitoSaveIndicatori();
		String[] nameParameter = { "idUtente", "idIride", "idDomanda", "tipoIndicatori" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, tipoIndicatori);

		try {
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(new BigDecimal(idProgetto));
			pbandiTProgettoVO = getGenericDAO().findSingleWhere(pbandiTProgettoVO);
			BigDecimal idDomanda = pbandiTProgettoVO.getIdDomanda();

			boolean isAvvio = false;

			inserisciIndicatori(tipoIndicatori, idDomanda, isAvvio, new BigDecimal(idUtente));

			esitoSaveIndicatori.setMessaggi(new String[] { Constants.MSG_SALVA_SUCCESSO });
			esitoSaveIndicatori.setSuccesso(true);
			LOG.info(prf + " END");
			return esitoSaveIndicatori;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
//			throw new UnrecoverableException(e.getMessage(), e);
		}

	}

	private void inserisciIndicatori(TipoIndicatoreDTO[] tipoIndicatori, BigDecimal idDomanda, boolean isAvvio,
			BigDecimal idUtente) throws Exception {
		String prf = "[IndicatoriDAOImpl::inserisciIndicatori]";
		LOG.info(prf + " START");

		PbandiRDomandaIndicatoriVO vo = new PbandiRDomandaIndicatoriVO();
		vo.setIdDomanda(idDomanda);

		genericDAO.deleteWhere(new FilterCondition<PbandiRDomandaIndicatoriVO>(vo));
//		if(res) {

		for (TipoIndicatoreDTO tipoIndicatoreDTO : tipoIndicatori) {
			IndicatoreDTO[] indicatori = tipoIndicatoreDTO.getIndicatori();
			LOG.info(prf + " Length tipoIndicatori: " + indicatori.length);
			for (IndicatoreDTO indicatoreDTO : indicatori) {
				PbandiRDomandaIndicatoriVO pbandiRDomandaIndicatoriVO = new PbandiRDomandaIndicatoriVO();
				Long idIndicatore = indicatoreDTO.getIdIndicatore();
				LOG.info(prf + "idINdicatori = " + idIndicatore);
				pbandiRDomandaIndicatoriVO.setIdIndicatori(new BigDecimal(idIndicatore));
				pbandiRDomandaIndicatoriVO.setIdDomanda(idDomanda);
				BigDecimal valoreIniziale = null;
				BigDecimal valoreAggiornamento = null;
				BigDecimal valoreFinale = null;
				if (indicatoreDTO.getFlagNonApplicabile()) {
					pbandiRDomandaIndicatoriVO.setFlagNonApplicabile(Constants.FLAG_TRUE);
				} else {
					pbandiRDomandaIndicatoriVO.setFlagNonApplicabile(Constants.FLAG_FALSE);
					if (indicatoreDTO.getValoreIniziale() != null)
						valoreIniziale = NumberUtil.createScaledBigDecimal(indicatoreDTO.getValoreIniziale(),
								MAX_SCALE_INDICATORI);

					if (indicatoreDTO.getValoreAggiornamento() != null)
						valoreAggiornamento = NumberUtil.createScaledBigDecimal(indicatoreDTO.getValoreAggiornamento(),
								MAX_SCALE_INDICATORI);

					if (indicatoreDTO.getValoreFinale() != null)
						valoreFinale = NumberUtil.createScaledBigDecimal(indicatoreDTO.getValoreFinale(),
								MAX_SCALE_INDICATORI);
				}
				if (isAvvio) {
					valoreAggiornamento = null;
					valoreFinale = null;
				}
				pbandiRDomandaIndicatoriVO.setValoreProgIniziale(valoreIniziale);
				pbandiRDomandaIndicatoriVO.setValoreProgAgg(valoreAggiornamento);
				pbandiRDomandaIndicatoriVO.setValoreConcluso(valoreFinale);

				java.sql.Date data = DateUtil.getSysdate();
				pbandiRDomandaIndicatoriVO.setDtInserimento(data);
				pbandiRDomandaIndicatoriVO.setDtAggiornamento(data);
				pbandiRDomandaIndicatoriVO.setIdUtenteIns(idUtente);
				genericDAO.insert(pbandiRDomandaIndicatoriVO);
				LOG.info(prf + " END");
			}
		}
//		} else {
//			throw new Exception("NON POSSO ESEGUIRE DELETE: PBANDI_R_DOMANDA_INDICATORI, ID_DOMANDA:" + " " + vo.getIdDomanda());
//		}

	}

	@Override
	@Transactional
	public EsitoSaveIndicatori deleteDomandaIndicatori(Long idUtente, String idIride, Long idProgetto,
			TipoIndicatoreDTO[] listaIndicatori) throws Exception {
		String prf = "[IndicatoriDAOImpl::deleteDomandaIndicatori]";
		LOG.info(prf + " START");
		try {
			EsitoSaveIndicatori esito = new EsitoSaveIndicatori();
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(new BigDecimal(idProgetto));
			pbandiTProgettoVO = getGenericDAO().findSingleWhere(pbandiTProgettoVO);
			BigDecimal idDomanda = pbandiTProgettoVO.getIdDomanda();

			PbandiRDomandaIndicatoriVO vo = new PbandiRDomandaIndicatoriVO();
			vo.setIdDomanda(idDomanda);
			boolean res = genericDAO.deleteWhere(new FilterCondition<PbandiRDomandaIndicatoriVO>(vo));
			if (res)
				esito.setSuccesso(true);
			else
				esito.setSuccesso(false);

			LOG.info(prf + " END");
			return esito;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}

	}

	@Override
	@Transactional
	public EsitoSaveIndicatori saveIndicatoriAvvio(Long idUtente, String idIride, Long idProgetto,
			TipoIndicatoreDTO[] listaIndicatori) throws Exception {
		String prf = "[IndicatoriDAOImpl::saveIndicatoriAvvio]";
		LOG.info(prf + " BEGIN");
		EsitoSaveIndicatori esitoSaveIndicatori = new EsitoSaveIndicatori();
		String[] nameParameter = { "idUtente", "idIride", "idProgetto", "tipoIndicatori" };

		ValidatorInput.verifyNullValue(nameParameter, idUtente, idIride, idProgetto, listaIndicatori);

		try {
			PbandiTProgettoVO pbandiTProgettoVO = new PbandiTProgettoVO(new BigDecimal(idProgetto));
			pbandiTProgettoVO = getGenericDAO().findSingleWhere(pbandiTProgettoVO);
			BigDecimal idDomanda = pbandiTProgettoVO.getIdDomanda();

			boolean isAvvio = true;

			inserisciIndicatori(listaIndicatori, idDomanda, isAvvio, new BigDecimal(idUtente));

			logger.warn("\n\n############################ NEOFLUX INDICATORI AVVIO ##############################\n");
			neofluxBusinessImpl.endAttivita(idUtente, idIride, idProgetto, Task.CARICAM_INDIC_AVVIO);
			logger.warn("############################ NEOFLUX INDICATORI AVVIO ##############################\n\n\n\n");

			LOG.info(prf + " END");
			esitoSaveIndicatori.setMessaggi(new String[] { Constants.MSG_SALVA_SUCCESSO });
			esitoSaveIndicatori.setSuccesso(true);
			return esitoSaveIndicatori;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
//			throw new UnrecoverableException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public InfoIndicatore getInfoIndicatore(Long idBando, Long idIndicatore) {
		String prf = "[IndicatoriDAOImpl::getInfoIndicatore]";
		LOG.info(prf + " BEGIN");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM PBANDI_R_BANDO_INDICATORI WHERE ID_BANDO = ").append(idBando)
					.append(" AND ID_INDICATORI = ").append(idIndicatore);
			InfoIndicatore infoIndicatore = getJdbcTemplate().queryForObject(sql.toString(),
					new InfoIndicatoriRowMapper());
			LOG.info(prf + "infoIniziale: " + infoIndicatore.getInfoIniziale() + " infoFinale: "
					+ infoIndicatore.getInfoFinale());
			LOG.info(prf + " END");
			return infoIndicatore;
		} catch (Exception e) {
			throw e;
		}

	}

	private Long getIdBandoByIdProgetto(Long idProgetto) {
		String prf = "[IndicatoriDAOImpl::getIdBandoByIdProgetto]";
		LOG.info(prf + " BEGIN");
		try {
			String sql = "SELECT ptb.ID_BANDO \r\n" + "FROM PBANDI_T_BANDO ptb \r\n"
					+ "JOIN PBANDI_R_BANDO_LINEA_INTERVENT prbli ON prbli.ID_BANDO = ptb.id_bando\r\n"
					+ "JOIN PBANDI_T_DOMANDA ptd ON ptd.PROGR_BANDO_LINEA_INTERVENTO = prbli.PROGR_BANDO_LINEA_INTERVENTO  \r\n"
					+ "JOIN PBANDI_T_PROGETTO ptp ON ptp.id_domanda = ptd.ID_DOMANDA \r\n"
					+ "WHERE ptp.ID_PROGETTO = ?";
			LOG.info(prf + "<idProgetto> : " + idProgetto);
			Object[] args = new Object[] { idProgetto };
			Long idBando = getJdbcTemplate().queryForObject(sql, args, Long.class);
			LOG.info(prf + "idBando: " + idBando);
			LOG.info(prf + " END");
			return idBando;
		} catch (Exception e) {
			throw e;
		}
	}

}
