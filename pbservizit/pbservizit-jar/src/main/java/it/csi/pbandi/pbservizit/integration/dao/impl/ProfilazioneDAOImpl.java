/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.integration.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import it.csi.pbandi.pbservizit.business.intf.ErrorConstants;
import it.csi.pbandi.pbservizit.dto.AvvisoUtenteDTO;
import it.csi.pbandi.pbservizit.dto.profilazione.BeneficiarioCount;
import it.csi.pbandi.pbservizit.dto.profilazione.Ruolo;
import it.csi.pbandi.pbservizit.dto.profilazione.UserInfoDTO;
import it.csi.pbandi.pbservizit.exception.DaoException;
import it.csi.pbandi.pbservizit.exception.UtenteException;
import it.csi.pbandi.pbservizit.integration.dao.ProfilazioneDAO;
import it.csi.pbandi.pbservizit.integration.dao.TracciamentoDAO;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeanRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.BeneficiarioSoggettoRuoloRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.PbandiBeneficiariRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.PbandiTSoggettoRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.PbandiTUtenteRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.SoggettoPermessoTipoAnagraficaRowMapper;
import it.csi.pbandi.pbservizit.integration.dao.impl.rowmapper.TipoAnagraficaRowMapper;
import it.csi.pbandi.pbservizit.integration.vo.AvvisoUtenteVO;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioSoggettoRuoloVO;
import it.csi.pbandi.pbservizit.integration.vo.BeneficiarioVO;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTSoggettoVO;
import it.csi.pbandi.pbservizit.integration.vo.PbandiTUtenteVO;
import it.csi.pbandi.pbservizit.integration.vo.SoggettoPermessoTipoAnagraficaVO;
import it.csi.pbandi.pbservizit.integration.vo.TipoAnagraficaVO;
import it.csi.pbandi.pbservizit.pbandisrv.business.manager.RegolaManager;
import it.csi.pbandi.pbservizit.security.BeneficiarioSec;
import it.csi.pbandi.pbservizit.util.BeanUtil;
import it.csi.pbandi.pbservizit.util.Constants;
import it.csi.pbandi.pbservizit.util.FileSqlUtil;
import it.csi.pbandi.pbservizit.util.NumberUtil;
import it.csi.pbandi.pbservizit.util.OperazioniConstants;

@Component
public class ProfilazioneDAOImpl extends JdbcDaoSupport implements ProfilazioneDAO {

	private Logger LOG = Logger.getLogger(Constants.COMPONENT_NAME);

	@Autowired
	private RegolaManager regolaManager;

	@Autowired
	TracciamentoDAO tracciamentoDAO;

	@Autowired
	protected BeanUtil beanUtil;

	@Autowired
	protected FileSqlUtil fileSqlUtil;

	@Autowired
	public ProfilazioneDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	public ProfilazioneDAOImpl() {
	}

	private static final Map<String, String> TIPO_ANAGRAFICA_VO_TO_DTO = new HashMap<String, String>();
//	private static final Map<String, String> BENEFICIARIO_VO_TO_DTO = new HashMap<String, String>();

	static {
		TIPO_ANAGRAFICA_VO_TO_DTO.put("idTipoAnagrafica", "id");
		TIPO_ANAGRAFICA_VO_TO_DTO.put("descBreveTipoAnagrafica", "descrizioneBreve");
		TIPO_ANAGRAFICA_VO_TO_DTO.put("descRuoloHelp", "ruoloHelp");
		TIPO_ANAGRAFICA_VO_TO_DTO.put("descTipoAnagrafica", "descrizione");
//		BENEFICIARIO_VO_TO_DTO.put("idSoggettoBeneficiario", "id_soggetto");
//		BENEFICIARIO_VO_TO_DTO.put("codiceFiscaleBeneficiario", "codiceFiscale");
//		BENEFICIARIO_VO_TO_DTO.put("denominazioneBeneficiario", "descrizione");
	}

	@Override
	public UserInfoDTO getInfoUtente(String codFisc, String nome, String cognome) throws UtenteException {
		String prf = "[ProfilazioneDAOImpl::getInfoUtente]";
		LOG.info(prf + " BEGIN");

		LOG.info(prf + "codFisc: " + codFisc);
		LOG.info(prf + "nome: " + nome);
		LOG.info(prf + "cognome: " + cognome);

		UserInfoDTO userInfo = new UserInfoDTO();

		userInfo.setCognome(cognome);
		userInfo.setNome(nome);
		userInfo.setCodiceFiscale(codFisc);
		userInfo.setIsIncaricato(Boolean.FALSE);
//		userInfo.setRuoloHelp(identita.getRuoloHelp());

		try {

			PbandiTUtenteVO utente = findUtente(userInfo.getCodiceFiscale());
			if (utente == null) {
				throw new UtenteException("Utente non autorizzato");
			}
			userInfo.setIdUtente(beanUtil.transform(utente.getIdUtente(), Long.class));
			LOG.info(prf + " utente=" + utente);

			String operazione = OperazioniConstants.PROFILAZIONE_GETINFOUTENTE;
			String esito = "S";
			Long idTracciamento = tracciamentoDAO.insertTraccia(operazione, userInfo.getIdUtente(), esito);
			LOG.info(prf + " idTracciamento=" + idTracciamento);

			Long t1 = System.currentTimeMillis();

			PbandiTSoggettoVO soggetto = findSoggetto(userInfo.getCodiceFiscale());
			LOG.info(prf + " soggetto=" + soggetto);

			userInfo.setIdSoggetto(NumberUtil.toLong(soggetto.getIdSoggetto()));

			List<TipoAnagraficaVO> tipiAnagrafica = findTipiAnagrafica(soggetto.getIdSoggetto());
			if (tipiAnagrafica != null && !tipiAnagrafica.isEmpty()) {
				for (TipoAnagraficaVO tipoAnagraficaVO : tipiAnagrafica) {
					LOG.info(prf + " tipiAnagrafica=" + tipoAnagraficaVO);
				}
			} else {
				LOG.info(prf + " tipiAnagrafica null or empty");
			}

			userInfo.setRuoli(beanUtil.transform(tipiAnagrafica, Ruolo.class, TIPO_ANAGRAFICA_VO_TO_DTO));

			eseguiControlliComuni(userInfo, soggetto, tipiAnagrafica);

			Long t2 = System.currentTimeMillis();
			tracciamentoDAO.updateTraccia(idTracciamento, t2 - t1);

		} catch (DaoException e) {
			throw new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
		}
		LOG.info(prf + "userInfo= " + userInfo);
		return userInfo;
	}

	/**
	 * Controlli: - che il CF sia presente nella PBANDI_T_SOGGETTO - che
	 * l'idSoggetto sia presente in PBANDI_D_TIPO_ANAGRAFICA , PBANDI_D_RUOLO_HELP ,
	 * PBANDI_R_SOGG_TIPO_ANAGRAFICA
	 * 
	 * @param userInfo
	 * @return
	 * @throws UtenteException
	 */
	private void eseguiControlliComuni(UserInfoDTO userInfo, PbandiTSoggettoVO soggetto,
			List<TipoAnagraficaVO> tipiAnagrafica) throws UtenteException {

		String prf = "[ProfilazioneDAOImpl::eseguiControlliComuni]";
		LOG.info(prf + "BEGIN");

		if (userInfo.getCodiceFiscale() == null) {
			LOG.error(prf + "Codice fiscale non valorizzato nel contesto identificativo.");
			throw new UtenteException("Codice fiscale null letto dal contesto identificativo.");
		}

		if (tipiAnagrafica == null || (tipiAnagrafica != null && tipiAnagrafica.isEmpty())) {
			LOG.error(prf + "Utente[" + userInfo.getCodiceFiscale() + "] non possiede ruoli, quindi non può accedere");
			throw new UtenteException(ErrorConstants.ERRORE_UTENTE_NON_AUTORIZZATO);
		}
		LOG.info(prf + "END");
	}

	@Override
	public PbandiTSoggettoVO findSoggetto(String codFiscale) throws DaoException {

		LOG.info("[ProfilazioneDAOImpl::findSoggetto] BEGIN");

		PbandiTSoggettoVO sogg = null;

		try {
			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT ID_SOGGETTO, CODICE_FISCALE_SOGGETTO, ID_UTENTE_INS, ID_UTENTE_AGG, ID_TIPO_SOGGETTO, DT_INSERIMENTO,");
			sql.append(" DT_AGGIORNAMENTO, RICEVENTE_TRASF");
			sql.append(" FROM PBANDI_T_SOGGETTO ");
			sql.append(" WHERE upper( CODICE_FISCALE_SOGGETTO )= '" + StringUtils.upperCase(codFiscale) + "'");
			sql.append(" AND ID_TIPO_SOGGETTO = " + Constants.VALORE_TIPO_SOGG_PERSONA_FISICA);
			LOG.debug(sql.toString());

//			PK preparementStatement

			List<PbandiTSoggettoVO> lista = getJdbcTemplate().query(sql.toString(), new PbandiTSoggettoRowMapper());
			if (lista != null && !lista.isEmpty()) {
				if (lista.size() > 1) {
					throw new IncorrectResultSizeDataAccessException(
							"DaoException while trying to read PbandiTSoggettoVO", 0, null);
				}
				sogg = lista.get(0);
			}

		} catch (EmptyResultDataAccessException e) {
			LOG.error("EmptyResultDataAccessException while trying to read PbandiTSoggettoVO", e);
			throw new DaoException("DaoException while trying to read PbandiTSoggettoVO", e);
		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error("IncorrectResultSizeDataAccessException while trying to read PbandiTSoggettoVO", e);
			throw new DaoException("DaoException while trying to read PbandiTSoggettoVO", e);
		} catch (Exception e) {
			LOG.error("Exception while trying to read PbandiTSoggettoVO", e);
			throw new DaoException("DaoException while trying to read PbandiTSoggettoVO", e);
		} finally {
			LOG.info("[ProfilazioneDAOImpl::findSoggetto] END");
		}

		return sogg;
	}

	@Override
	public List<TipoAnagraficaVO> findTipiAnagrafica(BigDecimal idSoggetto) throws DaoException {

		LOG.info("[ProfilazioneDAOImpl::findTipiAnagrafica] BEGIN");
		LOG.info("[ProfilazioneDAOImpl::findTipiAnagrafica] idSoggetto=" + idSoggetto);

		List<TipoAnagraficaVO> listTipiAnagrafica = new ArrayList<TipoAnagraficaVO>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT dta.ID_TIPO_ANAGRAFICA,dta.DESC_BREVE_TIPO_ANAGRAFICA, dta.DESC_TIPO_ANAGRAFICA, ");
			sql.append(" dta.DT_INIZIO_VALIDITA, dta.DT_FINE_VALIDITA, dta.ID_RUOLO_HELP,");
			sql.append(" dta.ID_CATEG_ANAGRAFICA, RSTA.ID_SOGGETTO, DRH.DESC_RUOLO_HELP ");
			sql.append(
					" FROM PBANDI_D_TIPO_ANAGRAFICA dta, PBANDI_D_RUOLO_HELP drh, PBANDI_R_SOGG_TIPO_ANAGRAFICA rsta ");
			sql.append(" WHERE RSTA.ID_TIPO_ANAGRAFICA=DTA.ID_TIPO_ANAGRAFICA ");
			sql.append(" AND DTA.ID_RUOLO_HELP= DRH.ID_RUOLO_HELP(+) ");
			sql.append(" AND NVL(TRUNC(dta.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) ");
			sql.append(" AND NVL(TRUNC(rsta.DT_FINE_VALIDITA), TRUNC(SYSDATE+1)) > TRUNC(SYSDATE) ");
			sql.append(" AND ID_SOGGETTO = ? ");

			LOG.info("[ProfilazioneDAOImpl::findTipiAnagrafica] sql=" + sql.toString());

			listTipiAnagrafica = getJdbcTemplate().query(sql.toString(), new Object[] { idSoggetto },
					new TipoAnagraficaRowMapper());

		} catch (EmptyResultDataAccessException e) {
			LOG.error("EmptyResultDataAccessException while trying to read findTipiAnagrafica", e);
			throw new DaoException("DaoException while trying to read findTipiAnagrafica", e);
		} catch (Exception e) {
			LOG.error("Exception while trying to read findTipiAnagrafica", e);
			throw new DaoException("DaoException while trying to read findTipiAnagrafica", e);
		} finally {
			LOG.info("[ProfilazioneDAOImpl::findTipiAnagrafica] END");
		}

		return listTipiAnagrafica;
	}

	@Override
	public PbandiTUtenteVO findUtente(String codFiscale) throws DaoException {
		LOG.info("[ProfilazioneDAOImpl::findUtente] BEGIN");

		PbandiTUtenteVO user = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT ID_UTENTE, LOGIN, PASSWORD, CODICE_UTENTE, ID_TIPO_ACCESSO, ID_SOGGETTO, DT_INSERIMENTO, ");
			sql.append("  DT_AGGIORNAMENTO, FLAG_CONSENSO_MAIL, EMAIL_CONSENSO ");
			sql.append(" FROM PBANDI_T_UTENTE ");
			sql.append(" WHERE ID_UTENTE = ( ");
			sql.append("   SELECT MAX(ID_UTENTE) FROM PBANDI_T_UTENTE ");
			sql.append("   WHERE upper (CODICE_UTENTE) = '" + StringUtils.upperCase(codFiscale) + "'");
			sql.append("    AND ID_SOGGETTO IS NOT NULL )");
			LOG.debug(sql.toString());

			List<PbandiTUtenteVO> lista = getJdbcTemplate().query(sql.toString(), new PbandiTUtenteRowMapper());
			if (lista != null && !lista.isEmpty()) {

				if (lista.size() > 1) {
					throw new IncorrectResultSizeDataAccessException(
							"DaoException while trying to read PbandiTUtenteVO", 0, null);
				}
				user = lista.get(0);
			}

		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error("IncorrectResultSizeDataAccessException while trying to read PbandiTUtenteVO", e);
			throw new DaoException("DaoException while trying to read PbandiTUtenteVO", e);
		} catch (Exception e) {
			LOG.error("Exception while trying to read PbandiTUtenteVO", e);
			throw new DaoException("DaoException while trying to read PbandiTUtenteVO", e);
		} finally {
			LOG.info("[ProfilazioneDAOImpl::findUtente] END");
		}
		return user;
	}

	@Override
	public BeneficiarioCount countBeneficiari(Long idUtente, String codiceFiscale, String ruoloIride)
			throws DaoException {
		String prf = "[ProfilazioneDAOImpl::findBeneficiari] ";
		LOG.info(prf + " BEGIN");

		String operazione = OperazioniConstants.PROFILAZIONE_FINDBENEFICIARI;
		String esito = "S";
		Long idTracciamento = tracciamentoDAO.insertTraccia(operazione, idUtente, esito);
		LOG.info(prf + " idTracciamento=" + idTracciamento);

		Long t1 = System.currentTimeMillis();

		PbandiTSoggettoVO soggettoVO = findSoggetto(codiceFiscale);

		BeneficiarioCount beneficiarioCount = findBeneficiariBySoggettoRuolo(soggettoVO.getIdSoggetto(), ruoloIride);

		Long t2 = System.currentTimeMillis();
		tracciamentoDAO.updateTraccia(idTracciamento, t2 - t1);

		LOG.info(prf + " END");
		return beneficiarioCount;
	}

	@Override
	public BeneficiarioSec findBeneficiarioByIdSoggettoBen(Long idUtente, String codiceFiscale, String ruoloIride,
			Long idSoggettoBeneficiario) throws DaoException {
		String prf = "[ProfilazioneDAOImpl::findBeneficiariByIdSoggettoBen] ";
		LOG.info(prf + " BEGIN");

		String operazione = OperazioniConstants.PROFILAZIONE_FINDBENEFICIARI;
		String esito = "S";
		Long idTracciamento = tracciamentoDAO.insertTraccia(operazione, idUtente, esito);
		LOG.info(prf + " idTracciamento=" + idTracciamento);

		Long t1 = System.currentTimeMillis();

		PbandiTSoggettoVO soggettoVO = findSoggetto(codiceFiscale);

		BeneficiarioSec beneficiario = findBeneficiariBySoggettoRuoloIdBen(soggettoVO.getIdSoggetto(), ruoloIride,
				idSoggettoBeneficiario);

		Long t2 = System.currentTimeMillis();
		tracciamentoDAO.updateTraccia(idTracciamento, t2 - t1);

		LOG.info(prf + " END");
		return beneficiario;
	}

	@Override
	public List<BeneficiarioSec> findBeneficiariByDenominazione(Long idUtente, String codiceFiscale, String ruoloIride,
			String denominazione) throws DaoException {
		String prf = "[ProfilazioneDAOImpl::findBeneficiari] ";
		LOG.info(prf + " BEGIN");

		String operazione = OperazioniConstants.PROFILAZIONE_FINDBENEFICIARI;
		String esito = "S";
		Long idTracciamento = tracciamentoDAO.insertTraccia(operazione, idUtente, esito);
		LOG.info(prf + " idTracciamento=" + idTracciamento);

		Long t1 = System.currentTimeMillis();

		PbandiTSoggettoVO soggettoVO = findSoggetto(codiceFiscale);

		List<BeneficiarioSec> beneficiariVO = findBeneficiariBySoggettoRuoloDenominazione(soggettoVO.getIdSoggetto(),
				ruoloIride, denominazione);

		Long t2 = System.currentTimeMillis();
		tracciamentoDAO.updateTraccia(idTracciamento, t2 - t1);

		LOG.info(prf + " END");
		return beneficiariVO;
	}

	private BeneficiarioCount findBeneficiariBySoggettoRuolo(BigDecimal idSoggetto, String ruoloIride)
			throws DaoException {

		String prf = "[ProfilazioneDAOImpl::findBeneficiariBySoggettoRuolo] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idSoggetto=" + idSoggetto);
		LOG.info(prf + " ruoloIride=[" + ruoloIride + "]");

		BeneficiarioCount beneficiarioCount = new BeneficiarioCount();

		try {
			// conto quanti beneficiari ho associati al profilo
			String sql = fileSqlUtil.getQuery("BeneficiarioSoggettoRuoloCountVO.sql");
			LOG.info(sql.toString());

			List<Long> counts = getJdbcTemplate().queryForList(sql.toString(),
					new Object[] { ruoloIride, idSoggetto, ruoloIride }, Long.class);

			if (counts != null && !counts.isEmpty() && counts.get(0) != null) {
				LOG.info(prf + " count beneficiari=" + counts.get(0));
				beneficiarioCount.setCount(counts.get(0));

				// se ho un solo beneficiario associato al profilo, cerco quale sia
				if (beneficiarioCount.getCount().equals(1L)) {
					try {
						sql = fileSqlUtil.getQuery("BeneficiarioSoggettoRuoloVO.sql");
						LOG.info(sql.toString());

						List<BeneficiarioSoggettoRuoloVO> listaBSR = getJdbcTemplate().query(sql.toString(),
								new Object[] { ruoloIride, idSoggetto, ruoloIride },
								new BeneficiarioSoggettoRuoloRowMapper());

						if (listaBSR != null && !listaBSR.isEmpty() && listaBSR.get(0) != null) {
							LOG.info(prf + " listaBSR.size=" + listaBSR.size());
							BeneficiarioSec ben = new BeneficiarioSec();
							ben.setCodiceFiscale(listaBSR.get(0).getCodiceFiscaleBeneficiario());
							ben.setDenominazione(listaBSR.get(0).getDenominazioneBeneficiario());
							ben.setIdBeneficiario(new Long(listaBSR.get(0).getIdSoggettoBeneficiario().longValue()));
							beneficiarioCount.setBeneficiario(ben);
						}
					} catch (FileNotFoundException e) {
						LOG.error(prf + "FileNotFoundException BeneficiarioSoggettoRuoloVO.sql", e);
						throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
					} catch (IOException e) {
						LOG.error(prf + "IOException BeneficiarioSoggettoRuoloVO.sql", e);
						throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
					} catch (Exception e) {
						LOG.error(prf + "Exception while trying to read PbandiTSoggettoVO", e);
						throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
					}
				}
			}
		} catch (FileNotFoundException e) {
			LOG.error(prf + "FileNotFoundException BeneficiarioSoggettoRuoloCountVO.sql", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
		} catch (IOException e) {
			LOG.error(prf + "IOException BeneficiarioSoggettoRuoloCountVO.sql", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PbandiTSoggettoVO", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return beneficiarioCount;
	}

	private BeneficiarioSec findBeneficiariBySoggettoRuoloIdBen(BigDecimal idSoggetto, String ruoloIride,
			Long idSoggettoBeneficiario) throws DaoException {

		String prf = "[ProfilazioneDAOImpl::findBeneficiariBySoggettoRuoloIdBen] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idSoggetto=" + idSoggetto);
		LOG.info(prf + " ruoloIride=[" + ruoloIride + "]");
		LOG.info(prf + " idSoggettoBeneficiario=" + idSoggettoBeneficiario);

		BeneficiarioSec beneficiario = null;

		try {
			// conto quanti beneficiari ho associati al profilo
			String sql = fileSqlUtil.getQuery("BeneficiarioSoggettoRuoloIdBenVO.sql");
			LOG.info(sql.toString());

			List<BeneficiarioSoggettoRuoloVO> listaBSR = getJdbcTemplate().query(sql.toString(),
					new Object[] { ruoloIride, idSoggetto, ruoloIride, idSoggettoBeneficiario },
					new BeneficiarioSoggettoRuoloRowMapper());

			if (listaBSR != null && !listaBSR.isEmpty() && listaBSR.get(0) != null) {
				LOG.info(prf + " listaBSR.size=" + listaBSR.size());
				beneficiario = new BeneficiarioSec();
				beneficiario.setCodiceFiscale(listaBSR.get(0).getCodiceFiscaleBeneficiario());
				beneficiario.setDenominazione(listaBSR.get(0).getDenominazioneBeneficiario());
				beneficiario.setIdBeneficiario(new Long(listaBSR.get(0).getIdSoggettoBeneficiario().longValue()));
			}

		} catch (FileNotFoundException e) {
			LOG.error(prf + "FileNotFoundException BeneficiarioSoggettoRuoloIdBenVO.sql", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
		} catch (IOException e) {
			LOG.error(prf + "IOException BeneficiarioSoggettoRuoloIdBenVO.sql", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read PbandiTSoggettoVO", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloVO", e);
		} finally {
			LOG.info(prf + " END");
		}

		return beneficiario;
	}

	private List<BeneficiarioSec> findBeneficiariBySoggettoRuoloDenominazione(BigDecimal idSoggetto, String ruoloIride,
			String denominazione) throws DaoException {

		String prf = "[ProfilazioneDAOImpl::findBeneficiariBySoggettoRuoloDenominazione] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idSoggetto=" + idSoggetto);
		LOG.info(prf + " ruoloIride=[" + ruoloIride + "]");
		LOG.info(prf + " denominazione=[" + denominazione + "]");

		List<BeneficiarioSec> beneficiari = new ArrayList<>();

		denominazione = "%" + denominazione.trim() + "%";

		try {

			String sql = fileSqlUtil.getQuery("BeneficiarioSoggettoRuoloDenominazioneVO.sql");
			LOG.info(sql.toString());

			List<BeneficiarioSoggettoRuoloVO> listaBSR = getJdbcTemplate().query(sql.toString(),
					new Object[] { ruoloIride, idSoggetto, ruoloIride, denominazione, denominazione },
					new BeneficiarioSoggettoRuoloRowMapper());

			if (listaBSR != null && !listaBSR.isEmpty()) {
				LOG.info(prf + " listaBSR.size=" + listaBSR.size());

				for (BeneficiarioSoggettoRuoloVO beneficiarioSoggettoRuoloVO : listaBSR) {
					BeneficiarioSec ben = new BeneficiarioSec();
					ben.setCodiceFiscale(beneficiarioSoggettoRuoloVO.getCodiceFiscaleBeneficiario());
					ben.setDenominazione(beneficiarioSoggettoRuoloVO.getDenominazioneBeneficiario());
					ben.setIdBeneficiario(
							new Long(beneficiarioSoggettoRuoloVO.getIdSoggettoBeneficiario().longValue()));

					if (ben != null) {
						// LOG.info(prf + ben.toString());
						beneficiari.add(ben);
					}
				}
			}
		} catch (FileNotFoundException e) {
			LOG.error(prf + "FileNotFoundException BeneficiarioSoggettoRuoloDenominazioneVO.sql", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloDenominazioneVO.sql", e);
		} catch (IOException e) {
			LOG.error(prf + "IOException BeneficiarioSoggettoRuoloDenominazioneVO.sql", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloDenominazioneVO.sql", e);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read BeneficiarioSoggettoRuoloDenominazioneVO.sql", e);
			throw new DaoException("DaoException while trying to read BeneficiarioSoggettoRuoloDenominazioneVO.sql", e);
		} finally {
			LOG.info(prf + " END");
		}

		return beneficiari;
	}

	@Override
	public void memorizzaIride(Long idUtenteFittizio, String idIrideShib, String sessionId, String action)
			throws DaoException {
		String prf = "[ProfilazioneDAOImpl::memorizzaIride] ";

		LOG.info(prf + " BEGIN");

		// Tracciamento
		String operazione = OperazioniConstants.PROFILAZIONE_MEMORIZZAIRIDE;
		String esito = "S";
		Long idTracciamento = tracciamentoDAO.insertTraccia(operazione, idUtenteFittizio, esito);
		LOG.info(prf + " idTracciamento=" + idTracciamento);

		Long idAccessoIride = getIdAccessoIride(); //

		Long t1 = System.currentTimeMillis();

		String query = "INSERT into PBANDI_T_ACCESSO_IRIDE (ID_ACCESSO_IRIDE, SHIB_ID, IRIDE_ID, MSG , SESSION_ID, ACCESS_DATE ) VALUES (?,?,?,?,?, sysdate) ";

		LOG.debug(prf + "  param [idAccessoIride] = " + idAccessoIride);
		LOG.debug(prf + "  param [idIrideShib] = " + idIrideShib);
		LOG.debug(prf + "  param [sessionId] = " + sessionId);
		LOG.debug(prf + "  param [action] = " + action);
		LOG.debug(prf + "  query = " + query);

		try {
			getJdbcTemplate().update(query,
					new Object[] { idAccessoIride, idIrideShib, idIrideShib, action, sessionId });

		} catch (DataIntegrityViolationException ex) {
			LOG.debug(prf + "  Integrity Keys Violation ");
			if (ex instanceof DuplicateKeyException) {
				throw new DaoException("Chiave Duplicata", ex.getMostSpecificCause());
			}
			throw new DaoException("Nessun dato in base alla richiesta", ex);

		} catch (Throwable ex) {
			LOG.error(prf + " esecuzione query Failed ", ex);
			throw new DaoException("Errore di sistema", ex);
		}

		Long t2 = System.currentTimeMillis();
		tracciamentoDAO.updateTraccia(idTracciamento, t2 - t1);

		LOG.debug(prf + " END ");
	}

	private Long getIdAccessoIride() throws DaoException {
		String prf = "[ProfilazioneDAOImpl::getIdAccessoIride] ";
		LOG.info(prf + " BEGIN");
		Long id = null;

		try {

			String sql = "select SEQ_PBANDI_T_ACCESSO_IRIDE.nextval from dual ";
			LOG.debug(prf + sql);

			id = (Long) getJdbcTemplate().queryForObject(sql.toString(), Long.class);

		} catch (IncorrectResultSizeDataAccessException e) {
			LOG.error(prf + "IncorrectResultSizeDataAccessException while trying to read SEQ_PBANDI_T_ACCESSO_IRIDE",
					e);

		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SEQ_PBANDI_T_ACCESSO_IRIDE", e);
			throw new DaoException("DaoException while trying to read SEQ_PBANDI_T_ACCESSO_IRIDE", e);
		} finally {
			LOG.info(prf + " END");
		}

		return id;
	}

	@Override
	public boolean hasPermesso(Long idUSoggetto, Long idUtente, String descBreveTipoAnagrafica,
			String descBrevePermesso) throws DaoException {
		String prf = "[ProfilazioneDAOImpl::hasPermesso] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idUSoggetto=" + idUSoggetto + ", idUtente=" + idUtente + ", descBreveTipoAnagrafica="
				+ descBreveTipoAnagrafica + ", descBrevePermesso=" + descBrevePermesso);

		// descBreveTipoAnagrafica == ruolo

		boolean ret = false;

		try {
			// Tracciamento
			String operazione = OperazioniConstants.PROFILAZIONE_HASPERMESSO;
			String esito = "S";
			Long idTracciamento = tracciamentoDAO.insertTraccia(operazione, idUtente, esito);
			LOG.info(prf + " idTracciamento=" + idTracciamento);
			Long t1 = System.currentTimeMillis();

			String sql = fileSqlUtil.getQuery("SoggettoPermessoTipoAnagraficaVO.sql");
			LOG.debug(sql.toString());

			List<SoggettoPermessoTipoAnagraficaVO> lista = getJdbcTemplate().query(sql.toString(),
					new Object[] { descBreveTipoAnagrafica, descBrevePermesso, idUSoggetto },
					new SoggettoPermessoTipoAnagraficaRowMapper());

			if (lista != null && !lista.isEmpty()) {
				ret = true;
				LOG.info(prf + " lista.size()=" + lista.size());
			}

			Long t2 = System.currentTimeMillis();
			tracciamentoDAO.updateTraccia(idTracciamento, t2 - t1);

		} catch (FileNotFoundException e) {
			LOG.error(prf + "FileNotFoundException SoggettoPermessoTipoAnagraficaVO.sql", e);
			throw new DaoException("DaoException while trying to read SoggettoPermessoTipoAnagraficaVO", e);
		} catch (IOException e) {
			LOG.error(prf + "IOException SoggettoPermessoTipoAnagraficaVO.sql", e);
			throw new DaoException("DaoException while trying to read SoggettoPermessoTipoAnagraficaVO", e);
		} catch (Exception e) {
			LOG.error(prf + "Exception while trying to read SoggettoPermessoTipoAnagraficaVO", e);
			throw new DaoException("DaoException while trying to read SoggettoPermessoTipoAnagraficaVO", e);
		} finally {
			LOG.info(prf + " END");
		}
		LOG.info(prf + " END");
		return ret;
	}

	@Override
	public BeneficiarioVO findBeneficiario(Long idUtente, String identitaDigitale, Long idSoggettoBeneficiario)
			throws DaoException {

		String prf = "[ProfilazioneDAOImpl::findBeneficiario] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idSoggettoBeneficiario=" + idSoggettoBeneficiario + ", idUtente=" + idUtente);

		BeneficiarioVO beneficiarioVO = null;

		StringBuilder sqlSelect = new StringBuilder(
				" SELECT DISTINCT PBANDI_R_SOGGETTO_PROGETTO.id_soggetto as id_soggetto,").append(" CASE ")
				.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
				.append("      SELECT PBANDI_T_ENTE_GIURIDICO.denominazione_ente_giuridico")
				.append("      FROM PBANDI_T_ENTE_GIURIDICO")
				.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
				.append("    )").append("    ELSE (")
				.append("      SELECT PBANDI_T_PERSONA_FISICA.cognome || ' ' || PBANDI_T_PERSONA_FISICA.nome")
				.append("      FROM PBANDI_T_PERSONA_FISICA")
				.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
				.append("    )").append(" END as descrizione,").append(" CASE ")
				.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
				.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_forma_giuridica")
				.append("      FROM PBANDI_T_ENTE_GIURIDICO")
				.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
				.append("    )").append("    ELSE null").append(" END as idFormaGiuridica,").append(" CASE ")
				.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("     THEN (")
				.append("      SELECT PBANDI_T_ENTE_GIURIDICO.id_dimensione_impresa")
				.append("      FROM PBANDI_T_ENTE_GIURIDICO")
				.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
				.append("    )").append("    ELSE null").append(" END as idDimensioneImpresa,").append(" CASE ")
				.append("   WHEN PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO is not null").append("    THEN  (")
				.append("      SELECT PBANDI_T_ENTE_GIURIDICO.dt_inizio_validita")
				.append("      FROM PBANDI_T_ENTE_GIURIDICO")
				.append("      WHERE PBANDI_T_ENTE_GIURIDICO.id_ente_giuridico = PBANDI_R_SOGGETTO_PROGETTO.ID_ENTE_GIURIDICO")
				.append("    )").append("    ELSE (").append("      SELECT PBANDI_T_PERSONA_FISICA.dt_inizio_validita")
				.append("      FROM PBANDI_T_PERSONA_FISICA")
				.append("      WHERE PBANDI_T_PERSONA_FISICA.id_persona_fisica = PBANDI_R_SOGGETTO_PROGETTO.id_persona_fisica")
				.append("    )").append(" END as dataIniziovalidita,")
				.append(" PBANDI_T_SOGGETTO.CODICE_FISCALE_SOGGETTO as codiceFiscale");

		sqlSelect.append(" FROM PBANDI_R_SOGGETTO_PROGETTO,PBANDI_T_SOGGETTO ");
		sqlSelect.append(" WHERE PBANDI_R_SOGGETTO_PROGETTO.id_tipo_anagrafica = 1 ");
		sqlSelect.append(" AND nvl(PBANDI_R_SOGGETTO_PROGETTO.id_tipo_beneficiario, -1) <> 4");
		sqlSelect.append(" AND PBANDI_T_SOGGETTO.id_soggetto = PBANDI_R_SOGGETTO_PROGETTO.id_soggetto ");
		sqlSelect.append(" AND PBANDI_T_SOGGETTO.id_soggetto = ? ");

		LOG.info(prf + " sqlSelect=" + sqlSelect.toString());

		List<BeneficiarioVO> lista = new ArrayList<BeneficiarioVO>();

		try {
			lista = getJdbcTemplate().query(sqlSelect.toString(), new Object[] { idSoggettoBeneficiario },
					new PbandiBeneficiariRowMapper());
			if (lista != null && lista.size() > 0) {
				beneficiarioVO = lista.get(0);
			}
		} catch (EmptyResultDataAccessException e) {
			LOG.error("EmptyResultDataAccessException while trying to read BeneficiarioVO", e);
			throw new DaoException("DaoException while trying to read BeneficiarioVO", e);
		} catch (Exception e) {
			LOG.error("Exception while trying to read BeneficiarioVO", e);
			throw new DaoException("DaoException while trying to read BeneficiarioVO", e);
		} finally {
			LOG.info("[ProfilazioneDAOImpl::findSoggetto] END");
		}

		LOG.info(prf + " END");
		return beneficiarioVO;
	}

	@Override
	// Restituisce gli avvisi da visualizzare all'utente.
	public ArrayList<AvvisoUtenteDTO> avvisi(String codiceRuolo, Long idUtente, String idIride)
			throws InvalidParameterException, Exception {
		String prf = "[ProfilazioneDAOImpl::avvisi] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + "idUtente = " + idUtente + "; codiceRuolo = " + codiceRuolo);

		if (idUtente == null) {
			throw new InvalidParameterException("idUtente non valorizzato.");
		}
		if (codiceRuolo == null) {
			throw new InvalidParameterException("codiceRuolo non valorizzato.");
		}

		ArrayList<AvvisoUtenteDTO> result = new ArrayList<AvvisoUtenteDTO>();
		try {

			String sqlTipoAnag = "";
			sqlTipoAnag += "SELECT ID_TIPO_ANAGRAFICA FROM PBANDI_D_TIPO_ANAGRAFICA ";
			sqlTipoAnag += "WHERE DESC_BREVE_TIPO_ANAGRAFICA = '" + codiceRuolo + "'";
			LOG.info(prf + "\n" + sqlTipoAnag);
			Long idTipoAnag = (Long) getJdbcTemplate().queryForObject(sqlTipoAnag, Long.class);

			String sql = "";
			sql += "SELECT A.ID_NEWS, A.TITOLO, DESCRIZIONE, A.TIPO_NEWS, A.DT_INIZIO \r\n";
			sql += "FROM PBANDI_T_NEWS A, PBANDI_R_NEWS_TIPO_ANAGRAFICA B \r\n";
			sql += "WHERE B.ID_NEWS = A.ID_NEWS AND B.ID_TIPO_ANAGRAFICA = " + idTipoAnag
					+ " AND (A.DT_FINE IS NULL OR A.DT_FINE > SYSDATE) \r\n";
			sql += "ORDER BY A.DT_INIZIO DESC";
			LOG.info(prf + "\n" + sql);

			List<AvvisoUtenteVO> newsVO = getJdbcTemplate().query(sql, new BeanRowMapper(AvvisoUtenteVO.class));

			if (newsVO != null) {
				List<AvvisoUtenteDTO> lista = beanUtil.transformList(newsVO, AvvisoUtenteDTO.class);
				result = new ArrayList<AvvisoUtenteDTO>(lista);
			}

		} catch (Exception e) {
			String msg = "Errore durante la lettura degli avvisi.";
			LOG.error(prf + msg, e);
			throw new Exception(msg, e);
		} finally {
			LOG.info(prf + " END");
		}

		return result;
	}

	@Override
	public Boolean isRegolaApplicabileForBandoLinea(Long idBandoLinea, String codiceRegola, Long idUtente,
			String idIride) throws DaoException {
		String prf = "[ProfilazioneDAOImpl::isRegolaApplicabileForBandoLinea] ";
		LOG.info(prf + " BEGIN");
		LOG.info(prf + " idBandoLinea=" + idBandoLinea + ", codiceRegola=" + codiceRegola + ", idUtente=" + idUtente);

		Boolean ret = new Boolean(false);

		try {

			ret = regolaManager.isRegolaApplicabileForBandoLinea(idBandoLinea, codiceRegola);

		} catch (Exception e) {
			LOG.error(prf, e);
			throw new DaoException("Errore nella ricerca della regola.", e);
		} finally {
			LOG.info(prf + " END");
		}
		LOG.info(prf + " END");
		return ret;
	}

}
